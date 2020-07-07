setTimeout(function () {
    $('#user-table-list').trigger('click');
}, 1);

$(function () {
    const id = $('#loggedUser').val();
    $.getJSON('api/user/' + id, function (user) {
        const adminTable = $("#allUsers");

        adminTable
            .find('#id')
            .text(user.id);
        adminTable
            .find('#name')
            .text(user.name);
        adminTable
            .find('#lastName')
            .text(user.lastName);
        adminTable
            .find('#email')
            .text(user.email);
        adminTable
            .find('#age')
            .text(user.age);
        adminTable
            .find('#role')
            .text(user.roles[0].role);
    });

});

$('a[id="user-table-list"]').on('show.bs.tab', function (e) {
    $.getJSON('api/all', function (data) {
        let r = [];
        let j = -1, recordId;
        for (const i in data) {
            const d = data[i];
            recordId = d.id
            r[++j] =
                `<tr id = "tr${recordId}"><td id="id${recordId}">${recordId}</td>
                <td id="name${recordId}">${d.name}</td>
                <td id="lastName${recordId}">${d.lastName}</td>
                <td id="email${recordId}">${d.email}</td>
                <td id="age${recordId}">${d.age}</td>
                <td id="role${recordId}">${d.roles[0].role}</td>
                <td><button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#editModal" onClick = getDetails(${d}) id=${recordId}>Edit</button></td>
                <td> <button type="button" class="btn btn-danger btn-sm" data-toggle="modal" data-target = "#deleteModal" onClick = getDetails(${d}) id =${recordId}>Delete</button></td></tr>`
        }
        $('#adminTable')[0].innerHTML = r.join('');
    });
});

function getDetails(id) {
    $('.modal').on('show.bs.modal', function () {
        $.getJSON('api/user/' + id, function (data) {
            const modal = $('#Edit-Form, #Delete-Form');
            modal.ready(function () {
                modal.find("input[name=id]").attr("value", data.id);
                modal.find("input[name=name]").attr("value", data.name);
                modal.find("input[name=lastName]").attr("value", data.lastName);
                modal.find("input[name=age]").attr("value", data.age);
                modal.find("input[name=email]").attr("value", data.email);
                modal.find("input[name=role]").attr("value", data.roles[0].role);
            });
        });
    });
};

$('#Delete-Form').submit(function (e) {
    e.preventDefault();
    const id = $('#ID2').val();
    $.post('api/delete/' + id)
    $("#adminTable")
        .find("#tr" + id)
        .remove();
    $('#deleteModal').modal('hide');
});


$('#Edit-Form').submit(function (e) {
    e.preventDefault();

    $.post('api/edit?' + $("#Edit-Form").serialize());

    $('#editModal').modal('hide');
});

$('#editModal').on('hide.bs.modal', function () {
    const id = $('#ID1').val();
    const adminTable = $("#adminTable");
    adminTable
        .find('#name' + id)
        .text($('#editName').val());
    adminTable
        .find('#lastName' + id)
        .text($('#editLastName').val());
    adminTable
        .find('#email' + id)
        .text($('#editEmail').val());
    adminTable
        .find('#age' + id)
        .text($('#editAge').val());
    adminTable
        .find('#role' + id)
        .text($('#editRole').val());

});
const newUserForm = $('#newUserForm');
newUserForm.each(function () {
    $(this).find("input[name=name]").attr("placeholder", 'First Name');
    $(this).find("input[name=lastName]").attr("placeholder", 'Last Name');
    $(this).find("input[name=age]").attr("placeholder", 'Age');
    $(this).find("input[name=email]").attr("placeholder", 'Email');
    $(this).find("input[name=password]").attr("placeholder", 'Password');
});

newUserForm.submit(function (e) {
    e.preventDefault();
    $.post('api/add?' + $("#newUserForm").serialize());
    $('#user-table-list').click();
});