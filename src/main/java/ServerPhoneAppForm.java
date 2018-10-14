import javax.swing.*;
import java.awt.event.*;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class ServerPhoneAppForm extends JDialog {
  private JPanel contentPane;
  private JButton buttonOK;
  private JButton buttonCancel;
  private JTable table1;

  static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/PhoneAppServer";
  static final String USER = "postgres";
  static final String PASS = "admin";

  public ServerPhoneAppForm() {


    setContentPane(contentPane);
    setModal(true);
    getRootPane().setDefaultButton(buttonOK);

    buttonOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        onOK();
      }
    });

    buttonCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        onCancel();
      }
    });

    // call onCancel() when cross is clicked
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        onCancel();
      }
    });

    // call onCancel() on ESCAPE
    contentPane.registerKeyboardAction(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        onCancel();
      }
    }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
  }

  private void onOK() {
    // add your code here
    dispose();
  }

  private void onCancel() {
    // add your code here if necessary
    dispose();
  }
  private static Connection startDB() {
    // add your code here if necessary
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      //System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
      e.printStackTrace();
      return;
    }

    //System.out.println("PostgreSQL JDBC Driver successfully connected");
    Connection connection = null;

    try {
      connection = DriverManager
              .getConnection(DB_URL, USER, PASS);

    } catch (SQLException e) {
      //System.out.println("Connection Failed");
      e.printStackTrace();
      return;
    }

    if (connection != null) {
      //System.out.println("You successfully connected to database now");
      return connection;
    } else {
      //System.out.println("Failed to make connection to database");
    }

  }

  public static void main(String[] args) {
    ServerPhoneAppForm dialog = new ServerPhoneAppForm();
    dialog.pack();
    dialog.setVisible(true);
    Connection connection = startDB();
    connection.prepareStatement("SELECT * FROM phonebook");

    System.exit(0);
  }
}
