function addNewRootGroup() {
	bootbox.prompt({
		title : "Please enter a group name",
		callback : function(ok){
			if(ok){
				$.ajax({
					type: "POST",
					data : {
						value : JSON.stringify(message)
					},
					url : "/smartmanager/groups/add",
					success : function(){
						parent.location.reload();
					},
					error: function(xhr, ajaxOptions, thrownError){
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
		callback : function(ok){
			if(ok){
				$.ajax({
					type: "POST",
					data : {
						value : JSON.stringify(message)
					},
					url : "/smartmanager/groups/" + parentId + "/add",
					success : function(){
						parent.location.reload();
					},
					error: function(xhr, ajaxOptions, thrownError){
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
		url : "/smartmanager/devices/" + id + "/memberships",
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
							url : "/smartmanager/devices/" + id + "/changeMembership",
							success : function(){
								parent.location.reload();
							},
							error: function(xhr, ajaxOptions, thrownError){
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
		url : "/smartmanager/groups/" + id + "/memberships",
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
							url : "/smartmanager/groups/" + id + "/changeMembership",
							success : function(){
								parent.location.reload();
							},
							error: function(xhr, ajaxOptions, thrownError){
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
		url : "/smartmanager/groups/" + id + "/members",
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
							url : "/smartmanager/groups/" + id + "/changeMembers",
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
			
		},
		error: function(xhr, ajaxOptions, thrownError){
			alert(thrownError);
		}
	})
}
