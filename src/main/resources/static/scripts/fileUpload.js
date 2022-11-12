
function _convert() {
    var selectedFile = document.getElementById('file-upload').files[0];
    validateSelectedFile(selectedFile);
    showLoader();

    upload(selectedFile)
        .done(onUploadSuccess)
        .fail(onUploadFail);
}

function upload(file) {
    let formData = createFormData(file);

    return $.ajax({
        url: 'file',
        type: 'POST',
        data: formData,
        processData: false,
        contentType: false
    });
}

function createFormData(file) {
    let formData = new FormData();
    formData.append("inputFile", file);
    return formData;
}

function validateSelectedFile(file) {
    if(!file) {
        const message = "Nie wybrano pliku!";
        alert(message);
        throw Error(message);
    }
}

function onUploadSuccess(response) {
    convertedFile.name = response;
    hideLoader();
}

function onUploadFail(error) {
    convertedFile.name = null;
    hideLoader();
    catchError(error);
}

function _download() {
    validateFileName();
    $.ajax('file/' + convertedFile.name, {
        method: "GET",
        xhrFields:{
            responseType: 'blob'
        }
    })
        .done(openWindowAndDownloadFile)
        .fail(catchError);
}

function validateFileName() {
    if(!convertedFile.name) {
        alert("Wystąpił błąd!");
        throw Error();
    }
}

function openWindowAndDownloadFile(response) {
    const url = window.URL.createObjectURL(response);
    let link = createLink(url);
    document.body.appendChild(link);
    link.click();
    window.URL.revokeObjectURL(url);
}

function createLink(url) {
    const a = document.createElement('a');
    a.style.display = 'none';
    a.href = url;
    a.download = convertedFile.name;
    return a;
}

function catchError(error) {
    showErrorInContainer(error.responseJSON.message);
    console.error(Error(error.responseJSON.message));
}

function showErrorInContainer(message) {
    showElement("#error-container");
    $("#error-message").html(message);
}

$(document)
    .ajaxError(
        function(event, jqxhr, settings, exception) {
            var message = jqxhr.responseJSON.message;
            showErrorInContainer(message);
        });