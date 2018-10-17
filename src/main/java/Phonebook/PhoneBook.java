package Phonebook;

import java.io.Serializable;

public class PhoneBook implements Serializable {
  private Integer mId;
  private String mName;
  private Long mPhone;
  private String mEmail;

  public PhoneBook(String name, Long phonenumber, String email) {
    mName = name;
    mPhone = phonenumber;
    mEmail = email;
  }
  public Integer getId() {
    return mId;
  }

  public void setName(String name) {
    mName = name;
  }

  public String getName() {
    return mName;
  }

  public void setPhone(Long phone) {
    mPhone = phone;
  }

  public Long getPhone() {
    return mPhone;
  }

  public void setEmail(String email) {
    mEmail = email;
  }

  public String getEmail() {
    return mEmail;
  }
}
