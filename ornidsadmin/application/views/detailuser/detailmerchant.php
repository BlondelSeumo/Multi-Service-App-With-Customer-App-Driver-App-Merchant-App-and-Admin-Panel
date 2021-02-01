
<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- detail driver Start -->

            <div class="row">

                <div class="col-lg-4 col-sm-12">

                    <div class="card">
                        <div class="card-header mx-auto pb-0">
                            <div class="row m-0">
                                <div>
                                    <div class="text-center card-title mb-1">
                                        <span><?= $partner['merchant_name'] ?>
                                        </span> <?php if ($partner['partner'] == 1) { ?>
                                            <span class="badge badge-primary">Official</span>
                                        <?php } else { ?>
                                        <?php } ?>
                                    </div>
                                    <div class="text-center"><?= $partner['partner_name'] ?>
                                        <p class="">M78343843</p>

                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card-content">
                            <div class="card-body text-center mx-auto">
                                <div class="avatar avatar-xl">
                                    <img class="img-fluid" src="<?= base_url('images/merchant/') . $partner['merchant_image'] ?>" alt="img placeholder"></div>
                                <div class="col-sm-12 text-center mt-2">
                                    <p class=""><?php if ($partner['partner_status'] == 3) { ?>
                                            <h3 class="badge badge-dark">Banned</h3>
                                        <?php } elseif ($partner['partner_status'] == 0) { ?>
                                            <h3 class="badge badge-secondary text-dark">New Reg</h3>
                                        <?php } else { ?>
                                            <?php if ($partner['merchant_status'] == 1) { ?>
                                                <h3 class="badge badge-success">Active</h3>
                                            <?php } ?>
                                            <?php if ($partner['merchant_status'] == 0) { ?>
                                                <h3 class="badge badge-danger">NonActive</h3>
                                            <?php } ?>
                                        <?php } ?></p>

                                </div>

                                <div class="col-sm-12 text-center mt-1 mb-2">

                                    <span class="badge badge-warning" data-toggle="modal" data-target="#ownerinfo">
                                        <a>
                                            <i class="feather icon-edit"></i>
                                            edit owner
                                        </a>
                                    </span>

                                    <span class="badge badge-outline-warning" data-toggle="modal" data-target="#merchantinfo">
                                        <a>
                                            <i class="feather icon-edit"></i>
                                            edit merchant
                                        </a>
                                    </span>

                                </div>
                                <hr class="my-1">
                                <div class="d-flex justify-content-between mt-2">
                                    <div class="uploads">
                                        <p class="font-weight-bold font-medium-2 mb-0"><?= $partner['service'] ?> </p>
                                        <span class="">Merchant Type</span>
                                    </div>
                                    <div class="followers">
                                        <p class="font-weight-bold font-medium-2 mb-0">
                                            <?= $partner['category_name'] ?>
                                        </p>
                                        <span class="">Category</span>
                                    </div>
                                    <div class="following">
                                        <p class="font-weight-bold font-medium-2 mb-0"><?= $countorder ?></p>
                                        <span class="">Order</span>
                                    </div>
                                    <div class="following">
                                        <p class="font-weight-bold font-medium-2 mb-0"><?= $currency ?>
                                            <?= number_format($partner['balance'] / 100, 2, ".", ".") ?></p>
                                        <span class="">Balance</span>
                                    </div>
                                </div>

                                <?php if ($partner['partner_status'] == 0) { ?>
                                    <a href="<?= base_url(); ?>detailuser/confirmmerchant/<?= $partner['partner_id'] ?>">
                                        <h3 class="btn btn-success col-12 mt-2">Confirm Merchant</h3>
                                    </a>
                                <?php } ?>

                            </div>
                        </div>
                    </div>

                    <div class="card">
                        <div class="card-header">

                            <!-- pagination swiper start -->
                            <div class="card">
                                <section id="component-swiper-pagination">
                                    <div class="card-header">
                                        <h4 class="card-title">Files</h4>
                                    </div>
                                    <div class="card-content">
                                        <div class="card-body">
                                            <div class="swiper-paginations swiper-container">
                                                <div class="swiper-wrapper">
                                                    <div class="swiper-slide">
                                                        <img style="max-width : 100%; height: 200px; display: inline–block;" src="<?= base_url(); ?>images/photofile/ktp/<?= $partner['idcard_images'] ?>" alt="">
                                                    </div>
                                                </div>
                                                <!-- Add Pagination -->
                                                <div class="swiper-pagination"></div>
                                            </div>
                                        </div>
                                    </div>
                                </section>
                            </div>
                            <!-- pagination swiper ends -->

                        </div>

                    </div>

                    <div class="card">
                        <div class="card-header"></div>
                        <div class="card-content">
                            <div class="card-body">
                                <ul class="activity-timeline timeline-left list-unstyled">
                                    <li>
                                        <div class="timeline-icon bg-primary">
                                            <i class="feather icon-credit-card font-medium-2"></i>
                                        </div>
                                        <div class="timeline-info">
                                            <p class="font-weight-bold">Identity
                                            </p>
                                            <p>Type of id card :
                                                <span class="text-muted"><?= $partner['partner_type_identity'] ?></span>
                                            </p>
                                            <p>Id card number :
                                                <span class="text-muted"><?= $partner['partner_identity_number'] ?></span></p>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="timeline-icon bg-warning">
                                            <i class="feather icon-mail font-medium-2"></i>
                                        </div>
                                        <div class="timeline-info">
                                            <p class="font-weight-bold">Contact
                                            </p>
                                            <p>phone :
                                                <span class="text-muted"><?= $partner['partner_country_code'] ?>
                                                    <?= $partner['partner_phone'] ?></span></p>
                                            <p>email :
                                                <span class="text-muted"><?= $partner['partner_email'] ?></span>
                                            </p>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="timeline-icon bg-info">
                                            <i class="feather icon-map-pin font-medium-2"></i>
                                        </div>
                                        <div class="timeline-info">
                                            <p class="font-weight-bold">Address
                                            </p>
                                            <p>Owner Address :
                                                <span class="text-muted"><?= $partner['partner_address'] ?></span>
                                            </p>
                                            <p>Merchant Location :
                                                <span class="text-muted"><?= $partner['merchant_address'] ?></span>
                                            </p>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="timeline-icon bg-warning">
                                            <i class="feather icon-clock font-medium-2"></i>
                                        </div>
                                        <div class="timeline-info">
                                            <p class="font-weight-bold">Oprational Hour
                                            </p>
                                            <p>Open :
                                                <span class="text-muted"><?= $partner['open_hour'] ?></span>
                                            </p>
                                            <p>Close :
                                                <span class="text-muted"><?= $partner['close_hour'] ?></span>
                                            </p>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="timeline-icon bg-danger">
                                            <i class="feather icon-clock font-medium-2"></i>
                                        </div>
                                        <div class="timeline-info">
                                            <p class="font-weight-bold">Member</p>
                                            <p>Created on :
                                                <span class="text-muted"><?= $partner['partner_created'] ?></span>
                                            </p>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>

                </div>

                <!-- driver tabs start -->
                <div class="col-lg-8 col-sm-12">
                    <section id="basic-tabs-components">
                        <div class="card overflow-hidden">
                            <div class="card-content">
                                <div class="card-body">
                                    <ul class="nav nav-tabs" role="tablist">
                                        <li class="nav-item">
                                            <a class="nav-link active" id="merchanttransaction-tab" data-toggle="tab" href="#merchanttransaction" aria-controls="merchanttransaction" role="tab" aria-selected="true">Transaction</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" id="merchantwallet-tab" data-toggle="tab" href="#merchantwallet" aria-controls="merchantwallet" role="tab" aria-selected="false">Wallet</a>
                                        </li>
                                    </ul>
                                    <div class="tab-content">
                                        <div class="tab-pane active" id="merchanttransaction" aria-labelledby="merchanttransaction-tab" role="tabpanel">
                                            <!-- start merchant transaction data table -->
                                            <section id="column-selectors">
                                                <div class="row">
                                                    <div class="col-12">
                                                        <div class="card">
                                                            <div class="card-header">
                                                                <h4 class="card-title">Merchant Transaction</h4>
                                                            </div>
                                                            <div class="card-content">
                                                                <div class="card-body card-dashboard">
                                                                    <div class="table-responsive">
                                                                        <table class="table table-striped dataex-html5-selectors">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th>No.</th>
                                                                                    <th>Transaction Inv</th>
                                                                                    <th>Date</th>
                                                                                    <th>Customer_name</th>
                                                                                    <th>Item Amount</th>
                                                                                    <th>Total Amount</th>
                                                                                    <th>Actions</th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                                <?php $i = 1;
                                                                                foreach ($transaction as $tr) { ?>
                                                                                    <tr>
                                                                                        <td><?= $i ?></td>
                                                                                        <td>#INV-<?= $tr['transaction_id'] ?></td>
                                                                                        <td><?= $tr['order_time'] ?></td>
                                                                                        <td><?= $tr['customer_fullname'] ?></td>
                                                                                        <td><?= $tr['item_amount'] ?></td>
                                                                                        <td>
                                                                                            <?= $currency ?>
                                                                                            <?= number_format($tr['total_price'] / 100, 2, ".", ".") ?>
                                                                                        </td>
                                                                                        <td>
                                                                                            <a href="<?= base_url(); ?>detailtransaction/index/<?= $tr['transaction_id']; ?>" class="btn btn-outline-primary">View</a>
                                                                                        </td>
                                                                                    <?php $i++;
                                                                                } ?>
                                                                            </tbody>
                                                                        </table>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </section>
                                            <!-- end of merchant transaction data table -->
                                        </div>
                                        <div class="tab-pane" id="merchantwallet" aria-labelledby="merchantwallet-tab" role="tabpanel">
                                            <!-- start merchant wallet data table -->
                                            <section id="column-selectors">
                                                <div class="row">
                                                    <div class="col-12">
                                                        <div class="card">
                                                            <div class="card-header">
                                                                <h4 class="card-title">Merchant Wallet</h4>
                                                            </div>
                                                            <div class="card-content">
                                                                <div class="card-body card-dashboard">
                                                                    <div class="table-responsive">
                                                                        <table class="table table-striped dataex-html5-selectors">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th>No.</th>
                                                                                    <th>Id</th>
                                                                                    <th>Type</th>
                                                                                    <th>Date</th>
                                                                                    <th>Amount</th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                                <?php $i = 1;
                                                                                foreach ($wallet as $wl) { ?>
                                                                                    <tr>
                                                                                        <td><?= $i ?></td>
                                                                                        <td><?= $wl['id']; ?></td>
                                                                                        <td><?= $wl['type']; ?></td>
                                                                                        <td><?= $wl['date']; ?></td>

                                                                                        <?php if ($wl['type'] == 'topup' or $wl['type'] == 'Order+') { ?>
                                                                                            <td class="text-success">
                                                                                                <?= $currency ?>
                                                                                                <?= number_format($wl['wallet_amount'] / 100, 2, ".", ".") ?>
                                                                                            </td>

                                                                                        <?php } else { ?>
                                                                                            <td class="text-danger">
                                                                                                <?= $currency ?>
                                                                                                <?= number_format($wl['wallet_amount'] / 100, 2, ".", ".") ?>
                                                                                            </td>
                                                                                        <?php } ?>



                                                                                    </tr>
                                                                                <?php $i++;
                                                                                } ?>
                                                                            </tbody>
                                                                        </table>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </section>

                                            <!-- end of merchant wallet data table -->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>

                    <section id="basic-tabs-components">
                        <div class="card overflow-hidden">
                            <div class="card-content">
                                <div class="card-body">
                                    <ul class="nav nav-tabs" role="tablist">
                                        <li class="nav-item">
                                            <a class="nav-link active" id="itemcategory-tab" data-toggle="tab" href="#itemcategory" aria-controls="itemcategory" role="tab" aria-selected="true">Item Category</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" id="item-tab" data-toggle="tab" href="#item" aria-controls="item" role="tab" aria-selected="false">Item</a>
                                        </li>
                                    </ul>
                                    <div class="tab-content">
                                        <div class="tab-pane active" id="itemcategory" aria-labelledby="itemcategory-tab" role="tabpanel">
                                            <!-- start item category data table -->
                                            <section id="column-selectors">
                                                <div class="row">
                                                    <div class="col-12">
                                                        <div class="card">
                                                            <div class="card-header">
                                                                <h4 class="card-title">Item Category</h4>
                                                            </div>
                                                            <div class="card-content">
                                                                <div class="card-body card-dashboard">
                                                                    <a class="btn btn-success mb-1 text-white" data-toggle="modal" data-target="#addcategory">
                                                                        <i class="feather icon-plus mr-1"></i>Add Category Item</a>
                                                                    <div class="table-responsive">
                                                                        <table class="table table-striped dataex-html5-selectors">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th style="width: 80px;">No.</th>
                                                                                    <th>Category Name</th>
                                                                                    <th style="width: 80px;">Action</th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                                <?php $i = 1;
                                                                                foreach ($itemcategory as $itkate) { ?>
                                                                                    <h4 id="idkat<?= $i ?>" style=display:none;><?= $itkate['category_item_id'] ?></h4>
                                                                                    <tr>
                                                                                        <td><?= $i ?></td>
                                                                                        <td id="namkat<?= $i ?>"><?= $itkate['category_name_item'] ?></td>
                                                                                        <td>
                                                                                            <span class="mr-1">
                                                                                                <a href="#" data-toggle="modal" data-target="#editcategory" onclick="edititemcategoryFunction('<?= $itkate['category_name_item'] . ',' . $itkate['category_item_id'] ?>')">
                                                                                                    <i class="feather icon-edit text-info"></i>
                                                                                                </a>
                                                                                            </span>
                                                                                            <span class="action-delete mr-1">
                                                                                                <a href="#">
                                                                                                    <i class="feather icon-trash text-danger"></i>
                                                                                                </a>
                                                                                            </span>
                                                                                        </td>
                                                                                    <?php $i++;
                                                                                } ?>
                                                                            </tbody>
                                                                        </table>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </section>
                                            <!-- end of item category data table -->
                                        </div>
                                        <div class="tab-pane" id="item" aria-labelledby="item-tab" role="tabpanel">
                                            <!-- start item data table -->
                                            <section id="column-selectors">
                                                <div class="row">
                                                    <div class="col-12">
                                                        <div class="card">
                                                            <div class="card-header">
                                                                <h4 class="card-title">Item</h4>
                                                            </div>
                                                            <div class="card-content">
                                                                <div class="card-body card-dashboard">
                                                                    <a class="btn btn-success mb-1 text-white" href="<?= base_url(); ?>detailuser/additemview/<?= $partner['partner_id'] ?>">
                                                                        <i class="feather icon-plus mr-1"></i>Add Item</a>
                                                                    <div class="table-responsive">
                                                                        <table class="table table-striped dataex-html5-selectors">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th style="width: 30px;">No.</th>
                                                                                    <th style="width: 50px;">Item Image</th>
                                                                                    <th>Item Name</th>
                                                                                    <th>Category Item</th>
                                                                                    <th>Price</th>
                                                                                    <th>Promo Price</th>
                                                                                    <th>Status</th>
                                                                                    <th>Actions</th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                                <?php $i = 1;
                                                                                foreach ($item as $it) { ?>
                                                                                    <tr>
                                                                                        <td><?= $i ?></td>
                                                                                        <td><img class="round" style="width: 40px; height: 40px;" src="<?= base_url('images/itemphoto/') . $it['item_image']; ?>"></td>
                                                                                        <td id="namaitem<?= $i ?>"><?= $it['item_name'] ?></td>
                                                                                        <td><?= $it['category_name_item'] ?></td>
                                                                                        <?php if ($it['promo_status'] == 0) { ?>
                                                                                            <td><?= $currency ?><?= number_format($it['item_price'] / 100, 2, ".", ".") ?></td>
                                                                                        <?php } else { ?>
                                                                                            <td style="text-decoration: line-through;"><?= $currency ?><?= number_format($it['item_price'] / 100, 2, ".", ".") ?></td>
                                                                                        <?php } ?>
                                                                                        <?php if ($it['promo_status'] == 1) { ?>
                                                                                            <td class="text-success"><?= $currency ?><?= number_format($it['promo_price'] / 100, 2, ".", ".") ?></td>
                                                                                        <?php } else { ?>
                                                                                            <td><label class="badge badge-danger">Not Promo</label></td>
                                                                                        <?php } ?>
                                                                                        <?php if ($it['item_status'] == 1) { ?>
                                                                                            <td><label class="badge badge-primary">Active</label></td>
                                                                                        <?php } else { ?>
                                                                                            <td><label class="badge badge-danger">Non Active</label></td>
                                                                                        <?php } ?>
                                                                                        <td>

                                                                                            <span class=" mr-1">
                                                                                                <a href="<?= base_url(); ?>detailuser/edititemview/<?= $it['item_id'] ?>">
                                                                                                    <i class="feather icon-edit text-info"></i>
                                                                                                </a>
                                                                                            </span>
                                                                                            <span class="action-delete mr-1">
                                                                                                <a href="#">
                                                                                                    <i class="feather icon-trash text-danger"></i>
                                                                                                </a>
                                                                                            </span>
                                                                                        </td>

                                                                                    <?php $i++;
                                                                                } ?>
                                                                            </tbody>
                                                                        </table>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </section>

                                            <!-- end of item data table -->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>

                </div>
                <!-- end of driver tab -->



            </div>

            <!-- end of detail driver -->
        </div>
    </div>
