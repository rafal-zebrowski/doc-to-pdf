let inputUpload = $("#file-upload");
let inputDownload = $("#file-download");
let convertButton = $("#convert");
let trash = $(".trash");

let convertedFile = ObservableSlim.create({}, true, change => {
    if(change[0].newValue) {
        hideElement(".upload-section");
        showElement(".download-section");
        inputDownload.val(change[0].newValue);
    } else {
        showElement(".upload-section");
        hideElement(".download-section");
    }
});

inputUpload.on("input", () => {
    if(inputUpload.get(0).files[0]) {
        enableConvertButton();
    } else {
        disableConvertButton();
    }
})
trash.on("click", clear);
convertButton.on("click", () => hideElement("#error-container"))

hideElement(".download-section");
hideElement("#error-container");
disableConvertButton();
hideLoader();



function hideElement(selector) {
    $(selector).addClass("d-none");
}

function showElement(selector) {
    $(selector).removeClass("d-none");
}

function disableConvertButton() {
    convertButton.attr("disabled", true);
}

function enableConvertButton() {
    convertButton.attr("disabled", false);
}

function showLoader() {
    $(".loader-container").show();
}

function hideLoader() {
    $(".loader-container").hide();
}

function clear() {
    convertedFile.name = null;
    inputUpload.val(null);

    hideElement("#error-container");
    disableConvertButton();
}