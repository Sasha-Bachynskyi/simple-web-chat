package chat.dao;

import chat.exception.DataProcessingException;
import chat.lib.Dao;
import chat.model.Message;
import chat.model.User;
import chat.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Dao
public class MessageDaoImpl implements MessageDao {
    @Override
    public Message create(Message message) {
        String query = "INSERT INTO messages (content, user_id) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, message.getContent());
            statement.setLong(2, message.getUser().getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                message.setId(resultSet.getObject(1, Long.class));
            }
            return message;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't create message: " + message, e);
        }
    }

    @Override
    public List<Message> getLastTenMessages() {
        String query = "SELECT u.name as user_name, m.id as message_id,"
                + " m.content as content, u.id as user_id FROM messages m JOIN"
                + " users u ON m.user_id = u.id ORDER BY m.id DESC LIMIT 10;";
        List<Message> messages = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                messages.add(parseMessageFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get last 10 messages", e);
        }
        return messages;
    }

    private Message parseMessageFromResultSet(ResultSet resultSet) throws SQLException {
        Long userId = resultSet.getObject("user_id", Long.class);
        String userName = resultSet.getString("user_name");
        Long messageId = resultSet.getObject("message_id", Long.class);
        User user = new User();
        user.setId(userId);
        user.setName(userName);
        Message message = new Message();
        message.setId(messageId);
        String content = resultSet.getString("content");
        message.setContent(content);
        message.setUser(user);
        return message;
    }
}
