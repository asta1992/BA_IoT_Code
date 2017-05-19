function createConfiguration(){
	$.ajax({
		type: "GET",
		dataType : "html",
		url : "/smartmanager/configurations/createConfigurationFragment",
		success : function(createConfigurationFragment) {
			bootbox.confirm({
				size: "large",
				title : "Prepare your command to write",
				message : createConfigurationFragment,
				callback : function(ok) {
					if(ok){
						alert("not implemented yet");
					}
				}
			})
		}
	})
}

function addAnotherConfigurationItem(){
	
}