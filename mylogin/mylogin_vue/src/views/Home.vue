<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
    <h1>Welcome to Your Vue.js App</h1>
    <h2>{{this.$store.state.user.name}} 欢迎登录</h2>
    <!-- <HelloWorld msg="Welcome to Your Vue.js App"/> -->
    <el-button type="danger" @click.native="logout">退出登录</el-button>
  </div>
</template>

<script>
// @ is an alias to /src
// import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'Home',
  components: {
    // HelloWorld
  },
  methods: {
    async logout() {
      this.$confirm("确定注销并退出系统吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$store.dispatch("LogOut").then(() => {
            location.href = "/login";
          });
        })
        .catch(() => {});
    }
  }

}
</script>
