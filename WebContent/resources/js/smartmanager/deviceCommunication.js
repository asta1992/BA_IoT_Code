function readData(url, objectLink) {
	$.ajax({
		dataType : "json",
		url : url,
		success : function(data) {
			if (data.code == "CONTENT") {
				$("#readResponse" + objectLink).text(data.content.value);
			} else {
				bootbox.alert({
					title : "Error",
					message : "ResponseCode: " + data.code + "<br> ErrorMessage: " + data.errorMessage + "<br> CoapResponse: " + data.coapResponse,
				});
			}
		}
	});
}

function readAll() {
	$('button[id^="btn-read-multiple"]').click();
}

function cutInstanceId(url) {
	const regex = /([\d\D]*\/[0-9]*)\//g;
	let m;
	var newUrl;

	while ((m = regex.exec(url)) !== null) {
	    if (m.index === regex.lastIndex) {
	        regex.lastIndex++;
	    }
	    
	    m.forEach((match, groupIndex) => {
	    	newUrl = match;
	    });
	}
	
	return newUrl;
}

function readMultiple(url) {
	var url = cutInstanceId(url)
	var objectLink = "";
	$.ajax({
		dataType : "json",
		url : url,
		success : function(data) {
			if (data.code == "CONTENT") {
				for(i in data.content.instances) {
					for (j in data.content.instances[i].resources) {
						objectLink = data.content.id + i + data.content.instances[i].resources[j].id;
						$("#readResponse" + objectLink).text(data.content.instances[i].resources[j].value);
					}
				}
				
			} else {
				bootbox.alert({
					title : "Error",
					message : "ResponseCode: " + data.code + "<br> ErrorMessage: " + data.errorMessage + "<br> CoapResponse: " + data.coapResponse,
				});
			}
		}
	});
}

function writeData(url, objectLink, type) {
	readUrl = url.replace('write', 'read');
	$.ajax({
		dataType : "json",
		url : readUrl,
		success : function(data) {
			var defaultValue = "";
			if (data.code == "CONTENT") {
				defaultValue = data.content.value;
			} else {
				defaultValue = data.code;
			}
			
			bootbox.prompt({
				title : "Please enter the value",
				inputType : 'text',
				value : defaultValue,
				callback : function(result) {
					switch (type) {
					case "STRING":

						break;
					case "INTEGER":
						if (!/^-?\d*\.?\d+$/.test(result)) {
							console.log("Wrong input!");
							return;
						}
						break;
					case "FLOAT":
						if (!/^[+-]?\d+(\.\d+)?$/.test(result)) {
							console.log("Wrong input!");
							return;
						}
						break;
					case "BOOLEAN":

						break;
					case "OPAQUE":

						break;
					case "TIME":

						break;
					case "OBJLNK":

						break;
					default:

					}
					$.ajax({
						type : "POST",
						dataType : "json",
						data : {
							value : result
						},
						url : url,
						success : function(data) {
							if (data.code == "CHANGED") {
								$("#readResponse" + objectLink).text(result);
								$("#writeResponse" + objectLink).text(Object.values(data));
								$("#writeResponse" + objectLink).fadeIn("slow").delay(2000).fadeOut('slow');
								
							} else {
								bootbox.alert({
									title : "Error",
									message : "ResponseCode: " + data.code + "<br> ErrorMessage: " + data.errorMessage + "<br> CoapResponse: " + data.coapResponse,
								});
							}
						}
					});
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
			if (data.code == "CHANGED") {
				$("#writeResponse" + objectLink).text("Accomplished!");
				$("#writeResponse" + objectLink).fadeIn("slow").delay(1000).fadeOut('slow');
				
			} else {
				bootbox.alert({
					title : "Error",
					message : "ResponseCode: " + data.code + "<br> ErrorMessage: " + data.errorMessage + "<br> CoapResponse: " + data.coapResponse,
					callback : function() {	}
				});
			}
		}
	});
}
