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
        String sql = "SELECT Orders.sid,Orders.date,Orders.customerId,OrderDetails.itemCode,OrderDetails.qty,OrderDetails.unitPrice FROM Ordrs INNER JOIN OrderDetails IN Orders.sid=Orderdetails.sid WHERE Orders.sid=?;";
        ResultSet rst = SQLUtil.executeQuery(sql,id);
        ArrayList<CustomDTO> orderRecords = new ArrayList<>();

        while (rst.next()) {
            String sid = rst.getString(1);
            String date = rst.getString(2);
            String customerId = rst.getString(3);
            String itemCode = rst.getString(4);
            int qty = rst.getInt(5);
            BigDecimal unitPrice = rst.getBigDecimal(5);

            CustomDTO customDTO = new CustomDTO();
            customDTO.setOid(sid);
            customDTO.setOrderDate(LocalDate.now());
            customDTO.setCustomerId(customerId);
            customDTO.setItemCode(itemCode);
            customDTO.setQty(qty);
            customDTO.setUnitPrice(unitPrice);

            orderRecords.add(customDTO);
        }
        return orderRecords;
    }
}
