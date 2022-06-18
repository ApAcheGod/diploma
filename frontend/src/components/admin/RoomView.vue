<script setup>
import methods from '../../store/methodsAdmin.js';
import {onMounted, ref} from 'vue';
import {useQuasar} from 'quasar';
import RoomCard from './RoomCard.vue';
import RoomDialog from './RoomDialog.vue';

let q = useQuasar();

let rooms = ref();
let teachers = ref ();



let roomPromptIsOpen = ref(false);

onMounted(async () => { 
  Promise.allSettled([
    methods.getRoomsFetch(),
    methods.getTeachersFetch(),
    ])
  .then((results) => {
    rooms.value = results[0].value;
    teachers.value = results[1].value;
  });
});

function triggerPositive(msg) {
  q.notify({
    type: 'positive',
    message: msg
  })
}

function triggerNegative(msg) {
  q.notify({
    type: 'negative',
    message: msg
  })
}

async function updateRoom(newRoom){
  let updateResult = await methods.updateRoomFetch(newRoom);
  if(updateResult){
    rooms.value = await methods.getRoomsFetch();
    triggerPositive('Информация о комнате успешно обновлена!');
  }
  else
    triggerNegative('Не удалось обновить информацию о комнате');
}

async function addNewRoom(newRoom){
  const createResult = await methods.createRoomFetch(newRoom);
  if (createResult) {
    rooms.value = await methods.getRoomsFetch();
    triggerPositive('Успешно добавлена новая комната!');
  }
  else
    triggerNegative('Не удалось добавить комнату');
}

async function deleteRoom(room){
  const deleteResult = await methods.deleteRoomFetch(room);
  if(deleteResult){
    rooms.value = await methods.getRoomsFetch();
    triggerPositive('Информация о комнате успешно удалена!')
  }
  else
    triggerNegative('Не удалось удалить информацию о комнате')
}

</script>
<template>
  <transition-group name="list" >
    <room-card v-for="room in rooms"
      v-bind:key="room?.id"
      :room="room"
      :teachers="teachers"
      @delete-click="deleteRoom"
      @update-click="updateRoom"
    />
  </transition-group>

  <q-page-sticky position="bottom-right" :offset="[20, 20]">
    <q-btn 
      fab 
      icon="add" 
      color="accent"
      @click="roomPromptIsOpen = true"/>
  </q-page-sticky>

  <room-dialog
    updateButtonLabel="Добавить"
    :teachers="teachers"
    :prompt="roomPromptIsOpen"
    @update-click="addNewRoom"
    @prompt-close="roomPromptIsOpen = false"
  />
</template>
<style scoped>

.list-item {
  display: inline-block;
  margin-right: 8px;
}

.list-enter-active,
.list-leave-active {
  transition: all 0.3s ease;
}

.list-enter-from,
.list-leave-to {
  opacity: 0;
  transform: translateY(20px);
}

</style>