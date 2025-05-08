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

        Connection connection = DBconnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setInt(1,doctor.getUser().getId());
        stmt.setString(2,doctor.getSpecialization());
        stmt.setString(3, doctor.getDepartment());
        
        stmt.executeUpdate();

    }

    public ResultSet viewAssociatedPatient(int id) throws SQLException {
        String patientList = """
                SELECT DISTINCT a.patient_id ,u.id AS PatientID, u.user_name AS PatientName, p.date_of_birth AS DOB , p.gender , p.address, p.phone ,p.insuranceID
                FROM users u
                JOIN patients p ON p.patient_id = u.id
                JOIN appointment a ON a.patient_id = p.patient_id
                WHERE a.doctor_id = ? ORDER BY u.id
                """;

        Connection connection = DBconnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(patientList);

        stmt.setInt(1, id);
        ResultSet rs =  stmt.executeQuery();
        return rs;
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
