
(function (window, document, $) {
    'use strict';
    if ($("#summernoteExample1").length) {
    $("#summernoteExample1").summernote({
			height: 200,
			toolbar: [
				["style", ["style"]],
				["fontname", ["fontname"]],
				["fontsize", ["fontsize"]],
        ["para", ["ol", "ul", "paragraph", "height"]],
				["insert", ["link"]],
				["view", ["undo", "redo", "fullscreen", "codeview", "help"]]
			],
      tabsize: 2
    });
  }
  if ($("#summernoteExample2").length) {
    $("#summernoteExample2").summernote({
			height: 200,
			toolbar: [
				["style", ["style"]],
				["fontname", ["fontname"]],
				["fontsize", ["fontsize"]],
        ["para", ["ol", "ul", "paragraph", "height"]],
				["insert", ["link"]],
				["view", ["undo", "redo", "fullscreen", "codeview", "help"]]
			],
      tabsize: 2
    });
  }
    
  if ($("#summernoteExample3").length) {
    $("#summernoteExample3").summernote({
			height: 200,
			toolbar: [
				["style", ["style"]],
				["fontname", ["fontname"]],
				["fontsize", ["fontsize"]],
        ["para", ["ol", "ul", "paragraph", "height"]],
				["insert", ["link"]],
				["view", ["undo", "redo", "fullscreen", "codeview", "help"]]
			],
      tabsize: 2
    });
  }

  if ($("#summernoteExample4").length) {
    $("#summernoteExample4").summernote({
			height: 200,
			toolbar: [
				["style", ["style"]],
				["fontname", ["fontname"]],
				["fontsize", ["fontsize"]],
        ["para", ["ol", "ul", "paragraph", "height"]],
				["insert", ["link"]],
				["view", ["undo", "redo", "fullscreen", "codeview", "help"]]
			],
      tabsize: 2
	});
	
	
  }
  if ($(".select2").length) {
  $(".select2").select2({
	dropdownAutoWidth: true,
	width: '100%'
  });
}
  
  })(window, document, jQuery);

  function admSelectCheck(nameSelect) {

    if (nameSelect) {
      userValue = document.getElementById("users").value;
      driverValue = document.getElementById("pengendara").value;
      console.log(nameSelect.value, driverValue)
      merchantValue = document.getElementById("merchant").value;
      bothValue = document.getElementById("other").value;

      if (userValue == nameSelect.value) {
        console.log(nameSelect.value, userValue)
        document.getElementById("linktes").required = false;
        document.getElementById("usercheck").style.display = "block";
        document.getElementById("merchantcheck").style.display = "none";
        document.getElementById("drivercheck").style.display = "none";
        document.getElementById("othercheck").style.display = "none";
      }
      if (nameSelect.value == driverValue) {
        console.log(nameSelect.value, driverValue)
        document.getElementById("linktes").required = false;
        document.getElementById("drivercheck").style.display = "block";
        document.getElementById("merchantcheck").style.display = "none";
        document.getElementById("usercheck").style.display = "none";
        document.getElementById("othercheck").style.display = "none";
      }
      if (merchantValue == nameSelect.value) {
        console.log(nameSelect.value, merchantValue)
        document.getElementById("linktes").required = false;
        document.getElementById("merchantcheck").style.display = "block";
        document.getElementById("drivercheck").style.display = "none";
        document.getElementById("usercheck").style.display = "none";
        document.getElementById("othercheck").style.display = "none";
      }
      if (bothValue == nameSelect.value) {
        console.log(nameSelect.value, bothValue)
        document.getElementById("linktes").required = true;
        document.getElementById("drivercheck").style.display = "none";
        document.getElementById("usercheck").style.display = "none";
        document.getElementById("merchantcheck").style.display = "none";
        document.getElementById("othercheck").style.display = "block";
      }
    } else {
      document.getElementById("usercheck").style.display = "block";
    }
  }
  