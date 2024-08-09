package lk.ijse.javaee_pos_assignment.dao.custom.impl;



import lk.ijse.javaee_pos_assignment.dao.custom.OrderDetailsDAO;
import lk.ijse.javaee_pos_assignment.entity.OrderDetails;
import lk.ijse.javaee_pos_assignment.util.CrudUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    @Override
    public boolean save(OrderDetails orderDetails, Connection connection) {
        try {
            String sql="insert into order_detail values (?,?,?,?)";
            return CrudUtil.execute(sql,connection,
                    orderDetails.getOrderId(),
                    orderDetails.getItemId(),
                    orderDetails.getQty(),
                    orderDetails.getTotal()
                    );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(OrderDetails orderDetails, Connection connection) {
        return false;
    }

    @Override
    public boolean delete(String s, Connection connection) {
        return false;
    }

    @Override
    public ArrayList<OrderDetails> getAll(Connection connection) {
        return null;
    }

    @Override
    public OrderDetails search(Connection connection, String s) {
        return null;
    }
}
