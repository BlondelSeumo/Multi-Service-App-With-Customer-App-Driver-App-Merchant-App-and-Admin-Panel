<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- start application setting -->

            <section id="basic-vertical-layouts">
                <div class="row match-height justify-content-center">
                    <div class="col-md-8 col-12">
                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">Application Settings</h4>
                            </div>
                            <div class="card-content">
                                <div class="card-body">
                                    <?= form_open_multipart('settings/editapp'); ?>
                                    <form class="form form-vertical">
                                        <div class="form-body">
                                            <div class="row">
                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="appemail">App Email</label>
                                                        <input type="email" class="form-control" id="appemail" name="app_email" value="<?= $appsettings['app_email']; ?>" required></div>
                                                    <div class="form-group">
                                                        <label for="appname">App Name</label>
                                                        <input type="text" class="form-control" id="appname" name="app_name" value="<?= $appsettings['app_name']; ?>" required></div>
                                                    <div class="form-group">
                                                        <label for="appcontact">App Contact</label>
                                                        <input type="text" class="form-control" id="appcontact" name="app_contact" value="<?= $appsettings['app_contact']; ?>" required></div>
                                                    <div class="form-group">
                                                        <label for="appwebsite">App Website</label>
                                                        <input type="text" class="form-control" id="appwebsite" name="app_website" value="<?= $appsettings['app_website']; ?>" required></div>
                                                    <div class="form-group">
                                                        <label for="privacypolicy">Privacy Policy</label>
                                                        <textarea type="text" class="form-control" id="summernoteExample1" name="app_privacy_policy" required><?= $appsettings['app_privacy_policy']; ?></textarea>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="aboutus">About Us</label>
                                                        <textarea type="text" class="form-control" id="summernoteExample2" name="app_aboutus" required><?= $appsettings['app_aboutus']; ?></textarea>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="appaddress">App Address</label>
                                                        <textarea type="text" class="form-control" id="summernoteExample3" name="app_address" required><?= $appsettings['app_address']; ?></textarea></div>
                                                    <div class="form-group">
                                                        <label for="googlelink">Google Link</label>
                                                        <input type="text" class="form-control" id="googlelink" name="app_linkgoogle" value="<?= $appsettings['app_linkgoogle']; ?>" required></div>
                                                    <div class="form-group">
                                                        <label for="appcurrency">App Currency</label>
                                                        <input type="text" class="form-control" id="appcurrency" name="app_currency" value="<?= $appsettings['app_currency']; ?>" required></div>

                                                    <div class="col-12">
                                                        <button type="submit" class="btn btn-primary mr-1 mb-1">Submit</button>
                                                    </div>
                                                </div>
                                            </div>
                                    </form>
                                    <?= form_close(); ?>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <!-- end of application setting data -->
        </div>
    </div>
</div>
<!-- END: Content-->