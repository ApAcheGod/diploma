<script setup>
import {inject, ref} from 'vue';

const { state } = inject('state');

let user = ref({});

</script>
<template>
  <q-input v-model="user.login" label="Login" />
  <q-input v-model="user.password" label="Password" />
  <q-btn flat label="Login"  @click="state.user.login(user)"/>
</template>
<style scoped>
</style>