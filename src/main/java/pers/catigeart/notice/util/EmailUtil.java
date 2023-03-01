package pers.catigeart.notice.util;

import pers.catigeart.notice.dto.NoticeDTO;
import pers.catigeart.notice.model.EmailModel;
import pers.catigeart.notice.vo.NoticeVO;

import java.util.Date;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author Catigeart
 * GitHub: https://github.com/Catigeart
 * Time: 2022/5/22 4:59
 */
public class EmailUtil {
    private static final String myEmailAccount = "catigeart@qq.com";
    private static final String myEmailPassword = "bvfjemolztnlfbia";
    private static final String myEmailSMTPHost = "smtp.qq.com";
    private static final String receiveMailAccount = "8102180511@csu.edu.cn";



    public static void sendEmail(EmailModel emailModel) throws Exception {
        String personal = emailModel.getPersonal();
        String subject = emailModel.getSubject();
        String content = emailModel.getContent();

        Properties props = new Properties();                    //参数配置
        props.setProperty("mail.transport.protocol", "smtp");   //使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost);   //发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            //需要请求认证

        //2.根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getInstance(props);
        //session.setDebug(true);                                 //设置为debug模式,可以查看详细的发送log

        //3.创建一封邮件
        MimeMessage message = createMimeMessage(personal, subject, content, session);

        //4.根据 Session获取邮件传输对象
        Transport transport = session.getTransport();

        transport.connect(myEmailAccount, myEmailPassword);
        //6.发送邮件,发到所有的收件地址,message.getAllRecipients()获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());
        System.out.println("邮件发送成功");
        // 7. 关闭连接
        transport.close();
    }

    private static MimeMessage createMimeMessage(String personal, String subject, String content, Session session) throws Exception {
        // 1.创建一封邮件
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(EmailUtil.myEmailAccount, personal, "UTF-8"));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(EmailUtil.receiveMailAccount, "XX用户", "UTF-8"));
        message.setSubject(subject, "UTF-8");
        message.setContent(content, "text/html;charset=UTF-8");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }

    public static EmailModel genNoticeEmail(boolean isKlass, NoticeDTO noticeDTO) {
        String personal = isKlass ? noticeDTO.getKlassRoleName() : noticeDTO.getOrgRole().getRoleName();
        String subject = noticeDTO.getNoticeType() + "-" + noticeDTO.getNoticeName();
        String content = "内容：" + noticeDTO.getContent() + "\n截止时间：" + noticeDTO.getEndTime().toString();
        EmailModel emailModel = new EmailModel();
        emailModel.setPersonal(personal);
        emailModel.setSubject(subject);
        emailModel.setContent(content);
        return emailModel;
    }

    public static EmailModel genNoticeEmail(String roleName, NoticeVO noticeVO) {
        String personal = roleName;
        String subject = noticeVO.getNoticeType() + "-" + noticeVO.getNoticeName();
        String content = "内容：" + noticeVO.getContent() + "\n截止时间：" + noticeVO.getEndTime().toString();
        EmailModel emailModel = new EmailModel();
        emailModel.setPersonal(personal);
        emailModel.setSubject(subject);
        emailModel.setContent(content);
        return emailModel;
    }
}
