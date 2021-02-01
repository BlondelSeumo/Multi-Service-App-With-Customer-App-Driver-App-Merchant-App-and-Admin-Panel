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
  var countrycode = $("#country").val();
  var code = countrycode; // Assigning value from model.
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
  console.log(code)
});




