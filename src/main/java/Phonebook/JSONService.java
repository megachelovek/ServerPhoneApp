package Phonebook;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/json/phonebook")
public class JSONService {
  @GET
  @Path("/get")
  @Produces("application/json")
  public PhoneBook getProductInJSON() {

    PhoneBook phoneBook = new PhoneBook();
    phoneBook.setName("iPad 3");
    phoneBook.setPhone((long) 1111);
    phoneBook.setEmail("rest@");

    return phoneBook;
  }

  @POST
  @Path("/post")
  @Consumes("application/json")
  public Response createProductInJSON(PhoneBook phoneBook) {
    String result = "PhoneBook created : " + phoneBook;
    return Response.status(201).entity(result).build();

  }



}
