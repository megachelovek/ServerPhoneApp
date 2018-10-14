package DAO;

import Phonebook.PhoneBook;

import java.util.List;

public interface IPhoneAppDAO {

  List<PhoneBook> getAll();

  void save(PhoneBook t);

  void update(PhoneBook t, String[] params);

  void delete(PhoneBook t);
}
