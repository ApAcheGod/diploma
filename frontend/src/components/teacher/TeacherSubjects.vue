<script setup>
import SubjectCard from "./SubjectCard.vue";
import actionsTypes from "../../store/actionsTypes";
import EmptyMessage from "../base/EmptyMessage.vue";
import {ref} from "vue";
import {useQuasar} from "quasar";
import {useStore} from "vuex";
import {computed} from "vue";

const q = useQuasar();
const store = useStore();
const subjectByRooms = computed(() => store.getters.getTeacherSubjects);
const teacher = computed(() => store.getters.getUserData);
const newSubjectName = ref([]);

function createSubject(subject){
  if (!subject.name) {
    q.notify({
      type: 'negative',
      message: 'Название предмета не может быть пустым',
    });
    return; 
  }
  if (!subject.teacherId || !subject.roomId) {
    q.notify({
      type: 'negative',
      message: 'Системная ошибка при создании предмета',
    });
    return; 
  }
  store.dispatch(actionsTypes.CREATE_SUBJECT, subject)
      .then(() => {
        q.notify({type: 'positive', message: 'Предмет успешно создан'})
        newSubjectName.value = '';
      })
      .catch(error => {
        console.error(error);
        q.notify({type: 'negative', message: 'Ошибка при создании предмета'})
      })
}

function deleteSubject(subject) {
  if (!subject.id) {
    q.notify({
      type: 'negative',
      message: 'Системная ошибка при удалении предмета',
    });
    return; 
  }
  store.dispatch(actionsTypes.DELETE_SUBJECT, subject)
      .then(() => {
        q.notify({type: 'positive', message: 'Предмет успешно удален'})
      })
      .catch(error => {
        console.error(error);
        q.notify({type: 'negative', message: 'Ошибка при удалении предмета'})
      })
}

</script>
<template>
<template v-if="subjectByRooms && subjectByRooms.length > 0">
  <div class="subject-block" v-for="(room, index) in subjectByRooms">
    <div class="subject-block__title">
      <hr/>
      {{room.roomName}}
    </div>
    <div class="subject-block__subjects">
      <transition-group name="list" >
        <subject-card v-for="subject in room.roomSubjects"
          :key="subject.id"
          :subject="subject"
          v-on:info="store.dispatch(actionsTypes.SET_ACTIVE_SUBJECT, subject)"
          v-on:delete="deleteSubject"
          v-on:cancel="roomIsNotInfo = true"
        />
        <div key="add-el" class="subject-card-add">
          <div key="add-el" class="room-card-add">
            <q-input v-model="newSubjectName[index]" label="Название предмета"/>
          </div>
          <button class="subject-card-add__button"
            v-on:click="createSubject({name: newSubjectName[index], teacherId:  teacher.id, roomId: room.roomId,}); newSubjectName[index] = ''">
            <img class="subject-card-add__icon" src="../../img/add.svg"/>
          </button>
        </div>
      </transition-group>
    </div>
  </div>
</template>
<template v-else>
  <empty-message>Нет доступных комнат</empty-message>
</template>
  
</template>
<style lang="scss">

</style>
<style lang="scss" scoped>
.subject-block {
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
  &__subjects {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    gap: 24px;
  }
}
.subject-card-add {
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 24px;
  align-items: center;
  background-color: white;
  filter: drop-shadow(0px 3px 6px rgba(0, 0, 0, 0.161));
  width: 304px;
  height: 344px;
  &__button {
    border-radius: 50px;
    padding: 21px;
    background: #03DAC5;
    transition: .3s;
    box-shadow: 0px 4px 5px rgba(0, 0, 0, 0.14), 0px 1px 10px rgba(0, 0, 0, 0.12), 0px 2px 4px rgba(0, 0, 0, 0.2);
    &__hover {
      background: rgba(3, 151, 135, 0.8);
      transition: .3s;
    }
  }
}
</style>