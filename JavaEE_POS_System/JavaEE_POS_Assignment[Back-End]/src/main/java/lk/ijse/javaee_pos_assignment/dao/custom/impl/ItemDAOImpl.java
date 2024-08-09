package lk.ijse.javaee_pos_assignment.dao.custom.impl;


import lk.ijse.javaee_pos_assignment.dao.custom.ItemDAO;
import lk.ijse.javaee_pos_assignment.entity.Item;
import lk.ijse.javaee_pos_assignment.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean save(Item item, Connection connection) {
        try {
            String sql="insert into item values (?,?,?,?,?)";
            return CrudUtil.execute(sql,connection,
                    item.getId(),
                    item.getName(),
                    item.getType(),
                    item.getPrice(),
                    item.getQty());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Item item, Connection connection) {
        try {
            String sql="update item set name=?,type=?,price=?,qty=? where id=?";
            return CrudUtil.execute(sql,connection,
                    item.getName(),
                    item.getType(),
                    item.getPrice(),
                    item.getQty(),
                    item.getId()
                    );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id, Connection connection) {
        try {
            String sql = "Delete from item where id=?";
            return CrudUtil.execute(sql, connection, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Item> getAll(Connection connection) {
        ArrayList<Item> items=new ArrayList<>();
        try {
             String sql="select * from item";
             ResultSet resultSet=CrudUtil.execute(sql,connection);
             while (resultSet.next()){
                 items.add(new Item(
                         resultSet.getString(1),
                         resultSet.getString(2),
                         resultSet.getString(3),
                         resultSet.getDouble(4),
                         resultSet.getInt(5)
                 ));
             }
             return items;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Item search(Connection connection, String id) {
        try {
            String sql = "select * from item where id=?";
            ResultSet resultSet = CrudUtil.execute(sql, connection, id);
            if (resultSet.next()){
                return new Item(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getInt(5)
                );
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public ArrayList<Item> liveSearch(Connection connection, String name){
        ArrayList<Item> list = new ArrayList<>();

        try {
            String sql="SELECT * FROM item WHERE LOWER(name) LIKE ?";
            ResultSet resultSet= CrudUtil.execute(sql,connection,"%" + name.toLowerCase() + "%");
            while (resultSet.next()){
                list.add(new Item(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getInt(5)
                ));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
