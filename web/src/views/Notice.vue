<template>
  <!--- 组织选择 --->
  <h3>通知管理模块</h3>
  <div style="text-align: left">
    <el-form :model="groupForm" :inline="true" label-width="120px">
      <el-form-item>
        <el-select v-model="groupForm.id" placeholder="请选择要查询的组织">
          <el-option
            v-for="group in groupList"
            :key="group.id"
            :label="group.name"
            :value="group.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="findByGroup()" type="primary">按组查询</el-button>
      </el-form-item>
    </el-form>
  </div>
  <!-----------------通知表格---------------------->
  <el-table :data="noticeList" stripe style="width: 100%">
    <el-table-column prop="id" label="通知编号" width="150" />
    <el-table-column prop="noticeName" label="通知标题" width="150" />
    <el-table-column prop="noticeType" label="通知类型" width="150" />
    <!--<el-table-column prop="org" label="通知来源" width="150" />-->
    <el-table-column prop="orgRole.roleName" label="通知者" width="150" />
    <el-table-column prop="klassRoleName" label="班委" width="150" />
    <el-table-column prop="beginTime" label="开始时间" width="150" :formatter="timeFormatter"/> 
    <el-table-column prop="endTime" label="结束时间" width="150" :formatter="timeFormatter"/> 
    <!-- 需班干部确认、需同学确认、需同学回复 -->
    <el-table-column prop="isNeedReply" label="是否需要回复" width="150">
    <template #default="scope">
      {{ scope.row.isNeedReply === true ? "是" : "否" }}
    </template>
    </el-table-column>
    <el-table-column label="操作">
      <template #default="scope">
        <!------------查看通知详情-------------->
        <el-button
          size="small"
          @click="showNoticeDetailDialog(scope.$index, scope.row)"
          >详情</el-button
        >
        <el-dialog
          v-model="noticeDetailSeen[scope.$index]"
          title="通知详情"
          append-to-body
        >
          通知编号：{{ scope.row.id }}
          <hr />
          通知标题：{{ scope.row.noticeName }}
          <hr />
          通知内容：{{ scope.row.content }}
          <hr />
          <!--
            管理员信息（仅管理员可见）：{{ notice.adminContent }}
            <hr />
            通知来源：{{ notice.org }}
            <hr />-->
          通知者：{{ scope.row.orgRole.roleName }}
          <hr />
          对接班委：{{ scope.row.klassRoleName }}
          <hr />
          开始时间：{{ formatDate(scope.row.beginTime) }}
          <hr />
          结束时间：{{ formatDate(scope.row.endTime) }}
          <hr />
          <!----------补充通知展示---------->
          补充通知：
          <hr />

          <el-table :data="scope.row.supplyList" stripe style="width: 100%">
            <el-table-column
              prop="belongingName"
              label="所属组织"
              width="150"
            />
            <el-table-column prop="roleName" label="补充者" width="150" />
            <el-table-column prop="content" label="补充内容" />
          </el-table>

          <!-------------提交回复---------------->
          <el-form v-if="addReplySeen[scope.$index]">
            回复通知：
            <hr />
            <el-form-item>
              <el-input
                v-model="addReplyForm.content"
                :rows="2"
                type="textarea"
                placeholder="Please input"
            /></el-form-item>
            <el-button @click="addReply(scope.row)">提交</el-button>
          </el-form>

          <template>
            <span class="dialog-footer">
              <el-button @click="noticeDetailSeen[scope.$index] = false"
                >取消</el-button
              >
              <el-button type="primary" @click="confirmNoticeDetail()"
                >确认</el-button
              >
            </span>
          </template>
        </el-dialog>

        <!---------------管理员对通知进行管理------------------->
        <el-button
          size="small"
          v-if="noticeMngBtnSeen[scope.$index]"
          @click="showNoticeMngDialog(scope.$index, scope.row)"
          >管理</el-button
        >
        <el-dialog v-model="noticeMngDialogSeen[scope.$index]" append-to-body>
          回复详情：
          <el-table :data="scope.row.replyList" stripe style="width: 100%">
            <el-table-column prop="username" label="用户编号" width="150" />
            <el-table-column prop="name" label="姓名" width="150" />
            <el-table-column prop="klassName" label="班级" width="150" />
            <el-table-column prop="sex" label="性别" width="150" />
            <el-table-column prop="content" label="内容" width="150" />
          </el-table>

          <!-------------补充通知---------------->
          <el-form>
            补充通知：
            <hr />
            <el-form-item>
              <el-input
                v-model="addSupplyForm.content"
                :rows="2"
                type="textarea"
                placeholder="Please input"
            /></el-form-item>
            <el-button @click="addSupply(scope.$index, scope.row)"
              >提交</el-button
            >
          </el-form>
        </el-dialog>
      </template>
    </el-table-column>
  </el-table>
  <!----------------发布通知-------------------->
  <el-button type="primary" @click="showAddNoticeDialog()">发布通知</el-button>
  <el-dialog v-model="addNoticeDialogSeen" title="发布通知" append-to-body>
    <el-form :model="addNoticeForm">
      <el-form-item>
        <el-input v-model="addNoticeForm.groupId" v-show="false"
      /></el-form-item>
      <el-form-item label="通知名称">
        <el-input v-model="addNoticeForm.noticeName" />
      </el-form-item>
      <el-form-item label="通知类型">
        <el-input v-model="addNoticeForm.noticeType" />
      </el-form-item>
      <el-form-item label="通知内容">
        <el-input v-model="addNoticeForm.content" type="textarea" />
      </el-form-item>
      <el-form-item label="您的角色">
        <el-select v-model="addNoticeForm.roleId">
          <el-option
            v-for="crole in currentRoleList"
            :key="crole.id"
            :label="crole.name"
            :value="crole.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="对接班委">
        <el-select
          v-if="parseInt(groupForm.id) < 10000"
          v-model="addNoticeForm.klassRole"
        >
          <el-option label="班长" value="班长" />
          <el-option label="副班长" value="副班长" />
          <el-option label="团支书" value="团支书" />
          <el-option label="心理委员" value="心理委员" />
          <el-option label="双创委员" value="双创委员" />
          <el-option label="学习委员" value="学习委员" />
          <el-option label="组宣委员" value="组宣委员" />
          <el-option label="文体委员" value="文体委员" />
          <el-option label="生活委员" value="生活委员" />
        </el-select>
      </el-form-item>
      <el-form-item label="截止时间">
        <el-date-picker v-model="addNoticeForm.endTime" type="datetime" />
      </el-form-item>
      <el-form-item label="是否需要回复">
        <el-switch v-model="addNoticeForm.isNeedReply" />
      </el-form-item>
      <!--WARNING: 通知组通知需要显式指定通知范围-->
      <el-form-item label="通知范围">
        <el-checkbox-group v-model="addNoticeForm.checkedKlassList">
          <el-checkbox
            v-for="klass in klassList"
            :key="klass.id"
            :label="klass"
            >{{ klass.name }}</el-checkbox
          >
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="是否发送电子邮件">
        <el-switch v-model="addNoticeForm.isSendEmail" />
      </el-form-item>
      <el-form-item label="是否发送到微信">
        <el-switch v-model="addNoticeForm.isSendWechat" />
      </el-form-item>
      <el-form-item>
        <el-button @click="addNoticeDialogSeen = false">取消</el-button>
        <el-button type="primary" @click="addNotice()">确认</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script lang="ts" setup>
