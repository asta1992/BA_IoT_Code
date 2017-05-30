$(document).ready(function() {
	$('#tree').tree({
		primaryKey : 'id',
		uiLibrary : 'bootstrap',
		dataSource : '/smartmanager/group/getAll'});

	
	$('#tree').tree().on('select', function (e, node, id) {
		$.ajax({
			dataType : "html",
			url : "/smartmanager/" + id,
			success : function(data) {
				$('#main-content').html(data);
			}
		});
    });
});