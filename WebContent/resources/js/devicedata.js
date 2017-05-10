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

function readData(url, objectLink) {
	$.ajax({
		dataType : "json",
		url : url,
		success : function(data) {
			$("#readResponse" + objectLink).text(data.content.value);
		}
	});
}

function writeData(url, objectLink, type) {
	var inputType = '';
	console.log(type)
	switch(type) {
    	case "STRING":
    		inputType = 'text';
    		break;
    	case "INTEGER":
    		inputType = 'number';
    		break;
    	case "FLOAT":
    		inputType = 'number';
    		break;
    	case "BOOLEAN":
    		inputType = 'text';
    		break;
    	case "OPAQUE":
    		inputType = 'text';
    		break;
    	case "TIME":
    		inputType = 'text';
    		break;
    	case "OBJLNK":
    		inputType = 'text';
    		break;
    	default:
    		inputType = 'text';
	}
	
	bootbox.prompt({
		title : "Please enter the value",
		inputType : 'text',
		callback : function(result) {
			$.ajax({
				type : "POST",
				dataType : "json",
				data : {
					"postValue" : result
				},
				url : url,
				success : function(data) {
					$("#writeResponse" + objectLink).hide();
					$("#readResponse" + objectLink).text(result);
					$("#writeResponse" + objectLink).text(
							data.coapResponse.code);
					$("#writeResponse" + objectLink).fadeIn("slow").delay(2000)
							.fadeOut('slow');
				}
			});
		}
	});
}

function execute(url, objectLink) {
	$.ajax({
		dataType : "json",
		url : url,
		success : function(data) {
			$("#writeResponse" + objectLink).hide();
			$("#writeResponse" + objectLink).text("Accomplished!");
			$("#writeResponse" + objectLink).fadeIn("slow").delay(2000)
					.fadeOut('slow');
		}
	});
}