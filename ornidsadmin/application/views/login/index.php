<!DOCTYPE html>
<html class="loading" data-textdirection="ltr">
<!-- BEGIN: Head-->

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimal-ui">
    <meta name="description" content="Multi Service App With Customer App, Driver App, Merchant App and Admin Panel">
    <meta name="keywords" content="car rental, codeigniter, delivery, driver, grab, maps tracking, ordering, package, ride, send, stripe, taxi, transportation, uber, wallet">
    <meta name="author" content="ourdevelops">
    <title>Login</title>
    <link rel="apple-touch-icon" href="<?= base_url(); ?>asset/app-assets/images/ico/apple-icon-120.png">
    <link rel="shortcut icon" type="image/x-icon" href="<?= base_url(); ?>asset/app-assets/images/ico/logoweb.png">
    <link href="<?= base_url(); ?>asset/app-assets/css/ourdevelops/font.css" rel="stylesheet">

    <!-- BEGIN: Vendor CSS-->
    <link rel="stylesheet" type="text/css" href="<?= base_url(); ?>asset/app-assets/vendors/css/vendors.min.css">
    <!-- END: Vendor CSS-->

    <!-- BEGIN: Theme CSS-->
    <link rel="stylesheet" type="text/css" href="<?= base_url(); ?>asset/app-assets/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="<?= base_url(); ?>asset/app-assets/css/bootstrap-extended.css">
    <link rel="stylesheet" type="text/css" href="<?= base_url(); ?>asset/app-assets/css/colors.css">
    <link rel="stylesheet" type="text/css" href="<?= base_url(); ?>asset/app-assets/css/components.css">
    <link rel="stylesheet" type="text/css" href="<?= base_url(); ?>asset/app-assets/css/themes/dark-layout.css">
    <link rel="stylesheet" type="text/css" href="<?= base_url(); ?>asset/app-assets/css/themes/semi-dark-layout.css">
    <link rel="stylesheet" type="text/css" href="<?= base_url(); ?>asset/app-assets/vendors/css/extensions/toastr.css">

    <!-- BEGIN: Page CSS-->
    <link rel="stylesheet" type="text/css" href="<?= base_url(); ?>asset/app-assets/css/core/menu/menu-types/horizontal-menu.css">
    <link rel="stylesheet" type="text/css" href="<?= base_url(); ?>asset/app-assets/css/core/colors/palette-gradient.css">
    <link rel="stylesheet" type="text/css" href="<?= base_url(); ?>asset/app-assets/css/pages/authentication.css">
    <link rel="stylesheet" type="text/css" href="<?= base_url(); ?>asset/app-assets/css/plugins/extensions/toastr.css">
    <!-- END: Page CSS-->

    <!-- BEGIN: Custom CSS-->
    <link rel="stylesheet" type="text/css" href="<?= base_url(); ?>asset/assets/css/style.css">
    <!-- END: Custom CSS-->

</head>
<!-- END: Head-->

<!-- BEGIN: Body-->

<body class="horizontal-layout horizontal-menu 1-column  navbar-floating footer-static bg-full-screen-image  blank-page blank-page" data-open="hover" data-menu="horizontal-menu" data-col="1-column">
    <!-- BEGIN: Content-->
    <div class="app-content content">
        <div class="content-overlay"></div>
        <div class="header-navbar-shadow"></div>
        <div class="content-wrapper">
            <div class="content-header row">
            </div>
            <div class="content-body">
                <?php if ($this->session->flashdata('success')) { ?>

                    <section id="animation">

                        <div id="type-success">

                            <input type="hidden" id="desctoast" value="<?php echo $this->session->flashdata('success'); ?>" />
                        </div>
                    </section>
                <?php } ?>

                <?php if ($this->session->flashdata('danger')) { ?>

                    <section id="animation">

                        <div id="type-danger">

                            <input type="hidden" id="desctoast" value="<?php echo $this->session->flashdata('danger'); ?>" />
                        </div>
                    </section>
                <?php } ?>
                <section class="row flexbox-container">
                    <div class="col-xl-8 col-11 d-flex justify-content-center">
                        <div class="card bg-authentication rounded-0 mb-0">
                            <div class="row m-0">
                                <div class="col-lg-6 d-lg-block d-none text-center align-self-center px-1 py-0">
                                    <img src="<?= base_url(); ?>asset/app-assets/images/logo/login.png" alt="branding logo">
                                </div>
                                <div class="col-lg-6 col-12 p-0">
                                    <div class="card rounded-0 mb-0 px-2">
                                        <div class="card-header pb-1">
                                            <div class="card-title">
                                                <h4 class="mb-0">Login</h4>
                                            </div>
                                        </div>
                                        <div class="card-content">
                                            <div class="card-body pt-1">
                                                <?= form_open_multipart('login/login_action'); ?>
                                                <fieldset class="form-label-group form-group position-relative has-icon-left">
                                                    <input type="text" class="form-control" id="username" placeholder="Username" name="user_name" required>
                                                    <div class="form-control-position">
                                                        <i class="feather icon-user"></i>
                                                    </div>
                                                    <label for="user-name">Username</label>
                                                </fieldset>

                                                <fieldset class="form-label-group position-relative has-icon-left">
                                                    <input type="password" class="form-control" id="userpassword" placeholder="Password" name="password" autocomplete="off" required>
                                                    <div class="form-control-position">
                                                        <i class="feather icon-lock"></i>
                                                    </div>
                                                    <label for="user-password">Password</label>
                                                </fieldset>

                                                <button type="submit" class="btn btn-primary float-right btn-inline mb-2">Login</button>
                                                <?= form_close(); ?>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

            </div>

        </div>

    </div>
    <!-- END: Content-->


    <!-- BEGIN: Vendor JS-->
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/vendors.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/extensions/toastr.min.js"></script>
    <!-- BEGIN Vendor JS-->

    <!-- BEGIN: Page Vendor JS-->
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/ui/jquery.sticky.js"></script>
    <!-- END: Page Vendor JS-->

    <!-- BEGIN: Theme JS-->
    <script src="<?= base_url(); ?>asset/app-assets/js/core/app-menu.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/core/app.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/components.js"></script>

    <?php if ($this->session->flashdata('success')) { ?>
        <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/toastrsuccess.js"></script>
    <?php } ?>

    <?php if ($this->session->flashdata('danger') || $this->session->flashdata('demo')) { ?>
        <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/toastrerror.js"></script>
    <?php } ?>
    <!-- END: Theme JS-->

    <!-- BEGIN: Page JS-->
    <!-- END: Page JS-->

</body>
<!-- END: Body-->

</html>