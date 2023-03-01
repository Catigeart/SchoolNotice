<template>
  <h3>用户管理模块</h3>
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
        <el-button @click="initByGroup()" type="primary">按组查询</el-button>
      </el-form-item>
    </el-form>
  </div>

  <div>
    用户列表
    <hr />
    <el-table :data="memberList" stripe style="width: 100%">
      <el-table-column prop="username" label="用户编号" width="150" />
      <el-table-column prop="name" label="姓名" width="150" />
      <el-table-column prop="sex" label="性别" width="100" />
      <!--<el-table-column prop="job" label="身份" width="150" />-->
      <el-table-column prop="roles" label="身份/角色" />
      <!---------------操作栏-------------->
      <el-table-column label="操作">
        <template #default="scope">
          <!-------------授予角色对话窗-------------->
          <el-button
            size="small"
            v-if="memberManageSeen"
            @click="showGrantUserDialog(scope.$index)"
            >授予角色</el-button
          >
          <el-dialog
            v-model="grantUserDialogSeen[scope.$index]"
            title="授予角色"
            append-to-body
          >
            <el-table
              :data="roleList"
              highlight-current-row
              @current-change="changeCurrentGrantRole"
            >
              <el-table-column prop="roleName" label="角色名称" width="150" />
            </el-table>
            <div style="margin-top: 20px">
              <el-button @click="hideGrantUserDialog(scope.$index)"
                >取消</el-button
              >
              <el-button
                type="primary"
                @click="confirmGrantUser(scope.$index, scope.row)"
                >确认</el-button
              >
            </div>
          </el-dialog>

          <!-------------收回角色对话窗-------------->
          <el-button
            size="small"
            v-if="memberManageSeen"
            @click="showRevokeUserDialog(scope.$index, scope.row)"
            >收回角色</el-button
          >
          <el-dialog
            v-model="revokeUserDialogSeen[scope.$index]"
            title="收回角色"
            append-to-body
          >
            <el-table
              :data="ownRoleList"
              highlight-current-row
              @current-change="changeCurrentRevokeRole"
            >
              <el-table-column prop="name" label="角色名称" width="150" />
            </el-table>
            <div style="margin-top: 20px">
              <el-button @click="hideRevokeUserDialog(scope.$index)"
                >取消</el-button
              >
              <el-button
                type="primary"
                @click="confirmRevokeUser(scope.$index, scope.row)"
                >确认</el-button
              >
            </div>
          </el-dialog>
          <!----------------删除成员按钮----------------->
          <el-button
            size="small"
            type="danger"
            @click="confirmDeleteUser(scope.row)"
            v-if="modifyManageSeen"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!----------------邀请成员------------------------->
    <el-button v-if="modifyManageSeen" @click="showInviteMemberDialog()"
      >邀请成员</el-button
    >

    <el-dialog v-model="inviteMemberDialogSeen" title="邀请成员" append-to-body>
      邀请成员：
      <hr />
      用户姓名：<el-input v-model="inviteMemberNameInput" />
      <el-button @click="findUserByname()">按姓名搜索</el-button>
      <el-table :data="inviteMemberSearchUserList" stripe style="width: 100%">
        <el-table-column prop="username" label="用户编号" width="150" />
        <el-table-column prop="name" label="姓名" width="150" />
        <el-table-column prop="sex" label="性别" width="100" />
        <!--<el-table-column prop="job" label="身份" width="150" />-->
        <el-table-column prop="roles" label="身份/角色" />
      </el-table>
      按用户编号邀请：<el-input v-model="inviteMemberUsernameInput"></el-input>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="hideInviteMemberDialog()">取消</el-button>
          <el-button type="primary" @click="confirmInviteMemberDialog()"
            >确认</el-button
          >
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import { ref, reactive, Ref } from "vue";
import {
  GetGroupListAPI,
  GetMemberByOrgAPI,
  GetRoleByOrgAPI,
  PostGrantUserRoleAPI,
  PostOwnRoleAPI,
  GetRevokeAPI,
  PostDeleteAllUserOrgRoleAPI,
  GetUserByNameAPI,
  GetAddInviteMsgAPI,
  GetIsSuperRoleAPI,
} from "../request/api";

interface User {
  // 对应后端的MemberDTO
  username: string;
  name: string;
  sex: string;
  roles: string; // 后端强转的字符串，蜜汁操作
}

interface Role {
  id: string;
  name: string;
}

const groupForm = reactive({
  // 组织选择表单
  id: "",
});
const groupList = ref([]); // 选择组织下拉框
GetGroupListAPI().then((res) => {
  // 获取选择组织下拉框
  groupList.value = res.data.data;
  console.log(res);
});

const memberList = ref([]);
const roleList = ref([]); // 角色表格

const grantUserDialogSeen = ref([]);
const revokeUserDialogSeen = ref([]);

