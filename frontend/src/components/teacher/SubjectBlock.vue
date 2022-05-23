<script setup>
import { onMounted } from 'vue';
import SubjectCard from "./SubjectCard.vue";
const props = defineProps({
  room : Object,
});

onMounted(() => {
  console.log(props.room);
});

</script>
<template>
  <template v-if="props?.room?.roomSubjects && props.room.roomSubjects.length > 0">
    <div class="subject-block">
      <div class="subject-block__title">
        <hr/>
        {{props.room.roomName}}
      </div>
      <div class="subject-block__subjects">
        <transition-group name="list" >
          <subject-card v-for="subject in props.room.roomSubjects" :subject="subject"/>
          <div key="add-el" class="room-card-add">
            <!-- <q-input v-model="newRoomName" label="Название комнаты"/> -->
            <button class="room-card-add__button">
              <img class="room-card-add__icon" src="../../img/add.svg"/>
            </button>
          </div>
        </transition-group>
      </div>
    </div>
  </template>
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
.room-card-add {
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
.room-card-add__button {
  border-radius: 50px;
  padding: 21px;
  background: #03DAC5;
  transition: .3s;
  box-shadow: 0px 4px 5px rgba(0, 0, 0, 0.14), 0px 1px 10px rgba(0, 0, 0, 0.12), 0px 2px 4px rgba(0, 0, 0, 0.2);
}
.room-card-add__button:hover {
  background: rgba(3, 151, 135, 0.8);
  transition: .3s;
}
</style>