package pers.catigeart.notice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import pers.catigeart.notice.dto.KlassDTO;
import pers.catigeart.notice.entity.Klass;
import pers.catigeart.notice.mapper.KlassMapper;
import pers.catigeart.notice.model.AllGroupModel;
import pers.catigeart.notice.model.AllRoleModel;
import pers.catigeart.notice.service.KlassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Catigeart
 * @since 2022-05-18
 */
@Service
public class KlassServiceImpl extends ServiceImpl<KlassMapper, Klass> implements KlassService {

    @Autowired
    KlassMapper klassMapper;

    @Override
    public KlassDTO klass2KlassDTO(Klass klass) {
        KlassDTO klassDTO = new KlassDTO();
        klassDTO.setId(klass.getId());
        klassDTO.setName(klass.getKlassName());
        return klassDTO;
    }

    @Override
    public List<KlassDTO> findKlassDTOBySch(String sch) {
        Map<String, Object> params = new HashMap<>();
        params.put("sch_name", sch);
        List<Klass> klassList = klassMapper.selectByMap(params);
        List<KlassDTO> klassDTOList = new ArrayList<>();
        for (Klass klass : klassList) {
            klassDTOList.add(klass2KlassDTO(klass));
        }
        return klassDTOList;
    }

    @Override
    public List<KlassDTO> findKlassDTOByDept(String dept) {
        Map<String, Object> params = new HashMap<>();
        params.put("dept_name", dept);
        List<Klass> klassList = klassMapper.selectByMap(params);
        List<KlassDTO> klassDTOList = new ArrayList<>();
        for (Klass klass : klassList) {
            klassDTOList.add(klass2KlassDTO(klass));
        }
        return klassDTOList;
    }

    @Override
    public List<KlassDTO> findKlassDTOByMajor(String major) {
        Map<String, Object> params = new HashMap<>();
        params.put("major_name", major);
        List<Klass> klassList = klassMapper.selectByMap(params);
        List<KlassDTO> klassDTOList = new ArrayList<>();
        for (Klass klass : klassList) {
            klassDTOList.add(klass2KlassDTO(klass));
        }
        return klassDTOList;
    }

    @Override
    public List<KlassDTO> findKlassDTOByGrade(String grade) {
        Map<String, Object> params = new HashMap<>();
        params.put("grade", grade);
        List<Klass> klassList = klassMapper.selectByMap(params);
        List<KlassDTO> klassDTOList = new ArrayList<>();
        for (Klass klass : klassList) {
            klassDTOList.add(klass2KlassDTO(klass));
        }
        return klassDTOList;
    }

    @Override
    public AllGroupModel genAllGroupModel(Klass klass) {
        AllGroupModel allGroupModel = new AllGroupModel();
        allGroupModel.setId(klass.getId());
        allGroupModel.setName(klass.getKlassName());
        allGroupModel.setKlass(true);
        return allGroupModel;
    }

    @Override
    public List<AllGroupModel> genAllGroupModel(List<Klass> klassList) {
        List<AllGroupModel> allGroupModelList = new ArrayList<>();
        for (Klass klass : klassList) {
            allGroupModelList.add(genAllGroupModel(klass));
        }
        return allGroupModelList;
    }

    @Override
    public Klass findByKlassName(String klassName) {
        Map<String, Object> params = new HashMap<>();
        params.put("klass_name", klassName);
        return klassMapper.selectByMap(params).get(0);
    }
}
