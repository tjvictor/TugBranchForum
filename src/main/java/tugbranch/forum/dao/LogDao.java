package tugbranch.forum.dao;

import tugbranch.forum.model.OpsLog;

import java.sql.SQLException;
import java.util.List;

public interface LogDao {
    List<OpsLog> getOpsLogs(int logType, String startTime, String endTime) throws SQLException;

    void insertOpsLog(String sid, int logType, String logContent) throws SQLException;


}
