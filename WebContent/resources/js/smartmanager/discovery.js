function addDevice(deviceId){
	var groupId = $('#groupSelector option:selected').attr('id');
	var configId = $('#configSelector option:selected').attr('id');
	console.log($('#groupSelector option:selected').text());
	$.ajax({
		type : "POST",
		data : {
			groupId : groupId,
			configId: configId
		},
		url : "/smartmanager/devices/" + deviceId + "/add",
		success : function() {
			window.location.reload();
		},
		error : function(xhr, ajaxOptions, thrownError) {
			window.location.reload();
			alert(thrownError);
		}
	});
	
}
