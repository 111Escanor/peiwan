<template>
  <div class="master-list">
    <el-input v-model="search" placeholder="搜索陪玩师" prefix-icon="el-icon-search" style="width:300px; margin-bottom:20px"></el-input>
    <el-table :data="filteredMasters" style="width:100%">
      <el-table-column prop="userId" label="ID" width="80"></el-table-column>
      <el-table-column prop="gameLevel" label="段位"></el-table-column>
      <el-table-column prop="pricePerHour" label="价格(元/小时)"></el-table-column>
      <el-table-column prop="tags" label="标签"></el-table-column>
      <el-table-column prop="rating" label="评分"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="primary" size="small" @click="order(scope.row)">下单</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="下单" :visible.sync="dialogVisible">
      <el-form :model="orderForm">
        <el-form-item label="陪玩师ID">{{ currentMaster.userId }}</el-form-item>
        <el-form-item label="金额">
          <el-input-number v-model="orderForm.amount" :min="0" :step="10"></el-input-number>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="submitOrder">确认下单</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getMasterList, createOrder } from '@/api'

export default {
  data() {
    return {
      masters: [],
      search: '',
      dialogVisible: false,
      currentMaster: {},
      orderForm: { amount: 0 }
    }
  },
  computed: {
    filteredMasters() {
      return this.masters.filter(m => m.tags.includes(this.search) || m.gameLevel.includes(this.search))
    }
  },
  async created() {
    const res = await getMasterList()
    if (res.code === 200) this.masters = res.data
  },
  methods: {
    order(master) {
      this.currentMaster = master
      this.orderForm.amount = master.pricePerHour
      this.dialogVisible = true
    },
    async submitOrder() {
      const params = {
        masterId: this.currentMaster.userId,
        amount: this.orderForm.amount
      }
      const res = await createOrder(params)
      if (res.code === 200) {
        this.$message.success('下单成功，请去订单页支付')
        this.dialogVisible = false
        this.$router.push('/orders')
      } else {
        this.$message.error(res.msg)
      }
    }
  }
}
</script>