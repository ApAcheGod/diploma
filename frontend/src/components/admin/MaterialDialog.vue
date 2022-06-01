<script setup>
import methods from '../../store/methodsAdmin.js';

import {computed, onMounted, ref} from "vue";

const emits = defineEmits(['update-click', 'prompt-close']);

const props = defineProps({
  prompt: Boolean,
  material: Object,
  updateButtonLabel: {
    default: 'Изменить',
    type: String
  },
  subjects: Array,
  teachers: Array,
});

let newMaterial = ref({
  name: null,
  text:  null,
  teacherId:  null,
  subjectId:  null,
});

onMounted(() => {
  if (props.material) {
    newMaterial.value.id =  props.material.id;
    newMaterial.value.name =  props.material.name;
    newMaterial.value.text =  props.material.text;
    newMaterial.value.teacherId =  props.material.teacherId;
    newMaterial.value.teacherName =  props.material.teacherName;
    newMaterial.value.subjectId =  props.material.subjectId;
    newMaterial.value.subjectName =  props.material.subjectName;
  }
});

const teacherOptions = computed(() => {
  return props.teachers?.map(t => {
    t.teacherId = t.id;
    return t;
  });
});

// const availableSubjects = computed(() => {
//   if (!newMaterial.value.teacherId || !props.teachers) return [];
//   let currentTeacherData = [];
//   props.teachers.forEach(teacher => {
//     if (teacher.id === newMaterial.value.teacherId){
//       currentTeacherData.push(teacher.subjects);
//     }
//   });
//   currentTeacherData.map(s => {
//     s.subjectId = s.id;
//     s.subjectName = s.name;
//     return s;
//   });
// });

const subjectOptions = computed(() => {
  return props.subjects?.map(s => {
    s.subjectId = s.id;
    s.subjectName = s.name;
    return s;
  });
});
</script>

<template>
  <q-dialog v-model="props.prompt" persistent>
    <q-card style="min-width: 350px" @keyup.esc="emits('prompt-close')" @keyup.enter="emits('update-click', newMaterial)">

      <q-card-section>
        <div class="text-h5">Редактирование</div>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <q-input dense v-model="newMaterial.name" autofocus label="Название"/>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <q-input dense autogrow v-model="newMaterial.text" autofocus label="Текст"/>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <q-select
            filled
            v-model="newMaterial.teacherId"
            :options="teacherOptions"
            option-value="teacherId"
            option-label="teacherName"
            label="Преподаватель"
            emit-value
            map-options
        />
      </q-card-section>

      <q-card-section class="q-pt-none">
        <q-select
          filled
          v-model="newMaterial.subjectId"
          :options="subjectOptions"
          option-value="subjectId"
          option-label="subjectName"
          label="Предмет"
          emit-value
          map-options
        />
      </q-card-section>

      <q-card-actions align="right" class="text-primary">
        <q-btn flat label="Отмена"  @click="emits('prompt-close')"/>
        <q-btn flat :label="updateButtonLabel" @click="emits('update-click', newMaterial)" />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<style scoped>
</style>
