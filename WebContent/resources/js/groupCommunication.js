function updateCompleteObjectId() {
	$('#completeObjectId').html(parseInt($('#objectDropdown').find(":selected").text()) + "/" + $('#instanceIdField').val() + "/" + parseInt($('#resourceDropdown').find(":selected").text()));
}

function getWriteableResources(){
	var objectId = parseInt($('#objectDropdown').find(":selected").text());
	var instanceIdField = $('#instanceIdField');
	$.ajax({
		type: "GET",
		url: "/smartmanager/group/" + objectId + "/multiInstance",
		success : function(multiInstance){
			if(multiInstance.value){
				instanceIdField.prop('disabled', false);
			}
			else {
				instanceIdField.val("0");
				instanceIdField.prop('disabled', true);
			}
			updateCompleteObjectId();
		}
	})
	
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
			updateCompleteObjectId();
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
						alert("Writing " + $('#writeValue').val() + " to " +  $('#completeObjectId').text);
					}
				}
			})
		}
	})
	
}