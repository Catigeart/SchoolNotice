package pers.catigeart.notice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Catigeart
 * @since 2022-05-18
 */
@Getter
@Setter
@TableName("notice")
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("notice_name")
    private String noticeName;

    @TableField("notice_type")
    private String noticeType;

    @TableField("content")
    private String content;

    @TableField("org_role_id")
    private Integer orgRoleId;

    @TableField("klass_role_name")
    private String klassRoleName;

    @TableField("begin_time")
    private LocalDateTime beginTime;

    @TableField("end_time")
    private LocalDateTime endTime;

    @TableField("is_need_reply")
    private Boolean isNeedReply;
}
