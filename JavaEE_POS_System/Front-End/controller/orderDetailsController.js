$("#showOrders").click(function(){
    loadOrderDetailsOnTable();

})

function loadOrderDetailsOnTable(){
    
    for(let i=0; i<order.orderDetails.length; i++){
        let Item=searchItem(order.orderDetails[i].itemId);
        console.log("this is item",Item);

        let tRow=`<tr> <td> ${order.orderDetails[i].orderId}</td>
        <td> ${order.orderDetails[i].itemId}</td>
        <td> ${Item.name}</td>
        <td> ${Item.unitPrice}</td>
        <td> ${order.orderDetails[i].qty}</td>
        <td> ${order.orderDetails[i].total}</td></tr>`;
    
        $("#orderDetailsTbody").append(tRow);
    }
}