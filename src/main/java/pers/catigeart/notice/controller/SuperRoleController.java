package pers.catigeart.notice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import pers.catigeart.notice.entity.*;
import pers.catigeart.notice.service.*;
import pers.catigeart.notice.util.JwtUtil;
import pers.catigeart.notice.util.Result;
import pers.catigeart.notice.util.RuleUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Catigeart
 * @since 2022-05-22
 */
@RestController
@RequestMapping("/superRole")
public class SuperRoleController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserKlassRoleService userKlassRoleService;

    @Autowired
    private KlassService klassService;

    @Autowired
    private SuperRoleService superRoleService;

    @Autowired
    private OrgService orgService;

    @Autowired
    private OrgRoleService orgRoleService;

    @Autowired
    private UserOrgRoleService userOrgRoleService;

    @GetMapping
    public Result<Boolean> judgeIsSuperRole(HttpServletRequest request, int orgId) {
        String token = request.getHeader("token");
        String username = JwtUtil.getUserId(token);
        User user = userService.getById(username);
        boolean isKlass = RuleUtil.isKlass(orgId);
        if (isKlass) {
            Klass klass = klassService.getById(orgId);
            List<UserKlassRole> userKlassRoleList = userKlassRoleService.findByUser(user);
            for (UserKlassRole userKlassRole : userKlassRoleList) {
                if (superRoleService.isSuperRole(userKlassRole.getId())) {
                    return Result.success(true);
                }
            }
        } else {
            Org org = orgService.getById(orgId);
            List<UserOrgRole> userOrgRoleList = userOrgRoleService.findByUserAndOrg(user, org);
            for (UserOrgRole userOrgRole : userOrgRoleList) {
                OrgRole orgRole = orgRoleService.getById(userOrgRole.getOrgRoleId());
                if (superRoleService.isSuperRole(orgRole.getId())) {
                    return Result.success(true);
                }
            }
        }
        return Result.success(false);
    }
}
