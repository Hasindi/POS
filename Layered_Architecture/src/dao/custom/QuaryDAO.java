package dao.custom;

import model.CustomDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QuaryDAO {
    ArrayList<CustomDTO> searchOrderByOrderId(String id) throws SQLException,ClassNotFoundException;
}
