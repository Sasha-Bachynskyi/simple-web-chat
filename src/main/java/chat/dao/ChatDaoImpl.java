package chat.dao;

import chat.exception.DataProcessingException;
import chat.lib.Dao;
import chat.model.Chat;
import chat.model.User;
import chat.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ChatDaoImpl implements ChatDao {
    @Override
    public List<Chat> getAll() {
        String query = "SELECT * FROM chats";
        List<Chat> chats = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                chats.add(parseChatFromResultSet(resultSet));
            }
            return chats;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get a list of chats from DB", e);
        }
    }

    @Override
    public Chat update(Chat chat) {
        String query = "UPDATE chats c SET type = ? WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, chat.getType());
            statement.setLong(2, chat.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't update chat in DB. Chat: "
                    + chat.getType(), e);
        }
        deleteAllRelationsBetweenChatAndUsers(chat);
        insertUsers(chat);
        return chat;
    }

    @Override
    public Optional<Chat> get(Long id) {
        String query = "SELECT * FROM chats where id = ?;";
        Chat chat = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                chat = parseChatFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get chat from DB. Chat ID: " + id, e);
        }
        if (chat != null) {
            chat.setUsers(getUsersForChat(id));
        }
        return Optional.ofNullable(chat);
    }

    @Override
    public void deleteUsersFromChat(Chat chat) {
        deleteAllRelationsBetweenChatAndUsers(chat);
    }

    @Override
    public boolean isUserPresent(Chat chat, User user) {
        String query = "SELECT * FROM users_chats WHERE user_id = ? AND chat_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, user.getId());
            statement.setLong(2, chat.getId());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new DataProcessingException("This user is not in chat", e);
        }
    }

    private List<User> getUsersForChat(Long chatId) {
        String query = "SELECT u.id as user_id, u.name as user_name FROM users"
                + " u JOIN users_chats uc ON u.id = uc.user_id where uc.chat_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, chatId);
            ResultSet resultSet = statement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(parseUsersFromResultSet(resultSet));
            }
            return users;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get any users by chat_id: " + chatId, e);
        }
    }

    private User parseUsersFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getObject("user_id", Long.class));
        user.setName(resultSet.getString("user_name"));
        return user;
    }

    private void insertUsers(Chat chat) {
        String query = "INSERT INTO users_chats (user_id, chat_id) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(2, chat.getId());
            for (User user : chat.getUsers()) {
                statement.setLong(1, user.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't insert users to chat. Chat: "
                    + chat.getType(), e);
        }
    }

    private void deleteAllRelationsBetweenChatAndUsers(Chat chat) {
        String query = "DELETE FROM users_chats WHERE chat_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, chat.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't delete users from chat. Chat: "
                    + chat.getType(), e);
        }
    }

    private Chat parseChatFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String type = resultSet.getString("type");
        Chat chat = new Chat();
        chat.setId(id);
        chat.setType(type);
        return chat;
    }
}
