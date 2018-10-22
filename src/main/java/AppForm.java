

import Phonebook.Client;
import Phonebook.PhoneBook;
import Phonebook.Server;



import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.sun.deploy.uitoolkit.ToolkitStore.dispose;

public class AppForm {

  //Массив содержащий заголоки таблицы
  Object[] headers = {"Имя", "Телефон", "Email"};
  TableModel model;
  Object[][] data;
  List<PhoneBook> phoneBooks = new ArrayList<PhoneBook>();

  //Объект таблицы
  JFrame jfrm;
  JTable jTabPhoneBooks;
  private JButton addButton;
  private JButton deleteButton;
  private JTextField namefield;
  private JTextField emailfield;
  private JTextField phonefield;
  private JTextField ip_android;
  private JButton ServerOn;
  private JLabel ip,msg;
  String name, phone, email;
  String url = "jdbc:postgresql://localhost:5432/PhoneAppServer";
  String login = "postgres";
  String password = "admin";
  String File = "";
  String Exc = "";
  String[][] rowData;
  int port =8080;
  Client client;
  Server server;

  AppForm() throws SQLException, ClassNotFoundException, UnknownHostException {

    FillTable();
    //Отображаем контейнер
    jfrm.add(addButton);
    jfrm.add(deleteButton);
    jfrm.add(ServerOn);
    jfrm.add(namefield);
    jfrm.add(emailfield);
    jfrm.add(phonefield);
    jfrm.add(ip_android);
    jfrm.add(ip);
    //jfrm.add(msg);
    jfrm.setVisible(true);
    ip_android.setText(String.valueOf(InetAddress.getLocalHost()));

    deleteButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          Class.forName("org.postgresql.Driver");
          Connection con = DriverManager.getConnection(url, login, password);
          Statement stmt = con.createStatement();
          try {
            int row = jTabPhoneBooks.getSelectedRow();
            stmt.execute("DELETE FROM phonebook WHERE name='" + phoneBooks.get(row).getName() + "'AND phonenumber=" + phoneBooks.get(row).getPhone() + " AND email='" + phoneBooks.get(row).getEmail() + "';");
            stmt.close();
            FillTable();
            new AppForm();
            dispose();
          } finally {
            con.close();
          }
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    });

    addButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        name = namefield.getText();
        phone = phonefield.getText();
        email = emailfield.getText();
        try {
          Class.forName("org.postgresql.Driver");
          Connection con = DriverManager.getConnection(url, login, password);
          Statement stmt = con.createStatement();
          try {
            stmt.execute("INSERT INTO phonebook (name, phonenumber, email) VALUES ('" + name + "'," + phone + ",'" + email + "');");
            stmt.close();
            FillTable();
            new AppForm();
            dispose();
          } finally {
            con.close();
          }
        } catch (Exception ex) {
          ex.printStackTrace();
        }

      }
    });

    ServerOn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        //принимаем
        client = new Client(ip.getText().toString(),port,Exc);
        //отправляем
        server = new Server( File);

       /* //принимаем
        try (Socket socket = new Socket("localhost", 8080)) {
          try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            try {
              Uri uri;
              uri = (Uri)in.readObject(); //добавить рест
            } catch (ClassNotFoundException exc) {
              exc.printStackTrace();
            }
          } catch (IOException exc) {
            exc.printStackTrace();
          }
        } catch (UnknownHostException exc) {
          exc.printStackTrace();
        } catch (IOException exc) {
          exc.printStackTrace();
        }

        //отправляем
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
          try (Socket socket = serverSocket.accept();
               ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
            JSONArray jsonArray = new JSONArray();
            for (int i=0;i< phoneBooks.size();i++){
              jsonArray.add(phoneBooks.get(i).toJSONString());
            }
            out.writeObject(jsonArray);
          }
          catch (IOException exc) {
            exc.printStackTrace();
          }
        }
        catch (IOException exc) {
          exc.printStackTrace();
        }*/

      }
    });
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        try {
          new AppForm();
        } catch (SQLException e) {
          e.printStackTrace();
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        } catch (UnknownHostException e) {
          e.printStackTrace();
        }
      }
    });
  }

  public void FillTable() {
    try {
      Class.forName("org.postgresql.Driver");
      Connection con = DriverManager.getConnection(url, login, password);
      Statement stmt = con.createStatement();
      try {
        ResultSet rs = stmt.executeQuery("SELECT * FROM phonebook");
        int rowCount = 1;
        while (rs.next()) {
          PhoneBook phoneBook = new PhoneBook(rs.getString(2), rs.getLong(3), rs.getString(4));
          phoneBooks.add(phoneBook);
        }
        rs.close();
        stmt.close();
        jfrm.dispose();
      } finally {
        con.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    rowData = new String[phoneBooks.size()][3];
    for (int i = 0; i < phoneBooks.size(); i++) {
      rowData[i][0] = phoneBooks.get(i).getName();
      rowData[i][1] = String.valueOf(phoneBooks.get(i).getPhone());
      rowData[i][2] = phoneBooks.get(i).getEmail();

      //Создаем новый контейнер JFrame
      jfrm = new JFrame("AppPhoneForm");
      //Устанавливаем диспетчер компоновки
      jfrm.getContentPane().setLayout(new GridLayout(3, 5));
      //Устанавливаем размер окна
      jfrm.setSize(500, 300);
      //Устанавливаем завершение программы при закрытии окна
      jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //Создаем новую таблицу на основе двумерного массива данных и заголовков
      //jTabPhoneBooks.setModel(model);
      jTabPhoneBooks = new JTable(rowData, headers);
      //jTabPhoneBooks.editingStopped();
      jTabPhoneBooks.setSize(500, 200);
      //Создаем панель прокрутки и включаем в ее состав нашу таблицу
      JScrollPane jscrlp = new JScrollPane(jTabPhoneBooks);
      //Устаналиваем размеры прокручиваемой области
      jTabPhoneBooks.setPreferredScrollableViewportSize(new Dimension(250, 100));
      //Добавляем в контейнер нашу панель прокрути и таблицу вместе с ней
      namefield.setSize(100, 20);
      emailfield.setSize(100, 20);
      phonefield.setSize(100, 20);
      jfrm.getContentPane().add(jscrlp);
      jscrlp.setSize(500, 400);
    }
  }


}