<script setup>
import { computed, ref } from "vue";
import { useStore } from 'vuex';;

import BaseCard from '../../base/BaseCard.vue';
import BaseCardWrapper from '../../base/BaseCardWrapper.vue';
import BaseAddNew from '../../base/BaseAddNew.vue';

import actionsTypes from "../../../store/actionsTypes";

const store = useStore();
const homeWorks = computed(() => {
  const hw = store.getters.getHomeWorks;
  if (!hw) store.dispatch(actionsTypes.DELETE_ACTIVE_SUBJECT);
  return hw;
});

</script>
<template>
  <template v-if="homeWorks">
    <base-card-wrapper v-for="task in homeWorks">
      <template #title>
        <hr/>
        {{task.name}}
      </template>
      <template #items>
        <base-card-wrapper v-for="group in task.uncheckdHomeWorks" :key="group.id">
          <template #title>
            <hr/>
            {{group.groupName}}
          </template>
          <template #items>
            <base-card v-for="solution in group.homeWorks" :key="solution.id">
              <template #title>
                {{solution.taskName}}
              </template>
              <template #body>
                {{solution.text}}
              </template>
              <template #actions>
                <!-- <button class="base-card__button base-card__button_delete">Удалить</button> -->
              </template>
            </base-card>
          </template>
        </base-card-wrapper>
      </template>
    </base-card-wrapper>
  </template>
    
</template>
<style lang="scss" scoped>
</style>