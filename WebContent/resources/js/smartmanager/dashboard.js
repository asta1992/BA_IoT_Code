function initMap() {
	var initLocation = {
		lat : 46.9151,
		lng : 8.22876
	};
	var map = new google.maps.Map(document.getElementById('map'), {
		zoom : 8,
		center : initLocation
	});
	getLocations(map);
	

}

function getLocations(map, groupId) {
	
	var url = "";
	if (groupId == null){
		url = "/smartmanager/devices/locations/all";
	}
	else {
		url = "/smartmanager/devices/locations/" + groupId;
	}
	$.ajax({
		type : "GET",
		dataType : "json",
		url : url,
		success : function(locations) {
			insertLocations(map, locations);
		}
	});
}

function insertLocations(map, locations) {
	var infowindow = new google.maps.InfoWindow();

	var marker, i;

	for (i = 0; i < locations.length; i++) {
		marker = new google.maps.Marker({
			position : new google.maps.LatLng(locations[i][1], locations[i][2]),
			map : map
		});

		google.maps.event.addListener(marker, 'click', (function(marker, i) {
			return function() {
				infowindow.setContent(locations[i][0]);
				infowindow.open(map, marker);
			}
		})(marker, i));
	}
}