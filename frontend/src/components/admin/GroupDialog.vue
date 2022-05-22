<script setup>
import methods from '../../store/methodsAdmin.js';

import {computed, onMounted, ref} from "vue";



const emits = defineEmits(['update-click', 'prompt-close']);

const props = defineProps({
  prompt: Boolean,
  group: Object,
  updateButtonLabel: {
    default: 'Изменить',
    type: String
  },
  students: Array,
  rooms: Array,
  studentsWithoutGroup: Array,
});

let newGroup = ref({});

const studentsOptions = computed(() => {
  if (props.group === undefined || !props.group.students) return props.studentsWithoutGroup;
  if (props.studentsWithoutGroup.length == 0) return props.group.students;
  
  let availableStudents = props.group.students.concat(props.studentsWithoutGroup);

  return availableStudents.map(s => {
    s.name = `${s.last_name} ${s.first_name} ${s.patronymic}`;
    return s;
  });
});

onMounted(() => {
  if (props.group)
    newGroup.value = JSON.parse(JSON.stringify(props.group));
});

const newGroupFormatted = computed(() => {
  let newGroupFormatted = JSON.parse(JSON.stringify(newGroup.value));

  newGroupFormatted.students = methods.idArrToObjs(newGroupFormatted.students);
  newGroupFormatted.rooms = methods.idArrToObjs(newGroupFormatted.rooms);

  return newGroupFormatted;
});

</script>

<template>
  <q-dialog v-model="props.prompt" persistent>
    <q-card style="min-width: 350px" @keyup.esc="emits('prompt-close')" @keyup.enter="emits('update-click', newGroupFormatted)">
      <q-card-section>
        <div class="text-h5">Редактирование</div>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <q-input dense v-model="newGroup.name" autofocus label="Название"/>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <q-select
          filled
          emit-value
          map-options
          multiple
          use-chips
          stack-label
          option-value="id"
          option-label="name"
          label="Студенты"
          :options="studentsOptions"
          v-model="newGroup.students"
        >
          <template v-slot:option="{ itemProps, opt, selected, toggleOption }">
            <q-item v-bind="itemProps">
              <q-item-section>
                <q-item-label v-html="opt.name" />
              </q-item-section>
              <q-item-section side>
                <q-toggle :model-value="selected" @update:model-value="toggleOption(opt)" />
              </q-item-section>
            </q-item>
          </template>
        </q-select>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <q-select
            filled
            emit-value
            map-options
            multiple
            use-chips
            stack-label
            option-value="id"
            option-label="name"
            label="Комнаты"
            :options="props.rooms"
            v-model="newGroup.rooms"
        >
          <template v-slot:option="{ itemProps, opt, selected, toggleOption }">
            <q-item v-bind="itemProps">
              <q-item-section>
                <q-item-label v-html="opt.name" />
              </q-item-section>
              <q-item-section side>
                <q-toggle :model-value="selected" @update:model-value="toggleOption(opt)" />
              </q-item-section>
            </q-item>
          </template>
        </q-select>
      </q-card-section>

      <q-card-actions align="right" class="text-primary">
        <q-btn flat label="Отмена"  @click="emits('prompt-close')"/>
        <q-btn flat :label="updateButtonLabel" @click="emits('update-click', newGroupFormatted)" />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<style scoped>
</style>
