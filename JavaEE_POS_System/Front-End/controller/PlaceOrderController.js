
let addToCartArray = [];
let newTotal = 0;
let total;

window.onload = function() {
    loadCustomerIds();
    loadItemIds();
    loadGenerateNextOrderId();

};



function loadGenerateNextOrderId(){
    $.ajax({
        url: "http://localhost:8080/app1/placeOrder?option=generateNextOrderId",
        method: "GET",
        contentType: "application/json",
        success: function (response) {
            console.log(response);
            $("#orderId").val(splitOrderId(response.orderId));

        },
        error: function (jqXHR) {
            console.log(jqXHR);
        }

    })
}

function loadCustomerIds() {
    $("#customerIdCmb").empty();
    $.ajax({
        url: "http://localhost:8080/app1/placeOrder?option=customer",
        method: "GET",
        contentType: "application/json",
        success: function (response) {
            console.log(response);

            for (const customerIds of response) {
                let data=`<option>${customerIds.id}</option>`;
                $("#customerIdCmb").append(data);

            }

        },
        error: function (jqXHR) {
            console.log(jqXHR);
        }

    })

}

function loadItemIds() {
    $("#itemIdCmb").empty();

    $.ajax({
        url: "http://localhost:8080/app1/placeOrder?option=item",
        method: "GET",
        contentType: "application/json",
        success: function (response) {
            console.log(response);

            for (const customerIds of response) {
                let data=`<option>${customerIds.id}</option>`;
                $("#itemIdCmb").append(data);

            }

        },
        error: function (jqXHR) {
            console.log(jqXHR);
        }

    })


}

$("#customerIdCmb").click(function () {
  let id=  $("#customerIdCmb").val();
    $.ajax({
        url: "http://localhost:8080/app1/placeOrder?option=customerSearch&id="+id,
        method: "GET",
        contentType: "application/json",
        success: function (response) {
            console.log(response);
            $("#cmbCustomerName").val(response.name);
            $("#cmbCustomerContact").val(response.address);
            $("#cmbCustomerEmail").val(response.salary);
        },
        error: function (jqXHR) {
            console.log(jqXHR);
        }

    })
})

$("#itemIdCmb").click(function () {

    let id=$("#itemIdCmb").val();
    console.log(id)


    $.ajax({
        url: "http://localhost:8080/app1/placeOrder?option=itemSearch&id="+id,
        method: "GET",
        contentType: "application/json",
        success: function (response) {
            console.log(response);

            $("#itemNameCmb").val(response.name);
            $("#itemPriceCmb").val(response.price);
            $("#itemTypeCmb").val(response.type);
            $("#itemQtyCmb").val(response.qty);

            if (response.qty==0) {
                $("#qtyWarningLbl").css({
                    "display": "block",
                    "color": "red",
                    "font-size": "12px"
                });

            }

            else {
                $("#qtyWarningLbl").css({
                    "display": "none"

                });
            }
        },
        error: function (jqXHR) {
            console.log(jqXHR);
        }

    })

})




$("#addToCart").click(function () {

    if ($("#itemNameCmb").val().length===0 |
        $("#itemPriceCmb").val().length === 0 | $("#itemTypeCmb").val().length === 0) {
        alert("please fill out all empty fields !");


    } else {

        if ($("#itemQtyCmb").val()==0) {
            Swal.fire(
                'Add to cart  fail',
                'no Quantity available to proceed ..!',
                'error'
            )

        }else{
            console.log($("#itemQtyCmb").val())
            setPlaceOrderDetails();
        }

    }

})



function setPlaceOrderDetails() {
    total = (parseFloat($("#itemPriceCmb").val()) * parseFloat($("#itemQtyCmb").val()));
    let cartDetails = {
        cartItemId: $("#itemIdCmb").val(),
        cartItemName: $("#itemNameCmb").val(),
        cartItemPrice: $("#itemPriceCmb").val(),
        cartItemQty: $("#itemQtyCmb").val(),
        cartItemTotal: total

    }

    if (addToCartArray.length === 0) {
        // console.log('this is lenth', addToCartArray.length);
        addToCartArray.push(cartDetails);
        console.log("this iss", addToCartArray);

    } else {
        for (let i = 0; i < addToCartArray.length; i++) {
            if (addToCartArray[i].cartItemId === $("#itemIdCmb").val()) {
                let newCartDetails = searchOrder(addToCartArray[i].cartItemId);

                newCartDetails.cartItemQty = parseInt($("#itemQtyCmb").val()) + parseInt(addToCartArray[i].cartItemQty);
                newCartDetails.cartItemTotal = (parseFloat($("#itemPriceCmb").val()) * parseFloat($("#itemQtyCmb").val()) + parseFloat(addToCartArray[i].cartItemTotal));
                // console.log("new qty",parseInt($("#itemQtyCmb").val())+parseInt(addToCartArray[i].cartItemQty));
                getCartData();
                calculateNetTotal();
                console.log(addToCartArray);

                // console.log(NeworderDetails.cartItemName );
                return;
            }
        }

        addToCartArray.push(cartDetails);
        console.log("this is", addToCartArray);

    }

    getCartData();
    calculateNetTotal();

}