</div>
<!-- END: Content-->

<!-- Modal -->
<!-- edit owner info -->
<div class="modal fade text-left" id="ownerinfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel17" aria-hidden="true">
    <div class="modal-dialog modal-dialog-scrollable" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel17">Edit Owner</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body pr-2 pl-2">

                <section id="basic-vertical-layouts">
                    <?= form_open_multipart('detailuser/editownerinfo'); ?>
                    <form class="form form-vertical">
                        <div class="form-body">
                            <div class="row">
                                <!-- start owner info form -->

                                <input type="hidden" name="partner_id" value="<?= $partner['partner_id'] ?>">

                                <div class="col-12">
                                    <div class="form-group">
                                        <label for="partner_name">Owner Name</label>
                                        <input type="text" id="partner_name" class="form-control" name="partner_name" placeholder="enter owner name" value="<?= $partner['partner_name'] ?>" required="required"></div>
                                </div>

                                <div class="col-12 form-group">
                                    <label for="partner">
                                        Official Partner
                                    </label>
                                    <select class="select2 form-control" id="partner" name="partner" required="required">
                                        <option id="partner" value="1" <?php if ($partner['partner'] == 1) { ?>selected<?php } ?>>Partner</option>
                                        <option id="non" value="0" <?php if ($partner['partner'] == 0) { ?>selected<?php } ?>>NonPartner</option>
                                    </select>
                                </div>

                                <div class="col-12">
                                    <div class="form-group">
                                        <label for="partner_address">Owner Address</label>
                                        <fieldset class="form-group">
                                            <textarea class="form-control" id="partner_address" rows="3" name="partner_address" required="required"><?= $partner['partner_name'] ?></textarea>
                                        </fieldset>
                                    </div>
                                </div>

                                <div class="col-12">
                                    <label>Phone</label>

                                    <div class="row">

                                        <input type="hidden" name="partner_id" value="<?= $partner['partner_id'] ?>">
                                        <input type="hidden" id="countryem" value="<?= $partner['partner_country_code'] ?>">

                                        <div class="form-group col-4">
                                            <input type="tel" id="txtPhone" class="form-control" name="partner_country_code">
                                        </div>
                                        <div class=" form-group col-8">
                                            <input type="text" class="form-control" id="partner_phone" name="partner_phone" placeholder="enter phone number" value="<?= $partner['partner_phone'] ?>" required="required">
                                        </div>
                                    </div>
                                </div>

                                <div class="col-12">
                                    <div class="form-group">
                                        <label for="partner_email">Email</label>
                                        <input type="email" id="partner_email" class="form-control" name="partner_email" placeholder="enter email" value="<?= $partner['partner_email'] ?>" required="required"></div>
                                </div>

                                <div class="col-12">
                                    <div class="form-group">
                                        <label for="partner_type_identity">Type of Id Card</label>
                                        <input type="text" id="partner_type_identity" class="form-control" name="partner_type_identity" placeholder="enter type of id card" value="<?= $partner['partner_type_identity'] ?>" required="required"></div>
                                </div>

                                <div class="col-12">
                                    <div class="form-group">
                                        <label for="partner_identity_number">Number of Id Card</label>
                                        <input type="text" id="partner_identity_number" class="form-control" name="partner_identity_number" placeholder="enter number of id card" value="<?= $partner['partner_identity_number'] ?>" required="required"></div>
                                </div>

                                <div class="col-12">
                                    <div class="form-group">
                                        <label>ID Card Image</label>
                                        <input type="file" name="idcard_images" class="dropify" data-max-file-size="1mb" data-default-file="<?= base_url(); ?>images/photofile/ktp/<?= $partner['idcard_images'] ?>" />
                                    </div>
                                </div>

                                <div class="col-12">
                                    <div class="form-group">
                                        <label for="password">Password</label>
                                        <input type="password" id="password" class="form-control" name="password" placeholder="enter password"></div>
                                </div>
                                <!-- end of owner info form -->

                                <div class="col-12">
                                    <button type="submit" class="btn btn-primary mr-1 mb-1">Save</button>
                                </div>
                            </div>
                        </div>
                    </form>
                    <?= form_close(); ?>
                </section>
            </div>
        </div>
    </div>
