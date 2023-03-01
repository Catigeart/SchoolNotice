package pers.catigeart.notice.mapper;

import org.springframework.stereotype.Repository;
import pers.catigeart.notice.entity.Klass;
import pers.catigeart.notice.entity.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import pers.catigeart.notice.entity.Org;

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
public interface NoticeMapper extends BaseMapper<Notice> {
    List<Notice> findByOrg(Org org);

    List<Notice> findByKlass(Klass klass);

    int saveAndGetId(Notice notice);
}
