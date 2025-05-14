package dao;

import config.DBconnection;
import model.Doctor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorDAO {


    public void registerDoctor(Doctor doctor) throws SQLException {
        String sql = "INSERT INTO doctors (doctor_id,specialization, department) VALUES (?,?,?)";
        try(Connection connection = DBconnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, doctor.getUser().getId());
            stmt.setString(2, doctor.getSpecialization());
            stmt.setString(3, doctor.getDepartment());
            stmt.executeUpdate();
        }
    }

    public boolean doctorExist(int id) throws  SQLException {
      String sql = "SELECT 1 FROM users WHERE id = ? && role = \"doctor\" LIMIT 1";
      try (Connection connection = DBconnection.getConnection();
      PreparedStatement stmt = connection.prepareStatement(sql)
      ) {
          stmt.setInt(1, id);
          ResultSet rs = stmt.executeQuery();
          return rs.next();
      }
    }


    public void doctorsProfile () throws SQLException {
        String sql = """
                SELECT
                    u.id AS DoctorID,
                    u.user_name AS DoctorName,
                    d.department AS Department,
                    d.specialization AS Specialization
                FROM users u
                JOIN doctors d ON d.doctor_id = u.id
                WHERE u.role = "Doctor";
                """;
        boolean found;
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            System.out.printf("%-5s %-18s %-13s %-20s%n", "DoctorID", "Doctor Name", "Department", "Specialization");
            System.out.println("----------------------------------------------------------------------");
            found = false;
            while (rs.next()) {
                found = true;
                int PatientID = rs.getInt(1);
                String name = rs.getString(2);
                String department = rs.getString(3);
                String specialization = rs.getString(4);
                System.out.printf("%-5s %-18s %-13s %-20s%n", PatientID, name, department, specialization);
            }
            if (!found) {
                System.out.println("No Doctors Available");
            }
        }
    }

    public boolean associatedPatientExist(int Did,int Pid) throws SQLException {
        String sql = """
                SELECT 1
                FROM appointment a
                WHERE a.doctor_id = ? AND a.patient_id = ?
                LIMIT 1;
                """;
        try(Connection connection = DBconnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,Did);
            stmt.setInt(2,Pid);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public void viewAssociatedPatient(int id) throws SQLException {
        String patientList = """
                SELECT DISTINCT
                        a.patient_id ,
                        u.id AS PatientID,
                        u.user_name AS PatientName,
                        p.date_of_birth AS DOB ,
                        p.gender ,
                        p.address,
                        p.phone ,
                        p.insuranceID
                FROM users u
                JOIN patients p ON p.patient_id = u.id
                JOIN appointment a ON a.patient_id = p.patient_id
                WHERE a.doctor_id = ? ORDER BY u.id
                """;
        boolean found;
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(patientList)
             ) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            found = false;
            System.out.printf("%-15s %-25s %-22s %-18s %-30s %-22s %-25s%n",
                    "PatientID", "Name", "DOB", "Gender", "Address", "Phone", "InsuranceID");
            System.out.println("------------------------------------------------------------------------------------------------------------------------");
            while (rs.next()) {
                found = true;
                int patientId = rs.getInt("PatientID");
                String name = rs.getString("PatientName");
                String dob = rs.getString("DOB");
                String gender = rs.getString("gender");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                String insuranceID = rs.getString("insuranceID");
                System.out.printf("%-15d %-25s %-22s %-18s %-30s %-22s %-25s%n",
                        patientId, name, dob, gender, address, phone, insuranceID);
            }if(!found){
                System.out.println("No associated Patient Found");
            }
        }
    }


    public void deletePatient(int id) throws SQLException{
        String deletePatient = "DELETE FROM patients WHERE patient_id = ?";
        String deleteUser = "DELETE FROM users WHERE id = ?";
        String updateAppointments = "DELETE FROM appointment WHERE patient_id = ?";
        try(Connection connection = DBconnection.getConnection();
        PreparedStatement updateAppointmentStmt = connection.prepareStatement(updateAppointments);
        PreparedStatement deletePatientStmt = connection.prepareStatement(deletePatient);
        PreparedStatement deleteUSerStmt = connection.prepareStatement(deleteUser)
        ){
            connection.setAutoCommit(false);
            updateAppointmentStmt.setInt(1, id);
            updateAppointmentStmt.executeUpdate();

            deletePatientStmt.setInt(1, id);
            deletePatientStmt.executeUpdate();

            deleteUSerStmt.setInt(1, id);
            deleteUSerStmt.executeUpdate();
            connection.commit();
        }
    }
}
