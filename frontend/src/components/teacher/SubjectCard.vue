<script setup>
import { onMounted, ref } from 'vue';
import { useStore } from "vuex";
import actionsTypes from "../../store/actionsTypes";

const props = defineProps({
  subject : Object,
});

const store = useStore();
const emits = defineEmits(['delete', 'save', 'cancel', 'info']);

const tasksRuLocale = (tasksCount) => {
  const tasksCountNums = tasksCount.toString();
  if (tasksCount % 5 === 0 || tasksCount === 11)
    return 'заданий';
  if (tasksCountNums[tasksCountNums.length - 1] == 1)
    return 'задание';
  return 'заданий';
}

const groupsRuLocale = (groupsCount) => {
  const groupsCountNums = groupsCount.toString();
  if (groupsCount % 5 === 0 || groupsCount === 11)
    return 'групп';
  if (groupsCountNums[groupsCountNums.length - 1] == 1)
    return 'группа';
  return 'группы';
}

const formatNamableArray = (array) => {
  if (!array || array.length === 0) return '';
  return array.length >= 5 ? `${props.subject.tasks.slice(0, 4).map(t => t.name).join(', ')} ...` : array.map(t => t.name).join(', ');
}

</script>
<template>
  <div class="subject-card">
    <div class="subject-card__title">
      {{props.subject?.name}}
    </div>
    <div class="subject-card__count">
      {{props.subject?.tasks?.length 
      ? `${props.subject.tasks.length} - ${tasksRuLocale(props.subject.tasks.length)}:` 
      : 'Нет заданий'}} <br>
      {{formatNamableArray(props.subject?.tasks)}}
    </div>
    <div class="subject-card__count">
      {{props.subject?.groups?.length 
      ? `${props.subject.groups.length} - ${groupsRuLocale(props.subject.groups.length)}:` 
      : 'Нет групп'}} <br>
      {{formatNamableArray(props.subject.groups)}}
    </div>
    <div class="subject-card__actions">
      <button 
          class="subject-card__button subject-card__button_edit"
          @click="emits('info', props.subject)">
          Подробнее
        </button>
        <button
          class="subject-card__button subject-card__button_delete"
          @click="emits('delete', props.subject)">
          Удалить
        </button>
    </div>
  </div>
</template>
<style lang="scss" scoped>
.subject-card {
  display: flex;
  flex-direction: column;
  gap: 16px;
  background: white;
  filter: drop-shadow(0px 3px 6px rgba(0, 0, 0, 0.161));
  padding: 16px;
  width: 304px;
  min-height: 344px;
  font-family: 'Montserrat';
  color: #1d1d1d;
  &__title {
    margin-bottom: 24px;
    display: flex;
    flex-direction: column;
    justify-content: left;
    font-size: 17px;
    font-weight: 500;
    height: 51px;
  }
  &__count {
    margin-bottom: 24px;
    display: flex;
    flex-direction: column;
    justify-content: left;
    height: 64px;
  }
  &__actions {
    display: flex;
    gap: 16px;
    padding-left: 16px;
    padding-right: 16px;
    height: 16px;
  }
  &__button {
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
      color: #2020CC;
      text-decoration: underline;
    }
    &_delete {
      color: crimson;
    }
  }
}
</style>