const memberManageSeen = ref(); // 如果是班级（或权限不足的管理组管理员）将被隐藏
const modifyManageSeen = ref();

async function initByGroup() {
  // 点击查询，按所选组织查找表格
  GetMemberByOrgAPI("", groupForm.id).then((res) => {
    memberList.value = res.data.data;
    console.log(res);
  });
  GetRoleByOrgAPI("", groupForm.id).then((res) => {
    roleList.value = res.data.data;
    console.log(res);
  });
  grantUserDialogSeen.value = Array(memberList.value.length).fill(false);
  revokeUserDialogSeen.value = Array(memberList.value.length).fill(false);

  // 如果是超级管理员，则允许进行进一步的修改
  await GetIsSuperRoleAPI({ orgId: groupForm.id }).then((res) => {
    console.log(res);
    if (res.data.data === true) {
      memberManageSeen.value = true;
      modifyManageSeen.value = true;
    } else {
      memberManageSeen.value = false;
      modifyManageSeen.value = false;
    }
  });

  if (parseInt(groupForm.id) > 10000) {
    modifyManageSeen.value = false; // 如果是班级，就ban掉成员管理（以及修改角色）
  } else {
    // memberManageSeen.value = true;
  }
}

/******************授予角色对话窗********************* */
function showGrantUserDialog(index: number) {
  // 打开弹窗
  grantUserDialogSeen.value[index] = true;
}

const grantUserCurrentRoleRow: Ref<Role> = ref(); // 当前选定的角色
const changeCurrentGrantRole = (val) => {
  // 改变当前选定的角色
  grantUserCurrentRoleRow.value = val;
  console.log(grantUserCurrentRoleRow);
};

function hideGrantUserDialog(index: number) {
  // 关闭弹窗
  grantUserDialogSeen.value[index] = false;
}

async function confirmGrantUser(index: number, row: User) {
  // 确认授予角色
  await PostGrantUserRoleAPI({
    member: row,
    role: grantUserCurrentRoleRow.value,
    groupId: groupForm.id,
  }).then((res) => {
    console.log(grantUserCurrentRoleRow);
    console.log(res);
  });
  alert("角色授予成功！")
  grantUserDialogSeen.value[index] = false;  
  initByGroup();
}
/********************收回角色对话窗****************** */
const ownRoleList = ref([]);
async function showRevokeUserDialog(index: number, row: User) {
  await PostOwnRoleAPI({
    groupId: groupForm.id,
    member: row,
  }).then((res) => {
    ownRoleList.value = res.data.data;
    console.log(res);
  });
  revokeUserDialogSeen.value[index] = true;
}

const revokeUserCurrentRow: Ref<Role> = ref();
const changeCurrentRevokeRole = (val) => {
  revokeUserCurrentRow.value = val;
};

function hideRevokeUserDialog(index: number) {
  revokeUserDialogSeen.value[index] = false;
}

async function confirmRevokeUser(index: number, row: User) {
  if (parseInt(groupForm.id) < 10000) {
    await GetRevokeAPI({
      username: row.username,
      groupId: groupForm.id,
      roleId: revokeUserCurrentRow.value.id,
    });
  } else {
    await GetRevokeAPI({
      username: row.username,
      groupId: groupForm.id,
      roleId: revokeUserCurrentRow.value.id,
    });
  }
  alert("角色收回成功！")
  revokeUserDialogSeen.value[index] = false;
  initByGroup();
}
/******************删除成员*******************/
async function confirmDeleteUser(row: User) {
  // TODO: 当前阶段相当于删除该成员在组织中的全部角色
  // 其实可以强制给所有角色赋一个basic身份（超级管理员的问题也可以用这种手段解决）
  // 建议把这个一并弄了。（指新增成员的用例）
  await PostDeleteAllUserOrgRoleAPI(row).then((res) => {
    console.log(res);
  });
  alert("成员删除成功！")
  initByGroup(); // 强制刷新
}
/*********************邀请成员******************** */
const inviteMemberDialogSeen = ref(false);

const inviteMemberNameInput = ref("");
const inviteMemberUsernameInput = ref("");

const inviteMemberSearchUserList: Ref<User> = ref();

function showInviteMemberDialog() {
  inviteMemberDialogSeen.value = true;
  console.log(inviteMemberDialogSeen.value);
}

function findUserByname() {
  GetUserByNameAPI({ name: inviteMemberNameInput.value }).then((res) => {
    inviteMemberSearchUserList.value = res.data.data;
    console.log(res);
  });
}

function hideInviteMemberDialog() {
  inviteMemberDialogSeen.value = false;
}

function confirmInviteMemberDialog() {
  GetAddInviteMsgAPI({}, groupForm.id, inviteMemberUsernameInput.value).then(
    (res) => {
      console.log(res);
    }
  );
  alert("邀请消息已发送！")
  inviteMemberDialogSeen.value = false;
}
</script>