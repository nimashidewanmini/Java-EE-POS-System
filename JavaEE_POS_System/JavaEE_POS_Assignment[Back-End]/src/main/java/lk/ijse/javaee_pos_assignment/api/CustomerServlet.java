package lk.ijse.javaee_pos_assignment.api;


import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.javaee_pos_assignment.bo.BOFactory;
import lk.ijse.javaee_pos_assignment.bo.custom.impl.CustomerBOImpl;
import lk.ijse.javaee_pos_assignment.dto.CustomerDTO;
import lk.ijse.javaee_pos_assignment.util.DataValidateController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "customer", urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {


    CustomerBOImpl customerBO = BOFactory.getInstance().getBO(BOFactory.BOTypes.CUSTOMER);

    static Logger logger = LoggerFactory.getLogger(CustomerServlet.class);

    DataSource pool;


    public void init() {

        try {
            pool = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/pos_assignment");
            System.out.println(pool.getConnection());
            logger.debug("DB Connection Initialized: {}",pool);
        } catch (Exception e) {
            logger.error("DB Connection Initialized failed",e);
            System.out.println(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = pool.getConnection()) {

            CustomerDTO customerDTO = JsonbBuilder.create().fromJson(req.getReader(), CustomerDTO.class);

            if (customerDTO.getId() != null && customerDTO.getName() != null && customerDTO.getAddress() != null && customerDTO.getSalary() != null) {
                if (DataValidateController.customerIdValidate(customerDTO.getId())) {

                    if (DataValidateController.customerNameValidate(customerDTO.getName())) {
                        if (DataValidateController.customerAddressValidate(customerDTO.getAddress())) {

                            if (DataValidateController.customerSalaryValidate(customerDTO.getSalary())) {

                                CustomerDTO searchCustomer = customerBO.searchCustomer(connection, customerDTO.getId());

                                if (searchCustomer != null) {
                                    resp.getWriter().write("Customer Id Already exits !!");
                                    resp.setStatus(HttpServletResponse.SC_CONFLICT);

                                } else {
                                    if (customerBO.saveCustomer(customerDTO, connection)) {
                                        resp.setStatus(HttpServletResponse.SC_CREATED);
                                        resp.getWriter().write("Customer saved successfully !!");
                                        logger.info("Customer saved successfully");
                                    } else {
                                        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                                        resp.getWriter().write("Failed to save customer !!");
                                        logger.error("Failed to save customer !!");
                                    }
                                }

                            } else {
                                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                                resp.getWriter().write("Customer salary doesn't match !!");
                                logger.error("Customer salary doesn't match !!");
                            }

                        } else {
                            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            resp.getWriter().write("Customer address doesn't match !!");
                            logger.error("Customer address doesn't match !!");
                        }

                    } else {
                        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        resp.getWriter().write("Customer name doesn't match !!");
                        logger.error("Customer name doesn't match !!");
                    }

                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("Customer Id doesn't match !!");
                    logger.error("Customer Id doesn't match !!");
                }

            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("NO Data to proceed !!");
                logger.error("NO Data to proceed !!");
            }

        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            resp.getWriter().write("Server Error");
            logger.error("Server Error");
            System.out.println(e);
        }

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {


        if (request.getParameter("method").equals("getAll")) {
            try (Connection con = pool.getConnection()) {
                ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomers(con);
                if (allCustomers != null) {
                    response.setContentType("application/json");
                    response.setStatus(HttpServletResponse.SC_CREATED);
                    response.getWriter().write(JsonbBuilder.create().toJson(allCustomers));

                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
                    response.getWriter().write("Failed to retrieve customers !!");
                    logger.error("Failed to retrieve customers !!");
                }
            } catch (Exception e) {
                System.out.println(e);
            }

        } else if (request.getParameter("method").equals("search")) {
//            System.out.println(request.getParameter("name"));
            try (Connection connection = pool.getConnection()) {
                ArrayList<CustomerDTO> customerDTOS = customerBO.liveSearch(connection, request.getParameter("name"));
                response.setContentType("application/json");
                response.getWriter().write(JsonbBuilder.create().toJson(customerDTOS));
//                System.out.println(customerDTOS);

            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = pool.getConnection()) {
//            System.out.println("id"+req.getParameter("id"));

            if (req.getParameter("id") == null || req.getParameter("id").isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("NO Data TO Proceed !!");
                return;
            }

            if (DataValidateController.customerIdValidate(req.getParameter("id"))) {
                CustomerDTO searchCustomer = customerBO.searchCustomer(connection, req.getParameter("id"));
                System.out.println("ddd" + searchCustomer);

                if (searchCustomer != null) {
                    if (customerBO.deleteCustomer(req.getParameter("id"), connection)) {
                        resp.setStatus(HttpServletResponse.SC_CREATED);
                        resp.getWriter().write("Customer Deleted successfully !!");
                        logger.info("Customer Deleted successfully !!");
                    } else {
                        resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
                        resp.getWriter().write("Failed to delete customer !!");
                        logger.error("Failed to delete customer !!");
                    }

                } else {
                    resp.setStatus(HttpServletResponse.SC_CONFLICT);
                    resp.getWriter().write("This Customer id doesn't exits  !!");
                    logger.error("This Customer id doesn't exits  !!");
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Customer Id doesn't match !!");
                logger.error("Customer Id doesn't match !!");
            }
        } catch (Exception e) {
            System.out.println(e);
            resp.getWriter().write(HttpServletResponse.SC_BAD_GATEWAY);
            resp.getWriter().write("Server Error !!");
            logger.error("Server Error !!");
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = pool.getConnection()) {
            CustomerDTO customerDTO = JsonbBuilder.create().fromJson(req.getReader(), CustomerDTO.class);
            System.out.println("update" + customerDTO);

            if (customerDTO.getId() != null && customerDTO.getName() != null && customerDTO.getAddress() != null && customerDTO.getSalary() != null) {

                if (DataValidateController.customerIdValidate(customerDTO.getId())) {

                    if (DataValidateController.customerNameValidate(customerDTO.getName())) {

                        if (DataValidateController.customerAddressValidate(customerDTO.getAddress())) {

                            if (DataValidateController.customerSalaryValidate(customerDTO.getSalary())) {

                                CustomerDTO searchCustomer = customerBO.searchCustomer(connection, customerDTO.getId());

                                if (searchCustomer != null) {
                                    if (customerBO.updateCustomer(customerDTO, connection)) {
                                        resp.setStatus(HttpServletResponse.SC_CREATED);
                                        resp.getWriter().write("Customer updated successfully !!");
                                        logger.info("Customer updated successfully !!");
                                    } else {
                                        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                                        resp.getWriter().write("Failed to update customer !!");
                                        logger.error("Failed to update customer !!");
                                    }
                                } else {
                                    resp.setStatus(HttpServletResponse.SC_CONFLICT);
                                    resp.getWriter().write("This Customer id doesn't exits  !!");
                                    logger.error("This Customer id doesn't exits  !!");
                                }

                            } else {
                                resp.setStatus(HttpServletResponse.SC_CONFLICT);
                                resp.getWriter().write("This Customer salary doesn't match  !!");
                                logger.error("This Customer salary doesn't match  !!");
                            }

                        } else {
                            resp.setStatus(HttpServletResponse.SC_CONFLICT);
                            resp.getWriter().write("This Customer address doesn't match  !!");
                            logger.error("This Customer address doesn't match  !!");
                        }
                    } else {
                        resp.setStatus(HttpServletResponse.SC_CONFLICT);
                        resp.getWriter().write("This Customer name doesn't match  !!");
                        logger.error("This Customer name doesn't match  !!");
                    }

                } else {
                    resp.setStatus(HttpServletResponse.SC_CONFLICT);
                    resp.getWriter().write("This Customer id doesn't match  !!");
                    logger.error("This Customer id doesn't match  !!");
                }

            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("NO Data to proceed !!");
                logger.error("NO Data to proceed !!");
            }

        } catch (Exception e) {
            System.out.println(e);
            resp.getWriter().write(HttpServletResponse.SC_BAD_GATEWAY);
            resp.getWriter().write("Server Error !!");
            logger.error("Server Error !!");
        }

    }


}