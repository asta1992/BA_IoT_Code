function createConfiguration() {
	$.ajax({
		type : "GET",
		dataType : "html",
		url : "/smartmanager/configurations/createConfigurationFragment",
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
					parent.location.reload();
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
	$('#configurationItems tr:last').after(
			'<tr id="' + rowNumber + 'Row">' + 
				'<td class="objectIdField">' + objectId + '</td>' + 
				'<td class="valueField">' + value + '<span class="pull-right"><button class="btn btn-danger btn-xs" onclick="removeConfigurationItem(' + rowNumber + ')"><span class="glyphicon glyphicon-minus"></span></button></span></td>' + 
			'</tr>');
	$("#configurationItems").html();
}

function removeConfigurationItem(rowNumber) {
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
		dataType : "json",
		data : {
			value : JSON.stringify(config)
		},
		url : "/smartmanager/configurations/add"
	});
}

function deleteConfiguration(configurationId) {
	bootbox.confirm({
		message : "Do you really want to delete this configuration?",
		callback : function(ok) {
			if (ok) {
				$.ajax({
					type : "POST",
					dataType : "json",
					data : {
						value : configurationId
					},
					url : "/smartmanager/configurations/delete"
					
				});
			}
			parent.location.reload();
		}
	});
	
}

function editConfiguration(configurationId) {
	$.ajax({
		type : "GET",
		dataType : "html",
		url : "/smartmanager/configurations/" + configurationId + "/editConfigurationFragment",
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
					parent.location.reload();
				}
			})
		}
	})
}

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

function getExecuteableResources(){
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
		url : "/smartmanager/group/" + objectId + "/executeToChildren",
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