<script setup>
import { computed } from '@vue/reactivity';
import { useStore } from 'vuex';
import JournalBlock from './JournalBlock.vue';
import {onMounted} from "vue";

const store = useStore();
const _formattedJournalData = computed(() => {
  return store.getters.getFormattedJournal;
});
const tableHeads = computed(() => {
  return _formattedJournalData.value.roomSubjectsHeadByTasks;
})
const examsByStudentIdTaskId = computed(() => {
  return _formattedJournalData.value.examsByStudentIdTaskId;
})
const solutionsByStudentIdTaskId = computed(() => {
  return _formattedJournalData.value.solutionsByStudentIdTaskId;
})
// onMounted(() => {
//   console.log(_formattedJournalData);
// });
const getMark = (studentId, taskId) => {
  console.log(`taskid - ${taskId}`);
  const examinationResult = examsByStudentIdTaskId.value.get(studentId).get(taskId);
  const solutionResult = solutionsByStudentIdTaskId.value.get(studentId).get(taskId);
  const examination = examinationResult ? examinationResult[0] : examinationResult;
  const solution = solutionResult ? solutionResult[0] : solutionResult;
  if (examination)
    return examination.examinationStatus;
  if (solution)
    return 'Сдано';
  return 'Не сдано';
};

const getGroupResults = (group, subject) => {
  group.students
  .forEach(student => {
    subject.tasks.forEach(task => {
      student[task.id] = getMark(student.id, task.id); 
    })
  });
  return group;
};

</script>
<template>

<div class="journal-room-block" v-for="head in tableHeads">
    <div class="journal-room-block__title">
      <hr/>
      {{head.roomName}}
    </div>
    <div class="journal-subject-block" v-for="subject in head.roomSubjects">
      <div class="journal-subject-block__title">{{subject.name}}</div>
      <div class="journal-group-table" v-for="group in subject.groups">
        <q-table
          :rows="getGroupResults(group, subject).students"
          :columns="subject.tasksTableHead"
          row-key="name"
        />
        <!-- :title="subject.name" -->
      </div>
    </div>
  </div>
</template>
<style lang="scss">
.journal-room-block {
  margin: 24px;
  &__title {
    font-family: 'Montserrat';
    text-transform: uppercase;
    font-weight: 500;
    font-size: 19px;
    letter-spacing: 0.03rem;
    margin-bottom: 16px;
    hr {
      margin-bottom: 8px;
      margin-top: 8px;
    }
  }
}
.journal-subject-block {
  &__title {
    font-family: 'Montserrat';
  }
}
.journal-group-table {
  &__title {
    font-family: 'Montserrat';
  }
}
</style>