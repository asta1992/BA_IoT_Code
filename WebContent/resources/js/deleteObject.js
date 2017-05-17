function deleteDevice(id, name) {
	bootbox.confirm({
		title : "Do you really want to delete device " + name + "?",
		callback : function(ok){
			if(message){
				$.ajax({
					type: "DELETE",
					url : "/smartmanager/devices/" + id + "/delete",
				});
			}
		}
	})
}

function deleteGroup(id, name) {
	bootbox.confirm({
		message : "Do you really want to delete group " + name + "?",
		callback : function(ok){
			if(message){
				$.ajax({
					type: "DELETE",
					url : "/smartmanager/groups/" + id + "/delete",
				});
			}
		}
	})
}

