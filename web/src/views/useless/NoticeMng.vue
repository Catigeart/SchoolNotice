<template>
  <el-table :data="tableData2" height="300" stripe style="width: 100%">
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
        <el-button size="small" @click="handleSupply(scope.$index, scope.row)">补充</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script lang="ts" setup>

interface Notice {
    name: string
    group: string
    klassRole: string
    text: string
    needConfirm: string
    isConfirmed: string
    needReply: string
    isReplyed: string
    beginTime: string
    endTime: string
}

const tableData2: Notice[] = [
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
];

const handleSupply = (index: number, row: Notice) => {
  console.log(index, row)
}
</script>