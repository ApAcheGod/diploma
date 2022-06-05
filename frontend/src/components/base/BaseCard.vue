<script setup>
</script>

<template>
  <div class="base-card">
    <div class="base-card__title">
      <slot name="title"></slot>
    </div>
    <div class="base-card__body">
      <slot name="body"></slot>
    </div>
    <div class="base-card__actions">
      <slot name="actions"></slot>
    </div>
  </div>
</template>

<style lang="scss">
.base-card {
  display: flex;
  flex-direction: column;
  gap: 16px;
  background: white;
  filter: drop-shadow(0px 3px 6px rgba(0, 0, 0, 0.161));
  padding-bottom: 16px;
  padding-top: 16px;
  width: min-content;
  min-height: 368px;
  min-width: 256px;
  font-family: 'Montserrat';
  &__title {
    display: flex;
    align-items: center;
    text-transform: uppercase;
    font-weight: 500;
    font-size: 19px;
    padding-left: 16px;
    padding-right: 16px;
    letter-spacing: 0.03rem;
    min-height: 56px;
  }
  &__actions {
    display: flex;
    gap: 16px;
    padding-left: 16px;
    padding-right: 16px;
    min-height: 16px;
  }
  &__body {
    padding-top: 8px;
    padding-bottom: 8px;
    // background-color:gainsboro;
  }
}
</style>