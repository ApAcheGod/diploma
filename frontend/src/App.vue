<script setup lang="ts">

import {provide} from 'vue';
import methods from './store/methods.js';

provide('methods', methods);

</script>

<template>
  <router-view />
</template>

<style scoped>
</style>
