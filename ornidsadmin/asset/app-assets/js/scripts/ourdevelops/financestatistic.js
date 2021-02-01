


$(document).ready(function () {
	var base_url = $("#base").val();
	var $primary = "#a357d7";
	var $warning = "#FF9F43";
	var $info = "#0DCCE1";
	var $danger = "#EA5455";
	var $success = "#00db89";

	var themeColors = [$primary, $success, $danger, $warning, $info];

	var yaxis_opposite = false;
	if ($("html").data("textdirection") == "rtl") {
		yaxis_opposite = true;
	}

	$.ajax({
		url: base_url + "/api/adminapi/revenuechart",
		type: "GET",
		success: function (data) {
			var lineAreaOptions = {
				chart: {
					height: 280,
					type: "area",
				},
				colors: themeColors,
				dataLabels: {
					enabled: false,
				},
				stroke: {
					curve: "smooth",
				},
				series: [
					{
						name: "revenue chart",
						data: [
							data.jan,
							data.feb,
							data.mar,
							data.apr,
							data.mei,
							data.jun,
							data.jul,
							data.aug,
							data.sep,
							data.oct,
							data.nov,
							data.des,
						],
					},
					
				],
				legend: {
					offsetY: -10,
				},
				xaxis: {
					categories: [
						"jan",
						"feb",
						"mar",
						"apr",
						"mei",
						"jun",
						"jul",
						"aug",
						"sep",
						"okt",
						"nov",
						"des",
					],
				},
				yaxis: {
					opposite: yaxis_opposite,
				},
				tooltip: {
					x: {
						format: "dd/MM/yy HH:mm",
					},
				},
			};
			var lineAreaChart = new ApexCharts(
				document.querySelector("#line-area-chart"),
				lineAreaOptions
			);
			lineAreaChart.render();

			//Chart1 ends //
		},
	});
	

	
});
