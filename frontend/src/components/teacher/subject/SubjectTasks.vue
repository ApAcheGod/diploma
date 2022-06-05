<script setup>
import { computed, ref } from "vue";
import { useStore } from 'vuex';

import BaseCard from '../../base/BaseCard.vue';
import BaseCardWrapper from '../../base/BaseCardWrapper.vue';
import BaseAddNew from '../../base/BaseAddNew.vue';
import TaskDialog from '../../admin/TaskDialog.vue';


import actionsTypes from "../../../store/actionsTypes";
import methods from "../../../store/methodsAdmin";

const store = useStore();
const activeSubject = computed(() => 
  store.getters.getActiveSubject
);

let taskPromptIsOpen = ref(false);

const newTask = ref({
  name : "",
  text: "",
  subjectId : activeSubject.value.id,
  teacherID : activeSubject.value.teacherId,
});

// const newTaskFormatted = computed(() => {
//   let newTaskFormatted = JSON.parse(JSON.stringify(newTask.value));
//
//   newTaskFormatted.groups = methods.idArrToObjs(newTaskFormatted.groups);
//   newTaskFormatted.solutions = methods.idArrToObjs(newTaskFormatted.solutions);
//
//   return newTaskFormatted;
// });

function triggerPositive(msg) {
  $q.notify({
    type: 'positive',
    message: msg
  })
}

function triggerNegative(msg) {
  $q.notify({
    type: 'negative',
    message: msg
  })
}

async function updateTask(newTask){
  console.log("update task")
  // let updateResult = await methods.updateTaskFetch(newTask);
  // if(updateResult){
  //   tasks.value = await methods.getTasksFetch();
  //   triggerPositive('Информация о задании успешно обновлена!');
  //   subjectPromptIsOpen.value = false;
  // }
  // else
  //   triggerNegative('Не удалось обновить информацию о задании');
}

async function addNewTask(newTask){
  const createResult = await methods.createTaskFetch(newTask);
  if (createResult) {
    tasks.value = await methods.getTasksFetch();
    triggerPositive('Успешно добавлено новое задание!');
  }
  else
    triggerNegative('Не удалось добавить задание');
}

async function deleteTask(task){
  console.log("delete task")
  // const deleteResult = await methods.deleteTaskFetch(task);
  // if(deleteResult){
  //   tasks.value = await methods.getTasksFetch();
  //   triggerPositive('Информация о задании успешно удалена!')
  // }
  // else
  //   triggerNegative('Не удалось удалить информацию о задании')
}

</script>
<template>
  <base-card-wrapper>
      <!-- <template #title>
      <hr/>
      {{room?.roomName}}
    </template> -->
    <template #items>
      <base-card v-for="task in activeSubject?.tasks" :key="task.id">
        <template #title>
          {{task.name}}
        </template>
        <template #body>
          <!-- {{group.students.length}} -->
        </template>
        <template #actions>
          <button class="base-card__button base-card__button_edit" @click="() => updateTask(task)">Изменить</button>
          <button class="base-card__button base-card__button_delete" @click="() => deleteTask(task)">Удалить</button>
        </template>
      </base-card>
      <base-add-new key="add-new">
        <template #body>
          <button class="base-card_add__button" @click="taskPromptIsOpen = true">
            <img class="room-card-add__icon" src="../../../img/add.svg"/>
          </button>
<!--          <task-dialog-->
<!--              updateButtonLabel="Добавить"-->
<!--              :subjects="subjects"-->
<!--              :teachers="teachers"-->
<!--              :solutions="solutions"-->
<!--              :prompt="taskPromptIsOpen"-->
<!--              @update-click="addNewTask"-->
<!--              @prompt-close="taskPromptIsOpen = false"-->
<!--          />-->

          <q-dialog v-model="taskPromptIsOpen" persistent>
            <q-card style="min-width: 350px" @keyup.esc="taskPromptIsOpen=false" @keyup.enter="updateTask(newTask)">
              <q-card-section>
                <div class="text-h5">Редактирование</div>
              </q-card-section>

              <q-card-section class="q-pt-none">
                <q-input dense v-model="newTask.name" autofocus label="Название"/>
              </q-card-section>

              <q-card-section class="q-pt-none">
                <q-input dense autogrow v-model="newTask.text" autofocus label="Текст задания"/>
              </q-card-section>

              <q-card-actions align="right" class="text-primary">
                <q-btn flat label="Отмена"  @click="emits('prompt-close')"/>
                <q-btn flat label="хузй" @click="addNewTask(newTask)" />
              </q-card-actions>
            </q-card>
          </q-dialog>


        </template>
      </base-add-new>
    </template>
  </base-card-wrapper>
</template>
<style lang="scss" scoped>
</style>