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
@TableName("klass")
public class Klass implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Integer id;

    @TableField("sch_name")
    private String schName;

    @TableField("dept_name")
    private String deptName;

    @TableField("major_name")
    private String majorName;

    @TableField("grade")
    private Integer grade;

    @TableField("klass_name")
    private String klassName;


}
