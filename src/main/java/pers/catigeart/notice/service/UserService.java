package pers.catigeart.notice.service;

import pers.catigeart.notice.entity.Klass;
import pers.catigeart.notice.entity.Org;
import pers.catigeart.notice.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Catigeart
 * @since 2022-05-18
 */
public interface UserService extends IService<User> {
    List<User> findByOrg(Org org);

    List<User> findByKlass(Klass klass);

    List<User> findWithRoleByKlass(Klass klass);

    List<User> findByName(String name);
}
