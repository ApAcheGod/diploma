<script setup>
import { useQuasar } from "quasar";

const q = useQuasar();
const props = defineProps({ 
  modelValue: {
    type: String,
    default: '',
  },
  placeholder: {
    type: String,
    default: 'Введите что-нибудь',
  },
});
const emits = defineEmits([ 'update:model-value' ]);

const toolbar = Object.freeze([
  ['bold', 'italic', 'strike', 'underline', 'subscript', 'superscript', 'link', 'removeFormat'],
  [
    {
      label: q.lang.editor.fontSize,
      icon: q.iconSet.editor.fontSize,
      fixedLabel: true,
      fixedIcon: true,
      list: 'no-icons',
      options: [
        'size-1',
        'size-2',
        'size-3',
        'size-4',
        'size-5'
      ]
    },
    {
      label: q.lang.editor.defaultFont,
      icon: q.iconSet.editor.font,
      fixedIcon: true,
      list: 'no-icons',
      options: [
        'default_font',
        'arial',
        'arial_black',
        'courier_new',
        'times_new_roman',
        'verdana'
      ]
    },
    {
      label: q.lang.editor.align,
      icon: q.iconSet.editor.align,
      fixedLabel: true,
      list: 'only-icons',
      options: ['left', 'center', 'right', 'justify']
    },
  ],
  ['quote', 'unordered', 'ordered', 'outdent', 'indent'],
  ['undo', 'redo', 'viewsource'],
]);
const fonts = Object.freeze(
  {
    arial: 'Arial',
    arial_black: 'Arial Black',
    comic_sans: 'Comic Sans MS',
    courier_new: 'Courier New',
    impact: 'Impact',
    lucida_grande: 'Lucida Grande',
    times_new_roman: 'Times New Roman',
    verdana: 'Verdana'
  }
);

</script>
<template>
  <q-editor 
    min-height="10rem" 
    :toolbar="toolbar"
    :fonts="fonts"
    :model-value="props.modelValue"
    :placeholder="placeholder"
    @update:model-value="(value) => emits('update:model-value', value)"
  />
</template>
<style scoped lang="scss">
</style>