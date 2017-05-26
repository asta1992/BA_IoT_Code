function showForm() {
	$.ajax({
		dataType : "html",
		url : "/smartmanager/users/userAddFragment",
		success : function(users) {
			dialog = bootbox.dialog({
				message : users
			});
		}
	});
}

function createUser() {
	console.log("Add User");
}






function deleteUser() {
	$.ajax({
		dataType : "html",
		url : "/smartmanager/users/userDeleteFragment",
		success : function(users) {
			bootbox.confirm({
				message : users,
				callback : function(ok) {
					if (ok) {
						var selectedUser = $("#selectedUser option:selected")
								.text();
						$.ajax({
							type : "POST",
							dataType : "json",
							data : {
								username : selectedUser
							},
							url : "/smartmanager/users/delete"
						});
					}

				}
			})

		}
	})
}