<script setup>
import { onMounted, ref, inject } from 'vue';
import { useQuasar } from 'quasar';
import { useStore } from 'vuex';
import actionsTypes from '../store/actionsTypes'
import mutationsTypes from '../store/mutationsTypes';
import methods from '../store/methods';

const $q = useQuasar();
const store = useStore();
const leftDrawerOpen = ref(true);

let teacherLogin = ref('');
let teacher = ref({})

const toggleLeftDrawer = () => {
  leftDrawerOpen.value = !leftDrawerOpen.value
}

onMounted(() => {
  teacherLogin.value = store.getters.getUserLogin;
  store.dispatch(actionsTypes.DATA_INIT, teacherLogin.value)
  .then(() => {
    teacher.value = store.getters.getUserData;
  })
  .catch(error => {
    console.error(error);
    $q.notify({
      type: 'negative',
      message: 'Ошибка при получении информации'
    });
  });


  // const room = {name : 'Тест1', teacherId : teacher.value.id};
  // let createdRoom = {};

  // teacherLogin.value = store.getters.getUserLogin;
  // store.dispatch(actionsTypes.DATA_INIT, teacherLogin.value)
  // .then (() => {
  //     teacher.value = store.getters.getUserData;
  //     console.log(teacher.value);
  //     console.log(store.getters.getTeacherStudentsGroups);
  //     console.log(store.getters.getUserSubjects);
  //     console.log(store.getters.getTeacherRooms);
  //   }
  // )
  // .then(() => {
  //   return store.dispatch(actionsTypes.CREATE_ROOM, room);
  // })
  // .then(() => {
  //   createdRoom = store.getters.getTeacherRooms.filter(r => r.name === room.name && r.teacherId === room.teacherId)[0];
  //   return store.dispatch(actionsTypes.DELETE_ROOM, createdRoom);

  // }).then(() => {
  //   return store.dispatch(actionsTypes.DELETE_ROOM, createdRoom)
  // })
  // .catch(() => console.log('Ошибка из колбека'));
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
        <router-link class="menu-container__link" :to="{ name: 'TeacherGroups' }">Группы студентов</router-link>
        <router-link class="menu-container__link" :to="{ name: 'TeacherSubjects' }">Предметы</router-link>
        <router-link class="menu-container__link" :to="{ name: 'TeacherMaterials' }">Материалы</router-link>
        <router-link class="menu-container__link" :to="{ name: 'TeacherJournal' }">Журнал</router-link>
      </nav>
      <button class="button-leave" @click="store.dispatch(actionsTypes.USER_SIGNOUT, user)">Выход</button>
    </q-drawer>

    <q-page-container>
      <div class="wrapper">
        <router-view />
      </div>

      <q-page-sticky position="bottom-left" :offset="[20, 20]">
        <q-btn fab class="bg-accent3" text-color="black" :icon="leftDrawerOpen ? 'keyboard_arrow_left' : 'keyboard_arrow_right'" @click="toggleLeftDrawer"/>
      </q-page-sticky>
    </q-page-container>

  </q-layout>

</template>

<style lang="scss">
.wrapper {
  display: flex;
  flex-direction: column;
}
.menu-container {
  display: flex;
  flex-direction: column;
  padding: 16px;
  background: white;

  font-family: 'Montserrat';
  color: #1d1d1d;

  &__navigation {
    flex-grow: 1;
    display: flex;
    flex-direction: column;
    gap: 8px;
  }

  &__link {
    font-size: 16px;
    font-weight: 500;
  }
}

.teacher-profile {
  margin-bottom: 24px;
  display: flex;
  flex-direction: column;
  justify-content: left;
  font-family: 'Montserrat';
  color:#1d1d1d;

  &__image {
    justify-self: left;
    align-self: flex-start;
    border-radius: 100%;
    object-fit: cover;
    height: 104px;
    width: 104px;
    margin-bottom: 16px;
  }

  &__name {
    font-size: 17px;
    font-weight: 500;
  }

  &__rank {
    align-self: left;
    font-size: 14px;
    font-weight: 400;
  }
}

.button-leave {
  align-self: center;
  font-family: 'Montserrat';
  color:#1d1d1d;
  font-weight: 600;
  padding: 12px 16px;
  text-transform: uppercase;
  background: #03DAC5;
  box-shadow: 0px 4px 5px rgba(0, 0, 0, 0.14), 0px 1px 10px rgba(0, 0, 0, 0.12), 0px 2px 4px rgba(0, 0, 0, 0.2);
  border-radius: 50px;
  transition: .3s;

  &:hover {
    background: rgba(3, 151, 135, 0.8);
    transition: .3s;
  }
}

</style>