<script setup lang="ts">
// This starter template is using Vue 3 <script setup> SFCs
// Check out https://v3.vuejs.org/api/sfc-script-setup.html#sfc-script-setup
import HelloWorld from './components/HelloWorld.vue'
import { onMounted, ref, shallowReactive } from 'vue';

let groups = ref();

onMounted(async () => { 
  console.log(await getStudents());

  console.log(await createStudent({
    student: {
      first_name: 'Артем',
      last_name: 'Мирвода',
      patronymic: 'Валерьевич',
      email: '@email.ru',
      groupId: '86bac443-c896-4cf9-b45e-bf7956d68a19'
    }
  }));

  console.log(await getStudents());

  console.log(await updateStudent({
    student: {
      id: 'f8198222-5e3f-4a5e-aadf-673e9c6e904d',
      first_name: 'Артем',
      last_name: 'Мирвода',
      patronymic: 'Валерьевич',
      email: 'asd@email.ru',
      groupId: '86bac443-c896-4cf9-b45e-bf7956d68a19'
    }
  }))

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

async function createStudent({student}){
  const header = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json;charset=utf-8',
      'Accept': 'application/json'
    },
    body: JSON.stringify(student),
  };
  await fetch('http://localhost:8080/api/student', header)
    .then(res => res.json())
    .then(json => json)
    .catch(error => console.error(error));
}
async function updateStudent(student){
  const header = {
    method: 'PUT',
    header: {
      'Content-Type': 'application/json;charset=utf-8',
      'Accept': 'application/json'
    },
    body: JSON.stringify(student),
  };
  await fetch('http://localhost:8080/api/student/f8198222-5e3f-4a5e-aadf-673e9c6e904d', header)
      .then(res => res.json())
      .then(json => json)
      .catch(error => console.error(error));
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
