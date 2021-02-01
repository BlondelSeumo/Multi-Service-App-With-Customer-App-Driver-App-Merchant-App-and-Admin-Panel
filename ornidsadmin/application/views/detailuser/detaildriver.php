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
                                <div class="text-center">
                                    <h4><?= $driver['driver_name'] ?>
                                    </h4>
                                    <div><?php if ($driver['gender'] == "Male") { ?>
                                            male
                                        <?php } else { ?>
                                            female
                                        <?php } ?>
                                        <p class=""><?= $driver['id'] ?></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card-content">
                            <div class="card-body text-center mx-auto">
                                <div class="avatar avatar-xl">
                                    <img class="img-fluid" src="<?= base_url('images/driverphoto/') . $driver['photo'] ?>" alt="img placeholder"></div>
                                <div class="col-sm-12 text-center mt-2">
                                    <p class=""><?= $driver['driver_address'] ?></p>
                                </div>

                                <div class="col-sm-12 text-center mt-1 mb-2">

                                    <?php if ($driver['status'] == 3) { ?>
                                        <h3 class="badge badge-dark">Banned</h3>
                                    <?php } elseif ($driver['status'] == 0) { ?>
                                        <h3 class="badge badge-secondary text-dark">New Reg</h3>
                                        <?php } else {
                                        if ($driver['status_job'] == 1) { ?>


                                            <h3 class="badge badge-info">Active</h3>
                                        <?php }
                                        if ($driver['status_job'] == 2) { ?>
                                            <h3 class="badge badge-primary">Pick'up</h3>
                                        <?php }
                                        if ($driver['status_job'] == 3) { ?>
                                            <h3 class="badge badge-success">work</h3>
                                        <?php }
                                        if ($driver['status_job'] == 4) { ?>
                                            <h3 class="badge badge-warning">Non Active</h3>
                                        <?php }
                                        if ($driver['status_job'] == 5) { ?>
                                            <h3 class="badge badge-danger">Log Out</h3>
                                    <?php }
                                    } ?>

                                    <span class="badge badge-outline-warning" data-toggle="modal" data-target="#maininfo">
                                        <a>
                                            <i class="feather icon-edit"></i>
                                            change info
                                        </a>
                                    </span>

                                </div>
                                <hr class="my-1">
                                <div class="d-flex justify-content-between mt-2">
                                    <div class="uploads">
                                        <p class="font-weight-bold font-medium-2 mb-0"><?= $countorder ?></p>
                                        <span class="">Order</span>
                                    </div>
                                    <div class="followers">
                                        <p class="font-weight-bold font-medium-2 mb-0">
                                            <?= $currency ?>
                                            <?= number_format($driver['balance'] / 100, 2, ".", ".") ?>
                                        </p>
                                        <span class="">Balance</span>
                                    </div>
                                    <div class="following">
                                        <p class="font-weight-bold font-medium-2 mb-0"><?= number_format($driver['rating'], 1) ?></p>
                                        <span class="">Rating</span>
                                    </div>
                                    <div class="following">
                                        <p class="font-weight-bold font-medium-2 mb-0"><?= $driver['driver_job'] ?></p>
                                        <span class="">Job Service</span>
                                    </div>
                                </div>

                                <?php if ($driver['status'] == 0) { ?>
                                    <a href="<?= base_url(); ?>detailuser/confirmdriver/<?= $driver['id'] ?>">
                                        <h3 class="btn btn-success col-12 mt-2">Confirm Driver</h3>
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
                                                        <img style="max-width : 100%; height: 200px; display: inline–block;" src="<?= base_url('images/photofile/ktp/') . $driver['idcard_images'] ?>" alt="">
                                                    </div>
                                                    <div class="swiper-slide">
                                                        <img style="max-width : 100%; height: 200px; display: inline–block;" src="<?= base_url('images/photofile/sim/') . $driver['driver_license_images'] ?>" alt="">
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
                                            <p class="font-weight-bold mb-1">Identity
                                                <a class="text-muted font-weight-light ml-1" data-toggle="modal" data-target="#identity">
                                                    <i class="feather icon-edit"></i>change</a>
                                            </p>
                                            <p>id card :
                                                <span class="text-muted"><?= $driver['user_nationid'] ?></span></p>
                                            <p>driver license :
                                                <span class="text-muted"><?= $driver['driver_license_id'] ?></span>
                                            </p>
                                            <p>date of birth :
                                                <span class="text-muted"><?= $driver['dob'] ?></span>
                                            </p>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="timeline-icon bg-warning">
                                            <i class="feather icon-mail font-medium-2"></i>
                                        </div>
                                        <div class="timeline-info">
                                            <p class="font-weight-bold mb-1">Contact
                                                <a class="text-muted font-weight-light ml-1" data-toggle="modal" data-target="#contact">
                                                    <i class="feather icon-edit"></i>change</a>
                                            </p>
                                            <p>phone :
                                                <span class="text-muted"><?= $driver['countrycode'] ?> <?= $driver['phone'] ?></span>
                                            </p>
                                            <p>email :
                                                <span class="text-muted"><?= $driver['email'] ?></span>
                                            </p>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="timeline-icon bg-info">
                                            <i class="feather icon-truck font-medium-2"></i>
                                        </div>
                                        <div class="timeline-info">
                                            <p class="font-weight-bold mb-1">Vehicle
                                                <a class="text-muted font-weight-light ml-1" data-toggle="modal" data-target="#vehicle">
                                                    <i class="feather icon-edit"></i>change</a>
                                            </p>
                                            <p>brand :
                                                <span class="text-muted"><?= $driver['color'] ?>
                                                    <?= $driver['brand'] ?>
                                                    <?= $driver['type'] ?></span>
                                            </p>
                                            <p>number :
                                                <span class="text-muted"><?= $driver['vehicle_registration_number'] ?></span>
                                            </p>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="timeline-icon bg-danger">
                                            <i class="feather icon-clock font-medium-2"></i>
                                        </div>
                                        <div class="timeline-info">
                                            <p class="font-weight-bold">Member</p>
                                            <p>update on :
                                                <span class="text-muted"><?= $driver['update_at'] ?></span>
                                            </p>
                                            <p>created on :
                                                <span class="text-muted"><?= $driver['created_at'] ?></span>
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
                                            <a class="nav-link active" id="drivertransaction-tab" data-toggle="tab" href="#drivertransaction" aria-controls="drivertransaction" role="tab" aria-selected="true">Transaction</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" id="driverwallet-tab" data-toggle="tab" href="#driverwallet" aria-controls="driverwallet" role="tab" aria-selected="false">Wallet</a>
                                        </li>
                                    </ul>
                                    <div class="tab-content">
                                        <div class="tab-pane active" id="drivertransaction" aria-labelledby="drivertransaction-tab" role="tabpanel">
                                            <!-- start driver transaction data table -->
                                            <section id="column-selectors">
                                                <div class="row">
                                                    <div class="col-12">
                                                        <div class="card">
                                                            <div class="card-header">
                                                                <h4 class="card-title">Driver Transaction</h4>
                                                            </div>
                                                            <div class="card-content">
                                                                <div class="card-body card-dashboard">
                                                                    <div class="table-responsive">
                                                                        <table class="table table-striped dataex-html5-selectors">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th>No.</th>
                                                                                    <th>Transaction Inv</th>
                                                                                    <th>Service</th>
                                                                                    <th>Date</th>
                                                                                    <th>Amount</th>
                                                                                    <th>Status</th>
                                                                                    <th>Actions</th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                                <?php $i = 1;
                                                                                foreach ($transaction as $tr) { ?>
                                                                                    <tr>
                                                                                        <td><?= $i ?></td>
                                                                                        <td>#INV-<?= $tr['id'] ?></td>
                                                                                        <td><?= $tr['service'] ?></td>
                                                                                        <td><?= $tr['order_time'] ?></td>
                                                                                        <td class="text-success">
                                                                                            <?= $currency ?>
                                                                                            <?= number_format($tr['final_cost'] / 100, 2, ".", ".") ?>
                                                                                        </td>
                                                                                        <td>
                                                                                            <?php if ($tr['status'] == '0') { ?>
                                                                                                <p class="ml-2 badge badge-danger">No Driver!</p>
                                                                                            <?php } ?>
                                                                                            <?php if ($tr['status'] == '1') { ?>
                                                                                                <p class="ml-2 badge badge-info"><?= $tr['transaction_status'] ?></p>
                                                                                            <?php } ?>
                                                                                            <?php if ($tr['status'] == '2') { ?>
                                                                                                <p class="ml-2 badge badge-primary"><?= $tr['transaction_status'] ?></p>
                                                                                            <?php } ?>
                                                                                            <?php if ($tr['status'] == '3') { ?>
                                                                                                <p class="ml-2 badge badge-primary"><?= $tr['transaction_status'] ?></p>
                                                                                            <?php } ?>
                                                                                            <?php if ($tr['status'] == '4') { ?>
                                                                                                <p class="ml-2 badge badge-success"><?= $tr['transaction_status'] ?></p>
                                                                                            <?php } ?>
                                                                                            <?php if ($tr['status'] == '5') { ?>
                                                                                                <p class="ml-2 badge badge-warning"><?= $tr['transaction_status'] ?></p>
                                                                                            <?php } ?>
                                                                                        </td>
                                                                                        <td>
                                                                                            <span class="mr-1">
                                                                                                <a href="<?= base_url(); ?>detailtransaction/index/<?= $tr['transaction_id']; ?>">
                                                                                                    <i class="feather icon-eye text-info"></i>
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
                                            <!-- end of driver transaction data table -->
                                        </div>
                                        <div class="tab-pane" id="driverwallet" aria-labelledby="driverwallet-tab" role="tabpanel">
                                            <!-- start driver wallet data table -->
                                            <section id="column-selectors">
                                                <div class="row">
                                                    <div class="col-12">
                                                        <div class="card">
                                                            <div class="card-header">
                                                                <h4 class="card-title">Driver Wallet</h4>
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

                                            <!-- end of driver wallet data table -->
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
<!-- edit driver info -->
<div class="modal fade text-left" id="maininfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel17" aria-hidden="true">
    <div class="modal-dialog modal-dialog-scrollable" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel17">Driver Info</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body pr-2 pl-2">

                <section id="basic-vertical-layouts">
                    <?= form_open_multipart('detailuser/editdriver'); ?>
                    <form class="form form-vertical">
                        <div class="form-body">
                            <div class="row">
                                <!-- start driver info form -->

                                <input type="hidden" name="id" value="<?= $driver['id'] ?>">

                                <div class="col-12">
                                    <div class="form-group">
                                        <label>Image</label>
                                        <input type="file" name="photo" class="dropify" data-max-file-size="1mb" data-default-file="<?= base_url(); ?>images/driverphoto/<?= $driver['photo'] ?>" />
                                    </div>
                                </div>

                                <div class="col-12">
                                    <div class="form-group">
                                        <label for="driver_name">Name</label>
                                        <input type="text" id="name" class="form-control" name="driver_name" placeholder="enter name" value="<?= $driver['driver_name'] ?>" required></div>
                                </div>

                                <div class="col-12">
                                    <div class="form-group">
                                        <label for="password">Password</label>
                                        <input type="password" id="password" class="form-control" name="password" placeholder="enter password"></div>
                                </div>

                                <div class="col-12">
                                    <div class="form-group">
                                        <label for="driver_address">Address</label>
                                        <fieldset class="form-group">
                                            <textarea class="form-control" id="basicTextarea" rows="3" name="driver_address" required><?= $driver['driver_address'] ?></textarea>
                                        </fieldset>
                                    </div>
                                </div>

                                <div class="col-12 form-group">
                                    <label for="gender">
                                        Gender
                                    </label>
                                    <select class="select2 form-control" id="data-gender" name="gender" required>
                                        <option value="Male" <?php if ($driver['gender'] == 'Male') { ?>selected<?php } ?>>Male</option>
                                        <option value="Female" <?php if ($driver['gender'] == 'Female') { ?>selected<?php } ?>>Female</option>
                                    </select>
                                </div>

                                <div class="col-12 form-group">
                                    <label for="jobservice">
                                        Job Service
                                    </label>
                                    <select class="select2 form-control" id="data-job" name="job" required>
                                        <?php foreach ($driverjob as $drj) { ?>
                                            <option value="<?= $drj['id'] ?>" <?php if ($driver['job'] == $drj['id']) { ?>selected<?php } ?>><?= $drj['driver_job'] ?></option>
                                        <?php } ?>
                                    </select>
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
<!-- edit driver info -->

<!-- edit driver identity -->
<div class="modal fade text-left" id="identity" tabindex="-1" role="dialog" aria-labelledby="myModalLabel17" aria-hidden="true">
    <div class="modal-dialog modal-dialog-scrollable" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel17">Driver Identity</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body pr-2 pl-2">

                <section id="basic-vertical-layouts">
                    <?= form_open_multipart('detailuser/editidentity'); ?>
                    <form class="form form-vertical">
                        <div class="form-body">
                            <div class="row">
                                <!-- start driver info form -->

                                <input type="hidden" name="id" value="<?= $driver['id'] ?>">

                                <div class="col-12">
                                    <div class="form-group">
                                        <label>ID Card Image</label>
                                        <input type="file" name="idcard_images" class="dropify" data-max-file-size="1mb" data-default-file="<?= base_url(); ?>images/photofile/ktp/<?= $driver['idcard_images'] ?>" />
                                    </div>
                                </div>

                                <div class="col-12">
                                    <div class="form-group">
                                        <label for="user_nationid">Id Card Number</label>
                                        <input type="text" id="user_nationid" class="form-control" name="user_nationid" placeholder="enter id number" value="<?= $driver['user_nationid'] ?>" required></div>
                                </div>

                                <div class="col-12">
                                    <div class="form-group">
                                        <label>Driver License Image</label>
                                        <input type="file" name="driver_license_images" class="dropify" data-max-file-size="1mb" data-default-file="<?= base_url(); ?>images/photofile/sim/<?= $driver['driver_license_images'] ?>" />
                                    </div>
                                </div>

                                <div class="col-12">
                                    <div class="form-group">
                                        <label for="driver_license_id">Driver License Number</label>
                                        <input type="text" id="driver_license_id" class="form-control" name="driver_license_id" placeholder="enter driver license number" value="<?= $driver['driver_license_id'] ?>" required></div>
                                </div>

                                <div class="col-12">
                                    <div class="form-group">
                                        <label for="dob">Date Of Birth</label>
                                        <input type="date" id="dob" class="form-control" name="dob" value="<?= $driver['dob'] ?>" required></div>
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
<!-- edit driver identity -->

<!-- edit driver contact -->
<div class="modal fade text-left" id="contact" tabindex="-1" role="dialog" aria-labelledby="myModalLabel17" aria-hidden="true">
    <div class="modal-dialog modal-dialog-scrollable" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel17">Driver Contact</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body pr-2 pl-2">

                <section id="basic-vertical-layouts">
                    <?= form_open_multipart('detailuser/editcontact'); ?>
                    <form class="form form-vertical">
                        <div class="form-body">
                            <div class="row">
                                <!-- start driver info form -->

                                <div class="col-12">
                                    <label>Phone</label>

                                    <div class="row">

                                        <input type="hidden" name="id" value="<?= $driver['id'] ?>">
                                        <input type="hidden" id="country" value="<?= $driver['countrycode'] ?>">

                                        <div class="form-group col-4">
                                            <input type="tel" id="txtPhone" class="form-control" name="countrycode" required>
                                        </div>
                                        <div class=" form-group col-8">
                                            <input type="text" class="form-control" id="phone" name="phone" placeholder="enter phone number" value="<?= $driver['phone'] ?>" required>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-12">
                                    <div class="form-group">
                                        <label for="email">Email</label>
                                        <input type="email" id="email" class="form-control" name="email" placeholder="enter email" value="<?= $driver['email'] ?>" required></div>
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
<!-- edit driver contact -->

<!-- edit driver Vehicle -->
<div class="modal fade text-left" id="vehicle" tabindex="-1" role="dialog" aria-labelledby="myModalLabel17" aria-hidden="true">
    <div class="modal-dialog modal-dialog-scrollable" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel17">Driver Vehicle</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body pr-2 pl-2">

                <section id="basic-vertical-layouts">
                    <?= form_open_multipart('detailuser/editvehicle'); ?>
                    <form class="form form-vertical">
                        <div class="form-body">
                            <div class="row">
                                <!-- start driver info form -->

                                <input type="hidden" name="id" value="<?= $driver['id'] ?>">
                                <input type="hidden" name="vehicle_id" value="<?= $driver['vehicle_id'] ?>">

                                <div class="col-12">
                                    <div class="form-group">
                                        <label for="brand">Vehicle Brand</label>
                                        <input type="text" id="brand" class="form-control" name="brand" placeholder="enter vehicle brand" value="<?= $driver['brand'] ?>" required></div>
                                </div>

                                <div class="col-12">
                                    <div class="form-group">
                                        <label for="type">Brand Variant</label>
                                        <input type="text" id="type" class="form-control" name="type" placeholder="enter vehicle variant" value="<?= $driver['type'] ?>" required></div>
                                </div>

                                <div class="col-12">
                                    <div class="form-group">
                                        <label for="color">Vehicle Color</label>
                                        <input type="text" id="color" class="form-control" name="color" placeholder="enter vehicle color" value="<?= $driver['color'] ?>" required></div>
                                </div>

                                <div class="col-12">
                                    <div class="form-group">
                                        <label for="vehicle_registration_number">Vehicle Registration Number</label>
                                        <input type="text" id="vehicle_registration_number" class="form-control" name="vehicle_registration_number" placeholder="enter vehicle registration number" value="<?= $driver['vehicle_registration_number'] ?>" required></div>
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
<!-- edit driver Vehicle -->