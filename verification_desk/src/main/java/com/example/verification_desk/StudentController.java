package com.example.verification_desk;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/student")
    public String getStudentDetails(@RequestParam("rollNo") String rollNo, Model model) {
        Student student = studentRepository.findByRollNo(rollNo);
        if (student != null) {
            model.addAttribute("student", student);
            return "details";
        }
        return "error"; // Handle error page
    }

    @PostMapping("/verify")
    @ResponseBody
    public String verifyStudent(@RequestBody VerifyRequest request) {
        Student student = studentRepository.findByRollNo(request.getRollNo());
        if (student!= null) {
            if ("Yes".equalsIgnoreCase(student.getVerifiedStatus())) {
                return "The student is already verified";
            }
            student.setVerifiedStatus("Yes");
            studentRepository.save(student);
            return "Student verified!";
        }
        return "Error verifying student!";
    }

    static class VerifyRequest {
        private String rollNo;

        public String getRollNo() {
            return rollNo;
        }

        public void setRollNo(String rollNo) {
            this.rollNo = rollNo;
        }
    }
}

