function addDevice(){
	var groupId = $('#groupSelector option:selected').attr('id');
	var configId = $('#configSelector option:selected').attr('id');
	var deviceIds = new Array();
	
	$('.discoveryCheckbox').each(function(){
		if($(this).is(':checked')){
			deviceIds.push($(this).val());
		}
	})
	
	console.log(groupId);
	console.log(configId);
	console.log(deviceIds);


	$.ajax({
		type : "POST",
		data : {
			groupId : groupId,
			configId: configId,
			deviceIds: deviceIds
		},
		url : ctx + "/devices/add",
		success : function() {
			window.location.reload();
		},
		error : function(xhr, ajaxOptions, thrownError) {
			window.location.reload();
			alert(thrownError);
		}
	});
}

