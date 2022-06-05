<script setup>
</script>
<template>
  <div class="base-wrapper">
    <div class="base-wrapper__title">
      <slot name="title"></slot>
    </div>
    <div class="base-wrapper__items">
      <transition-group name="list">
        <slot name="items"></slot>
      </transition-group>
    </div>
  </div>
</template>
<style lang="scss">
.base-wrapper {
  margin: 24px;
  font-family: 'Montserrat';
  &__title {
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
  &__items {
    display: flex;
    gap: 16px;
    flex-wrap: wrap;
    flex-direction: row;
    margin: 24px;
  }
}
</style>