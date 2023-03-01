package pers.catigeart.notice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.catigeart.notice.dto.KlassDTO;
import pers.catigeart.notice.dto.MemberDTO;
import pers.catigeart.notice.entity.Klass;
import pers.catigeart.notice.entity.Org;
import pers.catigeart.notice.entity.User;
import pers.catigeart.notice.entity.UserKlassRole;
import pers.catigeart.notice.service.KlassService;
import pers.catigeart.notice.service.OrgService;
import pers.catigeart.notice.service.UserKlassRoleService;
import pers.catigeart.notice.service.UserService;
import pers.catigeart.notice.util.Result;

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
@RequestMapping("/klass")
public class KlassController {
    @Autowired
    UserService userService;

    @Autowired
    KlassService klassService;

    @Autowired
    UserKlassRoleService userKlassRoleService;

    @Autowired
    OrgService orgService;

    @GetMapping
    public Result<List<KlassDTO>> findRangeKlass(@RequestParam int id) {
        List<KlassDTO> klassDTOList;
        if (id < 10000) {
            Org org = orgService.getById(id);
            switch (org.getBelongingLevel()) {
                case "sch":
                    klassDTOList = klassService.findKlassDTOBySch(org.getBelongingName());
                    break;
                case "dept":
                    klassDTOList = klassService.findKlassDTOByDept(org.getBelongingName());
                    break;
                case "major":
                    klassDTOList = klassService.findKlassDTOByMajor(org.getBelongingName());
                    break;
                case "grade":
                    klassDTOList = klassService.findKlassDTOByGrade(org.getBelongingName());
                    break;
                default:
                    klassDTOList = new ArrayList<>();
            }
        } else {
            klassDTOList = new ArrayList<>();
            Klass klass = klassService.getById(id);
            KlassDTO klassDTO = new KlassDTO();
            klassDTO.setId(klass.getId());
            klassDTO.setName(klass.getKlassName());
            klassDTOList.add(klassDTO);
        }

        return Result.success(klassDTOList);
    }

    @PostMapping(value = "/{id}/member-list")
    public Result<Object> findMemberByKlass(@PathVariable int id) {
        Klass klass = klassService.getById(id);
        List<User> userList = userService.findByKlass(klass);
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

    @PostMapping(value = "/{id}/role-list")
    public Result<List<User>> findRoleByKlass(@PathVariable int id) {
        Klass klass = klassService.getById(id);
        List<User> userList = userService.findByKlass(klass);
        return Result.success(userList);
    }
}
