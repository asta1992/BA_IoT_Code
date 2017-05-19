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
		url : "/smartmanager/groups/writeToChildsFragment",
		success : function(writeToChildsForm) {
			bootbox.confirm({
				size: "large",
				title : "Prepare your command to write",
				message : writeToChildsForm,
				callback : function(ok) {
					if(ok){
						var objectId = parseInt($('#objectDropdown').find(":selected").text());
						var objectInstanceId = $('#instanceIdField').val();
						var resourceId = parseInt($('#resourceDropdown').find(":selected").text())
						$.ajax({
							type: "POST",
							dataType : "json",
							data : {
								"value" : $('#writeValue').val()
							},
							url : "/smartmanager/groups/" + groupId + "/writeChildDevices/" + objectId + "/" + objectInstanceId + "/" + resourceId,
							success : function(result) {
								alert("Here comes the Result!");
							}
						})
					}
				}
			})
		}
	})
}