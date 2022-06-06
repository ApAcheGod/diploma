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
const activeSubjectEmpty = computed(() => store.getters.getActiveSubjectEmpty);
const availableStudentGroups = computed(() => {
  const studentGroups = store.getters.getAllGroups || [];
  const currentStudentGroupsId = activeSubject.value?.groups?.map(g => g.id) || [];
  const filteredStudentGroups = studentGroups.filter(group => !currentStudentGroupsId.includes(group.id))
    .map(group => { return {
      id : group.id,
      name : group.name,
    }});
  return filteredStudentGroups;
});
const defaultNewGroupEmpty = {
  id : null,
  name : 'Выберите группу',
};
let newGroup = ref(defaultNewGroupEmpty);

const studentsRuLocale = (studentsCount) => {
  const groupsCountNums = studentsCount.toString();
  if (!studentsCount)
    return 'Нет студентов';
  if (groupsCountNums[groupsCountNums.length - 1] == 1)
    return studentsCount + ' студент';
  if (groupsCountNums[groupsCountNums.length - 1] == 2 || groupsCountNums[groupsCountNums.length - 1] == 3 || groupsCountNums[groupsCountNums.length - 1] == 4)
    return studentsCount + ' студента';
  return studentsCount + ' студентов';
}

const addGroup = () => {
  if (newGroup.value.id === defaultNewGroupEmpty.id) return;
  const subject = activeSubjectEmpty.value;
  subject.groups ? subject.groups.push(newGroup.value) : subject.groups = [newGroup.value];
  store.dispatch(actionsTypes.UPDATE_SUBJECT, subject)
    .then(() => {
      q.notify({
        type: 'positive',
        message: 'Группа успешно добавлена в предмет!',
      });
      newGroup.value = defaultNewGroupEmpty;
    })
    .catch((error) => {
      console.error(error);
      q.notify({
        type: 'negative',
        message: 'Ошибка при добавлении группы',
      });
    })
}

const removeGroup = (removedGroup) => {
  const subject = activeSubjectEmpty.value;
  subject.groups = subject.groups.filter(group => group.id !== removedGroup.id);
  store.dispatch(actionsTypes.UPDATE_SUBJECT, subject)
    .then(() => {
      q.notify({
        type: 'positive',
        message: 'Группа успешно удалена',
      });
      newGroup.value = defaultNewGroupEmpty;
    })
    .catch((error) => {
      console.error(error);
      q.notify({
        type: 'negative',
        message: 'Ошибка при удалении группы',
      });
  })
}

</script>
<template>
  <base-card-wrapper>
    <template #items>
      <base-card v-for="group in activeSubject?.groups" :key="group.id">
        <template #title>
          {{group.name}}
        </template>
        <template #body>
          <b>{{`${studentsRuLocale(group.students.length)}:`}}</b>
          <p v-for="student in group.students">
            {{student.name}}
          </p>
        </template>
        <template #actions>
          <button class="base-card__button base-card__button_delete" @click="removeGroup(group)">Удалить</button>
        </template>
      </base-card>
      <base-add-new key="add-new">
        <template #body>
          <q-select
            v-model="newGroup" 
            option-value="id"
            option-label="name"
            :options="availableStudentGroups" 
            />
          <button class="base-card_add__button" @click="addGroup">
            <img class="room-card-add__icon" src="../../../img/add.svg"/>
          </button>
        </template>
      </base-add-new>
    </template>
  </base-card-wrapper>
</template>
<style lang="scss" scoped>
</style>