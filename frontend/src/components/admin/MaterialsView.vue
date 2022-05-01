<script setup>
import {inject, onMounted, ref} from 'vue';
import {useQuasar} from 'quasar'

import MaterialCard from './MaterialCard.vue';
import MaterialDialog from './MaterialDialog.vue';

let $q = useQuasar();

let materials = ref();

let teachers = ref();
let subjects = ref();

const methods = inject('methods');

let materialPromptIsOpen = ref(false);

onMounted(async () => { 
  Promise.allSettled([
    methods.getMaterialsFetch(),
    methods.getTeachersFetch(),
    methods.getSubjectsFetch(),
    ])
  .then((results) => {
    materials.value = results[0].value;
    teachers.value = results[1].value;
    subjects.value = results[2].value;
  });
});

function triggerPositive(msg) {
  $q.notify({
    type: 'positive',
    message: msg
  })
}

function triggerNegative(msg) {
  $q.notify({
    type: 'negative',
    message: msg
  })
}

async function updateMaterial(newMaterial){
  let updateResult = await methods.updateMaterialFetch(newMaterial);
  if(updateResult){
    materials.value = await methods.getMaterialsFetch();
    triggerPositive('Информация о материале успешно обновлена!');
    materialPromptIsOpen.value = false;
  }
  else
    triggerNegative('Не удалось обновить информацию о материале');
}

async function addNewMaterial(newMaterial){
  const createResult = await methods.createMaterialFetch(newMaterial);
  if (createResult) {
    materials.value = await methods.getMaterialsFetch();
    triggerPositive('Успешно добавлен новый материал!');
    materialPromptIsOpen.value = false;
  }
  else
    triggerNegative('Не удалось добавить материал');
}

async function deleteMaterial(material){
  const deleteResult = await methods.deleteMaterialFetch(material);
  if(deleteResult){
    materials.value = await methods.getMaterialsFetch();
    triggerPositive('Информация о материалу успешно удалена!')
  }
  else
    triggerNegative('Не удалось удалить информацию о материале')
}

</script>
<template>
  <transition-group name="list" >
    <material-card v-for="material in materials"
      v-bind:key="material?.id"
      :material="material"
      :subjects="subjects"
      :teachers="teachers"
      @delete-click="deleteMaterial"
      @update-click="updateMaterial"
    />
  </transition-group>

  <q-page-sticky position="bottom-right" :offset="[20, 20]">
    <q-btn 
      fab 
      icon="add" 
      color="accent"
      @click="materialPromptIsOpen = true"/>
  </q-page-sticky>

  <material-dialog
    updateButtonLabel="Добавить"
    :prompt="materialPromptIsOpen"
    :subjects="subjects"
    :teachers="teachers"
    @update-click="addNewMaterial"
    @prompt-close="materialPromptIsOpen = false"
  />
</template>
<style scoped>

.list-item {
  display: inline-block;
  margin-right: 8px;
}

.list-enter-active,
.list-leave-active {
  transition: all 0.3s ease;
}

.list-enter-from,
.list-leave-to {
  opacity: 0;
  transform: translateY(20px);
}

</style>