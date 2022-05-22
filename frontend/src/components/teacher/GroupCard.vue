<script setup>
import { onMounted } from 'vue';

const props = defineProps({
  group : Object,
});

onMounted(() => {
  console.log(props.group);
});

</script>
<template>
<div class="group-card">
  <div class="group-card__title">
    {{props.group?.name}}
  </div>
</div>
</template>
<style lang="scss" scoped>
.group-card {

}
</style>