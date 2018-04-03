package tugbranch.forum.dao;

import tugbranch.forum.model.Company;

import java.sql.SQLException;
import java.util.List;

public interface CompanyDao {
    List<Company> getCompanies(String name, int pageNumber, int pageSize) throws SQLException;

    Company getCompanyById(String id) throws SQLException;

    void deleteCompany(String id) throws SQLException;

    void addCompany(Company item) throws SQLException;

    boolean doesCompanyNameExist(String name) throws SQLException;

    void updateCompany(Company item) throws SQLException;

    int getCompanyTotalCount(String name) throws SQLException;
}
