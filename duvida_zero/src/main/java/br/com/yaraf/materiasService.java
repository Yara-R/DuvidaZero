package br.com.yaraf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class materiasService {

    private final String url = "jdbc:mysql://localhost:3306/DuvidaZero";
    private final String user = "root";
    private final String password = "Faran#2001";

    public Optional<Materias> buscarAulas(String codigoAula) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT * FROM Aulas WHERE codigoA = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, codigoAula);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Materias materias = new Materias();
                        materias.setCodigoA(resultSet.getString("codigoA"));
                        materias.setCronograma(resultSet.getString("cronograma"));
                        materias.setHorario(resultSet.getString("horario"));
                        return Optional.of(materias);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Materias> adicionarAula(String codigoAula, String cronograma, String horario) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql1 = "INSERT INTO Aulas (codigoA, cronograma, horario) VALUES (?, ?, ?)";


            try (PreparedStatement statement1 = connection.prepareStatement(sql1)) {
                statement1.setString(1, codigoAula);
                statement1.setString(2, cronograma);
                statement1.setString(3, horario);

                int affectedRows = statement1.executeUpdate();

                if (affectedRows == 0) {
                
                        Materias materias = new Materias();
                        materias.setCodigoA(codigoAula);
                        materias.setCronograma(cronograma);;
                        materias.setHorario(horario);

                        return Optional.of(materias);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return Optional.empty();
    }


    public Optional<Materias> editarAula(String codigoAula, String cronograma, String horario) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "UPDATE Aulas SET cronograma = ?, horario = ? WHERE codigoA = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                
                statement.setString(1, cronograma);
                statement.setString(2, horario);
                statement.setString(3, codigoAula);
    
                int affectedRows = statement.executeUpdate();
                
                if (affectedRows > 0) { 
                    Materias materias = new Materias();
                        materias.setCodigoA(codigoAula);
                        materias.setCronograma(cronograma);;
                        materias.setHorario(horario);

                        return Optional.of(materias);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    public Optional<Materias> deletarAula(String codigoAula) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "DELETE FROM Aulas WHERE codigoA=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                
                statement.setString(1, codigoAula);
    
                int affectedRows = statement.executeUpdate();
                
                if (affectedRows > 0) { 
                    Materias materias = new Materias();
                        materias.setCodigoA(codigoAula);
                        
                        return Optional.of(materias);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}