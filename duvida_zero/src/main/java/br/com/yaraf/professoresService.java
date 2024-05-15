package br.com.yaraf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class professoresService {

    private final String url = "jdbc:mysql://localhost:3306/DuvidaZero";
    private final String user = "root";
    private final String password = "Faran#2001";

    public Optional<ProfParticular> buscarPorCpf(String cpf) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            
            String sql = "SELECT * FROM Prof_particular WHERE cpf = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                
                statement.setString(1, cpf);

                try (ResultSet resultSet = statement.executeQuery()) {


                    if (resultSet.next()) {

                        ProfParticular professor = new ProfParticular();

                        professor.setCpf(resultSet.getString("cpf"));
                        professor.setNome(resultSet.getString("nome"));
                        professor.setTelefone(resultSet.getString("telefone"));
                        professor.setEmail(resultSet.getString("email"));
                        professor.setCidade(resultSet.getString("cidade"));
                        professor.setBairro(resultSet.getString("bairro"));
                        professor.setRua(resultSet.getString("rua"));
                        professor.setNumero(resultSet.getInt("numero"));
                        professor.setApartamento(resultSet.getString("apartamento"));

                        return Optional.of(professor);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    


    public void inserirProfessor(ProfParticular professor) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {

            String sql = "INSERT INTO Prof_particular (cpf, nome, telefone, email, cidade, bairro, rua, numero, apartamento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, professor.getCpf());
                statement.setString(2, professor.getNome());
                statement.setString(3, professor.getTelefone());
                statement.setString(4, professor.getEmail());
                statement.setString(5, professor.getCidade());
                statement.setString(6, professor.getBairro());
                statement.setString(7, professor.getRua());
                statement.setInt(8, professor.getNumero());
                statement.setString(9, professor.getApartamento());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void deletarProf(String cpf) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {

            String sql = "DELETE FROM Prof_particular WHERE cpf = ?";
            
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, cpf);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    
}

