package pers.catigeart.notice.entity;

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
 * @since 2022-05-18
 */
@Getter
@Setter
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField("u_name")
    private String uName;

    @TableField("sex")
    private String sex;

    @TableField("is_stu_role")
    private Integer isStuRole;

    @TableField("klass_id")
    private Integer klassId;

}
