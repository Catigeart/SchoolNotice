package pers.catigeart.notice.vo;

import lombok.Data;
import pers.catigeart.notice.dto.MemberDTO;

/**
 * @author Catigeart
 * GitHub: https://github.com/Catigeart
 * Time: 2022/5/21 16:11
 */
@Data
public class OwnRoleVO {
    private String groupId;
    private MemberDTO member;
}
