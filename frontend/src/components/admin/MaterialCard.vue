<script setup>
import methods from '../../store/methodsAdmin.js';
import {computed, ref} from 'vue';
import MaterialDialog from './MaterialDialog.vue';

const emits = defineEmits(['update-click', 'delete-click']);

const props = defineProps({
  material: Object,

  subjects: Array, 
  teachers: Array,
});

let promptIsOpen = ref(false);

const initials = computed(() => {
    return `${props.student.first_name[0].toUpperCase()}${props.student.patronymic[0].toUpperCase()}`
});

</script>
<template>
  <q-card class="my-card">
    <q-card-section class=" ">
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
       color="primary" 
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
<style scoped></style>