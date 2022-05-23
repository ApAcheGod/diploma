const state = {
  user: localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')) : null,
  userData: {},

  error: null,
  loading: false,

  rooms: [],
  studentGroups: [],
  materials: [],
  subjects: [],

  activeRoom: {},
};

export default state;
