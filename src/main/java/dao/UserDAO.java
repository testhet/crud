package dao;


import config.DBconnection;
import model.User;
import java.sql.*;
public class UserDAO {

    public int addUser(User user) throws SQLException {
        try (Connection connection = DBconnection.getConnection()){

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

public User deleteUser(int id) throws SQLException {

        String sql = "DELETE FROM users WHERE id=?";

        Connection connection = DBconnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setInt(1,id);

        stmt.executeUpdate();
    return null;
}



    public   boolean  isEmailExist (String s) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";
        Connection connection = DBconnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, s.trim());
        ResultSet rs = stmt.executeQuery();
        return rs.next();
    }

    public User userLogin(String email , String password){
    String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

        try(Connection conn = DBconnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1 ,email);
            stmt.setString(2,password);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUser_name(rs.getString("user_name"));
                    user.setEmail(rs.getString("email"));
                    user.setRole(rs.getString("role"));


             return user;
            } else {

                System.out.println("Invalid Email Or Password");

            }

        } catch (SQLException e) {
            System.out.println("Error IN Login "+ e.getMessage());
        }
        return null;
    }
}
