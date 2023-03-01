package pers.catigeart.notice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import pers.catigeart.notice.entity.Org;
import pers.catigeart.notice.entity.OrgRole;
import pers.catigeart.notice.entity.SuperRole;
import pers.catigeart.notice.entity.User;
import pers.catigeart.notice.mapper.OrgRoleMapper;
import pers.catigeart.notice.mapper.SuperRoleMapper;
import pers.catigeart.notice.model.AllRoleModel;
import pers.catigeart.notice.service.OrgRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Catigeart
 * @since 2022-05-18
 */
@Service
public class OrgRoleServiceImpl extends ServiceImpl<OrgRoleMapper, OrgRole> implements OrgRoleService {

    @Autowired
    OrgRoleMapper orgRoleMapper;

    @Autowired
    SuperRoleMapper superRoleMapper;

    @Override
    public List<OrgRole> findByOrg(Org org) {
        Map<String, Object> params = new HashMap<>();
        params.put("org_id", org.getId());
        return orgRoleMapper.selectByMap(params);
    }

    @Override
    public List<OrgRole> findByUser(User user) {
        return orgRoleMapper.findByUser(user);
    }

    @Override
    public OrgRole findByUsernameAndOrgRoleName(String username, String orgRoleName) {
        return orgRoleMapper.findByUsernameAndOrgRoleName(username, orgRoleName);
    }

    @Override
    public Boolean deleteByUser(User user) {
        Map<String, Object> params=  new HashMap<>();
        params.put("username", user.getUsername());
        orgRoleMapper.deleteByMap(params);
        return true;
    }

    @Override
    public Boolean insert(OrgRole orgRole) {
        orgRoleMapper.insertAndGetId(orgRole);
        return true;
    }

    @Override
    public AllRoleModel genAllRoleModel(OrgRole orgRole) {
        AllRoleModel allRoleModel = new AllRoleModel();
        allRoleModel.setId(orgRole.getId());
        allRoleModel.setName(orgRole.getRoleName());
        allRoleModel.setKlass(false);
        return allRoleModel;
    }

    @Override
    public List<AllRoleModel> genAllRoleModel(List<OrgRole> orgRoleList) {
        List<AllRoleModel> allRoleModelList = new ArrayList<>();
        for (OrgRole orgRole : orgRoleList) {
            allRoleModelList.add(genAllRoleModel(orgRole));
        }
        return allRoleModelList;
    }

    @Override
    public OrgRole findMemberRole(Integer orgId) {
        Map<String, Object> params = new HashMap<>();
        params.put("org_id", orgId);
        params.put("role_name", "成员"); // hard coding
        return orgRoleMapper.selectByMap(params).get(0);
    }

    @Deprecated
    @Override
    public OrgRole findSuperRoleByOrg(Org org) {
        Map<String, Object> params = new HashMap<>();
        params.put("org_id", org.getId());
        List<OrgRole> orgRoleList = orgRoleMapper.selectByMap(params);
        Map<String, Object> adminParams = new HashMap<>();
        adminParams.put("is_klass", 0);
        List<SuperRole> superRoleList = superRoleMapper.selectByMap(adminParams);
        for (OrgRole orgRole : orgRoleList) {
            for (SuperRole superRole : superRoleList) {
                if (Objects.equals(orgRole.getId(), superRole.getRoleId())) {
                    return orgRole;
                }
            }
        }
        return null;
    }

    @Override
    public List<OrgRole> findByUserAndOrg(User user, Org org) {
        return orgRoleMapper.findByUsernameAndOrgId(user.getUsername(), org.getId());
    }
}