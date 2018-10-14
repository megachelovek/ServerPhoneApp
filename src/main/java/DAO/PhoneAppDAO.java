package DAO;


import Phonebook.PhoneBook;
;
import java.util.ArrayList;
import java.util.List;


public abstract class PhoneAppDAO implements IPhoneAppDAO{

  private List<PhoneBook> phonebooks = new ArrayList<PhoneBook>();

  public PhoneAppDAO() {
    phonebooks.add(new PhoneBook("John", 11111,"john@domain.com"));
    phonebooks.add(new PhoneBook("Susan",222222, "susan@domain.com"));
  }

  public List<PhoneBook> getAll() {
    return phonebooks;
  }

  public void save(PhoneBook phonebook) {
    phonebooks.add(phonebook);
  }

  public void update(PhoneBook phonebook, String[] params) {
    phonebook.setName(params[0]);
    phonebook.setPhone(Integer.parseInt(params[1]));
    phonebook.setEmail(params[2]);
    phonebooks.add(phonebook);
  }


  public void delete(PhoneBook phonebook) {
    phonebooks.remove(phonebook);
  }
}
