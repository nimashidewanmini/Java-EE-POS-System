package lk.ijse.javaee_pos_assignment.api;


import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.javaee_pos_assignment.bo.BOFactory;
import lk.ijse.javaee_pos_assignment.bo.custom.impl.ItemBOImpl;
import lk.ijse.javaee_pos_assignment.dto.ItemDTO;
import lk.ijse.javaee_pos_assignment.util.DataValidateController;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

@WebServlet(name = "item", urlPatterns = "/item")
public class ItemServlet extends HttpServlet {

    DataSource pool;
    ItemBOImpl itemBO = BOFactory.getInstance().getBO(BOFactory.BOTypes.ITEM);

    @Override
    public void init() throws ServletException {
        try {
            pool = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/TestDB");
//            System.out.println(pool.getConnection());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("method").equals("getAll")) {
            try (Connection connection = pool.getConnection()) {
                ArrayList<ItemDTO> allItems = itemBO.getAllItems(connection);

                if (allItems != null) {
                    resp.setContentType("application/json");
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    resp.getWriter().write(JsonbBuilder.create().toJson(allItems));
                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
                    resp.getWriter().write("Failed to retrieve customers !!");
                }

            } catch (Exception e) {
                System.out.println(e);
            }

        } else if (req.getParameter("method").equals("search")) {
            try (Connection connection = pool.getConnection()) {
                ArrayList<ItemDTO> itemDTOS = itemBO.liveSearch(connection, req.getParameter("name"));
                resp.setContentType("application/json");
                resp.getWriter().write(JsonbBuilder.create().toJson(itemDTOS));

            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try (Connection connection = pool.getConnection()) {

            ItemDTO itemDTO = JsonbBuilder.create().fromJson(req.getReader(), ItemDTO.class);

            double neww = itemDTO.getPrice();

            if (itemDTO.getId() != null && itemDTO.getName() != null && itemDTO.getType() != null) {
                if (DataValidateController.itemIdValidate(itemDTO.getId())) {

                    if (DataValidateController.itemNameValidate(itemDTO.getName())) {
                        if (DataValidateController.itemTypeValidate(itemDTO.getType())) {

                            ItemDTO searchItem = itemBO.searchItem(connection, itemDTO.getId());


                            if (searchItem != null) {
                                resp.setStatus(HttpServletResponse.SC_CONFLICT);
                                resp.getWriter().write("Item Id Already exits !!");

                            } else {
                                if (itemBO.saveItem(itemDTO, connection)) {
                                    resp.setStatus(HttpServletResponse.SC_CREATED);
                                    resp.getWriter().write("Item saved successfully !!");

                                } else {
                                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                                    resp.getWriter().write("Failed to save item !!");
                                }

                            }

                        } else {
                            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            resp.getWriter().write("Item type doesn't match !!");

                        }
                    } else {
                        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        resp.getWriter().write("Item name doesn't match !!");

                    }

                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("Item id doesn't match !!");
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("NO Data to proceed !!");
            }


        } catch (Exception e) {
            System.out.println(e);
        }

    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = pool.getConnection()) {
            System.out.println(req.getParameter("id"));


            if (req.getParameter("id") == null || req.getParameter("id").isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("NO Data TO Proceed !!");
                return;
            }


            if (DataValidateController.itemIdValidate(req.getParameter("id"))) {
                ItemDTO searchItem = itemBO.searchItem(connection, req.getParameter("id"));
                System.out.println("delete search" + searchItem);
                if (searchItem != null) {
                    if (itemBO.deleteItem(req.getParameter("id"), connection)) {
                        resp.setStatus(HttpServletResponse.SC_CREATED);
                        resp.getWriter().write("Item Deleted successfully !!");
                    } else {
                        resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
                        resp.getWriter().write("Failed to delete item !!");
                    }

                } else {
                    resp.setStatus(HttpServletResponse.SC_CONFLICT);
                    resp.getWriter().write("Item Id doesn't exits !!");
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Item id doesn't match !!");
            }


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = pool.getConnection()) {
            ItemDTO itemDTO = JsonbBuilder.create().fromJson(req.getReader(), ItemDTO.class);
            System.out.println("update " + itemDTO);

            if (itemDTO.getId() != null && itemDTO.getName() != null && itemDTO.getType() != null && itemDTO.getPrice() != 0 && itemDTO.getQty() != 0) {
                if (DataValidateController.itemIdValidate(itemDTO.getId())) {

                    if (DataValidateController.itemNameValidate(itemDTO.getName())) {

                        if (DataValidateController.itemTypeValidate(itemDTO.getType())) {

                            ItemDTO searchItem = itemBO.searchItem(connection, itemDTO.getId());

                            if (searchItem != null) {
                                if (itemBO.updateItem(itemDTO, connection)) {
                                    resp.setStatus(HttpServletResponse.SC_CREATED);
                                    resp.getWriter().write("Item updated successfully !!");

                                } else {
                                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                                    resp.getWriter().write("Failed to update item !!");
                                }

                            } else {
                                resp.setStatus(HttpServletResponse.SC_CONFLICT);
                                resp.getWriter().write("Item Id doesn't exits !!");
                            }

                        } else {
                            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            resp.getWriter().write("Item type doesn't match !!");
                        }

                    } else {
                        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        resp.getWriter().write("Item name doesn't match !!");
                    }

                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("Item id doesn't match !!");
                }

            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("NO Data to proceed !!");
            }


        } catch (Exception e) {
            System.out.println(e);
            resp.getWriter().write(HttpServletResponse.SC_BAD_GATEWAY);
        }
    }
}
