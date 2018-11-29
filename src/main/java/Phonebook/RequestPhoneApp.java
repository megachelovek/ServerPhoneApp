package Phonebook;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
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
          String imagepath = line.split(" ")[4];
          try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(url, login, password);
            Statement stmt = con.createStatement();
            try {
              stmt.execute("INSERT INTO phonebook (name, phonenumber, email,imagepath) VALUES ('" + name + "'," + phonenumber + ",'" + email + "','"+imagepath+"');");
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
        case "ADDLIST": {
          String PhoneBookList = line.split(" ")[1];
          Gson gson = new Gson();
          Type PhoneBookListType = new TypeToken<ArrayList<PhoneBook>>(){}.getType();
          ArrayList<PhoneBook> listPhoneBookMain = gson.fromJson(PhoneBookList,PhoneBookListType);
          try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(url, login, password);
            Statement stmt = con.createStatement();
            try {
              for (PhoneBook phoneBook: listPhoneBookMain) {
                if (!containsPhoneBook(phoneBooks,phoneBook)) {
                  stmt.execute("INSERT INTO phonebook (name, phonenumber, email,imagepath) VALUES ('" + phoneBook.getName() + "'," + phoneBook.getPhone() + ",'" + phoneBook.getEmail() + "','" + phoneBook.getImagePath() + "');");
                  }
                }
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
        case "EDIT": {
          String name = line.split(" ")[1];
          String phonenumber = line.split(" ")[2];
          String email = line.split(" ")[3];
          String newname = line.split(" ")[4];
          String newphonenumber = line.split(" ")[5];
          String newemail = line.split(" ")[6];
          String newimagepath = line.split(" ")[7];
          try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(url, login, password);
            Statement stmt = con.createStatement();
            try {
              stmt.execute("UPDATE  phonebook SET name ='"+newname +"', phonenumber = '"+ newphonenumber+"', email = '"+newemail +"', imagepath = '"+ newimagepath +"' WHERE phonebook.name='" + name + "' AND phonebook.phonenumber='" + phonenumber + "' AND phonebook.email='" + email + "';");
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

  public static boolean containsPhoneBook(List<PhoneBook> list,PhoneBook phoneBook){
    for(PhoneBook phoneBookServer : list){
      if (phoneBookServer.getName().equals(phoneBook.getName())  && phoneBookServer.getPhone().equals(phoneBook.getPhone()) && phoneBookServer.getEmail().equals(phoneBook.getEmail()) ){
        return true;
      }
    }
    return false;
  }

}
