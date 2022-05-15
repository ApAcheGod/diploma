<script setup>
import { onMounted, ref, inject } from 'vue';
import { useStore } from 'vuex';
import mutationsTypes from '../store/mutationsTypes';

const store = useStore();
const leftDrawerOpen = ref(true);
const methods = inject('methods');
let teacherLogin = ref('');
let teacher = ref({})

const toggleLeftDrawer = () => {
  leftDrawerOpen.value = !leftDrawerOpen.value
}

onMounted(() => {
  teacherLogin.value = store.getters.getUserLogin;
  methods.getTeacherByLoginFetch(teacherLogin.value)
  .then(teacherData => {
    console.log(teacherData);
    teacher.value = teacherData;
    store.commit(mutationsTypes.SET_TEACHER, teacherData);
  });
})
</script>

<template>
  <q-layout view="lHh lpR lFf">
    <q-drawer class="menu-container" v-model="leftDrawerOpen" side="left" elevated>
      <div class="teacher-profile">
        <img class="teacher-profile__image" src="../img/teacher.jpg"/>
        <div class="teacher-profile__name">
          {{teacher.teacherName}}
        </div>
        <div class="teacher-profile__rank">старший преподаватель</div>
      </div>
      <nav class="menu-container__navigation">
        <router-link class="menu-container__link" :to="{ name: 'TeacherMain' }">Главная</router-link>
      </nav>
     
      <!-- drawer content -->
    </q-drawer>

    <q-page-container>
      <router-view />
      <q-page-sticky position="bottom-right" :offset="[20, 20]">
        <q-btn fab color="amber" text-color="black" icon="keyboard_arrow_right" direction="left" @click="toggleLeftDrawer"/>
      </q-page-sticky>
    </q-page-container>

  </q-layout>

</template>

<style>
.menu-container {
  padding: 16px;
  background: white;
}
.menu-container__navigation {

}
.menu-container__link {
  font-family: 'Montserrat';
  color: #1d1d1d;
  font-size: 16px;
  font-weight: 500;
}
.teacher-profile {
  margin-bottom: 24px;
  display: flex;
  flex-direction: column;
  justify-content: left;
}
.teacher-profile__image {
  justify-self: left;
  align-self: flex-start;
  border-radius: 100%;
  object-fit: cover;
  height: 104px;
  width: 104px;
  margin-bottom: 16px;
}
.teacher-profile__name {
  font-family: 'Montserrat';
  color:#1d1d1d;
  font-size: 17px;
  font-weight: 500;
}
.teacher-profile__rank {
  align-self: left;
  font-family: 'Montserrat';
  color:#1d1d1d;
  font-size: 14px;
  font-weight: 400;
}
</style>