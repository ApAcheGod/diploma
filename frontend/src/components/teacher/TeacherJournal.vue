<script setup>
import { computed } from '@vue/reactivity';
import { useStore } from 'vuex';
import JournalBlock from './JournalBlock.vue';
import {onMounted} from "vue";

const store = useStore();
const examinations = computed(() => { const journal = store.getters.getUserExaminations; console.log(journal); return journal;});
const solutions = computed(() => { const journal = store.getters.getUserSolutions; console.log(journal); return journal;});

onMounted(() => {
  console.log(examinations);
  console.log(solutions);
});

</script>
<template>
  <journal-block v-for="room in examinations" :key="room.name" :room="room" />
</template>
<style scoped>
</style>