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