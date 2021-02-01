function admSelectCheck(nameSelect) {
    console.log(nameSelect);
    if (nameSelect) {
        pelangganValue = document.getElementById("customer").value;
        driverValue = document.getElementById("driver").value;
        mitraValue = document.getElementById("partner").value;
        console.log(mitraValue);
        if (pelangganValue == nameSelect.value) {
            document.getElementById("pelanggancheck").style.display = "block";
            document.getElementById("drivercheck").style.display = "none";
            document.getElementById("mitracheck").style.display = "none";

        } else if (driverValue == nameSelect.value) {
            document.getElementById("drivercheck").style.display = "block";
            document.getElementById("pelanggancheck").style.display = "none";
            document.getElementById("mitracheck").style.display = "none";
        } else if (mitraValue == nameSelect.value) {
            document.getElementById("mitracheck").style.display = "block";
            document.getElementById("drivercheck").style.display = "none";
            document.getElementById("pelanggancheck").style.display = "none";

        } else {
            document.getElementById("pelanggancheck").style.display = "block";
        }
    }
}

$(function() {
  
    $(".select2").select2({
      dropdownAutoWidth: true,
      width: '100%'
    });
    
    
    });