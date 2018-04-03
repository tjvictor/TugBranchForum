package tugbranch.forum.dao.Imp;

import tugbranch.forum.dao.RoleDao;
import tugbranch.forum.model.Role;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class RoleDaoImp extends BaseDao implements RoleDao {
    @Override
    public List<Role> getRoles(String name, int pageNumber, int pageSize) throws SQLException {
        List<Role> items = new ArrayList<Role>();

        String whereSql = "";
        String limitSql = "";
        if(StringUtils.isNotEmpty(name))
            whereSql = " where a.Name like '%"+name+"%'";
        if(pageNumber != 0 && pageSize != 0)
            limitSql = String.format(" limit %s,%s", (pageNumber-1)*pageSize, pageSize);

        String selectSql = String.format("SELECT Id, Name, Remark FROM Role %s %s",whereSql, limitSql);

        try (Connection connection = DriverManager.getConnection(dbConnectString)) {
            try (Statement stmt = connection.createStatement()) {
                try(ResultSet rs = stmt.executeQuery(selectSql)) {
                    while(rs.next()){
                        Role item = new Role();
                        item.setId(rs.getString(1));
                        item.setName(rs.getString(2));
                        item.setRemark(rs.getString(3));
                        items.add(item);
                    }
                }
            }
        }

        return items;
    }
}
