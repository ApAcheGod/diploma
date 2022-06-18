<script setup>
import methods from '../../store/methodsAdmin.js';
import {onMounted, ref} from 'vue';
import {useQuasar} from 'quasar'

import SubjectCard from './SubjectCard.vue';
import SubjectDialog from './SubjectDialog.vue';

let q = useQuasar();

let materials = ref();

let teachers = ref();
let subjects = ref();
let tasks = ref();
let rooms = ref();
let groups = ref();



let subjectPromptIsOpen = ref(false);

onMounted(async () => { 
  Promise.allSettled([
    methods.getSubjectsFetch(),
    methods.getTeachersFetch(),
    methods.getTasksFetch(),
    methods.getMaterialsFetch(),
    methods.getRoomsFetch(),
      methods.getGroupsFetch(),
    ])
  .then((results) => {
    subjects.value = results[0].value;
    teachers.value = results[1].value;
    tasks.value = results[2].value;
    materials.value = results[3].value;
    rooms.value = results[4].value;
    groups.value = results[5].value;
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

async function updateSubject(newSubject){
  let updateResult = await methods.updateSubjectFetch(newSubject);
  if(updateResult){
    subjects.value = await methods.getSubjectsFetch();
    triggerPositive('Информация о предмете успешно обновлена!');
    subjectPromptIsOpen.value = false;
  }
  else
    triggerNegative('Не удалось обновить информацию о предмете');
}

async function addNewSubject(newSubject){
  const createResult = await methods.createSubjectFetch(newSubject);
  if (createResult) {
    subjects.value = await methods.getSubjectsFetch();
    triggerPositive('Успешно добавлен новый предмет!');
    subjectPromptIsOpen.value = false;
  }
  else
    triggerNegative('Не удалось добавить предмет');
}

async function deleteSubject(subject){
  const deleteResult = await methods.deleteSubjectFetch(subject);
  if(deleteResult){
    subjects.value = await methods.getSubjectsFetch();
    triggerPositive('Информация о предмете успешно удалена!')
  }
  else
    triggerNegative('Не удалось удалить информацию о предмете')
}

</script>
<template>
  <transition-group name="list" >
    <subject-card v-for="subject in subjects"
      v-bind:key="subject?.id"
      :subject="subject"
      :materials="materials"
      :teachers="teachers"
      :tasks="tasks"
      :rooms="rooms"
      :groups="groups"
      @delete-click="deleteSubject"
      @update-click="updateSubject"
    />
  </transition-group>

  <q-page-sticky position="bottom-right" :offset="[20, 20]">
    <q-btn 
      fab 
      icon="add" 
      color="accent"
      @click="subjectPromptIsOpen = true"/>
  </q-page-sticky>

  <subject-dialog
    updateButtonLabel="Добавить"
    :prompt="subjectPromptIsOpen"
    :materials="materials"
    :teachers="teachers"
    :tasks="tasks"
    :rooms="rooms"
    :groups="groups"
    @update-click="addNewSubject"
    @prompt-close="subjectPromptIsOpen = false"
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