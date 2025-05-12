package controller;

import dao.AppointmentDAO;
import model.Appointment;
import model.User;
import Validation_helper.InputValidator;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;

public class AppointmentController {
    private AppointmentDAO appointmentDAO = new AppointmentDAO();

    public void addAppointmentFromUser(User user) throws SQLException {
        if (!user.getRole().equalsIgnoreCase("patient")) {
            System.out.println("Only patients can schedule appointments.");
            return;
        }
        Appointment appointment = new Appointment();
        appointment.setDoctor_id(InputValidator.getValidatedInt("Enter doctor ID: "));
        appointment.setPatient_id(user.getId());
        appointment.setAppointment_date(InputValidator.getValidatedDateFuture("Enter appointment date (YYYY-MM-DD): "));
        appointment.setAppointment_time(InputValidator.getValidatedTime("Enter appointment time (HH:MM:SS): "));
        appointment.setStatus("Scheduled");

        appointmentDAO.addAppointment(appointment);
        System.out.println("Appointment scheduled successfully.");
    }


    public void updateAppointmentStatus(User user) throws SQLException {
        if (user.getRole().equalsIgnoreCase("doctor")) {
            int appointmentId = InputValidator.getValidatedInt("Enter appointment ID to update: ");
            if(appointmentDAO.getAppointmentStatus(appointmentId)){
            String newStatus = InputValidator.getValidatedStatus("Enter new status (Completed/Cancelled): ");
            appointmentDAO.updateAppointmentStatus(appointmentId, newStatus);
            System.out.println("Appointment status updated To :"+ newStatus);}
            else {
                System.out.println("Appointment is already cancelled or completed");
            }
        } else if (user.getRole().equalsIgnoreCase("patient")) {
            int appointmentId = InputValidator.getValidatedInt("Enter appointment ID to Cancel: ");
            String newStatus = "Cancelled";
            if (appointmentDAO.getAppointmentStatus(appointmentId)){
            appointmentDAO.updateAppointmentStatus(appointmentId,newStatus);
            System.out.println("Appointment Cancelled Successfully.");}
            else {
                System.out.println("Appointment is already cancelled or completed");
            }
        }
    }


    public void reScheduleAppointment(User user)throws SQLException{
        if(!user.getRole().equalsIgnoreCase("Patient")){
            System.out.println("Only Patient Can re-Schedule Appointment");
            return;
        }
        int appointmentID = InputValidator.getValidatedInt("Enter Appointment ID to ReSchedule : ");
        if(appointmentDAO.getAppointmentStatus(appointmentID)){
            String date = InputValidator.getValidatedDateFuture("New Date For the Appointment: ");
            String time = InputValidator.getValidatedTime("New Time For Appointment: ");
            appointmentDAO.rescheduleAppointment(appointmentID,date,time);
            System.out.println("SuccessFully Re-Scheduled Appointment For Date: "+date+" & Time: "+time);
        }else {
            System.out.println("Cancelled Or Completed Appointments can't be Updated.");
        }

    }




    public void viewAppointments(User user) throws SQLException {
        if (user.getRole().equalsIgnoreCase("doctor")) {
         ResultSet rs =  appointmentDAO.getAppointmentList(user);
            System.out.printf("%-3s %-3s %-15s %-8s %-12s %-12s %-10s %-12s%n","AppointmentID", "PatientID","PatientName","Gender","Phone","AppointmentDate","AppointmentTime", "Status");
            System.out.println("-----------------------------------------------------------------------------------------");
            if (rs == null) {
                System.out.println("No Appointment Found");
            } else {
            while (rs.next()){

                int appointmentID = rs.getInt("AppointmentID");
                int patientID = rs.getInt("PatientID");
                String PatientName = rs.getString("PatientName");
                String Gender = rs.getString("Gender");
                String Phone = rs.getString("Phone");
                Date date = rs.getDate("Date");
                Time time =  rs.getTime("Time");
                String status = rs.getString("Appointment Status");
                System.out.printf("%-3s %-3s %-15s %-8s %-12s %-12s %-10s %-12s%n",appointmentID,patientID,PatientName,Gender,Phone,date,time,status);
                }
            }
        } else if (user.getRole().equalsIgnoreCase("patient")) {
            ResultSet rs = appointmentDAO.getAppointmentList(user);
            System.out.printf("%-3s %-3s %-15s %-12s %-12s %-10s %-12s%n", "AppointmentID", "PatientID", "DoctorName", "Department", "AppointmentDate", "AppointmentTime", "Status");
            System.out.println("-----------------------------------------------------------------------------------------");
            if (rs == null) {
                System.out.println("No Appointment Found");
            } else {
                while (rs.next()) {
                    int appointmentID = rs.getInt("AppointmentID");
                    int doctorID = rs.getInt("DoctorID");
                    String DoctorName = rs.getString("DoctorName");
                    String Department = rs.getString("Department");
                    Date date = rs.getDate("Date");
                    Time time = rs.getTime("Time");
                    String status = rs.getString("Appointment Status");
                    System.out.printf("%-3s %-3s %-15s %-12s %-12s %-10s %-12s%n", appointmentID, doctorID, DoctorName, Department, date, time, status);
                }
            }
        }
            else {
            System.out.println("Only Doctor and Patient Can view Appointment");
        }
    }


}
