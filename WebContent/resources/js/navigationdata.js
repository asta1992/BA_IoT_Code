$(document).ready(function() {
	$('#tree').tree({
		primaryKey : 'id',
		uiLibrary : 'bootstrap',
		dataSource : [ {
			text : 'North America',
			children : [ {
				text : 'USA',
				children : [ {
					text : 'California'
				}, {
					text : 'Miami'
				} ]
			}, {
				text : 'Canada'
			}, {
				text : 'Mexico'
			} ]
		}, {
			text : 'Europe',
			children : [ {
				text : 'France'
			}, {
				text : 'Spain'
			}, {
				text : 'Italy'
			} ]
		}, {
			text : 'South America',
			children : [ {
				text : 'Brazil'
			}, {
				text : 'Argentina'
			}, {
				text : 'Columbia'
			} ]
		} ]
	});
});