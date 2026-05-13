<template>
  <div class="register-container">
    <el-card class="register-card">
      <h2>注册账号</h2>
      <el-form :model="form" :rules="rules" ref="form">
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" v-model="form.password" placeholder="密码"></el-input>
        </el-form-item>
        <el-form-item prop="realName">
          <el-input v-model="form.realName" placeholder="真实姓名"></el-input>
        </el-form-item>
        <el-form-item prop="phone">
          <el-input v-model="form.phone" placeholder="手机号"></el-input>
        </el-form-item>
        <el-form-item prop="role">
          <el-radio-group v-model="form.role">
            <el-radio label="player">普通玩家</el-radio>
            <el-radio label="master">陪玩师</el-radio>
          </el-radio-group>
        </el-form-item>
        <template v-if="form.role === 'master'">
          <el-form-item prop="gameLevel">
            <el-input v-model="form.gameLevel" placeholder="游戏段位，如：钻石"></el-input>
          </el-form-item>
          <el-form-item prop="pricePerHour">
            <el-input-number v-model="form.pricePerHour" :min="0" :step="10" placeholder="每小时价格"></el-input-number>
          </el-form-item>
          <el-form-item prop="tags">
            <el-input v-model="form.tags" placeholder="标签，逗号分隔"></el-input>
          </el-form-item>
        </template>
        <el-button type="primary" @click="handleRegister" style="width:100%">注册</el-button>
        <div style="margin-top:15px; text-align:center">
          <router-link to="/login">已有账号？去登录</router-link>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { register } from '@/api'

export default {
  data() {
    return {
      form: {
        username: '',
        password: '',
        realName: '',
        phone: '',
        role: 'player',
        gameLevel: '',
        pricePerHour: 50,
        tags: ''
      },
      rules: {
        username: [{ required: true, message: '请输入用户名' }],
        password: [{ required: true, message: '请输入密码' }],
        phone: [{ pattern: /^1[3-9]\d{9}$/, message: '手机号格式错误' }]
      }
    }
  },
  methods: {
    async handleRegister() {
      this.$refs.form.validate(async (valid) => {
        if (!valid) return
        try {
          const res = await register(this.form)
          if (res.code === 200) {
            this.$message.success('注册成功，请登录')
            this.$router.push('/login')
          } else {
            this.$message.error(res.msg)
          }
        } catch (err) {
          console.error(err)
        }
      })
    }
  }
}
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: #f0f2f5;
}
.register-card {
  width: 500px;
}
</style>