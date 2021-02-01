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
                                <h4 class="card-title">Email Settings</h4>
                            </div>
                            <br>
                            <div class="card-content">
                                <div class="card-body">
                                    <?= form_open_multipart('settings/editemail'); ?>
                                    <form class="form form-vertical">
                                        <div class="form-body">
                                            <div class="row">
                                                <!-- start forgot password email template -->
                                                <div class="col-12">
                                                    <h5 class="text-muted">Email Template
                                                    </h5>
                                                    <div class="form-group">
                                                        <label for="emailsubject">Email Subject</label>
                                                        <textarea type="email" class="form-control" id="emailsubject" name="email_subject" required><?= $appsettings['email_subject']; ?></textarea>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="emailtext1">Email Text 1</label>
                                                        <textarea type="email" class="form-control" id="summernoteExample1" name="email_text1" required><?= $appsettings['email_text1']; ?></textarea>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="emailtext2">Email Text 2</label>
                                                        <textarea type="email" class="form-control" id="summernoteExample2" name="email_text2" required><?= $appsettings['email_text2']; ?></textarea>
                                                    </div>

                                                    <h4 class="card-title">Email Template For Confirm Driver</h4>

                                                    <div class="form-group">
                                                        <label for="emailsubject">Email Subject</label>
                                                        <textarea type="email" class="form-control" id="emailsubject" name="email_subject_confirm" required><?= $appsettings['email_subject_confirm']; ?></textarea>
                                                    </div>

                                                    <div class="form-group">
                                                        <label for="emailtext1">Email Text 1</label>
                                                        <textarea type="email" class="form-control" id="summernoteExample3" name="email_text3" required><?= $appsettings['email_text3']; ?></textarea>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="emailtext2">Email Text 2</label>
                                                        <textarea type="email" class="form-control" id="summernoteExample4" name="email_text4" required><?= $appsettings['email_text4']; ?></textarea>
                                                    </div>

                                                    <!-- end of driver confirmation email template -->

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