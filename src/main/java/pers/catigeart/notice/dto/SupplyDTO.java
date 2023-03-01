package pers.catigeart.notice.dto;

import lombok.Data;

/**
 * @author Catigeart
 * GitHub: https://github.com/Catigeart
 * Time: 2022/5/20 18:57
 */
@Data
public class SupplyDTO {
    private Integer id;
    private Integer belongingId;
    private String belongingName;
    private Integer roleId;
    private String roleName;
    private String content;
}