</div>
<!-- edit owner info -->

<!-- edit merchant info -->
<div class="modal fade text-left" id="merchantinfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel17" aria-hidden="true">
    <div class="modal-dialog modal-dialog-scrollable" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel17">Edit Merchant</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body pr-2 pl-2">

                <section id="basic-vertical-layouts">
                    <?= form_open_multipart('detailuser/editmerchant'); ?>
                    <form class="form form-vertical">
                        <div class="form-body">
                            <div class="row">
                                <!-- start driver info form -->

                                <input type="hidden" name="merchant_id" value="<?= $partner['merchant_id'] ?>">
                                <input type="hidden" name="partner_id" value="<?= $partner['partner_id'] ?>">

                                <div class="col-12">
                                    <div class="form-group">
                                        <label>Image</label>
                                        <input type="file" name="merchant_image" class="dropify" data-max-file-size="1mb" data-default-file="<?= base_url(); ?>images/merchant/<?= $partner['merchant_image'] ?>" />
                                    </div>
                                </div>

                                <div class="col-12">
                                    <div class="form-group">
                                        <label for="merchant_name">Merchant Name</label>
                                        <input type="text" id="merchant_name" class="form-control" name="merchant_name" placeholder="enter merchant name" value="<?= $partner['merchant_name'] ?>" required="required"></div>
                                </div>

                                <div class="col-12 form-group">
                                    <label for="service_id">
                                        Merchant Type
                                    </label>
                                    <select class="select2 form-control" id="service_id" name="service_id" required="required">
                                        <?php foreach ($service as $ftr) { ?>
                                            <option id="<?= $ftr['service'] ?>" value="<?= $ftr['service_id'] ?>" <?php if ($partner['service_id'] == $ftr['service_id']) { ?>selected<?php } ?>><?= $ftr['service'] ?></option>
                                        <?php } ?>
                                    </select>
                                </div>

                                <div class="col-12 form-group">
                                    <label for="merchant_category">
                                        Merchant Category
                                    </label>
                                    <select class="select2 form-control" id="merchant_category" name="merchant_category" required="required">
                                        <?php foreach ($merchantcategory as $mck) { ?>
                                            <option value="<?= $mck['category_merchant_id'] ?>" <?php if ($mck['category_merchant_id'] == $partner['merchant_category']) { ?>selected<?php } ?>><?= $mck['category_name'] ?></option>
                                        <?php
                                        } ?>
                                    </select>
                                </div>

                                <div class="col-12">
                                    <div class="form-group">
                                        <label>Address</label>
                                        <input type="text" class="form-control" name="merchant_address" id="address" autocomplete="off" />
                                    </div>
                                </div>


                                <div class="col-12">
                                    <div class="form-group">
                                        <input type="hidden" id="merchant_latitude" value="<?= $partner['merchant_latitude'] ?>" />
                                        <input type="hidden" id="merchant_longitude" value="<?= $partner['merchant_longitude'] ?>" />
                                        <div id="mappicker" style="height: 300px;"></div>
                                    </div>
                                </div>

                                <div class="col-12">
                                    <div class="row">
                                        <div class="col-6">
                                            <div class="form-group">
                                                <label for="merchant_latitude">Latitude</label>
                                                <input type="text" id="latitude" class="form-control" name="merchant_latitude" placeholder="enter latitude" value="<?= $partner['merchant_latitude'] ?>" required="required"></div>
                                        </div>

                                        <div class="col-6">
                                            <div class="form-group">
                                                <label for="merchant_longitude">Longitude</label>
                                                <input type="text" id="longitude" class="form-control" name="merchant_longitude" placeholder="enter logitude" value="<?= $partner['merchant_longitude'] ?>" required="required"></div>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-12">
                                    <div class="row">
                                        <div class="col-6">
                                            <div class="form-group">
                                                <label for="open_hour">Open Hour</label>
                                                <input type="text" id="open_hour" class="form-control" name="open_hour" placeholder="enter open hour" value="<?= $partner['open_hour'] ?>" required="required"></div>
                                        </div>

                                        <div class="col-6">
                                            <div class="form-group">
                                                <label for="close_hour">Close Hour</label>
                                                <input type="text" id="close_hour" class="form-control" name="close_hour" placeholder="enter close hour" value="<?= $partner['close_hour'] ?>" required="required"></div>
                                        </div>
                                    </div>
                                </div>

                                <!-- end of driver info form -->

                                <div class="col-12">
                                    <button type="submit" class="btn btn-primary mr-1 mb-1">Save</button>
                                </div>
                            </div>
                        </div>
                    </form>
                    <?= form_close(); ?>
                </section>
            </div>
        </div>
    </div>
