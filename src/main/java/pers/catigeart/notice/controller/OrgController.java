package pers.catigeart.notice.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import pers.catigeart.notice.dto.KlassRoleDTO;
import pers.catigeart.notice.dto.MemberDTO;
import pers.catigeart.notice.entity.*;
import pers.catigeart.notice.service.*;
import pers.catigeart.notice.util.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Catigeart
 * @since 2022-05-18
 */
@RestController
@RequestMapping("/org")
public class OrgController {

    @Autowired
    OrgService orgService;

    @Autowired
    OrgRoleService orgRoleService;

    @Autowired
    UserService userService;

    @Autowired
    UserOrgRoleService userOrgRoleService;

    @Autowired
    VMemberService vMemberService;

    @Autowired
    KlassService klassService;

    @Autowired
    UserKlassRoleService userKlassRoleService;

    @PostMapping(value = "/{id}/user-list")
    public Result<List<User>> getUserByOrg(@PathVariable int id) {
        Org org = orgService.getById(id);
        List<User> userList = userService.findByOrg(org);
        return Result.success(userList);
    }

    static class InnerMember {
        String name;
        String sex;
        List<String> roles;


    }

    @RequestMapping(value = "/{idStr}/member-list")
    public Result<List<MemberDTO>> getMemberByOrg(@PathVariable String idStr) {

        int id = Integer.parseInt(idStr);
        // 代表性的屎山
        List<MemberDTO> memberDTOList = new ArrayList<>();

        if (id < 10000) {
            Org org = orgService.getById(id);

            List<UserOrgRole> userOrgRoleList = userOrgRoleService.findByOrg(org);
            Map<String, InnerMember> vMemberMap = new HashMap<>();
            for (UserOrgRole userOrgRole : userOrgRoleList) {
                String username = userOrgRole.getUsername();
                if (!vMemberMap.containsKey(username)) {
                    InnerMember innerMember = new InnerMember();
                    innerMember.roles = new ArrayList<>();
                    vMemberMap.put(username, innerMember);
                }
                User user = userService.getById(username);
                vMemberMap.get(username).name = user.getUName();
                vMemberMap.get(username).sex = user.getSex();
                OrgRole orgRole = orgRoleService.getById(userOrgRole.getOrgRoleId());
                vMemberMap.get(username).roles.add(orgRole.getRoleName());
            }

            for (Map.Entry<String, InnerMember> entry : vMemberMap.entrySet()) {
                MemberDTO memberDTO = new MemberDTO();
                memberDTO.setUsername(entry.getKey());
                memberDTO.setName(entry.getValue().name);
                memberDTO.setSex(entry.getValue().sex);
                User user = userService.getById(entry.getKey());

                StringBuilder stringBuilder = new StringBuilder();

                if (user.getIsStuRole() == 1) {
                    stringBuilder.append("学生");
                } else {
                    stringBuilder.append("教师");
                }

                for (String role : entry.getValue().roles) {
                    stringBuilder.append("，").append(role);
                }

                memberDTO.setRoles(stringBuilder.toString());
                memberDTOList.add(memberDTO);
            }
        } else {
            Klass klass = klassService.getById(id);
            List<User> userList = userService.findByKlass(klass);
            for (User user : userList) {
                MemberDTO memberDTO = new MemberDTO();
                memberDTO.setUsername(user.getUsername());
                memberDTO.setName(user.getUName());
                memberDTO.setSex(user.getSex());
                StringBuilder stringBuilder = new StringBuilder(100);
                if (user.getIsStuRole() == 0) {
                    stringBuilder.append("教师");
                } else {
                    stringBuilder.append("学生");
                }
                List<UserKlassRole> userKlassRoleList = userKlassRoleService.findByUser(user);
                for (UserKlassRole userKlassRole : userKlassRoleList) {
                    stringBuilder.append(",");
                    stringBuilder.append(userKlassRole.getKlassRoleName());
                }
                memberDTO.setRoles(stringBuilder.toString());
                memberDTOList.add(memberDTO);
            }
        }

        return Result.success(memberDTOList);
    }

    @GetMapping(value = "/{id}/role-list")
    public Result<Object> getRoleByOrg(@PathVariable int id) {
        if (id < 10000) {
            Org org = orgService.getById(id);
            List<OrgRole> orgRoleList = orgRoleService.findByOrg(org);
            return Result.success(orgRoleList);
        } else {
            return Result.success(KlassRoleDTO.getInstance());
        }

    }
}
