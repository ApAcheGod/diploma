<script setup lang="ts">

import { provide } from 'vue';
import methods from './store/methods.js';

provide('methods', methods);

</script>

<template>
  <router-view />
</template>


<style>
.q-layout, .q-header, .q-footer, .q-page {
  position: fixed;
}
</style>
