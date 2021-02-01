




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















