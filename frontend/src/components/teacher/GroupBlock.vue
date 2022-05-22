<script setup>
import { onMounted } from 'vue';
import GroupCard from './GroupCard.vue';
const props = defineProps({
  room : Object,
});

onMounted(() => {
  console.log(props.room);
});

</script>
<template>
  <div class="room-block">
    <div class="room-block__title">
      <hr/>
      {{props.room?.roomName}}
    </div>
    <group-card v-for="group in props.room?.roomGroups" :group="group"/>
  </div>
</template>
<style lang="scss">
.room-block {

  &__title {

  }
}
</style>