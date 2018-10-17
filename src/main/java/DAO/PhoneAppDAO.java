package DAO;


import Phonebook.PhoneBook;

import java.util.List;


public interface PhoneAppDAO {

  String _ID = "_id";

  String NAME = "name";

  String PHONENUMBER = "phonenumber";

  String EMAIL = "email";


  void addPhoneBook(PhoneBook phonebook);

  List<PhoneBook> getPhoneBooks();

  PhoneBook getPhoneBookNumber(Long phonenumber);

  PhoneBook getPhoneBookId(int _id);

  void updatePhoneBook(PhoneBook phonebook,PhoneBook phonebook2);

  void deletePhoneBook(PhoneBook phonebook);

}
