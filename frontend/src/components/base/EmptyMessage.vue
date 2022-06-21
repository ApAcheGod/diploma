<script setup>
</script>
<template>
  <div class="empty-message">
    <slot></slot>
  </div>
</template>
<style lang="scss" scoped>
  .empty-message {
    font-family: 'Montserrat';
    font-weight: 500;
    align-items: center;
    justify-content: center;
    display: flex;
    flex-direction: row;
    gap: 8px;
    font-size: 16px;
    margin-top: 160px;
    color: #1d1d1d;
    // color: #;
    // text-shadow: 
    //   0px 4px 3px rgba(0,0,0,.4),
    //   0px 8px 13px rgba(0,0,0,.1),
    //   0px 18px 23px rgba(0,0,0,.1);
  }
</style>