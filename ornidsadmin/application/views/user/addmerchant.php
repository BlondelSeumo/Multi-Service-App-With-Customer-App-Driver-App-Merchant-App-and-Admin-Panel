<!-- BEGIN: Content-->

<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- start form add merchant -->

            <section id="basic-vertical-layouts">
                <div class="row match-height justify-content-center">
                    <div class="col-md-6 col-12">
                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">Add Merchant</h4>
                            </div>
                            <br>
                            <div class="card-content">
                                <div class="card-body">
                                    <?= form_open_multipart('user/addmerchantdata'); ?>
                                    <form class="form form-vertical">
                                        <div class="form-body">
                                            <div class="row">

                                                <h5 class="col-12 text-muted">Owner Info
                                                </h5>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="partner_name">Owner Name</label>
                                                        <input type="text" id="partner_name" class="form-control" name="partner_name" placeholder="enter owner name" required="required"></div>
                                                </div>

                                                <div class="col-12 form-group">
                                                    <label for="partner">
                                                        Official Partner
                                                    </label>
                                                    <select class="select2 form-control" id="partner" name="partner" required="required">
                                                        <option id="partner" value="1">Partner</option>
                                                        <option id="non" value="0">NonPartner</option>
                                                    </select>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="partner_address">Owner Address</label>
                                                        <fieldset class="form-group">
                                                            <textarea class="form-control" id="partner_address" rows="3" name="partner_address" required="required"></textarea>
                                                        </fieldset>
                                                    </div>
                                                </div>

                                                <div class="col-12">
                                                    <label>Phone</label>

                                                    <div class="row">

                                                        <input type="hidden" id="countryem" value="">

                                                        <div class="form-group col-4">
                                                            <input type="tel" id="txtPhone" class="form-control" name="partner_country_code">
                                                        </div>
                                                        <div class=" form-group col-8">
                                                            <input type="text" class="form-control" id="partner_phone" name="partner_phone" placeholder="enter phone number" required="required">
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="partner_email">Email</label>
                                                        <input type="email" id="partner_email" class="form-control" name="partner_email" placeholder="enter email" required="required"></div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="partner_type_identity">Type of Id Card</label>
                                                        <input type="text" id="partner_type_identity" class="form-control" name="partner_type_identity" placeholder="enter type of id card" required="required"></div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="partner_identity_number">Number of Id Card</label>
                                                        <input type="text" id="partner_identity_number" class="form-control" name="partner_identity_number" placeholder="enter number of id card" required="required"></div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label>ID Card Image</label>
                                                        <input type="file" name="idcard_images" class="dropify" data-max-file-size="1mb" />
                                                    </div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="password">Password</label>
                                                        <input type="password" id="password" class="form-control" name="password" placeholder="enter password" required="required"></div>
                                                </div>

                                                <h5 class="col-12 text-muted mt-2">Merchant Info
                                                </h5>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label>Image</label>
                                                        <input type="file" name="merchant_image" class="dropify" data-max-file-size="1mb" />
                                                    </div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="merchant_name">Merchant Name</label>
                                                        <input type="text" id="merchant_name" class="form-control" name="merchant_name" placeholder="enter merchant name" required="required"></div>
                                                </div>

                                                <div class="col-12 form-group">
                                                    <label for="service_id">
                                                        Merchant Type
                                                    </label>
                                                    <select class="select2 form-control" id="service_id" name="service_id" required="required">
                                                        <?php foreach ($service as $ftr) { ?>
                                                            <option id="<?= $ftr['service'] ?>" value="<?= $ftr['service_id'] ?>"><?= $ftr['service'] ?></option>
                                                        <?php } ?>
                                                    </select>
                                                </div>

                                                <div class="col-12 form-group">
                                                    <label for="merchant_category">
                                                        Merchant Category
                                                    </label>
                                                    <select class="select2 form-control" id="merchant_category" name="merchant_category" required="required">
                                                        <?php foreach ($merchantcategory as $mck) { ?>
                                                            <option value="<?= $mck['category_merchant_id'] ?>"><?= $mck['category_name'] ?></option>
                                                        <?php
                                                        } ?>
                                                    </select>
                                                </div>


                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label>Address</label>
                                                        <input type="text" class="form-control" name="merchant_address" id="address" autocomplete="on" />
                                                    </div>
                                                </div>


                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <input type="hidden" id="merchant_latitude" value="-6.222320699570134" />
                                                        <input type="hidden" id="merchant_longitude" value="106.83289668750001" />
                                                        <div id="mappicker" style="width: 100%; height: 300px;"></div>
                                                    </div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="row">
                                                        <div class="col-6">
                                                            <div class="form-group">
                                                                <label for="merchant_latitude">Latitude</label>
                                                                <input type="text" id="latitude" class="form-control" name="merchant_latitude" placeholder="enter latitude" required="required"></div>
                                                        </div>

                                                        <div class="col-6">
                                                            <div class="form-group">
                                                                <label for="merchant_longitude">Longitude</label>
                                                                <input type="text" id="longitude" class="form-control" name="merchant_longitude" placeholder="enter logitude" required="required"></div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="row">
                                                        <div class="col-6">
                                                            <div class="form-group">
                                                                <label for="open_hour">Open Hour</label>
                                                                <input type="text" id="open_hour" class="form-control" name="open_hour" placeholder="enter open hour" required="required"></div>
                                                        </div>

                                                        <div class="col-6">
                                                            <div class="form-group">
                                                                <label for="close_hour">Close Hour</label>
                                                                <input type="text" id="close_hour" class="form-control" name="close_hour" placeholder="enter close hour" required="required"></div>
                                                        </div>
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

            <!-- end of form add merchant -->
        </div>
    </div>
</div>
<!-- END: Content-->