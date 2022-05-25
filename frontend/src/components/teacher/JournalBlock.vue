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
    <div class="room-block__groups">
<!--      <group-card v-for="group in props.room?.roomGroups" :key="group.id" :group="group"/>-->
    </div>

  </div>
</template>
<style lang="scss">
.room-block {
  margin: 24px;
  &__title {
    font-family: 'Montserrat';
    text-transform: uppercase;
    font-weight: 500;
    font-size: 19px;
    letter-spacing: 0.03rem;
    margin-bottom: 16px;
    hr {
      margin-bottom: 8px;
      margin-top: 8px;
    }
  }
  &__groups {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    gap: 24px;
  }
}
</style>