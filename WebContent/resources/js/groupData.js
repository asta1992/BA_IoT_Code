function updateAllChildDevices(groupId) {
	bootbox.confirm({
		title : "This action can cause high amount of traffic, are you sure?",
		callback : function(ok){
			if(ok){
				$.ajax({
					type: "GET",
					dataType : "json",
					url : "/smartmanager/groups/" + groupId + "/readChildDevices",
				});
			}
		}
	})
}

function writeAllChildDevices(groupId) {
	$.ajax({
		type: "GET",
		dataType : "html",
		url : "/smartmanager/groups/writeToChilds",
		success : function(writeToChildsForm) {
			bootbox.confirm({
				title : "Prepare your command to write",
				message : writeToChildsForm,
				callback : function(ok) {
					if(ok){
						
					}
				}
			})
		}
	})
	
}