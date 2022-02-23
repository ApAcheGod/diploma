<script setup>
import { computed } from 'vue';

const emits = defineEmits(['update-click', 'delete-click']);
const props = defineProps({
    student: Object,
});
const initials = computed(() => {
    return `${props.student.first_name[0].toUpperCase()}${props.student.patronymic[0].toUpperCase()}`
});

</script>
<template>
  <q-card class="my-card">
    <q-card-section class="bg-secondary text-white">
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
        @click="() => emits('update-click', props.student)"
        >
        Изменить
      </q-btn>
      <q-btn 
      color="accent" 
      flat
      @click="() => emits('delete-click', props.student)"
      >
      Удалить
      </q-btn>
    </q-card-actions>
    </q-card>
</template>
<style scoped></style>