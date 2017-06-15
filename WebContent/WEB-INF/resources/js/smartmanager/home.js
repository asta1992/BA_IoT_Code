function deleteAllUnreachableDevices() {
	bootbox.confirm({
		message : "Do you really want to delete all unreachable devices?",
		callback : function(ok) {
			if (ok) {
				$.ajax({
					type : "DELETE",
					url : ctx + "/devices/deleteAll",
					success : function() {
						window.location.href = ctx + "/";
					},
					error : function(xhr, ajaxOptions, thrownError) {
						window.location.href = ctx + "/";
						alert(thrownError);
					}
				});
			}
		}
	});
}
         
function deleteUnreachableDevice(id, name) {
	bootbox.confirm({
		message : "Do you really want to delete device " + name + "?",
		callback : function(ok) {
			if (ok) {
				$.ajax({
					type : "DELETE",
					url : ctx + "/devices/" + id + "/delete",
					success : function() {
						window.location.href = ctx + "/";
					},
					error : function(xhr, ajaxOptions, thrownError) {
						window.location.href = ctx + "/";
						alert(thrownError);
					}
				});
			}
		}
	});
}