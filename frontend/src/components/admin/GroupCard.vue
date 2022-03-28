<script setup>
import { computed, ref } from 'vue';
import TeacherDialog from './TeacherDialog.vue';

const emits = defineEmits(['update-click', 'delete-click']);
const props = defineProps({
  teacher: Object,
  subjects: Array, 
  rooms: Array,
  materials: Array, 
  tasks: Array,
});

let promptIsOpen = ref(false);

</script>
<template>
  <q-card class="my-card">
    <q-card-section class="bg-secondary text-white">
      <div class="text-h6">   
        {{props.teacher.login}}
      </div>

      <div class="text-subtitle2">   
        {{props.teacher.teacherName}}
      </div>

      <div class="text-subtitle2">   
        {{props.teacher.email}}
      </div>

      <div class="mt-2">
        <div class="text-subtitle2" v-for="subject in props.teacher.subjects">        
          {{subject.name}} 
        </div>

        <div class="text-subtitle2" v-for="room in props.teacher.rooms">        
          {{room.name}} 
        </div>
        
        <div class="text-subtitle2" v-for="task in props.teacher.tasks">        
          {{task.name}}
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
        @click="() => emits('delete-click', props.teacher)"
      >
      Удалить
      </q-btn>
    </q-card-actions>
    <teacher-dialog
      :subjects="subjects"
      :tasks="tasks"
      :materials="materials"
      :rooms="rooms"
      :prompt="promptIsOpen"
      :teacher="props.teacher"
      @prompt-close="promptIsOpen = false"
      @update-click="(newTeacher) => emits('update-click', newTeacher)"
    />
  </q-card>
</template>
<style scoped></style>