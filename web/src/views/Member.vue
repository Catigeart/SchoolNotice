<template>
  用户角色管理
  <!--- 组织选择 --->
  <el-form :model="groupForm" label-width="120px">
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
  <el-row :gutter="20">
    <!------------用户列表---------------->
    <el-col :span="18">
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
            <el-button size="small" @click="showGrantUserDialog(scope.$index)"
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
              v-if="memberManageSeen"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </el-col>
    <!----------------邀请成员------------------------->
    <el-button v-if="memberManageSeen" @click="showInviteMemberDialog()">邀请成员</el-button>

    <el-dialog v-model="inviteMemberDialogSeen"
              title="邀请成员"
              append-to-body>
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

    <!-------------------角色列表---------------------->
    <el-col :span="6">
      角色列表
      <hr />
      <el-table :data="roleList" stripe style="width: 100%">
        <el-table-column prop="roleName" label="角色名称" width="150" />
        <el-table-column label="操作">
          <template #default="scope">
            <!----------- 修改角色--------------->
            <el-button
              size="small"
              @click="showEditRoleDialog(scope.$index, scope.row)"
              v-if="memberManageSeen"
              >修改</el-button
            >
            <el-dialog
              v-model="editRoleDialogSeen[scope.$index]"
              title="修改角色"
              append-to-body
            >
              <el-form :model="editRoleForm">
                <el-form-item label="角色名称">
                  <el-input v-model="editRoleForm.name" autocomplete="off" />
                </el-form-item>
              </el-form>
              <template #footer>
                <span class="dialog-footer">
                  <el-button @click="hideEditRoleDialog(scope.$index)"
                    >取消</el-button
                  >
                  <el-button
                    type="primary"
                    @click="confirmEditRole(scope.$index)"
                    >确认</el-button
                  >
                </span>
              </template>
            </el-dialog>
            <!---删除角色--->
            <el-button
              size="small"
              type="danger"
              v-if="memberManageSeen"
              @click="confirmDeleteRole(scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <!----------- 新增角色--------------->
      <el-button v-if="memberManageSeen" @click="showAddRoleDialog()"
        >新增角色</el-button
      >
      <el-dialog v-model="addRoleDialogSeen" title="新增角色" append-to-body>
        角色名称：<br />
        <el-input v-model="addRoleName" autocomplete="off" />
        <div style="margin-top: 20px">
          <el-button @click="hideAddRoleDialog()">取消</el-button>
          <el-button type="primary" @click="confirmAddRole()">确认</el-button>
        </div>
      </el-dialog>
    </el-col>
  </el-row>
</template>

<script lang="ts" setup>
import { reactive, Ref, ref } from "vue";
import {
  GetGroupListAPI,
  GetMemberByOrgAPI,
  PostOwnRoleAPI,
  GetRoleByOrgAPI,
  GetAddRoleAPI,
  PostDeleteAllUserOrgRoleAPI,
  GetDeleteRoleAPI,
  PostDeleteUserOrgRoleAPI,
  PostEditRoleAPI,
  PostGrantUserRoleAPI,
  GetRevokeAPI,
  GetUserByNameAPI,
  GetAddInviteMsgAPI,
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

/****************按组织初始化数据******************/
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

const memberList = ref([]); // 成员表格
const roleList = ref([]); // 角色表格

// 弹窗可视化绑定
const grantUserDialogSeen = ref([]);
const revokeUserDialogSeen = ref([]);
const editRoleDialogSeen = ref([]);

const memberManageSeen = ref(); // 如果是班级（或权限不足的管理组管理员）将被隐藏

function initByGroup() {
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
  editRoleDialogSeen.value = Array(roleList.value.length).fill(false);
  if (parseInt(groupForm.id) > 10000) {
    memberManageSeen.value = false;
  } else {
    memberManageSeen.value = true; // TODO: 通知组管理员权限控制欠缺
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

function confirmGrantUser(index: number, row: User) {
  // 确认授予角色
  PostGrantUserRoleAPI({
    member: row,
    role: grantUserCurrentRoleRow.value,
    groupId: groupForm.id,
  }).then((res) => {
    console.log(grantUserCurrentRoleRow);
    console.log(res);
  });
  grantUserDialogSeen.value[index] = false;
}
/********************收回角色对话窗****************** */
const ownRoleList = ref([]);
function showRevokeUserDialog(index: number, row: User) {
  PostOwnRoleAPI({
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

function confirmRevokeUser(index: number, row: User) {
  if (parseInt(groupForm.id) < 10000) {
    GetRevokeAPI({
      username: row.username,
      groupId: groupForm.id,
      roleId: revokeUserCurrentRow.value.id,
    });
  } else {
    // TODO
  }
  revokeUserDialogSeen.value[index] = false;
}
/******************删除成员*******************/
function confirmDeleteUser(row: User) {
  // TODO: 当前阶段相当于删除该成员在组织中的全部角色
  // 其实可以强制给所有角色赋一个basic身份（超级管理员的问题也可以用这种手段解决）
  // 建议把这个一并弄了。（指新增成员的用例）
  PostDeleteAllUserOrgRoleAPI(row).then((res) => {
    console.log(res);
  });
  initByGroup(); // 强制刷新
}
/*********************邀请成员******************** */
const inviteMemberDialogSeen = ref(false);

const inviteMemberNameInput = ref("");
const inviteMemberUsernameInput = ref("");

const inviteMemberSearchUserList: Ref<User> = ref();

function showInviteMemberDialog() {
  inviteMemberDialogSeen.value = true;
  console.log(inviteMemberDialogSeen.value)
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
    GetAddInviteMsgAPI({}, groupForm.id, inviteMemberUsernameInput.value).then((res) => {
        console.log(res)
    })
    inviteMemberDialogSeen.value = false;
}

/*****************修改角色****************** */
const editRoleForm = reactive({
  id: "",
  name: "",
});

function showEditRoleDialog(index: number, row: Role) {
  editRoleForm.id = row.id;
  editRoleDialogSeen.value[index] = true;
}

function hideEditRoleDialog(index: number) {
  editRoleDialogSeen.value[index] = false;
}

function confirmEditRole(index: number) {
  PostEditRoleAPI(editRoleForm).then((res) => {
    console.log(res);
  });
  editRoleDialogSeen.value[index] = false;
  initByGroup(); // force update
}
/*****************删除角色*****************/
function confirmDeleteRole(row: Role) {
  GetDeleteRoleAPI({
    id: row.id,
  }).then((res) => {
    console.log(res);
  });
  initByGroup(); // force update
}
/*******************新增角色 ************* */
const addRoleName: Ref<string> = ref();
const addRoleDialogSeen = ref(false);
function showAddRoleDialog() {
  addRoleDialogSeen.value = true;
}

function hideAddRoleDialog() {
  addRoleDialogSeen.value = false;
}

function confirmAddRole() {
  GetAddRoleAPI({
    addRoleName: addRoleName.value,
    groupId: groupForm.id,
  }).then((res) => {
    console.log(res);
  });
  addRoleDialogSeen.value = false;
   initByGroup(); // force update
}
</script>
