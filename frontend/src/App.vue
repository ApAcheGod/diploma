<script setup lang="ts">
// This starter template is using Vue 3 <script setup> SFCs
// Check out https://v3.vuejs.org/api/sfc-script-setup.html#sfc-script-setup
import HelloWorld from './components/HelloWorld.vue'
import { onMounted, ref, shallowReactive } from 'vue';

let groups = ref();

onMounted(async () => { 
  console.log(await getStudents());

  console.log(await createStudent({
    first_name : 'Артем',
    last_name : 'Мирвода',
    patronymic : 'Валерьевич',
    login : 'mirvoda2',
    password : 'asdasd',
  }));
  
  console.log(await getStudents());
});
async function getStudents(){
  const header = {
    method: 'GET',
  };
  await fetch('http://localhost:8080/api/students', header)
    .then(res => res.json())
    .then(json => json)
    .catch(error => console.error(error));
}
async function createStudent(student){
  const header = {
    method: 'POST',
    body: JSON.stringify({
      first_name : student.first_name,
      last_name : student.last_name,
      patronymic : student.patronymic,
      login : student.login,
      password : student.password,
    }),
  };
  await fetch('http://localhost:8080/api/students', header)
    .then(res => res.json())
    .then(json => json)
    .catch(error => console.error(error));  
}
function updateStudnet(student){

}
function deleteStudent(student){

}
</script>

<template>
  <img alt="Vue logo" src="./assets/logo.png" />

  <HelloWorld msg="Hello Vue 3 + TypeScript + Vite" />
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
