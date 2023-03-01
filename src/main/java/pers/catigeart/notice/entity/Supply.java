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
@TableName("supply")
public class Supply implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("notice_id")
    private Integer noticeId;

    @TableField("role_id")
    private Integer roleId;

    @TableField("is_klass")
    private Integer isOnlyKlass;

    @TableField("begin_time")
    private LocalDateTime beginTime;

    @TableField("content")
    private String content;


}
