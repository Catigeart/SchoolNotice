package pers.catigeart.notice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import pers.catigeart.notice.entity.Org;
import pers.catigeart.notice.entity.User;
import pers.catigeart.notice.mapper.OrgMapper;
import pers.catigeart.notice.model.AllGroupModel;
import pers.catigeart.notice.service.OrgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Catigeart
 * @since 2022-05-18
 */
@Service
public class OrgServiceImpl extends ServiceImpl<OrgMapper, Org> implements OrgService {
    @Autowired
    OrgMapper orgMapper;

    @Override
    public List<Org> findByUser(User user) {
        return orgMapper.findByUser(user);
    }

    @Override
    public AllGroupModel genAllGroupModel(Org org) {
        AllGroupModel allGroupModel = new AllGroupModel();
        allGroupModel.setId(org.getId());
        allGroupModel.setName(org.getOrgName());
        allGroupModel.setKlass(false);
        return allGroupModel;
    }

    @Override
    public List<AllGroupModel> genAllGroupModel(List<Org> orgList) {
        List<AllGroupModel> allGroupModelList = new ArrayList<>();
        for (Org org : orgList) {
            allGroupModelList.add(genAllGroupModel(org));
        }
        return allGroupModelList;
    }
}
