<script setup>
import { onMounted } from 'vue';
import {ref} from "vue";
import { computed } from '@vue/reactivity';
import { useStore } from 'vuex';
import {useQuasar} from "quasar";
import MaterialCard from '../MaterialCard.vue';
import actionsTypes from "../../../store/actionsTypes";

const $q = useQuasar();
const store = useStore();
const materialsByRooms = computed(() => { const materials = store.getters.getUserMaterials; console.log(materials); return materials;});
const newMaterialName = ref('');
const materialIsNotEditing = ref(true);

</script>
<template>
  <div class="material-block" v-for="room in materialsByRooms" :key="room.name" :room="room">
    <div class="material-block__title">
      <hr/>
      {{room?.roomName}}
    </div>
      <material-card v-for="material in room?.roomMaterials" :material="material"/>
  </div>
</template>
<style lang="scss" scoped>
.mate-block {

  &__title {

  }
}
</style>