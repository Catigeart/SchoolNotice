package pers.catigeart.notice.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pers.catigeart.notice.entity.Reply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
public interface ReplyMapper extends BaseMapper<Reply> {
    List<Reply> findByKlassIdAndNoticeId(@Param("klassId") int klassId, @Param("noticeId") int noticeId);
}
