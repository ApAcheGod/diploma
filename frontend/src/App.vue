<script setup lang="ts">
import state from './store';
import { provide } from 'vue';

provide('state', state);

</script>

<template>
  <router-view />
</template>

<style scoped>
</style>
