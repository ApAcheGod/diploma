const state = {
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

  studentsMapByStudentId: null,
  groupsMapByGroupId: null,
  examsByStudentIdTaskId: null,
  solutionsByStudentIdTaskId: null,

  activeSubjectId: localStorage.getItem('activeSubjectId') ? localStorage.getItem('activeSubjectId') : '',
};

export default state;
