package pers.catigeart.notice.vo;

import lombok.Data;

/**
 * @author Catigeart
 * GitHub: https://github.com/Catigeart
 * Time: 2022/5/20 18:25
 */
@Data
public class SupplyVO {
    private Integer groupId;
    private Integer noticeId;
    private Boolean isKlass;
    private Integer orgRoleId;
    private String klassRoleName;
    private String content;
}
