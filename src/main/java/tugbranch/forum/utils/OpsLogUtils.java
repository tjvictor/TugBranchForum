package tugbranch.forum.utils;

import tugbranch.forum.dao.LogDao;
import tugbranch.forum.model.OpsLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.sql.SQLException;
import java.util.List;

@Component
public class OpsLogUtils {
    @Autowired
    private LogDao logDaoImp;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void log(String sid, OpsLogTypeEnum logType, String logContent) throws SQLException {
        logDaoImp.insertOpsLog(sid, logType.getIndex(), logContent);

        logger.info(String.format("日志类型: %s, %s %s", logType.getName(), sid, logContent));
    }

    public List<OpsLog> getOpsLogsByTypeAndDate(int logType, String startDate, String endDate) throws SQLException {
        return logDaoImp.getOpsLogs(logType, startDate, endDate);
    }
}
