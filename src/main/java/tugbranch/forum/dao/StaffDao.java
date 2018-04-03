package tugbranch.forum.dao;

import tugbranch.forum.model.Staff;

import java.sql.SQLException;
import java.util.List;

public interface StaffDao {
    List<Staff> getStaffs(String name, int pageNumber, int pageSize) throws SQLException;

    List<Staff> getRegisteringStaffs()  throws SQLException;

    Staff getStaffById(String id) throws SQLException;

    void deleteStaff(String id) throws SQLException;

    void addStaff(Staff item) throws SQLException;

    void updateStaff(Staff item) throws SQLException;

    void passRegisterStaff(String id) throws SQLException;

    int getStaffTotalCount(String name) throws SQLException;

    Staff login(String sid, String password) throws SQLException;

    boolean checkSidExist(String sid) throws SQLException;
}
