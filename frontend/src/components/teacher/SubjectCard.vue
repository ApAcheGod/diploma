<script setup>
import { onMounted } from 'vue';

const props = defineProps({
  subject : Object,
});

onMounted(() => {
  console.log(props.subject);
});

</script>
<template>
  <div class="subject-card">
    <div class="subject-card__title">
      {{props.subject?.name}}
    </div>
  </div>
</template>
<style lang="scss" scoped>
.subject-card {

}
</style>