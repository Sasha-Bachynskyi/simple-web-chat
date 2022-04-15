package chat.dao;

import chat.exception.DataProcessingException;
import chat.lib.Dao;
import chat.model.User;
import chat.util.ConnectionUtil;

import java.sql.*;
import java.util.Optional;

@Dao
public class UserDaoImpl implements UserDao {
    @Override
    public User create(User user) {
        String query = "INSERT INTO users (name) VALUES (?);";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getObject(1, Long.class));
            }
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't create user with name: " + user.getName(), e);
        }
    }

    @Override
    public Optional<User> findByName(String name) {
        String query = "SELECT * FROM users WHERE name = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = parseUserFromResultSet(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't ger user by name: " + name, e);
        }
    }

    @Override
    public Optional<User> get(Long id) {
        String query = "SELECT * FROM users WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = parseUserFromResultSet(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get user by id: " + id, e);
        }
    }

    private User parseUserFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        User user = new User();
        user.setId(id);
        user.setName(name);
        return user;
    }
}
