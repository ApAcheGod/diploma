<script setup>
import { computed, ref } from "vue";
import { useStore } from 'vuex';
import { useQuasar } from "quasar";

import BaseDialog from "../../base/BaseDialog.vue";
import BaseCard from '../../base/BaseCard.vue';
import BaseCardWrapper from '../../base/BaseCardWrapper.vue';
import BaseAddNew from '../../base/BaseAddNew.vue';
import BaseRichText from "../../base/BaseRichText.vue";

import actionsTypes from "../../../store/actionsTypes";

const titles = {
  NEW : 'Добавить новый материал',
  EDIT : 'Редактировать материал',
};
const q = useQuasar();
const store = useStore();
const activeSubject = computed(() => store.getters.getActiveSubject);
const newMaterial = ref({});

const promptIsOpen = ref(false);
const promptTitle = ref(titles.NEW)

const createOrUpdateMaterial = () => {
  const material = newMaterial.value;
  material.teacherId = activeSubject.value.teacherId;
  material.subjectId = activeSubject.value.id;

  if (!material.name) {
    q.notify({ type: 'negative', message: 'Материал не может иметь пустое имя', });
    return; 
  }

  if (!material.teacherId || !material.subjectId) {
    q.notify({ type: 'negative', message: 'Системная ошибка при изменении материала', });
    return; 
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
  store.dispatch(actionsTypes.DELETE_MATERIAL, deletedMaterial)
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
          <q-card-section v-html="material.text" />
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
            @click="deleteMaterial(material)"
          >
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

      <base-dialog 
        :title="promptTitle"
        :promptIsOpen="promptIsOpen"
        v-on:change-open-status="promptIsOpen = !promptIsOpen"
        >
        <template #body>
          <q-input padding="8px" dense v-model="newMaterial.name" autofocus label="Название" :rules="[val => !!val || 'Название обязательно']"/>
          <base-rich-text v-model="newMaterial.text" />
        </template>
        <template #actions>
          <q-btn class="base-card__button" padding="8px" flat label="Отмена"  @click="promptIsOpen=false; newMaterial={};"/>
          <q-btn class="base-card__button" padding="8px" flat label="Добавить" :disable="!newMaterial.name" @click="createOrUpdateMaterial(); promptIsOpen=false" />
        </template>
      </base-dialog>

    </template>
  </base-card-wrapper>
</template>
<style lang="scss" scoped>
</style>