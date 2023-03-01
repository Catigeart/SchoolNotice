package pers.catigeart.notice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.catigeart.notice.dto.MemberDTO;
import pers.catigeart.notice.dto.RoleDTO;
import pers.catigeart.notice.entity.OrgRole;
import pers.catigeart.notice.entity.User;
import pers.catigeart.notice.entity.UserKlassRole;
import pers.catigeart.notice.entity.UserOrgRole;
import pers.catigeart.notice.service.MemberService;
import pers.catigeart.notice.service.OrgRoleService;
import pers.catigeart.notice.service.UserKlassRoleService;
import pers.catigeart.notice.service.UserOrgRoleService;
import pers.catigeart.notice.util.JwtUtil;
import pers.catigeart.notice.util.Result;
import pers.catigeart.notice.util.RuleUtil;
import pers.catigeart.notice.vo.OwnRoleVO;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Catigeart
 * GitHub: https://github.com/Catigeart
 * Time: 2022/5/19 17:20
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    UserKlassRoleService userKlassRoleService;

    @Autowired
    UserOrgRoleService userOrgRoleService;

    @Autowired
    OrgRoleService orgRoleService;

    @Autowired
    MemberService memberService;

    @GetMapping
    public Result<List<RoleDTO>> findCurrentRoles(HttpServletRequest request, @RequestParam int id) {
        String token = request.getHeader("token");
        String username = JwtUtil.getUserId(token);
        User user = new User();
        user.setUsername(username);
        List<RoleDTO> roleDTOList;

        if (id < 10000) { // group
            List<OrgRole> orgRoleList = orgRoleService.findByUser(user);
            roleDTOList = RuleUtil.orgRole2RoleDTO(orgRoleList);
        } else { // klass
            List<UserKlassRole> userKlassRoleList =  userKlassRoleService.findByUser(user);
            roleDTOList = RuleUtil.userKlassRole2RoleDTO(userKlassRoleList);
        }

        return Result.success(roleDTOList);
    }

    @PostMapping(value = "/own")
    public Result<List<RoleDTO>> findOwnRole(@RequestBody OwnRoleVO ownRoleVO) {
        MemberDTO member = ownRoleVO.getMember();
        String groupId = ownRoleVO.getGroupId();

        List<RoleDTO> roleDTOList = new ArrayList<>();

        String username = member.getUsername();
        User user = new User();
        user.setUsername(username);

        int gid = Integer.parseInt(groupId);
        boolean isKlass = RuleUtil.isKlass(gid);
        if (isKlass) {
            List<UserKlassRole> userKlassRoleList = userKlassRoleService.findByUser(user);
            for (UserKlassRole userKlassRole : userKlassRoleList) {
                RoleDTO roleDTO = new RoleDTO();
                roleDTO.setId(userKlassRole.getId());
                roleDTO.setName(userKlassRole.getKlassRoleName());
                roleDTOList.add(roleDTO);
            }
        } else {
            List<OrgRole> orgRoleList = orgRoleService.findByUser(user);
            for (OrgRole orgRole : orgRoleList) {
                RoleDTO roleDTO = new RoleDTO();
                roleDTO.setId(orgRole.getId());
                roleDTO.setName(orgRole.getRoleName());
                roleDTOList.add(roleDTO);
            }
        }
        return Result.success(roleDTOList);
    }
}