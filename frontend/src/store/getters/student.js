import workStatuses from "../../models/workStatuses";
import methods from "../methods";

export default {
  getStudentGroups(state) {
    const student = state.userData;
    if (!student) return;

    const groups = state.studentGroups.filter(group => group.id === student.groupId);
    if (groups.length === 0) return;

    return JSON.parse(JSON.stringify(groups[0]));
  },
  getStudentSubjects(state, getters) {
    const group = getters.getStudentGroups;
    if (!group) return;

    const subjectMapBySubjectId = getters.getSubjectMapBySubjectId;
    return group.subjects.map(subject => subjectMapBySubjectId.get(subject.id));
  },
  getActiveSubjectFormattedTasks(state, getters) {
    const activeSubject = getters.getActiveSubject;
    if (!activeSubject) return;

    const student = state.userData;
    const solutionsMapByStudentIdTaskId = getters.getSolutionsMapByStudentIdTaskId;
    const examsMapByStudentIdTaskId = getters.getExamsMapByStudentIdTaskId;
    const tasks = activeSubject.tasks;
    
    tasks.forEach(task => {
      const examinationResult = examsMapByStudentIdTaskId?.get(student.id)?.get(task.id);
      const solutionResult = solutionsMapByStudentIdTaskId?.get(student.id)?.get(task.id);
      const examination = examinationResult ? examinationResult[0] : examinationResult;
      const solution = solutionResult ? solutionResult[0] : solutionResult;

      task.examination = examination ?? {};
      task.solution = solution ?? {};

      if (solution) task.status = workStatuses.COMPLETED;
      if (examination) task.status = examination.examinationStatus;
      if (!task.status) task.status = workStatuses.NOT_COMPLETED;
    });
    tasks.sort((taskA, taskB) => {
      if (taskA.status === workStatuses.NOT_COMPLETED) return 1;
      if (taskB.status === workStatuses.NOT_COMPLETED) return -1;
      return taskA.name.localeCompare(taskB.name);
    });
    return tasks;
  },
  getStudentJournal(state, getters) {
    const student = getters.getUserData;
    const subjects = getters.getStudentSubjects;
    const solutionsByStudentIdTaskId = getters.getSolutionsMapByStudentIdTaskId;
    const examsByStudentIdTaskId = getters.getExamsMapByStudentIdTaskId;
    const getMark = methods.getMark;

    subjects?.forEach(
      subject => subject.tasks?.forEach(
        task => task.mark = getMark(student.id, task.id, examsByStudentIdTaskId, solutionsByStudentIdTaskId)));

    return subjects;
  },
}