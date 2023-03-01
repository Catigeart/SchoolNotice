package pers.catigeart.notice.mapper;

import org.springframework.stereotype.Repository;
import pers.catigeart.notice.entity.Org;
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
public interface OrgMapper extends BaseMapper<Org> {
    List<Org> findByUser(User user);

}
