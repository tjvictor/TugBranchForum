package tugbranch.forum.dao;

import java.sql.SQLException;

public interface AboutDao {
    String getAboutByName(String name) throws SQLException;

    void updateAbout(String name, String content) throws SQLException;
}
