$(document).ready(getUserList());

function getUserList() {
    console.log('working');
    $.ajax({
        type: "GET",
        url: '/api/admin/users',
        success: function (allUsers) {
            let tbody = ``;
            for (let i = 0; i < allUsers.length; i++) {
                let id = allUsers[i].id;
                let username = allUsers[i].username;
                let password = allUsers[i].password;
                let roles = [];

                for (let j = 0; j < allUsers[i].roles.size(); j++) {
                    if (j > 0) {
                        roles.push(", " + allUsers[i].roles[j].role)
                    } else {
                        roles.push(allUsers[i].roles[j].role)
                    }
                }

                tbody += `<tr class=\"table-striped\">
                         <td>${id}</td>
                         <td>${username}</td>
                         <td>${password}</td>
                         <td>${roles}</td>`;
                if (id > 0) {
                    tbody += `
                        <td>
                            <button
                                type="button"
                                class="btn btn-primary btn-block-my"
                                data-toggle="modal"
                                data-target= "#deleteModal"
                                data-id="${id}">
                                DELETE
                            </button>
                            </td>
                            <td>
                                <button
                                    type="button"
                                        class="btn btn-primary btn-block-my"
                                        data-toggle="modal"
                                        data-target= "#editModal"
                                        data-id="${id}">                             
                                        EDIT
                                </button>
                            </td>`;
                } else {
                    tbody += `<td></td>
                              <td></td>`;
                }
                tbody += '</tr>';
                $("#tbody").html(tbody);
            }
        },
        dataType: "json",
        contentType: "application/json; charset=utf-8"
    })
}




// jQuery(function ($) {
//     console.log('working');
//     $.ajax({
//
//         url: '/admin/users',
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
//     var id = element.id;
//     var name = element.username;
//     var roles = element.roles;
//     var markup = `<tr id="${id}">
//                         <td id="userId-${id}">${id}</td>
//                         <td id="userRoles-${id}">${roles}</td>
//                         <td id="username-${id}">${name}</td>
//                         <td><button type="button" class="btn btn-info edit-user" data-toggle="modal" data-target="#modalWindow" id="editButton-${id}">Edit</button></td>
//                         <td><button type="button" class="btn btn-info delete-row" id="deleteButton-${id}">Delete</button></td>
//                   </tr>`;
//     $('#tbody').append(markup);
// }
