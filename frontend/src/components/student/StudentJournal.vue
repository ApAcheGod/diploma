<script setup>
import { computed } from '@vue/reactivity';
import { useStore } from 'vuex';
import EmptyMessage from '../base/EmptyMessage.vue';
import methods from '../../store/methods';


const store = useStore();
const journal = computed(() => store.getters.getStudentJournal)

</script>
<template>
  <template v-if="journal && journal.length > 0">
    <div class="journal-subject-block" v-for="subject in journal">
      <div class="journal-subject-block__title">{{subject.name + " | " + subject.teacherName}}</div>
      <hr>
        <div class="journal-group-block__table">
          <q-table
            :rows="subject.tasks"
            :columns="[
              {
                name: 'name',
                required: true,
                label: 'Задание',
                align: 'left',
                field: row => row.name,
                format: val => `${val}`,
                sortable: true 
              },
              {
                name: 'mark',
                required: true,
                label: 'Оценка',
                align: 'left',
                field: row => row.mark,
                format: val => `${val}`,
                sortable: true 
              },
            ]"
            row-key="name"
            no-data-label="Нет заданий по предмету"
          >
            <template v-slot:body-cell="props">
              <q-td :props="props">
                <q-badge outline :color="methods.getBadgeColor(props.value)" :label="props.value" />
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
  </template>
  <empty-message v-else>Нет доступных предметов</empty-message>
</template>
<style scoped lang="scss">
hr {
  margin-bottom: 8px;
  margin-top: 8px;
}
.journal-subject-block {
  margin: 24px;
  display: flex;
  flex-direction: column;
  gap: 16px;
  margin-bottom: 16px;
  &__title {
    font-family: 'Montserrat';
  }
}
</style>