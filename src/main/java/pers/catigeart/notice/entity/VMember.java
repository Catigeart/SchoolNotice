package pers.catigeart.notice.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author Catigeart
 * @since 2022-05-18
 */
@Getter
@Setter
@TableName("v_member")
public class VMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("username")
    private String username;

    @TableField("u_name")
    private String uName;

    @TableField("sex")
    private String sex;

    @TableField("role_name")
    private String roleName;


}
