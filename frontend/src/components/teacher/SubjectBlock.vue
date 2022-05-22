<script setup>
import { onMounted } from 'vue';
import SubjectCard from "./SubjectCard.vue";
const props = defineProps({
  room : Object,
});

onMounted(() => {
  console.log(props.room);
});

</script>
<template>
  <div class="subject-block">
    <div class="subject-block__title">
      <hr/>
      {{props.room?.roomName}}
    </div>
    <subject-card v-for="subject in props.room?.roomSubjects" :subject="subject"/>
  </div>
</template>
<style lang="scss">
.subject-block {

  &__title {

  }
}
</style>