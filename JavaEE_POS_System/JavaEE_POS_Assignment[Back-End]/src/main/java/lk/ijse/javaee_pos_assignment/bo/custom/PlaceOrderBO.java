package lk.ijse.javaee_pos_assignment.bo.custom;



import lk.ijse.javaee_pos_assignment.bo.SuperBO;

import java.util.ArrayList;

public interface PlaceOrderBO <Cu,I,O,C,ID> extends SuperBO {

   ArrayList <Cu> loadCustomerIds(C c);
   ArrayList <I> loadItemIds(C c);

   Cu searchCustomer(C c,ID id);
   I searchItem(C c,ID id);
    boolean placeOrder(O o,C c);

    O generateNextOrderId(C c);

}
