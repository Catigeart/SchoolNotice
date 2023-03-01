package pers.catigeart.notice.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pers.catigeart.notice.entity.Org;
import pers.catigeart.notice.entity.OrgRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import pers.catigeart.notice.entity.User;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Catigeart
 * @since 2022-05-18
 */
@Repository
public interface OrgRoleMapper extends BaseMapper<OrgRole> {

    List<OrgRole> findByUser(User user);

    OrgRole findByUsernameAndOrgRoleName(@Param("username") String username,
                                         @Param("orgRoleName") String orgRoleName);

    List<OrgRole> findByUsernameAndOrgId(@Param("username") String username, @Param("orgId") int orgId);

    void insertAndGetId(OrgRole orgRole);
}
