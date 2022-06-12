<script setup>
import { computed } from '@vue/reactivity';
import { useQuasar } from 'quasar';
import { useStore } from 'vuex';

import BaseCard from '../../base/BaseCard.vue';
import BaseCardWrapper from '../../base/BaseCardWrapper.vue';
import EmptyMessage from '../../base/EmptyMessage.vue';
import actionsTypes from '../../../store/actionsTypes';

const q = useQuasar();
const store = useStore();
const activeSubject = computed(() => store.getters.getActiveSubject);

</script>
<template>
  <base-card-wrapper v-if="activeSubject && activeSubject.materials.length > 0">
    <template #items>
      <base-card v-for="material in activeSubject.materials" :key="material.id">
        <template #title>
          {{material.name}}
        </template>
        <template #body>
          {{material.text}}
        </template>
      </base-card>
    </template>
  </base-card-wrapper>
  <empty-message v-else>
    Нет доступных материалов по этому предмету
  </empty-message>
</template>
<style scoped lang="scss">
</style>