package tugbranch.forum.dao.Imp;

import tugbranch.forum.dao.TopicDao;
import tugbranch.forum.model.Topic;

import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class TopicDaoImp extends BaseDao implements TopicDao {
    @Override
    public void addTopic(Topic item) throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbConnectString)){
            String insertSql = "insert into Topic values(?,?,?,?,?,?,?,?,?,?,?,?);";
            try(PreparedStatement ps = connection.prepareStatement(insertSql)) {
                int i = 1;
                ps.setString(i++, item.getId());
                ps.setString(i++, item.getTitle());
                ps.setString(i++, item.getContent());
                ps.setString(i++, item.getStaffId());
                ps.setInt(i++, item.getViewCount());
                ps.setInt(i++, item.getReplyCount());
                ps.setString(i++, item.getCategoryId());
                ps.setInt(i++, item.getStatus());
                ps.setBoolean(i++, item.isPutTop());
                ps.setBoolean(i++, item.isResolved());
                ps.setBoolean(i++, item.isEssence());
                ps.setString(i++, item.getDateTime());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public Topic getTopicById(String topicId, boolean topicReply) throws SQLException {
        Topic item = new Topic();

        String selectSql = String.format("SELECT a.Id, a.Name, a.Password, a.CompanyId, a.Sid, a.Tel, a.RoleId, a.Remark, a.Status, b.Name, c.Name FROM Staff a join Company b on a.CompanyId=b.Id join Role c on a.RoleId=c.Id where a.Id = '%s' ", topicId);

        try (Connection connection = DriverManager.getConnection(dbConnectString)) {
            try (Statement stmt = connection.createStatement()) {
                try (ResultSet rs = stmt.executeQuery(selectSql)) {
                    if (rs.next()) {
                        int i = 1;
                        item.setId(rs.getString(i++));

                    }
                }
            }
        }

        return item;
    }
}
