const notificationTypes = new Map([
    [200001, 'chat-square-dots'],
    [200002, 'envelope'],
    [200003, 'phone-vibrate']
]);

const notificationUserFields = new Map([
    [200001, 'phone'],
    [200002, 'email'],
    [200003, 'phone']
]);

const formSelector = ".needs-validation";

$(() => {
    const dataTable = $("#table").DataTable({
        ordering: false,
        ajax: {
            url: '/api/v1/notification',
            dataSrc: ''
        },
        columns: [
            {data: 'createdDateTime'},
            {data: 'categoryDesc'},
            {data: 'statusDesc'},
            {data: 'message'},
            {
                data: null,
                render: function (data, type, row) {
                    if (type === 'display') {
                        return `${row['user']['name']} <i class="bi bi-${notificationTypes.get(row['typeId'])}" title="${row['typeDesc']}"></i> [${row['user'][notificationUserFields.get(row['typeId'])]}]`;
                    } else if (type === 'filter') {
                        return `${row['user']['name']}_${row['user'][notificationUserFields.get(row['typeId'])]}`;
                    } else {
                        return null;
                    }
                }
            },
        ]
    });

    $.getJSON("/api/v1/catalog/message-category", result => {
        result.forEach(item => {
            $("#categoryId").append($("<option />").val(item.id).text(item.option));
        });
    });

    $(formSelector).on('submit', function (event) {
        event.preventDefault();
        event.stopPropagation();

        this.classList.add('was-validated')

        if (this.checkValidity()) {
            $.ajax({
                url: "/api/v1/notification",
                type: 'post',
                dataType: 'json',
                contentType: 'application/json',
                data: getMessagePayload()
            }).done(function () {
                displayResult("success", "Message sent successfully");
            }).fail(function () {
                displayResult("error", "Error sending message");
            })
        }
        return false;
    }).on('reset', function () {
        this.classList.remove('was-validated')
    });

    function getMessagePayload() {
        return JSON.stringify({
            categoryId: $("#categoryId").val(),
            message: $("#message").val()
        });
    }

    function displayResult(result, message) {
        $("#result").append($("<div>")
            .addClass(`alert alert-${result} alert-dismissible fade show`)
            .append('<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>')
            .append(message));
        $(formSelector).trigger("reset");

        setTimeout(function () {
            $(".alert").alert('close');
        }, 2000);

        dataTable.ajax.reload().draw();
    }

})
