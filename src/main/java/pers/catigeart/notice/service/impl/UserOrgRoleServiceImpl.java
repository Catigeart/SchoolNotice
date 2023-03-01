package pers.catigeart.notice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import pers.catigeart.notice.entity.Org;
import pers.catigeart.notice.entity.OrgRole;
import pers.catigeart.notice.entity.User;
import pers.catigeart.notice.entity.UserOrgRole;
import pers.catigeart.notice.mapper.UserOrgRoleMapper;
import pers.catigeart.notice.service.UserOrgRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class UserOrgRoleServiceImpl extends ServiceImpl<UserOrgRoleMapper, UserOrgRole> implements UserOrgRoleService {
    @Autowired
    UserOrgRoleMapper userOrgRoleMapper;

    @Override
    public Boolean insert(UserOrgRole userOrgRole) {
        userOrgRoleMapper.insert(userOrgRole);
        return true;
    }

    @Override
    public List<UserOrgRole> findByUserAndOrg(User user, Org org) {
        return userOrgRoleMapper.findByUsernameAndOrgId(user.getUsername(), org.getId());
    }

    @Override
    public List<UserOrgRole> findByOrg(Org org) {
        return userOrgRoleMapper.findByOrg(org);
    }

    @Override
    public List<UserOrgRole> findByOrgRole(OrgRole orgRole) {
        Map<String, Object> params = new HashMap<>();
        params.put("org_role_id", orgRole.getId());
        return userOrgRoleMapper.selectByMap(params);
    }
}
