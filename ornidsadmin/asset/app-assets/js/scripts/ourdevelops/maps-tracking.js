initMap();
var gmarkers = [];
var map;
var base_url = $("#base").val();
function initMap() {
	$.ajax({
		url: base_url + "api/adminapi/alldriver/ok",
		type: "GET",
		success: function (response) {
			if(response.data != undefined) {
			var data_parse = response.data;
			if (data_parse.length > 0) {
				for (var i = 0; i < data_parse.length; i++) {
					
					var lat = data_parse[i].latitude;
					var lng = data_parse[i].longitude;
					var online = data_parse[i].status;
					var nama_driver = data_parse[i].driver_name;
					var uluru = {
						lat: parseFloat(lat),
						lng: parseFloat(lng),
					};
					if (i == 0) {
						map = new google.maps.Map(document.getElementById("map"), {
							zoom: 15,
							center: uluru,
						});
					}
					if (online == "1") var image = base_url + "images/icon/active.png";
					else if (online == "2")
						var image = base_url + "images/icon/bekerja.png";
					else if (online == "3")
						var image = base_url + "images/icon/bekerja.png";
					else if (online == "4")
						var image = base_url + "images/icon/nonactive.png";
					else if (online == "5")
						var image = base_url + "images/icon/nonactive.png";
					var marker = new google.maps.Marker({
						position: uluru,
						map: map,
						icon: image,
						title: nama_driver,
					});
					var styles = [
						{
							elementType: "geometry",
							stylers: [
								{
									color: "#f5f5f5",
								},
							],
						},
						{
							elementType: "labels.icon",
							stylers: [
								{
									visibility: "off",
								},
							],
						},
						{
							elementType: "labels.text.fill",
							stylers: [
								{
									color: "#616161",
								},
							],
						},
						{
							elementType: "labels.text.stroke",
							stylers: [
								{
									color: "#f5f5f5",
								},
							],
						},
						{
							featureType: "administrative.land_parcel",
							elementType: "labels.text.fill",
							stylers: [
								{
									color: "#bdbdbd",
								},
							],
						},
						{
							featureType: "poi",
							elementType: "geometry",
							stylers: [
								{
									color: "#eeeeee",
								},
							],
						},
						{
							featureType: "poi",
							elementType: "labels.text.fill",
							stylers: [
								{
									color: "#757575",
								},
							],
						},
						{
							featureType: "poi.park",
							elementType: "geometry",
							stylers: [
								{
									color: "#e5e5e5",
								},
							],
						},
						{
							featureType: "poi.park",
							elementType: "labels.text.fill",
							stylers: [
								{
									color: "#9e9e9e",
								},
							],
						},
						{
							featureType: "road",
							elementType: "geometry",
							stylers: [
								{
									color: "#ffffff",
								},
							],
						},
						{
							featureType: "road.arterial",
							elementType: "labels.text.fill",
							stylers: [
								{
									color: "#757575",
								},
							],
						},
						{
							featureType: "road.highway",
							elementType: "geometry",
							stylers: [
								{
									color: "#dadada",
								},
							],
						},
						{
							featureType: "road.highway",
							elementType: "labels.text.fill",
							stylers: [
								{
									color: "#616161",
								},
							],
						},
						{
							featureType: "road.local",
							elementType: "labels.text.fill",
							stylers: [
								{
									color: "#9e9e9e",
								},
							],
						},
						{
							featureType: "transit.line",
							elementType: "geometry",
							stylers: [
								{
									color: "#e5e5e5",
								},
							],
						},
						{
							featureType: "transit.station",
							elementType: "geometry",
							stylers: [
								{
									color: "#eeeeee",
								},
							],
						},
						{
							featureType: "water",
							elementType: "geometry",
							stylers: [
								{
									color: "#c9c9c9",
								},
							],
						},
						{
							featureType: "water",
							elementType: "labels.text.fill",
							stylers: [
								{
									color: "#9e9e9e",
								},
							],
						},
					];

					/*this sets the style*/
					map.setOptions({
						styles: styles,
					});
					var infoWindow = new google.maps.InfoWindow();
					google.maps.event.addListener(marker, "click", function () {
						var markerContent = "<h4>" + this.title + "</h4>";
						infoWindow.setContent(markerContent);
						infoWindow.open(map, this);
					});
					// Push your newly created marker into the array:
					gmarkers.push(marker);
				}
			} else {
				var uluru = {
					lat: parseFloat("11.111111"),
					lng: parseFloat("-1.133344"),
				};
				map = new google.maps.Map(document.getElementById("map"), {
					zoom: 15,
					center: uluru,
				});
			}
			addYourLocationButton(map, marker);
		}
		},
	});
}

