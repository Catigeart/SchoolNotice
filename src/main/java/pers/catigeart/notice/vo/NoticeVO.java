package pers.catigeart.notice.vo;

import lombok.Data;
import pers.catigeart.notice.dto.KlassDTO;
import pers.catigeart.notice.dto.RoleDTO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Catigeart
 * GitHub: https://github.com/Catigeart
 * Time: 2022/5/19 18:38
 */
@Data
public class NoticeVO {
    private Integer groupId;
    private String noticeName;
    private String noticeType;
    private String content;
    private String klassRole;
    private Integer roleId;
    private LocalDateTime endTime;
    private List<KlassDTO> checkedKlassList;
    private Boolean isNeedReply;
    private Boolean isSendEmail;
    private Boolean isSendWechat;
}
