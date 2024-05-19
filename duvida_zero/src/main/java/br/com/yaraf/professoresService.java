package br.com.yaraf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    public Optional<ProfParticular> adicionarProfessor(String cpf, String nome, String telefone, String email, String cidade, String bairro, String rua, int numero, String apartamento) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "INSERT INTO Prof_particular (cpf, nome, telefone, email, cidade, bairro, rua, numero, apartamento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, cpf);
                statement.setString(2, nome);
                statement.setString(3, telefone);
                statement.setString(4, email);
                statement.setString(5, cidade);
                statement.setString(6, bairro);
                statement.setString(7, rua);
                statement.setInt(8, numero);
                statement.setString(9, apartamento);
    
                int affectedRows = statement.executeUpdate();
    
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            ProfParticular professor = new ProfParticular();
                            professor.setCpf(cpf);
                            professor.setNome(nome);
                            professor.setTelefone(telefone);
                            professor.setEmail(email);
                            professor.setCidade(cidade);
                            professor.setBairro(bairro);
                            professor.setRua(rua);
                            professor.setNumero(numero);
                            professor.setApartamento(apartamento);
                            return Optional.of(professor);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Editar

    public Optional<ProfParticular> editarProf(String nome, String cpf) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "UPDATE Prof_particular SET nome = ? WHERE cpf = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, nome); // Nome a ser atualizado
                statement.setString(2, cpf); // CPF do professor a ser atualizado
    
                int affectedRows = statement.executeUpdate();
                
                if (affectedRows > 0) { // Verifica se houve alguma linha afetada pela atualização
                    ProfParticular professor = new ProfParticular();
                    professor.setNome(nome); // Define o novo nome no objeto Professor
                    professor.setCpf(cpf); // Define o CPF no objeto Professor
    
                    return Optional.of(professor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty(); // Retorna um Optional vazio se não houver professor atualizado
    }
    
}

