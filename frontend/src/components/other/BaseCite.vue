<script setup>
import { onMounted, ref } from "vue";
import axios from "axios";

const CITE_API_URL = 'http://api.forismatic.com/api/1.0/?method=getQuote&format=json';
const hasError = ref(false);
const cite = ref({
    quoteText: 'Не относитесь к жизни сильно серьёзно, всё равно вам из неё живым не выбраться.',
    quoteAuthor: '',
    senderName: '',
    senderLink: '',
    quoteLink: ''
});

// onMounted(() => {
//   getCitation();
// });

// function getCitation(){
//   getCitationFetch()
//     .then(result => {
//       if (result) {
//         console.log(result);
//         cite.value = result;
//         hasError = false;
//       }
//       else 
//         hasError = true;
//     });
// }

// function getCitationFetch(){
//   return fetch(CITE_API_URL, {
//     mode: 'no-cors',
//   })
//   .then(response => response.json())
//   .then(jsonResult => jsonResult)
//   .catch(error => console.error(error));
// }

function nextCite() {
  // getCitation();
}

</script>
<template>
  <div v-if="!hasError" class="citation">
    <div class="citation__title">Цитата дня</div>
    <div class="citation__text">{{cite.quoteText}}</div>
    <button class="citation__next" @click="nextCite()">Следующая цитата</button>
  </div>
  <div v-else class="citation">
    <div class="citation-error">
      Ошибка
    </div>
  </div>
</template>
<style scoped>
.citation {
  margin: 24px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 16px;
  box-shadow:none;
  position:relative;
  background-color: white;
  filter: drop-shadow(0px 3px 6px rgba(0, 0, 0, 0.161));
  padding: 16px;
}
.citation__title {
  text-transform: uppercase;
  font-family: 'Montserrat';
  font-weight: 500;
  font-size: 17px;
  letter-spacing: 0.03rem;
}
.citation__text {
  font-family: 'Montserrat';
}
.citation__next {
  font-family: 'Montserrat';
  color:darkblue;
  text-decoration: underline;
  opacity: .8;
}
.citation__next:hover {
  opacity: 1;
}
</style>