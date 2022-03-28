<script setup>
 
import { onMounted, ref, watch, inject, computed } from "vue";

const store = inject('store');

const emits = defineEmits(['update-click', 'prompt-close']);

const props = defineProps({
  prompt: Boolean,
  teacher: Object,
  updateButtonLabel: {
    default: 'Изменить',
    type: String
  },
  subjects: Array, 
  rooms: Array,
  materials: Array, 
  tasks: Array,
});

let newTeacher = ref({
  first_name: null,
  last_name:  null,
  patronymic:  null,
  email:  null,
  login:  null,
});

onMounted(() => {
  if (props.teacher)
    newTeacher.value = JSON.parse(JSON.stringify(props.teacher));
    console.log(newTeacher.value);
});

const newTeacherFormatted = computed(() => {
  let newTeacherFormatted = JSON.parse(JSON.stringify(newTeacher.value));

  newTeacherFormatted.materials = store.methods.idArrToObjs(newTeacherFormatted.materials);
  newTeacherFormatted.tasks = store.methods.idArrToObjs(newTeacherFormatted.tasks);
  newTeacherFormatted.rooms = store.methods.idArrToObjs(newTeacherFormatted.rooms);
  newTeacherFormatted.subjects = store.methods.idArrToObjs(newTeacherFormatted.subjects);

  return newTeacherFormatted;
});

</script>

<template>
  <q-dialog v-model="props.prompt" persistent>
    <q-card style="min-width: 350px" @keyup.enter="emits('update-click', newTeacher)">
      <q-card-section>
        <div class="text-h5">Редактирование</div>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <q-input dense v-model="newTeacher.last_name" autofocus label="Фамилия"/>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <q-input dense v-model="newTeacher.first_name" autofocus label="Имя"/>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <q-input dense v-model="newTeacher.patronymic" autofocus label="Отчество"/>
      </q-card-section>
      
      <q-card-section class="q-pt-none">
        <q-input dense v-model="newTeacher.email" autofocus label="Email"/>
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
          label="Материалы"
          :options="props.materials"
          v-model="newTeacher.materials"
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
          label="Предметы"
          :options="props.subjects"
          v-model="newTeacher.subjects"
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
          v-model="newTeacher.rooms"
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
          v-model="newTeacher.tasks"
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
        <q-btn flat :label="updateButtonLabel" @click="emits('update-click', newTeacherFormatted)" />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<style scoped>
</style>
