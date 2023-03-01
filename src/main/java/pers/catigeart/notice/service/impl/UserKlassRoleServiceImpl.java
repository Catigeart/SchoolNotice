package pers.catigeart.notice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import pers.catigeart.notice.entity.Org;
import pers.catigeart.notice.entity.User;
import pers.catigeart.notice.entity.UserKlassRole;
import pers.catigeart.notice.mapper.UserKlassRoleMapper;
import pers.catigeart.notice.model.AllRoleModel;
import pers.catigeart.notice.service.UserKlassRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Catigeart
 * @since 2022-05-18
 */
@Service
public class UserKlassRoleServiceImpl extends ServiceImpl<UserKlassRoleMapper, UserKlassRole> implements UserKlassRoleService {

    @Autowired
    UserKlassRoleMapper userKlassRoleMapper;

    @Override
    public List<UserKlassRole> findByUser(User user) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", user.getUsername());
        return userKlassRoleMapper.selectByMap(params);
    }

    @Override
    public List<UserKlassRole> findByUsernameAndKlassRoleName(String username, String klassRoleName) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("klass_role_name", klassRoleName);
        return userKlassRoleMapper.selectByMap(params);
    }

    @Override
    public Boolean deleteByUser(User user) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", user.getUsername());
        userKlassRoleMapper.deleteByMap(params);
        return true;
    }

    @Override
    public AllRoleModel genAllRoleModel(UserKlassRole userKlassRole) {
        AllRoleModel allRoleModel = new AllRoleModel();
        allRoleModel.setId(userKlassRole.getId());
        allRoleModel.setName(userKlassRole.getKlassRoleName());
        allRoleModel.setKlass(true);
        return allRoleModel;
    }

    @Override
    public List<AllRoleModel> genAllRoleModel(List<UserKlassRole> userKlassRoleList) {
        List<AllRoleModel> allRoleModelList = new ArrayList<>();
        for (UserKlassRole userKlassRole : userKlassRoleList) {
            allRoleModelList.add(genAllRoleModel(userKlassRole));
        }
        return allRoleModelList;
    }

}
