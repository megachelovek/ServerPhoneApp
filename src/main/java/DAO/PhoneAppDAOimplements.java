package DAO;

import Phonebook.PhoneBook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhoneAppDAOimplements implements PhoneAppDAO{

  private DAO dao;

  public PhoneAppDAOimplements(DAO dao) {this.dao = dao;}

  @Override
  public List<PhoneBook> getPhoneBooks() {
    List<PhoneBook> phoneBooks = new ArrayList<PhoneBook>();
    String query = "SELECT * FROM phonebook ORDER BY _id";
    try (Connection connection = dao.getConnection()) {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        int _id = resultSet.getInt(_ID);
        String name = resultSet.getString(NAME);
        Long phonenumber = resultSet.getLong(PHONENUMBER);
        String email = resultSet.getString(EMAIL);
        PhoneBook phoneBook = new PhoneBook(name, phonenumber, email);
        phoneBooks.add(phoneBook);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return phoneBooks;
  }

  @Override
  public PhoneBook getPhoneBookNumber(Long number) {
    PhoneBook phoneBook = new PhoneBook("",(long)0 ,"");
    String query = "SELECT * FROM phonebook WHERE phonenumber=? ORDER BY _id";
    try (Connection connection = dao.getConnection()) {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setLong(1, number);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        int _id = resultSet.getInt(_ID);
        String name = resultSet.getString(NAME);
        Long phonenumber = resultSet.getLong(PHONENUMBER);
        String email = resultSet.getString(EMAIL);
        phoneBook = new PhoneBook(name, phonenumber, email);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return phoneBook;
  }


  public PhoneBook getPhoneBookId(int id) {
    PhoneBook phoneBook = new PhoneBook("", (long) 0,"");
    String query = "SELECT * FROM phonebook WHERE _id=? ORDER BY _id";
    try (Connection connection = dao.getConnection()) {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        int _id = resultSet.getInt(_ID);
        String name = resultSet.getString(NAME);
        Long phonenumber = resultSet.getLong(PHONENUMBER);
        String email = resultSet.getString(EMAIL);
        phoneBook = new PhoneBook(name, phonenumber, email);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return phoneBook;
  }

  @Override
  public void addPhoneBook(PhoneBook phonebook) {
    String query ="INSERT INTO phonebook VALUES " + "(?,?,?)";
    try (Connection connection = dao.getConnection() ){
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, phonebook.getName());
      preparedStatement.setLong(2, phonebook.getPhone());
      preparedStatement.setString(3, phonebook.getEmail());
      preparedStatement.execute();
    }   catch (Exception e) { e.printStackTrace();}
  }

  @Override
  public void deletePhoneBook(PhoneBook phonebook) {
    String query = "DELETE phonebook WHERE name = ? AND phonenumber=? AND email=?";
    try (Connection connection = dao.getConnection()){
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, phonebook.getName());
      preparedStatement.setLong(2, phonebook.getPhone());
      preparedStatement.setString(3, phonebook.getEmail());
      preparedStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }




  @Override
  public void updatePhoneBook(PhoneBook phonebook,PhoneBook phonebook2) {
    String query = "UPDATE phonebook SET name = ?, phonenumber = ?, email = ? WHERE name = ?, phonenumber = ?, email = ? ";
    try (Connection connection = dao.getConnection()) {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, phonebook.getName());
      preparedStatement.setLong(2, phonebook.getPhone());
      preparedStatement.setString(3, phonebook.getEmail());
      PreparedStatement preparedStatement2 = connection.prepareStatement(query);
      preparedStatement.setString(4, phonebook2.getName());
      preparedStatement.setLong(5, phonebook2.getPhone());
      preparedStatement.setString(6, phonebook2.getEmail());
      preparedStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
