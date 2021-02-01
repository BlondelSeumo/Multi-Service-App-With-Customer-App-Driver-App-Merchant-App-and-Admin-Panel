
$(document).ready(function () {
    //initialize swiper when document ready
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
    
  
    // centered slides option-1
    var mySwiperOpt1 = new Swiper('.swiper-centered-slides', {
      slidesPerView: 'auto',
      centeredSlides: true,
      spaceBetween: 30,
      navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev',
      },
    });
  
    // centered slides option-2
  
    var swiperLength = $(".swiper-slide").length;
    if (swiperLength) {
      swiperLength = Math.floor(swiperLength / 2)
    }

    $.ajax({
		url: base_url + "/api/adminapi/chart_entireprogress",
		type: "GET",
		success: function (data) {
			console.log(data);
			var goalChartoptions = {
				chart: {
					height: 250,
					type: "radialBar",
					sparkline: {
						enabled: true,
					},
					dropShadow: {
						enabled: true,
						blur: 3,
						left: 1,
						top: 1,
						opacity: 0.1,
					},
				},
				colors: [$success],
				plotOptions: {
					radialBar: {
						size: 110,
						startAngle: -150,
						endAngle: 150,
						hollow: {
							size: "60%",
						},
						track: {
							background: $strok_color,
							strokeWidth: "20%",
						},
						dataLabels: {
							name: {
								show: false,
							},
							value: {
								offsetY: 18,
								color: "#99a2ac",
								fontSize: "4rem",
							},
						},
					},
				},
				fill: {
					type: "gradient",
					gradient: {
						shade: "dark",
						type: "horizontal",
						shadeIntensity: 0.5,
						gradientToColors: ["#00b5b5"],
						inverseColors: true,
						opacityFrom: 1,
						opacityTo: 1,
						stops: [0, 100],
					},
				},
				series: [data.progress],
				stroke: {
					lineCap: "round",
				},
			};

			var goalChart = new ApexCharts(
				document.querySelector("#goal-overview-chart"),
				goalChartoptions
			);

			goalChart.render();
		},
	});

	$.ajax({
		url: base_url + "/api/adminapi/chart_transactions",
		type: "GET",
		success: function (data) {
			var lineAreaOptions = {
				chart: {
					height: 360,
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
						name: "passenger transportation",
						data: [
							data.jan1,
							data.feb1,
							data.mar1,
							data.apr1,
							data.mei1,
							data.jun1,
							data.jul1,
							data.aug1,
							data.sep1,
							data.okt1,
							data.nov1,
							data.des1,
						],
					},
					{
						name: "shipment",
						data: [
							data.jan2,
							data.feb2,
							data.mar2,
							data.apr2,
							data.mei2,
							data.jun2,
							data.jul2,
							data.aug2,
							data.sep2,
							data.okt2,
							data.nov2,
							data.des2,
						],
					},
					{
						name: "rental",
						data: [
							data.jan3,
							data.feb3,
							data.mar3,
							data.apr3,
							data.mei3,
							data.jun3,
							data.jul3,
							data.aug3,
							data.sep3,
							data.okt3,
							data.nov3,
							data.des3,
						],
					},
					{
						name: "purchasing service",
						data: [
							data.jan4,
							data.feb4,
							data.mar4,
							data.apr4,
							data.mei4,
							data.jun4,
							data.jul4,
							data.aug4,
							data.sep4,
							data.okt4,
							data.nov4,
							data.des4,
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
  