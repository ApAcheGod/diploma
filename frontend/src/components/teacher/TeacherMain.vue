<script setup>
import { onMounted } from 'vue';
import BaseCite from '../other/BaseCite.vue'
import TeacherRoomsBlockVue from './TeacherRoomsBlock.vue';

// const props = defineProps({
//     Teacher: Object,
// });

</script>
<template>
  <div class="wrapper">
    <base-cite/>
    <teacher-rooms-block-vue/>
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