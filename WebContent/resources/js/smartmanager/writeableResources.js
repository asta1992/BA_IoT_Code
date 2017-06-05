function getWriteableResources() {
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
		url : ctx + "/groups/" + objectId + "/writeToChildren",
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
		}
	});
}

function updateCompleteObjectId() {
	var objectId = parseInt($('#objectDropdown').find(":selected").text());
	var instanceId = $('#instanceIdField').val();
	if(instanceId === ''){
		instanceId = 0;
	}
	resourceId = parseInt($('#resourceDropdown').find(":selected").text());
	
	$('#completeObjectId').html(objectId + "/" + instanceId + "/" + resourceId);
}