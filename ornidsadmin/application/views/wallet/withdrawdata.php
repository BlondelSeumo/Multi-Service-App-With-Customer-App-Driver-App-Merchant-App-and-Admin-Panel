<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- withdraw data start -->
            <section id="data-thumb-view" class="data-thumb-view-header">
                <section id="basic-tabs-components">
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="card overflow-hidden">
                                <div class="card-header">
                                    <h4 class="card-title">Withdraw Data</h4>
                                </div>
                                <div class="card-header">
                                    <a class="btn btn-success mb-1 text-white" href="<?= base_url(); ?>wallet/addwithdraw">
                                        <i class="feather icon-plus mr-1"></i>Manual Withdraw</a>
                                </div>

                                <div class="card-content">
                                    <div class="card-body">
                                        <ul class="nav nav-tabs" role="tablist">
                                            <li class="nav-item">
                                                <a class="nav-link active" id="withdrawrequest-tab" data-toggle="tab" href="#withdrawrequest" aria-controls="withdrawrequest" role="tab" aria-selected="true">Withdraw Request</a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="nav-link" id="allwithdraw-tab" data-toggle="tab" href="#allwithdraw" aria-controls="allwithdraw" role="tab" aria-selected="false">All Withdraw</a>
                                            </li>
                                        </ul>
                                        <div class="tab-content">
                                            <div class="tab-pane active" id="withdrawrequest" aria-labelledby="withdrawrequest-tab" role="tabpanel">
                                                <!-- withdraw request table start -->
                                                <section id="basic-datatable">
                                                    <div class="row">
                                                        <div class="col-12">
                                                            <div class="card">
                                                                <div class="card-header">
                                                                    <h4 class="card-title">Withdraw Request</h4>
                                                                </div>
                                                                <div class="card-content">
                                                                    <div class="card-body card-dashboard">
                                                                        <div class="table-responsive">
                                                                            <table class="table table-striped dataex-html5-selectors">
                                                                                <thead>
                                                                                    <tr>
                                                                                        <th>No.</th>
                                                                                        <th>Invoice</th>
                                                                                        <th>Date</th>
                                                                                        <th>User</th>
                                                                                        <th>Name</th>
                                                                                        <th>amount</th>
                                                                                        <th>Bank</th>
                                                                                        <th>Account Name</th>
                                                                                        <th>Account Number</th>
                                                                                        <th>Status</th>
                                                                                        <th>Action</th>
                                                                                    </tr>
                                                                                </thead>
                                                                                <tbody>
                                                                                    <?php $i = 1;
                                                                                    foreach ($wallet as $wlt) {
                                                                                        if ($wlt['type'] == 'withdraw' && $wlt['status'] == '0') { ?>
                                                                                            <tr>
                                                                                                <td><?= $i ?></td>
                                                                                                <td>#<?= $wlt['id'] ?></td>
                                                                                                <td><?= $wlt['date'] ?></td>

                                                                                                <?php $caracter = substr($wlt['id_user'], 0, 1);
                                                                                                if ($caracter == 'P') { ?>
                                                                                                    <td class="text-primary">Customer</td>
                                                                                                <?php } elseif ($caracter == 'M') { ?>
                                                                                                    <td class="text-success">Merchant</td>
                                                                                                <?php } else { ?>
                                                                                                    <td class="text-warning">Driver</td>

                                                                                                <?php } ?>

                                                                                                <td><?= $wlt['driver_name'] ?><?= $wlt['customer_fullname'] ?><?= $wlt['partner_name'] ?></td>

                                                                                                <td class="text-danger"><?= $currency ?>
                                                                                                    <?= number_format($wlt['wallet_amount'] / 100, 2, ".", ".") ?></td>
                                                                                                <td><?= $wlt['bank'] ?></td>
                                                                                                <td><?= $wlt['holder_name'] ?></td>
                                                                                                <?php if ($wlt['bank'] == 'QRIS') { ?>
                                                                                                    <td>"QR CODE"</td>
                                                                                                <?php } else { ?>
                                                                                                    <td><?= $wlt['wallet_account'] ?></td>
                                                                                                <?php } ?>
                                                                                                <?php if ($wlt['status'] == '0') { ?>
                                                                                                    <td>
                                                                                                        <label class="badge badge-secondary text-dark">Pending</label>
                                                                                                    </td>
                                                                                                <?php }
                                                                                                if ($wlt['status'] == '1') { ?>
                                                                                                    <td>
                                                                                                        <label class="badge badge-success">Success</label>
                                                                                                    </td>
                                                                                                <?php }
                                                                                                if ($wlt['status'] == '2') { ?>
                                                                                                    <td>
                                                                                                        <label class="badge badge-danger">Canceled</label>
                                                                                                    </td>
                                                                                                <?php } ?>

                                                                                                <td>
                                                                                                    <?php if ($wlt['status'] == '0') { ?>
                                                                                                        <a href="<?= base_url(); ?>wallet/wconfirm/<?= $wlt['id'] ?>/<?= $wlt['id_user'] ?>/<?= $wlt['wallet_amount'] ?>">
                                                                                                            <button class="btn-sm btn-outline-primary">Confirm</button>
                                                                                                        </a>
                                                                                                        <a href="<?= base_url(); ?>wallet/wcancel/<?= $wlt['id'] ?>/<?= $wlt['id_user'] ?>">
                                                                                                            <button onclick="return confirm ('Are You Sure?')" class="btn-sm btn-outline-danger">Cancel</button>
                                                                                                        </a>
                                                                                                    <?php } else { ?>
                                                                                                        <span class="btn btn-outline-muted">Completed</span>
                                                                                                    <?php } ?>

                                                                                                </td>

                                                                                            </tr>
                                                                                    <?php $i++;
                                                                                        }
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
                                                <!-- end of withdraw request table -->
                                            </div>
                                            <div class="tab-pane" id="allwithdraw" aria-labelledby="allwithdraw-tab" role="tabpanel">
                                                <!-- all withdraw table start -->
                                                <section id="basic-datatable">
                                                    <div class="row">
                                                        <div class="col-12">
                                                            <div class="card">
                                                                <div class="card-header">
                                                                    <h4 class="card-title">All Withdraw Data</h4>
                                                                </div>
                                                                <div class="card-content">
                                                                    <div class="card-body card-dashboard">
                                                                        <div class="table-responsive">
                                                                            <table class="table table-striped dataex-html5-selectors">
                                                                                <thead>
                                                                                    <tr>
                                                                                        <th>No.</th>
                                                                                        <th>Invoice</th>
                                                                                        <th>Date</th>
                                                                                        <th>Users</th>
                                                                                        <th>Name</th>
                                                                                        <th>amount</th>
                                                                                        <th>Bank</th>
                                                                                        <th>Account Name</th>
                                                                                        <th>Account Number</th>
                                                                                        <th>Status</th>
                                                                                    </tr>
                                                                                </thead>
                                                                                <tbody>
                                                                                    <?php $i = 1;
                                                                                    foreach ($wallet as $wlt) {
                                                                                        if ($wlt['type'] == 'withdraw') { ?>
                                                                                            <tr>
                                                                                                <td><?= $i ?></td>
                                                                                                <td>#<?= $wlt['id'] ?></td>
                                                                                                <td><?= $wlt['date'] ?></td>

                                                                                                <?php $caracter = substr($wlt['id_user'], 0, 1);
                                                                                                if ($caracter == 'P') { ?>
                                                                                                    <td class="text-primary">Customer</td>
                                                                                                <?php } elseif ($caracter == 'M') { ?>
                                                                                                    <td class="text-success">Merchant</td>
                                                                                                <?php } else { ?>
                                                                                                    <td class="text-warning">Driver</td>

                                                                                                <?php } ?>

                                                                                                <td><?= $wlt['driver_name'] ?><?= $wlt['customer_fullname'] ?><?= $wlt['partner_name'] ?></td>

                                                                                                <td class="text-danger"><?= $currency ?>
                                                                                                    <?= number_format($wlt['wallet_amount'] / 100, 2, ".", ".") ?></td>
                                                                                                <td><?= $wlt['bank'] ?></td>
                                                                                                <td><?= $wlt['holder_name'] ?></td>
                                                                                                <?php if ($wlt['bank'] == 'QRIS') { ?>
                                                                                                    <td>"QR CODE"</td>
                                                                                                <?php } else { ?>
                                                                                                    <td><?= $wlt['wallet_account'] ?></td>
                                                                                                <?php } ?>
                                                                                                <?php if ($wlt['status'] == '0') { ?>
                                                                                                    <td>
                                                                                                        <label class="badge badge-secondary text-dark">Pending</label>
                                                                                                    </td>
                                                                                                <?php }
                                                                                                if ($wlt['status'] == '1') { ?>
                                                                                                    <td>
                                                                                                        <label class="badge badge-success">Success</label>
                                                                                                    </td>
                                                                                                <?php }
                                                                                                if ($wlt['status'] == '2') { ?>
                                                                                                    <td>
                                                                                                        <label class="badge badge-danger">Canceled</label>
                                                                                                    </td>
                                                                                                <?php } ?>

                                                                                            </tr>
                                                                                    <?php $i++;
                                                                                        }
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
                                                <!-- all withdraw transfer table -->
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
                <!-- add new sidebar starts -->
                <div class="add-new-data-sidebar">
                    <div class="overlay-bg"></div>
                    <div class="add-new-data">
                        <div class="div mt-2 px-2 d-flex new-data-title justify-content-between">
                            <div>
                                <h4 class="text-uppercase">Manual Withdraw</h4>
                            </div>
                            <div class="hide-data-sidebar">
                                <i class="feather icon-x"></i>
                            </div>
                        </div>
                        <div class="data-items pb-3">
                            <div class="data-fields px-2 mt-3">
                                <div class="row">
                                    <div class="col-sm-12 data-field-col">
                                        <label for="data-category">
                                            User Type
                                        </label>
                                        <select class="select2 form-control" id="data-category">
                                            <option>Customer</option>
                                            <option>Driver</option>
                                            <option>Merchant</option>
                                        </select>
                                    </div>
                                    <div class="col-sm-12 data-field-col">
                                        <label for="data-status">User</label>
                                        <select class="select2 form-control" id="data-status">
                                            <option>Pending</option>
                                            <option>Canceled</option>
                                            <option>Delivered</option>
                                            <option>On Hold</option>
                                        </select>
                                    </div>
                                    <div class="col-sm-12 data-field-col">
                                        <label for="data-name">Withdraw Amount</label>
                                        <input type="text" class="form-control" id="data-name">
                                    </div>
                                    <div class="col-sm-12 data-field-col">
                                        <label for="data-name">Bank Name</label>
                                        <input type="text" class="form-control" id="data-name">
                                    </div>
                                    <div class="col-sm-12 data-field-col">
                                        <label for="data-name">Bank Account</label>
                                        <input type="text" class="form-control" id="data-name">
                                    </div>
                                    <div class="col-sm-12 data-field-col">
                                        <label for="data-name">Account User Name</label>
                                        <input type="text" class="form-control" id="data-name">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="add-data-footer d-flex justify-content-around px-3 mt-2">
                            <div class="add-data-btn">
                                <button class="btn btn-primary">Top Up</button>
                            </div>
                            <div class="cancel-data-btn">
                                <button class="btn btn-outline-danger">Cancel</button>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- add new sidebar ends -->
            </section>
            <!-- end of withdraw data -->
        </div>
    </div>
</div>
<!-- END: Content-->