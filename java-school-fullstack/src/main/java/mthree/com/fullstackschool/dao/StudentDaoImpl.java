package mthree.com.fullstackschool.dao;

import mthree.com.fullstackschool.dao.mappers.StudentMapper;
import mthree.com.fullstackschool.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.sql.*;
import java.util.List;
import java.util.Objects;

@Repository
public class StudentDaoImpl implements StudentDao {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public StudentDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Student createNewStudent(Student student) {
        //YOUR CODE STARTS HERE


        final String INSERT_COURSE = "INSERT INTO student (fName, lName) VALUES (?,?)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(INSERT_COURSE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, student.getStudentFirstName());
            ps.setString(2, student.getStudentLastName());
            return ps;
        }, keyHolder);

        student.setStudentId(keyHolder.getKey().intValue());

        return student;


        //YOUR CODE ENDS HERE
    }

    @Override
    public List<Student> getAllStudents() {
        //YOUR CODE STARTS HERE


        final String SELECT_ALL = "SELECT * FROM student";
        return jdbcTemplate.query(SELECT_ALL, new StudentMapper());

        //YOUR CODE ENDS HERE
    }

    @Override
    public Student findStudentById(int id) {
        //YOUR CODE STARTS HERE

        final String SELECT_SPECIFIC_STUDENT = "SELECT * FROM student WHERE sid = ?";
        return jdbcTemplate.queryForObject(SELECT_SPECIFIC_STUDENT, new StudentMapper(), id);

        //YOUR CODE ENDS HERE
    }

    @Override
    public void updateStudent(Student student) {
        //YOUR CODE STARTS HERE

        final String UPDATE_STUDENT = "UPDATE student"
            + " SET fName = ?, lName = ?"
            + " WHERE sid = ?";
        jdbcTemplate.update(
                UPDATE_STUDENT,
                student.getStudentFirstName(),
                student.getStudentLastName(),
                student.getStudentId()
        );

        //YOUR CODE ENDS HERE
    }

    @Override
    public void deleteStudent(int id) {
        //YOUR CODE STARTS HERE

        // remove references
        final String DELETE_ALL_STUDENT_COURSES = "DELETE FROM course_student WHERE student_id = ?";
        jdbcTemplate.update(DELETE_ALL_STUDENT_COURSES, id);

        final String DELETE_STUDENT = "DELETE FROM student WHERE sid = ?";
        jdbcTemplate.update(DELETE_STUDENT, id);

        //YOUR CODE ENDS HERE
    }

    @Override
    public void addStudentToCourse(int studentId, int courseId) {
        //YOUR CODE STARTS HERE

        final String INSERT_COURSE_STUDENT = "INSERT INTO course_student (course_id, student_id) VALUES (?,?)";
        jdbcTemplate.update(INSERT_COURSE_STUDENT, courseId, studentId);

        //YOUR CODE ENDS HERE
    }

    @Override
    public void deleteStudentFromCourse(int studentId, int courseId) {
        //YOUR CODE STARTS HERE

        final String DELETE_COURSE_STUDENT = "DELETE FROM course_student WHERE course_id = ? AND student_id = ?";
        jdbcTemplate.update(DELETE_COURSE_STUDENT, courseId, studentId);

        //YOUR CODE ENDS HERE
    }
}