</div>
<!-- edit merchant info -->

<!-- add category item -->
<div class="modal fade text-left" id="addcategory" tabindex="-1" role="dialog" aria-labelledby="myModalLabel17" aria-hidden="true">
    <div class="modal-dialog modal-dialog-scrollable" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel17">Add Category Item</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body pr-2 pl-2">

                <section id="basic-vertical-layouts">
                    <?= form_open_multipart('detailuser/addcategoryitem'); ?>
                    <form class="form form-vertical">
                        <div class="form-body">
                            <div class="row">

                                <input type="hidden" name="merchant_id" value="<?= $partner['merchant_id'] ?>">
                                <input type="hidden" name="partner_id" value="<?= $partner['partner_id'] ?>">

                                <div class="col-12">
                                    <div class="form-group">
                                        <label for="category_name_item">Category item name</label>
                                        <input type="text" class="form-control" name="category_name_item" placeholder="enter category item name" required="required"></div>
                                </div>


                                <div class="col-12">
                                    <button type="submit" class="btn btn-primary mr-1 mb-1">Save</button>
                                </div>
                            </div>
                        </div>
                    </form>
                    <?= form_close(); ?>
                </section>
            </div>
        </div>
    </div>
</div>
<!-- add category item -->

<!-- edit category item -->
<div class="modal fade text-left" id="editcategory" tabindex="-1" role="dialog" aria-labelledby="myModalLabel17" aria-hidden="true">
    <div class="modal-dialog modal-dialog-scrollable" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel17">Edit Category Item</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body pr-2 pl-2">

                <section id="basic-vertical-layouts">
                    <?= form_open_multipart('detailuser/editcategoryitem'); ?>
                    <form class="form form-vertical">
                        <div class="form-body">
                            <div class="row">

                                <input type="hidden" name="partner_id" value="<?= $partner['partner_id'] ?>">
                                <input type="hidden" name="category_item_id" id="iditems">

                                <div class="col-12">
                                    <div class="form-group">
                                        <label for="category_name_item">Category item name</label>
                                        <input type="text" id="itemcategoryname" class="form-control" name="category_name_item" placeholder="enter category item name" required="required"></div>
                                </div>


                                <div class="col-12">
                                    <button type="submit" class="btn btn-primary mr-1 mb-1">Save</button>

                                </div>
                            </div>
                        </div>
                    </form>
                    <?= form_close(); ?>
                </section>
            </div>
        </div>
    </div>
</div>
<!-- edit category item -->
