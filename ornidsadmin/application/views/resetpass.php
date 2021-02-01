<!DOCTYPE html>

<html lang="en">

<head>
	<title>ouride - reset password</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!--===============================================================================================-->
	<link rel="icon" type="image/png" href="<?= base_url(); ?>asset/images/logo.png" />
	<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="<?= base_url(); ?>/assetreset/vendor/bootstrap/css/bootstrap.min.css">
	<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="<?= base_url(); ?>/assetreset/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
	<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="<?= base_url(); ?>/assetreset/fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
	<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="<?= base_url(); ?>/assetreset/vendor/animate/animate.css">
	<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="<?= base_url(); ?>/assetreset/vendor/css-hamburgers/hamburgers.min.css">
	<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="<?= base_url(); ?>/assetreset/vendor/animsition/css/animsition.min.css">
	<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="<?= base_url(); ?>/assetreset/vendor/select2/select2.min.css">
	<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="<?= base_url(); ?>assetreset/vendor/daterangepicker/daterangepicker.css">
	<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="<?= base_url(); ?>/assetreset/css/util.css">
	<link rel="stylesheet" type="text/css" href="<?= base_url(); ?>/assetreset/css/main.css">
	<!--===============================================================================================-->
</head>

<body>
	<div class="col-md-12">
		<?php if (isset($_SESSION['msg'])) { ?>
			<div class="alert alert-success alert-dismissible" role="alert"> <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
				<?php echo $client_lang[$_SESSION['msg']]; ?></a> </div>
		<?php unset($_SESSION['msg']);
		} ?>
	</div>

	<div class="limiter">


		<div class="container-login100">

			<div class="wrap-login100 p-t-50 p-b-90">

				<img style="width:60px;height:60px;margin-bottom:30px;" class="center" src="<?= base_url(); ?>asset/app-assets/images/logo/logoweb.png" alt="Card image cap">

				<form class="login100-form validate-form flex-sb flex-w" method="post">
					<input type="hidden" name="token" value="<?= $user['token']; ?>" />
					<input type="hidden" name="type" value="<?= $user['idkey']; ?>" />
					<input type="hidden" name="id" value="<?= $user['userid']; ?>" />
					<span class="login100-form-title p-b-51">
						Reset Password
					</span>


					<div class="form-group wrap-input100 validate-input m-b-16" data-validate="Password tidak boleh kosong!">
						<input class="input100" type="password" name="password" id="password" placeholder="New Password">
						<span class="focus-input100"></span>
					</div>

					<div class="form-group container-login100-form-btn m-t-17">
						<button class="login100-form-btn" type="submit" id="submit" name="submitter">
							Reset Password
						</button>
					</div>

				</form>
			</div>
		</div>
	</div>


	<div id="dropDownSelect1"></div>

	<!--===============================================================================================-->
	<script src="<?= base_url(); ?>/assetreset/vendor/jquery/jquery-3.2.1.min.js"></script>
	<!--===============================================================================================-->
	<script src="<?= base_url(); ?>/assetreset/vendor/animsition/js/animsition.min.js"></script>
	<!--===============================================================================================-->
	<script src="<?= base_url(); ?>/assetreset/vendor/bootstrap/js/popper.js"></script>
	<script src="<?= base_url(); ?>/assetreset/vendor/bootstrap/js/bootstrap.min.js"></script>
	<!--===============================================================================================-->
	<script src="<?= base_url(); ?>/assetreset/vendor/select2/select2.min.js"></script>
	<!--===============================================================================================-->
	<script src="<?= base_url(); ?>/assetreset/vendor/daterangepicker/moment.min.js"></script>
	<script src="<?= base_url(); ?>/assetreset/vendor/daterangepicker/daterangepicker.js"></script>
	<!--===============================================================================================-->
	<script src="<?= base_url(); ?>/assetreset/vendor/countdowntime/countdowntime.js"></script>
	<!--===============================================================================================-->
	<script src="<?= base_url(); ?>/assetreset/js/main.js"></script>

</body>

</html>