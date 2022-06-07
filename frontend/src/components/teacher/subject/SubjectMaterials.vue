<script setup>
import { computed, ref } from "vue";
import { useStore } from 'vuex';
import { useQuasar } from "quasar";

import BaseCard from '../../base/BaseCard.vue';
import BaseCardWrapper from '../../base/BaseCardWrapper.vue';
import BaseAddNew from '../../base/BaseAddNew.vue';

import actionsTypes from "../../../store/actionsTypes";

const titles = {
  NEW : 'Добавить',
  EDIT : 'Редактировать',
}
const q = useQuasar();
const store = useStore();
const activeSubject = computed(() => store.getters.getActiveSubject);
const newMaterial = ref({});
const promptIsOpen = ref(false);
const promptTitle = ref(titles.NEW)

const createOrUpdateMaterial = () => {
  const material = newMaterial.value;
  if (!material.name && !material.text) return;
  
  if (!material.id) {
    material.teacherId = activeSubject.value.teacherId;
    material.subjectId = activeSubject.value.id;
  }
  store.dispatch(material.id ? actionsTypes.UPDATE_MATERIAL : actionsTypes.CREATE_MATERIAL, material)
    .then(() => {
      q.notify({
        type: 'positive',
        message: material.id ? 'Материал успешно изменен' : 'Успешно создан новый материал',
      })
      newMaterial.value = {};
    })
    .catch((error) => {
      console.error(error);
      q.notify({
        type: 'negative',
        message: material.id ? 'Ошибка при изменении материала' : 'Ошибка при создании нового материала',
      })
    })
}

const deleteMaterial = (deletedMaterial) => {
  store.dispatch(actionsTypes.UPDATE_MATERIAL, deletedMaterial)
    .then(() => {
      q.notify({
        type: 'positive',
        message: 'Материал успешно удален',
      })
      newMaterial.value = {};
    })
    .catch((error) => {
      console.error(error);
      q.notify({
        type: 'negative',
        message: 'Ошибка при удалении материала',
      })
    })
}
</script>
<template>
  <base-card-wrapper>
    <template #items>

      <base-card v-for="material in activeSubject?.materials" :key="material.id">
        <template #title>
          {{material.name}}
        </template>
        <template #body>
          {{material.text}}
        </template>
        <template #actions>
          <button 
            class="base-card__button base-card__button_edit" 
            @click="
              newMaterial=JSON.parse(JSON.stringify(material)); 
              promptTitle=titles.EDIT;
              promptIsOpen=true;"
          >
            Изменить
          </button>
          <button 
            class="base-card__button base-card__button_delete" 
            @click="deleteMaterial(material)">
              Удалить
          </button>
        </template>
      </base-card>

      <base-add-new key="add-new">
        <template #body>
          <button 
            class="base-card_add__button"
            @click="
              newMaterial={};
              promptTitle=titles.NEW;
              promptIsOpen=true;"
            >
            <img class="room-card-add__icon" src="../../../img/add.svg"/>
          </button>
        </template>
      </base-add-new>

      <q-dialog v-model="promptIsOpen" persistent>
        <q-card style="min-width: 350px" @keyup.esc="promptIsOpen=false" @keyup.enter="updateTask(newTask)">
          <q-card-section>
            <div class="text-h5">{{promptTitle}}</div>
          </q-card-section>

          <q-card-section class="q-pt-none">
            <q-input dense v-model="newMaterial.name" autofocus label="Название"/>
          </q-card-section>

          <q-card-section class="q-pt-none">
            <q-input dense autogrow v-model="newMaterial.text" autofocus label="Текст материала"/>
          </q-card-section>

          <q-card-actions align="right" class="text-primary">
            <q-btn flat label="Отмена"  @click="promptIsOpen=false; newMaterial={};"/>
            <q-btn flat label="Добавить" @click="createOrUpdateMaterial()" />
          </q-card-actions>
        </q-card>
      </q-dialog>
    </template>
  </base-card-wrapper>
</template>
<style lang="scss" scoped>
</style>