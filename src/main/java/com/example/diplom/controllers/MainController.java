package com.example.diplom.controllers;

import com.example.diplom.entities.*;
import com.example.diplom.repositories.RoleRepository;
import com.example.diplom.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Controller
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {

    private final GroupService groupService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final TaskService taskService;
//    private final JournalService journalService;
    private final RoomService roomService;
    private final SubjectService subjectService;
    private final MaterialService materialService;
    private final SolutionService solutionService;
    private final CreateLoginService loginService;
    private final CreatePasswordService passwordService;
    private final RoleRepository roleRepository;
    private final UserService userService;

    @GetMapping
    @Secured("ROLE_ADMIN")
    public String adminMain(){
        return "admin/adminPage";
    }

//    @GetMapping("/welcome")
//    public String welcome(Principal principal, Model model){
//        model.addAttribute("user", principal.getName() == null ? "Не авторизован" : principal.getName());
//        model.addAttribute("roles", userService.findByUserLogin(principal.getName()).getRoles());
//        return "welcomePage";
//    }

    @PostMapping("/addGroup")
    @Secured("ROLE_ADMIN")
    public String addGroup(@ModelAttribute("group") Group group){
        groupService.save(group);
        return "redirect:/main/allGroups";
    }

    @GetMapping("/addGroup")
    @Secured("ROLE_ADMIN")
    public String addGroup(Model model){
        Group group = new Group();
        List<Room> rooms = roomService.findAll();
        model.addAttribute("group", group);
        model.addAttribute("rooms", rooms);
        return "admin/addGroupPage";
    }

    @GetMapping("/editGroup/{UUID}")
    @Secured("ROLE_ADMIN")
    public String editGroup(Model model, @PathVariable("UUID") UUID uuid){
        Group group = groupService.findById(uuid).orElse(null);
        List<Room> rooms = roomService.findAll();
        model.addAttribute("group", group);
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("studentsInGroup", group.getStudents());
        model.addAttribute("rooms", rooms);
        return "admin/editGroupPage";
    }

    @PostMapping("/editGroup")
    @Secured("ROLE_ADMIN")
    public String editGroup(@ModelAttribute("group") Group group){
        groupService.save(group);
        return "redirect:/main/allGroups";
    }

    @GetMapping("/removeGroup/{UUID}")
    @Secured("ROLE_ADMIN")
    public String removeGroup(@PathVariable("UUID") UUID uuid){
        groupService.removeById(uuid);
        return "redirect:/main/allGroups";
    }

    @GetMapping("/infoGroup/{UUID}")
    @Secured("ROLE_ADMIN")
    public String infoAboutGroup(Model model, @PathVariable("UUID") UUID uuid){
        Group group =  groupService.findById(uuid).orElse(null);
        model.addAttribute("group", group);
        return "admin/groupInfoPage";
    }

    @GetMapping("/addStudent")
    @Secured("ROLE_ADMIN")
    public String addStudent(Model model){
        Student student = new Student();
        model.addAttribute("student", student);
        model.addAttribute("groups", groupService.findAll());
        return "admin/addStudentPage";
    }

    @PostMapping("/addStudent")
    @Secured("ROLE_ADMIN")
    public String addStudent(@ModelAttribute("student") Student student){
        student.setRoles(List.of(roleRepository.findRoleByRoleName("ROLE_STUDENT")));
        loginService.createLoginForUser(student);
        student.setPassword(passwordService.createPassword());
        studentService.save(student);
        return "redirect:/main/allGroups";
    }



    @GetMapping("/addTeacher")
    @Secured("ROLE_ADMIN")
    public String addTeacher(Model model){
        Teacher teacher = new Teacher();
        model.addAttribute("teacher", teacher);
        return "admin/addTeacherPage";
    }

    @PostMapping("/addTeacher")
    @Secured("ROLE_ADMIN")
    public String addTeacher(@ModelAttribute("teacher") Teacher teacher){
        teacher.setRoles(List.of(roleRepository.findRoleByRoleName("ROLE_TEACHER")));
        loginService.createLoginForUser(teacher);
        teacher.setPassword(passwordService.createPassword());
        teacherService.save(teacher);
        return "redirect:/main/allTeachers";
    }

    @GetMapping("/addRoom")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public String addRoom(Model model){
        Room room = new Room();
        model.addAttribute("room", room);
        model.addAttribute("teachers", teacherService.findAll());
        return "admin/addRoomPage";
    }

    @PostMapping("/addRoom")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public String addRoom(@ModelAttribute("room") Room room){
        roomService.save(room);
        return "redirect:/main/allRooms";
    }

    @GetMapping("/addTask")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public String addTask(Model model){
        Task task = new Task();
        model.addAttribute("task", task);
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("teachers", teacherService.findAll());
        model.addAttribute("type", TaskType.class);
        return "admin/addTaskPage";
    }

    @PostMapping("addTask")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public String addTask(@ModelAttribute("task") Task task){
        taskService.save(task);
        return "redirect:/main/allTasks";
    }

    @GetMapping("/addMaterial")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public String addMaterial(Model model){
        Material material = new Material();
        model.addAttribute("material", material);
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("teachers", teacherService.findAll());
        return "admin/addMaterialPage";
    }

    @PostMapping("/addMaterial")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public String addMaterial(@ModelAttribute("material") Material material){
        materialService.save(material);
        return "redirect:/main/allMaterials";
    }

    @GetMapping("/addSubject")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public String addSubject(Model model){
        Subject subject = new Subject();
        List<Teacher> teachers = teacherService.findAll();
        model.addAttribute("subject", subject);
        model.addAttribute("teachers", teachers);
        return "admin/addSubjectPage";
    }

    @PostMapping("/addSubject")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public String addSubject(@ModelAttribute("subject") Subject subject){
        subjectService.save(subject);
        return "redirect:/main/allSubjects";
    }


    @GetMapping("/allMaterials")
    public String showAllMaterials(Model model){
        List<Material> materials = materialService.findAll();
        model.addAttribute("materials", materials);
        model.addAttribute("totalMaterials",materials.size());
        return "admin/materials";
    }

    @GetMapping("/allStudents")
    public String showAllStudent(Model model){
        List<Student> students = studentService.findAll();
        model.addAttribute("students", students);
        model.addAttribute("totalStudents", students.size());
        return "admin/students";
    }

    @GetMapping("/allSubjects")
    public String showAllSubject(Model model){
        List<Subject> subjects = subjectService.findAll();
        model.addAttribute("subjects", subjects);
        model.addAttribute("totalSubjects", subjects.size());
        return "admin/subjects";
    }

    @GetMapping("/allTasks")
    public String showAllTasks(Model model){
        List<Task> tasks = taskService.findAll();
        model.addAttribute("tasks", tasks);
        model.addAttribute("totalTasks", tasks.size());
        return "admin/tasks";
    }

    @GetMapping("/allRooms")
    public String showAllRooms(Model model){
        List<Room> rooms = roomService.findAll();
        model.addAttribute("rooms", rooms);
        model.addAttribute("totalRooms", rooms.size());
        return "admin/rooms";
    }

    @GetMapping("/allTeachers")
    public String showAllTeachers(Model model){
        List<Teacher> teachers = teacherService.findAll();
        model.addAttribute("teachers", teachers);
        model.addAttribute("totalTeachers", teachers.size());
        return "admin/teachers";
    }

    @GetMapping("/allGroups")
    public String showAllGroups(Model model){
        List<Group> groups = groupService.findAll();
        model.addAttribute("groups", groups);
        model.addAttribute("totalGroups", groups.size());
        return "admin/groups";
    }

//    @GetMapping("")
//    public void test(){
////      1. Создание группы
//        Group group = new Group();
//        group.setName("EMA");
//
//        groupService.save(group);
////      2. Создание преподавателя
//        Teacher teacher = new Teacher();
//        teacher.setFirst_name("Teacher FirstName");
//        teacher.setLast_name("Teacher LastName");
//        teacher.setPatronymic("Teacher patronymic");
//        teacher.addGroup(group);
//
//        teacherService.save(teacher);
////      3. Создание студента
//        Student student = new Student();
//        student.setFirst_name("Nikita");
//        student.setLast_name("Alpatov");
//        student.setPatronymic("Sergeevich");
//        student.setGroup(group);
//
//        studentService.save(student);
//
////      4. Создание комнаты
//        Room room = new Room();
//        room.setName("Название комнаты");
//        room.addGroup(group);
//        room.addTeacher(teacher);
//
//        roomService.save(room);
//
////      5. Создание предмета
//        Subject subject = new Subject();
//        subject.setName("Название предмета");
//        subject.addGroup(group);
//        subject.addRoom(room);
//        subject.addTeacher(teacher);
//        subject.addStudent(student);
//
//        subjectService.save(subject);
//
//
////      6. Добавление материала
//
//        Material material = new Material();
//        material.setName("Название материала");
//        material.setText("Текст материала");
//        material.addTeacher(teacher);
//        material.addSubject(subject);
//
//        materialServcie.save(material);
//
//
////      7. Создание задания
//
//        Task task = new Task();
//        task.setName("Название задания");
//        task.setCount_of_attempts(10);
//        task.setIsMandatory(Boolean.TRUE);
//        task.setIsTemporal(Boolean.TRUE);
//        task.setMin_rating(0);
//        task.setMax_rating(100);
//        task.setTaskType(TaskType.SIMPLE_TASK);
//        task.setText("Текст задания");
//
//        task.addStudent(student);
//        task.addGroup(group);
//        task.addSubject(subject);
//        task.addTeacher(teacher);
//
//        taskService.save(task);
//
////      8. Создание решения
//
//        Solution solution = new Solution();
//        solution.setText("Текст решения");
//        solution.addTask(task);
//        solution.addStudent(student);
//
//        solutionService.save(solution);
//
//
//
////        Journal journal = new Journal();
////        journal.addTeacher(teacher);
////        journal.addGroup(group);
////        journal.add
////
////        journalService.save(journal);
//
//        System.out.println(group);
//        System.out.println(student);
//        System.out.println(teacher);
//        System.out.println(task);
//        System.out.println(room);
//        System.out.println(subject);
//        System.out.println(material);
//        System.out.println(solution);
//
//
////        System.out.println(journal);
////        System.out.println(teacher.getJournal());
//    }
}
