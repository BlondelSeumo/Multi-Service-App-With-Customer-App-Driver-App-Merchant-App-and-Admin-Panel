
    var lat = $("#merchant_latitude").val();
    var long = $("#merchant_longitude").val();

$('#us3').locationpicker({
  location: {
      latitude: lat,
      longitude: long
  },
  radius: 300,
  inputBinding: {
      latitudeInput: $('#us3-lat'),
      longitudeInput: $('#us3-lon'),
      radiusInput: $('#us3-radius'),
      locationNameInput: $('#us3-address')
  },
  enableAutocomplete: true,
  onchanged: function(currentLocation, radius, isMarkerDropped) {}
});