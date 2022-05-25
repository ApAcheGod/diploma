const state = {
  user: localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')) : null,
  userData: {},

  error: null,
  loading: false,

  rooms: [],
  studentGroups: [],
  materials: [],
  subjects: [],

  activeSubjectId: localStorage.getItem('activeSubjectId') ? localStorage.getItem('activeSubjectId') : '',
};

export default state;
