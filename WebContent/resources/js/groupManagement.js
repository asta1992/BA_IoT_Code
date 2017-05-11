function openGroupManagement(id) {
	$.ajax({
		dataType : "html",
		url : "/smartmanager/devices/"+id+"/memberships",
		success : function(data) {
			
			
			bootbox.confirm({
				message : data,
				callback : function(data) {
					
					
				}
			});
		}
	});
	
	
}
