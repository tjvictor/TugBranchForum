package tugbranch.forum.dao.Imp;

import tugbranch.forum.dao.PostCategoryDao;
import tugbranch.forum.model.PostCategory;

import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostCategoryDaoImp extends BaseDao implements PostCategoryDao {
    @Override
    public List<PostCategory> getTopicCategory() throws SQLException {
        List<PostCategory> items = new ArrayList<PostCategory>();
        String selectSql = String.format("SELECT Id, Name, Orders FROM PostCategory order by Orders;");

        try (Connection connection = DriverManager.getConnection(dbConnectString)) {
            try (Statement stmt = connection.createStatement()) {
                try(ResultSet rs = stmt.executeQuery(selectSql)) {
                    while(rs.next()){
                        int i = 1;
                        PostCategory item = new PostCategory();
                        item.setId(rs.getString(i++));
                        item.setName(rs.getString(i++));
                        item.setOrders(rs.getInt(i++));
                        items.add(item);
                    }
                }
            }
        }

        return items;
    }
}
