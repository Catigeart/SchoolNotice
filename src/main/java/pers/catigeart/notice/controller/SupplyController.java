package pers.catigeart.notice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.catigeart.notice.entity.Supply;
import pers.catigeart.notice.entity.UserKlassRole;
import pers.catigeart.notice.service.SupplyService;
import pers.catigeart.notice.service.UserKlassRoleService;
import pers.catigeart.notice.util.JwtUtil;
import pers.catigeart.notice.util.Result;
import pers.catigeart.notice.vo.SupplyVO;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Catigeart
 * @since 2022-05-18
 */
@RestController
@RequestMapping("/supply")
public class SupplyController {

    @Autowired
    UserKlassRoleService userKlassRoleService;

    @Autowired
    SupplyService supplyService;

    @PostMapping(value = "/add")
    public Result<Boolean> addSupply(HttpServletRequest request, @RequestBody SupplyVO supplyVO) {
        Supply supply = new Supply();
        supply.setNoticeId(supplyVO.getNoticeId());
        if (supplyVO.getGroupId() < 10000) {
            // 如果小于10000，就是自增的组织
            supply.setIsOnlyKlass(0);
            supply.setRoleId(supplyVO.getOrgRoleId());
        } else { // 如果大于10000，就是班级
            supply.setIsOnlyKlass(1);
            String token = request.getHeader("token");
            String username = JwtUtil.getUserId(token);
            String klassRoleName = supplyVO.getKlassRoleName();
            UserKlassRole userKlassRole =
                    userKlassRoleService.findByUsernameAndKlassRoleName(username, klassRoleName).get(0);
            supply.setRoleId(userKlassRole.getId());
        }
        supply.setContent(supplyVO.getContent());
        supply.setBeginTime(LocalDateTime.now());

        supplyService.save(supply);

        return Result.success();
    }

}
