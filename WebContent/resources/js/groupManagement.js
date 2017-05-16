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
				url : "/smartmanager/groups/add",
			});
		}
	});
}

function addNewChildGroup(parentId) {
	bootbox.prompt({
		title : "Please enter a group name",
		callback : function(message){
			$.ajax({
				type: "POST",
				dataType : "json",
				data : {
					value : JSON.stringify(message)
				},
				url : "/smartmanager/groups/"+parentId+"/add",
			});
		}
	});
}

function openDeviceMemberships(id) {
	$.ajax({
		dataType : "html",
		url : "/smartmanager/devices/" + id + "/memberships",
		success : function(data) {
			bootbox.confirm({
				message : data,
				callback : function(data) {
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
				}
			});
		}
	});
}

function openGroupMemberships(id) {
	$.ajax({
		dataType : "html",
		url : "/smartmanager/groups/" + id + "/memberships",
		success : function(data) {
			bootbox.confirm({
				message : data,
				callback : function(data) {
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
				}
			});
		}
	});
}
