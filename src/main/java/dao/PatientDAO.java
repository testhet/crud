package dao;

import config.DBconnection;
import model.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PatientDAO {
    private UserDAO userDAO = new UserDAO();
    public void registerPatient (Patient patient) throws SQLException {
        int PatientID = userDAO.addUser(patient.getUser());

        String sql = "INSERT INTO patients(patient_id,date_of_birth,gender,address,phone,emergency_contact_number,insuranceID,insurance_provider) VALUES = (?,?,?,?,?,?,?,?) ";
        Connection connection = DBconnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setInt(1,PatientID);
        stmt.setString(2,patient.getDate_of_birth());
        stmt.setString(3,patient.getGender());
        stmt.setString(4,patient.getAddress());
        stmt.setInt(5,patient.getPhone());
        stmt.setInt(6,patient.getEmergency_contact_number());
        stmt.setString(7,patient.getInsuranceID());
        stmt.setString(8,patient.getInsurance_provider());

        stmt.executeUpdate();

    }

}
