<script setup>
import { onMounted } from 'vue';
import actionsTypes from "../../store/actionsTypes";
import {computed, ref} from "vue";

import MaterialDialog from '../admin/MaterialDialog.vue';
const emits = defineEmits(['update-click', 'delete-click']);

let promptIsOpen = ref(false);

const initials = computed(() => {
  return `${props.student.first_name[0].toUpperCase()}${props.student.patronymic[0].toUpperCase()}`
});

const props = defineProps({
  material : Object,
});


function createMaterial(material) {
  store.dispatch(actionsTypes.CREATE_MATERIAL, material)
    .then(() => {
      $q.notify({type: 'positive', message: 'Материал успешно создана'})
      newMaterialName.value = '';
    })
    .catch(error => {
      console.error(error);
      $q.notify({type: 'negative', message: 'Ошибка при создании материала'})
    })
}
function updateMaterial(material) {
  store.dispatch(actionsTypes.UPDATE_MATERIAL, material)
    .then(() => {
      $q.notify({type: 'positive', message: 'Материал успешно обновлен'})
      newMaterialName.value = '';
      materialIsNotEditing.value = true
    })
    .catch(error => {
      console.error(error);
      $q.notify({type: 'negative', message: 'Ошибка при обновлении материала'})
    })
}
function deleteMaterial(material) {
  store.dispatch(actionsTypes.DELETE_MATERIAL, material)
    .then(() => {
      $q.notify({type: 'positive', message: 'Материал успешно удалена'})
      newMaterialName.value = '';
      materialIsNotEditing.value = true
    })
    .catch(error => {
      console.error(error);
      $q.notify({type: 'negative', message: 'Ошибка при удалении материала'})
    })
}

</script>
<template>
<!--  <div class="material-card">-->
<!--    <div class="material-card__title">-->
<!--      {{props.material?.name}}-->
<!--    </div>-->
<!--  </div>-->
  <q-card class="my-card">
    <q-card-section class="bg-secondary text-white">
      <div class="text-h6">
        {{props.material.name}}
      </div>
      <div class="text my-3">
        {{props.material.text}}
      </div>
      <div class="text text-right">
        {{props.material.subjectName}}
      </div>
      <div class="text text-right">
        {{props.material.teacherName}}
      </div>
    </q-card-section>
    <q-separator />
    <q-card-actions align="right">
      <q-btn
          flat
          @click="promptIsOpen = true"
      >
        Изменить
      </q-btn>
      <q-btn
          color="accent"
          flat
          @click="() => emits('delete-click', props.material)"
      >
        Удалить
      </q-btn>
    </q-card-actions>
    <material-dialog
        :prompt="promptIsOpen"
        :material="props.material"
        :subjects="props.subjects"
        :teachers="props.teachers"
        @prompt-close="promptIsOpen = false"
        @update-click="(newMaterial) => emits('update-click', newMaterial)"
    />
  </q-card>
</template>
<style lang="scss" scoped>
.material-card {

}
</style>