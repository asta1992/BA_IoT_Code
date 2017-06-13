function showForm() {
	$.ajax({
		dataType : "html",
		url : ctx + "/users/userAddFragment",
		success : function(users) {
			bootbox.confirm({
				title : "Create a new User",
				message : users,
				callback : function(ok) {
					if (ok) {
						var username = $("#username").val();
						var firstPassword = $("#firstPassword").val();
						var secondPassword = $("#secondPassword").val();

						createUser(username, firstPassword, secondPassword, function(data) {
							if (data.map.usernameLength) {
								createUserAlert("Username Length Error. Min: 4, Max: 20");
							} else if (data.map.invalidCharError) {
								createUserAlert("Only a-z, A-Z and 0-9 is allowed");
							} else if (data.map.existsError) {
								createUserAlert("User already exists! Please choose another Username.");
							} else if (data.map.passwordLength) {
								createUserAlert("Password Length Error: Min: 8, Max: 50");
							} else if (data.map.matchError) {
								createUserAlert("Password doesn't match!");
							} else {
								bootbox.alert({
									size : "small",
									title : "Success",
									message : "New User '" + data.map.username + "' created",
									callback : function() {
										parent.location.reload();
									}
								});
							}
						});
					}
				}

			});
		},
		error : function(xhr, ajaxOptions, thrownError) {
			alert(thrownError);
		}
	});
}
function editForm(id) {
	$.ajax({
		dataType : "html",
		url : ctx + "/users/userEditFragment",
		success : function(users) {
			bootbox.confirm({
				title : "Change password",
				message : users,
				callback : function(ok) {
					if (ok) {
						var oldPassword = $("#oldPassword").val();
						var firstPassword = $("#firstPassword").val();
						var secondPassword = $("#secondPassword").val();

						editUser(id, oldPassword, firstPassword, secondPassword, function(data) {
							if (data.map.oldPasswordError) {
								createEditAlert(id, "Please check the old password");
							} else if (data.map.passwordLength) {
								createEditAlert(id, "The password is too short, The minimum length is 8");
							} else if (data.map.matchError) {
								createEditAlert(id, "Password doesn't match!");
							} else {
								bootbox.alert({
									size : "small",
									title : "Success",
									message : "Successfully changed password"
								});
							}
						});
					}
				}

			});
		},
		error : function(xhr, ajaxOptions, thrownError) {
			alert(thrownError);
		}
	});
}

function createUserAlert(message) {
	bootbox.alert({
		size : "small",
		title : "Error",
		message : message,
		callback : function() {
			showForm();
		}
	});
}

function createEditAlert(id, message) {
	bootbox.alert({
		size : "small",
		title : "Error",
		message : message,
		callback : function() {
			editForm(id);
		}
	});
}

function createUser(username, firstPassword, secondPassword, callback) {
	$.ajax({
		type : "POST",
		dataType : "json",
		data : {
			username : username,
			firstPassword : firstPassword,
			secondPassword : secondPassword
		},
		url : ctx + "/users/add",
		success : function(res) {
			callback(res);
		},
		error : function(xhr, ajaxOptions, thrownError) {
			alert(thrownError);
		}
	});
}

function editUser(id, oldPassword, firstPassword, secondPassword, callback) {
	$.ajax({
		type : "POST",
		dataType : "json",
		data : {
			oldPassword : oldPassword,
			firstPassword : firstPassword,
			secondPassword : secondPassword
		},
		url : "users/" + id + "/edit",
		success : function(res) {
			callback(res);
		},
		error : function(xhr, ajaxOptions, thrownError) {
			alert(thrownError);
		}
	});
}

function deleteUser() {
	$.ajax({
		dataType : "html",
		url : ctx + "/users/userDeleteFragment",
		success : function(users) {
			bootbox.confirm({
				title : "Delete a User",
				message : users,
				callback : function(ok) {
					if (ok) {
						var selectedUser = $("#selectedUser option:selected").text();
						$.ajax({
							type : "POST",
							dataType : "json",
							data : {
								username : selectedUser
							},
							url : ctx + "/users/delete",
							success : function() {
								parent.location.reload();
							},
							error : function(xhr, ajaxOptions, thrownError) {
								alert(thrownError);
							}
						});
					}

				}
			});
		}
	});
}