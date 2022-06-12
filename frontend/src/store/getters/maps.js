export default {
  getStudentsMapByStudentId(state){
    const studentsMapByStudentId = new Map();
    state.students.forEach(student => studentsMapByStudentId.set(student.id, student));
    return studentsMapByStudentId;
  },
  getTasksMapByTaskId(state){
    const tasksMapByTaskId = new Map();
    state.tasks.forEach(task => tasksMapByTaskId.set(task.id, task));
    return tasksMapByTaskId;
  },
  getMaterialsMapByMaterialId(state){
    const materialsMapByMaterialId = new Map();
    state.materials.forEach(material => materialsMapByMaterialId.set(material.id, material));
    return materialsMapByMaterialId;
  },
  getGroupsMapByGroupId(state){
    const groupsMapByGroupId = new Map();
    state.studentGroups.forEach(group => groupsMapByGroupId.set(group.id, group));
    return groupsMapByGroupId;
  },
  getExamsMapByTaskId(state){
    const examsByTasksId = new Map();
    state.examinations.forEach(exam => examsByTasksId.has(exam.taskId) ? examsByTasksId.get(exam.taskId).push(exam) : examsByTasksId.set(exam.taskId, [exam]));
    return examsByTasksId;
  },
  getSolutionsMapByTaskId(state){
    const solutionsByTasksId = new Map();
    state.solutions.forEach(solution => solutionsByTasksId.has(solution.taskId) ? solutionsByTasksId.get(solution.taskId).push(solution) : solutionsByTasksId.set(solution.taskId, [solution]));
    return solutionsByTasksId;
  },
  getExamsMapByStudentIdTaskId(state){
    const examsByStudentIdTaskId = new Map();
    state.students.forEach(student => {
      const examinationsMap = new Map();
      state.examinations.filter(e => e.studentId === student.id)
        .forEach(e => examinationsMap.has(e.taskId) ? examinationsMap.get(e.taskId).push(e) : examinationsMap.set(e.taskId, [e]));
      examsByStudentIdTaskId.set(student.id, examinationsMap);
    });
    return examsByStudentIdTaskId;
  },
  getSolutionsMapByStudentIdTaskId(state){
    const solutionsByStudentIdTaskId = new Map();
    state.students.forEach(student => {
      const solutionsMap = new Map();
      state.solutions.filter(e => e.studentId === student.id)
        .forEach(s => solutionsMap.has(s.taskId) ? solutionsMap.get(s.taskId).push(s) : solutionsMap.set(s.taskId, [s]));
      solutionsByStudentIdTaskId.set(student.id, solutionsMap);
    });
    return solutionsByStudentIdTaskId;
  },
  getSubjectMapBySubjectId(state){
    const subjectBySubjectId = new Map();
    state.subjects.forEach(subject => subjectBySubjectId.set(subject.id, subject));
    return subjectBySubjectId;
  },
}