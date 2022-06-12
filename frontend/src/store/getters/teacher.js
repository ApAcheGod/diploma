import workStatuses from "../../models/workStatuses";

export default {
  getTeacherStudentsGroups(state) {
    const studentGroupsObject = new Map();
    state.studentGroups.forEach(group => studentGroupsObject.set(group.id, group));
    const groupsByRooms = state.userData.rooms?.map(room => {
      return { 
        roomName : room.name, 
        roomGroups : state.subjects.filter(subject => subject.roomId === room.id)
          .map(subject => subject.groups.map(group => studentGroupsObject.get(group.id)))
          .flat(3),
      }
    });
    return groupsByRooms;
  },
  getTeacherRooms(state) {
    return state.userData.rooms;
  },
  getTeacherSubjects(state) {
    const userSubjectsByRooms = state.userData.rooms?.map(room => {
      return { 
        roomName : room.name,
        roomId : room.id,
        roomSubjects : state.subjects.filter(subject => subject.roomId === room.id),
      }
    });
    return userSubjectsByRooms;
  },
  getHomeWorks(state, getters) {
    const activeSubject = getters.getActiveSubject;
    if (!activeSubject) return null;
    const solutionsByStudentIdTaskId = getters.getSolutionsMapByStudentIdTaskId;
    const examsByStudentIdTaskId = getters.getExamsMapByStudentIdTaskId;
    let tasks = JSON.parse(JSON.stringify(activeSubject.tasks));
    tasks.forEach(task => task.uncheckdHomeWorks = activeSubject.groups
      .map(group => {
        return {
          groupId: group.id,
          groupName: group.name,
          homeWorks: group.students.map(student => {
            if (examsByStudentIdTaskId.get(student.id).get(task.id)) return;
            return solutionsByStudentIdTaskId.get(student.id).get(task.id);
          }).filter(hw => hw).flat(3),
        }
      })
      .filter(group => group.homeWorks.length > 0)
    )
    tasks = tasks.filter(task => task.uncheckdHomeWorks.length > 0);
    tasks.sort((taskA, taskB) => taskA.name.localeCompare(taskB.name));
    return tasks;
  },
  getActiveSubjectJournal(state, getters) {
    const solutionsByStudentIdTaskId = getters.getSolutionsMapByStudentIdTaskId;
    const examsByStudentIdTaskId = getters.getExamsMapByStudentIdTaskId;
    const groupsMapByGroupId = getters.getGroupsMapByGroupId;
    const studentsMapByStudentId = getters.getStudentsMapByStudentId;

    const setGroupResults = (group, subject) => {
      const getMark = (studentId, taskId) => {
        const examinationResult = examsByStudentIdTaskId?.get(studentId)?.get(taskId);
        const solutionResult = solutionsByStudentIdTaskId?.get(studentId)?.get(taskId);
        const examination = examinationResult ? examinationResult[0] : examinationResult;
        const solution = solutionResult ? solutionResult[0] : solutionResult;
        if (examination)
          return examination.examinationStatus;
        if (solution)
          return workStatuses.COMPLETED;
        return workStatuses.NOT_COMPLETED;
      };
      group.students?.forEach(student => {
        subject.tasks.forEach(task => {
          student[task.id] = getMark(student.id, task.id); 
        })
      });
      return group;
    };

    const activeSubject = getters.getActiveSubjectEmpty;
    if (!activeSubject) return;

    activeSubject.tableHead = [{
        name: 'name',
        required: true,
        label: 'Имя студента',
        align: 'left',
        field: row => row.name,
        format: val => `${val}`,
        sortable: true 
      }, 
      ...activeSubject.tasks.map(task => {
        return {
          name: task.id, 
          align: 'center', 
          label: task.name, 
          field: task.id, 
          sortable: false, 
        }}
      )
    ];

    activeSubject.groups = activeSubject.groups.map(group => { 
      let fullGroupData = groupsMapByGroupId.get(group.id);
      fullGroupData.students = fullGroupData.students?.map(student => studentsMapByStudentId.get(student.id)) ?? [];
      setGroupResults(fullGroupData, activeSubject);
      return fullGroupData;
    });

    return activeSubject;
  },
  getFormattedJournal(state, getters){ 
    const solutionsByStudentIdTaskId = getters.getSolutionsMapByStudentIdTaskId;
    const examsByStudentIdTaskId = getters.getExamsMapByStudentIdTaskId;
    const groupsMapByGroupId = getters.getGroupsMapByGroupId;
    const studentsMapByStudentId = getters.getStudentsMapByStudentId;

    const setGroupResults = (group, subject) => {
      const getMark = (studentId, taskId) => {
        const examinationResult = examsByStudentIdTaskId.get(studentId).get(taskId);
        const solutionResult = solutionsByStudentIdTaskId.get(studentId).get(taskId);
        const examination = examinationResult ? examinationResult[0] : examinationResult;
        const solution = solutionResult ? solutionResult[0] : solutionResult;
        if (examination)
          return examination.examinationStatus;
        if (solution)
          return workStatuses.COMPLETED;
        return workStatuses.NOT_COMPLETED;
      };

      group.students?.forEach(student => {
        subject.tasks.forEach(task => {
          student[task.id] = getMark(student.id, task.id); 
        })
      });
      return group;
    };

    const roomSubjectsHeadByTasks = state.userData.rooms?.map(room => {
      return { 
        roomName : room.name,
        roomId : room.id,
        roomSubjects : state.subjects.filter(subject => subject.roomId === room.id)
        .map(subject => {
          subject.tasks ? subject.tasks.sort((taskA, taskB) => taskA.name.localeCompare(taskB.name)) : []; // Сортировка по названиям заданий.
          return {
            name : subject.name,
            tasks: subject.tasks,
            groups: !subject.groups ? [] : subject.groups.map(group => { 
              let fullGroupData = groupsMapByGroupId.get(group.id);
              fullGroupData.students = fullGroupData.students?.map(student => studentsMapByStudentId.get(student.id)) ?? [];
              setGroupResults(fullGroupData, subject);
              return fullGroupData;
            }),
            tasksTableHead : [{
              name: 'name',
              required: true,
              label: 'Имя студента',
              align: 'left',
              field: row => row.name,
              format: val => `${val}`,
              sortable: true }, 
              ...subject.tasks ? subject.tasks.map(task => {
                return {
                  name: task.id, 
                  align: 'center', 
                  label: task.name, 
                  field: task.id, 
                  sortable: false, 
                }
              }) : []
            ],
          }
        }),
      }
    });
    return roomSubjectsHeadByTasks;
  },
}