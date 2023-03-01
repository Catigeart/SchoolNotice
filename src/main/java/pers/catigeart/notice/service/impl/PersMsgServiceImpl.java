package pers.catigeart.notice.service.impl;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import pers.catigeart.notice.entity.*;
import pers.catigeart.notice.mapper.*;
import pers.catigeart.notice.model.AllGroupModel;
import pers.catigeart.notice.model.AllRoleModel;
import pers.catigeart.notice.model.PersonalMsgModel;
import pers.catigeart.notice.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.catigeart.notice.util.RuleUtil;

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
 * @since 2022-05-21
 */
@Service
public class PersMsgServiceImpl extends ServiceImpl<PersMsgMapper, PersMsg> implements PersMsgService {

    @Autowired
    OrgService orgService;

    @Autowired
    OrgRoleService orgRoleService;

    @Autowired
    KlassService klassService;

    @Autowired
    UserKlassRoleService userKlassRoleService;

    @Autowired
    MsgTypeMapper msgTypeMapper;

    @Autowired
    UserKlassRoleMapper userKlassRoleMapper;

    @Autowired
    KlassMapper klassMapper;

    @Autowired
    OrgMapper orgMapper;

    @Autowired
    OrgRoleMapper orgRoleMapper;

    @Autowired
    OperationMapper operationMapper;

    @Autowired
    PersMsgMapper persMsgMapper;

    @Override
    public PersonalMsgModel genModel(PersMsg persMsg) {
        PersonalMsgModel personalMsgModel = new PersonalMsgModel();
        personalMsgModel.setId(persMsg.getId());
        personalMsgModel.setName(persMsg.getMsgName());
        MsgType msgType = msgTypeMapper.selectById(persMsg.getMsgTypeId());
        personalMsgModel.setMsgType(msgType);
        personalMsgModel.setIsOneSend(persMsg.getIsOneSend() == 1);
        if (!personalMsgModel.getIsOneSend()) { // 如果是角色发送，则需要补充组与角色
            boolean isKlass = RuleUtil.isKlass(persMsg.getSendGroupId());
            AllGroupModel allGroupModel;
            AllRoleModel allRoleModel;
            if (isKlass) {
                // group
                Klass klass = klassMapper.selectById(persMsg.getSendGroupId());
                allGroupModel = klassService.genAllGroupModel(klass);
                // role
                UserKlassRole userKlassRole = userKlassRoleMapper.selectById(persMsg.getSendRoleId());
                allRoleModel = userKlassRoleService.genAllRoleModel(userKlassRole);
            } else {
                // group
                Org org = orgMapper.selectById(persMsg.getSendGroupId());
                allGroupModel = orgService.genAllGroupModel(org);
                // role
                OrgRole orgRole = orgRoleMapper.selectById(persMsg.getSendRoleId());
                allRoleModel = orgRoleService.genAllRoleModel(orgRole);
            }
            personalMsgModel.setSendAllGroup(allGroupModel);
            personalMsgModel.setSendAllRole(allRoleModel);
        } else {
            personalMsgModel.setSendUsername(persMsg.getSendUsername());
        }
        personalMsgModel.setIsOneReceive(persMsg.getIsOneReceive() == 1);
        if (!personalMsgModel.getIsOneReceive()) {
            boolean isKlass = RuleUtil.isKlass(persMsg.getReceiveGroupId());
            AllGroupModel allGroupModel;
            AllRoleModel allRoleModel;
            if (isKlass) {
                // group
                Klass klass = klassMapper.selectById(persMsg.getReceiveGroupId());
                allGroupModel = klassService.genAllGroupModel(klass);
                // role
                UserKlassRole userKlassRole = userKlassRoleMapper.selectById(persMsg.getReceiveRoleId());
                allRoleModel = userKlassRoleService.genAllRoleModel(userKlassRole);
            } else {
                // group
                Org org = orgMapper.selectById(persMsg.getReceiveGroupId());
                allGroupModel = orgService.genAllGroupModel(org);
                // role
                OrgRole orgRole = orgRoleMapper.selectById(persMsg.getReceiveRoleId());
                allRoleModel = orgRoleService.genAllRoleModel(orgRole);
            }
            personalMsgModel.setReceiveAllGroup(allGroupModel);
            personalMsgModel.setReceiveAllRole(allRoleModel);
        } else {
            personalMsgModel.setReceiveUsername(persMsg.getReceiveUsername());
        }

        personalMsgModel.setContent(persMsg.getContent());
        Operation operation = operationMapper.selectById(persMsg.getOperationId());
        personalMsgModel.setOperation(operation);
        personalMsgModel.setIsDone(persMsg.getIsDone() == 1);

        return personalMsgModel;
    }

    @Override
    public List<PersonalMsgModel> genModel(List<PersMsg> persMsgList) {
        List<PersonalMsgModel> personalMsgModelList = new ArrayList<>();
        for (PersMsg persMsg : persMsgList) {
            personalMsgModelList.add(genModel(persMsg));
        }
        return personalMsgModelList;
    }

    @Override
    public PersMsg initReplyMsg(PersMsg persMsg) {
        PersMsg newMsg = new PersMsg();
        if (persMsg.getIsOneSend() == 1) {
            newMsg.setIsOneReceive(1);
            newMsg.setReceiveUsername(persMsg.getSendUsername());
        } else {
            newMsg.setIsOneReceive(0);
            newMsg.setReceiveGroupId(persMsg.getSendGroupId());
            newMsg.setReceiveRoleId(persMsg.getSendRoleId());
        }
        if (persMsg.getIsOneReceive() == 1) {
            newMsg.setIsOneSend(1);
            newMsg.setSendUsername(persMsg.getReceiveUsername());
        } else {
            newMsg.setIsOneSend(0);
            newMsg.setSendGroupId(persMsg.getReceiveGroupId());
            newMsg.setSendRoleId(persMsg.getReceiveRoleId());
        }
        return newMsg;
    }

    @Override
    public int insert(PersMsg persMsg) {
        return persMsgMapper.insert(persMsg);
    }

    @Override
    public List<PersMsg> findByAllReceiver(List<UserKlassRole> userKlassRoleList,
                                           List<OrgRole> orgRoleList,
                                           User user) {
        List<PersMsg> persMsgList = new ArrayList<>();
        // 不要脸的人是无敌的
        for (UserKlassRole userKlassRole : userKlassRoleList) {
            Map<String, Object> params = new HashMap<>();
            params.put("receive_group_id", user.getKlassId());
            params.put("receive_role_id", userKlassRole.getId());
            List<PersMsg> list = persMsgMapper.selectByMap(params);
            persMsgList.addAll(list);
        }
        for (OrgRole orgRole : orgRoleList) {
            Map<String, Object> params = new HashMap<>();
            params.put("receive_group_id", orgRole.getOrgId());
            params.put("receive_role_id", orgRole.getId());
            List<PersMsg> list = persMsgMapper.selectByMap(params);
            persMsgList.addAll(list);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("receive_username", user.getUsername());
        List<PersMsg> list = persMsgMapper.selectByMap(params);
        persMsgList.addAll(list);

        return persMsgList;
    }
}
