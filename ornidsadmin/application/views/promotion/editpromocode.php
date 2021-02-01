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
                                <h4 class="card-title">Add Promo Code</h4>
                            </div>
                            <br>
                            <div class="card-content">
                                <div class="card-body">
                                    <?= form_open_multipart('promotion/editpromocodedata/' . $promo['promo_id']); ?>
                                    <form class="form form-vertical">
                                        <div class="form-body">
                                            <div class="row">

                                            <input type="hidden" name="promo_id" value="<?= $promo['promo_id'] ?>">

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="promo_image">Promo Code Image</label>
                                                        <input 
                                                        type="file" 
                                                        name="promo_image" 
                                                        class="dropify" 
                                                        data-max-file-size="1mb" 
                                                        data-default-file="<?= base_url('images/promo/' . $promo['promo_image']); ?>"/>
                                                    </div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="title">Promo title</label>
                                                        <input type="text" class="form-control" id="promo_title" name="promo_title" placeholder="promo title" value="<?= $promo['promo_title'] ?>" required>
                                                    </div>
                                                </div>

                                                <div class=" col-12 form-group">
                                                    <div class="form-group">
                                                        <label for="promocode">Promo Code</label>
                                                        <input type="text" class="form-control" id="promo_code" name="promo_code" placeholder="enter promo code" value="<?= $promo['promo_code'] ?>" required>
                                                    </div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="gender">Promo Type</label>
                                                        <select class="select2 form-group" onchange="admSelectCheck(this);" name="promo_type" style="width:100%">
                                                            <option id="persen" value="persen" <?php if ($promo['promo_type']  == 'persen') { ?>selected<?php } ?>>Percentage</option>
                                                            <option id="fix" value="fix" <?php if ($promo['promo_type'] == 'fix') { ?>selected<?php } ?>>Fix</option>
                                                        </select>
                                                    </div>
                                                </div>

                                                <?php if ($promo['promo_type']  == 'persen') { ?>
                                                    <div id="persencheck" class="col-12 form-group" style="display:block;">
                                                        <label>Percentage Promo Amount</label>
                                                        <input id="persencheckrequired" type="text" class="form-control" id="promo_amount" name="nominal_promo_persen" value="<?= $promo['promo_amount']; ?>" placeholder="enter promo amount" required>
                                                    </div>
                                                    <div id="fixcheck" class="col-12 form-group" style="display:none;">
                                                        <label>Fix Promo Amount</label>
                                                        <input id="fixcheckrequired" type="text" pattern="^\d+(\.|\,)\d{2}$" data-type="currency" class="form-control" id="promo_amount" name="promo_amount" value="<?= number_format($promo['promo_amount'] / 100, 2, ".", "."); ?>" placeholder="enter promo amount">
                                                    </div>
                                                <?php } else { ?>
                                                    <div id="persencheck" class="col-12 form-group" style="display:none;">
                                                        <label>Percentage Promo Amount</label>
                                                        <input id="persencheckrequired" type="text" class="form-control" id="promo_amount" name="nominal_promo_persen" placeholder="enter promo amount" value="<?= $promo['promo_amount']; ?>" required>
                                                    </div>
                                                    <div id="fixcheck" class="col-12 form-group" style="display:block;">
                                                        <label>Fix Promo Amount</label>
                                                        <input id="fixcheckrequired" type="text" pattern="^\d+(\.|\,)\d{2}$" data-type="currency" class="form-control" id="promo_amount" name="promo_amount" value="<?= number_format($promo['promo_amount'] / 100, 2, ".", "."); ?>" placeholder="enter promo amount">
                                                    </div>
                                                <?php } ?>



                                                <div class="col-12 form-group">
                                                    <label for="birthdate">Exp On</label>
                                                    <input type="date" class="form-control" id="expired" name="expired" placeholder="" value="<?= $promo['expired'] ?>" required>
                                                </div>
                                                <div class="col-12 form-group">
                                                    <label for="type">Service</label>
                                                    <select class="select2 form-group" name="service" style="width:100%">
                                                        <?php foreach ($service as $ft) { ?>
                                                            <option value="<?= $ft['service_id'] ?>" <?php if ($promo['service'] == $ft['service_id']) { ?>selected<?php } ?>><?= $ft['service'] ?></option>
                                                        <?php } ?>
                                                    </select>
                                                </div>


                                                <div class="col-12 form-group">
                                                    <label for="gender">status</label>
                                                    <select class="select2 form-group" name="status" style="width:100%">

                                                        <option value="1" <?php if ($promo['status']  == '1') { ?>selected<?php } ?>>Active</option>
                                                        <option value="0" <?php if ($promo['status']  == '0') { ?>selected<?php } ?>>Nonactive</option>
                                                    </select>
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