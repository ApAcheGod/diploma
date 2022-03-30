<script setup>
 
import { onMounted, ref, watch, inject, computed } from "vue";

const store = inject('store');

const emits = defineEmits(['update-click', 'prompt-close']);

const props = defineProps({
  prompt: Boolean,
  task: Object,
  updateButtonLabel: {
    default: 'Изменить',
    type: String
  },
  subjects: Array, 
  groups: Array,
  teachers: Array, 
  solutions: Array,
});

let newTask = ref({});

onMounted(() => {
  if (props.task)
    newTask.value = JSON.parse(JSON.stringify(props.task));
});

const newTaskFormatted = computed(() => {
  let newTaskFormatted = JSON.parse(JSON.stringify(newTask.value));

  newTaskFormatted.groups = store.methods.idArrToObjs(newTaskFormatted.groups);
  newTaskFormatted.solutions = store.methods.idArrToObjs(newTaskFormatted.solutions);

  return newTaskFormatted;
});

const teacherOptions = computed(() => {
  return props.teachers?.map(t => {
    t.teacherId = t.id;
    return t;
  });
});

const subjectOptions = computed(() => {
  return props.subjects?.map(s => {
    s.subjectId = s.id;
    s.subjectName = s.name;
    return s;
  });
});

</script>

<template>
  <q-dialog v-model="props.prompt" persistent>
    <q-card style="min-width: 350px" @keyup.esc="emits('prompt-close')" @keyup.enter="emits('update-click', newTaskFormatted)">
      <q-card-section>
        <div class="text-h5">Редактирование</div>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <q-input dense v-model="newTask.name" autofocus label="Название"/>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <q-select
          filled
          v-model="newTask.subjectId"
          :options="subjectOptions"
          option-value="subjectId"
          option-label="subjectName"
          label="Предмет"
          emit-value
          map-options
        />
      </q-card-section>

      <q-card-section class="q-pt-none">
        <q-select
          filled
          v-model="newTask.teacherId"
          :options="teacherOptions"
          option-value="teacherId"
          option-label="teacherName"
          label="Преподаватель"
          emit-value
          map-options
        />
      </q-card-section>

      <q-card-section class="q-pt-none">
        <q-input dense v-model="newTask.text" autofocus label="Текст задания"/>
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
          label="Группы"
          :options="props.groups"
          v-model="newTask.groups"
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
          option-label="studentName"
          label="Решения"
          :options="props.solutions"
          v-model="newTask.solutions"
        >
          <template v-slot:option="{ itemProps, opt, selected, toggleOption }">
            <q-item v-bind="itemProps">
              <q-item-section>
                <q-item-label v-html="opt.text" />
                <q-item-label v-html="opt.studentName" />
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
        <q-btn flat :label="updateButtonLabel" @click="emits('update-click', newTaskFormatted)" />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<style scoped>
</style>
