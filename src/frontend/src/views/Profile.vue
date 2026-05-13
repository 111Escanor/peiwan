<template>
  <div class="profile">
    <el-card>
      <div slot="header">个人信息</div>
      <el-form label-width="100px">
        <el-form-item label="用户名">{{ userInfo.username }}</el-form-item>
        <el-form-item label="真实姓名">{{ userInfo.realName }}</el-form-item>
        <el-form-item label="手机号">{{ userInfo.phone }}</el-form-item>
        <el-form-item label="角色">{{ userInfo.role }}</el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { getUserInfo } from '@/api'

export default {
  data() {
    return { userInfo: {} }
  },
  async created() {
    const res = await getUserInfo()
    if (res.code === 200) this.userInfo = res.data
  }
}
</script>