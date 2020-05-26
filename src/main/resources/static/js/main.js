
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
                                data-id="${id}">
                                DELETE
                            </button>
                            </td>`;
                } else {
                    tbody += `<td></td><td></td>`;
                }
                tbody += `</tr>`;

            };
            $('#tbody').append(tbody);
        },
    })
    console.log('end working');
}



// jQuery(function ($) {
//     console.log('working');
//     $.ajax({
//
//         url: '/api/admin',
//         type: 'GET',
//         contentType: "application/json;charset=UTF-8",
//         dataType: 'json',
//         success: function (data) {
//             data.forEach(function (element) {
//                 addTableRow(element);
//             })
//         },
//     });
//
//
// });
//
// function addTableRow(element) {
//     let id = element.id;
//     let name = element.username;
//     let role = [];
//     for (let j = 0; j < element.roles.length; j++) {
//         role.push(element.roles[j].role);
//     }
//     let tablebody = `<tr id="${id}">
//                         <td id="userId-${id}">${id}</td>
//                         <td id="userRoles-${id}">${role}</td>
//                         <td id="username-${id}">${name}</td>
//                         <td><button type="button" class="btn btn-info edit-user" data-toggle="modal" data-target="#modalWindow" id="editButton-${id}">Edit</button></td>
//                         <td><button type="button" class="btn btn-info delete-row" id="deleteButton-${id}">Delete</button></td>
//                   </tr>`;
//     console.log(name + ': ' + role);
//     $('#tbody').append(tablebody);
// }
