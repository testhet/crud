package dao;

import config.DBconnection;
import model.Patient;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientDAO {

    public void registerPatient (Patient patient) throws SQLException {


        String sql = "INSERT INTO patients(patient_id,date_of_birth,gender,address,phone,emergency_contact_number,insuranceID,insurance_provider) VALUES  (?,?,?,?,?,?,?,?) ";
       try( Connection connection = DBconnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {

           stmt.setInt(1, patient.getUser().getId());
           stmt.setString(2, patient.getDate_of_birth());
           stmt.setString(3, patient.getGender());
           stmt.setString(4, patient.getAddress());
           stmt.setLong(5, patient.getPhone());
           stmt.setLong(6, patient.getEmergency_contact_number());
           stmt.setString(7, patient.getInsuranceID());
           stmt.setString(8, patient.getInsurance_provider());
           stmt.executeUpdate();
       }
    }

    public boolean viewProfile(int i) throws SQLException{
        String sql = """
                SELECT
                    u.id AS PatientID,
                    u.email AS Email,
                    u.user_name AS "Patient Name",
                    p.date_of_birth AS DOB,
                    p.address AS Address,
                    p.phone AS Phone,
                    p.emergency_contact_number AS "Emergency Contact Number",
                    p.insuranceID AS "Insurance ID",
                    p.insurance_provider AS "Insurance Provider"
                FROM users u
                JOIN patients p ON p.patient_id = u.id
                WHERE id = ? ;
                """;
       try(Connection connection = DBconnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
           stmt.setInt(1, i);

           try (ResultSet rs = stmt.executeQuery()) {
               if (rs.next()) {
                System.out.printf("%-28s%s%n", "Patient ID:", rs.getInt("PatientID"));
                System.out.printf("%-28s%s%n", "Email:", rs.getString("Email"));
                System.out.printf("%-28s%s%n", "Patient Name:", rs.getString("Patient Name"));
                System.out.printf("%-28s%s%n", "Date of Birth:", rs.getDate("DOB"));
                System.out.printf("%-28s%s%n", "Address:", rs.getString("Address"));
                System.out.printf("%-28s%s%n", "Phone:", rs.getString("Phone"));
                System.out.printf("%-28s%s%n", "Emergency Contact Number:", rs.getString("Emergency Contact Number"));
                System.out.printf("%-28s%s%n", "Insurance ID:", rs.getString("Insurance ID"));
                System.out.printf("%-28s%s%n", "Insurance Provider:", rs.getString("Insurance Provider"));
               } else {
                   return false;
               }
           }
       }
       return true;
    }


    public boolean phoneExist(long  s) throws SQLException{
        String sql = "SELECT * FROM patients WHERE phone = ?";
        try(Connection connection = DBconnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, s);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public boolean insuranceIDExist(String s) throws SQLException{
        String sql = "SELECT * FROM patients WHERE insuranceID = ?";
        try(Connection connection = DBconnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)){
        stmt.setString(1,s);
        ResultSet rs = stmt.executeQuery();
        return rs.next();
        }
    }


    public void updatePatientDetails(String address, long phone , long emergencyContact , int patientID  )throws SQLException {
        String sql = "UPDATE patients SET address = ?, phone = ? , emergency_contact_number = ? WHERE patient_id = ? ";
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, address);
            stmt.setLong(2, phone);
            stmt.setLong(3, emergencyContact);
            stmt.setInt(4, patientID);
            stmt.executeUpdate();
        }
    }
}
