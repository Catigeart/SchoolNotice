package pers.catigeart.notice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import pers.catigeart.notice.entity.Klass;
import pers.catigeart.notice.entity.Notice;
import pers.catigeart.notice.entity.Org;
import pers.catigeart.notice.entity.OrgRole;
import pers.catigeart.notice.mapper.NoticeMapper;
import pers.catigeart.notice.mapper.OrgRoleMapper;
import pers.catigeart.notice.service.NoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Autowired
    NoticeMapper noticeMapper;

    @Override
    public List<Notice> findByOrg(Org org) {
        return noticeMapper.findByOrg(org);
    }

    @Override
    public List<Notice> findByKlass(Klass klass) {
        return noticeMapper.findByKlass(klass);
    }

    @Override
    public Integer saveAndGetId(Notice notice) {
        return noticeMapper.saveAndGetId(notice);
    }
}
