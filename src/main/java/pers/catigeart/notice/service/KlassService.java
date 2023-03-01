package pers.catigeart.notice.service;

import pers.catigeart.notice.dto.KlassDTO;
import pers.catigeart.notice.entity.Klass;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.catigeart.notice.model.AllGroupModel;
import pers.catigeart.notice.model.AllRoleModel;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Catigeart
 * @since 2022-05-18
 */
public interface KlassService extends IService<Klass> {
    KlassDTO klass2KlassDTO(Klass klass);

    List<KlassDTO> findKlassDTOBySch(String sch);

    List<KlassDTO> findKlassDTOByDept(String dept);

    List<KlassDTO> findKlassDTOByMajor(String major);

    List<KlassDTO> findKlassDTOByGrade(String grade);

    AllGroupModel genAllGroupModel(Klass klass);

    List<AllGroupModel> genAllGroupModel(List<Klass> klassList);

    Klass findByKlassName(String klassName);
}
