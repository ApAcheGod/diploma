<script setup>
import {inject, onMounted, ref} from 'vue';
import {useQuasar} from 'quasar'
import axios from 'axios';
import StudentCard from './StudentCard.vue';
import StudentDialog from './StudentDialog.vue';

let $q = useQuasar();

let students = ref();

const methods = inject('methods');

let studentPromptIsOpen = ref(false);

onMounted(async () => {
  students.value = (await Promise.allSettled([methods.getStudentsFetch()]))[0].value;

  methods.userLoginAxios({
    "login": "BGruStudent",
    "password":"nxup1Z9rAw"
  })
  .then((loginResponse) => {
    console.log(`data - ${JSON.stringify(loginResponse.data)}`);
    console.log(`token - ${loginResponse.config.headers['X-XSRF-TOKEN']}`)
  })
  .then(() => methods.userCheckAxios())
  .then((checkResponse) => {
    console.log(`data - ${JSON.stringify(checkResponse.data)}`);
    console.log(`token - ${checkResponse.config.headers['X-XSRF-TOKEN']}`)
  })
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

async function updateStudent(newStudent){
  let updateResult = await methods.updateStudentFetch({
    id: newStudent.id,
    first_name: newStudent.first_name,
    last_name: newStudent.last_name,
    patronymic: newStudent.patronymic,
    email: newStudent.email
  });
  if(updateResult){
    students.value = await methods.getStudentsFetch();
    triggerPositive('Информация о студенте успешно обновлена!');
    studentPromptIsOpen.value = false;
  }
  else
    triggerNegative('Не удалось обновить информацию о студенте');
}

async function addNewStudent(newStudent){
  const createResult = await methods.createStudentFetch(newStudent);
  if (createResult) {
    students.value = await methods.getStudentsFetch();
    triggerPositive('Успешно добавлен новый студент!');
    studentPromptIsOpen.value = false;
  }
  else
    triggerNegative('Не удалось добавить студента');
}

async function deleteStudent(student){
  const deleteResult = await methods.deleteStudentFetch(student);
  if(deleteResult){
    students.value = await methods.getStudentsFetch();
    triggerPositive('Информация о студенте успешно удалена!')
  }
  else
    triggerNegative('Не удалось удалить информацию о студенте')
}

</script>
<template>
  <transition-group name="list" >
    <student-card v-for="student in students"
      v-bind:key="student?.id"
      :student="student"
      @delete-click="deleteStudent"
      @update-click="updateStudent"
    />
  </transition-group>

  <q-page-sticky position="bottom-right" :offset="[20, 20]">
    <q-btn 
      fab 
      icon="add" 
      color="accent"
      @click="studentPromptIsOpen = true"/>
  </q-page-sticky>

  <student-dialog
    :prompt="studentPromptIsOpen"
    updateButtonLabel="Добавить"
    @update-click="addNewStudent"
    @prompt-close="studentPromptIsOpen = false"
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