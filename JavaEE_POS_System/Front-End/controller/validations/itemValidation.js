const ITEM_ID_REGEX=/^I\d{3}$/;
const ITEM_NAME_REGEX=/^[A-Za-z ]{5,}$/;
const ITEM_TYPE_REGEX=/^[A-Za-z ]{5,}$/;
const ITEM_UNIT_PRICE_REGEX=/^\$?\d+(?:\.\d{2})?$/;
const ITEM_QTY_REGEX=/^\d+$/;

let itemValidation=new Array();

itemValidation.push({field:$("#itemId"),regEx:ITEM_ID_REGEX});
itemValidation.push({field:$("#itemName"),regEx:ITEM_NAME_REGEX});
itemValidation.push({field:$("#type"),regEx:ITEM_TYPE_REGEX});
itemValidation.push({field:$("#unitPrice"),regEx:ITEM_UNIT_PRICE_REGEX});
itemValidation.push({field:$("#qty"),regEx:ITEM_QTY_REGEX});

function clearItemInputFields(){
    $("#itemId,#itemName,#type,#unitPrice,#qty").val("");
    $("#itemId,#itemName,#type,#unitPrice,#qty").css("border", "1px solid #ced4da");
    $("#id").focus();
    setItemControllerBtn();
}

setItemControllerBtn();

$("#itemId,#itemName,#type,#unitPrice,#qty").on("keyup",function(e){
    let indexNo = itemValidation.indexOf(itemValidation.find((c) => c.field.attr("id") == e.target.id));
    console.log(indexNo);

    if(e.key=="Tab"){
        e.preventDefault();
    }

    checkItemValidations(itemValidation[indexNo]);

    setItemControllerBtn(); 

    if (e.key == "Enter") {

        if (e.target.id != itemValidation[itemValidation.length - 1].field.attr("id")) {
            //check validation is ok
            if (checkItemValidations(itemValidation[indexNo])) {
                itemValidation[indexNo + 1].field.focus();
            }
        } else {
            if (checkItemValidations(itemValidation[indexNo])) {
                saveItem();
            }
        }
    }
})


function checkItemValidations(object) {
    if (object.regEx.test(object.field.val())) {
        setItemControllerBorder(true, object)
        return true;
    }
    setItemControllerBorder(false, object)
    return false;
}


function setItemControllerBorder(bol, ob) {
    if (!bol) {
        if (ob.field.val().length >= 1) {
            ob.field.css("border", "2px solid red");
        } else {
            ob.field.css("border", "1px solid #ced4da");
        }
    } else {
        if (ob.field.val().length >= 1) {
            ob.field.css("border", "2px solid green");
        } else {
            ob.field.css("border", "1px solid #ced4da");
        }
    }

}

function checkAllItemsValidation() {
    for (let i = 0; i < itemValidation.length; i++) {
        if (!checkValidations(itemValidation[i])) return false;
    }
    return true;
}


function setItemControllerBtn() {
    // $("#itemDeleteBtn").prop("disabled", true);
    // $("#itemUpdateBtn").prop("disabled", true);

    if (checkAllItemsValidation()) {
        $("#itemSaveBtn").prop("disabled", false);
        $("#itemUpdateBtn").prop("disabled", false);
    } else {
        $("#itemSaveBtn").prop("disabled", true);
        $("#itemUpdateBtn").prop("disabled", true);

    }

    // let id = $("#id").val();

        // $("#itemDeleteBtn").prop("disabled", false);
        // $("#itemUpdateBtn").prop("disabled", true);
        //
        // $("#itemDeleteBtn").prop("disabled", false);
        // $("#itemUpdateBtn").prop("disabled", false);


}

