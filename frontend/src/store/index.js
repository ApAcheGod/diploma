import { reactive } from 'vue';
import teacherData from '../data/Teacher.json'

const state = reactive({teacher : teacherData});

export default{
  state,
}