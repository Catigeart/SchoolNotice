package pers.catigeart.notice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import pers.catigeart.notice.dto.RoleDTO;
import pers.catigeart.notice.entity.Org;
import pers.catigeart.notice.entity.OrgRole;
import pers.catigeart.notice.entity.UserOrgRole;
import pers.catigeart.notice.service.OrgRoleService;
import pers.catigeart.notice.service.UserOrgRoleService;
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
 * @since 2022-05-18
 */
@RestController
@RequestMapping("/orgRole")
public class OrgRoleController {

    @Autowired
    UserOrgRoleService userOrgRoleService;

    @Autowired
    OrgRoleService orgRoleService;


    @PostMapping(value = "/edit")
    public Result<Boolean> updateOrgRole(@RequestBody RoleDTO role) {
        OrgRole orgRole = orgRoleService.getById(role.getId());
        orgRole.setRoleName(role.getName());
        orgRoleService.updateById(orgRole);
        return Result.success();
    }

    @GetMapping(value = "/add")
    public Result<Boolean> addRole(@RequestParam String addRoleName, @RequestParam String groupId) {
        OrgRole orgRole = new OrgRole();
        orgRole.setRoleName(addRoleName);
        orgRole.setOrgId(Integer.parseInt(groupId));
        orgRoleService.insert(orgRole);
        return Result.success();
    }

    @GetMapping(value = "/delete")
    public Result<Boolean> deleteRole(@RequestParam String id) {
        OrgRole orgRole = new OrgRole();
        orgRole.setId(Integer.parseInt(id));
        List<UserOrgRole> userOrgRoleList = userOrgRoleService.findByOrgRole(orgRole);
        if (userOrgRoleList != null) {
            return Result.success(false);
        }

        orgRoleService.removeById(id);
        return Result.success(true);
    }
}
