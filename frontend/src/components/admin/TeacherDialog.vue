<script setup>

import {computed, inject, onMounted, ref} from "vue";

const methods = inject('methods');

const emits = defineEmits(['update-click', 'prompt-close']);

const props = defineProps({
  prompt: Boolean,
  teacher: Object,
  updateButtonLabel: {
    default: 'Изменить',
    type: String
  },
  formTitle: {
    default: 'Редактировать',
    type: String
  },
  subjects: Array, 
  rooms: Array,
  materials: Array, 
  tasks: Array,
});

let newTeacher = ref({});

onMounted(() => {
  clearForm();
});

function clearForm() {
  if (props.teacher)
    newTeacher.value = JSON.parse(JSON.stringify(props.teacher));
  else
    newTeacher.value = {};
}

const newTeacherFormatted = computed(() => {
  let newTeacherFormatted = JSON.parse(JSON.stringify(newTeacher.value));

  newTeacherFormatted.materials = methods.idArrToObjs(newTeacherFormatted.materials);
  newTeacherFormatted.tasks = methods.idArrToObjs(newTeacherFormatted.tasks);
  newTeacherFormatted.rooms = methods.idArrToObjs(newTeacherFormatted.rooms);
  newTeacherFormatted.subjects = methods.idArrToObjs(newTeacherFormatted.subjects);

  return newTeacherFormatted;
});

</script>

<template>
  <q-dialog v-model="props.prompt" persistent transition-show="none" transition-hide="none">
    <q-card style="min-width: 40%" @keyup.esc="emits('prompt-close'); clearForm();" @keyup.enter="emits('update-click', newTeacherFormatted)">
      <q-card-section>
        <div class="text-h5">{{props.formTitle}}</div>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <q-input dense v-model="newTeacher.last_name" autofocus label="Фамилия"/>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <q-input dense v-model="newTeacher.first_name" autofocus label="Имя"/>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <q-input dense v-model="newTeacher.patronymic" autofocus label="Отчество"/>
      </q-card-section>
      
      <q-card-section class="q-pt-none">
        <q-input dense v-model="newTeacher.email" autofocus label="Email"/>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <q-select
          filled
          emit-value
          map-options
          multiple
          use-chips
          stack-label
          option-value="id"
          option-label="name"
          label="Материалы"
          :options="props.materials"
          v-model="newTeacher.materials"
        >
          <template v-slot:option="{ itemProps, opt, selected, toggleOption }">
            <q-item v-bind="itemProps">
              <q-item-section>
                <q-item-label v-html="opt.name" />
              </q-item-section>
              <q-item-section side>
                <q-toggle :model-value="selected" @update:model-value="toggleOption(opt)" />
              </q-item-section>
            </q-item>
          </template>
        </q-select>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <q-select
          filled
          emit-value
          map-options
          multiple
          use-chips
          stack-label
          option-value="id"
          option-label="name"
          label="Предметы"
          :options="props.subjects"
          v-model="newTeacher.subjects"
        >
          <template v-slot:option="{ itemProps, opt, selected, toggleOption }">
            <q-item v-bind="itemProps">
              <q-item-section>
                <q-item-label v-html="opt.name" />
              </q-item-section>
              <q-item-section side>
                <q-toggle :model-value="selected" @update:model-value="toggleOption(opt)" />
              </q-item-section>
            </q-item>
          </template>
        </q-select>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <q-select
          filled
          emit-value
          map-options
          multiple
          use-chips
          stack-label
          option-value="id"
          option-label="name"
          label="Комнаты"
          :options="props.rooms"
          v-model="newTeacher.rooms"
        >
          <template v-slot:option="{ itemProps, opt, selected, toggleOption }">
            <q-item v-bind="itemProps">
              <q-item-section>
                <q-item-label v-html="opt.name" />
              </q-item-section>
              <q-item-section side>
                <q-toggle :model-value="selected" @update:model-value="toggleOption(opt)" />
              </q-item-section>
            </q-item>
          </template>
        </q-select>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <q-select
          filled
          emit-value
          map-options
          multiple
          use-chips
          stack-label
          option-value="id"
          option-label="name"
          label="Задания"
          :options="props.tasks"
          v-model="newTeacher.tasks"
        >
          <template v-slot:option="{ itemProps, opt, selected, toggleOption }">
            <q-item v-bind="itemProps">
              <q-item-section>
                <q-item-label v-html="opt.name" />
              </q-item-section>
              <q-item-section side>
                <q-toggle :model-value="selected" @update:model-value="toggleOption(opt)" />
              </q-item-section>
            </q-item>
          </template>
        </q-select>
      </q-card-section>

      <q-card-actions align="right" class="text-primary">
        <q-btn flat label="Отмена"  @click="emits('prompt-close'); clearForm();"/>
        <q-btn flat :label="updateButtonLabel" @click="emits('update-click', newTeacherFormatted)" />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<style scoped>
  
</style>
