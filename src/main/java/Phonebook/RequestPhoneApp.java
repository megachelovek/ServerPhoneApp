package Phonebook;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import static com.sun.deploy.uitoolkit.ToolkitStore.dispose;

public class RequestPhoneApp {
  private static String url = "jdbc:postgresql://localhost:5432/PhoneAppServer";
  private static String login = "postgres";
  private static String password = "admin";

  public static String GetResponseFromString(String line, List<PhoneBook> phoneBooks) {
    String response ="Error";
    if (line != null && line.length() != 0) {
      switch (line.split(" ")[0]) {
        case "GETALL": {
          Gson gson = new GsonBuilder().setPrettyPrinting().create();
          response = gson.toJson(phoneBooks);
          return response;
        }
        case "ADD": {
          String name = line.split(" ")[1];
          String phonenumber = line.split(" ")[2];
          String email = line.split(" ")[3];
          try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(url, login, password);
            Statement stmt = con.createStatement();
            try {
              stmt.execute("INSERT INTO phonebook (name, phonenumber, email) VALUES ('" + name + "'," + phonenumber + ",'" + email + "');");
              stmt.close();
              dispose();
              response = "request_completed";
            } finally {
              con.close();
            }
          } catch (Exception ex) {
            ex.printStackTrace();
          }
          return response;

        }
        case "DELETE": {
          String name = line.split(" ")[1];
          String phonenumber = line.split(" ")[2];
          String email = line.split(" ")[3];
          try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(url, login, password);
            Statement stmt = con.createStatement();
            try {
              stmt.execute("DELETE FROM phonebook WHERE name='" + name + "'AND phonenumber=" + phonenumber + " AND email='" + email + "';");
              stmt.close();
              dispose();
              response = "request_completed";
            } finally {
              con.close();
            }
          } catch (Exception ex) {
            ex.printStackTrace();
          }
          return response;
        }
      }
    }
    return response;
  }
}
