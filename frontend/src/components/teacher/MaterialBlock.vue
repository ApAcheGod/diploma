<script setup>
import { onMounted } from 'vue';
import MaterialCard from './MaterialCard.vue';
import actionsTypes from "../../store/actionsTypes";
import {useQuasar} from "quasar";
import {useStore} from "vuex";
import {ref} from "vue";

const $q = useQuasar();
const store = useStore();
const newMaterialName = ref('');
const materialIsNotEditing = ref(true);

const props = defineProps({
  room : Object,
});

</script>
<template>
  <div class="material-block">
    <div class="material-block__title">
      <hr/>
      {{props.room?.roomName}}
    </div>
      <material-card v-for="material in props.room?.roomMaterials" :material="material"/>
  </div>
</template>
<style lang="scss">
.mate-block {

  &__title {

  }
}
</style>