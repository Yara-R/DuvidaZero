package br.com.yaraf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    

    public void delete(String cpf) {
        
        try (Connection conexao = DriverManager.getConnection(url, user, password)) {

            String sql = "DELETE FROM Vestibular_Desejado WHERE fk_prevestibular IN (SELECT id FROM PreVestibular WHERE cpf = ?); DELETE FROM Ensino_Medio WHERE cpf = ?; DELETE FROM PreVestibular WHERE cpf = ?; DELETE FROM alunos WHERE cpf = ?";
    
            try (PreparedStatement statement = conexao.prepareStatement(sql)) {
                statement.setString(1, cpf);
                statement.setString(2, cpf);
                statement.setString(3, cpf);
                statement.setString(4, cpf);
    
                int linhasAfetadas = statement.executeUpdate();
                System.out.println("Linhas afetadas: " + linhasAfetadas);
    
            }
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

}