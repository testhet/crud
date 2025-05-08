package dao;

import config.DBconnection;
import model.Patient;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PatientDAO {

    public void registerPatient (Patient patient) throws SQLException {


        String sql = "INSERT INTO patients(patient_id,date_of_birth,gender,address,phone,emergency_contact_number,insuranceID,insurance_provider) VALUES  (?,?,?,?,?,?,?,?) ";
        Connection connection = DBconnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setInt(1,patient.getUser().getId());
        stmt.setString(2,patient.getDate_of_birth());
        stmt.setString(3,patient.getGender());
        stmt.setString(4,patient.getAddress());
        stmt.setLong(5,patient.getPhone());
        stmt.setLong(6,patient.getEmergency_contact_number());
        stmt.setString(7,patient.getInsuranceID());
        stmt.setString(8,patient.getInsurance_provider());

        stmt.executeUpdate();

    }

    public void updatePatientDetails(String address, long phone , long emergencyContact , int patientID  )throws SQLException{
        String sql = "UPDATE patients SET address = ?, phone = ? , emergency_contact_number = ? WHERE patient_id = ? ";
        Connection connection = DBconnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setString(1, address);
        stmt.setLong(2,phone);
        stmt.setLong(3,emergencyContact);
        stmt.setInt(4, patientID);

        stmt.executeUpdate();

    }

}
