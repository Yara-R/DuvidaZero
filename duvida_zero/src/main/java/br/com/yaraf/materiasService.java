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
}