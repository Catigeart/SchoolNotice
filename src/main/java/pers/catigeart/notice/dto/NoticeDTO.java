package pers.catigeart.notice.dto;

import lombok.Data;
import pers.catigeart.notice.entity.OrgRole;
import pers.catigeart.notice.entity.Reply;
import pers.catigeart.notice.entity.Supply;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Catigeart
 * GitHub: https://github.com/Catigeart
 * Time: 2022/5/19 15:04
 */
@Data
public class NoticeDTO {
    private Integer id;
    private String noticeName;
    private String noticeType;
    private String content;
    private OrgRole orgRole;
    private String klassRoleName;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private Boolean isNeedReply;
    private List<SupplyDTO> supplyList;
    private List<ReplyDTO> replyList;
}
