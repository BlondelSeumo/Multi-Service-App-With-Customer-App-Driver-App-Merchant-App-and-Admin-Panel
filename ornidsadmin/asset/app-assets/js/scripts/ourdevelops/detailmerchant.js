

Dropzone.options.dpzSingleFile = {
    paramName: "file", // The name that will be used to transfer the file
    maxFiles: 1,
    init: function () {
      this.on("maxfilesexceeded", function (file) {
        this.removeAllFiles();
        this.addFile(file);
      });
    }
  };

  $(document).ready(function () {

  // pagination
  var mySwiper2 = new Swiper('.swiper-paginations', {
    pagination: {
      el: '.swiper-pagination',
    },
  });
});


$(function() {
  
$(".select2").select2({
  dropdownAutoWidth: true,
  width: '100%'
});

  var countrycode = $("#countryem").val();
  if (countrycode == '') {
    var code = '+62';// Assigning value from model.
  } else {
    var code = countrycode;
    }
  $('#txtPhone').val(code);
  $('#txtPhone').intlTelInput({
      autoHideDialCode: true,
      autoPlaceholder: "ON",
      dropdownContainer: document.body,
      formatOnDisplay: true,
      hiddenInput: "full_number",
      initialCountry: "auto",
      nationalMode: true,
      placeholderNumberType: "MOBILE",
      preferredCountries: ['US'],
      separateDialCode: false
  });


});

function edititemcategoryFunction(dataitemcategory) {
  var fields = dataitemcategory.split(',');
  $('#iditems').val(fields[1]);
  $('#itemcategoryname').val(fields[0]);
}















