<script setup>
import { onMounted } from 'vue';
import MaterialCard from './MaterialCard.vue';
const props = defineProps({
  room : Object,
});

onMounted(() => {
  console.log(props.room);
});

</script>
<template>
  <div class="material-block">
    <div class="material-block__title">
      <hr/>
      {{props.room?.roomName}}
    </div>
      <material-card v-for="material in props.room?.roomMaterials" :material="material"/>
  </div>
</template>
<style lang="scss">
.room-block {

  &__title {

  }
}
</style>