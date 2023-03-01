package pers.catigeart.notice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import pers.catigeart.notice.dto.SupplyDTO;
import pers.catigeart.notice.entity.*;
import pers.catigeart.notice.mapper.*;
import pers.catigeart.notice.service.SupplyService;
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
public class SupplyServiceImpl extends ServiceImpl<SupplyMapper, Supply> implements SupplyService {
    @Autowired
    SupplyMapper supplyMapper;

    @Autowired
    KlassMapper klassMapper;

    @Autowired
    OrgMapper orgMapper;

    @Autowired
    OrgRoleMapper orgRoleMapper;

    @Autowired
    UserKlassRoleMapper userKlassRoleMapper;

    @Override
    public List<Supply> findByNotice(Notice notice) {
        Map<String, Object> params = new HashMap<>();
        params.put("notice_id", notice.getId());
        return supplyMapper.selectByMap(params);
    }

    @Override
    public List<SupplyDTO> findByNoticeWithLimit(Notice notice, int belongingId) {
        List<Supply> supplyList = findByNotice(notice);
        List<SupplyDTO> supplyDTOList = new ArrayList<>();
        for (Supply supply : supplyList) {
            SupplyDTO supplyDTO = new SupplyDTO();

            if (supply.getIsOnlyKlass() == 1) { // 如果是一个班级通知
                Klass klass = klassMapper.findByUserKlassRoleId(supply.getRoleId()); // 按角色追溯通知班级
                if (belongingId > 10000 && klass.getId() != belongingId) { // 如果当前是班级且不是同一个班级
                    continue;
                }
                // 角色和组织与班级数据同步
                supplyDTO.setBelongingId(klass.getId());
                supplyDTO.setBelongingName(klass.getKlassName());
                supplyDTO.setRoleId(supply.getRoleId());
                UserKlassRole userKlassRole = userKlassRoleMapper.selectById(supply.getRoleId());
                supplyDTO.setRoleName(userKlassRole.getKlassRoleName());
            } else { // 如果是一个通知组通知
                OrgRole orgRole = orgRoleMapper.selectById(supply.getRoleId());
                Org org = orgMapper.selectById(orgRole.getOrgId());
                supplyDTO.setBelongingId(org.getId());
                supplyDTO.setBelongingName(org.getOrgName());
                supplyDTO.setRoleId(supply.getRoleId());
                supplyDTO.setRoleName(orgRole.getRoleName());
            }
            supplyDTO.setId(supply.getId());
            supplyDTO.setContent(supply.getContent());

            supplyDTOList.add(supplyDTO);
        }

        return supplyDTOList;
    }
}
