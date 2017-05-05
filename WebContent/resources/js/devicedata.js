function post(path, params, method) {
	method = method || "post";

	var form = document.createElement("form");
	form.setAttribute("method", method);
	form.setAttribute("action", path);

	for ( var key in params) {
		if (params.hasOwnProperty(key)) {
			var hiddenField = document.createElement("input");
			hiddenField.setAttribute("type", "hidden");
			hiddenField.setAttribute("name", key);
			hiddenField.setAttribute("value", params[key]);

			form.appendChild(hiddenField);
		}
	}

	document.body.appendChild(form);
	form.submit();
}

function getData(url, id) {
	$.ajax({
		dataType : "json",
		url : url,
		success : function(data) {
			$("#" + id).text(data.content.value);
		}
	});
}

function writeData(url, id) {
	bootbox.prompt("Please enter the value", function(result) {
		$.ajax({
			type : "POST",
			dataType : "json",
			data : {
				"postValue" : result
			},
			url : url,
			success : function(data) {
				$("#res" + id).hide();
				$("#" + id).text(result);
				$("#res" + id).text(data.coapResponse.code);
				$("#res" + id).fadeIn("slow").delay(2000).fadeOut('slow');
			}
		});
	});
}

function execute(url, id) {
	$.ajax({
		dataType : "json",
		url : url,
		success : function(data) {
			$("#res" + id).hide();
			$("#res" + id).text("Mission accomplished!");
			$("#res" + id).fadeIn("slow").delay(2000).fadeOut('slow');
		}
	});
}