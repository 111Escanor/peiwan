<template>
  <div class="admin">
    <el-tabs>
      <el-tab-pane label="陪玩师审核">
        <el-table :data="pendingMasters">
          <el-table-column prop="userId" label="用户ID"></el-table-column>
          <el-table-column prop="gameLevel" label="段位"></el-table-column>
          <el-table-column prop="pricePerHour" label="价格"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button type="success" size="small" @click="approve(scope.row.userId, true)">通过</el-button>
              <el-button type="danger" size="small" @click="approve(scope.row.userId, false)">拒绝</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { getPendingMasters, approveMaster } from '@/api'

export default {
  data() {
    return { pendingMasters: [] }
  },
  async created() {
    const res = await getPendingMasters()
    if (res.code === 200) this.pendingMasters = res.data || []
  },
  methods: {
    async approve(userId, approved) {
      await approveMaster(userId, approved)
      this.$message.success('操作成功')
      this.pendingMasters = this.pendingMasters.filter(m => m.userId !== userId)
    }
  }
}
</script>