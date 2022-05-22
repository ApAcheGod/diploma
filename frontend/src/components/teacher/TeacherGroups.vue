<script setup>
import { computed } from '@vue/reactivity';
import { useStore } from 'vuex';
import RoomBlock from './RoomBlock.vue';

const store = useStore();
const groupsByRooms = computed(() => { const groups = store.getters.getTeacherStudentsGroups; console.log(groups); return groups;});

</script>
<template>
  <room-block v-for="room in groupsByRooms" :key="room.roomName" :room="room" />
</template>
<style scoped>
</style>