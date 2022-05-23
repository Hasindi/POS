package dao.custom;

import dao.CrudDAO;
import model.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO extends CrudDAO<ItemDTO,String> {
    public ArrayList<ItemDTO> getItemsFromPrice(double price) throws ClassNotFoundException, SQLException;

}
