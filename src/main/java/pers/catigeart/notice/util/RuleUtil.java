package pers.catigeart.notice.util;

import pers.catigeart.notice.dto.RoleDTO;
import pers.catigeart.notice.entity.OrgRole;
import pers.catigeart.notice.entity.UserKlassRole;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Catigeart
 * GitHub: https://github.com/Catigeart
 * Time: 2022/5/21 13:59
 */
public class RuleUtil {

    public static boolean isKlass(int groupId) {
        return groupId > 10000;
    }

    public static List<RoleDTO> userKlassRole2RoleDTO(List<UserKlassRole> userKlassRoleList) {
        List<RoleDTO> roleDTOList = new ArrayList<>();
        for (UserKlassRole userKlassRole : userKlassRoleList) {
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setId(userKlassRole.getId());
            roleDTO.setName(userKlassRole.getKlassRoleName());
            roleDTOList.add(roleDTO);
        }
        return roleDTOList;
    }

    public static List<RoleDTO> orgRole2RoleDTO(List<OrgRole> orgRoleList) {
        List<RoleDTO> roleDTOList = new ArrayList<>();
        for (OrgRole orgRole : orgRoleList) {
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setId(orgRole.getId());
            roleDTO.setName(orgRole.getRoleName());
            roleDTOList.add(roleDTO);
        }
        return roleDTOList;
    }
}
