<script setup>
import { computed } from '@vue/reactivity';
import { useStore } from 'vuex';

import BaseCard from '../../base/BaseCard.vue';
import BaseDialog from '../../base/BaseDialog.vue';
import BaseCardWrapper from '../../base/BaseCardWrapper.vue';
import EmptyMessage from '../../base/EmptyMessage.vue';

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
          <q-card-section v-html="material.text" />
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