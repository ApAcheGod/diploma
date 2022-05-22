<script setup>
import methods from '../../store/methodsAdmin.js';
import {ref, computed} from 'vue';
import TaskDialog from './TaskDialog.vue';

const emits = defineEmits(['update-click', 'delete-click']);
const props = defineProps({
  task: Object,
  subjects: Array,
  teachers: Array, 
  solutions: Array,
});

let promptIsOpen = ref(false);

const formattedDate = computed(() => {
  if (props.task.date_of_creation) {
      let dateOfDelivery = new Date(props.task.date_of_creation);
    return dateOfDelivery.toLocaleDateString('ru');
  }
  return "";
});

</script>
<template>
  <q-card class="my-card">
    <q-card-section class="bg-secondary text-white">
      <div class="text-h6" style="display: flex; justify-content: space-between">
        <div>
        {{props.task.name}}
        </div>
        <div>
          {{formattedDate}}
        </div>
      </div>

      <div class="text-subtitle2">   
        {{props.task.subjectName}}
      </div>

      <div class="text-subtitle2">   
        {{props.task.teacherName}}
      </div>

      <div class="text-subtitle2">   
        {{props.task.text}}
      </div>

      <div class="mt-2">
        <div class="text-subtitle2" v-for="solution in props.task.solutions">        
          {{solution.text}} 
          {{solution.studentName}} 
        </div>
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
        @click="() => emits('delete-click', props.task)"
      >
      Удалить
      </q-btn>
    </q-card-actions>
    <task-dialog
      :subjects="subjects"
      :teachers="teachers"
      :solutions="solutions"
      :prompt="promptIsOpen"
      :task="props.task"
      @prompt-close="promptIsOpen = false"
      @update-click="(newTask) => emits('update-click', newTask)"
    />
  </q-card>
</template>
<style scoped></style>