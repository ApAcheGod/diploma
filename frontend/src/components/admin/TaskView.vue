<script setup>
import {inject, onMounted, ref} from 'vue';
import {useQuasar} from 'quasar';
import TaskCard from './TaskCard.vue';
import TaskDialog from './TaskDialog.vue';

let $q = useQuasar();

let tasks = ref();
let subjects = ref();
let groups = ref();
let teachers = ref ();
let solutions = ref();

const store = inject('store');

let taskPromptIsOpen = ref(false);

onMounted(async () => { 
  Promise.allSettled([
    store.methods.getTasksFetch(),
    store.methods.getSubjectsFetch(),
    store.methods.getGroupsFetch(),
    store.methods.getTeachersFetch(),
    store.methods.getSolutionsFetch(),
    ])
  .then((results) => {
    tasks.value = results[0].value;
    subjects.value = results[1].value;
    groups.value = results[2].value;
    teachers.value = results[3].value;
    solutions.value = results[4].value;
  });
});

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
  let updateResult = await store.methods.updateTaskFetch(newTask);
  if(updateResult){
    tasks.value = await store.methods.getTasksFetch();
    triggerPositive('Информация о задании успешно обновлена!');
  }
  else
    triggerNegative('Не удалось обновить информацию о задании');
}

async function addNewTask(newTask){
  const createResult = await store.methods.createTaskFetch(newTask);
  if (createResult) {
    tasks.value = await store.methods.getTasksFetch();
    triggerPositive('Успешно добавлено новое задание!');
  }
  else
    triggerNegative('Не удалось добавить задание');
}

async function deleteTask(task){
  const deleteResult = await store.methods.deleteTaskFetch(task);
  if(deleteResult){
    tasks.value = await store.methods.getTasksFetch();
    triggerPositive('Информация о задании успешно удалена!')
  }
  else
    triggerNegative('Не удалось удалить информацию о задании')
}

</script>
<template>
  <transition-group name="list" >
    <task-card v-for="task in tasks"
      v-bind:key="task?.id"
      :task="task"
      :subjects="subjects"
      :groups="groups"
      :teachers="teachers"
      :solutions="solutions"
      @delete-click="deleteTask"
      @update-click="updateTask"
    />
  </transition-group>

  <q-page-sticky position="bottom-right" :offset="[20, 20]">
    <q-btn 
      fab 
      icon="add" 
      color="accent"
      @click="taskPromptIsOpen = true"/>
  </q-page-sticky>

  <task-dialog
    updateButtonLabel="Добавить"
    :subjects="subjects"
    :groups="groups"
    :teachers="teachers"
    :solutions="solutions"
    :prompt="taskPromptIsOpen"
    @update-click="addNewTask"
    @prompt-close="taskPromptIsOpen = false"
  />
</template>
<style scoped>

.list-item {
  display: inline-block;
  margin-right: 8px;
}

.list-enter-active,
.list-leave-active {
  transition: all 0.3s ease;
}

.list-enter-from,
.list-leave-to {
  opacity: 0;
  transform: translateY(20px);
}

</style>