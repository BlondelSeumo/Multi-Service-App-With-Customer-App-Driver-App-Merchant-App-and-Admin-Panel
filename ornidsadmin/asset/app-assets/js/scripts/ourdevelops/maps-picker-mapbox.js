
var lat = $("#merchant_latitude").val();
var long = $("#merchant_longitude").val();
var mapbox_token = $("#tokenode").val();
mapboxgl.accessToken = mapbox_token;
var map = new mapboxgl.Map({
	container: 'mappicker',
	style: 'mapbox://styles/ourdevelops/ckhltv5lk0mus19lbvnom8ppl',
	center: [long, lat],
	zoom: 2
});
var marker = new mapboxgl.Marker({
	draggable: true
})
	.setLngLat([long, lat])
	.addTo(map);
document.getElementById('latitude').value = lat;
document.getElementById('longitude').value = long;
coordinateFeature(long, lat);
function onDragEnd() {
	var lngLat = marker.getLngLat();
	document.getElementById('latitude').value = lngLat.lat;
	document.getElementById('longitude').value = lngLat.lng;
	coordinateFeature(lngLat.lng, lngLat.lat);
	fly([lngLat.lng, lngLat.lat]);
}

marker.on('dragend', onDragEnd);


function coordinateFeature(lng, lat) {
	$.ajax({
		url: 'https://api.mapbox.com/geocoding/v5/mapbox.places/' + lng + ', ' + lat + '.json?types=poi&access_token=' + mapbox_token,
		type: "GET",
		success: function (response) {
			var data_parse = response.features;
			console.log(data_parse);
			if (data_parse.length > 0) {
				console.log(data_parse[0].place_name);
				document.getElementById('address').value = data_parse[0].place_name;
			} else {
				document.getElementById('address').value = "";
				document.getElementById('address').placeholder = "address not found,  please input manual address";
			}
		}
	});
}

function fly(target) {
	map.flyTo({
		center: target,
		zoom: 15,
		bearing: 0,
		speed: 1, // make the flying slow
		curve: 1, // change the speed at which it zooms out

		easing: function (t) {
			return t;
		},
		essential: true
	});
}
