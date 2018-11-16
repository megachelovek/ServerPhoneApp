import Phonebook.PhoneBook;
import Phonebook.RequestPhoneApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Observer;
import java.util.concurrent.Semaphore;

public class PhoneAppServer implements Runnable, Observer {

  private Socket socket;
  private Semaphore mutexFlight;
  private Semaphore mutexRoute;
  private static Semaphore countF = new Semaphore(1);
  private static Semaphore countR = new Semaphore(1);
  private int newIdFlight;
  private int newIdRoute;
  private static String url;
  private static String login;
  private static String password;
  private List<PhoneBook> phoneBooks = new ArrayList<PhoneBook>();

  public PhoneAppServer(Socket socketnew, String url, String login, String password){
    this.socket = socketnew;
    try {
      Class.forName("org.postgresql.Driver");
      Connection con = DriverManager.getConnection(url, login, password);
      Statement stmt = con.createStatement();
      try {
        ResultSet rs = stmt.executeQuery("SELECT * FROM phonebook");
        while (rs.next()) {
          PhoneBook phoneBook = new PhoneBook(rs.getString(2), rs.getLong(3), rs.getString(4));
          phoneBooks.add(phoneBook);
        }
        rs.close();
        stmt.close();
      } finally {
        con.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void run() {
    try (DataInputStream dis = new DataInputStream(socket.getInputStream());
         DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
         BufferedReader br = new BufferedReader(new java.io.InputStreamReader(socket.getInputStream()));
    ) {
      while (true) {
        String request = dis.readUTF();
        String response = RequestPhoneApp.GetResponseFromString(request,phoneBooks);
        dos.writeUTF(response);
        dos.flush();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void update(Observable o, Object arg) {
    try {
      DataInputStream dis = new DataInputStream(socket.getInputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
