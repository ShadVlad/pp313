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

$(document).ready(function () {
    console.log('working');
    $('.table .editButton').on ('click', function (event) {
        event.preventDefault();

        const href = $(this).attr('href');
        $.get(href, function (user, status) {
            $('#editID').val(user.id);
            $('#editName').val(user.username);
            $('#editPassword').val(user.password);
            $('#editRoles').val(user.roles);
        })
        $('#editModal').modal();
    });
    $('.table .deleteButton').on('click', function (event) {
        event.preventDefault();
        const href = $(this).attr('href');
        $('#deleteModal #deletedId').attr('href', href);
        $('#deleteModal').modal();
    })

})