var timeFormat = 'MM/DD/YYYY HH:mm';

function newDateString(days) {
    return moment().add(days, 'd').format(timeFormat);
}

var color = Chart.helpers.color;
var lineComboConfig = {
    type: 'bar',
    data: {
        labels: [
            newDateString(0),
            newDateString(1),
            newDateString(2),
            newDateString(3),
            newDateString(4),
            newDateString(5)
        ],
        datasets: [{
            type: 'line',
            label: 'Dataset 1',
            backgroundColor: color(window.chartColors.red).alpha(0.5).rgbString(),
            borderColor: window.chartColors.red,
//			fill: false,
            data: [
                randomScalingFactor(100),
                randomScalingFactor(100),
                randomScalingFactor(100),
                randomScalingFactor(100),
                randomScalingFactor(100),
                randomScalingFactor(100)
            ],
        }, {
            type: 'line',
            label: 'Dataset 2',
            backgroundColor: color(window.chartColors.blue).alpha(0.5).rgbString(),
            borderColor: window.chartColors.blue,
//			fill: false,
            data: [
                randomScalingFactor(100),
                randomScalingFactor(100),
                randomScalingFactor(100),
                randomScalingFactor(100),
                randomScalingFactor(100),
                randomScalingFactor(100)
            ],
        }, {
            type: 'line',
            label: 'Dataset 3',
            backgroundColor: color(window.chartColors.green).alpha(0.5).rgbString(),
            borderColor: window.chartColors.green,
//            fill: false,
            data: [
                randomScalingFactor(100),
                randomScalingFactor(100),
                randomScalingFactor(100),
                randomScalingFactor(100),
                randomScalingFactor(100),
                randomScalingFactor(100)
            ],
        }, ]
    },
    options: {
        title: {
            text: "Chart.js Combo Time Scale"
        },
		legend: {
			labels: {
				boxWidth: 60,
				fontSize: 20,
			}
        },
		aspectRatio: 4,
        scales: {
            xAxes: [{
                type: "time",
                display: true,
                time: {
                    format: timeFormat,
                    // round: 'day'
                }
            }],
        },
		layout: {
			padding: {
				top: 20,
			}
		}
    }
};

function initLineComboChart() {
    var ctx = document.getElementById("line-combo-chart-area").getContext("2d");
    window.myLineCombo = new Chart(ctx, lineComboConfig);
};

document.getElementById('updateLineComboChartData').addEventListener('click', function() {
    lineComboConfig.data.datasets.forEach(function(dataset) {
        dataset.data = dataset.data.map(function() {
            return randomScalingFactor(100);
        });
    });

    window.myLineCombo.update();
});

var colorNames = Object.keys(window.chartColors);
document.getElementById('addLineComboChartDataset').addEventListener('click', function() {
    var colorName = colorNames[lineComboConfig.data.datasets.length % colorNames.length];
    var newColor = window.chartColors[colorName];
    var newDataset = {
        label: 'Dataset ' + lineComboConfig.data.datasets.length,
        borderColor: newColor,
        backgroundColor: color(newColor).alpha(0.5).rgbString(),
		type: 'bar',
        data: [],
    };

    for (var index = 0; index < lineComboConfig.data.labels.length; ++index) {
        newDataset.data.push(randomScalingFactor(100));
    }

    lineComboConfig.data.datasets.push(newDataset);
    window.myLineCombo.update();
});

document.getElementById('removeLineComboChartDataset').addEventListener('click', function() {
    lineComboConfig.data.datasets.splice(0, 1);
    window.myLineCombo.update();
});

document.getElementById('addLineComboChartData').addEventListener('click', function() {
    if (lineComboConfig.data.datasets.length > 0) {
        lineComboConfig.data.labels.push(newDateString(lineComboConfig.data.labels.length));

        for (var index = 0; index < lineComboConfig.data.datasets.length; ++index) {
            lineComboConfig.data.datasets[index].data.push(randomScalingFactor(100));
        }

        window.myLineCombo.update();
    }
});

document.getElementById('removeLineComboChartData').addEventListener('click', function() {
    lineComboConfig.data.labels.splice(-1, 1); // remove the label first

    lineComboConfig.data.datasets.forEach(function(dataset, datasetIndex) {
        lineComboConfig.data.datasets[datasetIndex].data.pop();
    });

    window.myLineCombo.update();
});