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
	config.push("Config Name:" + $('#configName').val());
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
		url : "/smartmanager/configurations/add",
		success : function() {
			window.location.href = "/smartmanager/configurations"
		}
	})
}