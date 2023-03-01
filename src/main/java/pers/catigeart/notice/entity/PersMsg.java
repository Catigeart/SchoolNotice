package pers.catigeart.notice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Catigeart
 * @since 2022-05-21
 */
@Getter
@Setter
@TableName("pers_msg")
public class PersMsg implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("msg_name")
    private String msgName;

    @TableField("msg_type_id")
    private Integer msgTypeId;

    @TableField("is_one_send")
    private Integer isOneSend;

    @TableField("send_group_id")
    private Integer sendGroupId;

    @TableField("send_role_id")
    private Integer sendRoleId;

    @TableField("send_username")
    private String sendUsername;

    @TableField("is_one_receive")
    private Integer isOneReceive;

    @TableField("receive_group_id")
    private Integer receiveGroupId;

    @TableField("receive_role_id")
    private Integer receiveRoleId;

    @TableField("receive_username")
    private String receiveUsername;

    @TableField("content")
    private String content;

    @TableField("operation_id")
    private Integer operationId;

    @TableField("is_done")
    private Integer isDone;

}
