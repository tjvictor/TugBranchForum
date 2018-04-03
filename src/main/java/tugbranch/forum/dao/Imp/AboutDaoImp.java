package tugbranch.forum.dao.Imp;

import tugbranch.forum.dao.AboutDao;

import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class AboutDaoImp extends BaseDao implements AboutDao {
    @Override
    public String getAboutByName(String name) throws SQLException {
        String selectSql = String.format("SELECT Content FROM About where Name = '%s';", name);
        try (Connection connection = DriverManager.getConnection(dbConnectString)) {
            try (Statement stmt = connection.createStatement()) {
                try (ResultSet rs = stmt.executeQuery(selectSql)) {
                    if (rs.next()) {
                        return rs.getString(1);
                    }
                }
            }
        }

        return "";
    }

    @Override
    public void updateAbout(String name, String content) throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbConnectString)) {
            String insertSql = "update About set Content = ? where Name = ?;";
            try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
                int i = 1;
                ps.setString(i++, content);
                ps.setString(i++, name);
                ps.executeUpdate();
            }
        }
    }
}
