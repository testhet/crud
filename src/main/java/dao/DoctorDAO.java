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
        try(Connection connection = DBconnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()){
            System.out.printf("%-5s %-18s %-13s %-20s%n", "DoctorID", "Doctor Name", "Department", "Specialization");
            System.out.println("----------------------------------------------------------------------");
            boolean found = false;
            while (rs.next()){
                found = true;
                int PatientID = rs.getInt(1);
                String name = rs.getString(2);
                String department = rs.getString(3);
                String specialization = rs.getString(4);
                System.out.printf("%-5s %-18s %-13s %-20s%n",PatientID,name , department , specialization);
            }if (!found) {
                System.out.println("No Doctors Available");
            }
        }
    }

    public void viewAssociatedPatient(int id) throws SQLException {
        String patientList = """
                SELECT DISTINCT a.patient_id ,u.id AS PatientID, u.user_name AS PatientName, p.date_of_birth AS DOB , p.gender , p.address, p.phone ,p.insuranceID
                FROM users u
                JOIN patients p ON p.patient_id = u.id
                JOIN appointment a ON a.patient_id = p.patient_id
                WHERE a.doctor_id = ? ORDER BY u.id
                """;
        try(Connection connection = DBconnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(patientList);
        ResultSet rs = stmt.executeQuery()) {
            stmt.setInt(1, id);
            boolean found = false;
            System.out.printf("%-5s %-15s %-12s %-8s %-20s %-12s %-15s%n",
                    "PatientID", "Name", "DOB", "Gender", "Address", "Phone", "InsuranceID");
            System.out.println("-------------------------------------------------------------------------------");
            while (rs.next()) {
                found = true;
                int patientId = rs.getInt("PatientID");
                String name = rs.getString("PatientName");
                String dob = rs.getString("DOB");
                String gender = rs.getString("gender");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                String insuranceID = rs.getString("insuranceID");
                System.out.printf("%-5d %-15s %-12s %-8s %-20s %-12s %-15s%n",
                        patientId, name, dob, gender, address, phone, insuranceID);
            }
            if (!found) {
                System.out.println("No patients found associated with doctor ID: " + id);
            }
        }
    }


    public void deletePatient(int id) throws SQLException{
        String deletePatient = "DELETE FROM patients WHERE id = ?";
        String deleteUser = "DELETE FROM doctors WHERE id = ?";
        String updateAppointments = "UPDATE appointment SET status = ? WHERE patient_id = ?";
        try(Connection connection = DBconnection.getConnection();
        PreparedStatement deletePatientStmt = connection.prepareStatement(deletePatient);
        PreparedStatement deleteUSerStmt = connection.prepareStatement(deleteUser);
        PreparedStatement updateAppointmentStmt = connection.prepareStatement(updateAppointments)
        ){
            connection.setAutoCommit(false);
            deletePatientStmt.setInt(1,id);
            deleteUSerStmt.setInt(1,id);
            updateAppointmentStmt.setString(1,"Cancelled Due To User DELETED");
            connection.commit();
            System.out.println("Successfully Deleted Patient");
        }
    }
}
