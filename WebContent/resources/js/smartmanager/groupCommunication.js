function executeAllChildDevices(groupId) {
	$.ajax({
		type : "GET",
		dataType : "html",
		url : ctx + "/groups/executeCommandToChildsFragment",
		success : function(executeToChildsForm) {
			bootbox.confirm({
				size : "large",
				title : "choose your command to execute",
				message : executeToChildsForm,
				callback : function(ok) {
					if (ok) {
						var objectId = parseInt($('#objectDropdown').find(":selected").text());
						var objectInstanceId = $('#instanceIdField').val();
						var resourceId = parseInt($('#resourceDropdown').find(":selected").text())
						
						getExecuteResult(groupId, objectId, objectInstanceId, resourceId, function(data) {
							bootbox.alert({
								title : "Your Results",
								message : data,
							});
						});
					}
				}
			})
		},
		error: function(xhr, ajaxOptions, thrownError){
			alert(thrownError);
		}
	})
}

function writeAllChildDevices(groupId) {
	$.ajax({
		type : "GET",
		dataType : "html",
		url : ctx + "/groups/writeCommandToChildsFragment",
		success : function(writeToChildsForm) {
			bootbox.confirm({
				size : "large",
				title : "Prepare your command to write",
				message : writeToChildsForm,
				callback : function(ok) {
					if (ok) {
						var objectId = parseInt($('#objectDropdown').find(":selected").text());
						var objectInstanceId = $('#instanceIdField').val();
						var resourceId = parseInt($('#resourceDropdown').find(":selected").text());
						var value = $('#writeValue').val();
						
						getWriteResult(groupId, objectId, objectInstanceId, resourceId, value, function(data) {
							bootbox.alert({
								title : "Your Results",
								message : data,
							});
						});
					}
				}
			})
		},
		error: function(xhr, ajaxOptions, thrownError){
			alert(thrownError);
		}
	})
}

function writeConfigToChildDevices(groupId, groupName) {
	$.ajax({
		type : "GET",
		dataType : "html",
		url : ctx + "/groups/writeConfigToChildsFragment",
		success : function(writeConfigToChildsFragment) {
			bootbox.confirm({
				title : "Write Configuration to Group " + groupName,
				message : writeConfigToChildsFragment,
				callback : function(ok) {
					if (ok) {
						getConfigResult(groupId, function(data) {
							bootbox.alert({
								title : "Your Results",
								message : data,
							});
						});
					}
				}
			});
		},
		error : function(xhr, ajaxOptions, thrownError){
			alert(thrownError);
		}
	});
}

function getExecuteResult(groupId, objectId, instanceId, resourceId, callback) {
	$.ajax({
		type : "GET",
		dataType : "html",
		url : ctx + "/groups/" + groupId + "/executeChildDevices/" + objectId + "/" + instanceId + "/" + resourceId,
		success : function(response) {
			callback(response);
		},
		error : function(xhr, ajaxOptions, thrownError) {
			alert(thrownError);
		}
	})
}

function getWriteResult(groupId, objectId, instanceId, resourceId, value, callback) {
	$.ajax({
		type : "GET",
		dataType : "html",
		data : {
			value : value
		},
		url : ctx + "/groups/" + groupId + "/writeChildDevices/" + objectId + "/" + instanceId + "/" + resourceId,
		success : function(response) {
			callback(response);
		},
		error : function(xhr, ajaxOptions, thrownError) {
			alert(thrownError);
		}
	})
}

function getConfigResult(groupId, callback) {
	$.ajax({
		type : "GET",
		dataType : "html",
		data : {
			value : $('#configSelector option:selected').val()
		},
		url : ctx + "/groups/" + groupId + "/writeConfiguration",
		success : function(response) {
			callback(response);
		},
		error : function(xhr, ajaxOptions, thrownError) {
			alert(thrownError);
		}
	})
}