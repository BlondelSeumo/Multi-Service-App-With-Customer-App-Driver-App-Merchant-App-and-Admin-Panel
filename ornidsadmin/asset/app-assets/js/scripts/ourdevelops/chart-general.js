


$(document).ready(function () {
	var base_url = $("#base").val();
	var $primary = "#a357d7";
	var $warning = "#FF9F43";
	var $info = "#0DCCE1";
	var $danger = "#EA5455";
	var $success = "#00db89";
	var $strok_color = "#b9c3cd";

	var themeColors = [$primary, $success, $danger, $warning, $info];

	var yaxis_opposite = false;
	if ($("html").data("textdirection") == "rtl") {
		yaxis_opposite = true;
	}

	$.ajax({
		url: base_url + "/api/adminapi/chart_user",
		type: "GET",
		success: function (data) {
			var firstday = data.firstday;
			var secondday = data.secondday;
			var thirdday = data.thirdday;
			var forthday = data.forthday;
			var fifthday = data.fifthday;
			var sixthday = data.sixthday;
			var seventhday = data.seventhday;
			var usersChartoptions = {
				chart: {
					height: 100,
					type: "area",
					toolbar: {
						show: false,
					},
					sparkline: {
						enabled: true,
					},
					grid: {
						show: false,
						padding: {
							left: 0,
							right: 0,
						},
					},
				},
				colors: [$success],
				dataLabels: {
					enabled: false,
				},
				stroke: {
					curve: "smooth",
					width: 2.5,
				},
				fill: {
					type: "gradient",
					gradient: {
						shadeIntensity: 0.9,
						opacityFrom: 0.7,
						opacityTo: 0.5,
						stops: [0, 80, 100],
					},
				},
				series: [
					{
						name: "Users",
						data: [
							firstday,
							secondday,
							thirdday,
							forthday,
							fifthday,
							sixthday,
							seventhday,
						],
					},
				],

				xaxis: {
					labels: {
						show: false,
					},
					axisBorder: {
						show: false,
					},
				},
				yaxis: [
					{
						y: 0,
						offsetX: 0,
						offsetY: 0,
						padding: { left: 0, right: 0 },
						
						
					},
				],
				tooltip: {
					x: { show: false },
				},
			};

			var usersChart = new ApexCharts(
				document.querySelector("#chart1"),
				usersChartoptions
			);

			usersChart.render();

			//Chart1 ends //
		},
	});

	$.ajax({
		url: base_url + "/api/adminapi/chart_driver",
		type: "GET",
		success: function (data) {
			var firstday = data.firstday;
			var secondday = data.secondday;
			var thirdday = data.thirdday;
			var forthday = data.forthday;
			var fifthday = data.fifthday;
			var sixthday = data.sixthday;
			var seventhday = data.seventhday;

			var driverChartoptions = {
				chart: {
					height: 100,
					type: "area",
					toolbar: {
						show: false,
					},
					sparkline: {
						enabled: true,
					},
					grid: {
						show: false,
						padding: {
							left: 0,
							right: 0,
						},
					},
				},
				colors: [$danger],
				dataLabels: {
					enabled: false,
				},
				stroke: {
					curve: "smooth",
					width: 2.5,
				},
				fill: {
					type: "gradient",
					gradient: {
						shadeIntensity: 0.9,
						opacityFrom: 0.7,
						opacityTo: 0.5,
						stops: [0, 80, 100],
					},
				},
				series: [
					{
						name: "Driver",
						data: [
							firstday,
							secondday,
							thirdday,
							forthday,
							fifthday,
							sixthday,
							seventhday,
						],
					},
				],

				xaxis: {
					labels: {
						show: false,
					},
					axisBorder: {
						show: false,
					},
				},
				yaxis: [
					{
						y: 0,
						offsetX: 0,
						offsetY: 0,
						padding: { left: 0, right: 0 },
					},
				],
				tooltip: {
					x: { show: false },
				},
			};

			var driverChart = new ApexCharts(
				document.querySelector("#chart2"),
				driverChartoptions
			);

			driverChart.render();
			//Chart2 ends //
		},
	});

	// Chart3 starts //
	// ----------------------------------

	$.ajax({
		url: base_url + "/api/adminapi/chart_merchant",
		type: "GET",
		success: function (data) {
			var firstday = data.firstday;
			var secondday = data.secondday;
			var thirdday = data.thirdday;
			var forthday = data.forthday;
			var fifthday = data.fifthday;
			var sixthday = data.sixthday;
			var seventhday = data.seventhday;

			var merchantChartoptions = {
				chart: {
					height: 100,
					type: "area",
					toolbar: {
						show: false,
					},
					sparkline: {
						enabled: true,
					},
					grid: {
						show: false,
						padding: {
							left: 0,
							right: 0,
						},
					},
				},
				colors: [$warning],
				dataLabels: {
					enabled: false,
				},
				stroke: {
					curve: "smooth",
					width: 2.5,
				},
				fill: {
					type: "gradient",
					gradient: {
						shadeIntensity: 0.9,
						opacityFrom: 0.7,
						opacityTo: 0.5,
						stops: [0, 80, 100],
					},
				},
				series: [
					{
						name: "Merchant",
						data: [
							firstday,
							secondday,
							thirdday,
							forthday,
							fifthday,
							sixthday,
							seventhday,
						],
					},
				],

				xaxis: {
					labels: {
						show: false,
					},
					axisBorder: {
						show: false,
					},
				},
				yaxis: [
					{
						y: 0,
						offsetX: 0,
						offsetY: 0,
						padding: { left: 0, right: 0 },
					},
				],
				tooltip: {
					x: { show: false },
				},
			};

			var merchantChart = new ApexCharts(
				document.querySelector("#chart3"),
				merchantChartoptions
			);

			merchantChart.render();
			//Chart3 ends //
		},
	});
	

	// Pie Chart
	// -----------------------------
	$.ajax({
		url: base_url + "api/adminapi/allservice/ok",
		type: "GET",
		success: function (data) {
			var labels = data.data.map(function (e) {
				return e.name;
			});
			var totals = data.data.map(function (e) {
				return Number(e.total);
			});
			var pieChartOptions = {
				chart: {
					type: "pie",
					height: 350,
				},
				colors: themeColors,
				labels: labels,
				series: totals,
				legend: {
					itemMargin: {
						horizontal: 2,
					},
				},
				responsive: [
					{
						breakpoint: 480,
						options: {
							chart: {
								width: 350,
							},
							legend: {
								position: "bottom",
							},
						},
					},
				],
			};
			var pieChart = new ApexCharts(
				document.querySelector("#pie-chart"),
				pieChartOptions
			);
			pieChart.render();
		},
	});

	// Donut Chart
	// -----------------------------

	$.ajax({
		url: base_url + "api/adminapi/allmerchantdonut/ok",
		type: "GET",
		success: function (data) {
			var labels = data.data.map(function (e) {
				return e.name;
			});
			var totals = data.data.map(function (e) {
				return Number(e.total);
			});
			var donutChartOptions = {
				chart: {
					type: "donut",
					height: 350,
				},
				colors: themeColors,
				labels: labels,
				series: totals,
				legend: {
					itemMargin: {
						horizontal: 2,
					},
				},
				responsive: [
					{
						breakpoint: 480,
						options: {
							chart: {
								width: 350,
							},
							legend: {
								position: "bottom",
							},
						},
					},
				],
			};
			var donutChart = new ApexCharts(
				document.querySelector("#donut-chart"),
				donutChartOptions
			);

			donutChart.render();
		},
	});

	
});
