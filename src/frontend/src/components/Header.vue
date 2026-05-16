<template>
  <div class="header">
    <div class="logo">
      <img src="@/assets/logo.png" alt="logo" />
      <span>Escanor陪玩</span>
    </div>
    <div class="nav">
      <router-link to="/home">首页</router-link>
      <router-link to="/masters">陪玩师</router-link>
      <router-link to="/orders">我的订单</router-link>
      <router-link to="/profile">个人中心</router-link>
      <router-link v-if="isAdmin" to="/admin">后台管理</router-link>
      <el-button type="text" @click="logout">退出</el-button>
    </div>
  </div>
</template>

<script>
export default {
  computed: {
    isAdmin() {
      const role = localStorage.getItem('role')
      return role === 'admin'
    }
  },
  methods: {
    logout() {
      localStorage.clear()
      this.$router.push('/login')
      this.$message.success('已退出登录')
    }
  }
}
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  height: 60px;
  background-color: #409eff;
  color: white;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}
.logo {
  display: flex;
  align-items: center;
}
.logo img {
  width: 40px;
  height: 40px;
  margin-right: 10px;
}
.logo span {
  font-size: 20px;
  font-weight: bold;
}
.nav a, .nav .el-button {
  color: white;
  margin-left: 20px;
  text-decoration: none;
}
.nav a.router-link-active {
  font-weight: bold;
  border-bottom: 2px solid white;
}
</style>