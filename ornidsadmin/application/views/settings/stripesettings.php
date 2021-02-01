<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- start stripe setting -->

            <section id="basic-vertical-layouts">
                <div class="row match-height justify-content-center">
                    <div class="col-md-8 col-12">
                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">Stripe Settings</h4>
                            </div>
                            <div class="card-content">
                                <div class="card-body">
                                    <?= form_open_multipart('settings/editstripe'); ?>
                                    <form class="form form-vertical">
                                        <div class="form-body">
                                            <div class="row">
                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="stripesecretkey">Stripe Secret Key</label>
                                                        <input type="text" class="form-control" id="stripesecretkey" name="stripe_secret_key" value="<?= $appsettings['stripe_secret_key']; ?>" required>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="stripepublishedkey">Stripe Published Key</label>
                                                        <input type="text" class="form-control" id="stripepublishedkey" name="stripe_published_key" value="<?= $appsettings['stripe_published_key']; ?>" required>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="stripe_status">Stripe Mode</label>
                                                        <select name="stripe_status" id="stripe_status" class="select2 form-group" style="width:100%">
                                                            <option value="1" <?php if ($appsettings['stripe_status'] == '1') { ?>selected<?php } ?>>Development Mode</option>
                                                            <option value="2" <?php if ($appsettings['stripe_status'] == '2') { ?>selected<?php } ?>>Published</option>
                                                        </select>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="stripe_active">Stripe Status</label>
                                                        <select name="stripe_active" id="stripe_active" class="select2 form-group" style="width:100%">
                                                            <option value="1" <?php if ($appsettings['stripe_active'] == '1') { ?>selected<?php } ?>>Active</option>
                                                            <option value="0" <?php if ($appsettings['stripe_active'] == '0') { ?>selected<?php } ?>>NonActive</option>
                                                        </select>
                                                    </div>

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

            <!-- end of stripe setting data -->
        </div>
    </div>
</div>
<!-- END: Content-->