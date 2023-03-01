package pers.catigeart.notice.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Catigeart
 * GitHub: https://github.com/Catigeart
 * Time: 2022/5/19 13:48
 */
public class KlassRoleDTO {

    private static final List<KlassRoleDTO> klassRoleDTOList = new ArrayList<>();

    private KlassRoleDTO(Integer id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    static {
        klassRoleDTOList.add(new KlassRoleDTO(1,"班长"));
        klassRoleDTOList.add(new KlassRoleDTO(2,"副班长"));
        klassRoleDTOList.add(new KlassRoleDTO(3,"团支书"));
        klassRoleDTOList.add(new KlassRoleDTO(4,"心理委员"));
        klassRoleDTOList.add(new KlassRoleDTO(5,"双创委员"));
        klassRoleDTOList.add(new KlassRoleDTO(6,"学习委员"));
        klassRoleDTOList.add(new KlassRoleDTO(7,"组宣委员"));
        klassRoleDTOList.add(new KlassRoleDTO(8,"文体委员"));
        klassRoleDTOList.add(new KlassRoleDTO(9,"生活委员"));
    }

    public Integer id;
    public String roleName;

    public static List<KlassRoleDTO> getInstance() {
        return klassRoleDTOList;
    }

    public static String getRoleNameById(int id) {
        for (KlassRoleDTO klassRoleDTO : klassRoleDTOList) {
            if (klassRoleDTO.id == id) {
                return klassRoleDTO.roleName;
            }
        }
        return null;
    }
}
