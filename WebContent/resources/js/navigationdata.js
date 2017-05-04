$(document).ready(function() {
	$('#tree').tree({
		primaryKey : 'text',
		uiLibrary : 'bootstrap',
		dataSource : 
		[ 
			{
				text : '_unassigned',
					children : 
					[ 
						{
							text : 'new Device 1'
						}
					]
			},
			{
				text : 'North America',
					children : 
					[ 
						{
							text : 'USA',
								children : 
								[ 
									{
										text : 'California'
									}, 
									{
										text : 'Miami'
									} 
								]
						}, 
						{
							text : 'Canada'
						}, 
						{
							text : 'Mexico'
						} 
					]
			}, 
			{
				text : 'Europe',
					children : 
					[ 
						{
							text : 'France'
						}, 
						{
							text : 'Spain'
						}, 
						{
							text : 'Italy'
						} 
					]
			}, 
			{
				text : 'South America',
					children : 
					[ 
						{
							text : 'Brazil'
						}, 
						{
							text : 'Argentina'
						}, 
						{
							text : 'Columbia'
						} 
					]
			} 
		]
	});
	
	$('#tree').tree().on('select', function (e, node, id) {
        alert(id + ' is fired.');
    });
});