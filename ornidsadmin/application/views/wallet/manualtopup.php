<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- start form add slider -->

            <section id="basic-vertical-layouts">
                <div class="row match-height justify-content-center">
                    <div class="col-md-6 col-12">
                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">Manual Topup</h4>
                            </div>
                            <br>
                            <div class="card-content">
                                <div class="card-body">
                                    <?= form_open_multipart('wallet/addtopupdata'); ?>
                                    <form class="form form-vertical">
                                        <div class="form-body">
                                            <div class="row">

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="type">User Type</label>
                                                        <select id="getFname" onchange="admSelectCheck(this);" class="select2 form-group" name="type_user">
                                                            <option id="customer" value="customer">USER</option>
                                                            <option id="driver" value="driver">DRIVER</option>
                                                            <option id="partner" value="partner">MERCHANT</option>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="col-12">
                                                    <div id="pelanggancheck" style="display:block;" class="form-group">
                                                        <label for="id_Pelanggan">Users</label>
                                                        <select class="select2 form-group" name="customer_id">
                                                            <?php foreach ($balance as $sl) {
                                                                if (substr($sl['id_user'], 0, 1) == 'P') { ?>
                                                                    <option value="<?= $sl['id_user'] ?>"><?= $sl['customer_fullname'] ?> (<?= $currency['duit'] ?><?= number_format($sl['balance'] / 100, 2, ".", ".") ?>)</option>
                                                            <?php }
                                                            } ?>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="col-12">
                                                    <div id="drivercheck" style="display:none;" class="form-group">
                                                        <label for="driver_id">Drivers</label>
                                                        <select class="select2 form-group" name="driver_id">
                                                            <?php foreach ($balance as $sl) {
                                                                if (substr($sl['id_user'], 0, 1) == 'D') { ?>
                                                                    <option value="<?= $sl['id_user'] ?>"><?= $sl['driver_name'] ?> (<?= $currency['duit'] ?><?= number_format($sl['balance'] / 100, 2, ".", ".") ?>)
                                                                    </option>
                                                            <?php }
                                                            } ?>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-12">
                                                    <div id="mitracheck" style="display:none;" class="form-group">
                                                        <label for="partner_id">Owners</label>
                                                        <select class="select2 form-group" name="partner_id">
                                                            <?php foreach ($balance as $sl) {
                                                                if (substr($sl['id_user'], 0, 1) == 'M') { ?>
                                                                    <option value="<?= $sl['id_user'] ?>"><?= $sl['partner_name'] ?> (<?= $currency['duit'] ?><?= number_format($sl['balance'] / 100, 2, ".", ".") ?>)
                                                                    </option>
                                                            <?php }
                                                            } ?>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="balance">Amount</label>
                                                        <input type="text" pattern="^\d+(\.|\,)\d{2}$" data-type="currency" class="form-control" id="balance" name="balance" placeholder="enter Amount" value="">
                                                    </div>
                                                </div>
                                                <div class="col-12">
                                                    <button type="submit" class="btn btn-primary mr-1 mb-1">Save</button>
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

            <!-- end of form add slider -->
        </div>
    </div>
</div>
<!-- END: Content-->