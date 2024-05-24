package br.com.yaraf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class alunosService {

    private final String url = "jdbc:mysql://localhost:3306/DuvidaZero";
    private final String user = "root";
    private final String password = "Faran#2001";

    public List<Alunos> buscarAlunosPorCpf(String cpf) {
        List<Alunos> alunosEncontrados = new ArrayList<>();
        
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT alunos.*, Ensino_Medio.nome_responsavel, Ensino_Medio.contato_responsavel, Ensino_Medio.ano_escolar, Ensino_Medio.colegio, VESTIBULAR_DESEJADO.vestibular, composta.codigo_turma FROM alunos LEFT JOIN PreVestibular ON alunos.cpf = PreVestibular.cpf LEFT JOIN Vestibular_Desejado ON PreVestibular.id = Vestibular_Desejado.fk_prevestibular LEFT JOIN Ensino_Medio ON alunos.cpf = Ensino_Medio.cpf LEFT JOIN composta ON composta.cpf = alunos.cpf WHERE alunos.cpf =?";
            
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, cpf);
                
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Alunos alunos = new Alunos();
                        
                        alunos.setCpf(resultSet.getString("cpf"));
                        alunos.setNome(resultSet.getString("nome"));
                        alunos.setContato(resultSet.getString("contato"));

                        alunos.setVestibularDesejado(resultSet.getString("vestibular"));

                        alunos.setNomeResponsavel(resultSet.getString("nome_responsavel"));
                        alunos.setContatoResponsavel(resultSet.getString("contato_responsavel"));
                        alunos.setAnoEscolar(resultSet.getString("ano_escolar"));
                        alunos.setColegio(resultSet.getString("colegio"));
                        
                        alunos.setCodigoTurma(resultSet.getString("codigo_turma"));
                        
                        alunosEncontrados.add(alunos);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return alunosEncontrados;
    }
    

    public Optional<Alunos> adicionarAlunos(String cpf, String nome, String contato) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "INSERT INTO alunos (cpf, nome, contato) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, cpf); 
                statement.setString(2, nome);
                statement.setString(3, contato);
    
                int affectedRows = statement.executeUpdate();
                
                if (affectedRows == 0) { 
                    Alunos alunos = new Alunos();

                    alunos.setCpf(cpf);
                    alunos.setNome(nome); 
                    alunos.setContato(contato);
    
    
                    return Optional.of(alunos);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty(); 
    }

    public Optional<Alunos> editarAluno(String nome, String contato, String cpf) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "UPDATE alunos SET nome = ?, contato = ? WHERE cpf = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, nome); 
                statement.setString(2, contato);
                statement.setString(3, cpf); 
    
                int affectedRows = statement.executeUpdate();
                
                if (affectedRows > 0) { 
                    Alunos alunos = new Alunos();
                    alunos.setNome(nome); 
                    alunos.setContato(contato);
                    alunos.setCpf(cpf); 
    
                    return Optional.of(alunos);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty(); 
    }


    public Optional<Alunos> deletarAluno(String cpf) {
            try (Connection connection = DriverManager.getConnection(url, user, password)) {
                // Iniciar a transação
                connection.setAutoCommit(false);
                
                try {
                    String sql1 = "DELETE FROM Ensino_medio WHERE cpf=?";
                    String sql2 = "DELETE FROM PreVestibular WHERE cpf=?";
                    String sql3 = "DELETE FROM alunos WHERE cpf=?";
                    
                    try (PreparedStatement statement1 = connection.prepareStatement(sql1);
                         PreparedStatement statement2 = connection.prepareStatement(sql2);
                         PreparedStatement statement3 = connection.prepareStatement(sql3)) {
                        
                        // Definir parâmetro para o primeiro comando DELETE
                        statement1.setString(1, cpf);
                        int affectedRows1 = statement1.executeUpdate();
                        
                        // Definir parâmetro para o segundo comando DELETE
                        statement2.setString(1, cpf);
                        int affectedRows2 = statement2.executeUpdate();

                        statement3.setString(1, cpf);
                        int affectedRows3 = statement3.executeUpdate();
                        
                        // Verificar se ambos os DELETEs afetaram linhas
                        if (affectedRows1 > 0 || affectedRows2 > 0 || affectedRows3 > 0) {
                            // Confirmar a transação
                            connection.commit();
                            
                            Alunos alunos = new Alunos();
                            alunos.setCpf(cpf);
                            return Optional.of(alunos);

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