<script setup>
import { ref } from 'vue';
import { useStore } from 'vuex';
import actionsTypes from  '../store/actionsTypes';

let user = ref({});
const state = useStore();
const showingPassword = ref(true);

</script>
<template>
<div class="sign-in__block">
  <div class="sign-in__form">
    <h2 class="sign-in__title">Вход</h2>
    <q-input 
      outlined 
      v-model="user.login" 
      label="Логин" 
      hint="Введите логин"/>
    <q-input 
      outlined 
      v-model="user.password" 
      label="Пароль" 
      hint="Введите пароль"
      :type="showingPassword ? 'password' : 'text'" >
      <template v-slot:append>
        <q-icon
          :name="showingPassword ? 'visibility_off' : 'visibility'"
          class="cursor-pointer"
          @click="showingPassword = !showingPassword"
        />
      </template>
    </q-input>  
    <q-btn flat class="sign-in__button" label="Войти" @click="state.dispatch(actionsTypes.USER_SIGNIN, user)"/>
  </div>
</div>
</template>
<style>
.sign-in__block {
  margin-top: 50px;
  display: grid;
  place-content: center center;
}
.sign-in__form {
  display: grid;
  font-family: 'Montserrat', sans-serif;
  gap: 24px;
  width: min(32vw, 400px);
}
.sign-in__title {
  font-weight: 700;
  font-size: 32px;
}
.sign-in__button {
  width: 8vw;
  justify-self: end;
}
</style>