<script setup>
import { computed } from '@vue/reactivity';
import { onMounted } from 'vue';
import { useStore } from 'vuex';

import BaseCite from '../other/BaseCite.vue'
import TeacherRoomsBlock from './TeacherRoomsBlock.vue';

const store = useStore();
const teacher = computed(() => {
  return store.getters.getUserData;
});

</script>
<template>
  <div class="wrapper">
    <base-cite/>
    <teacher-rooms-block :rooms="teacher?.rooms" />
  </div>
</template>
<style scoped>
.wrapper {
  display: flex;
  flex-direction: column;
  gap: 32px;
  margin: 32px;
}
</style>