package pers.catigeart.notice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import pers.catigeart.notice.entity.SuperRole;
import pers.catigeart.notice.mapper.SuperRoleMapper;
import pers.catigeart.notice.service.SuperRoleService;
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
 * @since 2022-05-22
 */
@Service
public class SuperRoleServiceImpl extends ServiceImpl<SuperRoleMapper, SuperRole> implements SuperRoleService {
    @Autowired
    SuperRoleMapper superRoleMapper;

    @Override
    public boolean isSuperRole(int roleId) {
        Map<String, Object> params = new HashMap<>();
        params.put("role_id", roleId);
        List<SuperRole> superRoleList =  superRoleMapper.selectByMap(params);
        return superRoleList.size() != 0;
    }
}
