<script setup>
import { onMounted } from 'vue';

const props = defineProps({
  material : Object,
});

onMounted(() => {
  console.log(props.material);
});

</script>
<template>
  <div class="material-card">
    <div class="material-card__title">
      {{props.material?.name}}
    </div>
  </div>
</template>
<style lang="scss" scoped>
.material-card {

}
</style>