<template>
  <h3>个人消息模块</h3>
  <!--个人信息列表-->
  <el-table :data="persMsgList" stripe style="width: 100%">
    <el-table-column prop="name" label="消息名称" width="150" />
    <el-table-column prop="msgType.typeName" label="消息类型" width="150" />
    <el-table-column prop="sendAllGroup.name" label="来源组织" width="150" />
    <el-table-column prop="sendAllRole.name" label="来源角色" width="150" />
    <el-table-column prop="sendUsername" label="发送者" width="150" />
    <el-table-column prop="operation.operationName" label="操作" width="150" />
    <el-table-column label="状态" width="150">
      <template #default="scope">
        <el-button v-if="scope.row.operation.id != 0"
          plain
          :type="scope.row.isDone === true ? 'success' : 'warning'"
          size="mini"
        >
          {{ scope.row.isDone === true ? "已处理" : "待处理" }}</el-button
        >
      </template>
    </el-table-column>

    <el-table-column label="操作">
      <template #default="scope">
        <!--------------消息详情------------------>
        <el-button
          size="small"
          @click="showMsgDetailDialog(scope.$index, scope.row)"
          >详情</el-button
        >
        <el-dialog
          v-model="msgDetailSeen[scope.$index]"
          title="消息详情"
          append-to-body
        >
          消息名称：{{ scope.row.id }}
          <hr />
          消息类型：{{ scope.row.msgType.typeName }}
          <hr />
          <!--
        消息来源：{{ scope.row.sendAllGroup.name }}
        <hr />
        发送者：{{ scope.row.sendAllRole.name }}
        <hr />
        -->
          状态：
          <div v-if="scope.row.isDone">已完成</div>
          <div v-else>未完成</div>
          <hr />
          内容：{{ scope.row.content }}
          <hr />

          <span class="dialog-footer">
            <el-button @click="hideMsgDetailDialog(scope.$index, scope.row)"
              >取消</el-button
            >
            <div v-if="!scope.row.isDone">
              <!---需确认---->
              <div v-if="scope.row.operation.id === 1">
                <el-button
                  type="primary"
                  @click="confirmMsg(scope.$index, scope.row)"
                  >确认消息</el-button
                >
              </div>
              <!----需接受/拒绝---->
              <div v-else-if="scope.row.operation.id === 2">
                <el-button
                  type="primary"
                  @click="acceptMsg(scope.$index, scope.row)"
                  >接受</el-button
                >
                <el-button @click="rejectMsg(scope.$index, scope.row)"
                  >拒绝</el-button
                >
              </div>
              <!-----
              <div v-else-if="scope.row.operation.id === 3"></div>    需回复
               ---->
            </div>
          </span>
        </el-dialog>
      </template>
    </el-table-column>
  </el-table>
</template>

<script lang="ts" setup>
import { reactive, Ref, ref } from "vue";
import {
  GetAcceptMsgAPI,
  GetConfirmMsgAPI,
  GetPersMsgAPI,
  GetRejectMsgAPI,
} from "../request/api";
import {
  AllGroup,
  AllRole,
  Operation,
  MsgType,
  PersMsg,
} from "../ts/interface";

/************初始化数据************* */
const persMsgList: Ref<PersMsg[]> = ref([
  {
    id: NaN,
    name: "",
    msgType: { id: NaN, typeName: "" },
    sendAllGroup: { id: NaN, name: "", isKlass: false },
    sendAllRole: { id: NaN, name: "", isKlass: false },
    sendUsername: "",
    receiveAllGroup: { id: NaN, name: "", isKlass: false },
    receiveAllRole: { id: NaN, name: "", isKlass: false },
    receiveUsername: "",
    content: "",
    operation: { id: NaN, name: "" },
    isDone: false,
  },
]);
GetPersMsgAPI().then((res) => {
  persMsgList.value = res.data.data;
  console.log(res);
});
const msgDetailSeen: Ref<boolean[]> = ref(
  Array(persMsgList.value.length).fill(false)
);
/*****************通知详情******************/
function showMsgDetailDialog(index: number, row: PersMsg) {
  msgDetailSeen.value[index] = true;
}

function hideMsgDetailDialog(index: number, row: PersMsg) {
  msgDetailSeen.value[index] = false;
}

async function confirmMsg(index: number, row: PersMsg) {
  await GetConfirmMsgAPI({}, row.id).then((res) => {
    console.log(res);
  });
  alert("消息确认成功！")
  GetPersMsgAPI().then((res) => {
    persMsgList.value = res.data.data;
    console.log(res);
  });
  hideMsgDetailDialog(index, row);
}

async function acceptMsg(index: number, row: PersMsg) {
  await GetAcceptMsgAPI({}, row.id).then((res) => {
    console.log(res);
  });
  alert("已接受消息/邀请！")
  GetPersMsgAPI().then((res) => {
    persMsgList.value = res.data.data;
    console.log(res);
  });
  hideMsgDetailDialog(index, row);
}

async function rejectMsg(index: number, row: PersMsg) {
  await GetRejectMsgAPI({}, row.id).then((res) => {
    console.log(res);
  });
  alert("已拒绝消息/邀请！")
  GetPersMsgAPI().then((res) => {
    persMsgList.value = res.data.data;
    console.log(res);
  });
  hideMsgDetailDialog(index, row);
}
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
