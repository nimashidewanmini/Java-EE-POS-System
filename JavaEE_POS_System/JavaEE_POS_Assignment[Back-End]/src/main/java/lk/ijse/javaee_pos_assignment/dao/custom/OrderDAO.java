package lk.ijse.javaee_pos_assignment.dao.custom;



import lk.ijse.javaee_pos_assignment.dao.CrudDAO;
import lk.ijse.javaee_pos_assignment.entity.Order;

import java.sql.Connection;

public interface OrderDAO extends CrudDAO<Order, Connection,String> {
    Order generateNextOrderId(Connection connection);
}
