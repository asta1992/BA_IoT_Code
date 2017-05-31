function initMap() {
	var initLocation = {
		lat : 46.8151,
		lng : 8.22876
	};
	var map = new google.maps.Map(document.getElementById('map'), {
		zoom : 8,
		center : initLocation
	});
	

}

function getLocations(groupId) {
	var url = "";
	if (groupId == null){
		url = "/smartmanager/device/locations/all";
	}
	else {
		url = "/smartmanager/device/locations/" + groupId;
	}
	$.ajax({
		type : "GET",
		dataType : "json",
		url : "/smartmanager/device/allLocations",
		success : function(locations) {
			insertLocations(locations);
		}
	});
}

function insertLocations() {
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