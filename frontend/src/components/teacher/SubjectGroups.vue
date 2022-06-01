<script setup>
import { computed } from '@vue/reactivity';
import { useStore } from 'vuex';
import GroupCard from './GroupCard.vue';

const store = useStore();
const groupsBySubject = computed(() => store.getters.getActiveSubject?.groups);

</script>
<template>
    <div class="room-block__groups">
        <group-card v-for="group in groupsBySubject" :key="group.id" :group="group" />
    </div>
</template>
<style lang="scss" scoped>
.room-block {
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
  &__groups {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    gap: 24px;
  }
}
</style>