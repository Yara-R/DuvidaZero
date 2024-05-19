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
            String sql = "SELECT alunos.*, ENSINO_MEDIO.nome_responsavel, ENSINO_MEDIO.contato_responsavel, ENSINO_MEDIO.ano_escolar, ENSINO_MEDIO.colegio, VESTIBULAR_DESEJADO.vestibular, composta.codigo_turma FROM alunos LEFT JOIN ENSINO_MEDIO ON ENSINO_MEDIO.cpf = alunos.cpf LEFT JOIN PREVESTIBULAR ON PREVESTIBULAR.cpf = alunos.cpf LEFT JOIN VESTIBULAR_DESEJADO ON VESTIBULAR_DESEJADO.fk_prevestibular = PREVESTIBULAR.id LEFT JOIN composta ON composta.cpf = alunos.cpf WHERE alunos.cpf = ?";
            
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
                        alunos.setAnoEscolar(resultSet.getInt("ano_escolar"));
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

    public Optional<Alunos> editarProf(String cpf, String nome, String contato) {
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
}