<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- detail customer Start -->

            <div class="row">

                <div class="col-lg-4 col-sm-12">

                    <div class="card">
                        <div class="card-header mx-auto pb-0">
                            <div class="row m-0">
                                <div class="text-center">
                                    <h4><?= $customer['customer_fullname'] ?>
                                    </h4>
                                    <div>
                                        <p class=""><?= $customer['id'] ?></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card-content">
                            <div class="card-body text-center mx-auto">
                                <div class="avatar avatar-xl">
                                    <img class="img-fluid" src="<?= base_url('images/customer/') . $customer['customer_image']; ?>" alt="img placeholder"></div>
                                <div class="col-sm-12 text-center mt-2">
                                    <p class=""></p>
                                </div>

                                <div class="col-sm-12 text-center mt-1 mb-2">
                                    <?php if ($customer['status'] == 1) { ?>
                                        <h3 class="badge badge-info">Active</h3>
                                    <?php } else { ?>
                                        <h3 class="badge badge-dark">Banned</h3>
                                    <?php  } ?>
                                    <span class="badge badge-outline-warning" data-toggle="modal" data-target="#customerinfo">
                                        <a>
                                            <i class="feather icon-edit"></i>
                                            change info
                                        </a>
                                    </span>

                                </div>
                                <hr class="my-1">
                                <div class="row">
                                    <div class="uploads col-6">
                                        <p class="font-weight-bold font-medium-2 mb-0"><?= count($countorder) ?></p>
                                        <span class="">Order</span>
                                    </div>
                                    <div class="followers col-6">
                                        <p class="font-weight-bold font-medium-2 mb-0">
                                            <?= $currency ?>
                                            <?= number_format($customer['balance'] / 100, 2, ".", ".") ?>
                                        </p>
                                        <span class="">Balance</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="card">
                        <div class="card-header"></div>
                        <div class="card-content">
                            <div class="card-body">
                                <ul class="activity-timeline timeline-left list-unstyled">
                                    <li>
                                        <div class="timeline-icon bg-warning">
                                            <i class="feather icon-mail font-medium-2"></i>
                                        </div>
                                        <div class="timeline-info">
                                            <p class="font-weight-bold">Contact</p>
                                            <p>phone :
                                                <span class="text-muted"><?= $customer['countrycode'] ?>
                                                    <?= $customer['phone'] ?></span>
                                            </p>
                                            <p>email :
                                                <span class="text-muted"><?= $customer['email'] ?></span>
                                            </p>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="timeline-icon bg-info">
                                            <i class="feather icon-credit-card font-medium-2"></i>
                                        </div>
                                        <div class="timeline-info">
                                            <p class="font-weight-bold">Identity</p>
                                            <p>date of birth :
                                                <span class="text-muted"><?= $customer['dob'] ?></span>
                                            </p>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="timeline-icon bg-danger">
                                            <i class="feather icon-clock font-medium-2"></i>
                                        </div>
                                        <div class="timeline-info">
                                            <p class="font-weight-bold">Member</p>
                                            <p>created on :
                                                <span class="text-muted"><?= $customer['created_on'] ?></span>
                                            </p>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>

                </div>

                <!-- customer tabs start -->
                <div class="col-lg-8 col-sm-12">
                    <section id="basic-tabs-components">
                        <div class="card overflow-hidden">
                            <div class="card-content">
                                <div class="card-body">
                                    <ul class="nav nav-tabs" role="tablist">
                                        <li class="nav-item">
                                            <a class="nav-link active" id="customertransaction-tab" data-toggle="tab" href="#customertransaction" aria-controls="customertransaction" role="tab" aria-selected="true">Transaction</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" id="customerwallet-tab" data-toggle="tab" href="#customerwallet" aria-controls="customerwallet" role="tab" aria-selected="false">Wallet</a>
                                        </li>
                                    </ul>
                                    <div class="tab-content">
                                        <div class="tab-pane active" id="customertransaction" aria-labelledby="customertransaction-tab" role="tabpanel">
                                            <!-- start customer transaction data table -->
                                            <section id="column-selectors">
                                                <div class="row">
                                                    <div class="col-12">
                                                        <div class="card">
                                                            <div class="card-header">
                                                                <h4 class="card-title">Customer Transaction</h4>
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
                                                                                foreach ($countorder as $tr) { ?>
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
                                            <!-- end of customer transaction data table -->
                                        </div>
                                        <div class="tab-pane" id="customerwallet" aria-labelledby="customerwallet-tab" role="tabpanel">
                                            <!-- start customer wallet data table -->
                                            <section id="column-selectors">
                                                <div class="row">
                                                    <div class="col-12">
                                                        <div class="card">
                                                            <div class="card-header">
                                                                <h4 class="card-title">customer Wallet</h4>
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

                                            <!-- end of customer wallet data table -->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                </div>
                <!-- end of customer tab -->

            </div>

            <!-- end of detail customer -->
        </div>
    </div>
</div>
<!-- END: Content-->

<!-- Modal -->
<!-- edit customer info -->
<div class="modal fade text-left" id="customerinfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel17" aria-hidden="true">
    <div class="modal-dialog modal-dialog-scrollable" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel17">Customer Info</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body pr-2 pl-2">

                <section id="basic-vertical-layouts">
                    <?= form_open_multipart('detailuser/editdatacustomer'); ?>
                    <form class="form form-vertical">
                        <div class="form-body">
                            <div class="row">
                                <!-- start customer info form -->

                                <input type="hidden" name="id" value="<?= $customer['id'] ?>">

                                <div class="col-12">
                                    <div class="form-group">
                                        <label>Image</label>
                                        <input type="file" name="customer_image" class="dropify" data-max-file-size="1mb" data-default-file="<?= base_url('images/customer/') . $customer['customer_image'] ?>" />
                                    </div>

                                </div>

                                <div class="col-12">
                                    <div class="form-group">
                                        <label for="customer_fullname">Name</label>
                                        <input type="text" id="customer_fullname" class="form-control" name="customer_fullname" placeholder="enter name" value="<?= $customer['customer_fullname'] ?>" required="required"></div>
                                </div>

                                <div class="col-12">
                                    <div class="form-group">
                                        <label for="dob">Date of birth</label>
                                        <input type="date" id="dob" class="form-control" name="dob" placeholder="enter name" value="<?= $customer['dob'] ?>" required="required"></div>
                                </div>

                                <div class="col-12">
                                    <label>Phone</label>

                                    <div class="row">

                                        <input type="hidden" name="id" value="<?= $customer['id'] ?>">
                                        <input type="hidden" id="countryec" value="<?= $customer['countrycode'] ?>">

                                        <div class="form-group col-4">
                                            <input type="tel" id="txtPhone" class="form-control" name="countrycode" required="required">
                                        </div>
                                        <div class=" form-group col-8">
                                            <input type="text" class="form-control" id="phone" name="phone" placeholder="enter phone number" value="<?= $customer['phone'] ?>" required="required">
                                        </div>
                                    </div>
                                </div>

                                <div class="col-12">
                                    <div class="form-group">
                                        <label for="email">Email</label>
                                        <input type="email" id="email" class="form-control" name="email" placeholder="enter email" value="<?= $customer['email'] ?>" required="required"></div>
                                </div>

                                <div class="col-12">
                                    <div class="form-group">
                                        <label for="password">Password</label>
                                        <input type="password" id="password" class="form-control" name="password" placeholder="enter password" ></div>
                                </div>

                                <!-- end of customer info form -->

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
<!-- edit customer info -->