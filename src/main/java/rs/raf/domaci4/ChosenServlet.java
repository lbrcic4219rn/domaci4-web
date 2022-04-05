package rs.raf.domaci4;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static rs.raf.domaci4.HelloServlet.ORDER;
import static rs.raf.domaci4.HelloServlet.SUBMITTED_USERS;

@WebServlet(name = "ChosenServlet", value = "/odabrana-jela")
public class ChosenServlet extends HttpServlet {

    private String password = "";

    public void init() {
        try {
            Scanner sc = new Scanner(new File("C:\\Users\\Luka\\IdeaProjects\\domaci4\\src\\main\\resources\\config\\password.txt"));
            password = sc.nextLine();
            System.out.println(password);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> menu = (ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>>) getServletContext().getAttribute(ORDER);

        out.println("<html><body>");
        if (request.getParameter("lozinka") != null && request.getParameter("lozinka").equals(password)) {
            out.println("<h1>Odabrana jela</h1>");

            out.println("<form method=\"post\" action=\"/odabrana-jela?lozinka=" + password + "\">");
            out.println("<button type=\"submit\">Ocisti</button>");
            out.println("</form>");

            out.println("<h1>PONEDELJAK</h1>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Jelo</th>");
            out.println("<th>Kolicina</th>");
            out.println("</tr>");
            for (Map.Entry entry : menu.get("ponedeljak").entrySet()) {
                out.println("<tr>");
                out.println("<td>" + entry.getKey() + "</td>");
                out.println("<td>" + entry.getValue() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");

            out.println("<h1>UTORAK</h1>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Jelo</th>");
            out.println("<th>Kolicina</th>");
            out.println("</tr>");
            for (Map.Entry entry : menu.get("utorak").entrySet()) {
                out.println("<tr>");
                out.println("<td>" + entry.getKey() + "</td>");
                out.println("<td>" + entry.getValue() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");

            out.println("<h1>SREDA</h1>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Jelo</th>");
            out.println("<th>Kolicina</th>");
            out.println("</tr>");
            for (Map.Entry entry : menu.get("sreda").entrySet()) {
                out.println("<tr>");
                out.println("<td>" + entry.getKey() + "</td>");
                out.println("<td>" + entry.getValue() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");

            out.println("<h1>CETVRTAK</h1>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Jelo</th>");
            out.println("<th>Kolicina</th>");
            out.println("</tr>");
            for (Map.Entry entry : menu.get("cetvrtak").entrySet()) {
                out.println("<tr>");
                out.println("<td>" + entry.getKey() + "</td>");
                out.println("<td>" + entry.getValue() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");

            out.println("<h1>PETAK</h1>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Jelo</th>");
            out.println("<th>Kolicina</th>");
            out.println("</tr>");
            for (Map.Entry entry : menu.get("petak").entrySet()) {
                out.println("<tr>");
                out.println("<td>" + entry.getKey() + "</td>");
                out.println("<td>" + entry.getValue() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        } else {
            response.setStatus(403);
            out.println("<h1>UNAUTHORIZED</h1>");
        }
        out.println("</html></body>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> menu = (ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>>) getServletContext().getAttribute(ORDER);
        CopyOnWriteArrayList<String> submittedUsers = (CopyOnWriteArrayList<String>) getServletContext().getAttribute(SUBMITTED_USERS);

        menu.get("ponedeljak").clear();
        menu.get("utorak").clear();
        menu.get("sreda").clear();
        menu.get("cetvrtak").clear();
        menu.get("petak").clear();
        submittedUsers.clear();

    }
}
