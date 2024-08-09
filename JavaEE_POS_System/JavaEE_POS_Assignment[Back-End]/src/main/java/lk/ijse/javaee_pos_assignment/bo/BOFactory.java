package lk.ijse.javaee_pos_assignment.bo;


import lk.ijse.javaee_pos_assignment.bo.custom.impl.CustomerBOImpl;
import lk.ijse.javaee_pos_assignment.bo.custom.impl.ItemBOImpl;
import lk.ijse.javaee_pos_assignment.bo.custom.impl.PlaceOrderBOImpl;

public class BOFactory {

    private static BOFactory boFactory;

    private BOFactory(){

    }

    public static BOFactory getInstance(){
        if (boFactory==null){
           return boFactory=new BOFactory();
        }else{
            return boFactory;
        }
    }


    public enum BOTypes{
        CUSTOMER,ITEM,PLACE_ORDER
    }

public <T extends SuperBO > T getBO(BOTypes boTypes){
        switch (boTypes){
            case CUSTOMER:
                return (T) new CustomerBOImpl();

            case ITEM:
                return (T) new ItemBOImpl();

            case PLACE_ORDER:
                return (T) new PlaceOrderBOImpl();


            default:
                return null;
        }
}
}
