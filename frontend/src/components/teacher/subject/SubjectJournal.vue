<script setup>
import { computed, ref } from "vue";
import { useStore } from 'vuex';

import EmptyMessage from "../../base/EmptyMessage.vue";
import methods from '../../../store/methods';
import actionsTypes from "../../../store/actionsTypes";

const store = useStore();

const subjectJournal = computed(() => {
  const formatted = store.getters.getActiveSubjectJournal;
  // if (!formatted) store.dispatch(actionsTypes.DELETE_ACTIVE_SUBJECT);
  return formatted;
});

</script>
<template>
    <div class="journal-room-block" v-if="subjectJournal && subjectJournal.groups && subjectJournal.groups.length > 0">
      <div class="journal-group-block" v-for="group in subjectJournal.groups">
        <div class="journal-group-block__title">
          <hr/>
          {{group.name}}
        </div>
        <div class="journal-group-block__table">
          <q-table
            :rows="group.students"
            :columns="subjectJournal.tableHead"
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
    </div>
    <empty-message v-else>
      Нет групп студентов, прикрепленных к предмету
    </empty-message>
</template>
<style lang="scss" scoped>
</style>