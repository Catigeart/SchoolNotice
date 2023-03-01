<template>
    <el-table-column prop="name" label="名称" />
    <el-table-column prop="group" label="通知来源" />
    <el-table-column prop="klassRole" label="班干部" />
    <el-table-column prop="text" label="通知内容" />
    <el-table-column prop="needConfirm" label="需要确认" />
    <el-table-column prop="needReply" label="需要回复" />
    <el-table-column prop="beginTime" label="发布时间" />
    <el-table-column prop="endTime" label="截止时间" />
</template>

<script>
import { defineComponent } from '@vue/composition-api'

export default defineComponent({
    setup() {
        
    },
})
</script>

<style>

</style>