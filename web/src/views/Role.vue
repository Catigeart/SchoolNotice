<template>
  <h3>角色管理模块</h3>
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
                <el-button type="primary" @click="confirmEditRole(scope.$index)"
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
  </div>
</template>

<script lang="ts" setup>
import { reactive, Ref, ref } from "vue";
import {
  GetGroupListAPI,
  GetRoleByOrgAPI,
  PostEditRoleAPI,
  GetDeleteRoleAPI,
  GetAddRoleAPI,
  GetIsSuperRoleAPI,
} from "../request/api";

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

const roleList = ref([]); // 角色表格

// 弹窗可视化绑定
const editRoleDialogSeen = ref([]);

const memberManageSeen = ref(); // 如果是班级（或权限不足的管理组管理员）将被隐藏
//const modifyManageSeen = ref();

function initByGroup() {
  // 点击查询，按所选组织查找表格
  GetRoleByOrgAPI("", groupForm.id).then((res) => {
    roleList.value = res.data.data;
    console.log(res);
  });

  GetIsSuperRoleAPI({ orgId: groupForm.id }).then((res) => {
    if (res.data.data === true && parseInt(groupForm.id) < 10000) {
      memberManageSeen.value = true;
      // modifyManageSeen.value = true;
    } else {
      memberManageSeen.value = false;
      // modifyManageSeen.value = false;
    }
  });

  editRoleDialogSeen.value = Array(roleList.value.length).fill(false);
  /*
  if (parseInt(groupForm.id) > 10000) {
    memberManageSeen.value = false;
  } else {
    //memberManageSeen.value = true; // TODO: 通知组管理员权限控制欠缺
  }*/
}
////////////////////
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

async function confirmEditRole(index: number) {
  await PostEditRoleAPI(editRoleForm).then((res) => {
    console.log(res);
  });
  alert("角色修改成功！")
  editRoleDialogSeen.value[index] = false;
  initByGroup(); // force update
}
/*****************删除角色*****************/
async function confirmDeleteRole(row: Role) {
  await GetDeleteRoleAPI({
    id: row.id,
  }).then((res) => {
    console.log(res);
    if (res.data.data === true) {
      alert("删除角色成功！")
    } else {
      alert("删除角色失败，请先删除拥有该角色的所有用户！")
    }
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

async function confirmAddRole() {
  await GetAddRoleAPI({
    addRoleName: addRoleName.value,
    groupId: groupForm.id,
  }).then((res) => {
    console.log(res);
  });
  alert("新增角色成功！")
  addRoleDialogSeen.value = false;
  initByGroup(); // force update
}
</script>

<style scoped>
</style>