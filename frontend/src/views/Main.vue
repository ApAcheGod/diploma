<script setup>
import { onMounted, ref, shallowReactive } from 'vue';
import { useQuasar } from 'quasar'

import StudentCard from '../components/StudentCard.vue';
import StudentDialog from '../components/StudentDialog.vue';


let groups = ref();
let students = ref();
let leftDrawerOpen = ref(false)

let studentPromptOpen = ref(false);
let studentPrompt = ref();

const $q = useQuasar();

const triggerPositive = (msg) => {
  $q.notify({
    type: 'positive',
    message: msg
  })
}

const triggerNegative = (msg) => {
  $q.notify({
    type: 'negative',
    message: msg
  })
}

const toggleLeftDrawer = () => {
  leftDrawerOpen.value = !leftDrawerOpen.value
}

onMounted(async () => { 
  students.value = (await Promise.allSettled([getStudentsFetch()]))[0].value;

});

function updateStudent(){

}

async function addNewFixedStudent(){
  const newStudent = {
    first_name: 'Артем',
    last_name: 'Мирвода',
    patronymic: 'Валерьевич',
    email: 'mirvodartem@gmail.com'
  }
  const createResult = await createStudentFetch(newStudent);
  if (createResult) {
    students.value = await getStudentsFetch();
    triggerPositive('Успешно добавлен новый студент!')
  }
  else
    triggerNegative('Не удалось добавить студента')
}

async function deleteStudent(student){
  const deleteResult = await deleteStudentFetch(student);
  if(deleteResult){
    students.value = await getStudentsFetch();
    triggerPositive('Информация о студенте успешно удалена!')
  }
  else
    triggerNegative('Не удалось удалить информацию о студенте')
}

function getStudentsFetch(){
  const header = {
    method: 'GET',
  };
  return fetch('http://localhost:8080/api/students', header)
    .then(res => res.json())
    .then(json => json)
    .catch(error => console.error(error));
}
function createStudentFetch(student){
  const header = {
    method: 'POST',
    headers: {
    'Content-Type': 'application/json;charset=utf-8',
    'Accept': 'application/json'
    },
    body: JSON.stringify(student),
  };
  return fetch('http://localhost:8080/api/student', header)
    .then(res => res.ok)
    .catch(error => console.error(error));
}
function updateStudentFetch(student){
  const header = {
    method: 'PUT',
    header: {
      'Content-Type': 'application/json;charset=utf-8',
      'Accept': 'application/json'
    },
    body: JSON.stringify(student),
  };
  return fetch(`http://localhost:8080/api/student/${student.id}`, header)
    .then(res => res.ok)
    .catch(error => console.error(error));
}
function deleteStudentFetch(student){
  const header = {
    method: 'DELETE',
  };
  return fetch(`http://localhost:8080/api/student/${student.id}`, header)
    .then(res => res.ok)
    .catch(error => console.error(error));
}

</script>
<template>
  <q-layout view="hHh LpR fFf">
    <q-header class="bg-primary text-white">
      <q-toolbar>
        <q-btn dense flat round icon="menu" @click="toggleLeftDrawer" />
        <q-toolbar-title>
          Студенты
        </q-toolbar-title>
      </q-toolbar>
    </q-header>
    <q-drawer v-model="leftDrawerOpen" side="left" overlay>
      <q-btn dense flat round icon="close" @click="toggleLeftDrawer" />
    </q-drawer>
    <q-page-container class="q-pa-md row items-start q-gutter-md">
      <router-view />
      <StudentCard v-for="student in students"
        :key="student.id"
        :student="student"
        @delete-click="deleteStudent"
        @update-click="(student) => studentPrompt = student"
      />
      <q-page-sticky position="bottom-right" :offset="[20, 20]">
        <q-btn 
          fab 
          icon="add" 
          color="accent"
          @click="addNewFixedStudent"/>
      </q-page-sticky>
    </q-page-container>
  </q-layout>
  <StudentDialog
    :prompt="!!studentPrompt"
    @update-click=""/>
</template>
<style scoped></style>