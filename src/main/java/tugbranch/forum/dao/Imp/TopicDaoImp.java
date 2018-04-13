package tugbranch.forum.dao.Imp;

import tugbranch.forum.dao.StaffDao;
import tugbranch.forum.dao.TopicDao;
import tugbranch.forum.model.ReplyTopic;
import tugbranch.forum.model.Topic;

import org.springframework.beans.factory.annotation.Autowired;
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
public class TopicDaoImp extends BaseDao implements TopicDao {

    @Autowired
    private StaffDao staffDaoImp;

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
    public void addReplyTopic(ReplyTopic item) throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbConnectString)){
            String insertSql = "insert into ReplyTopic values(?,?,?,?,?,?,?);";
            try(PreparedStatement ps = connection.prepareStatement(insertSql)) {
                int i = 1;
                ps.setString(i++, item.getId());
                ps.setString(i++, item.getTopicId());
                ps.setString(i++, item.getContent());
                ps.setString(i++, item.getStaffId());
                ps.setBoolean(i++, item.isResolved());
                ps.setString(i++, item.getCreateTime());
                ps.setInt(i++, item.getOrders());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public Topic getTopicById(String topicId, boolean topicReply) throws SQLException {
        Topic item = new Topic();

        String selectSql = String.format("SELECT Id, Title, Content, StaffId, ViewCount, ReplyCount, CategoryId, Status, PutTop, Resolved, Essence, CreateTime FROM Topic where Id = '%s' ", topicId);

        try (Connection connection = DriverManager.getConnection(dbConnectString)) {
            try (Statement stmt = connection.createStatement()) {
                try (ResultSet rs = stmt.executeQuery(selectSql)) {
                    if (rs.next()) {
                        int i = 1;
                        item.setId(rs.getString(i++));
                        item.setTitle(rs.getString(i++));
                        item.setContent(rs.getString(i++));
                        item.setStaffId(rs.getString(i++));
                        item.setViewCount(rs.getInt(i++));
                        item.setReplyCount(rs.getInt(i++));
                        item.setCategoryId(rs.getString(i++));
                        item.setStatus(rs.getInt(i++));
                        item.setPutTop(rs.getBoolean(i++));
                        item.setResolved(rs.getBoolean(i++));
                        item.setEssence(rs.getBoolean(i++));
                        item.setDateTime(rs.getString(i++));
                    }
                }
            }
        }

        return item;
    }

    @Override
    public void updateTopicViewCountById(String topicId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbConnectString)){
            String insertSql = String.format("update Topic set ViewCount=ViewCount+1 where Id = '%s';", topicId);
            try(PreparedStatement ps = connection.prepareStatement(insertSql)) {
                ps.executeUpdate();
            }
        }
    }

    @Override
    public void updateTopicReplyCountById(String topicId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbConnectString)){
            String insertSql = String.format("update Topic set ReplyCount=ReplyCount+1 where Id = '%s';", topicId);
            try(PreparedStatement ps = connection.prepareStatement(insertSql)) {
                ps.executeUpdate();
            }
        }
    }

    @Override
    public void updateTopicPropertyById(String topicId, String property, int status) throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbConnectString)){
            String insertSql = String.format("update Topic set %s=%s where Id = '%s';", property, status, topicId);
            try(PreparedStatement ps = connection.prepareStatement(insertSql)) {
                ps.executeUpdate();
            }
        }
    }

    @Override
    public List<ReplyTopic> getReplyTopicsByTopicId(String topicId, int pageNumber, int pageSize) throws SQLException {
        List<ReplyTopic> items = new ArrayList<ReplyTopic>();

        String selectSql = String.format("SELECT Id, TopicId, Content, StaffId, Resolved, CreateTime, Orders FROM ReplyTopic where TopicId = '%s' order by orders asc limit %s,%s", topicId, (pageNumber-1)*pageSize, pageSize);

        try (Connection connection = DriverManager.getConnection(dbConnectString)) {
            try (Statement stmt = connection.createStatement()) {
                try (ResultSet rs = stmt.executeQuery(selectSql)) {
                    while (rs.next()) {
                        int i = 1;
                        ReplyTopic item = new ReplyTopic();
                        item.setId(rs.getString(i++));
                        item.setTopicId(rs.getString(i++));
                        item.setContent(rs.getString(i++));
                        item.setStaffId(rs.getString(i++));
                        item.setResolved(rs.getBoolean(i++));
                        item.setCreateTime(rs.getString(i++));
                        item.setOrders(rs.getInt(i++));
                        item.setStaff(staffDaoImp.getStaffById(item.getStaffId()));
                        items.add(item);
                    }
                }
            }
        }

        return items;
    }


}
