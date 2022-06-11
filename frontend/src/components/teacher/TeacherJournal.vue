<script setup>
import { computed } from '@vue/reactivity';
import { useStore } from 'vuex';
import methods from '../../store/methods';

const store = useStore();
const formattedJournalData = computed(() => {
  return store.getters.getFormattedJournal;
});

</script>
<template>
  <div class="journal-room-block" v-for="room in formattedJournalData">
    <div class="journal-room-block__title">
      <hr/>
      {{room.roomName}}
    </div>
    <div class="journal-subject-block" v-for="subject in room.roomSubjects">
      <div class="journal-subject-block__title">
        <hr/>
        {{subject.name}}
      </div>
      <template v-if="subject.groups && subject.groups.length > 0">
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
              no-data-label="В группе нет студентов"
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
      <template v-else>
        <div class="journal-group-block journal-group-block__title">
          Нет групп
        </div>
      </template>
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
  margin-bottom: 16px;
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