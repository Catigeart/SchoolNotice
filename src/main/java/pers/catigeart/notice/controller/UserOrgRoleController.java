package pers.catigeart.notice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.catigeart.notice.dto.MemberDTO;
import pers.catigeart.notice.dto.RoleDTO;
import pers.catigeart.notice.entity.OrgRole;
import pers.catigeart.notice.entity.User;
import pers.catigeart.notice.entity.UserKlassRole;
import pers.catigeart.notice.service.OrgRoleService;
import pers.catigeart.notice.service.UserKlassRoleService;
import pers.catigeart.notice.util.Result;
import pers.catigeart.notice.util.RuleUtil;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Catigeart
 * @since 2022-05-18
 */
@RestController
@RequestMapping("/userOrgRole")
public class UserOrgRoleController {

    @Autowired
    UserKlassRoleService userKlassRoleService;

    @Autowired
    OrgRoleService orgRoleService;

    @PostMapping(value = "/delete")
    public Result<Boolean> deleteByMemberAndRole(@RequestBody MemberDTO member,
                                                 @RequestBody RoleDTO role,
                                                 @RequestBody String groupId) {
        int gid = Integer.parseInt(groupId);
        boolean isKlass = RuleUtil.isKlass(gid);
        if (isKlass) {
            String username = member.getUsername();
            String klassRoleName = role.getName();
            UserKlassRole userKlassRole =
                    userKlassRoleService.findByUsernameAndKlassRoleName(username, klassRoleName).get(0);
            userKlassRoleService.removeById(userKlassRole);
        } else {
            String username = member.getUsername();
            String orgRoleName = role.getName();
            OrgRole orgRole = orgRoleService.findByUsernameAndOrgRoleName(username, orgRoleName);
            orgRoleService.removeById(orgRole);
        }
        return Result.success();
    }

    @PostMapping(value = "/deleteAll")
    public Result<Boolean> deleteAllByMember(@RequestBody MemberDTO member,
                                             @RequestBody String groupId) {
        int gid = Integer.parseInt(groupId);
        boolean isKlass = RuleUtil.isKlass(gid);
        User user = new User();
        user.setUsername(member.getUsername());
        if (isKlass) {
            return Result.success(userKlassRoleService.deleteByUser(user));
        } else {
            return Result.success(orgRoleService.deleteByUser(user));
        }
    }
}
