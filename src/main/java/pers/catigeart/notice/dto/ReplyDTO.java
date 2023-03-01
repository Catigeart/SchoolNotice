package pers.catigeart.notice.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Catigeart
 * GitHub: https://github.com/Catigeart
 * Time: 2022/5/20 15:06
 */
@Data
public class ReplyDTO {
    private Integer id;

    private Integer noticeId;

    private String username;

    private String name;

    private String sex;

    private String klassName;

    private String content;
}
