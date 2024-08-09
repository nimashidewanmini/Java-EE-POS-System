package lk.ijse.javaee_pos_assignment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private String id;
    private String name;
    private String type;
    private double price;
    private int qty;
}

