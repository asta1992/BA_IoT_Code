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
		},
		error: function(xhr, ajaxOptions, thrownError){
			alert(thrownError);
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
	url = cutInstanceId(url);
	var objectLink = "";
	$.ajax({
		dataType : "json",
		url : url,
		success : function(data) {
			if (data.code == "CONTENT") {
				for(var i in data.content.instances) {
					for (var j in data.content.instances[i].resources) {
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
		},
		error: function(xhr, ajaxOptions, thrownError){
			alert(thrownError);
		}
	});
}

function writeData(url, objectLink, type) {
	var readUrl = url.replace('write', 'read');
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
					if(result) {
						switch (type) {
						case "STRING":

							break;
						case "INTEGER":
							if (!/^-?\d*\.?\d+$/.test(result)) {
								return;
							}
							break;
						case "FLOAT":
							if (!/^[+-]?\d+(\.\d+)?$/.test(result)) {
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
								if (Object.values(data)[0] === "CHANGED") {
									$("#readResponse" + objectLink).text(result);
									$("#writeResponse" + objectLink).text(Object.values(Object.values(data)));
									$("#writeResponse" + objectLink).fadeIn("slow").delay(2000).fadeOut('slow');
									
								} else {
									bootbox.alert({
										title : "Error",
										message : "ResponseCode: " + Object.values(data)[0] + "<br> ErrorMessage: " + data.errorMessage + "<br> CoapResponse: " + data.coapResponse,
									});
								}
							},
							error: function(xhr, ajaxOptions, thrownError){
								alert(thrownError);
							}
						});
					}
		
				}
			});
		},
		error: function(xhr, ajaxOptions, thrownError){
			alert(thrownError);
		}
	});

	}


function execute(url, objectLink) {
	$.ajax({
		dataType : "json",
		url : url,
		success : function(data) {
			if (Object.values(data)[0] === "CHANGED") {
				$("#writeResponse" + objectLink).text("Accomplished!");
				$("#writeResponse" + objectLink).fadeIn("slow").delay(1000).fadeOut('slow');
				
			} else {
				bootbox.alert({
					title : "Error",
					message : "ResponseCode: " + Object.values(data) + "<br> ErrorMessage: " + data.errorMessage + "<br> CoapResponse: " + data.coapResponse,
					callback : function() {	}
				});
			}
		},
		error: function(xhr, ajaxOptions, thrownError){
			alert(thrownError);
		}
	});
}

function openDeviceMemberships(id) {
	$.ajax({
		dataType : "html",
		url : ctx + "/devices/" + id + "/memberships",
		success : function(deviceMemberships) {
			bootbox.confirm({
				message : deviceMemberships,
				callback : function(ok) {
					if(ok) {
						var updatedMemberships = [];
						$('#bootstrap-duallistbox-selected-list_groups-duallistbox > option').each(function() {
							updatedMemberships.push(this.id);
						});
						$.ajax({
							type : "POST",
							data : {
								value : JSON.stringify(updatedMemberships)
							},
							url : ctx + "/devices/" + id + "/changeMembership",
							success : function(){
								parent.location.reload();
							},
							error: function(xhr, ajaxOptions, thrownError){
								parent.location.reload();
								alert(thrownError);
							}
						});
					}
				}
			});
		}
	});
}

function deleteDevice(id, name) {
	bootbox.confirm({
		message : "Do you really want to delete device " + name + "?",
		callback : function(ok){
			if(ok){
				$.ajax({
					type: "DELETE",
					url : ctx + "/devices/" + id + "/delete",
					success: function() {
						window.location.href = ctx + "/devices";
					},
					error: function(xhr, ajaxOptions, thrownError){
						window.location.href = ctx + "/devices";
						alert(thrownError);
					}
				});
			}
		}
	});
}