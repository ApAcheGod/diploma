<script setup>
import SubjectCard from "./SubjectCard.vue";
import actionsTypes from "../../store/actionsTypes";
import {ref} from "vue";
import {useQuasar} from "quasar";
import {useStore} from "vuex";
import {computed} from "vue";

const subjectByRooms = computed(() => store.getters.getUserSubjects);

const props = defineProps({
  room : Object,
});

const teacher = computed(() => {
  return store.getters.getUserData;
});

const $q = useQuasar();
const store = useStore();
const newSubjectName = ref('');
const subjectIsNotEditing = ref(true);

function createSubject(subject){
  store.dispatch(actionsTypes.CREATE_SUBJECT, subject)
      .then(() => {
        $q.notify({type: 'positive', message: 'Предмет успешно создан'})
        newSubjectName.value = '';
        subjectIsNotEditing.value = true
      })
      .catch(error => {
        console.error(error);
        $q.notify({type: 'negative', message: 'Ошибка при создании предмета'})
      })
}

function deleteSubject(subject) {
  store.dispatch(actionsTypes.DELETE_SUBJECT, subject)
      .then(() => {
        $q.notify({type: 'positive', message: 'Предмет успешно удален'})
        subjectIsNotEditing.value = true
      })
      .catch(error => {
        console.error(error);
        $q.notify({type: 'negative', message: 'Ошибка при удалении предмета'})
      })
}

</script>
<template>
  <div class="subject-block" v-for="room in subjectByRooms">
    <div class="subject-block__title">
      <hr/>
      {{room.roomName}}
    </div>
    <div class="subject-block__subjects">
      <transition-group name="list" >
        <subject-card v-for="subject in room.roomSubjects"
          :key="subject.id"
          :subject="subject"
          v-on:info="roomIsNotInfo = false"
          v-on:delete="deleteSubject"
          v-on:cancel="roomIsNotInfo = true"
        />
        <div key="add-el" class="subject-card-add">
          <div key="add-el" class="room-card-add">
            <q-input v-model="newSubjectName" label="Название предмета"/>
          </div>
          <button class="subject-card-add__button"
            v-on:click="createSubject({
            name: newSubjectName,
            teacherId:  teacher.id,
            roomId: props.room.roomId})">
            <img class="subject-card-add__icon" src="../../img/add.svg"/>
          </button>
        </div>
      </transition-group>
    </div>
  </div>
</template>
<style lang="scss">
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
}
.subject-card-add__button {
  border-radius: 50px;
  padding: 21px;
  background: #03DAC5;
  transition: .3s;
  box-shadow: 0px 4px 5px rgba(0, 0, 0, 0.14), 0px 1px 10px rgba(0, 0, 0, 0.12), 0px 2px 4px rgba(0, 0, 0, 0.2);
}
.subject-card-add__button:hover {
  background: rgba(3, 151, 135, 0.8);
  transition: .3s;
}
</style>