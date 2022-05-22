<script setup>
import { computed } from '@vue/reactivity';
import { useStore } from 'vuex';
import GroupBlock from './GroupBlock.vue';

const store = useStore();
const groupsByRooms = computed(() => store.getters.getTeacherStudentsGroups);
</script>
<template>
  <group-block v-for="group in groupsByRooms"/>
</template>
<style scoped>
</style>