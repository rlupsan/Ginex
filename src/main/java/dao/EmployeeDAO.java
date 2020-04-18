package dao;


import dao.connection.ConnectionClose;
import dao.connection.ConnectionFactory;
import model.Employee;
import model.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeDAO {
    private static final Logger LOGGER = Logger.getLogger(EmployeeDAO.class.getName());
    //Create
    private static final String insertStatementString =
            "INSERT INTO employee (username, password, role) VALUES (?,?,?)";
    //Read
    private final static String findStatementString = "SELECT * FROM employee where username = ?";
    //Update
    private static final String updateStatementString = "UPDATE employee SET username = ?,password =?, role=? " +
            "WHERE id = ?";
    //Delete
    private static final String deleteStatementString = "DELETE FROM employee where id = ?";

    public static Employee findByUsername(String username) {
        Employee toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setString(1, username);
            rs = findStatement.executeQuery();
            if (rs.next()) {
                toReturn = mapEmployee(rs);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "EmployeeDAO:findById " + e.getMessage());
        } finally {
            ConnectionClose.close(rs);
            ConnectionClose.close(findStatement);
        }
        return toReturn;
    }

    private static Employee mapEmployee(ResultSet rs) throws SQLException {
        String username = rs.getString("username");
        String password = rs.getString("password");
        String role = rs.getString("role");
        return new Employee(username, password, Role.valueOf(role));
    }

    public static int insert(Employee employee) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, employee.getUsername());
            insertStatement.setString(2, employee.getPassword());
            insertStatement.setString(3, String.valueOf(employee.getRole()));
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "EmployeeDAO:insert " + e.getMessage());
        } finally {
            ConnectionClose.close(insertStatement);
        }
        return insertedId;
    }

    public static int delete(int idEmployee) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement deleteStatement = null;
        int deletedId = -1;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
            deleteStatement.setInt(1, idEmployee);
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "EmployeeDAO:delete " + e.getMessage());
        } finally {
            ConnectionClose.close(deleteStatement);
        }
        return deletedId;
    }

    public static int update(Employee employee) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement updateStatement = null;
        int updatedId = -1;
        try {
            updateStatement = dbConnection.prepareStatement(updateStatementString, Statement.RETURN_GENERATED_KEYS);
            updateStatement.setString(1, employee.getUsername());
            updateStatement.setString(2, employee.getPassword());
            updateStatement.setString(3, String.valueOf(employee.getRole()));
            updateStatement.setInt(4, employee.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "EmployeeDAO:update" + e.getMessage());
        } finally {
            ConnectionClose.close(updateStatement);
        }
        return updatedId;
    }

    public static ArrayList<Employee> getEmployeeList() {
        ArrayList<Employee> employees = new ArrayList<>();
        try {
            Connection dbConnection = ConnectionFactory.getConnection();
            String query = "SELECT * FROM employee";
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Employee employee = new Employee(resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        Role.valueOf(resultSet.getString("role")));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
}
