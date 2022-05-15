<script setup>
import {ref} from 'vue';
import RoomDialog from './RoomDialog.vue';

const emits = defineEmits(['update-click', 'delete-click']);
const props = defineProps({
  room: Object,
  teachers: Array,
});

let promptIsOpen = ref(false);

</script>
<template>
  <q-card class="my-card">
    <q-card-section class="bg-secondary text-white">
      <div class="text-h6">   
        {{props.room.name}}
      </div>

      <div class="text-subtitle2">   
        {{props.room.teacherName}}
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
        @click="() => emits('delete-click', props.room)"
      >
      Удалить
      </q-btn>
    </q-card-actions>
    <room-dialog
      :teachers="teachers"
      :room="props.room"
      :prompt="promptIsOpen"
      @prompt-close="promptIsOpen = false"
      @update-click="(newRoom) => emits('update-click', newRoom)"
    />
  </q-card>
</template>
<style scoped></style>