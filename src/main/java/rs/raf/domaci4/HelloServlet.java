package rs.raf.domaci4;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/")
public class HelloServlet extends HttpServlet {
    private HashMap<String, ArrayList<String>> menu = new HashMap<>();
    private ArrayList<String> ponendeljak = new ArrayList<>();
    private ArrayList<String> utorak = new ArrayList<>();
    private ArrayList<String> sreda = new ArrayList<>();
    private ArrayList<String> cetvrtak = new ArrayList<>();
    private ArrayList<String> petak = new ArrayList<>();
    public static final String SUBMITTED_USERS = "submittedUsers";
    public static final String ORDER = "order";


    public void init() {
        ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> order = new ConcurrentHashMap<>();
        order.put("ponedeljak",  new ConcurrentHashMap<>());
        order.put("utorak",      new ConcurrentHashMap<>());
        order.put("sreda",       new ConcurrentHashMap<>());
        order.put("cetvrtak",    new ConcurrentHashMap<>());
        order.put("petak",       new ConcurrentHashMap<>());

        getServletContext().setAttribute(SUBMITTED_USERS, new CopyOnWriteArrayList<>());
        getServletContext().setAttribute(ORDER, order);

        menu.put("ponedeljak",  this.ponendeljak);
        menu.put("utorak",      this.utorak);
        menu.put("sreda",       this.sreda);
        menu.put("cetvrtak",    this.cetvrtak);
        menu.put("petak",       this.petak);
        initMenu("ponedeljak");
        initMenu("utorak");
        initMenu("sreda");
        initMenu("cetvrtak");
        initMenu("petak");
        System.out.println("MENI: " + menu);
    }

    private void initMenu(String day) {
        File dailyMenu = new File("C:\\Users\\Luka\\IdeaProjects\\domaci4\\src\\main\\resources\\dani\\" + day + ".txt");
        try {
            Scanner sc = new Scanner(dailyMenu);
            while(sc.hasNextLine()){
                menu.get(day).add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        CopyOnWriteArrayList<String> submittedUsers = (CopyOnWriteArrayList<String>) getServletContext().getAttribute(SUBMITTED_USERS);
        if(!submittedUsers.contains(request.getSession().getId())){
            out.println("<h1>Odaberite vas rucal:</h1>");
            out.println("<form action=\"submit\" method=\"post\">");

            out.println("<label for=\"ponedeljak\">Ponedeljak:</label><br/>");
            out.println("<select id=\"ponedeljak\" name=\"ponedeljak\">");
            for (String food : this.menu.get("ponedeljak")) {
                out.println("<option value=\"" + food + "\">" + food + "</option>");
            }
            out.println("</select><br/>");


            out.println("<label for=\"utorak\">Utorak:</label><br/>");
            out.println("<select id=\"utorak\" name=\"utorak\">");
            for (String food : this.menu.get("utorak")) {
                out.println("<option value=\"" + food + "\">" + food + "</option>");
            }
            out.println("</select><br/>");

            out.println("<label for=\"sreda\">Sreda:</label><br/>");
            out.println("<select id=\"sreda\" name=\"sreda\">");
            for (String food : this.menu.get("sreda")) {
                out.println("<option value=\"" + food + "\">" + food + "</option>");
            }
            out.println("</select><br/>");

            out.println("<label for=\"cetvrtak\">Cetvrtak:</label><br/>");
            out.println("<select id=\"cetvrtak\" name=\"cetvrtak\">");
            for (String food : this.menu.get("cetvrtak")) {
                out.println("<option value=\"" + food + "\">" +food + "</option>");
            }
            out.println("</select><br/>");


            out.println("<label for=\"petak\">Petak:</label><br/>");
            out.println("<select id=\"petak\" name=\"petak\">");
            for (String food : this.menu.get("petak")) {
                out.println("<option value=\"" + food + "\">" + food + "</option>");
            }
            out.println("</select><br/>");

            out.println("<br/><button type=\"submit\">Potvrdite unos</button>");
            out.println("</form>");
        } else {
            out.println("<h1>Vec ste potvrdili vasu porudzbinu za ovu nedelju</h1>");
            out.println("<ul>");
            out.println("<li><strong>Ponedeljak</strong> - " + request.getSession().getAttribute("ponedeljak") + "</li>");
            out.println("<li><strong>Utorak</strong> - " + request.getSession().getAttribute("utorak") + "</li>");
            out.println("<li><strong>Sreda</strong> - " + request.getSession().getAttribute("sreda") + "</li>");
            out.println("<li><strong>Cetvrtak</strong> - " + request.getSession().getAttribute("cetvrtak") + "</li>");
            out.println("<li><strong>Petak</strong> - " + request.getSession().getAttribute("petak") + "</li>");
            out.println("</ul>");
        }
        out.println("</body></html>");


    }

    public void destroy() {
    }
}