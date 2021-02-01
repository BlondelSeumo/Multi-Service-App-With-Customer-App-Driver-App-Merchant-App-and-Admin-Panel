function admSelectCheck(nameSelect) {
    console.log(nameSelect);
    if (nameSelect) {
        serviceValue = document.getElementById("persen").value;
        linkValue = document.getElementById("fix").value;
        if (serviceValue == nameSelect.value) {
            document.getElementById("persencheckrequired").required = true;
            document.getElementById("fixcheckrequired").required = false;
            document.getElementById("persencheck").style.display = "block";
            document.getElementById("fixcheck").style.display = "none";
        } else if (linkValue == nameSelect.value) {
            document.getElementById("fixcheckrequired").required = true;
            document.getElementById("persencheckrequired").required = false;
            document.getElementById("fixcheck").style.display = "block";
            document.getElementById("persencheck").style.display = "none";
        }
    } else {
        document.getElementById("persencheck").style.display = "block";
    }
}

$(function() {
  
    $(".select2").select2({
      dropdownAutoWidth: true,
      width: '100%'
    });
    
    
    });