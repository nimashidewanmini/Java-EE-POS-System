package lk.ijse.javaee_pos_assignment.dao.custom.impl;



import lk.ijse.javaee_pos_assignment.dao.custom.CustomerDAO;
import lk.ijse.javaee_pos_assignment.entity.Customer;
import lk.ijse.javaee_pos_assignment.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public boolean save(Customer customer, Connection connection) {

        try {
            String sql = "insert into customer values (?,?,?,?)";
            return CrudUtil.execute(sql, connection,
                    customer.getId(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getSalary()
            );

        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Customer customer, Connection connection) {
        try {
            String sql="update customer set name=?,address=?,salary=? where id=?";
           return CrudUtil.execute(sql,connection,
                    customer.getName(),
                    customer.getAddress(),
                    customer.getSalary(),
                    customer.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id, Connection connection) {
        try {
            String sql = "Delete from customer where id=?";
            return CrudUtil.execute(sql, connection, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Customer> getAll(Connection connection) {
        ArrayList<Customer> list = new ArrayList<>();
        try {
            String sql = "select * from customer";
            try (ResultSet resultSet = CrudUtil.execute(sql, connection)) {
                System.out.println(resultSet);
                while (resultSet.next()) {
                    list.add(new Customer(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)
                    ));
                }
            }
            return list;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public Customer search(Connection connection, String id) {
        System.out.println(id);

        try {
            String sql = "select * from customer where id=?";
            ResultSet resultSet = CrudUtil.execute(sql, connection, id);
            while (resultSet.next()) {
                return new Customer(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
            }

        } catch (SQLException e) {
            System.out.println("dd" + e);
            throw new RuntimeException(e);

        }
        return null;
    }

    public ArrayList<Customer> liveSearch(Connection connection, String name){
        ArrayList<Customer> list = new ArrayList<>();

        try {
            String sql="SELECT * FROM customer WHERE LOWER(name) LIKE ?";
           ResultSet resultSet= CrudUtil.execute(sql,connection,"%" + name.toLowerCase() + "%");
           while (resultSet.next()){
              list.add(new Customer(
                      resultSet.getString(1),
                      resultSet.getString(2),
                      resultSet.getString(3),
                      resultSet.getString(4)
              ));
           }
           return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
