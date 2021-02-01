$(".select2").select2({
    // the following code is used to disable x-scrollbar when click in select input and
    // take 100% width in responsive also
    dropdownAutoWidth: true,
    width: '100%'
  });
  
  function editSelectCheck(nameSelect) {
    console.log(nameSelect);
    if (nameSelect) {
        serviceValue = document.getElementById("service").value;
        linkValue = document.getElementById("link").value;
        if (serviceValue == nameSelect.value) {
            document.getElementById("linktes").required = false;
            document.getElementById("servicecheck").style.display = "block";
            document.getElementById("linkcheck").style.display = "none";
        } else if (linkValue == nameSelect.value) {
            document.getElementById("linktes").required = true;
            document.getElementById("linkcheck").style.display = "block";
            document.getElementById("servicecheck").style.display = "none";
        }
    } else {
         if ($promotion_type == 'service') {  
            document.getElementById("linkcheck").style.display = "none";
            document.getElementById("servicecheck").style.display = "block";
         } else { 
            document.getElementById("linkcheck").style.display = "block";
            document.getElementById("servicecheck").style.display = "none";
         } 
    }
}