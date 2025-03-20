package mthree.com.fullstackschool.service;

import mthree.com.fullstackschool.dao.TeacherDao;
import mthree.com.fullstackschool.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherServiceInterface {

    //YOUR CODE STARTS HERE
    @Autowired
    TeacherDao teacherDao;

    public TeacherServiceImpl() {
    }

    // using this because services are called with new ServiceImpl(dao) in the tests
    public TeacherServiceImpl(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    //YOUR CODE ENDS HERE

    public List<Teacher> getAllTeachers() {
        //YOUR CODE STARTS HERE

        return teacherDao.getAllTeachers();

        //YOUR CODE ENDS HERE
    }

    public Teacher getTeacherById(int id) {
        //YOUR CODE STARTS HERE

        try {
            return teacherDao.findTeacherById(id);
        } catch(DataAccessException e) {
            final Teacher teacherNotFound = new Teacher();
            teacherNotFound.setTeacherFName("Teacher Not Found.");
            teacherNotFound.setTeacherLName("Teacher Not Found.");
            return teacherNotFound;
        }

        //YOUR CODE ENDS HERE
    }

    public Teacher addNewTeacher(Teacher teacher) {
        //YOUR CODE STARTS HERE

        final boolean teacherHasNoFName = teacher.getTeacherFName() == null || teacher.getTeacherFName().isBlank();
        final boolean teacherHasNoLName = teacher.getTeacherLName() == null || teacher.getTeacherLName().isBlank();
        if(teacherHasNoFName) {
            teacher.setTeacherFName("First Name blank, teacher NOT added");
        }
        if(teacherHasNoLName) {
            teacher.setTeacherLName("Last Name blank, teacher NOT added");
        }
        if(teacherHasNoFName || teacherHasNoLName) {
            return teacher;
        }

        return teacherDao.createNewTeacher(teacher);

        //YOUR CODE ENDS HERE
    }

    public Teacher updateTeacherData(int id, Teacher teacher) {
        //YOUR CODE STARTS HERE


        if(id != teacher.getTeacherId()) {
            teacher.setTeacherFName("IDs do not match, teacher not updated");
            teacher.setTeacherLName("IDs do not match, teacher not updated");
            return teacher;
        }

        teacherDao.updateTeacher(teacher);
        return getTeacherById(id);

        //YOUR CODE ENDS HERE
    }

    public void deleteTeacherById(int id) {
        //YOUR CODE STARTS HERE

        teacherDao.deleteTeacher(id);

        //YOUR CODE ENDS HERE
    }
}
