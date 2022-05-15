<script setup>
import { inject, onMounted, ref } from 'vue';
import {useQuasar} from 'quasar'
import GroupCard from './GroupCard.vue';
import GroupDialog from './GroupDialog.vue';

let $q = useQuasar();

let groups = ref();
let tasks = ref();
let students = ref();
let rooms = ref();
let studentsWithoutGroup = ref();

const methods = inject('methods');

let groupPromptIsOpen = ref(false);

onMounted(async () => { 
  Promise.allSettled([
    methods.getGroupsFetch(),
    methods.getStudentsFetch(),
    methods.getRoomsFetch(),
    methods.getStudentsWithoutGroupFetch(),
    ])
  .then((results) => {
    groups.value = results[0].value;
    students.value = results[1].value;
    rooms.value = results[2].value;
    studentsWithoutGroup.value = results[3].value;
  });
});

function triggerPositive(msg) {
  $q.notify({
    type: 'positive',
    message: msg
  })
}

function triggerNegative(msg) {
  $q.notify({
    type: 'negative',
    message: msg
  })
}

async function updateGroup(newGroup){
  const updateResult = await methods.updateGroupFetch(newGroup);
  studentsWithoutGroup.value = await methods.getStudentsWithoutGroupFetch();

  if (updateResult) {
    groups.value = await methods.getGroupsFetch();
    triggerPositive('Информация о группе успешно обновлена!');
  }
  else
    triggerNegative('Не удалось обновить информацию о группе');
}

async function addNewGroup(newgroup){
  const createResult = await methods.createGroupFetch(newgroup);
  studentsWithoutGroup.value = await methods.getStudentsWithoutGroupFetch();

  if (createResult) {
    groups.value = await methods.getGroupsFetch();
    triggerPositive('Успешно добавлена новая группа!');
  }
  else
    triggerNegative('Не удалось добавить группу');
}

async function deleteGroup(group){
  const deleteResult = await methods.deleteGroupFetch(group);
  studentsWithoutGroup.value = await methods.getStudentsWithoutGroupFetch();

  if(deleteResult){
    groups.value = await methods.getGroupsFetch();
    triggerPositive('Информация о группе успешно удалена!')
  }
  else
    triggerNegative('Не удалось удалить информацию о группе')
}

</script>
<template>
  <transition-group name="list" >
    <group-card v-for="group in groups"
      v-bind:key="group?.id"
      :group="group"
      :students="students"
      :rooms="rooms"
      :studentsWithoutGroup="studentsWithoutGroup"
      @delete-click="deleteGroup"
      @update-click="updateGroup"
    />
  </transition-group>

  <q-page-sticky position="bottom-right" :offset="[20, 20]">
    <q-btn 
      fab 
      icon="add" 
      color="accent"
      @click="groupPromptIsOpen = true"/>
  </q-page-sticky>

  <group-dialog
    updateButtonLabel="Добавить"
    :students="students"
    :studentsWithoutGroup="studentsWithoutGroup"
    :rooms="rooms"
    :prompt="groupPromptIsOpen"
    @update-click="addNewGroup"
    @prompt-close="groupPromptIsOpen = false"
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