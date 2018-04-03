package tugbranch.forum.dao.Imp;

import tugbranch.forum.dao.LogDao;
import tugbranch.forum.model.OpsLog;
import tugbranch.forum.utils.CommonUtils;
import tugbranch.forum.utils.OpsLogTypeEnum;

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
public class LogDaoImp extends BaseDao implements LogDao {
    @Override
    public List<OpsLog> getOpsLogs(int logType, String startTime, String endTime) throws SQLException {
        List<OpsLog> logs = new ArrayList<OpsLog>();
        endTime = CommonUtils.dateAddDay(endTime, 1);
        String selectSql = String.format("SELECT o.Id, o.Sid, o.DateTime, o.LogType, o.LogContent, s.Name  FROM OpsLog o left join Staff s on o.Sid = s.Sid where o.LogType = %s and o.DateTime > '%s' and o.DateTime <= '%s';", logType, startTime, endTime);
        try (Connection connection = DriverManager.getConnection(dbConnectString)) {
            try (Statement stmt = connection.createStatement()) {
                try (ResultSet rs = stmt.executeQuery(selectSql)) {
                    while (rs.next()) {
                        int i = 1;
                        OpsLog item = new OpsLog();
                        item.setId(rs.getString(i++));
                        item.setSid(rs.getString(i++));
                        item.setDateTime(rs.getString(i++));
                        item.setLogType(OpsLogTypeEnum.getName(rs.getInt(i++)));
                        item.setLogContent(rs.getString(i++));
                        item.setOpName(rs.getString(i++));
                        logs.add(item);
                    }
                }
            }
        }

        return logs;
    }

    @Override
    public void insertOpsLog(String sid, int logType, String logContent) throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbConnectString)){
            String insertSql = "insert into OpsLog values(null,?,?,?,?);";
            try(PreparedStatement ps = connection.prepareStatement(insertSql)) {
                int i = 1;
                ps.setString(i++, sid);
                ps.setString(i++, CommonUtils.getCurrentDateTime());
                ps.setInt(i++, logType);
                ps.setString(i++, logContent);
                ps.executeUpdate();
            }
        }
    }
}
