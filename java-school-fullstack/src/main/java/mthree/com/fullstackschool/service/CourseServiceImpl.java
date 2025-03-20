package mthree.com.fullstackschool.service;

import mthree.com.fullstackschool.dao.CourseDao;
import mthree.com.fullstackschool.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseServiceInterface {

    //YOUR CODE STARTS HERE
    @Autowired
    CourseDao courseDao;

    public CourseServiceImpl() {
    }

    // using this because services are called with new ServiceImpl(dao) in the tests
    public CourseServiceImpl(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    //YOUR CODE ENDS HERE

    public List<Course> getAllCourses() {
        //YOUR CODE STARTS HERE

        return courseDao.getAllCourses();

        //YOUR CODE ENDS HERE
    }

    public Course getCourseById(int id) {
        //YOUR CODE STARTS HERE

        try {
            return courseDao.findCourseById(id);
        } catch(DataAccessException e) {
            final Course notFoundCourse = new Course();
            notFoundCourse.setCourseName("Course Not Found");
            notFoundCourse.setCourseDesc("Course Not Found");
            return notFoundCourse;
        }

        //YOUR CODE ENDS HERE
    }

    public Course addNewCourse(Course course) {
        //YOUR CODE STARTS HERE

        final boolean courseHasNoName = course.getCourseName() == null || course.getCourseName().isBlank();
        final boolean courseHasNoDesc = course.getCourseDesc() == null || course.getCourseDesc().isBlank();

        if(courseHasNoName) {
            course.setCourseName("Name blank, course NOT added");
        }
        if(courseHasNoDesc) {
            course.setCourseDesc("Description blank, course NOT added");
        }
        if(courseHasNoName || courseHasNoDesc) {
            return course;
        }


        return courseDao.createNewCourse(course);

        //YOUR CODE ENDS HERE
    }

    public Course updateCourseData(int id, Course course) {
        //YOUR CODE STARTS HERE

        if(id != course.getCourseId()) {
            course.setCourseName("IDs do not match, course not updated");
            course.setCourseDesc("IDs do not match, course not updated");
            return course;
        }

        courseDao.updateCourse(course);
        return getCourseById(id);

        //YOUR CODE ENDS HERE
    }

    public void deleteCourseById(int id) {
        //YOUR CODE STARTS HERE

        courseDao.deleteCourse(id);
        System.out.println("Course ID: " + id + " deleted");

        //YOUR CODE ENDS HERE
    }
}
