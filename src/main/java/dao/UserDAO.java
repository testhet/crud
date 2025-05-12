package dao;


import config.DBconnection;
import model.User;
import java.sql.*;


public class UserDAO {

    public int addUser(User user) throws SQLException {
        try (Connection connection = DBconnection.getConnection()) {
            String sql = "INSERT INTO users ( email,user_name, password, role) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getUser_name());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Failed to retrieve user ID.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLException("Email already exists.", e);
        }
    }

    public User userLogin(String email, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
       try( Connection connection = DBconnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)){
        stmt.setString(1, email);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUser_name(rs.getString("user_name"));
            user.setEmail(rs.getString("email"));
            user.setRole(rs.getString("role"));

            return user;

       }
        return null;
        }
    }

    public void deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id=?";
        try(Connection connection = DBconnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)){
        stmt.setInt(1, id);
        stmt.executeUpdate();
        }
    }

    public boolean isDoctor(String s) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ? && role =\"doctor\";";
        try(Connection connection = DBconnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)){
        stmt.setString(1, s.trim());
        ResultSet rs = stmt.executeQuery();
        return rs.next();
        }
    }

    public boolean isEmailExist(String s) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?;";
        try(Connection connection = DBconnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, s.trim());
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public void updatePassword(String email, String password) throws SQLException {
        String sql = "UPDATE users SET password = ? WHERE email = ?";
        try (Connection connection = DBconnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)){
        stmt.setString(1, password);
        stmt.setString(2, email);
        stmt.executeUpdate();
        }
    }

    public boolean dobMatch(String email, String dob) throws  SQLException {
        String sql = """
                    SELECT
                        u.email,
                        p.date_of_birth
                    FROM users u
                    JOIN patients p ON p.patient_id = u.id
                    WHERE  u.email = ? && p.date_of_birth = ?;
                """;
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email.trim());
            stmt.setString(2, dob.trim());
            ResultSet rs = stmt.executeQuery();
            stmt.close();
            connection.close();
            return rs.next();
        }
    }
}

