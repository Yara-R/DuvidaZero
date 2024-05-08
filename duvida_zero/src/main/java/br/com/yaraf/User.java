package br.com.yaraf;

import java.util.UUID;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

  private UUID id;
  private String name;
}