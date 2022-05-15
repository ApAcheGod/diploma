<script setup>
import { ref } from 'vue';
import { useStore } from 'vuex'
import actionsTypes from  '../store/actionsTypes';

const store = useStore();
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
        <q-btn dense flat round label="Выйти" @click="store.dispatch(actionsTypes.USER_SIGNOUT, user)" />
      </q-toolbar>
    </q-header>
    
    <q-drawer v-model="leftDrawerOpen" side="left" overlay elevated>

      <q-list>
        <q-item class="mt-8 pl-8 text-sm flex items-center tracking-wider font-sans font-medium backdrop-blur-xl"  clickable v-ripple :to="{ name: 'AdminStudents' }">
          Студенты
        </q-item>
        <q-item class="pl-8 text-sm flex items-center tracking-wider font-sans font-medium backdrop-blur-xl" clickable v-ripple :to="{ name: 'AdminMaterials' }">
          Материалы
        </q-item>
        <q-item class="pl-8 text-sm flex items-center tracking-wider font-sans font-medium backdrop-blur-xl" clickable v-ripple :to="{ name: 'AdminSubjects' }">
          Предметы
        </q-item>
        <q-item class="pl-8 text-sm flex items-center tracking-wider font-sans font-medium backdrop-blur-xl" clickable v-ripple :to="{ name: 'AdminTeachers' }">
          Преподаватели
        </q-item>
        <q-item class="pl-8 text-sm flex items-center tracking-wider font-sans font-medium backdrop-blur-xl" clickable v-ripple :to="{ name: 'AdminTasks' }">
          Задания
        </q-item>
        <q-item class="pl-8 text-sm flex items-center tracking-wider font-sans font-medium backdrop-blur-xl" clickable v-ripple :to="{ name: 'AdminGroups' }">
          Группы
        </q-item>
        <q-item class="pl-8 text-sm flex items-center tracking-wider font-sans font-medium backdrop-blur-xl" clickable v-ripple :to="{ name: 'AdminRooms' }">
          Комнаты
        </q-item>
      </q-list>
    </q-drawer>
    
    <q-page-container class="row q-gutter-md m-6"> 
      <router-view />
    </q-page-container>
  
  </q-layout>
</template>
<style>

.q-drawer {
  background-color: rgba(170, 170, 170, 0.01);
  backdrop-filter: blur(14px);
}

.my-card {
  flex: 1 0 25%;
  display: flex;
  flex-direction: column;
}

.q-card__section {
  flex: 1 0 auto;
}

.q-card__actions {
  flex-grow: 0;
}
</style>