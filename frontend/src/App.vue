<script setup>
import { computed } from '@vue/reactivity';
import { useQuasar } from 'quasar';
import { watchEffect } from 'vue';
import { useStore } from 'vuex';

const store = useStore();
const q = useQuasar();
const isLoading = computed(() => store.getters.getLoading);

watchEffect(() => {
  if (isLoading.value) {
    q.loading.show({
      message: 'Загрузка данных, пожалуйста, подождите'
    })
  }
  else 
    q.loading.hide();
});

</script>

<template>
  <router-view />
</template>

<style>

/* List of els effect */
.list-item {
  display: inline-block;
  margin-right: 8px;
}

.list-enter-active,
.list-leave-active {
  transition: all 0.3s ease;
}

.list-enter-from,
.list-leave-to {
  opacity: 0;
  transform: translateY(20px);
}

</style>
