addUserForm = $("#addUserForm");
addUserForm.on('submit', function (e) {
    e.preventDefault();
    const userName = $("#userName").val();
    const password = $("#password").val();
    const roles = $("#checkedRoles").val();
    $.ajax({
        type: "POST",
        url: '/api/admin/add',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({
            userName: userName,
            password: password,
            roles: roles
        }),
        success: function () {
            window.location.href = "http://localhost:8080/admin/users";
            getUserList()
        },
        error: function () {
            $('#message').empty().text('Error happened. User exists!');

        }
    });
 })



$(document).ready(getRoleList());
function getRoleList() {
    $.ajax({
        type: "GET",
        url: '/api/roles',
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (allRoles) {
            let checkedRoles = ``;
            for (let i = 0; i < allRoles.length; i++) {
                const role = allRoles[i].role;
                checkedRoles +=
                    `<option value="${role}">${role}</option>`;
            }
            $("#checkedRoles").html(checkedRoles);
        }
    })
}

editModal = $('#editModal');
editModal.on('show.bs.modal', function (event) {
    const button = $(event.relatedTarget);
    const modal = $(this);
    const id = $(event.relatedTarget).data('id');
    //const userName = $(event.relatedTarget).data('userName');

    modal.find('.modal-body #idEdit').val(id);


    $.ajax({
        url: '/api/admin/edit/' + id,
        method: "GET",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (user) {
            modal.find('.modal-body #usernameEdit').val(user.username);
            modal.find('.modal-body #passwordEdit').val(user.password);
            // console.log(user.username);
            // console.log(user.password);
            $.ajax({
                type: "GET",
                url: '/api/roles',
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function (allRoles) {
                    let checkedRoles = ``;
                    for (let i = 0; i < allRoles.length; i++) {
                        const role = allRoles[i];
                        console.log(role.id + "   " + role.role);
                        let select = false;
                        for (let j = 0; j < user.roles.length; j++) {
                            console.log("   " + user.roles[j].id + " " + user.roles[j].role);
                            select = user.roles[j].role === role.role;
                            if (select) break;
                            console.log("   " + select);
                        }
                        if (select) {checkedRoles +=
                            `<option value="${role.role}" selected>${role.role}</option>`;
                        } else {checkedRoles +=
                            `<option value="${role.role}">${role.role}</option>`;
                        }
                    }
                    console.log(checkedRoles);
                    $("#checkedRolesEdit").html(checkedRoles);
                }
            });
        }
    });
});

editModal.on('submit', function (e) {
    e.preventDefault();
    const id = $("#idEdit").val();
    const userName = $("#usernameEdit").val();
    const password = $("#passwordEdit").val();
    const roles = $("#checkedRolesEdit").val();
    console.log('working');
    $.ajax({
        url: '/api/admin/edit',
        method: "PUT",
        contentType: "application/json; charset=utf-8",
        dataType: "text",
        data: JSON.stringify({
            id:id,
            userName: userName,
            password: password,
            roles: roles
        }),
        success: function () {
            editModal.trigger("click");
            getUserList();
        }
    })
});



deleteModal = $('#deleteModal');
deleteModal.on('shown.bs.modal', function (event) {
    const button = $(event.relatedTarget);
    const modal = $(this);
    const id = button.data('id');
    let userName = button.data('name');

    if (Object.is(userName, "")) {
        userName = "this user";
    }
    modal.find('.modal-body label').text("Do you want to remove " + userName + "?");
    modal.find('.modal-footer button').val(id)
});

deleteModal.on('submit', function (e) {
    e.preventDefault();
    const id = $("button#id").val();
    $.ajax({
        url: '/api/admin/delete/' + id,
        method: "DELETE",
        contentType: 'application/json',
        dataType: 'text',
        success: function () {
            deleteModal.trigger("click");
            getUserList()
        }
    });
});


$(document).ready(getUserList());
function getUserList() {
    console.log('working');
    $.ajax({
        type: "GET",
        url: '/api/admin/users',
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (allUsers) {
            let tbody = ``;
            for (let i = 0; i < allUsers.length; i++) {
                let id = allUsers[i].id;
                let username = allUsers[i].username;
                //console.log(username);
                let password = allUsers[i].password;
                let role = [];

                for (let j = 0; j < allUsers[i].roles.length; j++) {
                    role.push(allUsers[i].roles[j].role);
                }
                console.log(username + ': ' + role);
                tbody += `<tr class=\"table-striped\">
                         <td>${id}</td>
                         <td>${username}</td>
                         <td>${password}</td>
                         <td>${role}</td>`;
                if (id > 0) {
                    tbody += `
                        <td>
                            <button
                                type="button"
                                class="btn btn-primary"
                                data-toggle="modal"
                                data-target= "#editModal"
                                data-id="${id}">
                                EDIT
                            </button>
                        </td>
                        <td>
                            <button
                                type="button"
                                class="btn btn-danger btn-sm"
                                data-toggle="modal"
                                data-target= "#deleteModal"
                                data-id="${id}"
                                data-name="${username}">
                                DELETE
                            </button>
                            </td>`;
                } else {
                    tbody += `<td></td><td></td>`;
                }
                tbody += `</tr>`;

            };
            $('#tbody').html(tbody);

        },
    })
    console.log('end working');
}
//
// $(document).ready(function () {
//
//     $('.table .editButton').on ('click', function (event) {
//         event.preventDefault();
//
//         const href = $(this).attr('href');
//         $.get(href, function (user, status) {
//             $('#editID').val(user.id);
//             $('#editName').val(user.username);
//             $('#editPassword').val(user.password);
//             $('#editRoles').val(user.roles);
//         })
//         $('#editModal').modal();
//     });
//     $('.table .deleteButton').on('click', function (event) {
//         event.preventDefault();
//         console.log('delete' + this);
//         const href = $(this).attr('href');
//         $('#deleteModal #deletedId').attr('href', href);
//         $('#deleteModal').modal();
//     })
//
// })