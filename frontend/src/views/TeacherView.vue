<script setup>
import { onMounted, ref } from 'vue';

const leftDrawerOpen = ref(false);
let tab = ref();

const toggleLeftDrawer = ()=> {
  leftDrawerOpen.value = !leftDrawerOpen.value
}

onMounted(() => {

})
</script>

<template>
  <q-layout view="hhh lpR fFf">

    <q-header reveal elevated class="bg-primary text-white">
      <q-toolbar>
        <q-btn dense flat round icon="menu" @click="toggleLeftDrawer" />

        <q-toolbar-title>
          {{state?.teacher?.login}}
        </q-toolbar-title>
      </q-toolbar>
    </q-header>

    <q-drawer show-if-above v-model="leftDrawerOpen" side="left" elevated>
      <q-list bordered separator>
        <q-item clickable v-ripple>
          <router-link :to="{ name: 'TeacherRooms' }">Комнаты</router-link>
        </q-item>

        <q-item clickable v-ripple>
          <router-link :to="{ name: 'TeacherProfile' }">Личный кабинет</router-link>
        </q-item>

      </q-list>
    </q-drawer>

    <q-page-container>
      <router-view />
    </q-page-container>

  </q-layout>
</template>

<style scoped>

</style>