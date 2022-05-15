<script setup>

const props = defineProps({
  room: Object,
});

</script>
<template>
  <div class="room-card">
    <div class="room-card__title">{{props.room.name}}</div>
    <div class="room-card__body">
      <img src="../../img/university-default.png" class="room-card__image">
    </div>
    <div class="room-card__actions">
      <button class="room-card__button room-card__button_edit">Редактировать</button>
      <button class="room-card__button room-card__button_delete">Удалить</button>
    </div>
  </div>
</template>
<style scoped>
.room-card {
  display: flex;
  flex-direction: column;
  gap: 16px;
  background: white;
  filter: drop-shadow(0px 3px 6px rgba(0, 0, 0, 0.161));
  padding-bottom: 16px;
  padding-top: 16px;
}
.room-card__title {
  font-family: 'Montserrat';
  text-transform: uppercase;
  font-weight: 500;
  font-size: 17px;
  padding-left: 16px;
  padding-right: 16px;
  letter-spacing: 0.03rem;
}
.room-card__actions {
  display: flex;
  gap: 16px;
  padding-left: 16px;
  padding-right: 16px;
}
.room-card__body {
  padding-top: 8px;
  padding-bottom: 8px;
  background-color:gainsboro;
}
.room-card__image {
filter: invert(70%) sepia(1%) saturate(0%) hue-rotate(27deg) brightness(89%) contrast(83%);
}
.room-card__button {
  font-family: 'Montserrat';
  font-weight: 500;
  font-size: 14px;
  line-height: 16px;
  text-transform: uppercase;
  color: #6200EE;
  transition: .3s;
  opacity: 1;
}
.room-card__button:hover {
  transition: .3s;
  opacity: .8;
}
.room-card__button_edit {

}
.room-card__button_delete {
  color: crimson;
}
</style>