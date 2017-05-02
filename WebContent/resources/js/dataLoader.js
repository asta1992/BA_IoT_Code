function getData(url) {
	$.ajax({ 
	    type: "GET",
	    dataType: "json",
	    url: url,
	    success: function(data) {
	       console.log(data)
	    }
	}); 
}