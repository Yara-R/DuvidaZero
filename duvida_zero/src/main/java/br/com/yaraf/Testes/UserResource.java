package br.com.yaraf.Testes;

import java.util.*;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import org.jboss.resteasy.reactive.RestQuery;

@Path("/users")
public class UserResource {

  @Inject
  private UserService userService;

  @GET()
  public Set<User> getUsers() {
    return this.userService.getUsers();
  }

  @GET
  @Path("/findByName")
  public Optional<User> getUserByName(@RestQuery String name) {
    return this.userService.getUserByName(name);
  }

  @GET
  @Path("/findByParams/{name}")
  public Optional<User> findByParams(String name) {
    System.out.println("Nome " + name);
    return this.userService.getUserByName(name);
  }

}
