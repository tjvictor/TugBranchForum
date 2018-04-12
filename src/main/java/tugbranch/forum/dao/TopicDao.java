package tugbranch.forum.dao;

import tugbranch.forum.model.Topic;

import java.sql.SQLException;

public interface TopicDao {

    void addTopic(Topic item) throws SQLException;

    Topic getTopicById(String topicId, boolean topicReply) throws SQLException;
}
