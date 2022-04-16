<script setup>
import {ref} from 'vue';
import GroupDialog from './GroupDialog.vue';

const emits = defineEmits(['update-click', 'delete-click']);
const props = defineProps({
  group: Object,

  students: Array, 
  // subjects: Array,
  studentsWithoutGroup: Array,
  rooms: Array, 
  // tasks: Array,
});

let promptIsOpen = ref(false);

</script>
<template>
  <q-card class="my-card">
    <q-card-section class="bg-secondary text-white">
      <div class="text-h6" style="display: flex; justify-content: space-between">
        <div>{{props.group.name}}</div>
        <div>Студентов в группе: {{props.group.countOfStudents}}</div>
      </div>

      <div class="mt-2">

        <div class="text-subtitle2" v-for="room in props.group.rooms">
          {{room.name}}
        </div>

        <div class="text-subtitle2" v-for="subject in props.group.subjects">
          {{subject.name}}
        </div>

        <div class="text-subtitle2" v-for="task in props.group.tasks">
          {{task.name}}
        </div>

        <div class="text-subtitle2" v-for="student in props.group.students">
          {{student.last_name}} {{student.first_name}} {{student.patronymic}}
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
        @click="() => emits('delete-click', props.group)"
      >
      Удалить
      </q-btn>
    </q-card-actions>
    <group-dialog
      :students="students"
      :studentsWithoutGroup="props.studentsWithoutGroup"
      :rooms="rooms"
      :prompt="promptIsOpen"
      :group="props.group"
      @prompt-close="promptIsOpen = false"
      @update-click="(newGroup) => emits('update-click', newGroup)"
    />
  </q-card>
</template>
<style scoped></style>