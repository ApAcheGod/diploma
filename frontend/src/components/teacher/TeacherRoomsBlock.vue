<script setup>
import { ref, computed } from 'vue';
import { useQuasar } from 'quasar'
import { useStore } from 'vuex';
import actionsTypes from '../../store/actionsTypes';
import RoomCard from './RoomCard.vue';

const $q = useQuasar();
const store = useStore();
const newRoomName = ref('');
const roomIsNotEditing = ref(true);

const props = defineProps({
  rooms: Array,
});

const teacher = computed(() => {
  return store.getters.getUserData;
});

function createRoom(room) {
  store.dispatch(actionsTypes.CREATE_ROOM, room)
  .then(() => {
    $q.notify({type: 'positive', message: 'Комната успешно создана'})
    newRoomName.value = '';
  })
  .catch(error => {
    console.error(error);
    $q.notify({type: 'negative', message: 'Ошибка при создании комнаты'})
  })
}
function updateRoom(room) {
  store.dispatch(actionsTypes.UPDATE_ROOM, room)
  .then(() => {
    $q.notify({type: 'positive', message: 'Комната успешно обновлена'})
    newRoomName.value = '';
    roomIsNotEditing.value = true
  })
  .catch(error => {
    console.error(error);
    $q.notify({type: 'negative', message: 'Ошибка при обновлении комнаты'})
  })
}
function deleteRoom(room) {
  store.dispatch(actionsTypes.DELETE_ROOM, room)
  .then(() => {
    $q.notify({type: 'positive', message: 'Комната успешно удалена'})
    newRoomName.value = '';
    roomIsNotEditing.value = true
  })
  .catch(error => {
    console.error(error);
    $q.notify({type: 'negative', message: 'Ошибка при удалении комнаты'})
  })
}

</script>
<template>
  <div class="rooms-block">
    <transition-group name="list" >
      <room-card 
        v-for="room in props.rooms"
        :key="room.id"
        :room="room"
        :allowEditing="roomIsNotEditing" 
        v-on:edit="roomIsNotEditing = false"
        v-on:delete="deleteRoom"
        v-on:cancel="roomIsNotEditing = true"
        v-on:save="updateRoom"
      />
      <div key="add-el" class="room-card-add">
          <q-input v-model="newRoomName" label="Название комнаты"/>
          <button class="room-card-add__button" v-on:click="createRoom({ name: newRoomName, teacherId:  teacher.id})">
            <img class="room-card-add__icon" src="../../img/add.svg"/>
          </button>
      </div>
    </transition-group>
  </div>
</template>
<style scoped>
.rooms-block {
  display: flex;
  gap: 16px;
  flex-wrap: wrap;
  flex-direction: row;
  margin: 24px;
}
.room-card-add {
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 24px;
  align-items: center;
  background-color: white;
  filter: drop-shadow(0px 3px 6px rgba(0, 0, 0, 0.161));
  min-height: 368px;
  min-width: 256px;

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