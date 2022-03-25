<script setup>
import { onMounted, ref, inject } from 'vue';
import { useQuasar } from 'quasar'

import MaterialCard from './MaterialCard.vue';
import MaterialDialog from './MaterialDialog.vue';

let $q = useQuasar();

let materials = ref();

const store = inject('store');

let materialPromptOpen = ref(false);
let materialPrompt = ref();

onMounted(async () => { 
  materials.value = (await Promise.allSettled([store.methods.getMaterialsFetch()]))[0].value;

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
  let updateResult = await store.methods.updateMaterialFetch({
    id: newMaterial.id,
    first_name: newMaterial.first_name,
    last_name: newMaterial.last_name,
    patronymic: newMaterial.patronymic,
  });
  if(updateResult){
    materials.value = await store.methods.getMaterialsFetch();
    triggerPositive('Информация о студенте успешно обновлена!');
  }
  else
    triggerNegative('Не удалось обновить информацию о студенте');
}

async function addNewMaterial(newMaterial){
  const createResult = await store.methods.createMaterialFetch(newMaterial);
  if (createResult) {
    materials.value = await store.methods.getMaterialsFetch();
    triggerPositive('Успешно добавлен новый студент!');
  }
  else
    triggerNegative('Не удалось добавить студента');
}

async function deleteMaterial(material){
  const deleteResult = await store.methods.deleteMaterialFetch(material);
  if(deleteResult){
    materials.value = await store.methods.getMaterialsFetch();
    triggerPositive('Информация о студенте успешно удалена!')
  }
  else
    triggerNegative('Не удалось удалить информацию о студенте')
}

</script>
<template>
  <transition-group name="list" >
    <material-card v-for="material in materials"
      v-bind:key="material?.id"
      :material="material"
      @delete-click="deleteMaterial"
      @update-click="updateMaterial"
    />
  </transition-group>

  <q-page-sticky position="bottom-right" :offset="[20, 20]">
    <q-btn 
      fab 
      icon="add" 
      color="accent"
      @click="addNewMaterial"/>
  </q-page-sticky>
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