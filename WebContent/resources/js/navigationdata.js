$(document).ready(function() {
	$('#tree').tree({
		primaryKey : 'id',
		uiLibrary : 'bootstrap',
		dataSource : '/smartmanager/group/getAll'});

	
	$('#tree').tree().on('select', function (e, node, id) {
		console.log(e);
        alert(id + ' is fired.');
    });
});