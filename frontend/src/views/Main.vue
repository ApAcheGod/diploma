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

async function updateStudent(newStudent){
  let updateResult = await updateStudentFetch({
    id: newStudent.id,
    first_name: newStudent.first_name,
    last_name: newStudent.last_name,
    patronymic: newStudent.patronymic,
  });
  if(updateResult){
    students.value = await getStudentsFetch();
    triggerPositive('Информация о студенте успешно обновлена!');
  }
  else
    triggerNegative('Не удалось обновить информацию о студенте');
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
    triggerPositive('Успешно добавлен новый студент!');
  }
  else
    triggerNegative('Не удалось добавить студента');
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
    headers: {
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
  <q-layout view="hHh lpR fFf">
    
    <q-header class="bg-primary text-white">
      <q-toolbar>
        <q-btn dense flat round icon="menu" @click="toggleLeftDrawer" />
        <q-toolbar-title>
          Студенты
        </q-toolbar-title>
        <router-link to="/teacher">Учитель</router-link>
      </q-toolbar>
    </q-header>
    
    <q-drawer v-model="leftDrawerOpen" side="left" overlay elevated>

      <q-list bordered separator>
        <q-item clickable v-ripple>
          <q-item-section>Студенты</q-item-section>
        </q-item>

        <q-item clickable v-ripple>
          <q-item-section>
            <q-item-label>Материалы</q-item-label>
          </q-item-section>
        </q-item>

        <q-item clickable v-ripple>
          <q-item-section>
            <q-item-label>Задания</q-item-label>
          </q-item-section>
        </q-item>
      </q-list>
      <!-- <q-btn dense flat round icon="close" @click="toggleLeftDrawer" /> -->
    </q-drawer>
    
    <q-page-container class="q-pa-md row items-start q-gutter-md">
      <!-- <router-view /> -->
      
      <transition-group name="list" >
        <StudentCard v-for="student in students"
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
          @click="addNewFixedStudent"/>
      </q-page-sticky>
    </q-page-container>
  
  </q-layout>
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