function getCartData() {
    $("#placeOrderTbody").empty();
    for (let i = 0; i < addToCartArray.length; i++) {
        let tRow = `<tr> <td>${addToCartArray[i].cartItemId}</td>
        <td>${addToCartArray[i].cartItemName}</td>
    <td>${addToCartArray[i].cartItemPrice}</td>
    <td>${addToCartArray[i].cartItemQty}</td>
    <td>${addToCartArray[i].cartItemTotal}</td>
    </tr>`;
        $("#placeOrderTbody").append(tRow);

    }

}

function calculateNetTotal() {
    newTotal = 0;
    for (let i = 0; i < addToCartArray.length; i++) {
        newTotal = newTotal + addToCartArray[i].cartItemTotal;
    }
    console.log("total is"+newTotal)
    $("#totalSpan").text(newTotal);

}


$("#discount").on("keydown keyup", function (e) {
    $("#subTotal").val(newTotal - (newTotal * $("#discount").val() / 100));

})

$("#payment").on("keydown keyup", function (e) {
    if ($("#discount").val().length > 0) {
        $("#balance").val(parseFloat($("#payment").val()) - parseFloat($("#subTotal").val()));

    } else {
        $("#balance").val(parseFloat($("#payment").val()) - parseFloat(newTotal));

    }


})



$("#placeOrderBtn").click(function () {
    let newOrderDetails = Object.assign({}, orderDetails);
    let newOrder = Object.assign({}, order);
    newOrder = {
        orderId: $("#orderId").val(),
        customerId: $("#customerIdCmb").val(),
        date: $("#date").val(),
        orderDetailsList: [] // Initialize orderDetailsList as an empty array
    };


    if ($("#customerIdCmb ").val() === null | $("#payment").val().length === 0 | $("#itemIdCmb ").val() === null |
        $("#itemQtyCmb").val().length === 0) {
        alert("please fill all empty fields !")
    } else {
        if ($("#balance").val() >= 0) {

            for (let i = 0; i < addToCartArray.length; i++) {
                newOrderDetails = {
                    orderId: $("#orderId").val(),
                    itemId: addToCartArray[i].cartItemId,
                    qty: addToCartArray[i].cartItemQty,
                    total: addToCartArray[i].cartItemTotal
                }
                newOrder.orderDetailsList.push(newOrderDetails);
                console.log("order detail ", newOrderDetails);
                console.log("Border "+newOrder)

            }
            
            console.log("ou "+newOrder.orderId)
            console.log("cu "+newOrder.customerId)
            console.log("da "+newOrder.date)
            console.log("chekc "+newOrder.orderDetailsList)

            let jsonData=JSON.stringify(newOrder);
            console.log(jsonData)

            /* set data to order */

            $.ajax({
                url: "http://localhost:8080/app1/placeOrder",
                method: "POST",
                contentType: "application/json",
                data: jsonData,

                success: function (response) {
                    console.log(response);
                    loadGenerateNextOrderId();
                    Swal.fire(
                        'Order Saved Successfully',
                        'Order has been Placed successfully..!',
                        'success'
                    )

                },
                error: function (jqXHR) {
                    console.log(jqXHR);

                    Swal.fire(
                        'process failed',
                        'Order placed failed..!',
                        'error'
                    )
                }
            })


            addToCartArray = [];

            $("#placeOrderTbody").empty();

            // $("#orderId").val(splitOrderId(orderDB[orderDB.length - 1].orderId));

        } else {
            Swal.fire(
                'Insufficient Credit',
                'Insufficient Credit to place order..!',
                'error'
            )
        }
    }
    /* set data to order details */



})





function splitOrderId(currentId) {
    if (currentId != null) {
        var strings = currentId.split("OD-");
        var id = parseInt(strings[1]);
        ++id;
        var digit = ("000" + id).slice(-3); // Format to 3 digits
        return "OD-" + digit;
    }
    return "OD-001";
}

function searchOrder(id) {
    return addToCartArray.find(function (cartDetails) {
        return cartDetails.cartItemId === id;
    });

}



