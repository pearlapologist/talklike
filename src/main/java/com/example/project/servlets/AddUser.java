package com.example.project.servlets;

import com.example.project.config.DbHelper;
import com.example.project.domain.User;
import com.example.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.PrintWriter;
import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddUser extends HttpServlet {
    @Autowired
    private UserService userService;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    } @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        try {
            JsonReader jsonReader = Json.createReader(request.getReader());

            JsonObject jsonObject = jsonReader.readObject();


            String username = jsonObject.getString("username");
            String email = jsonObject.getString("email");
            String passwd = jsonObject.getString("passwd");

            User r = new User();
            r.setUsername(username);
            r.setPassword(passwd);
            r.setEmail(email);

            DbHelper db = new DbHelper();
            db.addUser(r);
            out.print(r.getId());
        } catch (Exception e) {
            out.print("Error: " + e.getMessage());
        }
    } @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
