function deleteDevice(id, name) {
	bootbox.confirm({
		message : "Do you really want to delete device " + name + "?",
		callback : function(ok){
			if(ok){
				$.ajax({
					type: "DELETE",
					url : "/smartmanager/devices/" + id + "/delete",
					
				});
				window.location.href = "/smartmanager/devices"
			}
		}
	})
}

function deleteGroup(id, name) {
	bootbox.confirm({
		message : "Do you really want to delete group " + name + "?",
		callback : function(ok){
			if(ok){
				$.ajax({
					type: "DELETE",
					url : "/smartmanager/groups/" + id + "/delete"
				});
				window.location.href = "/smartmanager/devices"
			}
		}
	})
}

