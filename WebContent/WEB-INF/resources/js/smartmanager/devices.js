$(document).ready(function() {
	$('#tree').tree(
	{
		primaryKey : 'id',
		uiLibrary : 'bootstrap',
		dataSource : ctx + '/groups/getAll'
	});

	
	$('#tree').tree().on('select', function (e, node, id) {
		$.ajax({
			dataType : "html",
			url : ctx + "/" + id,
			success : function(data) {
				$('#main-content').html(data);
			},
			error: function(xhr, ajaxOptions, thrownError){
				alert(thrownError);
			}
		});
    });
});

function addNewRootGroup() {
	bootbox.prompt({
		title : "Please enter a group name",
		callback : function(message){
			if(message){
				$.ajax({
					type: "POST",
					data : {
						value : message
					},
					url : ctx + "/groups/add",
					success : function(data){
						if(data === 'false') {
							bootbox.alert({
								size : "small",
								title : "Error",
								message : "Please choose another Groupname. <br> Max length: 20 <br> Chars: a-z, A-z, 0-9 and _.-",
								callback : function() {
									addNewRootGroup();
								}
							});
						}
						else {
							parent.location.reload();
						}
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

