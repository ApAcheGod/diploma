const state = {
  user: localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')) : null,
  teacher: null,
  error: null,
  loading: false,
};

export default state;
