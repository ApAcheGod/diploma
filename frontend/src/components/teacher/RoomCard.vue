<script setup>
import { useQuasar } from 'quasar'
import { ref } from 'vue';

const q = useQuasar();
const props = defineProps({
  room: Object,
  allowEditing: Boolean,
});
const emits = defineEmits(['edit', 'delete', 'save', 'cancel']);
const isEditing = ref(false);
const newName = ref(props.room?.name);

const updatedRoom = function() {
  const newRoom = {...props.room, ...{name : newName.value}};
  newRoom.name = newName.value;
  return newRoom;
}

</script>
<template>
  <div class="room-card">
    <div class="room-card__title">
      <template v-if="!isEditing">
        {{props.room.name}}
      </template>
      <template v-if="isEditing">
        <q-input v-model="newName"/>
      </template>
    </div>
    <div class="room-card__body">
      <img src="../../img/university-default.png" class="room-card__image">
    </div>
    <div class="room-card__actions">
      <template v-if="props.allowEditing">
        <button 
          class="room-card__button room-card__button_edit"
          @click="{emits('edit'); isEditing = true}">
          Редактировать
        </button>
        <button 
          class="room-card__button room-card__button_delete"
          @click="emits('delete', room)">
          Удалить
        </button>
      </template>
      <template v-else-if="isEditing">
        <button 
          class="room-card__button room-card__button_edit"
          @click="{emits('save', updatedRoom()); isEditing = false;}">
          Сохранить
        </button>
        <button 
          class="room-card__button room-card__button_delete"
          @click="{emits('cancel'); isEditing = false;}">
          Отменить
        </button>
      </template>
    </div>
  </div>
</template>
<style lang="scss" scoped>
.room-card {
  display: flex;
  flex-direction: column;
  gap: 16px;
  background: white;
  filter: drop-shadow(0px 3px 6px rgba(0, 0, 0, 0.161));
  padding-bottom: 16px;
  padding-top: 16px;
  width: min-content;
  min-height: 368px;
  min-width: 256px;
  &__title {
    display: flex;
    align-items: center;
    font-family: 'Montserrat';
    text-transform: uppercase;
    font-weight: 500;
    font-size: 19px;
    padding-left: 16px;
    padding-right: 16px;
    letter-spacing: 0.03rem;
    min-height: 56px;
  }
  &__actions {
    display: flex;
    gap: 16px;
    padding-left: 16px;
    padding-right: 16px;
    min-height: 16px;
  }
  &__body {
    padding-top: 8px;
    padding-bottom: 8px;
    background-color:gainsboro;
  }
  &__image {
    filter: invert(70%) sepia(1%) saturate(0%) hue-rotate(27deg) brightness(89%) contrast(83%); // gray to svg
  }
  &__button {
    font-family: 'Montserrat';
    font-weight: 500;
    font-size: 14px;
    line-height: 16px;
    text-transform: uppercase;
    color: #6200EE;
    transition: .3s;
    opacity: 1;
    &:hover {
      transition: .3s;
      opacity: .8;
    }
    &_edit {

    }
    &_delete {
      color: crimson;
    }
  }
}
</style>