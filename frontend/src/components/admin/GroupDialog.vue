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


// const subjectsOptions = computed(() => {
  // let ss = [{"id":"891c56ad-7229-427f-bb87-fd60657540e1", "name": "Сетевые технологии телекоммуникации и сети"}];
  //
  // for (let room in newGroup.rooms){
  //   for (let subjects in room){
  //     ss.push(subjects?.reduce((a, b) => [...a, ...b], []));
  //   }}
  // return ss;
  // return newGroup.rooms;
// });

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
<!--                <span>{{newGroup.rooms?.map(r => r.subjects).flat(Infinity)}}</span>-->
<!--                <span>{{newGroup.rooms?.map(r => r?.subjects).flat(1)}}</span>-->
<!--                <span>{{newGroup.rooms?.map(r => r?.subjects).flat(Infinity)}}</span> &lt;!&ndash; просто вывел список предметов в выбранных комнатах &ndash;&gt;-->
<!--                <span>{{newGroup.rooms?.map(r => r?.subjects).reduce((subjectId, subjectName) => [...subjectId, ...subjectName], [])}}</span>-->
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
          :options="newGroup.rooms?.map(r => r.subjects).flat(Infinity)"
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
