package tugbranch.forum.dao;

import tugbranch.forum.model.Role;

import java.sql.SQLException;
import java.util.List;

public interface RoleDao {
    List<Role> getRoles(String name, int pageNumber, int pageSize) throws SQLException;
}
