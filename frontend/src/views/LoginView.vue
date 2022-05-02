<script setup>
import { ref } from 'vue';
import { useStore } from 'vuex'

let user = ref({});
const state = useStore();

</script>
<template>
  <q-input v-model="user.login" label="Login" />
  <q-input v-model="user.password" label="Password" />
  <q-btn flat label="Login"  @click="state.dispatch('userSignIn', user)"/>
</template>
<style scoped>
</style>