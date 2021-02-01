<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- start edit merchant category -->
            <div class="row match-height justify-content-center">
                <div class="col-md-8 col-12">
                    <div class="card">
                        <div class="card-header">
                            <h4 class="card-title">Edit Merchant Category</h4>
                        </div>
                        <section id="basic-vertical-layouts">
                            <div class="card-content">
                                <div class="card-body">
                                    <?= form_open_multipart('service/editmerchantcategory'); ?>
                                    <form class="form form-vertical">
                                        <div class="form-body">
                                            <div class="row">
                                                <input type="hidden" value="<?= $merchantcategory['category_merchant_id']?>" name="category_merchant_id"/>

                                                <div class="col-12 form-group">
                                                    <label for="category_name">Category Name</label>
                                                    <input type="text" class="form-control" name="category_name" placeholder="enter category name" value="<?= $merchantcategory['category_name']?>" required>
                                                </div>
                                                <div class="col-12 form-group">
                                                    <label for="service_id">Status</label>
                                                    <select class="select2 form-group" style="width:100%" name="service_id">
                                                    <?php foreach ($service as $ft) { ?>
                            <option value="<?= $ft['service_id'] ?>" <?php if ($merchantcategory['service_id'] == $ft['service_id']) { ?> selected <?php } ?>><?= $ft['service'] ?></option>
                        <?php } ?>
                                                    </select>
                                                </div>
                                                <div class="col-12 form-group">
                                                    <label for="category_status">Status</label>
                                                    <select class="select2 form-group" style="width:100%" name="category_status">
                                                    <option value="1" <?php if ($merchantcategory['category_status'] == 1) { ?>selected<?php } ?>>Active</option>
                            <option value="0" <?php if ($merchantcategory['category_status'] == 0) { ?>selected<?php } ?>>NonActive</option>
                                                    </select>
                                                </div>
                                                <!-- end of edit merchant category form -->

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

            <!-- end of add merchant category -->
        </div>
    </div>
</div>
<!-- END: Content-->