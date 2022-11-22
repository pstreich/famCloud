// document.addEventListener("DOMContentLoaded", function(event) {
//     console.log("Hiii")
//     identicon.generate({ id: 'sadasdad', size: 100 }, function(err, buffer) {
//         if (err) throw err;
//
//         var img = new Image();
//         img.src = buffer;
//         document.getElementById('identicon').appendChild(img);
//     });
// });

function generateAvatar() {
    let avatarSeed = $('#avatargen').val();

    identicon.generate({ id: avatarSeed, size: 100 }, function(err, buffer) {
        if (err) throw err;

        var img = new Image();
        img.src = buffer;
        // $(img).attr("th:field")
        let identiconEl = document.getElementById('identicon');
        if ($(identiconEl).children("img")) {
            $(identiconEl).children("img").remove();
        }
        document.getElementById('identicon').appendChild(img);
        $('#form-identicon-image-input').val(img.src.substring(img.src.indexOf(",") + 1, img.src.length));
    });
}