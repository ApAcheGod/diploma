<script setup>
import { computed } from '@vue/reactivity';
import { useQuasar } from 'quasar';
import { useStore } from 'vuex';
import { ref } from 'vue';

import BaseCard from '../../base/BaseCard.vue';
import BaseDialog from '../../base/BaseDialog.vue';
import BaseRichText from '../../base/BaseRichText.vue';
import BaseCardWrapper from '../../base/BaseCardWrapper.vue';
import EmptyMessage from '../../base/EmptyMessage.vue';
import actionsTypes from '../../../store/actionsTypes';
import workStatuses from '../../../models/workStatuses';
import methods from '../../../store/methods';

const q = useQuasar();
const store = useStore();
const tasks = computed(() => store.getters.getActiveSubjectFormattedTasks);
const user = computed(() => store.getters.getUserData);
const examinations = computed(() => store.getters.getExamsMapByStudentIdTaskId);

const findExamination = () => {
  try{
    return JSON.parse(JSON.stringify(...examinations.value.get(user.value.id).get(currentTask.value.id))).comment;
  }catch (e){
    return "";
  }
};

const promptTypes = Object.freeze({SOLVE: 'SOLVE', READONLY: 'READONLY'});
const _prompt = Object.freeze({
  [promptTypes.SOLVE] : {
    solutionIsReadOnly : false,
    showActionButton : true,
  },
  [promptTypes.READONLY] : {
    solutionIsReadOnly : true,
    showActionButton : false,
  },
});

const promptSolutionIsReadOnly = computed(() => _prompt[promptType.value].solutionIsReadOnly);
const promptShowAction = computed(() => _prompt[promptType.value].showActionButton);
const promptType = ref(prompt.READONLY);
const promptIsOpen = ref(false);

const currentTask = ref({});
const newSolutionText = ref('');

const createSolution = () => {
  const solution = {
    text: newSolutionText.value,
    taskId: currentTask.value.id,
    studentId: user.value.id,
  };

  if (!solution.taskId || !solution.studentId) {
    q.notify({
      type: 'negative',
      message: 'Ошибка системы при сдачи задания',
    });
    return; 
  }

  store.dispatch(actionsTypes.CREATE_SOLUTION, solution)
    .then(() => {
      q.notify({
        type: 'positive',
        message: 'Задание успешно сдано'
      });
      promptIsOpen.value=false;
    })
    .catch((error) => {
      console.error(error);
      q.notify({
        type: 'negative',
        message: 'Ошибка при сдачи задания',
      });
    });
}

</script>
<template>
  <base-card-wrapper v-if="tasks && tasks.length > 0">
    <template #items>
      <base-card v-for="task in tasks" :key="task.id">
        <template #title>
          {{task.name}}
          <div class="base-card__subtitle">{{methods.dateFormated(task.date_of_creation)}}</div>
          <q-badge class="text-transforn-none" :color="methods.getBadgeColor(task.status)" :label="task.status" />
        </template>
        <template #body>
          <q-card-section v-html="task.text" />
        </template>
        <template #actions>
          <button 
            v-if="task.status === workStatuses.NOT_COMPLETED" 
            class="base-card__button"
            @click="promptType=promptTypes.SOLVE; currentTask=task; currentTask.solution = {}, newSolutionText=''; promptIsOpen=true;"
          >
            Сдать
          </button>
          <button 
            v-else 
            class="base-card__button"
            @click="promptType=promptTypes.READONLY; currentTask=task; newSolutionText=currentTask.solution?.text, comment=findExamination(); promptIsOpen=true;"
          >
            Просмотр решения
          </button>
        </template>
      </base-card>
    </template>
  </base-card-wrapper>

  <empty-message v-else>
    Нет доступных заданий по этому предмету
  </empty-message>

  <base-dialog 
    :title="currentTask.name"
    :promptIsOpen="promptIsOpen"
    v-on:change-open-status="promptIsOpen = !promptIsOpen"
    >
    <template #body>
      <div>{{methods.dateFormated(currentTask.date_of_creation)}}</div>
      <div>Задание:</div>
      <q-card-section v-html="currentTask.text" />
      <template v-if="promptSolutionIsReadOnly">
        <div>Решение:</div>
        <q-card-section  v-html="newSolutionText" />
        <div v-if="comment !== ''">Комментарий преподавателя:</div>
        <q-card-section  v-html="comment" />
      </template>
      <base-rich-text v-else placeholder="Решение" v-model="newSolutionText" />
    </template>
    <template #actions>
      <q-btn flat label="Отмена"  @click="promptIsOpen=false; currentTask={};"/>
      <q-btn v-if="promptShowAction" flat label="Отправить" @click="createSolution" />
    </template>
  </base-dialog>

</template>
<style scoped lang="scss">
.text-transforn-none {
  text-transform: none;
}
.black {
  color: #1d1d1d;
}
</style>