package pers.catigeart.notice.service;

import pers.catigeart.notice.entity.SuperRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Catigeart
 * @since 2022-05-22
 */
public interface SuperRoleService extends IService<SuperRole> {
    boolean isSuperRole(int roleId);
}