function addYourLocationButton(map, marker) {
	var controlDiv = document.createElement("div");

	var firstChild = document.createElement("button");
	firstChild.style.backgroundColor = "#fff";
	firstChild.style.border = "none";
	firstChild.style.outline = "none";
	firstChild.style.width = "40px";
	firstChild.style.height = "40px";
	firstChild.style.borderRadius = "2px";
	firstChild.style.boxShadow = "0 1px 4px rgba(0,0,0,0.3)";
	firstChild.style.cursor = "pointer";
	firstChild.style.marginRight = "10px";
	firstChild.style.padding = "0px";
	firstChild.title = "Your Location";
	controlDiv.appendChild(firstChild);

	var secondChild = document.createElement("div");
	secondChild.style.margin = "10px";
	secondChild.style.width = "18px";
	secondChild.style.height = "18px";
	secondChild.style.backgroundImage =
		"url(https://maps.gstatic.com/tactile/mylocation/mylocation-sprite-1x.png)";
	secondChild.style.backgroundSize = "180px 18px";
	secondChild.style.backgroundPosition = "0px 0px";
	secondChild.style.backgroundRepeat = "no-repeat";
	secondChild.id = "you_location_img";
	firstChild.appendChild(secondChild);

	google.maps.event.addListener(map, "dragend", function () {
		$("#you_location_img").css("background-position", "0px 0px");
	});

	firstChild.addEventListener("click", function () {
		var imgX = "0";
		var animationInterval = setInterval(function () {
			if (imgX == "-18") imgX = "0";
			else imgX = "-18";
			$("#you_location_img").css("background-position", imgX + "px 0px");
		}, 500);
		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(function (position) {
				var latlng = new google.maps.LatLng(
					position.coords.latitude,
					position.coords.longitude
				);
				marker.setPosition(latlng);
				map.setCenter(latlng);
				clearInterval(animationInterval);
				$("#you_location_img").css("background-position", "-144px 0px");
			});
		} else {
			clearInterval(animationInterval);
			$("#you_location_img").css("background-position", "0px 0px");
		}
	});

	controlDiv.index = 1;
	map.controls[google.maps.ControlPosition.RIGHT_BOTTOM].push(controlDiv);
}

function removeMarkers() {
	for (i = 0; i < gmarkers.length; i++) {
		gmarkers[i].setMap(null);
	}
}

function getVehicleAll2() {
	$.ajax({
		url: base_url + "/api/adminapi/alldriver/ok",
		type: "GET",
		success: function (response) {
			if(response.data != undefined) {
			var data_parse = response.data;
			removeMarkers();
			for (var i = 0; i < data_parse.length; i++) {
				var lat = data_parse[i].latitude;
				var lng = data_parse[i].longitude;
				var online = data_parse[i].status;
				var nama_driver = data_parse[i].driver_name;
				var uluru = {
					lat: parseFloat(lat),
					lng: parseFloat(lng),
				};
				if (online == "1") var image = base_url + "images/icon/active.png";
				else if (online == "2")
					var image = base_url + "images/icon/bekerja.png";
				else if (online == "3")
					var image = base_url + "images/icon/bekerja.png";
				else if (online == "4")
					var image = base_url + "images/icon/nonactive.png";
				else if (online == "5")
					var image = base_url + "images/icon/nonactive.png";
				var marker = new google.maps.Marker({
					position: uluru,
					map: map,
					icon: image,
					title: nama_driver,
				});

				var infoWindow = new google.maps.InfoWindow();
				google.maps.event.addListener(marker, "click", function () {
					var markerContent = "<h4>" + this.title + "</h4>";
					infoWindow.setContent(markerContent);
					infoWindow.open(map, this);
				});
				// Push your newly created marker into the array:
				gmarkers.push(marker);
			}
		}
		},
	});
}

function foo() {
	var day = new Date().getDay();
	var hours = new Date().getHours();
	getVehicleAll2();

	if (day === 0 && hours > 12 && hours < 13) {
	}
	// Do what you want here:
}

setInterval(foo, 4000);
