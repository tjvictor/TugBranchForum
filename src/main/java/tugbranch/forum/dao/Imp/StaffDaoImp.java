package tugbranch.forum.dao.Imp;

import tugbranch.forum.dao.StaffDao;
import tugbranch.forum.model.Staff;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class StaffDaoImp extends BaseDao implements StaffDao {
    @Override
    public List<Staff> getStaffs(String name, int pageNumber, int pageSize) throws SQLException {
        List<Staff> items = new ArrayList<Staff>();

        String whereSql = "where Status=1 ";
        String limitSql = "";
        if (StringUtils.isNotEmpty(name))
            whereSql += " and a.Name like '%" + name + "%'";
        if (pageNumber != 0 && pageSize != 0)
            limitSql = String.format(" limit %s,%s", (pageNumber - 1) * pageSize, pageSize);

        String selectSql = String.format("SELECT a.Id, a.Name, a.Password, a.CompanyId, a.Sid, a.Tel, a.RoleId, a.Remark, b.Name, c.Name FROM Staff a join Company b on a.CompanyId=b.Id join Role c on a.RoleId=c.Id %s %s", whereSql, limitSql);

        try (Connection connection = DriverManager.getConnection(dbConnectString)) {
            try (Statement stmt = connection.createStatement()) {
                try (ResultSet rs = stmt.executeQuery(selectSql)) {
                    while (rs.next()) {
                        Staff item = new Staff();
                        int i = 1;
                        item.setId(rs.getString(i++));
                        item.setName(rs.getString(i++));
                        item.setPassword(rs.getString(i++));
                        item.setCompanyId(rs.getString(i++));
                        item.setSid(rs.getString(i++));
                        item.setTel(rs.getString(i++));
                        item.setRoleId(rs.getString(i++));
                        item.setRemark(rs.getString(i++));
                        item.setCompanyName(rs.getString(i++));
                        item.setRoleName(rs.getString(i++));
                        items.add(item);
                    }
                }
            }
        }

        return items;
    }

    @Override
    public List<Staff> getRegisteringStaffs() throws SQLException {
        List<Staff> items = new ArrayList<Staff>();

        String selectSql = String.format("SELECT a.Id, a.Name, a.Password, a.CompanyId, a.Sid, a.Tel, a.RoleId, a.Remark, b.Name, c.Name FROM Staff a join Company b on a.CompanyId=b.Id join Role c on a.RoleId=c.Id where a.Status=0;");

        try (Connection connection = DriverManager.getConnection(dbConnectString)) {
            try (Statement stmt = connection.createStatement()) {
                try (ResultSet rs = stmt.executeQuery(selectSql)) {
                    while (rs.next()) {
                        Staff item = new Staff();
                        int i = 1;
                        item.setId(rs.getString(i++));
                        item.setName(rs.getString(i++));
                        item.setPassword(rs.getString(i++));
                        item.setCompanyId(rs.getString(i++));
                        item.setSid(rs.getString(i++));
                        item.setTel(rs.getString(i++));
                        item.setRoleId(rs.getString(i++));
                        item.setRemark(rs.getString(i++));
                        item.setCompanyName(rs.getString(i++));
                        item.setRoleName(rs.getString(i++));
                        items.add(item);
                    }
                }
            }
        }

        return items;
    }

    @Override
    public Staff getStaffById(String id) throws SQLException {
        Staff item = new Staff();

        String selectSql = String.format("SELECT a.Id, a.Name, a.Password, a.CompanyId, a.Sid, a.Tel, a.RoleId, a.Remark, a.Status, b.Name, c.Name FROM Staff a join Company b on a.CompanyId=b.Id join Role c on a.RoleId=c.Id where a.Id = '%s' ", id);

        try (Connection connection = DriverManager.getConnection(dbConnectString)) {
            try (Statement stmt = connection.createStatement()) {
                try (ResultSet rs = stmt.executeQuery(selectSql)) {
                    if (rs.next()) {
                        int i = 1;
                        item.setId(rs.getString(i++));
                        item.setName(rs.getString(i++));
                        item.setPassword(rs.getString(i++));
                        item.setCompanyId(rs.getString(i++));
                        item.setSid(rs.getString(i++));
                        item.setTel(rs.getString(i++));
                        item.setRoleId(rs.getString(i++));
                        item.setRemark(rs.getString(i++));
                        item.setStatus(rs.getString(i++));
                        item.setCompanyName(rs.getString(i++));
                        item.setRoleName(rs.getString(i++));
                    }
                }
            }
        }

        return item;
    }

    @Override
    public void deleteStaff(String id) throws SQLException {
        String deleteSql = String.format("delete from Staff where id = '%s'", id);
        delete(deleteSql);
    }

    @Override
    public void addStaff(Staff item) throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbConnectString)) {
            String insertSql = "insert into Staff values(?,?,?,?,?,?,?,?,?);";
            try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
                int i=1;
                ps.setString(i++, item.getId());
                ps.setString(i++, item.getSid());
                ps.setString(i++, item.getName());
                ps.setString(i++, item.getPassword());
                ps.setString(i++, item.getCompanyId());
                ps.setString(i++, item.getTel());
                ps.setString(i++, item.getRoleId());
                ps.setString(i++, item.getRemark());
                ps.setString(i++, item.getStatus());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public void updateStaff(Staff item) throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbConnectString)) {
            String insertSql = "update Staff set Name=?, Password=?, CompanyId=?, Sid=?, Tel=?, RoleId=?, Remark=?, Status=? where Id=?";
            try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
                ps.setString(1, item.getName());
                ps.setString(2, item.getPassword());
                ps.setString(3, item.getCompanyId());
                ps.setString(4, item.getSid());
                ps.setString(5, item.getTel());
                ps.setString(6, item.getRoleId());
                ps.setString(7, item.getRemark());
                ps.setString(8, item.getStatus());
                ps.setString(9, item.getId());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public void passRegisterStaff(String id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbConnectString)) {
            String insertSql = "update Staff set Status=1 where Id=?";
            try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
                ps.setString(1, id);
                ps.executeUpdate();
            }
        }
    }

    @Override
    public int getStaffTotalCount(String name) throws SQLException {
        String whereSql = "where Status = 1 ";
        if (StringUtils.isNotEmpty(name))
            whereSql += " and Name like '%" + name + "%'";

        String selectSql = String.format("select count(0) from Staff %s", whereSql);
        try (Connection connection = DriverManager.getConnection(dbConnectString)) {
            try (Statement stmt = connection.createStatement()) {
                try (ResultSet rs = stmt.executeQuery(selectSql)) {
                    if (rs.next())
                        return rs.getInt(1);
                }
            }
        }

        return 0;
    }

    @Override
    public Staff login(String sid, String password) throws SQLException {
        Staff item = new Staff();

        String selectSql = String.format("SELECT a.Id, a.Name, a.Password, a.CompanyId, a.Sid, a.Tel, a.RoleId, a.Remark, b.Name, c.Name FROM Staff a join Company b on a.CompanyId=b.Id join Role c on a.RoleId=c.Id where Status=1 and a.Sid = '%s' and a.Password= '%s' ", sid, password);

        try (Connection connection = DriverManager.getConnection(dbConnectString)) {
            try (Statement stmt = connection.createStatement()) {
                try (ResultSet rs = stmt.executeQuery(selectSql)) {
                    if (rs.next()) {
                        int i = 1;
                        item.setId(rs.getString(i++));
                        item.setName(rs.getString(i++));
                        item.setPassword(rs.getString(i++));
                        item.setCompanyId(rs.getString(i++));
                        item.setSid(rs.getString(i++));
                        item.setTel(rs.getString(i++));
                        item.setRoleId(rs.getString(i++));
                        item.setRemark(rs.getString(i++));
                        item.setCompanyName(rs.getString(i++));
                        item.setRoleName(rs.getString(i++));
                    }
                }
            }
        }

        return item;
    }

    @Override
    public boolean checkSidExist(String sid) throws SQLException {
        String selectSql = String.format("SELECT count(0) from Staff where Sid = '%s'", sid);

        try (Connection connection = DriverManager.getConnection(dbConnectString)) {
            try (Statement stmt = connection.createStatement()) {
                try (ResultSet rs = stmt.executeQuery(selectSql)) {
                    if (rs.next()) {
                        if (rs.getInt(1) > 0)
                            return true;
                    }
                }
            }
        }
        return false;
    }
}
