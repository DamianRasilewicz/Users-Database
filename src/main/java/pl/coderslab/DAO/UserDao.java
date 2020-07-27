package pl.coderslab.DAO;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.DbUtil;
import pl.coderslab.entity.User;

import java.sql.*;
import java.util.Arrays;

public class UserDao {

    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";



    public User create(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
            //Pobieramy wstawiony do bazy identyfikator, a następnie ustawiamy id obiektu user.
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    private static final String READ_QUERY = "SELECT * FROM workshop2.users WHERE id = ?";

    public User read(int userId) throws SQLException {

        User user = new User();
        Connection conn = DbUtil.getConnection();
        PreparedStatement statement =
                conn.prepareStatement(READ_QUERY);
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();
        int check = 0;
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String email = resultSet.getString("email");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            user.setAllParameters(email, username, password);
            check++;
            System.out.println("User:" );
            System.out.println("id = " + id);
            System.out.println("email = " + email);
            System.out.println("username = " + username);
            System.out.println("password = " + password);
            System.out.println();
        }
        if (check == 0){
            user = null;
        }

        return user;
    }

    private static final String UPDATE_QUERY = "UPDATE workshop2.users SET email = ? , username = ? , password = ? WHERE id = ?;";

    public void update(User user) throws SQLException{

        Connection conn = DbUtil.getConnection();
        PreparedStatement statement =
                conn.prepareStatement(UPDATE_QUERY);
        statement.setString(1, user.getEmail());
        statement.setString(2, user.getUserName());
        statement.setString(3,hashPassword(user.getPassword()));
        statement.setInt(4, user.getId());
        statement.executeUpdate();
    }

    private static final String DELETE_QUERY = "DELETE FROM users WHERE id = ?;";

    public void delete(int userId) throws SQLException{

        Connection conn = DbUtil.getConnection();
        PreparedStatement statement =
                conn.prepareStatement(DELETE_QUERY);
        statement.setInt(1, userId);
        statement.executeUpdate();
    }

    private static final String FINDALL_QUERY = "SELECT * FROM workshop2.users";

    public User[] findALL () throws SQLException {
        User[] userList = new User[0];
        Connection conn = DbUtil.getConnection();
        PreparedStatement statement =
                conn.prepareStatement(FINDALL_QUERY);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            User user = new User();
            int id = resultSet.getInt("id");
            String email = resultSet.getString("email");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            user.setAllParameters(id, username, email, password);
            userList = addToArray(user, userList);
        }
//        System.out.println(Arrays.toString(userList));
        return userList;
    }


    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    private User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1); // Tworzymy kopię tablicy powiększoną o 1.
        tmpUsers[users.length] = u; // Dodajemy obiekt na ostatniej pozycji.
        return tmpUsers; // Zwracamy nową tablicę.
    }
}
