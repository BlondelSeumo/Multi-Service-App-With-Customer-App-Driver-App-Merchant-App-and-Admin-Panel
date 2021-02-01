<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- start form add item -->

            <section id="basic-vertical-layouts">
                <div class="row match-height justify-content-center">
                    <div class="col-md-6 col-12">
                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">Add Item</h4>
                            </div>
                            <br>
                            <div class="card-content">
                                <div class="card-body">
                                    <?= form_open_multipart('detailuser/additem'); ?>
                                    <form class="form form-vertical">
                                        <div class="form-body">
                                            <div class="row">

                                                <input type="hidden" class="form-control" name="merchant_id" value="<?= $partner['merchant_id'] ?>">
                                                <input type="hidden" class="form-control" name="partner_id" value="<?= $partner['partner_id'] ?>">

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="item_image">Image</label>
                                                        <input type="file" name="item_image" class="dropify" data-max-file-size="1mb" />
                                                    </div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="item_name">Item name</label>
                                                        <input type="text" id="item_name" class="form-control" name="item_name" placeholder="enter item name" required="required"></div>
                                                </div>

                                                <div class="col-12 form-group">
                                                    <label for="item_category">
                                                        Category
                                                    </label>
                                                    <select class="select2 form-control" id="item_category" name="item_category" required="required">
                                                        <?php foreach ($itemcategory as $itk) { ?>
                                                            <option value="<?= $itk['category_item_id'] ?>"><?= $itk['category_name_item'] ?></option>
                                                        <?php } ?>
                                                    </select>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="item_desc">Item Description</label>
                                                        <fieldset class="form-group">
                                                            <textarea class="form-control" id="item_desc" rows="3" name="item_desc" placeholder="enter description" required="required"></textarea>
                                                        </fieldset>
                                                    </div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="item_price">Item Price</label>
                                                        <input type="text" id="item_price" class="form-control" name="item_price" placeholder="enter item price <?= $currency ?>" value="" data-type="currency" pattern="^\d+(\.|\,)\d{2}$" required="required"></div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="row">
                                                        <div class="col-4 form-group">
                                                            <label for="promo_status">
                                                                Is Promo
                                                            </label>
                                                            <select class="select2 form-control" id="promo_status" name="promo_status" onchange="addSelectCheck(this);">
                                                                <option id="yes" value="1">Yes</option>
                                                                <option id="no" value="0">No</option>
                                                            </select>
                                                        </div>

                                                        <div class="col-8">
                                                            <div id="yescheck" style="display:block;" class="form-group">
                                                                <label for="yes">Promo Price</label>
                                                                <input type="text" id="yes" class="form-control" name="promo_price" pattern="^\d+(\.|\,)\d{2}$" data-type="currency" placeholder="enter promo price(<?= $currency ?>)"></div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="col-12 form-group">
                                                    <label for="item_status">
                                                        Status
                                                    </label>
                                                    <select class="select2 form-control" id="item_status" name="item_status" required="required">
                                                        <option value="1">Active</option>
                                                        <option value="0">NonActive</option>
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

            <!-- end of form add item -->
        </div>
    </div>
</div>
<!-- END: Content-->