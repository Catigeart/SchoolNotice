<template>
用户角色管理
  <el-row :gutter="20">
    <el-col :span="18">
      用户列表
      <hr />
      <el-table :data="userList" stripe style="width: 100%">
        <el-table-column prop="username" label="用户编号" width="150" />
        <el-table-column prop="name" label="姓名" width="150" />
        <el-table-column prop="sex" label="性别" width="100" />
        <el-table-column prop="job" label="身份" width="150" />
        <!-- 教师、信管1801学生 -->
        <el-table-column prop="roleString" label="角色" />
        <!-- 需将所有角色转换成String -->
        <el-table-column label="操作">
          <template #default="scope">
            <el-button size="small" @click="openUserGrantDialog(scope.row)"
              >授予角色</el-button
            >
            <el-dialog
              v-model="userGrantVisible"
              title="授予角色"
              append-to-body
            >
              <el-table
                :data="roleList"
                highlight-current-row
                @current-change="changeCurrentRole"
              >
                <el-table-column prop="name" label="角色名称" width="150" />
              </el-table>
              <div style="margin-top: 20px">
                <el-button @click="userGrantVisible = false">取消</el-button>
                <el-button type="primary" @click="userGrant()">确认</el-button>
              </div>
            </el-dialog>

            <el-button size="small" @click="openUserRevokeDialog(scope.row)"
              >收回角色</el-button
            >
            <el-dialog
              v-model="userRevokeVisible"
              title="收回角色"
              append-to-body
            >
              <el-table
                :data="roleList"
                highlight-current-row
                @current-change="changeCurrentRole"
              >
                <el-table-column prop="name" label="角色名称" width="150" />
              </el-table>
              <div style="margin-top: 20px">
                <el-button @click="userRevokeVisible = false">取消</el-button>
                <el-button type="primary" @click="userRevoke()">确认</el-button>
              </div>
            </el-dialog>

            <el-button
              size="small"
              type="danger"
              @click="userDelete(scope.$index, scope.row)"
              v-if="userDeleteSeen"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <el-button @click="userAddVisible=true">邀请用户</el-button>
                  <el-dialog
              v-model="userAddVisible"
              title="邀请用户"
              append-to-body
            >
            <el-form v-model="tmpAddUserView">
              <el-form-item>
                用户编号：<br/>
                <el-input v-model="tmpAddUserView.username" autocomplete="off" />
              </el-form-item>
            </el-form>
                          <div style="margin-top: 20px">
                <el-button @click="userAddVisible = false">取消</el-button>
                <el-button type="primary" @click="userAdd()">确认</el-button>
              </div>
            </el-dialog>
    </el-col>

    <el-col :span="6">
      角色列表
      <hr />
      <el-table :data="roleList" stripe style="width: 100%">
        <el-table-column prop="name" label="角色名称" width="150" />
        <el-table-column label="操作">
          <template #default="scope">
            <el-button size="small" @click="openRoleEditDialog(scope.row)"
              >修改</el-button
            >
            <el-dialog
              v-model="roleEditVisible"
              title="修改角色"
              append-to-body
            >
              <el-form :model="tmpRole">
                <el-form-item label="角色名称">
                  <el-input v-model="tmpRole.name" autocomplete="off" />
                </el-form-item>
              </el-form>
              <template #footer>
                <span class="dialog-footer">
                  <el-button @click="dialogFormVisible = false">取消</el-button>
                  <el-button type="primary" @click="roleEdit()">确认</el-button>
                </span>
              </template>
            </el-dialog>

            <el-button
              size="small"
              type="danger"
              @click="roleDelete(scope.$index, scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <el-button @click="roleAddVisible=true">新增角色</el-button>
                        <el-dialog
              v-model="roleAddVisible"
              title="新增角色"
              append-to-body
            >
            <el-form v-model="tmpRole">
              <el-form-item>
                角色名称：<br/>
                <el-input v-model="tmpRole.name" autocomplete="off" />
              </el-form-item>
            </el-form>
                          <div style="margin-top: 20px">
                <el-button @click="roleAddVisible = false">取消</el-button>
                <el-button type="primary" @click="roleAdd()">确认</el-button>
              </div>
            </el-dialog>


    </el-col>
  </el-row>
</template>

<script lang="ts">
// import { reactive, ref } from 'vue'
import { defineComponent } from "@vue/composition-api";

interface User {
  username: string;
  name: string;
  sex: string;
  job: string;
  roleString: string;
}

interface Role {
  name: string;
}

interface AddUserView {
  username: string
}

export default defineComponent({
  setup() {},

  data() {
    return {
      userGrantVisible: false,
      userRevokeVisible: false,
      userDeleteSeen: true,
      roleEditVisible: false,
      userAddVisible: false,
      roleAddVisible: false,

      tmpUser: {
        username: "",
        name: "",
        sex: "",
        job: "",
        roleString: "",
      },

      tmpRole: {
        name: "",
      },

      tmpAddUserView: {
        username: ""
      },

      userList: [
        {
          username: "80121805XX",
          name: "猫心虎",
          sex: "男",
          job: "信管1801学生",
          roleString: "学习委员",
        },
      ],

      roleList: [
        {
          name: "学习委员",
        },
      ],
    };
  },

  methods: {
    userAdd() {
      console.log(this.tmpAddUserView)
      this.userAddVisible = false
    },

    roleAdd() {
      this.roleAddVisible = false
    },

    changeCurrentRole(val: Role | undefined) {
      this.tmpRole = val;
    },

    openUserGrantDialog(row: User) {
      this.tmpUser = Object.assign({}, row);
      this.userGrantVisible = true;
    },
    userGrant() {
      console.log(this.tmpUser);
      this.userGrantVisible = false;
    },

    openUserRevokeDialog(row: User) {
      this.tmpUser = Object.assign({}, row);
      this.userRevokeVisible = true;
    },
    userRevoke() {
      console.log(this.tmpUser);
      this.userRevokeVisible = false;
    },

    userDelete(index: number, row: User) {
      console.log(index, row);
    },

    openRoleEditDialog(row: Role) {
      this.tmpRole = Object.assign({}, row);
      this.roleEditVisible = true;
    },
    roleEdit() {
      console.log(this.tmpRole);
      this.roleEditVisible = false;
    },

    roleDelete(index: number, row: Role) {
      console.log(index, row);
    },
  },
});
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