<script setup>
import { onMounted } from 'vue';
import {ref} from "vue";
import { computed } from '@vue/reactivity';
import { useStore } from 'vuex';
import {useQuasar} from "quasar";

import BaseCard from '../../base/BaseCard.vue';
import BaseCardWrapper from '../../base/BaseCardWrapper.vue';
import BaseAddNew from '../../base/BaseAddNew.vue';

import actionsTypes from "../../../store/actionsTypes";

const $q = useQuasar();
const store = useStore();
const materialsByRooms = computed(() => { const materials = store.getters.getUserMaterials; console.log(materials); return materials;});
const newMaterialName = ref('');
const materialIsNotEditing = ref(true);

</script>
<template>
  <base-card-wrapper v-for="room in materialsByRooms" :key="room.name" :room="room">
    <template #title>
      <hr/>
      {{room?.roomName}}
    </template>
    <template #items>
      <base-card>
        <template #title>

        </template>
        <template #body>
          
        </template>
        <template #actions>
          
        </template>
      </base-card>
      <base-add-new>
        <template #body>
          
        </template>
      </base-add-new>
    </template>
  </base-card-wrapper>
</template>
<style lang="scss" scoped>
</style>