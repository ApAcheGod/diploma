<script setup>
import { computed } from '@vue/reactivity';
import { useStore } from 'vuex';
// import GroupCard from '../GroupCard.vue';

const store = useStore();
const groupsByActiveSubject = computed(() => store.getters.getActiveSubject?.groups);

</script>
<template>
  <div class="room-groups">
    <!-- <group-card v-for="group in groupsByActiveSubject" :key="group.id" :group="group" /> -->
  </div>
</template>
<style lang="scss" scoped>
.room-groups {
  margin: 24px;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  gap: 24px;
}
</style>