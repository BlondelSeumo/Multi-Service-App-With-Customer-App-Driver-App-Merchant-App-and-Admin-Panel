<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- transaction history Start -->
            <section id="column-selectors">
                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">History Transaction</h4>
                            </div>
                            <div class="card-content">
                                <div class="card-body card-dashboard">
                                    <div class="table-responsive">
                                        <table class="table table-striped dataex-html5-selectors">
                                            <thead>
                                                <tr>
                                                    <th>No</th>
                                                    <th>Inv</th>
                                                    <th>Customer</th>
                                                    <th>Driver</th>
                                                    <th>Service</th>
                                                    <th>Pick Up</th>
                                                    <th>Destination</th>
                                                    <th>Price</th>
                                                    <th>Payment Method</th>
                                                    <th>Status</th>
                                                    <th>Actions</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <?php $i = 1;
                                                foreach ($transaction as $tr) { ?>
                                                    <tr>
                                                        <th><?= $i; ?></th>
                                                        <th>#INV-<?= $tr['transaction_id'] ?></th>
                                                        <th><?= $tr['customer_fullname'] ?></th>
                                                        <th>
                                                            <?php if (empty($tr['driver_name']) || $tr['driver_name'] == 'D0') { ?>
                                                                <?php if ($tr['status'] == '0') { ?>
                                                                    <p class="text-danger">no driver</p>
                                                                <?php } ?>

                                                                <?php if ($tr['status'] == '1') { ?>
                                                                    <p class="text-info">search driver</p>
                                                                <?php } ?>
                                                            <?php } else { ?>
                                                                <?= $tr['driver_name'] ?>
                                                            <?php } ?></th>
                                                        <th><?= $tr['service'] ?></th>
                                                        <th><?= $tr['pickup_address'] ?></th>
                                                        <th><?= $tr['destination_address'] ?></th>
                                                        <th><?= $currency ?>
                                                            <?= number_format($tr['final_cost'] / 100, 2, ".", ".") ?></th>
                                                        <th>
                                                            <?php if ($tr['wallet_payment'] == '0') {
                                                                echo 'CASH';
                                                            } else {
                                                                echo 'WALLET';
                                                            } ?>
                                                        </th>
                                                        <th>
                                                            <?php if ($tr['status'] == '0') { ?>
                                                                <label class="badge badge-danger">no driver</label>
                                                            <?php } elseif ($tr['status'] == '1') { ?>
                                                                <label class="badge badge-info"><?= $tr['transaction_status']; ?></label>
                                                            <?php } elseif ($tr['status'] == '2') { ?>
                                                                <label class="badge badge-primary"><?= $tr['transaction_status']; ?></label>
                                                            <?php } elseif ($tr['status'] == '3') { ?>
                                                                <label class="badge badge-primary"><?= $tr['transaction_status']; ?></label>
                                                            <?php } elseif ($tr['status'] == '5') { ?>
                                                                <label class="badge badge-warning"><?= $tr['transaction_status']; ?></label>
                                                            <?php } elseif ($tr['status'] == '4') { ?>
                                                                <label class="badge badge-success"><?= $tr['transaction_status']; ?></label>
                                                            <?php } ?>
                                                        </th>
                                                        <th>
                                                            <span>
                                                                <a href="<?= base_url(); ?>detailtransaction/index/<?= $tr['transaction_id']; ?>">
                                                                    <i class="feather icon-eye text-success"></i></a>
                                                            </span>

                                                            <span class="action-delete ml-1">
                                                                <a onclick="return confirm ('Are You Sure?')" href="<?= base_url(); ?>historytransaction/deletetransaction/<?= $tr['transaction_id']; ?>">
                                                                    <i class="feather icon-trash text-danger"></i></a>
                                                            </span>

                                                        </th>
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
            <!-- end of transaction history -->
        </div>
    </div>
</div>
<!-- END: Content-->