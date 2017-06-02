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
	})
}

function deleteGroup(id, name) {
	bootbox.confirm({
		message : "Do you really want to delete group " + name + "?",
		callback : function(ok){
			if(ok){
				$.ajax({
					type: "DELETE",
					url : ctx + "/groups/" + id + "/delete",
					success : function(){
						window.location.href = ctx + "/devices";
					},
					error: function(xhr, ajaxOptions, thrownError){
						window.location.href = ctx + "/devices";
						alert(thrownError);
					}
				});
			}
		}
	})
}

