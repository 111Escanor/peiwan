<template>
  <div class="login-container">
    <el-card class="login-card">
      <h2>Escanor陪玩平台</h2>
      <el-form :model="form" :rules="rules" ref="form">
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="用户名" prefix-icon="el-icon-user"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" v-model="form.password" placeholder="密码" prefix-icon="el-icon-lock"></el-input>
        </el-form-item>
        <el-button type="primary" @click="handleLogin" :loading="loading" style="width:100%">登录</el-button>
        <div style="margin-top:15px; text-align:center">
          <router-link to="/register">还没有账号？立即注册</router-link>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { login } from '@/api'

export default {
  data() {
    return {
      form: { username: '', password: '' },
      rules: {
        username: [{ required: true, message: '请输入用户名' }],
        password: [{ required: true, message: '请输入密码' }]
      },
      loading: false
    }
  },
  methods: {
    async handleLogin() {
      this.$refs.form.validate(async (valid) => {
        if (!valid) return
        this.loading = true
        try {
          const res = await login(this.form)
          if (res.code === 200) {
            localStorage.setItem('token', res.data)
            // 解析token获取角色（实际应由后端返回，这里简化，调用info接口）
            const userInfo = await this.$api.getUserInfo()
            localStorage.setItem('role', userInfo.data.role)
            this.$message.success('登录成功')
            this.$router.push('/home')
          } else {
            this.$message.error(res.msg)
          }
        } catch (err) {
          console.error(err)
        } finally {
          this.loading = false
        }
      })
    }
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}
.login-card {
  width: 400px;
  padding: 20px;
}
h2 {
  text-align: center;
  margin-bottom: 20px;
}
</style>