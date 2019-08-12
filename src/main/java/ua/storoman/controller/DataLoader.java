package ua.storoman.controller;

import org.apache.log4j.Logger;
import ua.storoman.model.service.util.FactoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet( urlPatterns = "/load")
public class DataLoader extends HttpServlet {
    final static Logger logger = Logger.getLogger(DataLoader.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doActions(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doActions(req, resp);

    }


    private void doActions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        FactoryService factoryService =  FactoryService.getInstance();
        factoryService.getFillingDB().getResponse();
    }
}
