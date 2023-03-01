<template>
  通知管理
  <el-table :data="noticeList" stripe style="width: 100%">
    <el-table-column prop="id" label="通知编号" width="150" />
    <el-table-column prop="name" label="通知标题" width="150" />
    <el-table-column prop="type" label="通知类型" width="150" />
    <!--<el-table-column prop="org" label="通知来源" width="150" />-->
    <el-table-column prop="role" label="通知者" width="150" />
    <el-table-column prop="klassRole" label="班委" width="150" />
    <el-table-column prop="beginTime" label="开始时间" width="150" />
    <el-table-column prop="endTime" label="结束时间" width="150" />
    <!-- 需班干部确认、需同学确认、需同学回复 -->
    <el-table-column prop="state" label="状态" width="150" />
    <el-table-column label="操作">
      <template>
        <el-button size="small" @click="noticeDetailVisible=true"
          >详情</el-button
        >
        <el-dialog
          v-model="noticeDetailVisible"
          title="通知详情"
          append-to-body
        >
          通知编号：{{ notice.id }}
          <hr />
          通知标题：{{ notice.name }}
          <hr />
          通知内容：{{ notice.content }}
          <hr />
          管理员信息（仅管理员可见）：{{ notice.adminContent }}
          <hr />
          通知来源：{{ notice.org }}
          <hr />
          通知者：{{ notice.role }}
          <hr />
          对接班委：{{ notice.klassRole }}
          <hr />
          开始时间：{{ notice.beginTime }}
          <hr />
          结束时间：{{ notice.endTime }}
          <hr />
          补充通知：
          <hr />

          <el-table :data="notice.supplyList" stripe style="width: 100%">
            <el-table-column prop="role" label="补充者" width="150" />
            <el-table-column prop="content" label="补充内容" />
          </el-table>
          回复内容：
          <hr />
          <el-table :data="notice.replyList" stripe style="width: 100%">
            <el-table-column prop="username" label="用户编号" width="150" />
            <el-table-column prop="name" label="姓名" width="150" />
            <el-table-column prop="klass" label="班级" width="150" />
            <el-table-column prop="content" label="内容" width="150" />
          </el-table>
          <el-button>下载附件</el-button>
          <template>
            <span class="dialog-footer">
              <el-button @click="noticeDetailVisible = false">取消</el-button>
              <el-button type="primary" @click="confirmNoticeDetail()"
                >确认</el-button
              >
            </span>
          </template>
          <el-form>
            补充通知：
            <hr />
            <el-form-item>
              <el-input
                v-model="tmpSupply.content"
                :rows="2"
                type="textarea"
                placeholder="Please input"
            /></el-form-item>
            <el-button>提交</el-button>
          </el-form>

          <template #footer>
            <span class="dialog-footer">
              <el-button @click="noticeDetailVisible = false">取消</el-button>
              <el-button type="primary" @click="confirmNoticeDetail()"
                >确认</el-button
              >
            </span>
          </template>
        </el-dialog>
      </template>
    </el-table-column>
  </el-table>
  <el-button>发布通知</el-button>
</template>

<script lang="ts" setup>
import { reactive, ref } from "vue";

interface Supply {
  role: string;
  time: string;
  content: string;
}

interface Reply {
  username: string;
  name: string;
  klass: string;
  content: string;
}

interface Notice {
  id: string;
  name: string;
  type: string;
  org: string;
  role: string;
  klassRole: string;
  beginTime: string;
  endTime: string;
  state: string;
  content: string;
  adminContent: string;
  supplyList: Supply[];
  replyList: Reply[];
}

const noticeDetailVisible = ref(false);
const notice = {
  id: "1",
  name: "2022大创通知",
  type: "大创通知",
  org: "商学院团委学生会",
  role: "创新创业部干事",
  klassRole: "双创委员",
  beginTime: "2022-05-01",
  endTime: "2022-05-10",
  state: "需同学回复",
  content: "请同学们在截止时间前提交立项！",
  adminContent: "整点薯条",
  supplyList: [
    {
      role: "学习委员",
      time: "2022-05-02",
      content: "请同学们按格式命名",
    },
  ],
  replyList: [
    {
      username: "810218XXXX",
      name: "张三",
      klass: "信管1801",
      content: "测试123",
    },
  ],
};
const noticeList = [notice, notice, notice];
let tmpNotice: Notice = reactive(notice);
let tmpSupply: Supply = reactive({
  role: "",
  time: "",
  content: "",
});

function showNoticeDetail(index: number, row: Notice) {
  noticeDetailVisible.value = true;
  tmpNotice = row;
}

function confirmNoticeDetail() {
  noticeDetailVisible.value = false;
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
