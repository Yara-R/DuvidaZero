package br.com.yaraf;

import java.sql.*;

import jakarta.activation.DataSource;
import jakarta.inject.Inject;

public class profParticularService {
    
    @Inject
    DataSource dataSource;

    public void consultarBancoDeDados() {
        try (Connection conn = ((Statement) dataSource).getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT nome FROM profParticular");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                
            }
        } catch (SQLException e) {
            // Lidar com exceções
        }
    }
}
