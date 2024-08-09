var customer = document.querySelector("#customer");
customer.style.display = 'none';

var item = document.querySelector("#item");
item.style.display = 'none';

var placeOrder = document.querySelector("#placeOrder");
placeOrder.style.display = 'none';

var orderDetail = document.querySelector("#OrderDetail");
orderDetail.style.display = 'none';

var header = document.querySelector("#header");
header.style.display = 'none';

var dashboard = document.querySelector("#dashboard");

var customerClick = document.querySelector("#customerClick");
var itemClick = document.querySelector("#itemClick");
var placeOrderClick = document.querySelector("#placeOrderClick");
var ordeDetailclick = document.querySelector("#OrderDetailClick");



document.querySelector("#manageCustomerClick").addEventListener("click", function () {
    dashboard.style.display = 'none';
    header.style.display = 'inline';
    customer.style.display = 'inline';
});

document.querySelector("#manageItemClick").addEventListener("click", function () {
    dashboard.style.display = 'none';
    header.style.display = 'inline';
    item.style.display = 'inline';
});

document.querySelector("#manageOrdersClick").addEventListener("click", function () {
    dashboard.style.display = 'none';
    header.style.display = 'inline';
    placeOrder.style.display = 'inline';
});


//  other

customerClick.addEventListener("click", function () {
    sessionStorage.setItem('lastActiveSection', 'customer');
    customer.style.display = 'inline';
    placeOrder.style.display = 'none';
    orderDetail.style.display = 'none';
    header.style.display = 'inline';
    item.style.display = 'none';

})

itemClick.addEventListener("click", function () {
    sessionStorage.setItem('lastActiveSection', 'item');
    customer.style.display = 'none';
    placeOrder.style.display = 'none';
    orderDetail.style.display = 'none';
    header.style.display = 'inline';
    item.style.display = 'inline';

})

placeOrderClick.addEventListener("click", function () {
    sessionStorage.setItem('lastActiveSection', 'placeOrder');
    customer.style.display = 'none';
    placeOrder.style.display = 'inline';
    orderDetail.style.display = 'none';
    header.style.display = 'inline';
    item.style.display = 'none';

})

ordeDetailclick.addEventListener("click", function () {
    sessionStorage.setItem('lastActiveSection', 'OrderDetail');
    customer.style.display = 'none';
    placeOrder.style.display = 'none';
    orderDetail.style.display = 'inline';
    header.style.display = 'inline';
    item.style.display = 'none';

})

document.querySelector("#posSystemClcik").addEventListener("click", function () {
    customer.style.display = 'none';
    placeOrder.style.display = 'none';
    orderDetail.style.display = 'none';
    header.style.display = 'none';
    item.style.display = 'none';
    dashboard.style.display = 'inline';
})


document.addEventListener('DOMContentLoaded', function () {
    const lastActiveSection = sessionStorage.getItem('lastActiveSection');
    const sections = document.querySelectorAll('section');


    if (lastActiveSection) {
        sections.forEach(section => {

            section.style.display = 'none';

        });
        const activeSection = document.querySelector(`#${lastActiveSection}`);
        if (activeSection) {
            activeSection.style.display = 'block';
            header.style.display = 'inline';
        } else {

        }
    }
});
