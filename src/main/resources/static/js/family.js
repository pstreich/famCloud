function initHobbyRadar(hobbyRadar, username) {
    RadarChart.defaultConfig.color = function() {return hobbyRadar.color};
    RadarChart.defaultConfig.radius = 3;
    RadarChart.defaultConfig.w = window.screen.width < 400 ? 75 : 150;
    RadarChart.defaultConfig.h = window.screen.width < 400 ? 75 : 150;

    var data = [
        {
            // className: 'germany', // optional can be used for styling
            axes: [
                {axis: "Sports", value: hobbyRadar.sportsValue},
                {axis: "Games", value: hobbyRadar.gamesValue},
                {axis: "Creativity", value: hobbyRadar.creativityValue},
                {axis: "Outdoor", value: hobbyRadar.outdoorValue},
                {axis: "Travel", value: hobbyRadar.travelValue},
                {axis: "Reading", value: hobbyRadar.readingValue},
                {axis: "Films & Series", value: hobbyRadar.filmsSeriesValue},
            ]
        }
    ];
    localStorage.setItem("radarData", JSON.stringify(data));

    var chart = RadarChart.chart();
    console.log(chart);
    var cfg = chart.config(); // retrieve default config
    var svg = d3.select('#fc-card-' + username + ' #fc-interests-radar').append('svg')
        .attr('width', cfg.w + cfg.w + 50)
        .attr('height', cfg.h + cfg.h / 4);
    svg.append('g').classed('single', 1).datum(data).call(chart);
}

function initializeHobbyRadar() {
    // initHobbyRadarColor();
    for (let i = 0; i < userInfoList.length; i++) {
        initHobbyRadar(userInfoList[i].hobbyRadar, userInfoList[i].username);
    }

}

$(function (){
    initializeHobbyRadar();

})