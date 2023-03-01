package pers.catigeart.notice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.catigeart.notice.dto.KlassRoleDTO;
import pers.catigeart.notice.dto.MemberDTO;
import pers.catigeart.notice.dto.RoleDTO;
import pers.catigeart.notice.entity.OrgRole;
import pers.catigeart.notice.entity.User;
import pers.catigeart.notice.entity.UserKlassRole;
import pers.catigeart.notice.entity.UserOrgRole;
import pers.catigeart.notice.service.OrgRoleService;
import pers.catigeart.notice.service.UserKlassRoleService;
import pers.catigeart.notice.service.UserOrgRoleService;
import pers.catigeart.notice.service.UserService;
import pers.catigeart.notice.util.CodeUtil;
import pers.catigeart.notice.util.JwtUtil;
import pers.catigeart.notice.util.Result;
import pers.catigeart.notice.util.RuleUtil;
import pers.catigeart.notice.vo.GrantUserVO;

import javax.servlet.http.HttpServletRequest;
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
// @RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserKlassRoleService userKlassRoleService;

    @Autowired
    UserOrgRoleService userOrgRoleService;

    @Autowired
    OrgRoleService orgRoleService;

    @PostMapping(value = "/login")
    public Result<String> login(@RequestBody User user) {
        User userDB = userService.getById(user.getUsername());
        //System.out.println(userDB);
        if (userDB == null) {
            return Result.fail(CodeUtil.FAILURE.getCode(), "该用户不存在！");
        } else if (!userDB.getPassword().equals(user.getPassword())) {
            return Result.fail(CodeUtil.FAILURE.getCode(), "用户密码错误！");
        } else {
            Map<String, Object> info = new HashMap<>();
            //System.out.println("flag");
            String token = JwtUtil.sign(user.getUsername(), info);
            //System.out.println("flag");
            return Result.success(token);
        }
    }

    @GetMapping(value = "/userInfo")
    public Result<User> getCurrentUser(HttpServletRequest request) {
        String token = request.getHeader("token");
        String username = JwtUtil.getUserId(token);
        User user = userService.getById(username);
        user.setPassword("***");
        return Result.success(user);
    }

    @PostMapping(value = "/user/grant")
    public Result<Boolean> grantUser(@RequestBody GrantUserVO grantUserVO
                                     ) {
        MemberDTO member = grantUserVO.getMember();
        RoleDTO role = grantUserVO.getRole();
        String groupId = grantUserVO.getGroupId();
        int gId = Integer.parseInt(groupId);
        if (gId < 10000) { // group

            UserOrgRole userOrgRole = new UserOrgRole();
            userOrgRole.setOrgRoleId(role.getId());
            userOrgRole.setUsername(member.getUsername());
            userOrgRoleService.save(userOrgRole);
        } else { // klass
            role.setName(KlassRoleDTO.getRoleNameById(role.getId()));
            UserKlassRole userKlassRole = new UserKlassRole();
            userKlassRole.setUsername(member.getUsername());
            userKlassRole.setKlassRoleName(role.getName());
            userKlassRoleService.save(userKlassRole);
        }

        return Result.success();
    }

    @GetMapping(value = "/user/revoke")
    public Result<Boolean> revokeUser(@RequestParam String username,
                                      @RequestParam String groupId,
                                      @RequestParam String roleId) {
        int gId = Integer.parseInt(groupId);
        boolean isKlass = RuleUtil.isKlass(gId);
        if (isKlass) {
            userKlassRoleService.removeById(roleId);
        } else {
            Map<String, Object> params = new HashMap<>();
            params.put("username", username);
            params.put("org_role_id", roleId);
            userOrgRoleService.removeByMap(params);
        }
        return Result.success();
    }

    @GetMapping(value = "/user")
    public Result<List<MemberDTO>> findUserByName(@RequestParam String name) {
        List<User> userList = userService.findByName(name);
        List<MemberDTO> memberDTOList = new ArrayList<>();

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

        return Result.success(memberDTOList);
    }
}