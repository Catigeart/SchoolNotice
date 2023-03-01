package pers.catigeart.notice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.catigeart.notice.dto.KlassDTO;
import pers.catigeart.notice.dto.NoticeDTO;
import pers.catigeart.notice.dto.ReplyDTO;
import pers.catigeart.notice.dto.SupplyDTO;
import pers.catigeart.notice.entity.*;
import pers.catigeart.notice.model.EmailModel;
import pers.catigeart.notice.service.*;
import pers.catigeart.notice.util.EmailUtil;
import pers.catigeart.notice.util.Result;
import pers.catigeart.notice.vo.NoticeVO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Catigeart
 * @since 2022-05-18
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    @Autowired
    OrgService orgService;

    @Autowired
    KlassService klassService;

    @Autowired
    SupplyService supplyService;

    @Autowired
    ReplyService replyService;

    @Autowired
    OrgRoleService orgRoleService;

    @Autowired
    KlassNoticeService klassNoticeService;

    @Autowired
    UserKlassRoleService userKlassRoleService;

    @Autowired
    UserService userService;

    @Autowired
    PersMsgService persMsgService;

    @GetMapping
    public Result<List<NoticeDTO>> findByOrgOrKlass(@RequestParam int id) {
        List<Notice> noticeList;
        if (id < 10000) {
            noticeList = noticeService.findByOrg(orgService.getById(id));
        } else {
            noticeList = noticeService.findByKlass(klassService.getById(id));
        }
        List<NoticeDTO> noticeDTOList = new ArrayList<>();
        for (Notice notice : noticeList) {
            NoticeDTO noticeDTO = new NoticeDTO();
            OrgRole orgRole = orgRoleService.getById(notice.getOrgRoleId());
            // 信息流范围受限
            List<SupplyDTO> supplyList = supplyService.findByNoticeWithLimit(notice, id);
            List<Reply> replyList = replyService.findByNoticeWithLimit(notice, id); // TODO：WRONG
            List<ReplyDTO> replyDTOList = new ArrayList<>();
            for (Reply reply : replyList) {
                ReplyDTO replyDTO = replyService.reply2ReplyDTO(reply);
                replyDTOList.add(replyDTO);
            }

            noticeDTO.setId(notice.getId());
            noticeDTO.setNoticeName(notice.getNoticeName());
            noticeDTO.setNoticeType(notice.getNoticeType());
            noticeDTO.setContent(notice.getContent());
            noticeDTO.setOrgRole(orgRole);
            noticeDTO.setKlassRoleName(notice.getKlassRoleName());
            noticeDTO.setBeginTime(notice.getBeginTime());
            noticeDTO.setEndTime(notice.getEndTime());
            noticeDTO.setIsNeedReply(notice.getIsNeedReply());
            noticeDTO.setSupplyList(supplyList);
            noticeDTO.setReplyList(replyDTOList);

            noticeDTOList.add(noticeDTO);
        }

        return Result.success(noticeDTOList);
    }

    @PostMapping(value = "/add")
    public Result<Boolean> addNotice(@RequestBody NoticeVO noticeVO) throws Exception {
        String roleName; // needed by email

        Notice notice = new Notice();
        notice.setNoticeName(noticeVO.getNoticeName());
        notice.setNoticeType(noticeVO.getNoticeType());
        notice.setContent(noticeVO.getContent());
        if (noticeVO.getGroupId() < 10000) { // 组织发送
            notice.setOrgRoleId(noticeVO.getRoleId());
            roleName = orgRoleService.getById(noticeVO.getRoleId()).getRoleName();

            notice.setKlassRoleName(noticeVO.getKlassRole());

            // TODO: 向对接班委发送处理的通知，注意判空
            if (noticeVO.getKlassRole() != null && noticeVO.getKlassRole().length() != 0) {


                List<KlassDTO> klassDTOList = noticeVO.getCheckedKlassList();
                for (KlassDTO klassDTO : klassDTOList) {
                    Klass klass =  klassService.getById(klassDTO.getId());
                    List<User> userList = userService.findByKlass(klass);
                    for (User user : userList) {
                        List<UserKlassRole> userKlassRoleList = userKlassRoleService.findByUsernameAndKlassRoleName(user.getUsername(), noticeVO.getKlassRole());
                        for (UserKlassRole userKlassRole : userKlassRoleList) {
                            PersMsg persMsg = new PersMsg();
                            persMsg.setMsgName("班委对接提醒");
                            persMsg.setMsgTypeId(4);
                            persMsg.setContent("你有新的需要对接的通知事务：" +
                                    noticeVO.getContent() +
                                    "，请前往通知管理页面查看！");
                            persMsg.setOperationId(1);
                            persMsg.setIsDone(0);

                            persMsg.setIsOneSend(0);
                            persMsg.setSendGroupId(noticeVO.getGroupId());
                            persMsg.setSendRoleId(noticeVO.getRoleId());
                            persMsg.setIsOneReceive(0);


                            persMsg.setReceiveGroupId(klass.getId());
                            persMsg.setReceiveRoleId(userKlassRole.getId());
                            persMsgService.save(persMsg);
                        }
                    }
                }
            }

        } else {
            UserKlassRole userKlassRole = userKlassRoleService.getById(noticeVO.getRoleId());
            notice.setKlassRoleName(userKlassRole.getKlassRoleName());
            roleName = userKlassRole.getKlassRoleName();
        }
        notice.setBeginTime(LocalDateTime.now());
        notice.setEndTime(noticeVO.getEndTime());
        notice.setIsNeedReply(noticeVO.getIsNeedReply());

        noticeService.saveAndGetId(notice);
        int noticeId = notice.getId();

        List<KlassNotice> klassNoticeList = new ArrayList<>();

        if (noticeVO.getCheckedKlassList() != null) { // 其实应该不能为空（
            for (KlassDTO klassDTO : noticeVO.getCheckedKlassList()) {
                KlassNotice klassNotice = new KlassNotice();
                klassNotice.setNoticeId(noticeId);
                klassNotice.setKlassId(klassDTO.getId());
                klassNoticeList.add(klassNotice);
            }
            klassNoticeService.saveBatch(klassNoticeList);
        }

        // 发送电子邮件
        if (noticeVO.getIsSendEmail()) {
            EmailModel emailModel = EmailUtil.genNoticeEmail(roleName, noticeVO);
            EmailUtil.sendEmail(emailModel);
        }

        return Result.success();
    }
}