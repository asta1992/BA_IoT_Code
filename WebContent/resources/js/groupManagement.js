function openGroupManagement(id) {
	var msg = getMessage();

	bootbox.confirm({
		size : "large",
		message : msg,
		callback : function(id) {
			console.log("yay");
		}
	});
}

function getMessage(){
	return "<div class='col-md-7' id='non-selected'><select multiple='multiple' size='10' name='duallistbox_demo2' class='demo2'><option value='option1'>Option 1</option><option value='option2'>Option 2</option><option value='option3' selected='selected'>Option 3</option><option value='option4'>Option 4</option><option value='option5'>Option 5</option><option value='option6' selected='selected'>Option 6</option><option value='option7'>Option 7</option><option value='option8'>Option 8</option><option value='option9'>Option 9</option><option value='option0'>Option 10</option><option value='option0'>Option 11</option><option value='option0'>Option 12</option><option value='option0'>Option 13</option><option value='option0'>Option 14</option><option value='option0'>Option 15</option><option value='option0'>Option 16</option><option value='option0'>Option 17</option><option value='option0'>Option 18</option><option value='option0'>Option 19</option><option value='option0'>Option 20</option></select>"
}