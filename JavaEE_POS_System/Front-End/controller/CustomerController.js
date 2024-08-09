getAllCustomers();
window.onload = function() {
    getAllCustomers();
    tblRowClick();
    tblRowDoubleClick();
};

// get all customers
function getAllCustomers() {
    $.ajax({
        url: "http://localhost:8080/app1/customer?method=getAll",
        method: "GET",
        contentType: "application/json",
        success: function (response) {
            console.log(response);
            loadDataToTable(response)
        },
        error: function (jqXHR) {
            console.log(jqXHR);
        }

    })
}

// load customer data to table
function loadDataToTable(response) {

    $("#cTBody").empty();
    for (const cusData of response) {
        console.log(cusData.name)
        let data = `<tr>

        <td>${cusData.id}</td>
        <td>${cusData.name}</td>
        <td>${cusData.address}</td>
        <td>${cusData.salary}</td>
        
        </tr>`

        $("#cTBody").append(data)


    }
    tblRowClick();
    tblRowDoubleClick();

}



// customer save
function saveCustomer(cId, cName, cAddress, cSalary) {
    let newCustomer = Object.assign({}, Customer);
    newCustomer.id = cId;
    newCustomer.name = cName;
    newCustomer.address = cAddress;
    newCustomer.salary = cSalary;

    console.log(newCustomer);

    let customerData = JSON.stringify(newCustomer);
    console.log(customerData)

    $.ajax({
        url: "http://localhost:8080/app1/customer",
        method: "POST",
        contentType: "application/json",
        data: customerData,

        success: function (response) {
            console.log(response);
            getAllCustomers();
            clearCustomerInputFields();
            Swal.fire(
                'Customer Saved Successfully',
                'Customer has been Saved successfully..!',
                'success'
            )
        },
        error: function (jqXHR) {
            clearCustomerInputFields();
            console.log(jqXHR);
            if (jqXHR.status === 409) {
                Swal.fire(
                    'This Customer Id is already exists',
                    'Customer saved failed..!',
                    'error'
                )
                return;
            }
            Swal.fire(
                'process failed',
                'Customer saved failed..!',
                'error'
            )
        }
    })

    $("#customerSaveBtn").prop("disabled", true);

}

$("#customerSaveBtn").click(function () {

    let cId = $("#cId").val();
    let cName = $("#cName").val();
    let cAddress = $("#cAddress").val();
    let cSalary = $("#cSalary").val();

    if (checkAll()) {
        saveCustomer(cId, cName, cAddress, cSalary);

    } else {
        alert("Error! Try Again !")
        clearCustomerInputFields();

    }
})

// customer delete
function deleteCustomer(cId) {
    $.ajax({
        url: "http://localhost:8080/app1/customer?id=" + cId,
        method: "delete",

        success: function (response) {
            console.log(response);
            getAllCustomers();
            clearCustomerInputFields();
            Swal.fire(
                'Customer Deleted Successfully',
                'Customer has been Deleted successfully..!',
                'success'
            )
        },
        error: function (jqXHR) {
            clearCustomerInputFields();
            console.log(jqXHR);
            if (jqXHR.status === 409) {
                Swal.fire(
                    'Can not find customer Id !!',
                    'Customer delete failed..!',
                    'error'
                )
                return;
            }
            Swal.fire(
                'process failed',
                'Customer delete failed..!',
                'error'
            )
        }

    })
}

$("#customerDeleteBtn").click(function () {
    Swal.fire({
        title: 'Are you sure?',
        text: 'You are about to delete this record!',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, delete it!',
        cancelButtonText: 'Close'
    }).then((result) => {
        if (result.isConfirmed) {
            let cId = $("#cId").val();
            deleteCustomer(cId);
        } else if (result.dismiss === Swal.DismissReason.cancel) {
            Swal.fire('Cancelled', 'Your record is safe :)', 'info');
        }
    });

})

// customer update
function updateCustomer(cId, cName, cAddress, cSalary) {
    let newCustomer = Object.assign({}, Customer);
    newCustomer.id = cId;
    newCustomer.name = cName;
    newCustomer.address = cAddress;
    newCustomer.salary = cSalary;
    console.log("update customer " + newCustomer)

    let customerData = JSON.stringify(newCustomer);

    $.ajax({
        url: "http://localhost:8080/app1/customer",
        method: "PUT",
        contentType: "application/json",
        data: customerData,

        success: function (response) {
            console.log(response);
            getAllCustomers();
            clearCustomerInputFields();
            Swal.fire(
                'Customer Updated Successfully',
                'Customer has been updated successfully..!',
                'success'
            )
        },
        error: function (jqXHR) {
            clearCustomerInputFields();
            console.log(jqXHR);
            if (jqXHR.status === 409) {
                Swal.fire(
                    'Can not find customer Id !!',
                    'Customer update failed..!',
                    'error'
                )
                return;
            }
            Swal.fire(
                'process failed',
                'Customer saved failed..!',
                'error'
            )
        }
    })
    $("#CustomerUpdateBtn").prop("disabled", true);

}

$("#CustomerUpdateBtn").click(function () {
    let cId = $("#cId").val();
    let cName = $("#cName").val();
    let cAddress = $("#cAddress").val();
    let cSalary = $("#cSalary").val();

    Swal.fire({
        title: 'Are you sure?',
        text: 'You are about to update this record!',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, update it!',
        cancelButtonText: 'Close'
    }).then((result) => {
        if (result.isConfirmed) {
            if(checkAll()){
                updateCustomer(cId, cName, cAddress, cSalary);
            }else{
                alert("Error! Try Again !")
                clearItemInputFields();
            }

        } else if (result.dismiss === Swal.DismissReason.cancel) {
            Swal.fire('Cancelled', 'Your record is not updated :)', 'info');
        }
    });

})

// live table search
$("#searchTxt").on("input", function () {
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
});

function setDataToLiveTableSearch(response) {
    $("#cTBody").empty();
    for (const cusData of response) {
        let row = `<tr>
        <td>${cusData.id}</td>
        <td>${cusData.name}</td>
        <td>${cusData.address}</td>
        <td>${cusData.salary}</td>
        </tr>`;

        $("#cTBody").append(row);
    }
}

// table row click
function tblRowClick(){
    $("#cTBody tr").click(function () {

        let id = $(this).children(":nth-child(1)").text();
        let name = $(this).children(":nth-child(2)").text();
        let address = $(this).children(":nth-child(3)").text();
        let salary = $(this).children(":nth-child(4)").text();

        setCustomerTableDataToFields(id, name, address, salary);

    })
}
function setCustomerTableDataToFields(id, name, address, salary) {
    $("#cId").val(id);
    $("#cName").val(name);
    $("#cAddress").val(address);
    $("#cSalary").val(salary);

}

// table row double click
function tblRowDoubleClick(){
    $("#cTBody tr").dblclick(function () {
        let cId = $(this).children(":nth-child(1)").text();

        Swal.fire({
            title: 'Are you sure?',
            text: 'You are about to delete this record!',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Yes, delete it!',
            cancelButtonText: 'Close'
        }).then((result) => {
            if (result.isConfirmed) {
                deleteCustomer(cId);
            } else if (result.dismiss === Swal.DismissReason.cancel) {
                Swal.fire('Cancelled', 'Your record is safe :)', 'info');
            }
        });

    })
}


// clear input fields

$("#customerClearBTn").click(function () {
    clearCustomerInputFields();
})



// function searchCustomer(id) {
//     return customerDB.find(function (Customer) {
//         return Customer.id == id;
//     });
// }

