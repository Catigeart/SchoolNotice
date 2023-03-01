package pers.catigeart.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import pers.catigeart.notice.dto.NoticeDTO;
import pers.catigeart.notice.entity.OrgRole;
import pers.catigeart.notice.mapper.NoticeMapper;
import pers.catigeart.notice.model.EmailModel;
import pers.catigeart.notice.util.EmailUtil;

import java.time.LocalDateTime;

/**
 * @author Catigeart
 * GitHub: https://github.com/Catigeart
 * Time: 2022/5/23 17:18
 */
public class EmailTest {

    public static void main(String[] args) throws Exception {
        NoticeDTO noticeDTO = new NoticeDTO();
        noticeDTO.setNoticeName("校园通知测试");
        noticeDTO.setId(114514);
        noticeDTO.setNoticeType("测试");
        noticeDTO.setIsNeedReply(false);
        noticeDTO.setBeginTime(LocalDateTime.now());
        noticeDTO.setEndTime(LocalDateTime.now());
        noticeDTO.setContent("邮箱通知测试");

        OrgRole orgRole = new OrgRole();
        orgRole.setRoleName("通知者");

        noticeDTO.setOrgRole(orgRole);

        EmailModel emailModel = EmailUtil.genNoticeEmail(false, noticeDTO);
        EmailUtil.sendEmail(emailModel);
    }
}
