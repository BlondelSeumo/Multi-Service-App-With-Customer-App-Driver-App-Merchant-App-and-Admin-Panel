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
                                <h4 class="card-title">Edit Service</h4>
                            </div>
                            <br>
                            <div class="card-content">
                                <div class="card-body">
                                    <?= form_open_multipart('service/editservicedata/' . $service_id); ?>
                                    <form class="form form-vertical">
                                        <div class="form-body">
                                            <div class="row">

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="icon">Icon</label>
                                                        <input 
                                                        type="file" 
                                                        name="icon" 
                                                        class="dropify" 
                                                        data-max-file-size="1mb" 
                                                        data-default-file="<?= base_url('images/service/') . $icon ?>"/>
                                                    </div>
                                                </div>
                                                
                                                <div class="col-12 form-group">
                                                    <label for="newstitle">Name</label>
                                                    <input type="text" class="form-control" id="newstitle" name="service" value="<?= $service ?>" required>
                                                </div>
                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="type">Type</label>
                                                        <select class="select2 form-group" name="home">
                                                            <?php foreach ($typeservice as $drj) { ?>
                                                                <option value="<?= $drj['ext_id'] ?>" <?php if ($home == $drj['ext_id']) { ?>selected<?php } ?>><?= $drj['title'] ?></option>
                                                            <?php } ?>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="col-12 form-group">
                                                    <label for="newstitle">Price</label>
                                                    <input type="text" pattern="^\d+(\.|\,)\d{2}$" data-type="currency" class="form-control" id="newstitle" name="cost" value="<?= number_format($cost / 100, 2, ".", ".") ?>" required>
                                                </div>
                                                <div class="col-12 form-group">
                                                    <label for="newstitle">Discount (%)</label>
                                                    <input type="text" class="form-control" id="newstitle" name="voucher_discount" value="<?= $voucher_discount ?>" placeholder="ex 10%" required>
                                                </div>
                                                <div class="col-12 form-group">
                                                    <label for="newscategory">Unit</label>
                                                    <select class="select2 form-group" name="cost_desc" style="width:100%">
                                                        <option value="KM" <?php if ($cost_desc == 'KM') { ?>selected<?php } ?>>Kilometers</option>
                                                        <option value="Hr" <?php if ($cost_desc == 'Hr') { ?>selected<?php } ?>>An Hour</option>
                                                    </select>
                                                </div>
                                                <div class="col-12 form-group">
                                                    <label for="newstitle">Commission (%)</label>
                                                    <input type="text" class="form-control" id="newstitle" name="commision" placeholder="ex 10%" value="<?= $commision ?>" required>
                                                </div>
                                                <div class="col-12 form-group">
                                                    <label for="newscategory">vechile</label>
                                                    <select class="select2 form-group" name="driver_job" style="width:100%">
                                                        <?php foreach ($driverjob as $drj) { ?>
                                                            <option value="<?= $drj['id'] ?>" <?php if ($driver_job == $drj['id']) { ?>selected<?php } ?>><?= $drj['driver_job'] ?></option>
                                                        <?php } ?>
                                                    </select>
                                                </div>
                                                <div class="col-12 form-group">
                                                    <label for="newstitle">Minimum Price</label>
                                                    <input type="text" pattern="^\d+(\.|\,)\d{2}$" data-type="currency" class="form-control" id="newstitle" name="minimum_cost" value="<?= number_format($minimum_cost / 100, 2, ".", ".") ?>" required>
                                                </div>
                                                <div class="col-12 form-group">
                                                    <label for="newstitle">Driver Radius</label>
                                                    <input type="text" class="form-control" id="newstitle" name="minimum_distance" value="<?= $minimum_distance ?>" required>
                                                </div>
                                                <div class="col-12 form-group">
                                                    <label for="newstitle">Max Distance Order</label>
                                                    <input type="text" class="form-control" id="newstitle" name="maks_distance" value="<?= $maks_distance ?>" required>
                                                </div>
                                                <div class="col-12 form-group">
                                                    <label for="newstitle">Minimum Saldo</label>
                                                    <input type="text" pattern="^\d+(\.|\,)\d{2}$" data-type="currency" class="form-control" id="newstitle" name="minimum_wallet" value="<?= number_format($minimum_wallet / 100, 2, ".", ".") ?>" required>
                                                </div>
                                                <div class="col-12 form-group">
                                                    <label for="newstitle">Description</label>
                                                    <input type="text" class="form-control" id="newstitle" name="description" value="<?= $description ?>" required>
                                                </div>

                                                <div class="col-12 form-group">
                                                    <label for="newscategory">Status</label>
                                                    <select class="select2 form-group" name="active" style="width:100%">
                                                        <option value="0" <?php if ($active == 0) { ?>selected<?php } ?>>Nonactive</option>
                                                        <option value="1" <?php if ($active == 1) { ?>selected<?php } ?>>Active</option>
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