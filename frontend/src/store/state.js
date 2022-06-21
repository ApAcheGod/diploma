const state = {
  activeSubjectId: localStorage.getItem('activeSubjectId') ? localStorage.getItem('activeSubjectId') : '',
  user: localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')) : null,
  userData: {},

  error: null,
  loading: false,

  rooms: [],
  students: [],
  studentGroups: [],
  materials: [],
  subjects: [],
  solutions: [],
  examinations: [],
  tasks: [],
};

export default state;
