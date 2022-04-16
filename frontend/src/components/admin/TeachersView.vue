<script setup>
import {inject, onMounted, ref} from 'vue';
import {useQuasar} from 'quasar'

import TeacherDialog from './TeacherDialog.vue';
import TeacherCard from './TeacherCard.vue';

let $q = useQuasar();

let teachers = ref();
let subjects = ref();
let tasks = ref();
let materials = ref();
let rooms = ref();

const store = inject('store');

let teacherPromptIsOpen = ref(false);

onMounted(async () => { 
  Promise.allSettled([
    store.methods.getTeachersFetch(),
    store.methods.getSubjectsFetch(),
    store.methods.getTasksFetch(),
    store.methods.getMaterialsFetch(),
    store.methods.getRoomsFetch(),
    ])
  .then((results) => {
    teachers.value = results[0].value;
    subjects.value = results[1].value;
    tasks.value = results[2].value;
    materials.value = results[3].value;
    rooms.value = results[4].value;
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

async function updateTeacher(newTeacher){
  let updateResult = await store.methods.updateTeacherFetch(newTeacher);
  if(updateResult){
    teachers.value = await store.methods.getTeachersFetch();
    triggerPositive('Информация о преподавателе успешно обновлена!');
  }
  else
    triggerNegative('Не удалось обновить информацию о преподавателе');
}

async function addNewTeacher(newteacher){
  const createResult = await store.methods.createTeacherFetch(newteacher);
  if (createResult) {
    teachers.value = await store.methods.getTeachersFetch();
    triggerPositive('Успешно добавлен новый преподаватель!');
  }
  else
    triggerNegative('Не удалось добавить преподавателя');
}

async function deleteTeacher(teacher){
  const deleteResult = await store.methods.deleteTeacherFetch(teacher);
  if(deleteResult){
    teachers.value = await store.methods.getTeachersFetch();
    triggerPositive('Информация о преподавателе успешно удалена!')
  }
  else
    triggerNegative('Не удалось удалить информацию о преподавателе')
}

</script>
<template>
  <transition-group name="list" >
    <teacher-card v-for="teacher in teachers"
      v-bind:key="teacher?.id"
      :teacher="teacher"
      :subjects="subjects"
      :tasks="tasks"
      :materials="materials"
      :rooms="rooms"
      @delete-click="deleteTeacher"
      @update-click="updateTeacher"
    />
  </transition-group>

  <q-card class="my-card add-card flex justify-center items-center h-48" style="max-width: 30%; min-width: 300px;">
    <q-btn 
      fab 
      icon="add" 
      color="accent"
      @click="teacherPromptIsOpen = true"/>
  </q-card>

  <teacher-dialog
    formTitle="Добавить нового преподавателя"
    updateButtonLabel="Добавить"
    :subjects="subjects"
    :tasks="tasks"
    :materials="materials"
    :rooms="rooms"
    :prompt="teacherPromptIsOpen"
    @update-click="addNewTeacher"
    @prompt-close="teacherPromptIsOpen = false"
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