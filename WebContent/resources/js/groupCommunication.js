function getWriteableResources(){
	var objectId = parseInt($('#objectDropdown').find(":selected").text());
	var resourceDropdown = $('#resourceDropdown');
	$.ajax({
		type: "GET",
		url : "/smartmanager/group/" + objectId + "/writeToChildren",
		success : function(resources) {
			
			resourceDropdown.empty();
			resources.forEach(function(entry){
				resourceDropdown.append('<option value="' + entry.resourceModel.id + '">' + entry.resourceModel.id + " " + "(" +entry.resourceModel.name + ")" + '</option>');
			})
			resourceDropdown.selectpicker('refresh');
		}
	})
}

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
		url : "/smartmanager/groups/writeToChilds",
		success : function(writeToChildsForm) {
			bootbox.confirm({
				size: "large",
				title : "Prepare your command to write",
				message : writeToChildsForm,
				callback : function(ok) {
					if(ok){
						alert($('.bootstrap-select > .dropdown-toggle'));
					}
				}
			})
		}
	})
	
}