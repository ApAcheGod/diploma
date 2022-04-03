package com.example.diplom.controllers;

import com.example.diplom.Exceptions.NotOwnerException;
import com.example.diplom.entities.*;
import com.example.diplom.repositories.RoleRepository;
import com.example.diplom.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teacher")
public class TeacherController {

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

    @GetMapping("/addRoom")
    @Secured({"ROLE_TEACHER"})
    public String addRoom(Model model){
        Room room = new Room();
        model.addAttribute("room", room);
        return "teacher/addRoomPage";
    }

    @PostMapping("/addRoom")
    @Secured({"ROLE_TEACHER"})
    public String addRoom(Principal principal, @ModelAttribute("room") Room room){
        Teacher teacher = teacherService.findByLogin(principal.getName());
        room.addTeacher(teacher);
        roomService.save(room);
        return "redirect:/teacher/rooms";
    }

    @GetMapping("/room/{UUID}")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')") //TODO сделать чтобы только владелец комнаты мог просматривать свою комнату
    public String showRoom(Principal principal, Model model, @PathVariable("UUID") UUID uuid) throws NotOwnerException {
//        Room room = roomService.findRoomById(uuid);
//        if (room.getTeacher().getLogin().equals(principal.getName())){
//            model.addAttribute("room" , room);
//            model.addAttribute("subjects", room.getSubjects());
//        }

        Room room = teacherService.findByLogin(principal.getName()).getRooms()
                .stream()
                .filter(r -> r.getId().equals(uuid))
                .findFirst().orElseThrow(NotOwnerException::new); //TODO что возвращает если нет такой комнаты
        model.addAttribute("room", room);
        model.addAttribute("subjects", room.getSubjects());
        return "teacher/roomInfo";
    }

    @GetMapping("/room/{UUID}/addSubject")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    public String addSubject(Model model, Principal principal, @PathVariable("UUID") UUID uuid){
        Subject subject = new Subject();
        Teacher teacher = teacherService.findByLogin(principal.getName());
        List<Group> groups = groupService.findAll();
        Room room = roomService.findById(uuid);
        subject.setTeacher(teacher);
        subject.setRoom(room);
        model.addAttribute("subject", subject);
        model.addAttribute("groups", groups);
        return "teacher/addSubjectPage";
    }

    @PostMapping("/addSubject")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    public String addSubject(@ModelAttribute("subject") Subject subject){
        subjectService.save(subject);
        return "redirect:/teacher/rooms";
    }

    @GetMapping("/subject/{UUID}")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    public String subjectInfo(Model model, Principal principal, @PathVariable("UUID") UUID uuid){
        Subject subject = subjectService.findById(uuid);
        model.addAttribute("subject", subject);
//        model.addAttribute("groups", subject.getGroups());
        model.addAttribute("materials", subject.getMaterials());
        model.addAttribute("tasks", subject.getTasks());
//        model.addAttribute("student", subject.getStudent()); // TODO Предмет у студента надо ли
        return "teacher/subjectInfo";
    }

    @GetMapping("/room/{room_id}/subject/{subject_id}/addMaterial")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    public String addMaterial(Model model, Principal principal,
                              @PathVariable("room_id") UUID room_id,
                              @PathVariable("subject_id") UUID subject_id){
        Material material = new Material();
        Teacher teacher = teacherService.findByLogin(principal.getName());
        roomService.findById(room_id).getSubjects().forEach(System.out::println);
        Subject subject = subjectService.findById(subject_id);
        material.addTeacher(teacher);
        material.addSubject(subject);

        model.addAttribute("material", material);
        return "teacher/addMaterialPage";
    }

    @PostMapping("/addMaterial")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    public String addMaterial(@ModelAttribute("material") Material material){
        materialService.save(material);
        return "redirect:/teacher/rooms";
    }
    // TODO переделать
//    @GetMapping("/room/{room_id}/subject/{subject_id}/addGroup")
//    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
//    public String addGroup(Model model, Principal principal,
//                              @PathVariable("room_id") UUID room_id,
//                              @PathVariable("subject_id") UUID subject_id){
//        List<Group> groups = groupService.findAll();
//        Subject subject = subjectService.findById(subject_id);
//        model.addAttribute("groups", groups);
//        model.addAttribute("subject", subject);
//        return "teacher/addGroupPage";
//    }
    // TODO переделать
//    @PostMapping("/addGroup")
//    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
//    public String addGroup(@ModelAttribute("subject") Subject subject){
//        System.out.println(subject);
//        System.out.println(subject.getGroups());
//        return "redirect:/teacher/rooms";
//    }
//
    @GetMapping("/material/{UUID}")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    public String materialInfo(Model model, Principal principal, @PathVariable("UUID") UUID uuid){
        Material material = materialService.findById(uuid);
        model.addAttribute("material", material);
        return "teacher/materialInfo";
    }


    @GetMapping("/room/{room_id}/subject/{subject_id}/addTask")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    public String addTask(Model model,Principal principal,
                              @PathVariable("room_id") UUID room_id,
                              @PathVariable("subject_id") UUID subject_id){
        Task task = new Task();
        Teacher teacher = teacherService.findByLogin(principal.getName());
        Subject subject = subjectService.findById(subject_id);
        Room room = roomService.findById(room_id);
        task.addTeacher(teacher); // TODO где-то сделать присвоение группы комнате
        task.setGroups(room.getGroups());
        task.addSubjects(subject);
        model.addAttribute("task", task);
        model.addAttribute("groups", room.getGroups());
        return "teacher/addTaskPage";
    }

    @PostMapping("/addTask")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    public String addTask(@ModelAttribute("task") Task task){
        taskService.save(task);
        return "redirect:/teacher/rooms";
    }

    @GetMapping("/task/{UUID}")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    public String TaskInfo(Model model, Principal principal, @PathVariable("UUID") UUID uuid){
        Task task = taskService.findById(uuid);
        model.addAttribute("task", task);
        return "teacher/taskInfo";
    }


    @GetMapping("/group/{UUID}")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    public String groupInfo(Model model, Principal principal, @PathVariable("UUID") UUID uuid){
        Group group = groupService.findById(uuid);
        model.addAttribute("group", group);
        model.addAttribute("studentsInGroup", group.getStudents());
        model.addAttribute("groupTasks", group.getTasks());
//        model.addAttribute("students", group.getStudents());
//        System.out.println("group tasks === " + group.getTasks());
//        System.out.println("group subjects === " + group.getSubjects());
        return "teacher/groupInfo";
    }

    @GetMapping("/rooms")
    @Secured({"ROLE_TEACHER"})
    public String getRooms(Principal principal, Model model){
        model.addAttribute("login", principal.getName());
        model.addAttribute("rooms", teacherService.findByLogin(principal.getName()).getRooms());
        return "teacher/roomsPage";
    }
}
