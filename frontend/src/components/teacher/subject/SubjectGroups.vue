<script setup>
import { computed, ref } from "vue";
import { useStore } from 'vuex';

import BaseCard from '../../base/BaseCard.vue';
import BaseCardWrapper from '../../base/BaseCardWrapper.vue';
import BaseAddNew from '../../base/BaseAddNew.vue';

import actionsTypes from "../../../store/actionsTypes";

const store = useStore();
const activeSubject = computed(() => 
  store.getters.getActiveSubject
);
const availableStudentGroups = computed(() => {
  const studentGroups = store.getters.getAllGroups || [];
  const currentStudentGroupsId = activeSubject?.groups?.map(g => g.id) || [];
  console.log(currentStudentGroupsId);
  const filteredStudentGroups = studentGroups.filter(group => !currentStudentGroupsId.includes(group.id));
  return filteredStudentGroups;
});
let newGroupId = ref('');

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

</script>
<template>
  <base-card-wrapper>
    <template #items>
      <base-card v-for="group in activeSubject?.groups" :key="group.id">
        <template #title>
          {{group.name}}
        </template>
        <template #body>
          {{`${studentsRuLocale(group.students.length)}: ${group.students.map(s => s.name).join(', ')}`}}
        </template>
        <template #actions>
          <button class="base-card__button base-card__button_delete">Удалить</button>
        </template>
      </base-card>
      <base-add-new key="add-new">
        <template #body>
          <q-select 
            v-model="newGroupId" 
            option-value="id"
            option-label="name"
            :options="availableStudentGroups" 
            label="Добавить группу" />
          <button class="base-card_add__button">
            <img class="room-card-add__icon" src="../../../img/add.svg"/>
          </button>
        </template>
      </base-add-new>
    </template>
  </base-card-wrapper>
</template>
<style lang="scss" scoped>
</style>