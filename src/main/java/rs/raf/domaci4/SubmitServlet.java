package rs.raf.domaci4;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static rs.raf.domaci4.HelloServlet.*;

@WebServlet(name = "submitServlet", value = "/submit")
public class SubmitServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        CopyOnWriteArrayList<String> submittedUsers = (CopyOnWriteArrayList<String>) getServletContext().getAttribute(SUBMITTED_USERS);
        ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> menu = (ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>>) getServletContext().getAttribute(ORDER);
        submittedUsers.add(request.getSession().getId());

        //dodajemo stvari u meni
        menu.get("ponedeljak")
                .putIfAbsent(request.getParameter("ponedeljak"), 0);
        menu.get("ponedeljak")
                .put(request.getParameter("ponedeljak"), menu.get("ponedeljak").get(request.getParameter("ponedeljak")) + 1);

        menu.get("utorak")
                .putIfAbsent(request.getParameter("utorak"), 0);
        menu.get("utorak")
                .put(request.getParameter("utorak"), menu.get("utorak").get(request.getParameter("utorak")) + 1);

        menu.get("sreda")
                .putIfAbsent(request.getParameter("sreda"), 0);
        menu.get("sreda")
                .put(request.getParameter("sreda"), menu.get("sreda").get(request.getParameter("sreda")) + 1);

        menu.get("cetvrtak")
                .putIfAbsent(request.getParameter("cetvrtak"), 0);
        menu.get("cetvrtak")
                .put(request.getParameter("cetvrtak"), menu.get("cetvrtak").get(request.getParameter("cetvrtak")) + 1);

        menu.get("petak")
                .putIfAbsent(request.getParameter("petak"), 0);
        menu.get("petak")
                .put(request.getParameter("petak"), menu.get("petak").get(request.getParameter("petak")) + 1);

        System.out.println("order: " + menu);

        request.getSession().setAttribute("ponedeljak", request.getParameter("ponedeljak"));
        request.getSession().setAttribute("utorak", request.getParameter("utorak"));
        request.getSession().setAttribute("sreda", request.getParameter("sreda"));
        request.getSession().setAttribute("cetvrtak", request.getParameter("cetvrtak"));
        request.getSession().setAttribute("petak", request.getParameter("petak"));

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Vasa porudzbina je potvrdjena!</h1>");
        out.println("<ul>");
        out.println("<li>Ponedeljak - " + request.getParameter("ponedeljak") + "</li>");
        out.println("<li>Utorak - " + request.getParameter("utorak") + "</li>");
        out.println("<li>Sreda - " + request.getParameter("sreda") + "</li>");
        out.println("<li>Cetvrtak - " + request.getParameter("cetvrtak") + "</li>");
        out.println("<li>Petak - " + request.getParameter("petak") + "</li>");
        out.println("</ul>");
        out.println("</body></html>");
    }
}
