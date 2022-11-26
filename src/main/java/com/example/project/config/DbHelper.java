package com.example.project.config;

import com.example.project.domain.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbHelper {
    private static final String URL = "jdbc:mysql://chsvrfqhds0c3hsm:rijqd4xml10qnwjx@oliadkuxrl9xdugh.chr7pe7iynqr.eu-west-1.rds.amazonaws.com:3306/gkdx22i5wcqgj9hruseSSL=false";
    private static final String DBUSER = "chsvrfqhds0c3hsm";
    private static final String DBPASSWORD = "rijqd4xml10qnwjx";

    public static final String TABLE_PERSON = "user";
    public static final String KEY_PERSON_NAME = "username";
    public static final String KEY_PERSON_EMAIL = "email";
    public static final String KEY_PERSON_PASSWD = "password";

    public void addUser(User userr) {
        String query = "INSERT INTO " + TABLE_PERSON + "(" + KEY_PERSON_NAME + ", " + KEY_PERSON_EMAIL + ", " + KEY_PERSON_PASSWD
                + ") VALUES ('" + userr.getUsername() + "','"
                + userr.getEmail() + "','"
                + userr.getPassword() + "')";
        try (Connection con = DriverManager.getConnection(URL, DBUSER, DBPASSWORD)) {
            Class.forName("com.mysql.jdbc.Driver");

            Statement stmt2 = con.createStatement();
            stmt2.execute(query);

            String sqlMaxId = "SELECT MAX(id) FROM "+TABLE_PERSON;
            Long maxId = null;
            ResultSet rs = stmt2.executeQuery(sqlMaxId);
            if (rs.first()) {
                maxId = rs.getLong(1);
            }
            userr.setId(maxId);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
