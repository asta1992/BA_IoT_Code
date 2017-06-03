function addNewRootGroup() {
	bootbox.prompt({
		title : "Please enter a group name",
		callback : function(message){
			if(message){
				$.ajax({
					type: "POST",
					data : {
						value : message
					},
					url : ctx + "/groups/add",
					success : function(data){
						if(data === 'false') {
							bootbox.alert({
								size : "small",
								title : "Error",
								message : "Please choose another Groupname. <br> Max length: 20 <br> Chars: a-z, A-z, 0-9 and _.-",
								callback : function() {
									addNewRootGroup()
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
					success : function(){
						if(data === 'false') {
							bootbox.alert({
								size : "small",
								title : "Error",
								message : "Please choose another Groupname. <br> Max length: 20 <br> Chars: a-z, A-z, 0-9 and _.-",
								callback : function() {
									addNewRootGroup()
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

function openDeviceMemberships(id) {
	$.ajax({
		dataType : "html",
		url : ctx + "/devices/" + id + "/memberships",
		success : function(deviceMemberships) {
			bootbox.confirm({
				message : deviceMemberships,
				callback : function(ok) {
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
							url : ctx + "/devices/" + id + "/changeMembership",
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
			})
			
		},
		error: function(xhr, ajaxOptions, thrownError){
			alert(thrownError);
		}
	})
}
