function deleteDevice(id, name) {
	bootbox.confirm({
		title : "Do you really want to delete device " + name + "?",
		callback : function(message){
			if(message){
				$.ajax({
					type: "DELETE",
					dataType : "json",
					data : {
						value : JSON.stringify(message)
					},
					url : "/smartmanager/devices/"+id+"/delete",
				});
			}
		}
	})
}

function deleteGroup(id, name) {
	bootbox.confirm({
		message : "Do you really want to delete group " + name + "?",
		callback : function(message){
			if(message){
				$.ajax({
					type: "DELETE",
					dataType : "json",
					data : {
						value : JSON.stringify(message)
					},
					url : "/smartmanager/groups/"+id+"/delete",
				});
			}
		}
	})
}

