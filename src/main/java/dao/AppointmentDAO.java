package dao;

import config.DBconnection;
import model.Appointment;
import model.User;
import java.sql.*;

public class AppointmentDAO {

    public void addAppointment(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO appointment (doctor_id, patient_id, appointment_date, appointment_time, status) " +
                "VALUES (?, ?, ?, ?, ?)";
        try(Connection connection = DBconnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, appointment.getDoctor_id());
            stmt.setInt(2, appointment.getPatient_id());
            stmt.setDate(3, Date.valueOf(appointment.getAppointment_date()));
            stmt.setTime(4, Time.valueOf(appointment.getAppointment_time()));
            stmt.setString(5, appointment.getStatus());
            stmt.executeUpdate();
            System.out.println("Appointment inserted successfully.");
        }
    }

    public void updateAppointmentStatus(int appointmentId, String status) throws SQLException {
        String sql = "UPDATE appointment SET status = ? WHERE id = ? ";
        try(Connection connection = DBconnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, appointmentId);
            stmt.executeUpdate();
        }
    }

    public void rescheduleAppointment (int id, String date , String time) throws SQLException{
        String sql = "UPDATE appointment SET appointment_date = ? , appointment_time = ? WHERE id = ? ;";
        try(Connection connection = DBconnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setDate(2, Date.valueOf(date));
            stmt.setTime(3, Time.valueOf(time));
            stmt.executeUpdate();
        }
    }

    public boolean getAppointmentStatus (int id) throws SQLException {
        String sql = "SELECT * FROM appointment WHERE id = ? && status = \"Scheduled\" ; ";
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }



    public ResultSet getAppointmentList(User currentUser) throws SQLException {
        String appointmentListForDoctor = """
                 SELECT
                      a.id as AppointmentID,
                      u.id AS PatientID,
                      u.user_name AS PatientName,
                      p.gender AS Gender,
                      p.phone AS Phone,
                      a.appointment_date AS Date,
                      a.appointment_time AS Time,
                      a.status AS "Appointment Status"
                  FROM appointment a
                  JOIN patients p ON p.patient_id = a.patient_id
                  JOIN users u ON u.id = p.patient_id
                  WHERE a.doctor_id = ? AND u.role = "PATIENT"
                  ORDER BY a.appointment_date, a.appointment_time;
                """;
        String appointmentListForPatient = """
                SELECT
                    a.id AS AppointmentID,
                    u.id AS DoctorID,
                    u.user_name AS DoctorName,
                    d.department AS Department,
                    a.appointment_date AS Date,
                    a.appointment_time AS Time,
                    a.status AS "Appointment Status"
                FROM appointment a
                JOIN doctors d ON d.doctor_id = a.doctor_id
                JOIN users u ON u.id = d.doctor_id
                WHERE a.patient_id = ? AND u.role = "DOCTOR"
                ORDER BY a.appointment_date,a.appointment_time;
                """;
        try (Connection connection = DBconnection.getConnection()) {
            PreparedStatement statement;
            ResultSet resultSet = null;
            try {
                if (currentUser.getRole().equalsIgnoreCase("DOCTOR")) {
                    statement = connection.prepareStatement(appointmentListForDoctor);
                    statement.setInt(1, currentUser.getId()); // Set doctor_id parameter
                } else if (currentUser.getRole().equalsIgnoreCase("PATIENT")) {
                    statement = connection.prepareStatement(appointmentListForPatient);
                    statement.setInt(1, currentUser.getId()); // Set patient_id parameter
                } else {
                    System.out.println("Invalid user role: " + currentUser.getRole());
                    return null;
                }
                resultSet = statement.executeQuery();
            } catch (SQLException e) {
                System.err.println("Error retrieving appointment list: " + e.getMessage());
            }
            return resultSet;
        }
    }
}
