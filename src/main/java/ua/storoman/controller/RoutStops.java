package ua.storoman.controller;

import org.apache.log4j.Logger;
import ua.storoman.model.entity.BusStop;
import ua.storoman.model.entity.Rout;
import ua.storoman.model.service.RoutService;
import ua.storoman.model.service.RoutStopService;
import ua.storoman.model.service.util.FactoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet( urlPatterns = "/routs/*")
public class RoutStops extends HttpServlet {
    final static Logger logger = Logger.getLogger(Routs.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doActions(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doActions(req, resp);
    }


    private void doActions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        try {
            int id = Integer.parseInt(req.getPathInfo().replace("/",""));
            RoutStopService routStopService = FactoryService.getInstance().getRoutStopService();
            List<BusStop> busStops = routStopService.getBusStopsByRoutId(id);

            resp.setContentType("text/html; charset=UTF-8");
            PrintWriter out = resp.getWriter();

            out.println("<html>");
            out.println("<head>");
            out.println("<title> all routs</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div style='width: 1200px; margin-left: auto; margin-right: auto;'>");
            out.println("<table cellpadding='10'>");
            out.println("<tr>");
            out.println("<th>Id</th>");
            out.println("<th>Name</th>");
            out.println("<th>Street Name</th>");
            out.println("<th>Covered</th>");
            out.println("<th></th>");
            out.println("</tr>");

            for (BusStop busStop:busStops) {
                out.println("<tr>");
                out.println("<td>"+ busStop.getId() + "</td>");
                out.println("<td>"+ busStop.getName() + "</td>");
                out.println("<td>"+ busStop.getStreetName() + "</td>");
                out.println("<td>"+ busStop.isCovered() + "</td>");
                out.println("</tr>");
            }


            out.println("</body>");

        }catch (Exception e) {
            logger.info(e.getMessage());

            resp.setContentType("text/html; charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html lang='en'>");
            out.println("<head>");
            out.println(" <meta charset='UTF-8'>");
            out.println(" <title>Error</title>");
            out.println("</head>");
            out.println(" <body>");
            out.println("<H2>Что-то пошло не так.</H2>");
            out.println("</body>");
            out.println("</html>");
        }
    }

}

