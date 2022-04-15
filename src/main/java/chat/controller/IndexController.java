package chat.controller;

import chat.lib.Injector;
import chat.model.Chat;
import chat.service.ChatService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class IndexController extends HttpServlet {
    public static final Injector injector = Injector.getInstance("chat");
    private final ChatService chatService = (ChatService) injector.getInstance(ChatService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        String userName = (String) session.getAttribute("user_name");
        req.setAttribute("user_name", userName);
        List<Chat> chats = chatService.getAll();
        req.setAttribute("chats", chats);
        req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
    }
}
