package lk.ijse.javaee_pos_assignment.dao;


import lk.ijse.javaee_pos_assignment.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.javaee_pos_assignment.dao.custom.impl.ItemDAOImpl;
import lk.ijse.javaee_pos_assignment.dao.custom.impl.OrderDAOImpl;
import lk.ijse.javaee_pos_assignment.dao.custom.impl.OrderDetailsDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){

    }

    public static DAOFactory getInstance(){
        if (daoFactory==null){
            return daoFactory=new DAOFactory();
        }else {
            return daoFactory;
        }
    }

    public enum DAOTypes{
        CUSTOMER,ITEM,ORDER,ORDER_DETAILS
    }

    public  <T extends SuperDAO>T getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case CUSTOMER:
                return (T) new CustomerDAOImpl();

            case ITEM:
                return (T) new ItemDAOImpl();

            case ORDER:
                return (T) new OrderDAOImpl();

            case ORDER_DETAILS:
                return (T) new OrderDetailsDAOImpl();

            default:
                return null;
        }

    }
}
