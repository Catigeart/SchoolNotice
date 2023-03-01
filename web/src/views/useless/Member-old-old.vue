<template>
  <el-form :model="selectForm" label-width="120px">
    <el-form-item>
      <el-select v-model="selectForm.groupId" placeholder="请选择要查询的组织">
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
  <el-table :data="memberList.arr" stripe style="width: 100%">
    <el-table-column prop="id" label="编号" width="150" />
    <el-table-column prop="name" label="姓名" width="150" />
    <el-table-column prop="sex" label="性别" width="150" />
    <el-table-column prop="roles" label="角色" width="150" />
    <el-table-column label="操作">
      <template #default="scope">
        <el-button text size="small" @click="deleteUser(scope.row)"
          >删除成员</el-button
        >
        <!------------------------------------------------------>
        <el-button text size="small" @click="openModifyRole()">修改角色</el-button>
        <el-dialog v-model="modifyRoleSeen" title="修改角色" append-to-body>
        </el-dialog>
        <!------------------------------------------------------>
      </template>
    </el-table-column>
  </el-table>
</template>

<script lang="ts" setup>
import { reactive, ref } from "vue";
import {
  GetGroupListAPI,
  GetMemberByKlassAPI,
  GetMemberByOrgAPI,
  GetRoleByOrgAPI,
} from "../../request/api";
import { Group, Member } from "../../ts/interface";

import { getCurrentInstance } from "vue";

/***********  group processing  ***********/
let groupList = reactive({ arr: [] });
GetGroupListAPI().then((res) => {
  groupList.arr = res.data.data;
  console.log(res);
  console.log(groupList);
});
let groupSelected: Group = reactive({
  id: "",
  type: "",
  name: "",
});

const memberList = reactive({
  arr: [],
});

const selectForm = reactive({
  groupSelected: {},
  groupId: "",
});

function findByGroup() {
  GetMemberByOrgAPI("", selectForm.groupId).then((res) => {
    memberList.arr = res.data.data;
  });
}

/** 
function findByGroup(groupList, groupId) {
    console.log(selectForm);
    for (let group in groupList.arr) {
        if (group.id === "klass")
    }
  if (selectForm.group.type === "klass") {
    GetMemberByKlassAPI(selectForm.group.id).then((res) => {
      memberList.arr = res.data.data;
    });
  } else if (this.groupSelected.value.type == "group") {
      GetMemberByOrgAPI(selectForm.group.id).then((res) => {
      memberList.arr = res.data.data;
    });
  }
}*/

/**********  table op mng  ************/
const modifyRoleSeen = ref(false);

const roleList = reactive({
    arr: []
})

function deleteUser(row: Member) {
  console.log(row);
}

function openModifyRole() {
    GetRoleByOrgAPI("", selectForm.groupId).then((res) => {
        roleList.arr = res.data.data;
    })
    modifyRoleSeen.value = !modifyRoleSeen.value;
}

</script>