package com.example.task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/userss";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void createUsersTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users (\n"
                + " iduser INT NOT NULL AUTO_INCREMENT,\n"
                + "	username VARCHAR(255) UNIQUE NOT NULL,\n"
                + "	password VARCHAR(255) NOT NULL,\n"
                + " PRIMARY KEY (iduser)\n"
                + ");";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public static void insertUser(Users user) throws SQLException {
        String sql = "INSERT INTO users(username, password) VALUES(?,?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.executeUpdate();

            System.out.println("A new user has been inserted!");

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Username already exists. Please choose a different username.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static boolean validateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return true;  // User exists
            } else {
                return false;  // User does not exist
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public static void createTasksTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS tasks (\n"
                + " idtask INT NOT NULL AUTO_INCREMENT,\n"
                + " description VARCHAR(255) NOT NULL,\n"
                + " username VARCHAR(255) NOT NULL,\n"
                + " PRIMARY KEY (idtask),\n"
                + " FOREIGN KEY (username) REFERENCES users(username)\n"
                + ");";
        try (Connection conn = getConnection();
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tasks table has been created!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
    public static void insertTask(Task task, String username) throws SQLException {
        String sql = "INSERT INTO tasks(description, username) VALUES(?,?)";
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, task.getTaskDescription());
            pstmt.setString(2, username);
            pstmt.executeUpdate();

            System.out.println("A new task has been inserted!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void deleteTask(Task task) throws SQLException {
        String sql = "DELETE FROM tasks WHERE description = ?";
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1,task.getTaskDescription());
            pstmt.executeUpdate();

            System.out.println("A task has been deleted!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static List<Task> getTasks(String username) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT description FROM tasks WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1,username);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                tasks.add(new Task(rs.getString("Description")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tasks;
    }

}