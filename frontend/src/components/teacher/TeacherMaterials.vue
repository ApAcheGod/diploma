<script setup>
import { computed } from '@vue/reactivity';
import { useStore } from 'vuex';
import MaterialBlock from './MaterialBlock.vue';

const store = useStore();
const materialsByRooms = computed(() => { const materials = store.getters.getUserMaterials; console.log(materials); return materials;});

</script>
<template>
  <material-block v-for="room in materialsByRooms" :key="room.name" :room="room" />
</template>
<style scoped>
</style>