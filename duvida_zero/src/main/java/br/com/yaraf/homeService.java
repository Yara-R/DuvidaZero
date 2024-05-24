package br.com.yaraf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class homeService {
    
    private final String url = "jdbc:mysql://localhost:3306/DuvidaZero";
    private final String user = "root";
    private final String password = "Faran#2001";

    public Optional<Home> buscarHome(String codigoT, String codigoA) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT Turmas.codigoT, Aulas.codigoA, Prof_particular.cpf, Prof_particular.nome, COUNT(composta.codigo_turma) AS alunosporturma FROM Turmas JOIN ensina ON Turmas.codigoT = ensina.codigo_turma JOIN Aulas ON ensina.codigo_aula = Aulas.codigoA JOIN composta ON Turmas.codigoT = composta.codigo_turma JOIN Prof_particular ON ensina.cpf_prof_particular = Prof_particular.cpf WHERE Turmas.codigoT = ? OR Aulas.codigoA IN (SELECT codigoA FROM Aulas WHERE Aulas.codigoA = ?) GROUP BY Turmas.codigoT, Aulas.codigoA, Prof_particular.cpf, Prof_particular.nome;";
    
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, codigoT);
                statement.setString(2, codigoA);
    
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Home home = new Home();
                        home.setCpf(resultSet.getString("cpf"));
                        home.setNome(resultSet.getString("nome"));
                        home.setCodigoA(resultSet.getString("codigoA"));
                        home.setCodigoT(resultSet.getString("codigoT"));
                        home.setAlunosPorTurma(resultSet.getString("alunosporturma"));
                        return Optional.of(home);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    

}
