package pers.catigeart.notice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import pers.catigeart.notice.dto.ReplyDTO;
import pers.catigeart.notice.entity.Klass;
import pers.catigeart.notice.entity.Notice;
import pers.catigeart.notice.entity.Reply;
import pers.catigeart.notice.entity.User;
import pers.catigeart.notice.mapper.KlassMapper;
import pers.catigeart.notice.mapper.ReplyMapper;
import pers.catigeart.notice.mapper.UserMapper;
import pers.catigeart.notice.service.ReplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.catigeart.notice.util.RuleUtil;

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
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, Reply> implements ReplyService {
    @Autowired
    private ReplyMapper replyMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private KlassMapper klassMapper;

    @Override
    public List<Reply> findByNotice(Notice notice) {
        Map<String, Object> params = new HashMap<>();
        params.put("notice_id", notice.getId());
        return replyMapper.selectByMap(params);
    }

    @Override
    public List<Reply> findByNoticeWithLimit(Notice notice, int groupId) {
        boolean isKlass = RuleUtil.isKlass(groupId);
        if (!isKlass) {
            return findByNotice(notice);
        } else {
            return replyMapper.findByKlassIdAndNoticeId(groupId, notice.getId());
        }
    }

    @Override
    public ReplyDTO reply2ReplyDTO(Reply reply) {
        User user = userMapper.selectById(reply.getUsername());
        Klass klass = klassMapper.selectById(user.getKlassId());

        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setId(reply.getId());
        replyDTO.setUsername(reply.getUsername());
        replyDTO.setName(user.getUName());
        replyDTO.setKlassName(klass.getKlassName());
        replyDTO.setSex(user.getSex());
        replyDTO.setContent(reply.getContent());

        return replyDTO;
    }
}
