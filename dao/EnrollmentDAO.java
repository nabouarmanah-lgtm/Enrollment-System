package dao;

import config.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.Enrollment;

public class EnrollmentDAO {

    public List<Enrollment> findAll() {
        List<Enrollment> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM enrollment";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Enrollment e = new Enrollment(
                        rs.getInt("enroll_id"),
                        rs.getInt("student_id"),
                        rs.getInt("course_id"),
                        rs.getString("enroll_date")
                );
                list.add(e);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public boolean insert(Enrollment e) {
        try {
            Connection con = DBConnection.getInstance().getConnection();

            // Check duplicate
            String checkSql = "SELECT COUNT(*) FROM enrollment WHERE student_id=? AND course_id=?";
            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setInt(1, e.getStudentId());
            checkPs.setInt(2, e.getCourseId());
            ResultSet rs = checkPs.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false;
            }

            String sql = "INSERT INTO enrollment (student_id, course_id, enroll_date) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, e.getStudentId());
            ps.setInt(2, e.getCourseId());
            ps.setString(3, e.getEnrollDate());
            ps.executeUpdate();
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean delete(Enrollment e) {
        try {
            Connection con = DBConnection.getInstance().getConnection();
            String sql = "DELETE FROM enrollment WHERE enroll_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, e.getEnrollId());
            ps.executeUpdate();
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean update(Enrollment e) {
        try {
            Connection con = DBConnection.getInstance().getConnection();
            String sql = "UPDATE enrollment SET enroll_date=? WHERE enroll_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, e.getEnrollDate());
            ps.setInt(2, e.getEnrollId());
            ps.executeUpdate();
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
