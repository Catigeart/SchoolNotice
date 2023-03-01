package pers.catigeart.notice.service;

import pers.catigeart.notice.dto.ReplyDTO;
import pers.catigeart.notice.entity.Notice;
import pers.catigeart.notice.entity.Reply;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Catigeart
 * @since 2022-05-18
 */
public interface ReplyService extends IService<Reply> {
    List<Reply> findByNotice(Notice notice);

    ReplyDTO reply2ReplyDTO(Reply reply);

    List<Reply> findByNoticeWithLimit(Notice notice, int groupId);
}
