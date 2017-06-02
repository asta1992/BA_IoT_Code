function updateAllChildDevices(groupId) {
	bootbox.confirm({
		title : "This action can cause high amount of traffic, are you sure?",
		callback : function(ok) {
			if (ok) {
				$.ajax({
					type : "GET",
					dataType : "json",
					url : "/smartmanager/groups/" + groupId
							+ "/readChildDevices",
					success : function(){
						parent.location.reload();
					},
					error: function(xhr, ajaxOptions, thrownError){
						alert(thrownError);
					}
				});
			}
		}
	})
}

function executeAllChildDevices(groupId) {
	$.ajax({
		type : "GET",
		dataType : "html",
		url : "/smartmanager/groups/executeCommandToChildsFragment",
		success : function(executeToChildsForm) {
			bootbox.confirm({
				size : "large",
				title : "choose your command to execute",
				message : executeToChildsForm,
				callback : function(ok) {
					if (ok) {
						var objectId = parseInt($('#objectDropdown').find(
								":selected").text());
						var objectInstanceId = $('#instanceIdField').val();
						var resourceId = parseInt($('#resourceDropdown').find(
								":selected").text())
						$.ajax({
							type : "POST",
							url : "/smartmanager/groups/" + groupId
									+ "/executeChildDevices/" + objectId + "/"
									+ objectInstanceId + "/" + resourceId,
							error: function(xhr, ajaxOptions, thrownError){
								alert(thrownError);
							}
						})
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
		url : "/smartmanager/groups/writeCommandToChildsFragment",
		success : function(writeToChildsForm) {
			bootbox.confirm({
				size : "large",
				title : "Prepare your command to write",
				message : writeToChildsForm,
				callback : function(ok) {
					if (ok) {
						var objectId = parseInt($('#objectDropdown').find(
								":selected").text());
						var objectInstanceId = $('#instanceIdField').val();
						var resourceId = parseInt($('#resourceDropdown').find(
								":selected").text())
						$.ajax({
							type : "POST",
							data : {
								"value" : $('#writeValue').val()
							},
							url : "/smartmanager/groups/" + groupId
									+ "/writeChildDevices/" + objectId + "/"
									+ objectInstanceId + "/" + resourceId,
							error: function(xhr, ajaxOptions, thrownError){
								alert(thrownError);
							}
						})
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
		url : "/smartmanager/groups/writeConfigToChildsFragment",
		success : function(writeConfigToChildsFragment) {
			bootbox.confirm({
				title : "Write Configuration to Group " + groupName,
				message : writeConfigToChildsFragment,
				callback : function(ok) {
					if (ok) {
						getResult(groupId, function(data) {
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

function getResult(groupId, callback) {
	$.ajax({
		type : "GET",
		dataType : "html",
		data : {
			value : $('#configSelector option:selected').val()
		},
		url : "/smartmanager/groups/" + groupId + "/writeConfiguration",
		success : function(response) {
			callback(response);
		},
		error : function(xhr, ajaxOptions, thrownError) {
			alert(thrownError);
		}
	})
}