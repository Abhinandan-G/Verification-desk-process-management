package com.example.verification_desk;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student getStudentByRollNo(String rollNo) {
        return studentRepository.findByRollNo(rollNo);
    }

    public String verifyStudent(String rollNo) {
        Student student = studentRepository.findByRollNo(rollNo);
        if (student != null) {
            if ("Yes".equalsIgnoreCase(student.getVerifiedStatus())) {
                return "The student is already verified";
            }
            student.setVerifiedStatus("Yes");
            studentRepository.save(student);
            return "Student verified!";
        }
        return "Error verifying student!";
    }
}