import { reactive, Ref, ref } from "vue";
import {
  GetGroupListAPI,
  GetNoticeByGroupAPI,
  GetCurrentRolesAPI,
  PostAddNoticeAPI,
  GetRangeKlassAPI,
  GetCurrentUserAPI,
  PostReplyAPI,
  PostSupplyAPI,
  PostWeComRobot,
} from "../request/api";
import { timeFormatter, formatDate } from "../ts/util"

/********* 选择组织并展示表格 **********/
interface OrgRole {
  id: string;
  roleName: string;
  orgId: string;
}

interface Supply {
  id: string;
  belongingid: number;
  belongingName: string;
  roleId: number;
  roleName: string;
  content: string;
}

interface Reply {
  id: string;
  noticeId: string;
  username: string;
  name: string;
  sex: string;
  klassName: string;
  content: string;
}

interface Notice {
  id: string;
  noticeName: string;
  noticeType: string;
  content: string;
  orgRole: OrgRole;
  klassRoleName: string;
  beginTime: Date;
  endTime: Date;
  isNeedReply: boolean;
  supplyList: Supply[];
  replyList: Reply[];
}

const groupList = ref([
  {
    id: "",
    type: "",
    name: "",
  },
]);

GetGroupListAPI().then((res) => {
  groupList.value = res.data.data;
  console.log(groupList.value);
});

interface User {
  username: string;
  password: string;
  uName: string;
  sex: string;
  isStuRole: number;
  klassId: string;
}

let currentUser: User;
GetCurrentUserAPI().then((res) => {
  currentUser = res.data.data;
});

const noticeList: Ref<[Notice]> = ref();

const groupForm = reactive({
  id: "",
});

const noticeDetailSeen = ref([]); // 详情是否可视

const addSupplySeen = ref([]); // 是否有权限补充通知

const addReplySeen = ref([]); // 是否需要回复通知

const noticeMngBtnSeen = ref([]); // 是否拥有对该通知的管理权限

const noticeMngDialogSeen = ref([]); // 是否展示通知管理对话框

const currentRoleList = ref([
  {
    id: "",
    name: "",
  },
]);

function findCurrentRoles() { // TODO
  GetCurrentRolesAPI({ id: groupForm.id }).then((res) => {
    currentRoleList.value = res.data.data;
    console.log(currentRoleList.value);
  });
}

