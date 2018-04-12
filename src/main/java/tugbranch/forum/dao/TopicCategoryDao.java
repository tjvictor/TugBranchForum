package tugbranch.forum.dao;

import tugbranch.forum.model.TopicCategory;

import java.sql.SQLException;
import java.util.List;

public interface TopicCategoryDao {

    List<TopicCategory> getTopicCategory() throws SQLException;

    TopicCategory getTopicCategoryById(String id) throws SQLException;
}
