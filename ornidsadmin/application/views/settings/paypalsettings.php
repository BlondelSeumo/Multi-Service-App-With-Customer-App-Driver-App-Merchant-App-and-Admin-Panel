<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- start paypal setting -->

            <section id="basic-vertical-layouts">
                <div class="row match-height justify-content-center">
                    <div class="col-md-8 col-12">
                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">Paypal Settings</h4>
                            </div>
                            <div class="card-content">
                                <div class="card-body">
                                <?= form_open_multipart('settings/editpaypal'); ?>
                                    <form class="form form-vertical">
                                        <div class="form-body">
                                            <div class="row">
                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="paypalkey">PayPal Key</label>
                                                        <input type="text" class="form-control" id="paypalkey" name="paypal_key" value="<?= $appsettings['paypal_key'] ?>" required>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="paypalcurrency">PayPal Currency</label>
                                                        <input type="text" class="form-control" id="paypalcurrency" name="app_currency_text" value="<?= $appsettings['app_currency_text'] ?>" required>
                                                        <a Paypal href="https://developer.paypal.com/docs/api/reference/currency-codes/">
                                                            <p>Paypal Currency</p>
                                                        </a>
                                                    </div>


                                                    <div class="form-group">
                                                        <label for="paypal_mode">PayPal Mode</label>
                                                        <select name="paypal_mode" id="paypal_mode" class="select2 form-group" style="width:100%">
                                                            <option value="1" <?php if ($appsettings['paypal_mode'] == '1') { ?>selected<?php } ?>>Development Mode</option>
                                                            <option value="2" <?php if ($appsettings['paypal_mode'] == '2') { ?>selected<?php } ?>>Published</option>
                                                        </select>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="paypal_active">PayPal Status</label>
                                                        <select name="paypal_active" id="paypal_active" class="select2 form-group" style="width:100%">
                                                            <option value="1" <?php if ($appsettings['paypal_active'] == '1') { ?>selected<?php } ?>>Active</option>
                                                            <option value="0" <?php if ($appsettings['paypal_active'] == '0') { ?>selected<?php } ?>>NonActive</option>
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

            <!-- end of paypal setting data -->
        </div>
    </div>
</div>
<!-- END: Content-->