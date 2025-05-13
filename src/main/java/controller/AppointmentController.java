package controller;

import dao.AppointmentDAO;
import dao.DoctorDAO;
import model.Appointment;
import model.User;
import Validation_helper.InputValidator;
import java.sql.SQLException;


public class AppointmentController {
    private AppointmentDAO appointmentDAO = new AppointmentDAO();
    private DoctorDAO doctorDAO = new DoctorDAO();

    public void addAppointmentFromUser(User user) throws SQLException {
        if (!user.getRole().equalsIgnoreCase("patient")) {
            System.out.println("Only patients can schedule appointments.");
            return;
        }
        Appointment appointment = new Appointment();
        boolean doctorExiast;
        int id;
        do {
            id = InputValidator.getValidatedInt("\"Enter doctor ID: (OR Enter 0 to Exit) ");
            if(id == 0){return;}
            doctorExiast = doctorDAO.doctorExist(id);
            if(!doctorExiast){
                System.out.println("Enter Valid Doctor ID form Above list");
            }else {
                appointment.setDoctor_id(id);
            }
        }while (!doctorExiast);
        appointment.setPatient_id(user.getId());
        appointment.setAppointment_date(InputValidator.getValidatedDateFuture("Enter appointment date (YYYY-MM-DD): "));
        appointment.setAppointment_time(InputValidator.getValidatedTime("Enter appointment time (HH:MM:SS): "));
        appointment.setStatus("Scheduled");
        appointmentDAO.addAppointment(appointment);
        System.out.println("Appointment scheduled successfully.");

    }


    public void updateAppointmentStatus(User user) throws  SQLException{
        int appointmentID;
        if (user.getRole().equalsIgnoreCase("Doctor")){
            do {
                appointmentID = InputValidator.getValidatedInt("Enter Appointment ID to ReSchedule : \n To exit Enter 0 : ");
                if (appointmentID == 0) {
                    break;
                }
                if(!appointmentDAO.appointmentExistDoctor(appointmentID, user.getId())){
                    System.out.println("No associated appointment Exist for ID Provided");
                }else {
                    if (appointmentDAO.getAppointmentStatus(appointmentID)) {
                        String newStatus = InputValidator.getValidatedStatus("Enter new status (Completed/Cancelled): ");
                        appointmentDAO.updateAppointmentStatus(appointmentID, newStatus);
                        System.out.println("Appointment status updated To :" + newStatus);
                    } else {
                        System.out.println("Cancelled Or Completed Appointments can't be Updated.");
                    }
                }
            }while (!appointmentDAO.appointmentExistDoctor(appointmentID,user.getId()));
        }
        else if(user.getRole().equalsIgnoreCase("Patient")){
            do {
                appointmentID = InputValidator.getValidatedInt("Enter Appointment ID to ReSchedule : \n To exit Enter 0 : ");
                if (appointmentID == 0) {
                    break;
                }
                if(!appointmentDAO.appointmentExistPatient(appointmentID, user.getId())){
                    System.out.println("No associated appointment Exist for ID Provided");

                }else {
                    if (appointmentDAO.getAppointmentStatus(appointmentID)) {
                        appointmentDAO.updateAppointmentStatus(appointmentID, "Cancelled");
                        System.out.println("Appointment Cancelled!!!");
                    } else {
                        System.out.println("Already Cancelled Or Completed Appointments can't be Updated.");

                    }
                }

            }while (!appointmentDAO.appointmentExistPatient(appointmentID,user.getId()));
        }
    }


    public void reScheduleAppointment(User user)throws SQLException {
        if (!user.getRole().equalsIgnoreCase("Patient")) {
            System.out.println("Only Patient Can re-Schedule Appointment");
            return;
        }
        int appointmentID;
        do {
            appointmentID = InputValidator.getValidatedInt("Enter Appointment ID to ReSchedule : \n To exit Enter 0 : ");
            if(appointmentID == 0){
                break;
            }
            if(appointmentDAO.appointmentExistPatient(appointmentID, user.getId())){
            if (appointmentDAO.getAppointmentStatus(appointmentID)) {
                String date = InputValidator.getValidatedDateFuture("New Date For the Appointment: ");
                String time = InputValidator.getValidatedTime("New Time For Appointment: ");
                appointmentDAO.rescheduleAppointment(appointmentID, date, time);
                System.out.println("SuccessFully Re-Scheduled Appointment For Date: " + date + " & Time: " + time);
            } else {
                System.out.println("Cancelled Or Completed Appointments can't be Updated.");
                }
            }  else  {
                System.out.println("No Associated Appointment Exist With ID Provided");
            }
        } while (!appointmentDAO.appointmentExistPatient(appointmentID, user.getId()));
    }


    public void viewAppointments(User user) throws SQLException {
        if (user.getRole().equalsIgnoreCase("doctor")) {
            appointmentDAO.getAppointmentList(user);
        } else if (user.getRole().equalsIgnoreCase("patient")) {
            appointmentDAO.getAppointmentList(user);
        } else {
            System.out.println("Only Doctor and Patient Can view Appointment");
        }
    }


}
