function readData(url, objectLink) {
	$.ajax({
		dataType : "json",
		url : url,
		success : function(data) {
			if (data.code == "CONTENT") {
				$("#readResponse" + objectLink).text(data.content.value);
			} else {
				alert(data.code);
			}
		}
	});
}

function readAll() {
	$('button[id^="btn-read-multiple"]').click();
}

function readMultiple(url) {
	var objectLink = "";
	$.ajax({
		dataType : "json",
		url : url,
		success : function(data) {
			if (data.code == "CONTENT") {
				for (i in data.content.instances[0].resources) {
					objectLink = data.content.id + "0"
							+ data.content.instances[0].resources[i].id;
					$("#readResponse" + objectLink).text(
							data.content.instances[0].resources[i].value);
				}
			} else {
				alert(data.code);
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
					console.log("Wert: " + result);
					console.log("URL: " + url);
					$.ajax({
						type : "POST",
						dataType : "json",
						data : {
							"value" : result
						},
						url : url,
						success : function(data) {
							$("#readResponse" + objectLink).text(result);
							$("#writeResponse" + objectLink).text(
									data.coapResponse.code);
							$("#writeResponse" + objectLink).fadeIn("slow")
									.delay(2000).fadeOut('slow');
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
			$("#writeResponse" + objectLink).text("Accomplished!");
			$("#writeResponse" + objectLink).fadeIn("slow").delay(1000)
					.fadeOut('slow');
		}
	});
}