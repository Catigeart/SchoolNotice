package pers.catigeart.notice.model;

import lombok.Data;

/**
 * @author Catigeart
 * GitHub: https://github.com/Catigeart
 * Time: 2022/5/22 5:12
 */
@Data
public class EmailModel {
    private String personal;
    private String subject;
    private String content;
}
