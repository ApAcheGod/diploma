<script setup>
import { inject, ref } from 'vue';
import RoomDashboard from './RoomDashboard.vue';

const { state } = inject('state');

// const props = defineProps({
//   Rooms: Array,
// });

let tab = ref();

</script>
<template>
  <q-tabs
    align="left"
    v-model="tab"
    inline-label
    class="bg-accent text-white shadow-2"
  >
    <q-tab 
      v-for="room in state.teacher?.rooms" 
      :key="room?.id" 
      :name="room?.name"  
      :label="room?.name"
      />
  </q-tabs>
  <q-separator />

  <q-tab-panels v-model="tab" animated>
    
    <q-tab-panel v-for="room in state.teacher?.rooms" :name="room.name">
      <room-dashboard
        :Room="room"
      />
    </q-tab-panel>
  
  </q-tab-panels>

</template>
<style scoped>
</style>