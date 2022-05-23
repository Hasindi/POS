package dao.custom.impl;

import dao.SQLUtil;
import dao.custom.QuaryDAO;
import model.CustomDTO;
import model.CustomerDTO;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class QueryDAOImpl implements QuaryDAO {
    public ArrayList<CustomDTO> searchOrderByOrderId(String id) throws SQLException,ClassNotFoundException{
        ResultSet rst = SQLUtil.executeQuery("SELECT Orders.sid,Orders.date,Orders.customerId,OrderDetails.itemCode,OrderDetails.qty,OrderDetails.unitPrice FROM Ordrs INNER JOIN OrderDetails IN Orders.sid=Orderdetails.sid WHERE Orders.sid=?;",id);
        ArrayList<CustomDTO> orderRecords = new ArrayList<>();

        while (rst.next()) {
            orderRecords.add(new CustomDTO(rst.getString(1),LocalDate.parse(rst.getString(2)),
                    rst.getString(3),rst.getString(4),rst.getInt(5), rst.getBigDecimal(5)));
        }
        return orderRecords;
    }
}
