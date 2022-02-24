<script setup>
 
import { onMounted, ref, watch } from "vue";

const emits = defineEmits(['update-click', 'prompt-close']);

const props = defineProps({
  prompt: Boolean,
  student: Object,
});

let newStudent = ref({
  id: "",
  first_name:  "",
  last_name:  "",
  patronymic:  "",
  email:  "",
  login:  "",
});

onMounted(() => {
  if (props.student) {
    newStudent.value.id =  props.student.id;
    newStudent.value.first_name =  props.student.first_name;
    newStudent.value.last_name =  props.student.last_name;
    newStudent.value.patronymic =  props.student.patronymic;
    newStudent.value.email =  props.student.email;
    newStudent.value.login =  props.student.login;
  }
});

</script>

<template>
  <q-dialog v-model="props.prompt" persistent>
    <q-card style="min-width: 350px">
      <q-card-section>
        <div class="text-h5">Редактирование</div>
      </q-card-section>
      <q-card-section class="q-pt-none">
        <q-input dense v-model="newStudent.last_name" autofocus label="Фамилия"/>
      </q-card-section>
      <q-card-section class="q-pt-none">
        <q-input dense v-model="newStudent.first_name" autofocus label="Имя"/>
      </q-card-section>
      <q-card-section class="q-pt-none">
        <q-input dense v-model="newStudent.patronymic" autofocus label="Отчество"/>
      </q-card-section>
      <q-card-section class="q-pt-none">
        <q-input dense v-model="newStudent.email" autofocus label="Email"/>
      </q-card-section>
      <q-card-actions align="right" class="text-primary">
        <q-btn flat label="Отмена"  @click="emits('prompt-close')"/>
        <q-btn flat label="Изменить" @click="emits('update-click', newStudent)" />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<style scoped>
</style>
