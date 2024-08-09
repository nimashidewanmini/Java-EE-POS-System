package lk.ijse.javaee_pos_assignment.bo.custom.impl;



import lk.ijse.javaee_pos_assignment.bo.custom.CustomerBO;
import lk.ijse.javaee_pos_assignment.dao.DAOFactory;
import lk.ijse.javaee_pos_assignment.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.javaee_pos_assignment.dto.CustomerDTO;
import lk.ijse.javaee_pos_assignment.entity.Customer;

import java.sql.Connection;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO<CustomerDTO, Connection, String> {

    CustomerDAOImpl customerDAO = DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public boolean saveCustomer(CustomerDTO customerDTO, Connection connection) {
        return customerDAO.save(new Customer(
                customerDTO.getId(),
                customerDTO.getName(),
                customerDTO.getAddress(),
                customerDTO.getSalary()
        ), connection);
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO, Connection connection) {
        return customerDAO.update(new Customer(
                customerDTO.getId(),
                customerDTO.getName(),
                customerDTO.getAddress(),
                customerDTO.getSalary()
        ),connection);
    }

    @Override
    public boolean deleteCustomer(String id, Connection connection) {
        return customerDAO.delete(id, connection);
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers(Connection connection) {
        ArrayList<Customer> all = customerDAO.getAll(connection);
        ArrayList<CustomerDTO> dtoArrayList = new ArrayList<>();

        for (Customer customer : all) {
            dtoArrayList.add(new CustomerDTO(
                    customer.getId(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getSalary()
            ));
        }
        return dtoArrayList;

    }

    @Override
    public CustomerDTO searchCustomer(Connection connection, String id) {
        Customer customer = customerDAO.search(connection, id);
        System.out.println("bo" + customer);
        if (customer != null) {
            return new CustomerDTO(
                    customer.getId(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getSalary()
            );

        }
        return null;
    }

    public ArrayList<CustomerDTO> liveSearch(Connection connection, String name) {
        ArrayList<Customer> customers = customerDAO.liveSearch(connection, name);
        ArrayList<CustomerDTO> dtoArrayList = new ArrayList<>();

        for (Customer customer : customers) {
            dtoArrayList.add(new CustomerDTO(
                    customer.getId(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getSalary()
            ));
        }

return dtoArrayList;
    }
}
