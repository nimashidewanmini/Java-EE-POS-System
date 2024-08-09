package lk.ijse.javaee_pos_assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDTO {
    private String orderId;
    private String itemId;
    private int qty;
    private double total;
}
