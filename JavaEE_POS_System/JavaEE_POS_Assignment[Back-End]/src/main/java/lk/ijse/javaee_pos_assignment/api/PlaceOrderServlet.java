package lk.ijse.javaee_pos_assignment.api;


import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.javaee_pos_assignment.bo.BOFactory;
import lk.ijse.javaee_pos_assignment.bo.custom.impl.PlaceOrderBOImpl;
import lk.ijse.javaee_pos_assignment.dto.CustomerDTO;
import lk.ijse.javaee_pos_assignment.dto.ItemDTO;
import lk.ijse.javaee_pos_assignment.dto.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

@WebServlet(name = "placeOrder", urlPatterns = "/placeOrder")
public class PlaceOrderServlet extends HttpServlet {

    DataSource pool;
    PlaceOrderBOImpl placeOrderBO = BOFactory.getInstance().getBO(BOFactory.BOTypes.PLACE_ORDER);

    static Logger logger = LoggerFactory.getLogger(PlaceOrderServlet.class);

    @Override
    public void init() throws ServletException {
        try {
            pool = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/pos_assignment");
            logger.debug("DB Connection Initialized..");
//            System.out.println(pool.getConnection());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("option").equals("customer")) {
            try (Connection connection = pool.getConnection()) {
                ArrayList<CustomerDTO> customerIds = placeOrderBO.loadCustomerIds(connection);
                resp.setContentType("application/json");
                resp.getWriter().write(JsonbBuilder.create().toJson(customerIds));

            } catch (Exception e) {

            }

        } else if (req.getParameter("option").equals("item")) {
            try (Connection connection = pool.getConnection()) {
                ArrayList<ItemDTO> itemDTOS = placeOrderBO.loadItemIds(connection);
                resp.setContentType("application/json");
                resp.getWriter().write(JsonbBuilder.create().toJson(itemDTOS));

            } catch (Exception e) {

            }

        } else if (req.getParameter("option").equals("customerSearch")) {
            try (Connection connection = pool.getConnection()) {
                CustomerDTO customerDTOS = placeOrderBO.searchCustomer(connection, req.getParameter("id"));
                resp.setContentType("application/json");
                resp.getWriter().write(JsonbBuilder.create().toJson(customerDTOS));

            } catch (Exception e) {

            }

        } else if (req.getParameter("option").equals("itemSearch")) {
            try (Connection connection = pool.getConnection()) {
                ItemDTO itemDTO = placeOrderBO.searchItem(connection, req.getParameter("id"));
                resp.setContentType("application/json");
                resp.getWriter().write(JsonbBuilder.create().toJson(itemDTO));

            } catch (Exception e) {

            }

        } else if (req.getParameter("option").equals("generateNextOrderId")) {
            try (Connection connection = pool.getConnection()) {
                OrderDTO orderDTO = placeOrderBO.generateNextOrderId(connection);
//                System.out.println("g "+orderDTO);
                resp.setContentType("application/json");
                resp.getWriter().write(JsonbBuilder.create().toJson(orderDTO));

            } catch (Exception e) {
                System.out.println(e);
            }

        }


    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try (Connection connection = pool.getConnection()) {
            OrderDTO orderDTO = JsonbBuilder.create().fromJson(req.getReader(), OrderDTO.class);
            System.out.println(orderDTO);

            if (placeOrderBO.placeOrder(orderDTO, connection)) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("Order placed successfully !!");
                logger.info("Order placed successfully !!");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
                resp.getWriter().write("Order placed failed !!");
                logger.error("Order placed failed !!");
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
