var doughnutChartConfig = {
    type: 'doughnut',
    data: getInitialData(),
    options: {
        responsive: true,
        legend: {
            position: 'right',
			labels: {
				boxWidth: 80,
				fontSize: 25,
			}
        },
        title: {
            display: true,
			fontSize: 30,
			fontStyle: 'bold',
			fontColor: 'Red',
			fontFamily: "'PT Sans Caption', sans-serif",
            text: 'Power consumption summary'
        },
        animation: {
            animateScale: true,
            animateRotate: true
        },
		aspectRatio: 1.5,
		layout: {
			padding: {
				top: 10,
			}
		}
    }
};

function getInitialData() {
    return {
        datasets: [{
            data: [
                randomScalingFactor(100),
                randomScalingFactor(100),
                randomScalingFactor(100),
                randomScalingFactor(100),
                randomScalingFactor(100),
            ],
            backgroundColor: [
                randomRgbString(1),
                randomRgbString(1),
                randomRgbString(1),
                randomRgbString(1),
                randomRgbString(1),
            ],
            label: 'Dataset 1'
        }],
        labels: [
            "Red",
            "Orange",
            "Yellow",
            "Green",
            "Blue",
        ]
    };
}

///////////////////////////////////////////////////////////////////////////////////
//    Chart operation
//
function updateData() {
    doughnutChartConfig.data.datasets.forEach(function(dataset) {
        dataset.data = dataset.data.map(function() {
            return randomScalingFactor(100);
        });
    });
}

function addData(dataValue=function() {
	return randomScalingFactor(100);
}) {
	doughnutChartConfig.data.labels.push('data #' + doughnutChartConfig.data.labels.length);
    doughnutChartConfig.data.datasets.forEach(function(dataset) {
        dataset.data.push(randomScalingFactor(dataValue()));
        dataset.backgroundColor.push(randomRgbString(1));
    });
	return dataValue;
}

function removeData(index, number = 1) {
    doughnutChartConfig.data.labels.splice(index, number); // remove the label first
    doughnutChartConfig.data.datasets.forEach(function(dataset) {
		dataset.data.splice(index, number);
		dataset.backgroundColor.splice(index, number);
    });
}

///////////////////////////////////////////////////////////////////////////////////
// Onload function
//
function initDoughnutChart() {
    var ctx = document.getElementById("doughnut-chart-area").getContext("2d");
    window.myDoughnut = new Chart(ctx, doughnutChartConfig);
	//return myDoughnut
};

///////////////////////////////////////////////////////////////////////////////////
// UI Actions - Single item
//
/*  */
document.getElementById('updateData').addEventListener('click', function() {
    updateData();
    window.myDoughnut.update();
});

document.getElementById('addData').addEventListener('click', function() {
    if (doughnutChartConfig.data.datasets.length > 0) {
		addData();
        window.myDoughnut.update();
    }
});

document.getElementById('removeData').addEventListener('click', function() {
	removeData(randomScalingFactor(doughnutChartConfig.data.datasets[0].data.length) - 1);
    window.myDoughnut.update();
});

///////////////////////////////////////////////////////////////////////////////////
// UI Actions - Batch add or remove
//
var colorNames = Object.keys(window.chartColors);
document.getElementById('addDataset').addEventListener('click', function() {
    var newDataset = {
        backgroundColor: [],
        data: [],
        label: 'New dataset ' + doughnutChartConfig.data.datasets.length,
    };

    for (var index = 0; index < doughnutChartConfig.data.labels.length; ++index) {
        newDataset.data.push(randomScalingFactor(100));
		newDataset.backgroundColor.push(randomRgbString(1));
    }

    doughnutChartConfig.data.datasets.push(newDataset);
    window.myDoughnut.update();
});

document.getElementById('removeDataset').addEventListener('click', function() {
    doughnutChartConfig.data.datasets.splice(0, 1);
    window.myDoughnut.update();
});