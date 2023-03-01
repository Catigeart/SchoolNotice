package pers.catigeart.notice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import pers.catigeart.notice.api.Todo;
import pers.catigeart.notice.entity.*;
import pers.catigeart.notice.model.PersonalMsgModel;
import pers.catigeart.notice.service.*;
import pers.catigeart.notice.util.JwtUtil;
import pers.catigeart.notice.util.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Catigeart
 * @since 2022-05-21
 */
@RestController
@RequestMapping("/persMsg")
public class PersMsgController {
    @Autowired
    PersMsgService persMsgService;

    @Autowired
    MsgTypeService msgTypeService;

    @Autowired
    KlassService klassService;

    @Autowired
    UserKlassRoleService userKlassRoleService;

    @Autowired
    OrgRoleService orgRoleService;

    @Autowired
    UserOrgRoleService userOrgRoleService;

    @Autowired
    OrgService orgService;

    @Autowired
    UserService userService;

    @GetMapping(value = "/{id}")
    public Result<Boolean> confirmMsg(@PathVariable Integer id) {
        PersMsg persMsg = persMsgService.getById(id);
        persMsg.setIsDone(1);
        persMsgService.updateById(persMsg);
        return Result.success();
    }

    @GetMapping(value = "/{id}/accept")
    public Result<Boolean> acceptMsg(@PathVariable Integer id) {
        // 更新通知状态
        PersMsg persMsgReq = persMsgService.getById(id);
        // 现阶段只有msgTypeId=1, 邀请加入通知组需要选择接受/拒绝
        persMsgReq.setIsDone(1);
        persMsgService.updateById(persMsgReq);

        // 只能是通知组，因此不需要特别判断
        /*
        OrgRole memberRole = orgRoleService.findMemberRole(persMsgReq.getSendGroupId());
        UserOrgRole userOrgRole = new UserOrgRole();
        userOrgRole.setOrgRoleId(memberRole.getId());
        userOrgRole.setUsername(persMsgReq.getReceiveUsername());
        userOrgRoleService.insert(userOrgRole);
        */
        OrgRole orgRole = new OrgRole();
        orgRole.setOrgId(persMsgReq.getSendGroupId());
        orgRole.setRoleName("临时"+persMsgReq.getReceiveUsername());
        orgRoleService.insert(orgRole);
        UserOrgRole userOrgRole = new UserOrgRole();
        userOrgRole.setUsername(persMsgReq.getReceiveUsername());
        userOrgRole.setOrgRoleId(orgRole.getId());
        userOrgRoleService.save(userOrgRole);

        // 自动创建回复内容
        PersMsg persMsg = new PersMsg();
        persMsg.setMsgName("通知组邀请被接受");
        persMsg.setMsgTypeId(2); // 同意加入通知组
        persMsg.setIsOneSend(1);
        persMsg.setSendUsername(persMsgReq.getReceiveUsername());
        persMsg.setIsOneReceive(0);
        persMsg.setReceiveGroupId(persMsgReq.getSendGroupId());
        persMsg.setReceiveRoleId(persMsgReq.getSendRoleId());
        persMsg.setContent(persMsgReq.getReceiveUsername() +
                "已接受邀请，现已成为贵组的一员！");
        persMsg.setOperationId(1); // 需确认
        persMsg.setIsDone(0);
        persMsgService.save(persMsg);

        Todo.sendMessage();

        return Result.success();
    }

    @GetMapping(value = "/{id}/reject")
    public Result<Boolean> rejectMsg(@PathVariable int id) {
        PersMsg persMsgReq = persMsgService.getById(id);
        // 现阶段只有msgTypeId=1, 邀请加入通知组需要选择接受/拒绝
        // 自动回复给发送者邀请被拒绝
        persMsgReq.setIsDone(1);
        persMsgService.updateById(persMsgReq);
        PersMsg persMsg = persMsgService.initReplyMsg(persMsgReq);
        persMsg.setMsgName("通知组邀请被拒绝");
        persMsg.setMsgTypeId(3); // 拒绝加入通知组
        persMsg.setContent(persMsgReq.getReceiveUsername() +
                "拒绝了加入通知组的邀请。");
        persMsg.setOperationId(1); // 需确认
        persMsg.setIsDone(0);
        persMsgService.save(persMsg);

        Todo.sendMessage();

        return Result.success();
    }

    @Deprecated
    @GetMapping(value = "/{orgId}/invite/{username}")
    public Result<Boolean> invite(@PathVariable int orgId,
                                  @PathVariable  String username) {
        Org org = orgService.getById(orgId);
        OrgRole orgRole = orgRoleService.findSuperRoleByOrg(org);
        PersMsg persMsg = new PersMsg();
        persMsg.setMsgName("邀请加入通知组");
        persMsg.setMsgTypeId(1); // 邀请加入通知组
        persMsg.setIsOneSend(0);
        persMsg.setSendGroupId(orgId);
        persMsg.setSendRoleId(orgRole.getId());
        persMsg.setIsOneReceive(1);
        persMsg.setReceiveUsername(username);
        persMsg.setContent("您被邀请加入"+org.getOrgName()+"通知组，您可以选择接受或拒绝。");
        persMsg.setOperationId(2); // 需接受拒绝
        persMsg.setIsDone(0);

        persMsgService.insert(persMsg);

        return Result.success();
    }

    @GetMapping
    public Result<List<PersonalMsgModel>> findByCurrentUser(HttpServletRequest request) {
        String token = request.getHeader("token");
        String username = JwtUtil.getUserId(token);
        User user = userService.getById(username);
        user.setUsername(username);
        List<UserKlassRole> userKlassRoleList = userKlassRoleService.findByUser(user);
        List<OrgRole> orgRoleList = orgRoleService.findByUser(user);
        List<PersMsg> persMsgList = persMsgService.findByAllReceiver(userKlassRoleList, orgRoleList, user);
        List<PersonalMsgModel> personalMsgModelList = persMsgService.genModel(persMsgList);
        return Result.success(personalMsgModelList);
    }
}
