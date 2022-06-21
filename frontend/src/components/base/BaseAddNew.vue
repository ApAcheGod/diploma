<script setup>
</script>

<template>
  <div class="base-card base-card_add">
    <slot name="body"></slot>
  </div>
</template>

<style lang="scss">
.base-card_add {
  justify-content: center;
  gap: 24px;
  align-items: center;
  &__button {
    border-radius: 50px;
    padding: 21px;
    background: #03DAC5;
    transition: .3s;
    box-shadow: 0px 4px 5px rgba(0, 0, 0, 0.14), 0px 1px 10px rgba(0, 0, 0, 0.12), 0px 2px 4px rgba(0, 0, 0, 0.2);
    &:hover {
      background: rgba(3, 151, 135, 0.8);
      transition: .3s;
    }
  }
}
</style>