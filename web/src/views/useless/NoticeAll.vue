<template>
  <el-table :data="tableData" height="300" stripe style="width: 100%">
    <el-table-column prop="id" label="通知编号" />
    <el-table-column prop="name" label="名称" />
    <el-table-column prop="group" label="通知来源" />
    <el-table-column prop="klassRole" label="班干部" />
    <el-table-column prop="text" label="通知内容" />
    <el-table-column prop="needConfirm" label="需要确认" />
    <el-table-column prop="needReply" label="需要回复" />
    <el-table-column prop="beginTime" label="发布时间" />
    <el-table-column prop="endTime" label="截止时间" />
    <!--
    <el-table-column prop="date" label="Date" width="180" />
    <el-table-column prop="name" label="Name" width="180" />
    <el-table-column prop="address" label="Address" />
    -->
    <el-table-column label="操作">
      <template #default="scope">
        <el-button size="small" @click="handleDetail(scope.$index, scope.row)"
          >详情</el-button
        >

        <!-- Form -->
        <el-button type="text" @click="dialogFormVisible = true"
          >open a Form nested Dialog</el-button
        >

        <el-dialog v-model="dialogFormVisible" title="Shipping address">
          <el-form :model="form">
            <el-form-item label="Promotion name" :label-width="formLabelWidth">
              <el-input v-model="form.name" autocomplete="off" />
            </el-form-item>
            <el-form-item label="Zones" :label-width="formLabelWidth">
              <el-select
                v-model="form.region"
                placeholder="Please select a zone"
              >
                <el-option label="Zone No.1" value="shanghai" />
                <el-option label="Zone No.2" value="beijing" />
              </el-select>
            </el-form-item>
          </el-form>
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="dialogFormVisible = false">Cancel</el-button>
              <el-button type="primary" @click="dialogFormVisible = false"
                >Confirm</el-button
              >
            </span>
          </template>
        </el-dialog>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import { reactive, ref } from 'vue'

export default {
  data() {
    return {
        dialogFormVisible : ref(false),

        formLabelWidth : '140px',

form : reactive({
  name: '',
  region: '',
  date1: '',
  date2: '',
  delivery: false,
  type: [],
  resource: '',
  desc: '',
}),

      tableData: [
        {
          name: "学分认证表通知",
          group: "商学院创新创业部",
          klassRole: "学习委员",
          text: "请同学们上交学分认证表。",
          needConfirm: "是",
          isConfirmed: "否",
          needReply: "是",
          isReplyed: "否",
          beginTime: "2022-05-01",
          endTime: "2022-05-10",
        },
        /*
  {
    date: '2016-05-03',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  
  {
    date: '2016-05-02',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-04',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-01',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  */
      ],
    };
  },

  methods: {
    handleDetail(index, row) {
      console.log(index, row);
    },
  },
};
</script>