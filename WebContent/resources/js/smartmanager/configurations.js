function createConfiguration() {
	$.ajax({
		type : "GET",
		dataType : "html",
		url : ctx + "/configurations/createConfigurationFragment",
		success : function(createConfigurationFragment) {
			bootbox.confirm({
				size : "large",
				title : "Prepare your configuration",
				message : createConfigurationFragment,
				buttons : {
					confirm : {
						label : 'Save',
					}
				},
				callback : function(save) {
					if (save) {
						saveConfiguration();
					}
				}
			})
		}
	})
}

function addConfigurationItem() {
	var rowNumber = parseInt($('#configurationItems tr:last').attr('id'));
	rowNumber++;
	var objectId = $('#completeObjectId').text();
	var value = $('#writeValue').val();
	$('#configurationItems tr:last')
			.after(
					'<tr id="'
							+ rowNumber
							+ 'Row">'
							+ '<td class="objectIdField">'
							+ objectId
							+ '</td>'
							+ '<td class="valueField">'
							+ value
							+ '<span class="pull-right"><button class="btn btn-danger btn-xs" onclick="removeConfigurationItem('
							+ rowNumber
							+ ')"><span class="glyphicon glyphicon-minus"></span></button></span></td>'
							+ '</tr>');
	$('#writeValue').val('');
	$("#configurationItems").html();
}

function removeConfigurationItem(rowNumber) {
	console.log(rowNumber)
	$('#' + rowNumber + 'Row').remove();
}

function saveConfiguration() {
	var config = [];
	config.push($('#configName').val());
	config.push($('#description').val());
	var headers = [];
	$('#configurationItems th').each(function(index, item) {
		headers[index] = $(item).text();
	});
	$('#configurationItems tr').has('td').each(function() {
		var configItem = {};
		$('td', $(this)).each(function(index, item) {
			configItem[headers[index]] = $(item).text();
		});
		config.push(configItem);
	});
	$.ajax({
		type : "POST",
		data : {
			value : JSON.stringify(config)
		},
		url : ctx + "/configurations/add",
		success : function(){
			parent.location.reload();
		},
		error: function(xhr, ajaxOptions, thrownError){
			parent.location.reload();
			alert(thrownError);
		}
	});
}

function deleteConfiguration(configurationId) {
	bootbox.confirm({
		message : "Do you really want to delete this configuration?",
		callback : function(ok) {
			if (ok) {
				$.ajax({
					type : "POST",
					data : {
						value : configurationId
					},
					url : ctx + "/configurations/delete",
					success: function(){
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

function editConfiguration(configurationId) {
	$.ajax({
		type : "GET",
		dataType : "html",
		url : ctx + "/configurations/" + configurationId
				+ "/editConfigurationFragment",
		success : function(createConfigurationFragment) {
			bootbox.confirm({
				size : "large",
				title : "Prepare your configuration",
				message : createConfigurationFragment,
				buttons : {
					confirm : {
						label : 'Save',
					}
				},
				callback : function(save) {
					if (save) {
						saveConfiguration();
					}
				}
			})
		}
	})
}

function updateCompleteObjectId() {
	var objectId = parseInt($('#objectDropdown').find(":selected").text());
	var instanceId = $('#instanceIdField').val();
	if(instanceId == ''){
		var instanceId = 0;
	}
	resourceId = parseInt($('#resourceDropdown').find(":selected").text());
	
	$('#completeObjectId').html(objectId + "/" + instanceId + "/" + resourceId);
}

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
	})

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
			})
			resourceDropdown.selectpicker('refresh');
			updateCompleteObjectId();
		}
	})
}

function getExecuteableResources() {
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
	})

	var resourceDropdown = $('#resourceDropdown');
	$.ajax({
		type : "GET",
		url : ctx + "/groups/" + objectId + "/executeToChildren",
		success : function(resources) {
			resourceDropdown.empty();
			resources.forEach(function(entry) {
				resourceDropdown.append('<option value="'
						+ entry.resourceModel.id + '">'
						+ entry.resourceModel.id + " " + "("
						+ entry.resourceModel.name + ")" + '</option>');
			})
			resourceDropdown.selectpicker('refresh');
			updateCompleteObjectId();
		},
		error: function(xhr, ajaxOptions, thrownError){
			alert(thrownError);
		}
	})
}