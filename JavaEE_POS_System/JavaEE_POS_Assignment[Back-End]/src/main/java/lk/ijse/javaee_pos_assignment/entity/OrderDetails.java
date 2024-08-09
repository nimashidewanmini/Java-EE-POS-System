package lk.ijse.javaee_pos_assignment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {
    private String orderId;
    private String itemId;
    private int qty ;
    private Double total;
}
