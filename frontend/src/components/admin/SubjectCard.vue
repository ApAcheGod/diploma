<script setup>
import {computed, ref} from 'vue';
import SubjectDialog from './SubjectDialog.vue';

const emits = defineEmits(['update-click', 'delete-click']);
const props = defineProps({
    subject: Object,
    promptIsOpen: {
      default: false,
      type: Boolean,
    },
    tasks: Array,
    rooms: Array,
    materials: Array,
    teachers: Array,
    groups: Array,
});

let promptIsOpen = ref(false);

const initials = computed(() => {
    return `${props.subject.first_name[0].toUpperCase()}${props.subject.patronymic[0].toUpperCase()}`
});

</script>
<template>
  <q-card class="my-card">
    <q-card-section class="bg-secondary text-white">
      <div class="text-h6">   
        {{props.subject.name}}
      </div>
      <div class="text-subtitle2">
        {{props.subject.roomName}}
      </div>
      <div class="text-subtitle2">
        {{props.subject.teacherName}}
      </div>
      <div class="text-subtitle2" v-for="material in props.subject.materials">        
        {{material.name}} 
      </div>
      <div class="text-subtitle2" v-for="task in props.subject.tasks">        
        {{task.name}} 
      </div>
      <div class="text-subtitle2" v-for="group in props.subject.groups">
        {{group.name}}
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
      @click="() => emits('delete-click', props.subject)"
      >
      Удалить
      </q-btn>
    </q-card-actions>
    <subject-dialog
    :prompt="promptIsOpen"
    :subject="props.subject"
    :rooms="props.rooms"
    :materials="props.materials"
    :tasks="props.tasks"
    :teachers="props.teachers"
    :groups="props.groups"
    @prompt-close="promptIsOpen = false"
    @update-click="(newSubject) => emits('update-click', newSubject)"
    />
  </q-card>
</template>
<style scoped></style>