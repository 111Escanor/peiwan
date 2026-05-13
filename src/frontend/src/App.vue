<template>
  <div id="app">
    <Header v-if="isLoggedIn" />
    <router-view />
    <Footer v-if="isLoggedIn" />
  </div>
</template>

<script>
import Header from '@/components/Header.vue'
import Footer from '@/components/Footer.vue'

export default {
  name: 'App',
  components: { Header, Footer },
  computed: {
    isLoggedIn() {
      return !!localStorage.getItem('token')
    }
  }
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
body {
  font-family: 'Helvetica Neue', Helvetica, 'PingFang SC', 'Microsoft YaHei', sans-serif;
  background-color: #f5f7fa;
}
#app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}
</style>