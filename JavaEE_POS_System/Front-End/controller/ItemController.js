getAllItems();
function getAllItems(){
    $.ajax({
        url: "http://localhost:8080/app1/item?method=getAll",
        method: "GET",
        contentType: "application/json",
        success: function (response) {
            console.log(response);
            loadItemDataToTable(response)
        },
        error: function (jqXHR) {
            console.log(jqXHR);
        }

    })

}

function loadItemDataToTable(response) {
    $("#iTbody").empty();
    for (const itemData of response) {
        let row=`<tr>
        <td>${itemData.id}</td>
        <td>${itemData.name}</td>
        <td>${itemData.type}</td>
        <td>${itemData.price}</td>
        <td>${itemData.qty}</td>
                        </tr>`;

        $("#iTbody").append(row);

    }
    itemDataBindEvents();
    doubleCLickDeleteItem();

}

function saveItem(id,name,type,price,qty) {

    let newItem = Object.assign({}, Item);
    newItem.id = id ;
    newItem.name =  name;
    newItem.type =  type;
    newItem.price =  price;
    newItem.qty =  qty;

    let itemData = JSON.stringify(newItem);

    $.ajax({
        url: "http://localhost:8080/app1/item",
        method: "POST",
        contentType: "application/json",
        data: itemData,

        success: function (response) {
            console.log(response);
            getAllItems();
            clearItemInputFields();
            Swal.fire(
                'Item Saved Successfully',
                'Item has been Saved successfully..!',
                'success'
            )
        },
        error: function (jqXHR) {
            clearItemInputFields();
            console.log(jqXHR);
            if (jqXHR.status === 409) {
                Swal.fire(
                    'This Item Id is already exists',
                    'Item saved failed..!',
                    'error'
                )
                return;
            }
            Swal.fire(
                'process failed',
                'Item saved failed..!',
                'error'
            )
        }
    })

    $("#itemSaveBtn").prop("disabled", true);

}
$("#itemSaveBtn").click(function () {

    let id=$("#itemId").val();
    let name=$("#itemName").val();
    let type=$("#type").val();
    let price=$("#unitPrice").val();
    let qty=$("#qty").val();

        if(checkAllItemsValidation()){
            saveItem(id,name,type,price,qty);

        }else{
            alert("Error! Try Again !")
            clearItemInputFields();
        }

})

function updateItem(id, name, type, price,qty){
    let newItem = Object.assign({}, Item);
    newItem.id = id ;
    newItem.name =  name;
    newItem.type =  type;
    newItem.price =  price;
    newItem.qty =  qty;

    let itemData = JSON.stringify(newItem);

    $.ajax({
        url: "http://localhost:8080/app1/item",
        method: "PUT",
        contentType: "application/json",
        data: itemData,

        success: function (response) {
            console.log(response);
            getAllItems();
            clearItemInputFields();
            Swal.fire(
                'Item Updated Successfully',
                'Item has been updated successfully..!',
                'success'
            )
        },
        error: function (jqXHR) {
            clearItemInputFields();
            console.log(jqXHR);
            if (jqXHR.status === 409) {
                Swal.fire(
                    'Can not find item Id !!',
                    'Item update failed..!',
                    'error'
                )
                return;
            }
            Swal.fire(
                'process failed',
                'Item saved failed..!',
                'error'
            )
        }
    })
    $("#itemUpdateBtn").prop("disabled", true);

}
$("#itemUpdateBtn").click(function () {
    let id=$("#itemId").val();
    let name=$("#itemName").val();
    let type=$("#type").val();
    let price=$("#unitPrice").val();
    let qty=$("#qty").val();

    Swal.fire({
        title: 'Are you sure?',
        text: 'You are about to update this record!',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, update it!',
        cancelButtonText: 'Close'
    }).then((result) => {
        if (result.isConfirmed) {
            if(checkAllItemsValidation()){
                updateItem(id, name, type, price,qty);
            }else{
                alert("Error! Try Again !")
                clearItemInputFields();
            }

        } else if (result.dismiss === Swal.DismissReason.cancel) {
            Swal.fire('Cancelled', 'Your record is not updated :)', 'info');
        }
    });

})

function deleteItem(itemId){
    $.ajax({
        url: "http://localhost:8080/app1/item?id=" + itemId,
        method: "delete",

        success: function (response) {
            console.log(response);
            getAllItems();
            clearItemInputFields();
            Swal.fire(
                'Item Deleted Successfully',
                'Item has been Deleted successfully..!',
                'success'
            )
        },
        error: function (jqXHR) {
            clearItemInputFields();
            console.log(jqXHR);
            if (jqXHR.status === 409) {
                Swal.fire(
                    'Can not find Item Id !!',
                    'Item delete failed..!',
                    'error'
                )
                return;
            }
            Swal.fire(
                'process failed',
                'Item delete failed..!',
                'error'
            )
        }

    })

}
$("#itemDeleteBtn").click(function () {
    Swal.fire({
        title: 'Are you sure?',
        text: 'You are about to delete this record!',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, delete it!',
        cancelButtonText: 'Close'
    }).then((result) => {
        if (result.isConfirmed) {
            let itemId = $("#itemId").val();
            deleteItem(itemId);

        } else if (result.dismiss === Swal.DismissReason.cancel) {
            Swal.fire('Cancelled', 'Your record is safe :)', 'info');
        }
    });

})


function itemDataBindEvents() {
    $("#iTbody tr").click(function () {
        let id = $(this).children(":nth-child(1)").text();
        let name = $(this).children(":nth-child(2)").text();
        let type = $(this).children(":nth-child(3)").text();
        let unitPrice = $(this).children(":nth-child(4)").text();
        let qty = $(this).children(":nth-child(5)").text();
        setItemTableDataToFields(id, name, type, unitPrice, qty);

    })
}
function setItemTableDataToFields(id, name, type, unitPrice, qty) {

    $("#itemId").val(id.trim());
    $("#itemName").val(name.trim());
    $("#type").val(type.trim());
    $("#unitPrice").val(parseInt(unitPrice));
    $("#qty").val(parseInt(qty));

}

function doubleCLickDeleteItem() {
    $("#iTbody tr").dblclick(function () {

        let iId = $(this).children(":nth-child(1)").text();

        Swal.fire({
            title: 'Are you sure?',
            text: 'You are about to delete this record!',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Yes, delete it!',
            cancelButtonText: 'Close'
        }).then((result) => {
            if (result.isConfirmed) {
                deleteItem(iId);
            } else if (result.dismiss === Swal.DismissReason.cancel) {
                Swal.fire('Cancelled', 'Your record is safe :)', 'info');
            }
        });

    })
}


$(".searchItem").on("input", function () {

    let name = $("#searchTxt").val();

    $.ajax({
        url: "http://localhost:8080/app1/customer?name=" + name + "&method=search",
        method: "GET",

        success: function (response) {
            console.log(response);
            setDataToLiveTableSearch(response)

        },
        error: function (jqXHR) {
            console.log(jqXHR);

        }

    })
})

$("#clearItemBTn").click(function(){
    clearItemInputFields();
})

