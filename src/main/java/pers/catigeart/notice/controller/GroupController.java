package pers.catigeart.notice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.catigeart.notice.dto.GroupDTO;
import pers.catigeart.notice.entity.Klass;
import pers.catigeart.notice.entity.Org;
import pers.catigeart.notice.entity.User;
import pers.catigeart.notice.entity.UserKlassRole;
import pers.catigeart.notice.service.*;
import pers.catigeart.notice.util.JwtUtil;
import pers.catigeart.notice.util.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Catigeart
 * GitHub: https://github.com/Catigeart
 * Time: 2022/5/18 17:20
 */
@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    UserService userService;

    @Autowired
    UserKlassRoleService userKlassRoleService;

    @Autowired
    UserOrgRoleService userOrgRoleService;

    @Autowired
    KlassService klassService;

    @Autowired
    OrgService orgService;

    @GetMapping
    public Result<List<GroupDTO>> getGroupList(HttpServletRequest request) {
        List<GroupDTO> groupDTOList = new ArrayList<>();

        String token = request.getHeader("token");
        String username = JwtUtil.getUserId(token);
        User user = userService.getById(username);

        GroupDTO klassGroup = new GroupDTO();

        Klass klass = klassService.getById(user.getKlassId());
        if (klass != null) {
            klassGroup.setId(klass.getId());
            klassGroup.setName(klass.getKlassName());
            klassGroup.setType("班级");
            groupDTOList.add(klassGroup);
        }

        List<Org> orgList = orgService.findByUser(user);
        for (Org org : orgList) {
            GroupDTO groupDTO = new GroupDTO();
            groupDTO.setId(org.getId());
            groupDTO.setType("通知组");
            groupDTO.setName(org.getOrgName());
            groupDTOList.add(groupDTO);
        }

        return Result.success(groupDTOList);
    }
}
