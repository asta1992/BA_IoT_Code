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