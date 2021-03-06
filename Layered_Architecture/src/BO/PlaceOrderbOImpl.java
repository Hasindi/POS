package BO;

import dao.CrudDAO;
import dao.custom.*;
import dao.custom.impl.*;
import db.DBConnection;
import model.CustomerDTO;
import model.ItemDTO;
import model.OrderDTO;
import model.OrderDetailDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderbOImpl {
    private ItemDAO itemDAO = new ItemDAOImpl();
    private CustomerDAO customerDAO = new CustomerDAOImpl();
    private OrderDAO orderDAO = new OrderDAOImpl();
    private OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();
    private QuaryDAO queryDAO = new QueryDAOImpl();


    public boolean perchesOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException {

            Connection connection = DBConnection.getDbConnection().getConnection();

            /*if order id already exist*/
            if (orderDAO.exist(orderId)) {

            }

            connection.setAutoCommit(false);

            boolean save = orderDAO.save(new OrderDTO(orderId,orderDate,customerId));

            if (!save) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            CrudDAO<OrderDetailDTO,String> orderDetailDAO = new OrderDetailDAOImpl();

            for (OrderDetailDTO detail : orderDetails) {

                boolean save1 = orderDetailDAO.save(detail);

                if (!save1) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }

//                //Search & Update Item
                ItemDTO item = null; //findItem(detail.getItemCode());
                item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());

                //Update item
                boolean update = itemDAO.update(new ItemDTO(item.getCode(),item.getDescription(),item.getUnitPrice(),item.getQtyOnHand()));

                if (!update) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }

            connection.commit();
            connection.setAutoCommit(true);
            return true;


        //return false;
    }

    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
       return customerDAO.search(id);
    }

    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.search(code);
    }

    public boolean checkItemIsAveilable(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(code);
    }

    public boolean checkCustomerIsAveilable(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    public String genarateNewOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewId();
    }

    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        return customerDAO.getAll();
    }

    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        return itemDAO.getAll();
    }
}
