function addDevice(deviceId){
	var groupId = $('#groupSelector option:selected').attr('id');
	var configId = $('#configSelector option:selected').attr('id');
	console.log($('#groupSelector option:selected').text());
	$.ajax({
		type : "POST",
		dataType : "json",
		data : {
			groupId : groupId,
			configId: configId
		},
		url : "/smartmanager/devices/" + deviceId + "/add"
	});
	window.location.href = "/smartmanager/discovery"
	
}
