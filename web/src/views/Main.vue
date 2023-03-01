<template>
<div>欢迎进入校园通知管理系统！</div>

<!--
    <h1>主界面</h1>

    <router-link :to="{name:'Member'}"><el-button v-if="roleSeen" type="primary">用户一览</el-button></router-link>
    <router-link :to="{name:'Notice'}"><el-button v-if="noticeSeen" type="primary">通知一览</el-button></router-link>
    <router-link :to="{name:'Msg'}"><el-button v-if="noticeSeen" type="primary">个人消息</el-button></router-link>
   -->
    <!-- 限超级管理员：角色管理里面放两个表格，一个是用户一览，一个是角色一览，用户一览包括角色的授予和收回，角色一览包括CRUD -->
    <!-- 班级管理员不能对角色CRUD（重要） -->
    <!-- 限管理员：通知管理会返回所有角色相关的通知，可以发布通知、查看详情、补充通知、下载附件 -->
    <!-- 所有用户：显示曾经收到的所有通知，可以查看详情，确认通知、上传文件，管理员只能收到邀请 -->
    <!--
    <router-link :to="{name:'Mailbox'}"><el-button v-if="mailboxSeen" type="primary">收件箱</el-button></router-link>
-->
</template>

<script lang="ts" setup>
import { reactive, ref } from "vue";
import router from '../router/index.js';
import { GetIfRoleSeenAPI } from "../request/api";
import "../ts/interface"

let roleSeen: boolean = true;
let noticeSeen: boolean = true;
let mailboxSeen: boolean = true;

/*
GetIfRoleSeenAPI().then((res) => {
    if (res.data.code === 200) {
        roleSeen = res.data.data;
    }
})
*/



</script>