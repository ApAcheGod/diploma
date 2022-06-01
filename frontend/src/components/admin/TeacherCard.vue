<script setup>
import methods from '../../store/methodsAdmin.js';
import {ref} from 'vue';
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

      <div class="ml-2 text-h6 font-semibold">   
        {{props.teacher.login ? props.teacher.login : "Нет логина"}}
      </div>

      <div class="text-subtitle2 font-semibold">   
        {{props.teacher.teacherName ? props.teacher.teacherName : "Нет имени"}}
      </div>

      <div class="text-subtitle2 font-semibold">   
        {{props.teacher.email ? props.teacher.email : "Нет адреса почты" }}
      </div>

      <div class="text-subtitle2">

        <div v-if="props.teacher.materials?.length > 0">
          <div class="mt-1 font-semibold">
            Материалы:
          </div>

          <q-chip size="s" v-for="(material, id) in props.teacher.materials.slice(0, 2)">        
            {{material.name}} 
          </q-chip>

        </div>

        <div v-if="props.teacher.subjects?.length > 0">
          <div class="mt-1 font-semibold">
            Предметы:
          </div>

          <q-chip size="s" v-for="(subject, id) in props.teacher.subjects.slice(0, 2)">        
            {{subject.name}} 
          </q-chip>

        </div>

        <div v-if="props.teacher.rooms?.length > 0">
          <div class="mt-1 font-semibold">
            Комнаты:
          </div>

          <q-chip size="s" v-for="(room, id) in props.teacher.rooms.slice(0, 2)">        
            {{room.name}} 
          </q-chip>

        </div>

        <div v-if="props.teacher.tasks?.length > 0">
          <div class="mt-1 font-semibold">
            Задания:
          </div>

          <q-chip size="s" v-for="(task, id) in props.teacher.tasks.slice(0, 2)">        
            {{task.name}}
          </q-chip>

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