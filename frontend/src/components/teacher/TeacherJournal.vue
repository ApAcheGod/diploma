<script setup>
import { computed } from '@vue/reactivity';
import { useStore } from 'vuex';
import {onMounted} from "vue";
import JournalBlock from './JournalBlock.vue';
import workStatuses from '../../models/workStatuses';

const store = useStore();
const _formattedJournalData = computed(() => {
  return store.getters.getFormattedJournal;
});
const tableHeads = computed(() => {
  return _formattedJournalData.value.roomSubjectsHeadByTasks;
})

const getBadgeColor = (workStatus) => {
  switch (workStatus) {
    case workStatuses.NOT_COMPLETED:
      return 'blue';
      case workStatuses.COMPLETED:
        return 'orange';
      case workStatuses.CORRECTLY:
        return 'green';
      case workStatuses.INCORRECTLY:
        return 'red';
    default:
      return 'black';
  }
}

</script>
<template>

<div class="journal-room-block" v-for="head in tableHeads">
    <div class="journal-room-block__title">
      <hr/>
      {{head.roomName}}
    </div>
    <div class="journal-subject-block" v-for="subject in head.roomSubjects">
      <div class="journal-subject-block__title">
        <hr/>
        {{subject.name}}
      </div>
      <div class="journal-group-block" v-for="group in subject.groups">
        <div class="journal-group-block__title">
          <hr/>
          {{group.name}}
        </div>
        <div class="journal-group-block__table">
          <q-table
            :rows="group.students"
            :columns="subject.tasksTableHead"
            row-key="name"
            no-data-label="Нет данных"
          >
            <template v-slot:body-cell="props">
              <q-td :props="props">
                <q-badge outline :color="getBadgeColor(props.value)" :label="props.value" />
              </q-td>
            </template>
            <template v-slot:body-cell-name="props">
              <q-td :props="props">
                {{props.value}}
              </q-td> 
            </template>
          </q-table>
        </div>
      </div>
    </div>
  </div>
</template>
<style lang="scss">
hr {
  margin-bottom: 8px;
  margin-top: 8px;
}
.journal-room-block {
  margin: 24px;
  &__title {
    font-family: 'Montserrat';
    text-transform: uppercase;
    font-weight: 500;
    font-size: 19px;
    letter-spacing: 0.03rem;
    margin-bottom: 16px;

  }
}
.journal-subject-block {
  margin-left: 16px;
  display: flex;
  flex-direction: column;
  gap: 16px;
  &__title {

    font-family: 'Montserrat';
  }
}
.journal-group-block {
  margin-left: 32px;
  display: flex;
  flex-direction: column;
  gap: 16px;
  &__title {
    font-family: 'Montserrat';
  }
  &__table {
    font-family: 'Montserrat';
  }
}
</style>