package pers.catigeart.notice.controller;

import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import pers.catigeart.notice.entity.Reply;
import pers.catigeart.notice.service.ReplyService;
import pers.catigeart.notice.util.JwtUtil;
import pers.catigeart.notice.util.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Catigeart
 * @since 2022-05-18
 */
@RestController
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    ReplyService replyService;

    @PostMapping(value = "/add")
    public Result<Boolean> addReply(HttpServletRequest request, @RequestBody Reply reply) {
        String token = request.getHeader("token");
        String username = JwtUtil.getUserId(token);
        reply.setUsername(username);
        replyService.save(reply);

        return Result.success();
    }
}
