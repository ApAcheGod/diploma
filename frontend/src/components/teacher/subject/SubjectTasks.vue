<script setup>
import { computed, ref } from "vue";
import { useStore } from 'vuex';
import { useQuasar } from "quasar";

import BaseCard from '../../base/BaseCard.vue';
import BaseCardWrapper from '../../base/BaseCardWrapper.vue';
import BaseAddNew from '../../base/BaseAddNew.vue';

import actionsTypes from "../../../store/actionsTypes";

const q = useQuasar();
const store = useStore();
const activeSubject = computed(() => store.getters.getActiveSubject);

const promptTypes = Object.freeze({
  NEW : 'NEW', EDIT : 'EDIT',
})
const _prompt = Object.freeze({
  [promptTypes.NEW] : {
    title : 'Добавление задания',
    actionName : 'Добавить',
    action : addNewTask,
  },
  [promptTypes.EDIT] : {
    title : 'Редактирование задания',
    actionName : 'Редактировать',
    action : updateTask,
  },
});

const promptType = ref(promptTypes.NEW);
const promptIsOpen = ref(false);

const promptTitle = computed(() => _prompt[promptType.value].title);
const promptActionName = computed(() => _prompt[promptType.value].actionName);
const promptAction = computed(() => _prompt[promptType.value].action);

const newTask = ref({});
const triggerPositive = (msg) => q.notify({ type: 'positive', message: msg });
const triggerNegative = (msg) => q.notify({ type: 'negative', message: msg });
const clearActiveSubject = () => newTask.value = { name : '', text: '', subjectId : '', teacherId : '' };

function updateTask(task){
  store.dispatch(actionsTypes.UPDATE_TASK, task)
    .then(() => {
      triggerPositive('Успешно обновлено новое задание!');
      promptIsOpen.value = false;
    })
    .catch((error) => {
      console.error(error);
      triggerNegative('Не удалось обновить задание')
    });
}
function addNewTask(task){
  task.subjectId = activeSubject.value.id;
  task.teacherId = activeSubject.value.teacherId;
  store.dispatch(actionsTypes.CREATE_TASK, task)
    .then(() => {
      triggerPositive('Успешно добавлено новое задание!');
      promptIsOpen.value = false;
    })
    .catch((error) => {
      console.error(error);
      triggerNegative('Не удалось добавить задание');
    });
}
function deleteTask(task){
  store.dispatch(actionsTypes.DELETE_TASK, task)
    .then(() => {
      triggerPositive('Успешно удалено задание!');
      promptIsOpen.value = false;
    })
    .catch((error) => {
      console.error(error);
      triggerNegative('Не удалось удалить задание');
    });
}
function dateFormated(dateAtString){
  const parsedDate = Date.parse(dateAtString);
  if (isNaN(parsedDate)) return;
  const publicAtRuLocale = 'Добавлено';
  const now = new Date();
  const date = new Date(parsedDate);
  const dayDiff = Math.abs(now.getDay() - date.getDay());
  if (now.getMonth() !== date.getMonth() || now.getFullYear() !== date.getFullYear() || dayDiff > 2)
    return `${publicAtRuLocale}: ${date.toLocaleDateString()} ${date.toLocaleTimeString()}`;
  if (dayDiff === 2) 
    return `${publicAtRuLocale}: позавчера ${date.toLocaleTimeString()}`;
  if (dayDiff === 1) 
    return `${publicAtRuLocale}: вчера ${date.toLocaleTimeString()}`;
  return `${publicAtRuLocale}: сегодня ${date.toLocaleTimeString()}`;
}

</script>
<template>
  <base-card-wrapper>

    <template #items>
      <base-card v-for="task in activeSubject?.tasks" :key="task.id">
        <template #title>
          {{task.name}}
          <div class="base-card__subtitle">{{dateFormated(task.date_of_creation)}}</div>
        </template>
        <template #body>

          {{task.text}}
        </template>
        <template #actions>
          <button class="base-card__button base-card__button_edit" @click="() => {promptType = promptTypes.EDIT; newTask = task; promptIsOpen = true;}">Изменить</button>
          <button class="base-card__button base-card__button_delete" @click="deleteTask(task)">Удалить</button>
        </template>
      </base-card>
      
      <base-add-new key="add-new">
        <template #body>
          <button class="base-card_add__button" @click="clearActiveSubject(); promptType = promptTypes.NEW; promptIsOpen = true">
            <img class="room-card-add__icon" src="../../../img/add.svg"/>
          </button>
        </template>
      </base-add-new>
      
      <q-dialog v-model="promptIsOpen" persistent>
        <q-card style="min-width: 350px" @keyup.esc="promptIsOpen=false" @keyup.enter="updateTask(newTask)">
          <q-card-section>
            <div class="text-h5">{{promptTitle}}</div>
          </q-card-section>

          <q-card-section class="q-pt-none">
            <q-input dense v-model="newTask.name" autofocus label="Название"/>
          </q-card-section>

          <q-card-section class="q-pt-none">
            <q-input dense autogrow v-model="newTask.text" autofocus label="Текст задания"/>
          </q-card-section>

          <q-card-actions align="right" class="text-primary">
            <q-btn flat label="Отмена"  @click="() => {promptIsOpen=false; clearActiveSubject();}"/>
            <q-btn flat :label="promptActionName" @click="promptAction(newTask)" />
          </q-card-actions>
        </q-card>
      </q-dialog>
    </template>
  </base-card-wrapper>
</template>
<style lang="scss" scoped>
</style>