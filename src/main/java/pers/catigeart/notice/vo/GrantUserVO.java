package pers.catigeart.notice.vo;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;
import pers.catigeart.notice.dto.MemberDTO;
import pers.catigeart.notice.dto.RoleDTO;

/**
 * @author Catigeart
 * GitHub: https://github.com/Catigeart
 * Time: 2022/5/21 16:01
 */
@Data
public class GrantUserVO {
    private MemberDTO member;
    private RoleDTO role;
    private String groupId;
}
