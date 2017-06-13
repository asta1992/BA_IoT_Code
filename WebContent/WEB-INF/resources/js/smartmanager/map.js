function initMap() {
	var initLocation = {
		lat : 46.9151,
		lng : 8.22876
	};
	var map = new google.maps.Map(document.getElementById('map'), {
		zoom : 4,
		center : initLocation
	});

	getLocations(map);
}

function getLocations(map) {
	var componentId = $('#componentId').text();
	var mapType = "";
	const regex = /map-([a-z]*)/g;
	const str = document.getElementById('map').className;
	let m;

	while ((m = regex.exec(str)) !== null) {
		if (m.index === regex.lastIndex) {
			regex.lastIndex++;
		}
		mapType = m[1];
	}
	var url = "";
	if (componentId === null) {
		url = ctx + "/devices/locations/" + mapType;
	} else {
		url = ctx + "/devices/"+ componentId + "/locations/" + mapType;
	}
	$.ajax({
		type : "GET",
		dataType : "json",
		url : url,
		success : function(locations) {
			insertLocations(map, locations);
		},
		error : function(xhr, ajaxOptions, thrownError) {
			alert(thrownError);
		}
	});
}

function insertLocations(map, locations) {
	var infowindow = new google.maps.InfoWindow();

	var marker, i;

	if (locations.length !== 0) {
		var newCenter = new google.maps.LatLng(locations[0][1], locations[0][2]);
		map.setCenter(newCenter);

		for (i = 0; i < locations.length; i++) {
			marker = new google.maps.Marker({
				position : new google.maps.LatLng(locations[i][1],
						locations[i][2]),
				map : map
			});

			google.maps.event.addListener(marker, 'click',
					(function(marker, i) {
						return function() {
							infowindow.setContent(locations[i][0]);
							infowindow.open(map, marker);
						};
					})(marker, i));
		}
	}
}