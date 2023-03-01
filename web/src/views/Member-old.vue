<template>
  用户角色管理
  <!--- 组织选择 --->
  <el-form :model="groupForm" label-width="120px">
    <el-form-item>
      <el-select v-model="groupForm.id" placeholder="请选择要查询的组织">
        <el-option
          v-for="group in groupList.arr"
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
  <el-row :gutter="20">
    <!------------用户列表---------------->
    <el-col :span="18">
      用户列表
      <hr />
      <el-table :data="memberList.arr" stripe style="width: 100%">
        <el-table-column prop="username" label="用户编号" width="150" />
        <el-table-column prop="name" label="姓名" width="150" />
        <el-table-column prop="sex" label="性别" width="100" />
        <!--<el-table-column prop="job" label="身份" width="150" />-->
        <el-table-column prop="roles" label="身份/角色" />
        <!---------------操作栏-------------->
        <el-table-column label="操作">
          <template #default="scope">
            <!-------------授予角色对话窗-------------->
            <el-button size="small" @click="openGrantUserDialog(scope.row)"
              >授予角色</el-button
            >
            <el-dialog
              v-model="grantUserDialogSeen"
              title="授予角色"
              append-to-body
            >
              <el-table
                :data="roleList.arr"
                highlight-current-row
                @current-change="changeCurrentGrantRole"
              >
                <el-table-column prop="roleName" label="角色名称" width="150" />
              </el-table>
              <div style="margin-top: 20px">
                <el-button @click="grantUserDialogSeen = false">取消</el-button>
                <el-button type="primary" @click="grantUser()">确认</el-button>
              </div>
            </el-dialog>

            <!-------------收回角色对话窗-------------->
            <el-button size="small" @click="openRevokeUserDialog(scope.row)"
              >收回角色</el-button
            >
            <el-dialog
              v-model="revokeUserDialogSeen"
              title="收回角色"
              append-to-body
            >
              <el-table
                :data="roleList.arr"
                highlight-current-row
                @current-change="changeCurrentRevokeRole"
              >
                <el-table-column prop="name" label="角色名称" width="150" />
              </el-table>
              <div style="margin-top: 20px">
                <el-button @click="revokeUserDialogSeen = false"
                  >取消</el-button
                >
                <el-button type="primary" @click="revokeUser()">确认</el-button>
              </div>
            </el-dialog>
            <!----------------------------------->

            <el-button
              size="small"
              type="danger"
              @click="deleteUser(scope.row)"
              v-if="deleteUserSeen"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </el-col>

    <!-------------------角色列表---------------------->
    <el-col :span="6">
      角色列表
      <hr />
      <el-table :data="roleList.arr" stripe style="width: 100%">
        <el-table-column prop="roleName" label="角色名称" width="150" />
        <el-table-column label="操作">
          <template #default="scope">
            <!----------- 修改角色--------------->
            <el-button size="small" @click="openEditRoleDialog(scope.row)"
              >修改</el-button
            >
            <el-dialog
              v-model="editRoleDialogSeen"
              title="修改角色"
              append-to-body
            >
              <el-form :model="editedRoleObj.role">
                <el-form-item label="角色名称">
                  <el-input
                    v-model="editedRoleObj.role.name"
                    autocomplete="off"
                  />
                </el-form-item>
              </el-form>
              <template #footer>
                <span class="dialog-footer">
                  <el-button @click="editRoleDialogSeen = false"
                    >取消</el-button
                  >
                  <el-button type="primary" @click="editRole()">确认</el-button>
                </span>
              </template>
            </el-dialog>
            <!---删除角色--->
            <el-button
              size="small"
              type="danger"
              @click="deleteRole(scope.$index, scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <!----------- 新增角色--------------->
      <el-button @click="addRoleSeen = true">新增角色</el-button>
      <el-dialog v-model="addRoleSeen" title="新增角色" append-to-body>
        <el-form v-model="addedRole">
          <el-form-item>
            角色名称：<br />
            <el-input v-model="addedRole.name" autocomplete="off" />
          </el-form-item>
        </el-form>
        <div style="margin-top: 20px">
          <el-button @click="addRoleSeen = false">取消</el-button>
          <el-button type="primary" @click="addRole()">确认</el-button>
        </div>
      </el-dialog>
    </el-col>
  </el-row>
</template>

<script lang="ts" setup>
import { reactive, ref } from "vue";
import {
  DeleteUserAPI,
  GetGroupListAPI,
  GetMemberByOrgAPI,
  GetRoleByOrgAPI,
  GetRoleByUserAPI,
  GrantUserRoleAPI,
} from "../request/api";
import { Group, Member } from "../ts/interface";

/***********  选择组织  ***********/
const groupList = reactive({ arr: [] });
GetGroupListAPI().then((res) => {
  groupList.arr = res.data.data;
  console.log(groupList.arr)
});

const groupForm = reactive({
  id: "",
});

const memberList = reactive({
  arr: [],
});

const roleList = reactive({
  arr: [],
});

function findByGroup() {
  GetMemberByOrgAPI("", groupForm.id).then((res) => {
    memberList.arr = res.data.data;
    console.log(memberList.arr)
  });
  GetRoleByOrgAPI("", groupForm.id).then((res) => {
    roleList.arr = res.data.data;
    console.log(roleList.arr)
  });
}

/**********用户列表*************/
const userList = reactive({
  arr: [],
});

const deleteUserSeen = ref(parseInt(groupForm.id) < 10000); // TODO: 在findByGroup完成权限处理

function deleteUser(row) {
  DeleteUserAPI(row).then((res) => {
    console.log("res");
  });
}

/*****授予角色*****/
const grantUserDialogSeen = ref(false);

const currentGrantUserObj = reactive({
  data: {},
});

function openGrantUserDialog(row) {
  currentGrantUserObj.data = row;
  grantUserDialogSeen.value = true;
  GetRoleByOrgAPI("", groupForm.id).then((res) => {
    console.log(res);
  });
  
  console.log(grantUserDialogSeen)
}

const grantUserCurrentRoleRow = ref();
const changeCurrentGrantRole = (val) => {
  grantUserCurrentRoleRow.value = val;
};

function grantUser() {
  GrantUserRoleAPI({
    grantUserCurrentRoleRow: grantUserCurrentRoleRow,
    currentGrantUser: currentGrantUserObj.data,
  }).then((res) => {
    console.log(res);
  });
  grantUserDialogSeen.value = false;
}

/********收回角色******/
const revokeUserDialogSeen = ref(false);

const revokeRoleList = reactive({
  arr: [],
});
function openRevokeUserDialog(row) {
  GetRoleByUserAPI(row).then((res) => {
    revokeRoleList.arr = res.data.data;
    console.log(res);
  });
  revokeUserDialogSeen.value = true;
}

const revokeUserCurrentRow = ref();
const changeCurrentRevokeRole = (val) => {
  revokeUserCurrentRow.value = val;
};

function revokeUser() {
  console.log("TODO"); // TODO
  revokeUserDialogSeen.value = false;
}

/******************角色列表******************* */

/*******修改角色******* */
const editRoleDialogSeen = ref(false);
const editedRoleObj = reactive({
  role: {
    name: "",
  },
});

function openEditRoleDialog(row) {
  editedRoleObj.role = row;
}

function editRole() {
  // TODO
  editRoleDialogSeen.value = false;
}

function deleteRole(index, row) {
  // TODO
}

/********新增角色****** */
const addRoleSeen = ref(false);

const addedRole = reactive({
  name: "",
});

function addRole() {
  // TODO
}
</script>