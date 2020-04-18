package model;


import java.sql.Time;
import java.util.Date;

public class Appointment {
    private Integer id;
    private String firstName;
    private String lastName;
    private AppointmentType typeAppointment;
    private Date dateAppointment;
    private Time hourAppointment;
    private String phone;

    public Appointment(Integer id, String firstName, String lastName, AppointmentType typeAppointment, Date dateAppointment, Time hourAppointment, String phone) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.typeAppointment = typeAppointment;
        this.dateAppointment = dateAppointment;
        this.hourAppointment = hourAppointment;
        this.phone = phone;
    }

    public Appointment(String firstName, String lastName, AppointmentType typeAppointment, Date dateAppointment, Time hourAppointment, String phone) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.typeAppointment = typeAppointment;
        this.dateAppointment = dateAppointment;
        this.hourAppointment = hourAppointment;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public AppointmentType getTypeAppointment() {
        return typeAppointment;
    }

    public void setTypeAppointment(AppointmentType typeAppointment) {
        this.typeAppointment = typeAppointment;
    }

    public Date getDateAppointment() {
        return dateAppointment;
    }

    public void setDateAppointment(Date dateAppointment) {
        this.dateAppointment = dateAppointment;
    }

    public Time getHourAppointment() {
        return hourAppointment;
    }

    public void setHourAppointment(Time hourAppointment) {
        this.hourAppointment = hourAppointment;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
