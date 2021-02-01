<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- start form edit item -->

            <section id="basic-vertical-layouts">
                <div class="row match-height justify-content-center">
                    <div class="col-md-6 col-12">
                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">Edit Item</h4>
                            </div>
                            <br>
                            <div class="card-content">
                                <div class="card-body">
                                    <?= form_open_multipart('detailuser/edititem/'. $item['item_id'] ); ?>
                                    <form class="form form-vertical">
                                        <div class="form-body">
                                            <div class="row">

                                                <input type="hidden" name="merchant_id" value="<?= $item['merchant_id'] ?>">

                                                <div class="col-12">
                                    <div class="form-group">
                                        <label for="item_image">Item Image</label>
                                        <input type="file" name="item_image" class="dropify" data-max-file-size="1mb" data-default-file="<?= base_url(); ?>images/itemphoto/<?= $item['item_image'] ?>" />
                                    </div>

                                </div>

                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="item_name">Item Name</label>
                                                        <input
                                                            type="text"
                                                            id="item_name"
                                                            class="form-control"
                                                            name="item_name"
                                                            placeholder="enter item name"
                                                            value="<?= $item['item_name'] ?>"
                                                            required></div>
                                                </div>

                                                <div class="col-12 form-group">
                                                    <label for="item_category">
                                                        Category
                                                    </label>
                                                    <select
                                                        class="select2 form-control"
                                                        id="item_category"
                                                        name="item_category"
                                                        required>
                                                        <?php foreach ($itemcategory as $itk) { ?>
                                <option value="<?= $itk['category_item_id'] ?>" <?php if ($itk['category_item_id'] == $item['item_category']) { ?>selected<?php } ?>><?= $itk['category_name_item'] ?></option>
                            <?php } ?>
                                                    </select>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="item_desc">Item Description</label>
                                                        <fieldset class="form-group">
                                                            <textarea
                                                                class="form-control"
                                                                id="item_desc"
                                                                rows="3"
                                                                name="item_desc"
                                                                required><?= $item['item_desc'] ?></textarea>
                                                        </fieldset>
                                                    </div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="item_price">Item Price</label>
                                                        <input
                                                            type="text"
                                                            id="item_price"
                                                            class="form-control"
                                                            pattern="^\d+(\.|\,)\d{2}$"
                                                            data-type="currency"
                                                            name="item_price"
                                                            value="<?= number_format($item['item_price'] / 100, 2, ".", ".") ?>"
                                                            placeholder="enter item price"
                                                            required></div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="row">
                                                        <div class="col-4 form-group">
                                                            <label for="promo_status">
                                                                Is Promo
                                                            </label>
                                                            <select
                                                                class="select2 form-control"
                                                                id="promo_status"
                                                                name="promo_status"
                                                                onchange="editSelectCheck(this);">
                                                                <option id="yes" value="1" <?php if ($item['promo_status'] == '1') { ?>selected<?php } ?>>Yes</option>
                                                                <option id="no" value="0" <?php if ($item['promo_status'] == '0') { ?>selected<?php } ?>>No</option>
                                                            </select>
                                                        </div>

                                                        <div class="col-8">
                                                    <div class="form-group" id="yescheck" <?php if ($item['promo_status'] == 1) { ?> style="display:block;" <?php } else { ?> style="display:none;" <?php } ?>>
                                                        <label for="promo_price">Promo Price</label>
                                                        <input
                                                            type="text"
                                                            class="form-control"
                                                            pattern="^\d+(\.|\,)\d{2}$"
                                                            data-type="currency"
                                                            name="promo_price"
                                                            value="<?= number_format($item['promo_price'] / 100, 2, ".", ".") ?>"
                                                            placeholder="enter promo price"></div>
                                                </div>
                                                    </div>
                                                </div>

                                                <div class="col-12 form-group">
                                                    <label for="item_status">
                                                        Status
                                                    </label>
                                                    <select
                                                        class="select2 form-control"
                                                        id="item_status"
                                                        name="item_status"
                                                        required>
                                                        <option value="1" <?php if ($item['item_status'] == 1) { ?>selected<?php } ?>>Active</option>
                            <option value="0" <?php if ($item['item_status'] == 0) { ?>selected<?php } ?>>NonActive</option>
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

            <!-- end of form edit item -->
        </div>
    </div>
</div>
<!-- END: Content-->