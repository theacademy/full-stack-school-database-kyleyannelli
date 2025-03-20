package mthree.com.fullstackschool.service;

import mthree.com.fullstackschool.dao.StudentDao;
import mthree.com.fullstackschool.model.Course;
import mthree.com.fullstackschool.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentServiceInterface {

    //YOUR CODE STARTS HERE
    @Autowired
    private StudentDao studentDao;

    @Autowired
    private CourseServiceInterface courseService;

    // using this because services are called with new ServiceImpl(dao) in the tests
    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    //YOUR CODE ENDS HERE

    public List<Student> getAllStudents() {
        //YOUR CODE STARTS HERE

        return studentDao.getAllStudents();

        //YOUR CODE ENDS HERE
    }

    public Student getStudentById(int id) {
        //YOUR CODE STARTS HERE

        try {
            return studentDao.findStudentById(id);
        } catch(DataAccessException e) {
            final Student notFoundStudent = new Student();
            notFoundStudent.setStudentFirstName("Student Not Found");
            notFoundStudent.setStudentLastName("Student Not Found");
            return notFoundStudent;
        }

        //YOUR CODE ENDS HERE
    }

    public Student addNewStudent(Student student) {
        //YOUR CODE STARTS HERE

        final boolean studentHasNoFName = student.getStudentFirstName() == null || student.getStudentFirstName().isBlank();
        final boolean studentHasNoLName = student.getStudentLastName() == null || student.getStudentLastName().isBlank();
        if(studentHasNoFName) {
            student.setStudentFirstName("First Name blank, student NOT added");
        }
        if(studentHasNoLName) {
            student.setStudentLastName("Last Name blank, student NOT added");
        }
        if(studentHasNoFName || studentHasNoLName) {
            return student;
        }

        return studentDao.createNewStudent(student);

        //YOUR CODE ENDS HERE
    }

    public Student updateStudentData(int id, Student student) {
        //YOUR CODE STARTS HERE

        if(id != student.getStudentId()) {
            student.setStudentFirstName("IDs do not match, student not updated");
            student.setStudentLastName("IDs do not match, student not updated");
            return student;
        }

        studentDao.updateStudent(student);
        return getStudentById(id);

        //YOUR CODE ENDS HERE
    }

    public void deleteStudentById(int id) {
        //YOUR CODE STARTS HERE

        studentDao.deleteStudent(id);

        //YOUR CODE ENDS HERE
    }

    public void deleteStudentFromCourse(int studentId, int courseId) {
        //YOUR CODE STARTS HERE

        final Student student = getStudentById(studentId);
        if(student.getStudentFirstName().equals("Student Not Found")) {
            System.out.println("Student not found");
            return;
        }

        final Course course = courseService.getCourseById(courseId);
        if(course.getCourseName().equals("Course Not Found")) {
            System.out.println("Course not found");
            return;
        }

        studentDao.deleteStudentFromCourse(studentId, courseId);
        System.out.printf(
                "Student: %d deleted from course: %d",
                studentId,
                courseId
        );

        //YOUR CODE ENDS HERE
    }

    public void addStudentToCourse(int studentId, int courseId) {
        //YOUR CODE STARTS HERE

        final Student student = getStudentById(studentId);
        if(student.getStudentFirstName().equals("Student Not Found")) {
            System.out.println("Student not found");
            return;
        }

        final Course course = courseService.getCourseById(courseId);
        if(course.getCourseName().equals("Course Not Found")) {
            System.out.println("Course not found");
            return;
        }

        try {
            studentDao.addStudentToCourse(studentId, courseId);
            System.out.format(
                    "Student: %d added to course: %d",
                    studentId,
                    courseId
            );
        } catch(DataAccessException e) {
            System.out.format(
                    "Student: %d already enrolled in course: %d",
                    studentId,
                    courseId
            );
        }

        //YOUR CODE ENDS HERE
    }
}
