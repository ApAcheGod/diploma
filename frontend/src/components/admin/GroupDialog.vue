<script setup>

import {computed, inject, onMounted, ref} from "vue";

const store = inject('store');

const emits = defineEmits(['update-click', 'prompt-close']);

const props = defineProps({
  prompt: Boolean,
  group: Object,
  updateButtonLabel: {
    default: 'Изменить',
    type: String
  },
  students: Array,
  subjects: Array,
  rooms: Array,
  tasks: Array,
});

let newGroup = ref({});

const studentsOptions = computed(() => {
  return props.students?.map(s => {
    s.name = `${s.last_name} ${s.first_name} ${s.patronymic}`;
    return s;
  });
});

const availableSubjects = computed(() => {
  if (!newGroup.value.rooms || !props.rooms) return [];
  let currentRoomsData = [];
  newGroup.value.rooms.forEach(room => {
      let findedRoomData = props.rooms.find(roomData => {
        if (typeof room === 'object')
          return roomData.id === room.id;
        return roomData.id === room;
      });
      if (findedRoomData && findedRoomData.subjects) 
        currentRoomsData.push(findedRoomData);
    });
  if (currentRoomsData.length === 0) return [];
  let availableSubjects = [];
  currentRoomsData.forEach(room => availableSubjects = availableSubjects.concat(room.subjects));
  return availableSubjects;
});

onMounted(() => {
  if (props.group)
    newGroup.value = JSON.parse(JSON.stringify(props.group));
});

const newGroupFormatted = computed(() => {
  let newGroupFormatted = JSON.parse(JSON.stringify(newGroup.value));

  newGroupFormatted.students = store.methods.idArrToObjs(newGroupFormatted.students);
  newGroupFormatted.tasks = store.methods.idArrToObjs(newGroupFormatted.tasks);
  newGroupFormatted.rooms = store.methods.idArrToObjs(newGroupFormatted.rooms);
  newGroupFormatted.subjects = store.methods.idArrToObjs(newGroupFormatted.subjects);

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

      <q-card-section class="q-pt-none" v-if="newGroup.rooms && newGroup.rooms.length > 0">
        <q-select
          filled
          emit-value
          map-options
          multiple
          use-chips
          stack-label
          option-value="id"
          option-label="name"
          label="Предметы"
          :options="availableSubjects"
          v-model="newGroup.subjects"
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
          label="Задания"
          :options="props.tasks"
          v-model="newGroup.tasks"
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
