let hobbyRadarMap = new Map();
let hobbyRadar_totalPoints = 21;
let hobbyRadar_color = "";

function reDrawRadar() {
    let radarData = JSON.parse(localStorage.getItem("radarData"));
    if (!radarData) {
        return;
    }
    $('svg').remove();
    RadarChart.defaultConfig.w = window.screen.width < 400 ? 200 : 400;
    RadarChart.defaultConfig.h = window.screen.width < 400 ? 200 : 400;
    console.log(hobbyRadar_color.length > 0 ? hobbyRadar_color : "black");
    RadarChart.defaultConfig.color = function() {return hobbyRadar_color.length > 0 ? hobbyRadar_color : "black"};
    var chart = RadarChart.chart();
    var cfg = chart.config();
    var svg = d3.select('#fc-interests-radar').append('svg')
        .attr('width', cfg.w + cfg.w + 50)
        .attr('height', cfg.h + cfg.h / 4);
    svg.append('g').classed('single', 1).datum(radarData).call(chart);
}

function getAvailablePoints() {
    let tmpPoints = 0;
    hobbyRadarMap.forEach((value, key) => {
        tmpPoints = tmpPoints + +value;
    })
    return hobbyRadar_totalPoints - tmpPoints;
}

function updatePointsAvailable() {
    let availablePoints = getAvailablePoints();
    $("#fc-hobby-points-header").text(availablePoints + " Points available");
}

function updateForm(interest, val) {
    let form = $("#fc-hobbyRadar-form"),
        interestInput = $(form).find("input[data-name='hobbyRadar[\\'" + interest + "\\']']");

    if (interestInput[0]) {
        interestInput.val(val);
        hobbyRadarMap.set(interest, val);
        updatePointsAvailable();
    }

}

function checkInputStatus(inputEl) {
    let availablePoints = getAvailablePoints();
    if (availablePoints < 0) {
        // hobbyRadar_totalPoints + availablePoints
        inputEl.stepDown(-availablePoints);
        updateInterests(inputEl);
    }

}

function updateInterests(inputEl) {
    let radarData = JSON.parse(localStorage.getItem("radarData"));
    if (radarData) {
        let interest = $(inputEl).closest(".fc-interests-listEntry-wrapper").children("span").first().text();
        radarData[interest] = $(inputEl).val();
        radarData[0].axes.forEach(axis => {
            if (axis.axis === interest) {
                axis.value = $(inputEl).val();
                localStorage.setItem("radarData", JSON.stringify(radarData));
                reDrawRadar();
                updateForm(interest, $(inputEl).val());
            }
        });
        checkInputStatus(inputEl);
    }
}

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

function initHobbyRadar() {
    console.log(hobbyRadar_color);
    console.log(hobbyRadar_color.length);
    console.log(hobbyRadar_color.length > 0 ? hobbyRadar_color : "black");
    RadarChart.defaultConfig.color = function() {return hobbyRadar_color.length > 0 ? hobbyRadar_color : "black"};
    RadarChart.defaultConfig.radius = 3;
    RadarChart.defaultConfig.w = window.screen.width < 400 ? 200 : 400;
    RadarChart.defaultConfig.h = window.screen.width < 400 ? 200 : 400;

    var data = [
        {
            // className: 'germany', // optional can be used for styling
            axes: [
                {axis: "Sports", value: 0},
                {axis: "Games", value: 0},
                {axis: "Creativity", value: 0},
                {axis: "Outdoor", value: 0},
                {axis: "Travel", value: 0},
                {axis: "Reading", value: 0},
                {axis: "Films & Series", value: 0},
            ]
        }
    ];
    localStorage.setItem("radarData", JSON.stringify(data));

    var chart = RadarChart.chart();
    console.log(chart);
    var cfg = chart.config(); // retrieve default config
    var svg = d3.select('#fc-interests-radar').append('svg')
        .attr('width', cfg.w + cfg.w + 50)
        .attr('height', cfg.h + cfg.h / 4);
    svg.append('g').classed('single', 1).datum(data).call(chart);
}

// function initHobbyRadarColor() {
//     let hobbyColor = fc-interests-listEntry-color
// }

function initializeHobbyRadar() {
    // initHobbyRadarColor();
    initHobbyRadar();
    let pointsHeaderEl = $('#fc-hobby-points-header');

    if (pointsHeaderEl.text() === "0 Points available") {
        let interestWrapper = $(".fc-interests-listEntry-wrapper");
        interestWrapper.find(".fc-interests-points-input").each(function(index, inputEl) {
            updateInterests(inputEl);
        });
    }
}

$(function (){
    hobbyRadar_color = userInfo_hobbyColor;

    window.addEventListener("resize", reDrawRadar);

    $(".fc-interests-points-input").on("click", function() {
        updateInterests(this);
    });

    $("#fc-hobbyRadar-form-submit").on("click", function() {
        let form = $("#fc-hobbyRadar-form"),
            availablePoints = getAvailablePoints();

        if (availablePoints !== 0) {
            $("#fc-hobbyRadar-form-alert").show();
            return;
        } else {
            form.submit();
        }

    });

    $(".fc-interests-radar-palette-btn-inner").on("click", function() {
        let form = $("#fc-hobbyRadar-form"),
            interestInputColor = $(form).find("input[data-name='color']");

        hobbyRadar_color = this.classList[this.classList.length-1];
        interestInputColor.val(hobbyRadar_color);

        reDrawRadar();
    });

    console.log(hobbyRadar_color);

    initializeHobbyRadar();

})

