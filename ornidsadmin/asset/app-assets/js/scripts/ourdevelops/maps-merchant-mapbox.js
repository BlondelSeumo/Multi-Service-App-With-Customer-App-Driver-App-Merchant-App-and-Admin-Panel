var mapbox_token = $("#tokenode").val();
mapboxgl.accessToken = mapbox_token;
map = new mapboxgl.Map({
	container: 'mapmerchant',
	style: 'mapbox://styles/ourdevelops/ckhltv5lk0mus19lbvnom8ppl',
	center: [-65.017, -16.457],
	zoom: 1
	});			
var gmarkers = [];
var map;
var image = [];
var popup;
var el;
var base_url = $("#base").val();

$.ajax({
		url: base_url + "/api/adminapi/allmerchant/ok",
		type: "GET",
		success: function (response) {
			if(response.data != undefined) {
			var data_parse = response.data;
			if (data_parse.length > 0) {
				for (var i = 0; i < data_parse.length; i++) {					
					var lat = data_parse[i].merchant_latitude;
					var lng = data_parse[i].merchant_longitude;
					var online = data_parse[i].merchant_status;
					var nama_driver = data_parse[i].merchant_name;
						
					console.log(lng, lat);
					popup = new mapboxgl.Popup({ offset: 25 }).setText(nama_driver);
					if (online == "1") 
					image = base_url + "images/icon/active.png";
					else if (online == "2")
					image = base_url + "images/icon/bekerja.png";
					else if (online == "3")
					image = base_url + "images/icon/bekerja.png";
					else if (online == "4")
					image = base_url + "images/icon/nonactive.png";
					else if (online == "5")
					image = base_url + "images/icon/nonactive.png";

						el = document.createElement('div');
						el.className = 'marker';
						el.style.backgroundImage =
						'url('+image+')';
						el.style.width =  '50px';
						el.style.height =  '50px';

						
					gmarker = new mapboxgl.Marker(el)
					.setLngLat([lng, lat])
					.setPopup(popup)
					.addTo(map);
					gmarkers.push(gmarker);
				}
			} else {
				map = new mapboxgl.Map({
					container: 'map',
					style: 'mapbox://styles/mapbox/streets-v11',
					center: [-65.017, -16.457],
					zoom: 5
					});
			}
		}
		},
	});
	

	function removeMarkers() {
		for (var i = gmarkers.length - 1; i >= 0; i--) {
			gmarkers[i].remove();
		  }
	}

	function getVehicleAll2() {
		$.ajax({
			url: base_url + "api/adminapi/allmerchant/ok",
			type: "GET",
			success: function (response) {
				if(response.data != undefined) {
					removeMarkers();
				var data_parse = response.data;	
					for (var i = 0; i < data_parse.length; i++) {				
						var lat = data_parse[i].merchant_latitude;
					var lng = data_parse[i].merchant_longitude;
					var online = data_parse[i].merchant_status;
					var nama_driver = data_parse[i].merchant_name;
						popup = new mapboxgl.Popup({ offset: 25 }).setText(nama_driver);
						if (online == "1") var image = base_url + "images/icon/active.png";
						else if (online == "2")
							image = base_url + "images/icon/bekerja.png";
						else if (online == "3")
							image = base_url + "images/icon/bekerja.png";
						else if (online == "4")
							image = base_url + "images/icon/nonactive.png";
						else if (online == "5")
							image = base_url + "images/icon/nonactive.png";
	
							el = document.createElement('div');
						el.className = 'marker';
						el.style.backgroundImage =
						'url('+image+')';
						el.style.width =  '50px';
						el.style.height =  '50px';
	
							
						 gmarker = new mapboxgl.Marker(el)
						.setLngLat([lng, lat])
						.setPopup(popup)
						.addTo(map);
						gmarkers.push(gmarker);
						
					}
			}
			},
		});
	}


function foo() {
	getVehicleAll2();
}

setInterval(foo, 4000);
