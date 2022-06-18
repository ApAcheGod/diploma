<script setup>
import { computed, ref } from "vue";
import { useQuasar } from "quasar";
import { useStore } from 'vuex';

import BaseDialog from "../../base/BaseDialog.vue";
import BaseCard from '../../base/BaseCard.vue';
import BaseCardWrapper from '../../base/BaseCardWrapper.vue';
import EmptyMessage from "../../base/EmptyMessage.vue";

import actionsTypes from "../../../store/actionsTypes";
import workStatuses from "../../../models/workStatuses";

const q = useQuasar();
const store = useStore();
const homeWorks = computed(() => store.getters.getHomeWorks);
const promptIsOpen = ref(false);
const currentSolution = ref({});

const correctSolution = (solution) => {
  const exam = { examinationStatus: workStatuses.CORRECTLY, solutionId: solution.id};
  store.dispatch(actionsTypes.CREATE_EXAMINATION, exam)
  .then(() => {
    q.notify({
      type: 'positive',
      message: 'Работа успешно проверена'
    })
  })
  .catch((error) => {
    console.error(error);
    q.notify({
      type: 'positive',
      message: 'Ошибка при проверке работы'
    })
  });
}
const notCorrectSolution = (solution) => {
  const exam = { examinationStatus: workStatuses.INCORRECTLY, solutionId: solution.id};
  store.dispatch(actionsTypes.CREATE_EXAMINATION, exam)
  .then(() => {
    q.notify({
      type: 'positive',
      message: 'Работа успешно проверена'
    })
  })
  .catch((error) => {
    console.error(error);
    q.notify({
      type: 'positive',
      message: 'Ошибка при проверке работы'
    })
  });
}

</script>
<template>
  <template v-if="homeWorks && homeWorks.length > 0">
    <base-card-wrapper v-for="task in homeWorks">
      <template #title>
        <hr/>
        {{task.name}}
      </template>
      <template #items>
        <template v-for="group in task.uncheckdHomeWorks">
          <base-card v-for="solution in group.homeWorks" :key="solution.id">
            <template #title>
              {{group.groupName}}
              <div class="base-card__subtitle">{{solution.studentName}}</div>
            </template>
            <template #body>
              <q-card-section v-html="solution.text" />
            </template>
            <template #actions>
              <button class="base-card__button" @click="currentSolution=solution; promptIsOpen=true">Проверка</button>
            </template>
          </base-card>
        </template>
      </template>
    </base-card-wrapper>
  </template>
  <empty-message v-else>
    <svg style="width:24px;height:24px" viewBox="0 0 24 24">
      <path fill="#1d1d1d" d="M21,7L9,19L3.5,13.5L4.91,12.09L9,16.17L19.59,5.59L21,7Z" />
    </svg>
    Все задания проверены!
  </empty-message>

  <base-dialog 
    title="Проверка работы"
    :promptIsOpen="promptIsOpen"
    v-on:change-open-status="promptIsOpen = !promptIsOpen"
    >
    <template #body>
      {{currentSolution.studentName}}
      <q-card-section v-html="currentSolution.text" />
    </template>
    <template #actions>
      <q-btn class="base-card__button" padding="8px" flat @click="correctSolution(currentSolution)">Верно</q-btn>
      <q-btn class="base-card__button base-card__button_delete" padding="8px" flat @click="notCorrectSolution(currentSolution)">Неверно</q-btn>
    </template>
  </base-dialog>
</template>
<style lang="scss">
</style>