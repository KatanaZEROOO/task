package com.example.task;

import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetViewSelectionModel;

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

    public static void createProjectsTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS projects (\n"
                + " idproject INT NOT NULL AUTO_INCREMENT, \n"
                + " projectName VARCHAR(255) NOT NULL, \n"
                + " description VARCHAR(255) NOT NULL, \n"
                + " startDate DATE, \n"
                + " endDate DATE, \n"
                + " assignedTo VARCHAR(255), \n"
                + " createdBy VARCHAR(255) NOT NULL, \n"
                + " status VARCHAR(255), \n"
                + " priority VARCHAR(255), \n"
                + " PRIMARY KEY (idproject), \n"
                + " FOREIGN KEY (createdBy) REFERENCES users(username) \n"
                + ");";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Project table has been created!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
    public static void createProjectUsersTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS ProjectUsers (\n"
                + " id INT NOT NULL AUTO_INCREMENT, \n"
                + " projectId INT, \n"
                + " username VARCHAR(255), \n"
                + " PRIMARY KEY (id), \n"
                + " FOREIGN KEY (projectId) REFERENCES projects(idproject), \n"
                + " FOREIGN KEY (username) REFERENCES users(username) \n"
                + ");";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("ProjectUsers table has been created!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public static void deleteProject(Projects projects) throws SQLException {
        String sql = "DELETE FROM projects where idproject = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, projects.getId());
            pstmt.executeUpdate();

            System.out.println("A project has been deleted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<Projects> getProjects() throws SQLException {
        String sql = "SELECT * FROM projects";
        List<Projects> projectsList = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                int projectId = rs.getInt("idproject");
                String projectName = rs.getString("projectName");
                String projectDescription = rs.getString("description");
                String startDate = rs.getString("startDate");
                String endDate = rs.getString("endDate");
                List<String> assignedTo = getAssignedUsers(projectId);
                String createdBy = rs.getString("createdBy");
                String status = rs.getString("status");
                String priority = rs.getString("priority");

                projectsList.add(new Projects(projectId, projectName, projectDescription, startDate, endDate, assignedTo, createdBy, status, priority));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return projectsList;
    }

    public static List<String> getAssignedUsers(int projectId) throws SQLException {
        String sql = "SELECT username FROM ProjectUsers WHERE projectId = ?";
        List<String> assignedUsers = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                assignedUsers.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return assignedUsers;
    }



    public static void updateProjects(Projects projects) throws SQLException {
        String sql = "UPDATE projects SET description = ? where idproject = ?";
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, projects.getProjectDescription());
            pstmt.setInt(2, projects.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int insertProject(Projects projects, List<String> assignedUsers) throws SQLException {
        String sql = "INSERT INTO projects (projectName, description, startDate, endDate, createdBy, status, priority) VALUES(?,?,?,?,?,?,?)";
        ResultSet rs = null;
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, projects.getProjectName());
            pstmt.setString(2, projects.getProjectDescription());
            pstmt.setString(3, projects.getStartDate());
            pstmt.setString(4, projects.getEndDate());
            pstmt.setString(5, projects.getCreatedBy());
            pstmt.setString(6, projects.getStatus());
            pstmt.setString(7, projects.getPriority());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedKey = rs.getInt(1);
                System.out.println("A new project has been inserted with ID " + generatedKey + "!!");
                for (String user : assignedUsers) {
                    insertProjectUser(generatedKey, user);
                }
                return generatedKey;
            } else {
                throw new SQLException("Creating project failed, no ID obtained");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (rs != null) rs.close();
        }
    }

    public static void insertProjectUser(int projectId, String username) throws SQLException {
        String sql = "INSERT INTO ProjectUsers (projectId, username) VALUES(?,?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }


    public static int insertTask(Task task, String username) throws SQLException {
        String sql = "INSERT INTO tasks(description, username) VALUES(?,?)";
        ResultSet rs = null;
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, task.getTaskDescription());
            pstmt.setString(2, username);
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedKey = rs.getInt(1);
                System.out.println("A new task has been inserted with ID " + generatedKey + "!");
                return generatedKey;
            } else {
                throw new SQLException("Creating task failed, no ID obtained.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (rs != null) rs.close();
        }
    }

    public static void deleteTask(Task task) throws SQLException {
        String sql = "DELETE FROM tasks WHERE idtask = ?";
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            System.out.println("Deleting task with id:" + task.getId());
            pstmt.setInt(1, task.getId());
            pstmt.executeUpdate();

            System.out.println("A task has been deleted!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<Task> getTasks(String username) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT idtask, description FROM tasks WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                tasks.add(new Task(rs.getString("Description"), rs.getInt("idtask")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tasks;
    }

    public static void updateTask(Task task) throws SQLException {
        String sql = "UPDATE tasks SET description = ? where idtask = ?";
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, task.getTaskDescription());
            pstmt.setInt(2, task.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<String> getAllUsers() throws SQLException {
        List<String> users = new ArrayList<>();
        String sql = "Select username FROM users";
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                users.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }
}