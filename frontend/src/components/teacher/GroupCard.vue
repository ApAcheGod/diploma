<script setup>
import { onMounted } from 'vue';

const props = defineProps({
  group : Object,
});

onMounted(() => {
  console.log(props.group);
});

</script>
<template>
  <div class="group-card">
    <div class="group-card__title">
      {{props.group?.name}}
    </div>
    <div class="group-card__count">
      {{props.group?.students?.length ? `${props.group?.students?.length} - студентов` : 'Нет студентов'}}
    </div>
  </div>
</template>
<style lang="scss" scoped>
.group-card {
  display: flex;
  flex-direction: column;
  gap: 16px;
  background: white;
  filter: drop-shadow(0px 3px 6px rgba(0, 0, 0, 0.161));
  padding: 16px;
  // width: min-content;
  // min-height: 368px;
  // min-width: 256px;

  &__title {
    margin-bottom: 24px;
    display: flex;
    flex-direction: column;
    justify-content: left;
    font-family: 'Montserrat';
    font-size: 17px;
    font-weight: 500;
    color: #1d1d1d;
  }
  &__count {
    margin-bottom: 24px;
    display: flex;
    flex-direction: column;
    justify-content: left;
    font-family: 'Montserrat';
    color: #1d1d1d;
  }
}
</style>