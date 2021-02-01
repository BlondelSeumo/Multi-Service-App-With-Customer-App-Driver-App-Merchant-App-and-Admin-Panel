$(function() {
    var code = "+62"; // Assigning value from model.
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