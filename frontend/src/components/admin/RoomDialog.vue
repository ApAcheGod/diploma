<script setup>
 
import { onMounted, ref, watch, inject, computed } from "vue";

const store = inject('store');

const emits = defineEmits(['update-click', 'prompt-close']);

const props = defineProps({
  prompt: Boolean,
  room: Object,
  updateButtonLabel: {
    default: 'Изменить',
    type: String
  },
  teachers: Array, 
});

let newRoom = ref({});

onMounted(() => {
  if (props.room)
    newRoom.value = JSON.parse(JSON.stringify(props.room));
});

const teacherOptions = computed(() => {
  return props.teachers?.map(t => {
    t.teacherId = t.id;
    return t;
  });
});

</script>

<template>
  <q-dialog v-model="props.prompt" persistent>
    <q-card style="min-width: 350px" @keyup.esc="emits('prompt-close')" @keyup.enter="emits('update-click', newRoom)">
      <q-card-section>
        <div class="text-h5">Редактирование</div>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <q-input dense v-model="newRoom.name" autofocus label="Название"/>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <q-select
          filled
          v-model="newRoom.teacherId"
          :options="teacherOptions"
          option-value="teacherId"
          option-label="teacherName"
          label="Преподаватель"
          emit-value
          map-options
        />
      </q-card-section>

      <q-card-actions align="right" class="text-primary">
        <q-btn flat label="Отмена"  @click="emits('prompt-close')"/>
        <q-btn flat :label="updateButtonLabel" @click="emits('update-click', newRoom)" />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<style scoped>
</style>
