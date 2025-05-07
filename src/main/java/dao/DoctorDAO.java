package dao;

import config.DBconnection;
import controller.PatientController;
import model.Doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DoctorDAO {


    public void registerDoctor(Doctor doctor) throws SQLException {
        String sql = "INSERT INTO doctors (doctor_id,specialization, department) VALUES (?,?,?)";

        Connection connection = DBconnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setInt(1,doctor.getUser().getId());
        stmt.setString(2,doctor.getSpecialization());
        stmt.setString(3, doctor.getDepartment());
        
        stmt.executeUpdate();

    }

}
