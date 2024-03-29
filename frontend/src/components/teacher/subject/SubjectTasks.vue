<script setup>
import { computed, ref } from "vue";
import { useStore } from 'vuex';
import { useQuasar } from "quasar";

import BaseCard from '../../base/BaseCard.vue';
import BaseCardWrapper from '../../base/BaseCardWrapper.vue';
import BaseAddNew from '../../base/BaseAddNew.vue';
import BaseDialog from "../../base/BaseDialog.vue";
import BaseRichText from "../../base/BaseRichText.vue";

import methods from "../../../store/methods";
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
  if (!task.id) {
    q.notify({
      type: 'negative',
      message: 'Системная ошибка при обновлении задания',
    });
    return; 
  }
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

  if (!task.subjectId || !task.teacherId) {
    q.notify({ type: 'negative', message: 'Системная ошибка при обновлении задания', });
    return; 
  }

  if (!task.name) {
    q.notify({ type: 'negative', message: 'Имя задание не может быть пустым', });
    return; 
  }

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


</script>
<template>
  <base-card-wrapper>

    <template #items>
      <base-card v-for="task in activeSubject?.tasks" :key="task.id">
        <template #title>
          {{task.name}}
          <div class="base-card__subtitle">{{methods.dateFormated(task.date_of_creation)}}</div>
        </template>
        <template #body>
          <q-card-section v-html="task.text" />
        </template>
        <template #actions>
          <button class="base-card__button base-card__button_edit" @click="() => {promptType = promptTypes.EDIT; newTask = {...task}; promptIsOpen = true;}">Изменить</button>
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
      
      <base-dialog 
        :title="promptTitle"
        :promptIsOpen="promptIsOpen"
        v-on:change-open-status="promptIsOpen = !promptIsOpen"
        >
        <template #body>
          <q-input padding="8px" dense v-model="newTask.name" autofocus label="Название" :rules="[val => !!val || 'Название обязательно']"/>
          <base-rich-text v-model="newTask.text" />
        </template>
        <template #actions>
          <q-btn class="base-card__button" padding="8px" flat label="Отмена" @click="() => {promptIsOpen=false; clearActiveSubject();}"/>
          <q-btn class="base-card__button" padding="8px" flat :label="promptActionName" :disable="!newTask.name" @click="promptAction(newTask)" />
        </template>
      </base-dialog>

    </template>
  </base-card-wrapper>
</template>
<style lang="scss" scoped>
</style>