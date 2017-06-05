function addNewChildGroup(parentId) {
	bootbox.prompt({
		title : "Please enter a group name",
		callback : function(message){
			if(message){
				$.ajax({
					type: "POST",
					data : {
						value : message
					},
					url : ctx + "/groups/" + parentId + "/add",
					success : function(data){
						if(data === 'false') {
							bootbox.alert({
								size : "small",
								title : "Error",
								message : "Please choose another Groupname. <br> Max length: 20 <br> Chars: a-z, A-z, 0-9 and _.-",
								callback : function() {
									addNewChildGroup();
								}
							});
						}
						else {
							parent.location.reload();
						}
					},
					error: function(xhr, ajaxOptions, thrownError){
						parent.location.reload();
						alert(thrownError);
					}
				});
			}
		}
	});
}

function openGroupMemberships(id) {
	$.ajax({
		dataType : "html",
		url : ctx + "/groups/" + id + "/memberships",
		success : function(groupMemberships) {
			bootbox.confirm({
				message : groupMemberships,
				callback : function(ok) {
					if(ok){
						var updatedMemberships = [];
						$('#bootstrap-duallistbox-selected-list_groups-duallistbox > option').each(function() {
							updatedMemberships.push(this.id);
						});

						$.ajax({
							type : "POST",
							data : {
								value : JSON.stringify(updatedMemberships)
							},
							url : ctx + "/groups/" + id + "/changeMembership",
							success : function(){
								parent.location.reload();
							},
							error: function(xhr, ajaxOptions, thrownError){
								parent.location.reload();
								alert(thrownError);
							}
						});
					}
				}
			});
		},
		error: function(xhr, ajaxOptions, thrownError){
			alert(thrownError);
		}
	});
}

function openGroupMembers(id){
	$.ajax({
		dataType: "html",
		url : ctx + "/groups/" + id + "/members",
		success : function(groupMembers) {
			bootbox.confirm({
				message: groupMembers,
				callback: function(ok) {
					if(ok) {
						var updatedMemberships = [];
						$('#bootstrap-duallistbox-selected-list_groups-duallistbox > option').each(function() {
							updatedMemberships.push(this.id);
						});

						$.ajax({
							type : "POST",
							data : {
								value : JSON.stringify(updatedMemberships)
							},
							url : ctx + "/groups/" + id + "/changeMembers",
							success : function(){
								parent.location.reload();
							},
							error: function(xhr, ajaxOptions, thrownError){
								parent.location.reload();
								alert(thrownError);
							}
						});
					}
				}
			});
			
		},
		error: function(xhr, ajaxOptions, thrownError){
			alert(thrownError);
		}
	});
}

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
						var resourceId = parseInt($('#resourceDropdown').find(":selected").text());
						
						getExecuteResult(groupId, objectId, objectInstanceId, resourceId, function(data) {
							bootbox.alert({
								title : "Your Results",
								message : data,
							});
						});
					}
				}
			});
		},
		error: function(xhr, ajaxOptions, thrownError){
			alert(thrownError);
		}
	});
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
			});
		},
		error: function(xhr, ajaxOptions, thrownError){
			alert(thrownError);
		}
	});
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
	});
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
	});
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
	});
}

function getExecuteableResources() {
	var objectId = parseInt($('#objectDropdown').find(":selected").text());
	var instanceIdField = $('#instanceIdField');
	$.ajax({
		type : "GET",
		url : ctx + "/groups/" + objectId + "/multiInstance",
		success : function(multiInstance) {
			if (multiInstance.value) {
				instanceIdField.prop('disabled', false);
			} else {
				instanceIdField.val("0");
				instanceIdField.prop('disabled', true);
			}
			updateCompleteObjectId();
		}
	});

	var resourceDropdown = $('#resourceDropdown');
	$.ajax({
		type : "GET",
		url : ctx + "/groups/" + objectId + "/executeToChildren",
		success : function(resources) {
			resourceDropdown.empty();
			resources.forEach(function(entry) {
				resourceDropdown.append('<option value="'
						+ entry.resourceModel.id + '">'
						+ entry.resourceModel.id + " " + "("
						+ entry.resourceModel.name + ")" + '</option>');
			});
			resourceDropdown.selectpicker('refresh');
			updateCompleteObjectId();
		},
		error: function(xhr, ajaxOptions, thrownError){
			alert(thrownError);
		}
	});
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
	});
}