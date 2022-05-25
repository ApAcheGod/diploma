<script setup>
import { computed } from '@vue/reactivity';
import { useStore } from 'vuex';
import GroupBlock from './GroupBlock.vue';

const store = useStore();
const groupsByRooms = computed(() => store.getters.getTeacherStudentsGroups);

</script>
<template>
  <group-block v-for="room in groupsByRooms" :key="room.roomName" :room="room" />
</template>
<style scoped>
</style>