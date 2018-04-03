package tugbranch.forum.dao.Imp;

import tugbranch.forum.dao.CompanyDao;
import tugbranch.forum.model.Company;

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
public class CompanyDaoImp extends BaseDao implements CompanyDao {

    @Override
    public List<Company> getCompanies(String name, int pageNumber, int pageSize) throws SQLException {
        List<Company> items = new ArrayList<Company>();

        String whereSql = "";
        String limitSql = "";
        if(StringUtils.isNotEmpty(name))
            whereSql = " where Name like '%"+name+"%'";
        if(pageNumber != 0 && pageSize != 0)
            limitSql = String.format(" limit %s,%s", (pageNumber-1)*pageSize, pageSize);

        String selectSql = String.format("SELECT Id, Name, Manager, Tel, Mail, Remark  FROM Company %s %s",whereSql, limitSql);

        try (Connection connection = DriverManager.getConnection(dbConnectString)) {
            try (Statement stmt = connection.createStatement()) {
                try(ResultSet rs = stmt.executeQuery(selectSql)) {
                    while(rs.next()){
                        Company item = new Company();
                        item.setId(rs.getString(1));
                        item.setName(rs.getString(2));
                        item.setManager(rs.getString(3));
                        item.setTel(rs.getString(4));
                        item.setMail(rs.getString(5));
                        item.setRemark(rs.getString(6));
                        items.add(item);
                    }
                }
            }
        }

        return items;
    }

    @Override
    public Company getCompanyById(String id) throws SQLException {
        Company item = new Company();
        String selectSql = String.format("SELECT Id, Name, Manager, Tel, Mail, Remark  FROM Company where Id = '%s'", id);
        try (Connection connection = DriverManager.getConnection(dbConnectString)) {
            try (Statement stmt = connection.createStatement()) {
                try(ResultSet rs = stmt.executeQuery(selectSql)) {
                    if(rs.next()){
                        item.setId(rs.getString(1));
                        item.setName(rs.getString(2));
                        item.setManager(rs.getString(3));
                        item.setTel(rs.getString(4));
                        item.setMail(rs.getString(5));
                        item.setRemark(rs.getString(6));
                    }
                }
            }
        }

        return item;
    }

    @Override
    public void deleteCompany(String id) throws SQLException {
        String deleteSql = String.format("delete from Company where id = '%s'", id);
        delete(deleteSql);
    }

    @Override
    public void addCompany(Company item) throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbConnectString)){
            String insertSql = "insert into Company values(?,?,?,?,?,?);";
            try(PreparedStatement ps = connection.prepareStatement(insertSql)) {
                ps.setString(1, item.getId());
                ps.setString(2, item.getName());
                ps.setString(3, item.getManager());
                ps.setString(4, item.getTel());
                ps.setString(5,item.getMail());
                ps.setString(6,item.getRemark());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public boolean doesCompanyNameExist(String name) throws SQLException {

        String selectSql = String.format("select count(0) from Company where Name = '%s'", name);
        try (Connection connection = DriverManager.getConnection(dbConnectString)) {
            try (Statement stmt = connection.createStatement()) {
                try (ResultSet rs = stmt.executeQuery(selectSql)) {
                    if (rs.next())
                        if (rs.getInt(1) > 0)
                            return true;
                    return false;
                }
            }
        }
    }

    @Override
    public void updateCompany(Company item) throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbConnectString)){
            String insertSql = "update Company set Name=?, Manager=?, Tel=?, Mail=?, Remark=? where Id=?";
            try(PreparedStatement ps = connection.prepareStatement(insertSql)) {
                ps.setString(1, item.getName());
                ps.setString(2, item.getManager());
                ps.setString(3, item.getTel());
                ps.setString(4,item.getMail());
                ps.setString(5,item.getRemark());
                ps.setString(6, item.getId());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public int getCompanyTotalCount(String name) throws SQLException {

        String whereSql = "";
        if(StringUtils.isNotEmpty(name))
            whereSql = " where Name like '%"+name+"%'";

        String selectSql = String.format("select count(0) from Company %s", whereSql);
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
}
