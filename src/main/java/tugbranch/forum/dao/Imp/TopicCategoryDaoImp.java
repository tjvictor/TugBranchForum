package tugbranch.forum.dao.Imp;

import tugbranch.forum.dao.TopicCategoryDao;
import tugbranch.forum.model.TopicCategory;

import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class TopicCategoryDaoImp extends BaseDao implements TopicCategoryDao {
    @Override
    public List<TopicCategory> getTopicCategory() throws SQLException {
        List<TopicCategory> items = new ArrayList<TopicCategory>();
        String selectSql = String.format("SELECT Id, Name, Orders FROM TopicCategory order by Orders;");

        try (Connection connection = DriverManager.getConnection(dbConnectString)) {
            try (Statement stmt = connection.createStatement()) {
                try(ResultSet rs = stmt.executeQuery(selectSql)) {
                    while(rs.next()){
                        int i = 1;
                        TopicCategory item = new TopicCategory();
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
