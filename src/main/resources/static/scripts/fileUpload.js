let convertedFileName = null;

function _convert() {
    var selectedFile = document.getElementById('file-upload').files[0];
    validateSelectedFile(selectedFile);

    upload(selectedFile)
        .done(response => convertedFileName = response)
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

function onUploadFail(error) {
    convertedFileName = null;
    catchError(error);
}

function _download() {
    validateFileName();
    $.ajax('file/' + convertedFileName, {
        method: "GET",
        xhrFields:{
            responseType: 'blob'
        }
    })
        .done(openWindowAndDownloadFile)
        .fail(catchError);
}

function validateFileName() {
    if(!convertedFileName) {
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
    a.download = convertedFileName;
    return a;
}

function catchError(error) {
    alert(error.responseJSON.message);
    throw Error(error.responseJSON.message);
}