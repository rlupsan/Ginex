package dao;

import dao.connection.ConnectionClose;
import dao.connection.ConnectionFactory;
import model.Appointment;
import model.AppointmentType;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppointmentDAO {

    private static final Logger LOGGER = Logger.getLogger(AppointmentDAO.class.getName());
    //Create
    private static final String insertStatementString =
            "INSERT INTO appointment (firstName, lastName, typeAppointment, dateAppointment, hourAppointment, phone ) VALUES (?,?,?,?,?,?)";
    //Read
    private final static String findStatementString =
            "SELECT * FROM appointment where id = ?";
    //Update
    private static final String updateStatementString =
            "UPDATE appointment SET firstName =?, lastName=?, typeAppointment=? ,dateAppointment=?, hourAppointment=?, phone=? WHERE id = ?";
    //Delete
    private static final String deleteStatementString = "DELETE FROM appointment where id = ?";


    public static ArrayList<Appointment> getAppointmentList() {
        ArrayList<Appointment> appointments = new ArrayList<>();
        try {
            Connection dbConnection = ConnectionFactory.getConnection();
            String query = "SELECT * FROM appointment order by dateAppointment ASC, hourAppointment ASC";
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Appointment appointment = new Appointment(resultSet.getInt("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        AppointmentType.valueOf(resultSet.getString("typeAppointment")),
                        resultSet.getDate("dateAppointment"),
                        resultSet.getTime("hourAppointment"),
                        resultSet.getString("phone")
                );
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

//    public static Appointment findById(int id) {
//        Appointment toReturn = null;
//
//        Connection dbConnection = ConnectionFactory.getConnection();
//        PreparedStatement findStatement = null;
//        ResultSet rs = null;
//        try {
//            findStatement = dbConnection.prepareStatement(findStatementString);
//            findStatement.setLong(1, id);
//            rs = findStatement.executeQuery();
//            rs.next();
//
//            String name = rs.getString("name");
//            String pnc = rs.getString("pnc");
//            String idCard = rs.getString("idCard");
//            String address = rs.getString("address");
//            toReturn = new Client(name, pnc, idCard, address);
//        } catch (SQLException e) {
//            LOGGER.log(Level.WARNING, "ClientDAO:findById " + e.getMessage());
//        } finally {
//            ConnectionClose.close(rs);
//            ConnectionClose.close(findStatement);
//        }
//        return toReturn;
//    }

    public static int insert(Appointment appointment) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, appointment.getFirstName());
            insertStatement.setString(2, appointment.getLastName());
            insertStatement.setString(3, String.valueOf(appointment.getTypeAppointment()));
            insertStatement.setDate(4, (Date) appointment.getDateAppointment());
            insertStatement.setTime(5, appointment.getHourAppointment());
            insertStatement.setString(6, appointment.getPhone());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "AppointmentDAO:insert " + e.getMessage());
        } finally {
            ConnectionClose.close(insertStatement);
        }
        return insertedId;
    }


    public static int delete(int id) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement deleteStatement = null;
        int deletedId = -1;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
            deleteStatement.setInt(1, id);
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "AppointmentDAO:delete " + e.getMessage());
        } finally {
            ConnectionClose.close(deleteStatement);
        }
        return deletedId;
    }


    public static int update(Appointment appointment) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement updateStatement = null;
        int updatedId = -1;
        try {
            updateStatement = dbConnection.prepareStatement(updateStatementString, Statement.RETURN_GENERATED_KEYS);
            updateStatement.setString(1, appointment.getFirstName());
            updateStatement.setString(2, appointment.getLastName());
            updateStatement.setString(3, String.valueOf(appointment.getTypeAppointment()));
            updateStatement.setDate(4, (Date) appointment.getDateAppointment());
            updateStatement.setTime(5, appointment.getHourAppointment());
            updateStatement.setString(6, appointment.getPhone());
            updateStatement.setInt(7, appointment.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "AppointmentDAO:update" + e.getMessage());
        } finally {
            ConnectionClose.close(updateStatement);
        }
        return updatedId;
    }

    public static ArrayList<Appointment> getSelectedAppointmentList(String dateSelectedString) {
        ArrayList<Appointment> appointments = new ArrayList<>();
        try {
            Connection dbConnection = ConnectionFactory.getConnection();
            String query = "SELECT * FROM appointment where dateAppointment='" + dateSelectedString + "'order by dateAppointment ASC, hourAppointment ASC";
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Appointment appointment = new Appointment(resultSet.getInt("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        AppointmentType.valueOf(resultSet.getString("typeAppointment")),
                        resultSet.getDate("dateAppointment"),
                        resultSet.getTime("hourAppointment"),
                        resultSet.getString("phone")
                );
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }
}
