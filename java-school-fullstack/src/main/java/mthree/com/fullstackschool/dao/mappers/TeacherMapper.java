package mthree.com.fullstackschool.dao.mappers;

import mthree.com.fullstackschool.model.Teacher;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherMapper implements RowMapper<Teacher> {
    @Override
    public Teacher mapRow(ResultSet rs, int rowNum) throws SQLException {
        //YOUR CODE STARTS HERE

        final Teacher teacher = new Teacher();
        teacher.setTeacherId(rs.getInt("tid"));
        teacher.setDept(rs.getString("dept"));
        teacher.setTeacherFName(rs.getString("tFName"));
        teacher.setTeacherLName(rs.getString("tLName"));

        return teacher;

        //YOUR CODE ENDS HERE
    }
}
