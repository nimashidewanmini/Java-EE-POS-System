package lk.ijse.javaee_pos_assignment.dao.custom.impl;


import lk.ijse.javaee_pos_assignment.dao.custom.OrderDAO;
import lk.ijse.javaee_pos_assignment.entity.Order;
import lk.ijse.javaee_pos_assignment.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public boolean save(Order order, Connection connection) {

        try {
            String sql="insert into orders values (?,?,?)";
            return CrudUtil.execute(sql,connection,
                    order.getOrderId(),
                    order.getCustomerId(),
                    order.getDate());
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Order order, Connection connection) {
        return false;
    }

    @Override
    public boolean delete(String s, Connection connection) {
        return false;
    }

    @Override
    public ArrayList<Order> getAll(Connection connection) {
        return null;
    }

    @Override
    public Order search(Connection connection, String s) {
        return null;
    }

    @Override
    public Order generateNextOrderId(Connection connection) {

        try {

            String sql="SELECT orderId,customerId,date FROM orders ORDER BY orderId DESC LIMIT 1";
            ResultSet resultSet= CrudUtil.execute(sql,connection);
            if (resultSet.next()){
                return new Order(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
