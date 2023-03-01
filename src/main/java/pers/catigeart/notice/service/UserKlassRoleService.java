package pers.catigeart.notice.service;

import pers.catigeart.notice.entity.Org;
import pers.catigeart.notice.entity.User;
import pers.catigeart.notice.entity.UserKlassRole;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.catigeart.notice.model.AllRoleModel;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Catigeart
 * @since 2022-05-18
 */
public interface UserKlassRoleService extends IService<UserKlassRole> {
    List<UserKlassRole> findByUser(User user);

    List<UserKlassRole> findByUsernameAndKlassRoleName(String username, String klassRoleName);

    Boolean deleteByUser(User user);

    AllRoleModel genAllRoleModel(UserKlassRole userKlassRole);

    List<AllRoleModel> genAllRoleModel(List<UserKlassRole> userKlassRoleList);

}
