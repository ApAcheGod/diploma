<script setup>
import { computed } from '@vue/reactivity';
import { useStore } from 'vuex';
import JournalBlock from './JournalBlock.vue';
import {onMounted} from "vue";

const store = useStore();
const journalByRooms = computed(() => { const journal = store.getters.getUserExaminations; console.log(journal); return journal;});

onMounted(() => {
  console.log(journalByRooms);
});

</script>
<template>
  <journal-block v-for="room in journalByRooms" :key="room.name" :room="room" />
</template>
<style scoped>
</style>