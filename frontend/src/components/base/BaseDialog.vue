<script setup>
import { onMounted, ref } from 'vue';

const promptIsMaximized = ref(false);
const props = defineProps({
  promptIsOpen: Boolean,
  title: String,
});
const promptState = ref({});
const emit = defineEmits(['change-open-status']);

onMounted(() => {
 Object.defineProperty(promptState.value, "isOpen", {
    get: () => props.promptIsOpen,
    set: (value) => emit('change-open-status'),
  });
});

</script>
<template>
  <q-dialog 
    persistent
    v-model="promptState.isOpen"
    :maximized="promptIsMaximized" 
    transition-show="slide-up"
    transition-hide="slide-down"
  >
    <q-card class="base-dialog" @keyup.esc="emit('change-open-status')" @keyup.enter="updateTask(newTask)">
      <q-bar>
        <div class="base-dialog__title">{{props.title}}</div>
        <q-space />
        <q-btn dense flat icon="minimize" @click="promptIsMaximized = false" :disable="!promptIsMaximized">
          <q-tooltip v-if="promptIsMaximized" class="bg-white text-primary">Свернуть</q-tooltip>
        </q-btn>
        <q-btn dense flat icon="crop_square" @click="promptIsMaximized = true" :disable="promptIsMaximized">
          <q-tooltip v-if="!promptIsMaximized" class="bg-white text-primary">Развернуть</q-tooltip>
        </q-btn>
        <q-btn dense flat icon="close" v-close-popup>
          <q-tooltip class="bg-white text-primary">Закрыть</q-tooltip>
        </q-btn>
      </q-bar>
      <div class="base-dialog__body">
        <slot name="body"></slot>
        <slot name="comment"></slot>
      </div>
      <div class="base-dialog__actions">
        <slot name="actions"></slot>
      </div>
    </q-card>
  </q-dialog>
</template>
<style lang="scss">
.base-dialog {
  min-width: 400px; 
  min-height: 480px;
  display: flex;
  flex-direction: column;
  &__title {
    font-family: Montserrat;
    font-weight: 500;
    font-size: 16px;
  }
  &__body {
    display: flex;
    flex-direction: column;
    gap: 24px;
    padding: 24px;
  }
  &__comment {
    display: flex;
    flex-direction: column;
    gap: 24px;
    padding: 24px;
  }
  &__actions {
    margin: 16px;
    flex-grow: 1;
    display: flex;
    align-items: flex-end; 
    justify-content: flex-end;
  }
}
</style>