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
            
            String sql = "SELECT Prof_particular.*, COUNT(ensina.cpf_prof_particular) AS turmasporprofessor FROM Prof_particular LEFT JOIN ensina ON Prof_particular.cpf = ensina.cpf_prof_particular WHERE Prof_particular.cpf = ? GROUP BY Prof_particular.cpf;";

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
                        professor.setTurmaPorProfessor(resultSet.getInt("turmasporprofessor"));
    
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
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
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
                
                if (affectedRows == 0) { // Verifica se houve alguma linha afetada pela atualização
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty(); // Retorna um Optional vazio se não houver professor atualizado
    }

    // Editar

    public Optional<ProfParticular> editarProf(String nome, String telefone, String cpf) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "UPDATE Prof_particular SET nome = ?, telefone = ? WHERE cpf = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, nome); // Nome a ser atualizado
                statement.setString(2, telefone);
                statement.setString(3, cpf); // CPF do professor a ser atualizado
    
                int affectedRows = statement.executeUpdate();
                
                if (affectedRows > 0) { // Verifica se houve alguma linha afetada pela atualização
                    ProfParticular professor = new ProfParticular();
                    professor.setNome(nome); // Define o novo nome no objeto Professor
                    professor.setTelefone(telefone);
                    professor.setCpf(cpf); // Define o CPF no objeto Professor
    
                    return Optional.of(professor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty(); // Retorna um Optional vazio se não houver professor atualizado
    }

    // Deletar

    public Optional<ProfParticular> deletarProfessor(String cpf) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // Iniciar a transação
            connection.setAutoCommit(false);
            
            try {
                String sql1 = "DELETE FROM ensina WHERE cpf_prof_particular = ?";
                String sql2 = "DELETE FROM Prof_particular WHERE cpf = ?";
                
                try (PreparedStatement statement1 = connection.prepareStatement(sql1);
                     PreparedStatement statement2 = connection.prepareStatement(sql2)) {
                    
                    // Definir parâmetro para o primeiro comando DELETE
                    statement1.setString(1, cpf);
                    int affectedRows1 = statement1.executeUpdate();
                    
                    // Definir parâmetro para o segundo comando DELETE
                    statement2.setString(1, cpf);
                    int affectedRows2 = statement2.executeUpdate();
                    
                    // Verificar se ambos os DELETEs afetaram linhas
                    if (affectedRows1 > 0 || affectedRows2 > 0) {
                        // Confirmar a transação
                        connection.commit();
                        
                        ProfParticular professor = new ProfParticular();
                        professor.setCpf(cpf);
                        return Optional.of(professor);
                    } else {
                        // Se nenhum DELETE afetou linhas, desfazer a transação
                        connection.rollback();
                    }
                } catch (SQLException e) {
                    // Em caso de exceção, desfazer a transação
                    connection.rollback();
                    e.printStackTrace();
                }
            } finally {
                // Restaurar o auto-commit para o estado padrão
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    
    
    
}

