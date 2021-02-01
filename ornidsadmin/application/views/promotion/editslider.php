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
                                <h4 class="card-title">Edit Promo Slider</h4>
                            </div>
                            <br>
                            <div class="card-content">
                                <div class="card-body">
                                    <?= form_open_multipart('promotion/editpromoslider/'. $id); ?>
                                    <form class="form form-vertical">
                                        <div class="form-body">
                                            <div class="row">

                                            <input type="hidden" name="id" value="<?= $id ?>">

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="photo">Slider Image</label>
                                                        <input 
                                                        type="file" 
                                                        name="photo" 
                                                        class="dropify" 
                                                        data-max-file-size="1mb" 
                                                        data-default-file="<?= base_url('images/promo/') . $photo ?>"/>
                                                    </div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="exp_date">Exp on</label>
                                                        <input
                                                            type="date"
                                                            id="exp_date"
                                                            class="form-control"
                                                            name="exp_date"
                                                            placeholder="enter exp date"
                                                            value="<?= $exp_date ?>"
                                                            required="required"></div>
                                                </div>

                                                <div class="col-12 form-group">
                                                    <label for="item_category">
                                                        Promo Slider Type
                                                    </label>
                                                    <select
                                                        class="select2 form-control"
                                                        id="promotion_type"
                                                        onchange="editSelectCheck(this);"
                                                        name="promotion_type"
                                                        required="required">
                                                        <option id="service" value="service" <?php if ($promotion_type == 'service') { ?>selected<?php } ?>>Service</option>
                                                        <option id="link" value="link" <?php if ($promotion_type == 'link') { ?>selected<?php } ?>>Link</option>
                                                    </select>
                                                </div>

                                                <?php if ($promotion_type == 'service') {  ?>
                                                <div class="col-12 form-group" id="servicecheck" style="display:block;">
                                                    <label for="promotion_service">
                                                        For Service
                                                    </label>
                                                    <select
                                                        class="select2 form-control"
                                                        id="promotion_service"
                                                        name="promotion_service"
                                                        required="required">
                                                        <?php foreach ($service as $ftr) { ?>
                                        <option value="<?= $ftr['service_id'] ?>" <?php if ($promotion_service == $ftr['service_id']) { ?>selected<?php } ?>><?= $ftr['service'] ?></option>
                                    <?php } ?>
                                                    </select>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group" id="linkcheck" style="display:none;">
                                                        <label for="promotion_link">Slider Link</label>
                                                        <input
                                                            type="text"
                                                            id="linktes"
                                                            class="form-control"
                                                            name="promotion_link"
                                                            placeholder="enter slider link"></div>
                                                        </div>

                                            <?php } else { ?>

                                                <div class="col-12 form-group" id="servicecheck" style="display:none;">
                                                    <label for="promotion_service">
                                                        For Service
                                                    </label>
                                                    <select
                                                        class="select2 form-control"
                                                        id="promotion_service"
                                                        name="promotion_service"
                                                        required="required">
                                                        <?php foreach ($service as $ftr) { ?>
                                        <option value="<?= $ftr['service_id'] ?>" <?php if ($promotion_service == $ftr['service_id']) { ?>selected<?php } ?>><?= $ftr['service'] ?></option>
                                    <?php } ?>
                                                    </select>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group" id="linkcheck" style="display:block;">
                                                        <label for="promotion_link">Slider Link</label>
                                                        <input
                                                            type="text"
                                                            id="linktes"
                                                            class="form-control"
                                                            name="promotion_link"
                                                            value="<?= $promotion_link ?>"
                                                            placeholder="enter slider link"></div>
                                                        </div>

                                                            <?php } ?>

                                                <div class="col-12 form-group">
                                                    <label for="is_show">
                                                        Status
                                                    </label>
                                                    <select
                                                        class="select2 form-control"
                                                        id="is_show"
                                                        name="is_show"
                                                        required="required">
                                                        <option value="1" <?php if($is_show == 1){ ?>selected<?php } ?>>Active</option>
                                                        <option value="0" <?php if($is_show == 0){ ?>selected<?php } ?>>Nonactive</option>
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