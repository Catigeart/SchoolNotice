package pers.catigeart.notice.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pers.catigeart.notice.entity.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Catigeart
 * @since 2022-05-21
 */
@Repository
public interface PersMsgMapper extends BaseMapper<PersMsg> {
    //List<PersMsg> findByUserKlassRoleList(@Param("list") List<UserKlassRole> userKlassRoleList);

    //List<PersMsg> findByOrgRoleList(List<OrgRole> orgRoleList);

    //List<PersMsg> findByUser(User user);

    @Deprecated
    List<PersMsg> findByAllReceiver(@Param("ukrList") List<UserKlassRole> userKlassRoleList,
                                    @Param("orList") List<OrgRole> orgRoleList,
                                    @Param("user") User user);
}
