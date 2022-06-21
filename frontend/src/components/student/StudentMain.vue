<script setup>
import { useQuasar } from 'quasar';
import { useStore } from 'vuex';

import BaseCard from '../base/BaseCard.vue';
import BaseCite from '../other/BaseCite.vue'
import BaseCardWrapper from '../base/BaseCardWrapper.vue';

const q = useQuasar();
const store = useStore();


</script>
<template>
  <base-cite/>
</template>
<style scoped lang="scss">
</style>