package lk.ijse.javaee_pos_assignment.dao.custom;



import lk.ijse.javaee_pos_assignment.dao.CrudDAO;
import lk.ijse.javaee_pos_assignment.entity.Customer;

import java.sql.Connection;

public interface CustomerDAO extends CrudDAO<Customer, Connection,String> {

}
