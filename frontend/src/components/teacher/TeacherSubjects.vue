<script setup>
import { computed } from '@vue/reactivity';
import { useStore } from 'vuex';
import SubjectBlock from './SubjectBlock.vue';

const store = useStore();
const subjectByRooms = computed(() => store.getters.getUserSubjects);

</script>
<template>
  <subject-block v-for="room in subjectByRooms" :key="room.name" :room="room" />
</template>
<style scoped>
</style>