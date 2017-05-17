function deleteDevice(id) {
	console.log(id);
	$.ajax({
		url : "/smartmanager/devices/" + id + "/delete"
	});
}