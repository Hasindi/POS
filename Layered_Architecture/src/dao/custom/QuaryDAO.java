package dao.custom;

import java.sql.SQLException;

public interface QuaryDAO {
    public void searchOrderByOrderId(String id) throws SQLException,ClassNotFoundException;
}
