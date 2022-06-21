<script setup>
import methods from '../../store/methodsAdmin.js';
import {computed, ref} from 'vue';
import StudentDialog from './StudentDialog.vue';

const emits = defineEmits(['update-click', 'delete-click']);
const props = defineProps({
    student: Object,
    promptIsOpen: {
      default: false,
      type: Boolean,
    }
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
        {{props.student.login}}
      </div>
      <div class="text-subtitle2">        
        {{props.student.last_name}} 
        {{props.student.first_name}} 
        {{props.student.patronymic}}
      </div>
      <div class="text-subtitle2">
        {{props.student.email}}
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
      @click="() => emits('delete-click', props.student)"
      >
      Удалить
      </q-btn>
    </q-card-actions>
    <student-dialog
    :prompt="promptIsOpen"
    :student="props.student"
    @prompt-close="promptIsOpen = false"
    @update-click="(newStudent) => emits('update-click', newStudent)"
    />
  </q-card>
</template>
<style scoped></style>