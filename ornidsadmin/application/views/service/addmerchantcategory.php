<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- start add merchant category -->
            <div class="row match-height justify-content-center">
                <div class="col-md-8 col-12">
                    <div class="card">
                        <div class="card-header">
                            <h4 class="card-title">Add Merchant Category</h4>
                        </div>
                        <section id="basic-vertical-layouts">
                            <div class="card-content">
                                <div class="card-body">
                                    <?= form_open_multipart('service/addmerchantcategory'); ?>
                                    <form class="form form-vertical">
                                        <div class="form-body">
                                            <div class="row">
                                                <div class="col-12 form-group">
                                                    <label for="category_name">Category Name</label>
                                                    <input type="text" class="form-control" name="category_name" placeholder="enter category name" required>
                                                </div>
                                                <div class="col-12 form-group">
                                                    <label for="service_id">Status</label>
                                                    <select class="select2 form-group" style="width:100%" name="service_id">
                                                    <?php foreach ($service as $ft) { ?>
                            <option value="<?= $ft['service_id'] ?>"><?= $ft['service'] ?></option>
                        <?php } ?>
                                                    </select>
                                                </div>
                                                <div class="col-12 form-group">
                                                    <label for="category_status">Status</label>
                                                    <select class="select2 form-group" style="width:100%" name="category_status">
                                                        <option value="1">Active</option>
                                                        <option value="0">NonActive</option>
                                                    </select>
                                                </div>
                                                <!-- end of add merchant category form -->

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