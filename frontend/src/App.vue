<script setup lang="ts">

import {provide} from 'vue';
import store from './store';

provide('store', store);

</script>

<template>
  <router-view />
</template>

<style scoped>
</style>
