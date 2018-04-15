package tugbranch.forum.dao;

import tugbranch.forum.model.ReplyTopic;
import tugbranch.forum.model.Topic;

import java.sql.SQLException;
import java.util.List;

public interface TopicDao {

    void addTopic(Topic item) throws SQLException;

    void editTopic(Topic item) throws SQLException;

    void addReplyTopic(ReplyTopic item) throws SQLException;

    Topic getTopicById(String topicId, boolean topicReply) throws SQLException;

    void updateTopicViewCountById(String topicId) throws SQLException;

    void updateTopicReplyCountById(String topicId) throws SQLException;

    void updateTopicPropertyById(String topicId, String property, int status) throws SQLException;

    List<ReplyTopic> getReplyTopicsByTopicId(String topicId, int pageNumber, int pageSize) throws SQLException;

    List<Topic> getTopicListByCategory(String title, String categoryId, int pageNumber, int pageSize) throws SQLException;

    int getTopicCountByCategory(String title, String categoryId) throws SQLException;

    int getReplyTopicCountByTopicId(String topicId) throws SQLException;

    List<Topic> getPublicTopicsByUserId(String userId, int pageNumber, int pageSize) throws SQLException;

    int getPublicTopicCountByUserId(String userId) throws SQLException;

    List<Topic> getReplyTopicsByUserId(String userId, int pageNumber, int pageSize) throws SQLException;

    int getReplyTopicCountByUserId(String userId) throws SQLException;

    Topic checkUserPermission(String topicId, String userId) throws SQLException;
}
