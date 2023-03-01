package pers.catigeart.notice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import pers.catigeart.notice.entity.*;
import pers.catigeart.notice.mapper.OrgRoleMapper;
import pers.catigeart.notice.mapper.UserMapper;
import pers.catigeart.notice.mapper.UserOrgRoleMapper;
import pers.catigeart.notice.service.OrgRoleService;
import pers.catigeart.notice.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    OrgRoleMapper orgRoleMapper;

    @Autowired
    UserOrgRoleMapper userOrgRoleMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> findByOrg(Org org) {
        return userMapper.findByOrg(org);
        // Map<String, Object> params = new HashMap<>();
        //params.put("org_id", org.getId());
        //List<OrgRole> orgRoleList = orgRoleMapper.selectByMap(params);
        //List<UserOrgRole> userOrgRoleList = userOrgRoleMapper.findByOrgRole(orgRoleList);
    }

    @Override
    public List<User> findByKlass(Klass klass) {
        return userMapper.findByKlass(klass);
    }

    @Override
    public List<User> findWithRoleByKlass(Klass klass) {
        return userMapper.findWithRoleByKlass(klass);
    }

    @Override
    public List<User> findByName(String name) {
        Map<String, Object> params = new HashMap<>();
        params.put("u_name", name);
        return userMapper.selectByMap(params);
    }
}
