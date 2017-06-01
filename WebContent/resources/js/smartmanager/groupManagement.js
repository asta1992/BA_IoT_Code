function addNewRootGroup() {
	bootbox.prompt({
		title : "Please enter a group name",
		callback : function(message){
			$.ajax({
				type: "POST",
				dataType : "json",
				data : {
					value : JSON.stringify(message)
				},
				url : "/smartmanager/groups/add"
			});
			parent.location.reload();
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
					dataType : "json",
					data : {
						value : JSON.stringify(message)
					},
					url : "/smartmanager/groups/" + parentId + "/add",
				});
				parent.location.reload();
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
				callback : function() {
					var updatedMemberships = [];
					$('#bootstrap-duallistbox-selected-list_groups-duallistbox > option').each(function() {
						updatedMemberships.push(this.id);
					});
					$.ajax({
						type : "POST",
						dataType : "json",
						data : {
							value : JSON.stringify(updatedMemberships)
						},
						url : "/smartmanager/devices/" + id + "/changeMembership"
					});
					parent.location.reload();
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
				callback : function() {
					var updatedMemberships = [];
					$('#bootstrap-duallistbox-selected-list_groups-duallistbox > option').each(function() {
						updatedMemberships.push(this.id);
					});

					$.ajax({
						type : "POST",
						dataType : "json",
						data : {
							value : JSON.stringify(updatedMemberships)
						},
						url : "/smartmanager/groups/" + id + "/changeMembership"
					});
					parent.location.reload();
				}
			});
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
				callback: function() {
					var updatedMemberships = [];
					$('#bootstrap-duallistbox-selected-list_groups-duallistbox > option').each(function() {
						updatedMemberships.push(this.id);
					});

					$.ajax({
						type : "POST",
						dataType : "json",
						data : {
							value : JSON.stringify(updatedMemberships)
						},
						url : "/smartmanager/groups/" + id + "/changeMembers"
					});
					parent.location.reload();
				}
			})
			
		}
	})
}
