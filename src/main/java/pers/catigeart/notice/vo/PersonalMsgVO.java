package pers.catigeart.notice.vo;

import lombok.Data;

/**
 * @author Catigeart
 * GitHub: https://github.com/Catigeart
 * Time: 2022/5/21 17:25
 */
@Data
public class PersonalMsgVO {
    private Integer id;

    private String msgName;

    private String type;

    private String typeText;

    private Integer sendGroupId;

    private String sendGroupName;

    private Integer sendRoleId;

    private String sendRoleName;

    private String receiveUsername;

    private String content;

    private Integer status; // -1=done,0=no_need,1=need_choice,2=need_confirm

    private String statusText;
}
