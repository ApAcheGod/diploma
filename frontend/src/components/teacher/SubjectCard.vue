<script setup>
import { onMounted } from 'vue';

const props = defineProps({
  subject : Object,
});

onMounted(() => {
  console.log(props.subject);
});

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
      {{props.subject?.tasks
      ? `${props.subject.tasks.slice(0, 4).map(t => t.name).join()}` 
      : ''}}
    </div>
    <div class="subject-card__count">
      {{props.subject?.groups?.length 
      ? `${props.subject.groups.length} - ${groupsRuLocale(props.subject.groups.length)}:` 
      : 'Нет групп'}} <br>
      {{props.subject?.groups 
      ? `${props.subject.groups.slice(0, 4).map(g => g.name).join()}` 
      : ''}}
    </div>
    <div class="subject-card__actions">
      <button 
          class="subject-card__button subject-card__button_edit">
          Редактировать
        </button>
        <button 
          class="subject-card__button subject-card__button_delete">
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
  // width: min-content;
  // min-height: 368px;
  // min-width: 256px;
  width: 304px;
  height: 344px;

  &__title {
    margin-bottom: 24px;
    display: flex;
    flex-direction: column;
    justify-content: left;
    font-family: 'Montserrat';
    font-size: 17px;
    font-weight: 500;
    color: #1d1d1d;
    height: 51px;
  }
  &__count {
    margin-bottom: 24px;
    display: flex;
    flex-direction: column;
    justify-content: left;
    font-family: 'Montserrat';
    color: #1d1d1d;
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
      color: #2020CC;
      text-decoration: underline;
    }
    &_delete {
      color: crimson;
    }
  }
}
</style>