function findByGroup() {
  findCurrentRoles();
  GetNoticeByGroupAPI({
    id: groupForm.id,
  }).then((res) => {
    noticeList.value = res.data.data;
    noticeDetailSeen.value = Array(noticeList.value.length).fill(false);

    // 每个通知的管理员权限控制
    for (let i = 0; i < noticeList.value.length; i++) {
      for (let j = 0; j < currentRoleList.value.length; j++) {
        if (
          currentRoleList.value[j].id == noticeList.value[i].orgRole.id ||
          currentRoleList.value[j].name == noticeList.value[i].klassRoleName
        ) {
          noticeMngBtnSeen.value[i] = true;
          continue;
        }
      }
    }

    noticeMngDialogSeen.value = Array(noticeList.value.length).fill(false);

    ///////////////我超，逻辑都在前端做了
    ////////////先按组的编号对情况进行分类
    addReplySeen.value = Array(noticeList.value.length).fill(false);
    if (parseInt(groupForm.id) > 10000 && currentUser.isStuRole === 1) {
      for (let i = 0; i < noticeList.value.length; i++) {
        if (noticeList.value[i].isNeedReply === true) {
          addReplySeen.value[i] = true;
        }
      }
    } // 是班级且是学生，那么可以对内容通知内容进行回复

    console.log(noticeList.value);
  });
}

/************* 通知详情页面 ************* */

function showNoticeDetailDialog(index: number, row) {
  noticeDetailSeen.value[index] = true;
  console.log(noticeDetailSeen.value);
}

function confirmNoticeDetail() {}

/*******************发布通知！*******************/
const addNoticeForm = reactive({
  groupId: "",
  noticeName: "",
  noticeType: "",
  content: "",
  roleId: "",
  klassRole: "",
  endTime: Date.now(),
  checkedKlassList: [],
  isNeedReply: false,
  isSendWechat: true,
  isSendEmail: false,
});

const addNoticeDialogSeen = ref(false);

const klassList = ref([
  {
    id: "",
    name: "",
  },
]);

function getRangeKlass() {
  GetRangeKlassAPI({ id: groupForm.id }).then((res) => {
    klassList.value = res.data.data;
    console.log(klassList);
  });
}

function showAddNoticeDialog() {
  findCurrentRoles();
  /*
  if (parseInt(groupForm.id) < 10000) {
    getRangeKlass();
  }*/
  getRangeKlass();
  addNoticeForm.groupId = groupForm.id;
  addNoticeDialogSeen.value = true;
}

function hideAddNoticeDialog() {
  addNoticeDialogSeen.value = false;
}

async function addNotice() {
  console.log(addNoticeForm)
  await PostAddNoticeAPI(addNoticeForm).then((res) => {
    console.log(addNoticeForm);
  });
  if (addNoticeForm.isSendWechat === true) {
    PostWeComRobot({
      msgtype: "text",
      text: {
        content:
          "通知名称：" +
          addNoticeForm.noticeName +
          "\n通知类型：" +
          addNoticeForm.noticeType +
          "\n通知内容：" +
          addNoticeForm.content +
          "\n详情内容请登录校园通知系统查询。",
        mentioned_list: ["@all"],
        //"mentioned_mobile_list":["@all"]
      },
    }).then((res) => {
      console.log(res);
    });
  }
  alert("发布通知成功！")
  hideAddNoticeDialog();
}
/*************回复通知************* */
const addReplyForm = reactive({
  noticeId: "",
  content: "",
});

async function addReply(row: Notice) {
  addReplyForm.noticeId = row.id;
  await PostReplyAPI(addReplyForm).then((res) => {
    console.log(addReplyForm);
  });
  alert("发布回复成功！")
}

/**************通知管理***************** */
const addSupplyForm = reactive({
  groupId: "",
  noticeId: "",
  isKlass: false,
  orgRoleId: "",
  klassRoleName: "",
  content: "",
});
function showNoticeMngDialog(index: number, row: Notice) {
  noticeMngDialogSeen.value[index] = true;
}

async function addSupply(index: number, row: Notice) {
  addSupplyForm.groupId = groupForm.id;
  addSupplyForm.noticeId = row.id;
  if (parseInt(groupForm.id) > 10000) {
    addSupplyForm.isKlass = true;
    addSupplyForm.klassRoleName = row.klassRoleName;
  } else {
    addSupplyForm.isKlass = false;
    addSupplyForm.orgRoleId = row.orgRole.id;
  }
  await PostSupplyAPI(addSupplyForm).then((res) => {
    console.log(addSupplyForm);
  });
  alert("增加补充成功！")

  noticeMngDialogSeen.value[index] = false;
  findByGroup()
}

function hideNoticeMngDialog(index: number, row: Notice) {
  noticeMngDialogSeen.value[index] = false;
}

////////////////////返回某用户所拥有的角色//////////////////////

//////////////////////////////////////////////////////
</script>


<style scoped>
.el-button--text {
  margin-right: 15px;
}
.el-select {
  width: 300px;
}
.el-input {
  width: 300px;
}
.dialog-footer button:first-child {
  margin-right: 10px;
}
</style>