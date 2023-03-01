<template>
  <h1>登录</h1><hr>
  {{ userReturn.username + " " + userReturn.password }}
  <el-form :inline="true" :model="userForm" class="demo-form-inline">
    <el-form-item label="username">
      <el-input v-model="userForm.username" placeholder="用户名" />
    </el-form-item>
    <el-form-item label="password">
      <el-input v-model="userForm.password" placeholder="密码" />
    </el-form-item>
    <el-form-item>
      <el-button @click="getTest" type="primary">get test</el-button>
      <el-button @click="postTest" type="primary">post test</el-button><br>
    </el-form-item>
  </el-form>
</template>

<script>
import { reactive } from 'vue'
import { GetTestAPI, PostTestAPI } from "@/request/api"

export default {
  name: "index",
  data() {
      return {
        userForm : reactive({
            username: '',
            password: '',
        }),
        userReturn: {
          username: '',
          password: '',
        }
      }
  },
  methods: {
    getTest() {
        const username = this.userForm.username
        const password = this.userForm.password
      GetTestAPI({
          username,
          password
      }).then((res) => {
        this.userReturn.username = res.data.username;
        this.userReturn.password = res.data.password;
        console.log(res);
      });
    },
    postTest() {
      PostTestAPI(this.userForm).then((res) => {
        this.userReturn.username = res.data.username;
        this.userReturn.password = res.data.password;
        console.log(res);
      });
    },
  },
};
</script>

<style scoped>
</style>