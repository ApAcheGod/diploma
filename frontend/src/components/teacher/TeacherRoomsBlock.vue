<script setup>
import { ref, computed } from 'vue';
import RoomCard from './RoomCard.vue';

const props = defineProps({
  rooms: Array,
});

</script>
<template>
  <div class="rooms-block">
    <room-card v-for="room in props.rooms" :room="room"/>
    <div class="room-card-add">
      <button class="room-card-add__button">
        <img class="room-card-add__icon" src="../../img/add.svg"/>
      </button>
    </div>
  </div>
</template>
<style scoped>
.rooms-block {
  display: grid;
  gap: 16px;
  grid-template-columns: repeat(3, 1fr);
}
.room-card-add {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: white;
  filter: drop-shadow(0px 3px 6px rgba(0, 0, 0, 0.161));
}
.room-card-add__button {
  border-radius: 50px;
  padding: 21px;
  background: #03DAC5;
  transition: .3s;
  box-shadow: 0px 4px 5px rgba(0, 0, 0, 0.14), 0px 1px 10px rgba(0, 0, 0, 0.12), 0px 2px 4px rgba(0, 0, 0, 0.2);
}
.room-card-add__button:hover {
  background: rgba(3, 151, 135, 0.8);
  transition: .3s;
}
</style>