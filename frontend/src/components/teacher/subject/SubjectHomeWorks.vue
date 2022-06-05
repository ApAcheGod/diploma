<script setup>
import { computed, ref } from "vue";
import { useStore } from 'vuex';;

import BaseCard from '../../base/BaseCard.vue';
import BaseCardWrapper from '../../base/BaseCardWrapper.vue';
import BaseAddNew from '../../base/BaseAddNew.vue';

import actionsTypes from "../../../store/actionsTypes";

const store = useStore();
const activeSubject = computed(() => 
  store.getters.getActiveSubject
);

</script>
<template>
  <base-card-wrapper>
      <!-- <template #title>
      <hr/>
      {{room?.roomName}}
    </template> -->
    <template #items>
      <base-card v-for="group in activeSubject?.groups" :key="group.id">
        <template #title>
          {{group.name}}
        </template>
        <template #body>
          <!-- {{group.students.length}} -->
        </template>
        <template #actions>
          <button class="base-card__button base-card__button_delete">Удалить</button>
        </template>
      </base-card>
      <base-add-new key="add-new">
        <template #body>
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