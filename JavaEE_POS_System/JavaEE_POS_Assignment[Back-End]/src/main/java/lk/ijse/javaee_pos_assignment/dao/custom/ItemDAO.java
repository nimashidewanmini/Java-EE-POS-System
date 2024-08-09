package lk.ijse.javaee_pos_assignment.dao.custom;



import lk.ijse.javaee_pos_assignment.dao.CrudDAO;
import lk.ijse.javaee_pos_assignment.entity.Item;

import java.sql.Connection;

public interface ItemDAO extends CrudDAO<Item,Connection,String> {
}
