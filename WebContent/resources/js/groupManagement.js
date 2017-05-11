function openGroupManagement(id) {
	$.ajax({
		dataType : "html",
		url : "/smartmanager/devices/"+id+"/memberships",
		success : function(data) {
			bootbox.confirm({
				message : data,
				callback : function(data) {
					var updatedMemberships = {};
					$( '#bootstrap-duallistbox-selected-list_groups-duallistbox > option').each(function() {
					    updatedMemberships += JSON.stringify(this.id);
					    });
					console.log(JSON.stringify(updatedMemberships));
				}
			});
		}
	});
	
	
}
