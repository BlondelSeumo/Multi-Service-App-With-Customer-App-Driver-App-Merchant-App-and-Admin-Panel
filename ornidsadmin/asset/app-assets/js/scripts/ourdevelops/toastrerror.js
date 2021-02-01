

    var desc = $("#desctoast").val();

    $('#type-danger').ready(function () {
        toastr.error('', desc);
      });
  