package pers.catigeart.notice.service;

import org.springframework.beans.factory.annotation.Autowired;
import pers.catigeart.notice.entity.Org;
import pers.catigeart.notice.entity.OrgRole;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.catigeart.notice.entity.User;
import pers.catigeart.notice.mapper.OrgRoleMapper;
import pers.catigeart.notice.model.AllGroupModel;
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
public interface OrgRoleService extends IService<OrgRole> {
    List<OrgRole> findByOrg(Org org);

    List<OrgRole> findByUser(User user);

    OrgRole findByUsernameAndOrgRoleName(String username, String orgRoleName);

    Boolean deleteByUser(User user);

    Boolean insert(OrgRole orgRole);

    AllRoleModel genAllRoleModel(OrgRole orgRole);

    List<AllRoleModel> genAllRoleModel(List<OrgRole> orgRoleList);

    OrgRole findMemberRole(Integer orgId);

    OrgRole findSuperRoleByOrg(Org org);

    List<OrgRole> findByUserAndOrg(User user, Org org);
}
