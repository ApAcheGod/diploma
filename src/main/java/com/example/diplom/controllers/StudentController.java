package com.example.diplom.controllers;

import com.example.diplom.entities.Student;
import com.example.diplom.repositories.RoleRepository;
import com.example.diplom.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

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

    @GetMapping()
    public String studentPage(Principal principal, Model model){
        Student student = studentService.findByLogin(principal.getName());
        System.out.println(principal);
        System.out.println(student);
        model.addAttribute("user", student);
        return "welcomePage";
    }
}
