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
                                    <?= form_open_multipart('promotion/addpromocodedata'); ?>
                                    <form class="form form-vertical">
                                        <div class="form-body">
                                            <div class="row">

                                            <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="promo_image">Image</label>
                                                        <input type="file" name="promo_image" class="dropify" data-max-file-size="1mb" />
                                                    </div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="title">Promo title</label>
                                                        <input type="text" class="form-control" id="promo_title" name="promo_title" placeholder="promo title" required>
                                                    </div>
                                                </div>

                                                <div class="col-12 form-group">
                                                    <div class="form-group">
                                                        <label for="promocode">Promo Code</label>
                                                        <input type="text" class="form-control" id="promo_code" name="promo_code" placeholder="enter promo code" required>
                                                    </div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="gender">Promo Type</label>
                                                        <select class="select2 form-group" onchange="admSelectCheck(this);" name="promo_type" style="width:100%">
                                                            <option id="persen" value="persen">Percentage</option>
                                                            <option id="fix" value="fix">Fix</option>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div id="persencheck" class="col-12 form-group" style="display:block;">
                                                    <label>Percentage Promo Amount</label>
                                                    <input id="persencheckrequired" type="text" class="form-control" id="promo_amount" name="nominal_promo_persen" placeholder="enter promo amount" required>
                                                </div>
                                                <div id="fixcheck" class="col-12 form-group" style="display:none;">
                                                    <label>Fix Promo Amount</label>
                                                    <input id="fixcheckrequired" type="text" pattern="^\d+(\.|\,)\d{2}$" data-type="currency" class="form-control" id="promo_amount" name="promo_amount" placeholder="enter promo amount">
                                                </div>

                                                <div class="col-12 form-group">
                                                    <label for="birthdate">Exp On</label>
                                                    <input type="date" class="form-control" id="expired" name="expired" placeholder="" required>
                                                </div>
                                                <div class="col-12 form-group">
                                                    <label for="type">Service</label>
                                                    <select class="select2 form-group" name="service" style="width:100%">
                                                        <?php foreach ($service as $ft) { ?>
                                                            <option value="<?= $ft['service_id'] ?>"><?= $ft['service'] ?></option>
                                                        <?php } ?>
                                                    </select>
                                                </div>


                                                <div class="col-12 form-group">
                                                    <label for="gender">status</label>
                                                    <select class="select2 form-group" name="status" style="width:100%">
                                                        <option value="1">Active</option>
                                                        <option value="0">Nonactive</option>
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