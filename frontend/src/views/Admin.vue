<script setup>
import { onMounted, ref, inject } from 'vue';

const store = inject('store');

let leftDrawerOpen = ref(false)

const toggleLeftDrawer = () => {
  leftDrawerOpen.value = !leftDrawerOpen.value
}

</script>
<template>
  <q-layout view="hHh lpR fFf">
    
    <q-header class="bg-primary text-white">
      <q-toolbar>
        <q-btn dense flat round icon="menu" @click="toggleLeftDrawer" />
        <q-separator dark vertical inset class="ml-3 mr-2"/>
        <q-toolbar-title>
          Администратор
        </q-toolbar-title>
        <router-link to="/teacher">Учитель</router-link>
      </q-toolbar>
    </q-header>
    
    <q-drawer class="backdrop-blur-sm" v-model="leftDrawerOpen" side="left" overlay elevated>

      <q-list bordered separator>
        <q-item clickable v-ripple :to="{ name: 'AdminStudents' }">
          Студенты
        </q-item>

        <q-item clickable v-ripple :to="{ name: 'AdminMaterials' }">
          Материалы
        </q-item>

        <q-item clickable v-ripple :to="{ name: 'AdminSubjects' }">
          <q-item-label>Предметы</q-item-label>
        </q-item>

        <q-item clickable v-ripple :to="{ name: 'AdminTeachers' }">
          <q-item-label>Преподаватели</q-item-label>
        </q-item>

        <q-item clickable v-ripple :to="{ name: 'AdminTasks' }">
          <q-item-label>Задания</q-item-label>
        </q-item>

        <q-item clickable v-ripple :to="{ name: 'AdminGroups' }">
          <q-item-label>Группы</q-item-label>
        </q-item>

        <q-item clickable v-ripple :to="{ name: 'AdminRooms' }">
          <q-item-label>Комнаты</q-item-label>
        </q-item>

      </q-list>
    </q-drawer>
    
    <q-page-container class="q-pa-md row items-start q-gutter-md m-1"> 
      <router-view />
    </q-page-container>
  
  </q-layout>
</template>
<style>
.my-card {
  flex-basis: 30%;
}
</style>