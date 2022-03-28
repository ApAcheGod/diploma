<script setup>
 
import { onMounted, ref, watch, computed } from "vue";

const emits = defineEmits(['update-click', 'prompt-close']);

const props = defineProps({
  prompt: Boolean,
  subject: Object,
  updateButtonLabel: {
    default: 'Изменить',
    type: String
  },
  tasks: Array,
  materials: Array,
  teachers: Array,
});

let newSubject = ref({
  id: null,
  name: null,
  teacherId:  null,
  teacherName:  null,
  materials: [],
  tasks: [],
});

onMounted(() => {
  if (props.subject) {
    newSubject.value.id =  props.subject.id;
    newSubject.value.name =  props.subject.name;
    newSubject.value.teacherId =  props.subject.teacherId;
    newSubject.value.teacherName =  props.subject.teacherName;
    newSubject.value.materials =  props.subject.materials;
    newSubject.value.tasks =  props.subject.tasks;
  }
});

const teacherOptions = computed(() => {
  return props.teachers?.map(t => {
    t.teacherId = t.id;
    return t;
  });
});

const newSubjectFormatted = computed(() => {
  let newSubjectFormatted = JSON.parse(JSON.stringify(newSubject.value));
  if (!newSubjectFormatted.id)
    delete newSubjectFormatted.id

  newSubjectFormatted.materials = newSubjectFormatted.materials.map((m) => {
    if (typeof m === 'string' || m instanceof String)
      return {
        id: m,
      }
    else 
      return m;
  });

  newSubjectFormatted.tasks = newSubjectFormatted.tasks.map((t) => {
    if (typeof t === 'string' || t instanceof String)
      return {
        id: t,
      }
    else 
      return t;
  });

  return newSubjectFormatted;
});

</script>

<template>
  <q-dialog v-model="props.prompt" persistent>
    <q-card style="min-width: 350px" @keyup.enter="emits('update-click', newSubjectFormatted)">

      <q-card-section>
        <div class="text-h5">Редактирование</div>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <q-input dense v-model="newSubject.name" autofocus label="Название"/>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <q-select
          filled
          emit-value
          map-options
          option-value="teacherId"
          option-label="teacherName"
          label="Преподаватель"
          :options="teacherOptions"
          v-model="newSubject.teacherId"
        />
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
          v-model="newSubject.materials"
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
          v-model="newSubject.tasks"
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
        <q-btn flat :label="updateButtonLabel" @click="emits('update-click', newSubjectFormatted)" />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<style scoped>
</style>
