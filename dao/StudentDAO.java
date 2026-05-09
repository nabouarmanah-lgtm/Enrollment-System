package dao;

import config.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public List<Integer> getAllStudentsids() {
        List<Integer> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getInstance().getConnection();
            String sql = "SELECT student_id FROM students";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                list.add(rs.getInt("student_id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}