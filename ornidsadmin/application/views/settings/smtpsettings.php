<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- start SMTP setting -->

            <section id="basic-vertical-layouts">
                <div class="row match-height justify-content-center">
                    <div class="col-md-8 col-12">
                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">SMTP Settings</h4>
                            </div>
                            <div class="card-content">
                                <div class="card-body">
                                    <?= form_open_multipart('settings/editsmtp'); ?>
                                    <form class="form form-vertical">
                                        <div class="form-body">
                                            <div class="row">
                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="smtphost">SMTP Host</label>
                                                        <input type="text" value="<?= $appsettings['smtp_host']; ?>" class="form-control" id="smtphost" name="smtp_host" required>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="smtpport">SMTP Port</label>
                                                        <input type="text" class="form-control" id="smtpport" name="smtp_port" value="<?= $appsettings['smtp_port']; ?>" required>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="smtpusername">SMTP User Name</label>
                                                        <input type="text" class="form-control" id="smtpusername" name="smtp_username" value="<?= $appsettings['smtp_username']; ?>" required>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="smtppassword">SMTP Password</label>
                                                        <?php if (demo == TRUE) { ?>
                                                        <input type="password" class="form-control" id="smtppassword" name="smtp_password" autocomplete="off" value="123456" required>
                                                        <?php } else { ?>
                                                        <input type="password" class="form-control" id="smtppassword" name="smtp_password" autocomplete="off" value="<?= $appsettings['smtp_password']; ?>" required>
                                                        <?php } ?>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="smtpform">SMTP Form</label>
                                                        <input type="text" class="form-control" id="smtpfrom" name="smtp_from" value="<?= $appsettings['smtp_from']; ?>" required>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="smtp_secure">SMTP Secure</label>
                                                        <select class="select2 form-group" name="smtp_secure" id="smtp_secure">
                                                            <option value="tls" <?php if ($appsettings['smtp_secure'] == 'tls') { ?>selected<?php } ?>>TLS</option>
                                                            <option value="ssl" <?php if ($appsettings['smtp_secure'] == 'ssl') { ?>selected<?php } ?>>SSL</option>
                                                        </select>
                                                    </div>

                                                    <div class="col-12">
                                                        <button type="submit" class="btn btn-primary mb-1">Submit</button>
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

            <!-- end of SMTP setting data -->
        </div>
    </div>
</div>
<!-- END: Content-->