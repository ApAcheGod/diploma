<script setup>
import methods from '../../store/methodsAdmin.js';

import {computed, onMounted, ref} from "vue";



const emits = defineEmits(['update-click', 'prompt-close']);

const props = defineProps({
  prompt: Boolean,
  subject: Object,
  updateButtonLabel: {
    default: 'Изменить',
    type: String
  },
  rooms: Array,
  tasks: Array,
  materials: Array,
  teachers: Array,
  groups: Array,
});

let newSubject = ref({});

onMounted(() => {
  if (props.subject)
    newSubject.value = JSON.parse(JSON.stringify(props.subject));
});

const teacherOptions = computed(() => {
  return props.teachers?.map(t => {
    t.teacherId = t.id;
    return t;
  });
});

const roomOptions = computed(() => {
  return props.rooms?.map(r => {
    r.roomId = r.id;
    return r;
  });
});

const newSubjectFormatted = computed(() => {
  let newSubjectFormatted = JSON.parse(JSON.stringify(newSubject.value));

  newSubjectFormatted.materials = methods.idArrToObjs(newSubjectFormatted.materials);
  newSubjectFormatted.tasks = methods.idArrToObjs(newSubjectFormatted.tasks);
  newSubjectFormatted.rooms = methods.idArrToObjs(newSubjectFormatted.rooms);
  newSubjectFormatted.groups = methods.idArrToObjs(newSubjectFormatted.groups);
  return newSubjectFormatted;
});

</script>

<template>
  <q-dialog v-model="props.prompt" persistent>
    <q-card style="min-width: 350px" @keyup.esc="emits('prompt-close')" @keyup.enter="emits('update-click', newSubjectFormatted)">

      <q-card-section>
        <div class="text-h5">Редактирование</div>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <q-input dense v-model="newSubject.name" autofocus label="Название"/>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <q-select
          filled
          emit-value
          map-options
          option-value="teacherId"
          option-label="teacherName"
          label="Преподаватель"
          :options="teacherOptions"
          v-model="newSubject.teacherId"
        />
      </q-card-section>

      <q-card-section class="q-pt-none">
        <q-select
            filled
            emit-value
            map-options
            option-value="roomId"
            option-label="name"
            label="Комнаты"
            :options="roomOptions"
            v-model="newSubject.roomId"
        />
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
          v-model="newSubject.materials"
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
          v-model="newSubject.tasks"
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
            label="Группы"
            :options="props.groups"
            v-model="newSubject.groups"
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
        <q-btn flat label="Отмена"  @click="emits('prompt-close')"/>
        <q-btn flat :label="updateButtonLabel" @click="emits('update-click', newSubjectFormatted)" />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<style scoped>
</style>
