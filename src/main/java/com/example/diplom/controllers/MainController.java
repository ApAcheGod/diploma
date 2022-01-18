package com.example.diplom.controllers;

import com.example.diplom.entities.*;
import com.example.diplom.services.*;
import lombok.RequiredArgsConstructor;
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
    private final MaterialServcie materialServcie;
    private final SolutionService solutionService;
    private final CreateLoginService loginService;

    @GetMapping
    public String adminMain(){
        return "adminPage";
    }


    @PostMapping("/addGroup")
    public String addGroup(@ModelAttribute("group") Group group){
        groupService.save(group);
        return "redirect:/main/allGroups";
    }

    @GetMapping("/addGroup")
    public String addGroup(Model model){
        Group group = new Group();
        model.addAttribute("group", group);
        return "addGroupPage";
    }

    @GetMapping("/editGroup/{UUID}")
    public String editGroup(Model model, @PathVariable("UUID") UUID uuid){
        Group group = groupService.findById(uuid);
        model.addAttribute("group", group);
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("studentsInGroup", group.getStudents());
        return "editGroupPage";
    }

    @PostMapping("/editGroup")
    public String editGroup(@ModelAttribute("group") Group group){
        groupService.save(group);
        return "redirect:/main/allGroups";
    }

    @GetMapping("/removeGroup/{UUID}")
    public String removeGroup(@PathVariable("UUID") UUID uuid){
        groupService.removeById(uuid);
        return "redirect:/main/allGroups";
    }

    @GetMapping("/infoGroup/{UUID}")
    public String infoAboutGroup(Model model, @PathVariable("UUID") UUID uuid){
        Group group =  groupService.findById(uuid);
        model.addAttribute("group", group);
        return "groupInfoPage";
    }

    @GetMapping("/addStudent")
    public String addStudent(Model model){
        Student student = new Student();
        model.addAttribute("student", student);
        model.addAttribute("groups", groupService.findAll());
        return "addStudentPage";
    }

    @PostMapping("/addStudent")
    public String addStudent(@ModelAttribute("student") Student student){
        loginService.createLoginForUser(student);
        studentService.save(student);
        studentService.findAll().forEach(s -> System.out.println(s.getLogin()));
        return "redirect:/main/allGroups";
    }



    @GetMapping("/addTeacher")
    public String addTeacher(Model model){
        Teacher teacher = new Teacher();
        model.addAttribute("teacher", teacher);
        return "addTeacherPage";
    }

    @PostMapping("/addTeacher")
    public String addTeacher(@ModelAttribute("teacher") Teacher teacher){
        loginService.createLoginForUser(teacher);
        teacherService.save(teacher);
        return "redirect:/main/allTeachers";
    }

    @GetMapping("/addRoom")
    public String addRoom(Model model){
        Room room = new Room();
        model.addAttribute("room", room);
        model.addAttribute("teachers", teacherService.findAll());
        return "addRoomPage";
    }

    @PostMapping("/addRoom")
    public String addRoom(@ModelAttribute("room") Room room){
        roomService.save(room);
        return "redirect:/main/allRooms";
    }

    @GetMapping("/addTask")
    public String addTask(Model model){
        Task task = new Task();
        model.addAttribute("task", task);
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("teachers", teacherService.findAll());
        model.addAttribute("type", TaskType.class);
        return "addTaskPage";
    }

    @PostMapping("addTask")
    public String addTask(@ModelAttribute("task") Task task){
        taskService.save(task);
        return "redirect:/main/allTasks";
    }

    @GetMapping("/addMaterial")
    public String addMaterial(Model model){
        Material material = new Material();
        model.addAttribute("material", material);
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("teachers", teacherService.findAll());
        return "addMaterialPage";
    }

    @PostMapping("/addMaterial")
    public String addMaterial(@ModelAttribute("material") Material material){
        materialServcie.save(material);
        return "redirect:/main/allMaterials";
    }

    @GetMapping("/allMaterials")
    public String showAllMaterials(Model model){
        List<Material> materials = materialServcie.findAll();
        model.addAttribute("materials", materials);
        model.addAttribute("totalMaterials",materials.size());
        return "materials";
    }

    @GetMapping("/allStudents")
    public String showAllStudent(Model model){
        List<Student> students = studentService.findAll();
        model.addAttribute("students", students);
        model.addAttribute("totalStudents", students.size());
        return "students";
    }

    @GetMapping("/allTasks")
    public String showAllTasks(Model model){
        List<Task> tasks = taskService.findAll();
        model.addAttribute("tasks", tasks);
        model.addAttribute("totalTasks", tasks.size());
        return "tasks";
    }

    @GetMapping("/allRooms")
    public String showAllRooms(Model model){
        List<Room> rooms = roomService.findAll();
        model.addAttribute("rooms", rooms);
        model.addAttribute("totalRooms", rooms.size());
        return "rooms";
    }

    @GetMapping("/allTeachers")
    public String showAllTeachers(Model model){
        List<Teacher> teachers = teacherService.findAll();
        model.addAttribute("teachers", teachers);
        model.addAttribute("totalTeachers", teachers.size());
        return "teachers";
    }

    @GetMapping("/allGroups")
    public String showAllGroups(Model model){
        List<Group> groups = groupService.findAll();
        model.addAttribute("groups", groups);
        model.addAttribute("totalGroups", groups.size());
        return "groups";
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
