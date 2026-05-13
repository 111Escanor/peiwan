<template>
  <div class="order-list">
    <el-tabs v-model="activeTab">
      <el-tab-pane label="我的订单" name="player">
        <el-table :data="playerOrders">
          <el-table-column prop="id" label="订单号"></el-table-column>
          <el-table-column prop="amount" label="金额"></el-table-column>
          <el-table-column prop="status" label="状态"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button v-if="scope.row.status === 'pending'" type="success" size="small" @click="pay(scope.row.id)">支付</el-button>
              <el-button v-else-if="scope.row.status === 'completed'" type="info" size="small" @click="review(scope.row)">评价</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="接单大厅" name="master" v-if="role === 'master'">
        <el-table :data="pendingOrders">
          <el-table-column prop="id" label="订单号"></el-table-column>
          <el-table-column prop="amount" label="金额"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button type="primary" size="small" @click="accept(scope.row.id)">接单</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>

    <el-dialog title="评价" :visible.sync="reviewDialog">
      <el-rate v-model="reviewForm.rating"></el-rate>
      <el-input type="textarea" v-model="reviewForm.comment" placeholder="评论"></el-input>
      <span slot="footer">
        <el-button @click="reviewDialog=false">取消</el-button>
        <el-button type="primary" @click="submitReview">提交</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getPlayerOrders, getMasterOrders, mockPay, acceptOrder, completeOrder } from '@/api'

export default {
  data() {
    return {
      activeTab: 'player',
      playerOrders: [],
      masterOrders: [],
      role: localStorage.getItem('role'),
      reviewDialog: false,
      currentOrder: null,
      reviewForm: { rating: 5, comment: '' }
    }
  },
  computed: {
    pendingOrders() {
      return this.masterOrders.filter(o => o.status === 'pending')
    }
  },
  async created() {
    await this.loadOrders()
  },
  methods: {
    async loadOrders() {
      if (this.role === 'master') {
        const res = await getMasterOrders()
        if (res.code === 200) this.masterOrders = res.data
      } else {
        const res = await getPlayerOrders()
        if (res.code === 200) this.playerOrders = res.data
      }
    },
    async pay(orderId) {
      const res = await mockPay({ orderId })
      if (res.code === 200) {
        this.$message.success('支付成功')
        await this.loadOrders()
      }
    },
    async accept(orderId) {
      const res = await acceptOrder(orderId)
      if (res.code === 200) {
        this.$message.success('接单成功')
        await this.loadOrders()
      }
    },
    review(order) {
      this.currentOrder = order
      this.reviewDialog = true
    },
    async submitReview() {
      // 调用评价接口（需要后端实现）
      await completeOrder(this.currentOrder.id)
      this.reviewDialog = false
      this.$message.success('评价成功')
      await this.loadOrders()
    }
  }
}
</script>