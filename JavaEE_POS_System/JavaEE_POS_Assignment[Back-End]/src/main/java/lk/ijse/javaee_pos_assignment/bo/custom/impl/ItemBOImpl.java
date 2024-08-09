package lk.ijse.javaee_pos_assignment.bo.custom.impl;



import lk.ijse.javaee_pos_assignment.bo.custom.ItemBO;
import lk.ijse.javaee_pos_assignment.dao.DAOFactory;
import lk.ijse.javaee_pos_assignment.dao.custom.impl.ItemDAOImpl;
import lk.ijse.javaee_pos_assignment.dto.ItemDTO;
import lk.ijse.javaee_pos_assignment.entity.Item;

import java.sql.Connection;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO<ItemDTO, Connection,String> {
    ItemDAOImpl itemDAO= DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ITEM);
    @Override
    public boolean saveItem(ItemDTO itemDTO, Connection connection) {
        return itemDAO.save(new Item(
                itemDTO.getId(),
                itemDTO.getName(),
                itemDTO.getType(),
                itemDTO.getPrice(),
                itemDTO.getQty()
        ),connection);
    }

    @Override
    public boolean updateItem(ItemDTO itemDTO, Connection connection) {
        return itemDAO.update(new Item(
                itemDTO.getId(),
                itemDTO.getName(),
                itemDTO.getType(),
                itemDTO.getPrice(),
                itemDTO.getQty()
        ),connection);
    }

    @Override
    public boolean deleteItem(String id, Connection connection) {
        return itemDAO.delete(id, connection);

    }

    @Override
    public ArrayList<ItemDTO> getAllItems(Connection connection) {
        ArrayList<Item> list = itemDAO.getAll(connection);
        ArrayList<ItemDTO> itemDTOS=new ArrayList<>();

        for (Item item : list) {
            itemDTOS.add(new ItemDTO(
                    item.getId(),
                    item.getName(),
                    item.getType(),
                    item.getPrice(),
                    item.getQty()
            ));

        }

        return itemDTOS;
    }

    @Override
    public ItemDTO searchItem(Connection connection, String id) {
        Item item = itemDAO.search(connection, id);
        if(item!=null){
            return new ItemDTO(
                    item.getId(),
                    item.getName(),
                    item.getType(),
                    item.getPrice(),
                    item.getQty()
            );
        }
        return null;
    }

    public ArrayList<ItemDTO> liveSearch(Connection connection, String name) {
        ArrayList<Item> items = itemDAO.liveSearch(connection, name);
        ArrayList<ItemDTO> dtoArrayList = new ArrayList<>();

        for (Item item : items) {
            dtoArrayList.add(new ItemDTO(
                    item.getId(),
                    item.getName(),
                    item.getType(),
                    item.getPrice(),
                    item.getQty()
            ));
        }

        return dtoArrayList;
    }
}
