package chat.controller.chats;

import chat.lib.Injector;
import chat.model.Chat;
import chat.model.Message;
import chat.model.User;
import chat.service.ChatService;
import chat.service.MessageService;
import chat.service.UserService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MainController extends HttpServlet {
    public static final Injector injector = Injector.getInstance("chat");
    public static final Long MAIN_CHAT_ID = 1L;
    private final UserService userService = (UserService) injector.getInstance(UserService.class);
    private final ChatService chatService = (ChatService) injector.getInstance(ChatService.class);
    private final MessageService messageService =
            (MessageService) injector.getInstance(MessageService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Chat chat = chatService.get(MAIN_CHAT_ID);
        HttpSession session = req.getSession();
        Long userId = (Long) session.getAttribute("user_id");
        User user = userService.get(userId);
        if (chatService.isUserPresent(chat, user)) {
            req.setAttribute("users_in_chat", chat.getUsers());
        } else {
            Chat updatedChat = chatService.addUserToChat(user, chat);
            req.setAttribute("users_in_chat", updatedChat.getUsers());
        }
        List<Message> messages = messageService.getLastTenMessages();
        req.setAttribute("messages", messages);
        req.getRequestDispatcher("/WEB-INF/views/chats/main.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String content = req.getParameter("content");
        Message message = new Message();
        message.setContent(content);
        HttpSession session = req.getSession();
        message.setUser(userService.get((Long) session.getAttribute("user_id")));
        messageService.create(message);
        resp.sendRedirect(req.getContextPath() + "/chats/main");
    }

    @Override
    public void destroy() {
        chatService.deleteUsersFromChat(MAIN_CHAT_ID);
    }
}
