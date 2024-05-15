package br.com.yaraf.Testes;

import java.util.Collections;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.*;

@ApplicationScoped
public class UserService {
  Set<User> users = Collections.synchronizedSet(new LinkedHashSet<>());

  public Set<User> getUsers() {
    users.add(new User(UUID.randomUUID(), "Yara"));
    users.add(new User(UUID.randomUUID(), "Rebeka"));

    return users;

  }

  public Optional<User> getUserByName(String name) {
    var findUser = this.users.stream().filter(user -> user.getName().equals(name));
    return findUser.findFirst();
  }
}
