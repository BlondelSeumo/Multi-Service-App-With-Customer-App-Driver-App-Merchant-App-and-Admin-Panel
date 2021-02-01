<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- Wallet count starts -->
            <section id="statistics-card">
                <div class="row">
                    <div class="col-lg-4 col-sm-6 col-12">
                        <div class="card">
                            <div class="card-header d-flex align-items-start pb-0">
                                <div>
                                    <h2 class="text-bold-700 mb-0">
                                        <?php
                                        $walletvalue = $totaltopup['total'] - $totalwithdraw['total'] - ($totalordermin['total'] - $totalorderplus['total']);
                                        $apprevenue = ($totalordermin['total'] - $totalorderplus['total']);
                                        ?>
                                        <?= $currency ?>
                                        <?= number_format($apprevenue / 100, 2, ".", ".") ?>
                                    </h2>
                                    <p>Current App Revenue</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4 col-sm-6 col-12">
                        <div class="card">
                            <div class="card-header d-flex align-items-start pb-0">
                                <div>
                                    <h2 class="text-bold-700 mb-0">
                                    <?= $currency ?>
                                        <?= number_format($balance['total'] / 100, 2, ".", ".") ?>
                                    </h2>
                                    <p>User Wallet Amount</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4 col-sm-6 col-12">
                        <div class="card">
                            <div class="card-header d-flex align-items-start pb-0">
                                <div>
                                    <h2 class="text-bold-700 mb-0">
                                        <?php
                                        $walletvalue = $totaltopup['total'] - $totalwithdraw['total'] - ($totalordermin['total'] - $totalorderplus['total']);
                                        ?>
                                        <?= $currency ?>
                                        <?= number_format($totaldiscount['discount'] / 100, 2, ".", ".") ?>
                                    </h2>
                                    <p>Current Discount Spent</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-4 col-sm-6 col-12">
                        <div class="card">
                            <div class="card-header d-flex align-items-start pb-0">
                                <div>
                                    <h2 class="text-bold-700 mb-0">
                                        <?= $currency ?>
                                        <?= number_format($totaltopup['total'] / 100, 2, ".", ".") ?>
                                    </h2>
                                    <p>Current TopUp Amount</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4 col-sm-6 col-12">
                        <div class="card">
                            <div class="card-header d-flex align-items-start pb-0">
                                <div>
                                    <h2 class="text-bold-700 mb-0">
                                        <?= $currency ?>
                                        <?= number_format($totalwithdraw['total'] / 100, 2, ".", ".") ?>
                                    </h2>
                                    <p>Current Withdraw Amount</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4 col-sm-6 col-12">
                        <div class="card">
                            <div class="card-header d-flex align-items-start pb-0">
                                <div>
                                    <h2 class="text-bold-700 mb-0">
                                        <i class="feather icon-plus-circle text-success"></i>
                                        <?= $currency ?>
                                        <?= number_format($totalorderplus['total'] / 100, 2, ".", ".") ?>
                                    </h2>
                                    <p>Transaction Amount</p>
                                </div>
                                <div>
                                    <h2 class="text-bold-700 mb-0">
                                        <i class="feather icon-minus-circle text-danger"></i>
                                        <?= $currency ?>
                                        <?= number_format($totalordermin['total'] / 100, 2, ".", ".") ?>
                                    </h2>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <!-- Wallet count end -->

            <!-- Wallet data start -->
            <section id="basic-tabs-components">
                <div class="row">
                    <div class="col-sm-12">
                        <div class="card overflow-hidden">
                            <div class="card-header">
                                <h4 class="card-title">Wallet Data</h4>
                            </div>
                            <div class="card-content">
                                <div class="card-body">
                                    <ul class="nav nav-tabs" role="tablist">
                                        <li class="nav-item">
                                            <a class="nav-link active" id="allwallet-tab" data-toggle="tab" href="#allwallet" aria-controls="allwallet" role="tab" aria-selected="true">All Wallet</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" id="transaction-tab" data-toggle="tab" href="#transaction" aria-controls="transaction" role="tab" aria-selected="false">Transaction</a>
                                        </li>
                                    </ul>
                                    <div class="tab-content">
                                        <div class="tab-pane active" id="allwallet" aria-labelledby="allwallet-tab" role="tabpanel">
                                            <!-- recent all wallet table start -->
                                            <section id="basic-datatable">
                                                <div class="row">
                                                    <div class="col-12">
                                                        <div class="card">
                                                            <div class="card-header">
                                                                <h4 class="card-title">All Wallet</h4>
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
                                                                                    <th>Type</th>
                                                                                    <th>Status</th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                                <?php $i = 1;
                                                                                foreach ($wallet as $wlt) { ?>
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
                                                                                        <?php if ($wlt['type'] == 'topup' or $wlt['type'] == 'Order+') { ?>
                                                                                            <td class="text-success">
                                                                                                <?= $currency ?>
                                                                                                <?= number_format($wlt['wallet_amount'] / 100, 2, ".", ".") ?>
                                                                                            </td>

                                                                                        <?php } else { ?>
                                                                                            <td class="text-danger">
                                                                                                <?= $currency ?>
                                                                                                <?= number_format($wlt['wallet_amount'] / 100, 2, ".", ".") ?>
                                                                                            </td>
                                                                                        <?php } ?>



                                                                                        <?php if ($wlt['type'] == 'topup' or $wlt['type'] == 'Order+') { ?>
                                                                                            <td>
                                                                                                <label class="badge badge-success"><?= $wlt['type'] ?></label>
                                                                                            </td>
                                                                                        <?php } else { ?>
                                                                                            <td>
                                                                                                <label class="badge badge-danger"><?= $wlt['type'] ?></label>
                                                                                            </td>
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
                                            <!-- end of all wallet table -->
                                        </div>
                                        <div class="tab-pane" id="transaction" aria-labelledby="transaction-tab" role="tabpanel">
                                            <!-- recent transaction table start -->
                                            <section id="basic-datatable">
                                                <div class="row">
                                                    <div class="col-12">
                                                        <div class="card">
                                                            <div class="card-header">
                                                                <h4 class="card-title">Transaction Wallet</h4>
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
                                                                                    <th>Users</th>
                                                                                    <th>Name</th>
                                                                                    <th>Amount</th>
                                                                                    <th>Type</th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                                <?php $i = 1;
                                                                                foreach ($wallet as $wlt) {
                                                                                    if ($wlt['type'] == 'Order+' or $wlt['type'] == 'Order-') { ?>
                                                                                        <tr>
                                                                                            <td><?= $i ?></td>
                                                                                            <td>#<?= $wlt['id'] ?></td>
                                                                                            <td><?= $wlt['bank'] ?></td>
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

                                                                                            <?php if ($wlt['type'] == 'Order+') { ?>
                                                                                                <td class="text-success"><?= $currency ?>
                                                                                                    <?= number_format($wlt['wallet_amount'] / 100, 2, ".", ".") ?></td>
                                                                                            <?php } else { ?>
                                                                                                <td class="text-danger"><?= $currency ?>
                                                                                                    <?= number_format($wlt['wallet_amount'] / 100, 2, ".", ".") ?></td>
                                                                                            <?php } ?>

                                                                                            <?php if ($wlt['type'] == 'Order+') { ?>
                                                                                                <td>
                                                                                                    <label class="badge badge-success">IN</label>
                                                                                                </td>
                                                                                            <?php } else { ?>
                                                                                                <td>
                                                                                                    <label class="badge badge-danger">OUT</label>
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
                                            <!-- end of recent transaction table -->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <!-- end of wallet data -->
        </div>
    </div>
</div>
<!-- END: Content-->