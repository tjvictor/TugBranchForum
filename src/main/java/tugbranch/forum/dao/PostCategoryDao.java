package tugbranch.forum.dao;

import tugbranch.forum.model.PostCategory;

import java.sql.SQLException;
import java.util.List;

public interface PostCategoryDao {

    List<PostCategory> getTopicCategory() throws SQLException;
}
