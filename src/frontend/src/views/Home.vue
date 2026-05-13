<template>
  <div class="home">
    <el-carousel height="300px">
      <el-carousel-item v-for="(item, idx) in banners" :key="idx">
        <div class="banner" :style="{ backgroundImage: `url(${item})` }"></div>
      </el-carousel-item>
    </el-carousel>
    <div class="recommend">
      <h3>推荐陪玩师</h3>
      <el-row :gutter="20">
        <el-col :span="6" v-for="master in masters" :key="master.userId">
          <el-card shadow="hover" @click.native="gotoDetail(master.userId)">
            <div class="master-avatar">🎮</div>
            <div class="master-info">
              <div>段位：{{ master.gameLevel }}</div>
              <div>价格：{{ master.pricePerHour }}元/小时</div>
              <div>评分：{{ master.rating }}</div>
              <div>标签：{{ master.tags }}</div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import { getMasterList } from '@/api'

export default {
  data() {
    return {
      banners: ['https://via.placeholder.com/1200x300?text=无畏契约', 'https://via.placeholder.com/1200x300?text=高端陪玩'],
      masters: []
    }
  },
  async created() {
    const res = await getMasterList()
    if (res.code === 200) {
      this.masters = res.data.slice(0, 4)
    }
  },
  methods: {
    gotoDetail(userId) {
      this.$router.push(`/masters?userId=${userId}`)
    }
  }
}
</script>

<style scoped>
.home {
  padding: 20px;
}
.banner {
  height: 100%;
  background-size: cover;
  background-position: center;
}
.recommend {
  margin-top: 30px;
}
.master-avatar {
  font-size: 48px;
  text-align: center;
}
.master-info {
  margin-top: 10px;
  text-align: center;
}
</style>