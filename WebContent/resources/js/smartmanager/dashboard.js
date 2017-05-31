function initMap(){
	var uluru = {lat: 46.8151, lng: 8.22876};
    var map = new google.maps.Map(document.getElementById('map'), {
      zoom: 8,
      center: uluru
    });
    /*var marker = new google.maps.Marker({
      position: uluru,
      map: map
    });*/
}