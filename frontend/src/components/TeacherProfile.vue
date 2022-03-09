<script setup>
import { inject, onMounted } from 'vue';

const { state } = inject('state');

onMounted(() => {
  console.log(state.teacher);
});

// const props = defineProps({
//     Teacher: Object,
// });

</script>
<template>
  Профиль учителя {{state.teacher?.first_name}}
</template>
<style scoped>
</style>