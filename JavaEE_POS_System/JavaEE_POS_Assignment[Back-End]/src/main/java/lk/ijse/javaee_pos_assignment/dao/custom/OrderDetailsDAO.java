package lk.ijse.javaee_pos_assignment.dao.custom;



import lk.ijse.javaee_pos_assignment.dao.CrudDAO;
import lk.ijse.javaee_pos_assignment.entity.OrderDetails;

import java.sql.Connection;

public interface OrderDetailsDAO extends CrudDAO<OrderDetails, Connection,String> {
}
