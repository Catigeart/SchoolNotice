<template>
  <h1>登录</h1>
  <el-row>
    <el-col :span="8"></el-col>
    <el-col :span="8">
      <el-form class="demo-form-inline">
        <el-form-item label="username">
          <el-input v-model="loginUser.username" placeholder="用户名" />
        </el-form-item>
        <el-form-item label="password">
          <el-input
            v-model="loginUser.password"
            type="password"
            placeholder="密码"
          />
        </el-form-item>
        <!--
        <el-form-item>
          是否登入通知组：<el-checkbox-button v-model="isLoginGroup" label="是否登入通知组"
            >登入通知组</el-checkbox-button
          >
          </el-form-item>
          
        <el-form-item><el-input :disabled="!isLoginGroup" placeholder="通知组名称"></el-input></el-form-item>
        -->
        <el-form-item>
          <el-button @click="login" type="primary">登录</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <el-col :span="8"></el-col>
  </el-row>
</template>

<script lang="ts" setup>
import { reactive, ref } from "vue";
import { PostTestAPI, LoginAPI } from "../../request/api";

interface LoginUser {
  username: string;
  password: string;
}

const isLoginGroup = ref(false);

const loginUser_ = {
  username: "",
  password: "",
};
let loginUser: LoginUser = reactive(loginUser_);
function login(loginUser: LoginUser) {
  LoginAPI(loginUser).then((res) => {
    console.log(res);
    alert(res.data.msg);
  });
}
</script>

