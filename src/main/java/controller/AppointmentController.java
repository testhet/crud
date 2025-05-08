package controller;

import dao.AppointmentDAO;
import model.Appointment;
import model.User;
import Validation_helper.InputValidator;

import java.sql.SQLException;
import java.util.List;

public class AppointmentController {

    private AppointmentDAO appointmentDAO = new AppointmentDAO();
    private User currentUser;

    public AppointmentController(User currentUser) {
        this.currentUser = currentUser;
    }

    public void addAppointmentFromUser() throws SQLException {
        if (!currentUser.getRole().equalsIgnoreCase("patient")) {
            System.out.println("Only patients can schedule appointments.");
            return;
        }

        Appointment appointment = new Appointment();

        appointment.setDoctor_id(InputValidator.getValidatedInt("Enter doctor ID: "));
        appointment.setPatient_id(currentUser.getId());
        appointment.setAppointment_date(InputValidator.getValidatedDateFuture("Enter appointment date (YYYY-MM-DD): "));
        appointment.setAppointment_time(InputValidator.getValidatedTime("Enter appointment time (HH:MM:SS): "));
        appointment.setStatus("Scheduled");

        appointmentDAO.addAppointment(appointment);
        System.out.println("Appointment scheduled successfully.");
    }

    public void updateAppointmentStatus() throws SQLException {
        if (currentUser.getRole().equalsIgnoreCase("doctor")) {
            int appointmentId = InputValidator.getValidatedInt("Enter appointment ID to update: ");
            String newStatus = InputValidator.getValidatedStatus("Enter new status (Completed/Cancelled): ");

            appointmentDAO.updateAppointmentStatus(appointmentId, newStatus);
            System.out.println("Appointment status updated To :"+ newStatus);

        } else if (currentUser.getRole().equalsIgnoreCase("patient")) {
            int appointmentId = InputValidator.getValidatedInt("Enter appointment ID to update: ");
            String newStatus = "Cancelled";
            appointmentDAO.updateAppointmentStatus(appointmentId,newStatus);
            System.out.println("Appointment Cancelled Successfully.");
        }

    }

    public void reScheduleAppointment()throws SQLException{

        if(!currentUser.getRole().equalsIgnoreCase("Patient")){
            System.out.println("Only Patient Can re-Schedule Appointment");
            return;
        }

        int appointmentID = InputValidator.getValidatedInt("Enter Appointment ID to ReSchedule : ");
        String date = InputValidator.getValidatedDateFuture("New Date For the Appointment: ");
        String time = InputValidator.getValidatedTime("New Time For Appointment: ");

        appointmentDAO.rescheduleAppointment(appointmentID,date,time);
        System.out.println("SuccessFully Re-Scheduled Appointment For Date: "+date+" & Time: "+time);


    }




    public void viewAppointments() throws SQLException {
        List<Appointment> appointments;

        if (currentUser.getRole().equalsIgnoreCase("doctor")) {
            appointments = appointmentDAO.getAppointmentsByDoctorId(currentUser.getId());
        } else if (currentUser.getRole().equalsIgnoreCase("patient")) {
            appointments = appointmentDAO.getAppointmentsByPatientId(currentUser.getId());
        } else {
            System.out.println("Invalid role for viewing appointments.");
            return;
        }

        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
        } else {
            for (Appointment appt : appointments) {
                System.out.println(appt);
            }
        }
    }
}
