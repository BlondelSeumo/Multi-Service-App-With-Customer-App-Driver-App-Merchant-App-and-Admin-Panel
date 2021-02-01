<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- Dashboard Analytics Start -->
            <section id="chart-dashboard">
                <div class="row">
                    <div class="col-md-3 col-12">
                        <div class="card">
                            <div class="card-header d-flex flex-column align-items-start pb-0">
                                <div class="avatar bg-rgba-success p-50 m-0">
                                    <div class="avatar-content">
                                        <i class="feather icon-user text-success font-medium-5"></i>
                                    </div>
                                </div>
                                <h2 class="text-bold-700 mt-1 mb-25"><?= $counters->row('usercount') ?></h2>
                                <p class="mb-0">Total Users</p>
                            </div>
                            <div class="card-content">
                                <div id="chart1"></div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3 col-12">
                        <div class="card">
                            <div class="card-header d-flex flex-column align-items-start pb-0">
                                <div class="avatar bg-rgba-danger p-50 m-0">
                                    <div class="avatar-content">
                                        <i class="feather icon-users text-danger font-medium-5"></i>
                                    </div>
                                </div>
                                <h2 class="text-bold-700 mt-1 mb-25"><?= $counters->row('drivercount') ?></h2>
                                <p class="mb-0">Total Drivers</p>
                            </div>
                            <div class="card-content">
                                <div id="chart2"></div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3 col-12">
                        <div class="card">
                            <div class="card-header d-flex flex-column align-items-start pb-0">
                                <div class="avatar bg-rgba-warning p-50 m-0">
                                    <div class="avatar-content">
                                        <i class="feather icon-shopping-bag text-warning font-medium-5"></i>
                                    </div>
                                </div>
                                <h2 class="text-bold-700 mt-1 mb-25"><?= $counters->row('merchantcount') ?></h2>
                                <p class="mb-0">Total Merchants</p>
                            </div>
                            <div class="card-content">
                                <div id="chart3"></div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3 col-12">
                        <div class="card">
                            <div class="card-header d-flex flex-column align-items-start pb-0">
                                <div class="avatar bg-rgba-info p-50 m-0">
                                    <div class="avatar-content">
                                        <i class="feather icon-refresh-ccw text-info font-medium-5"></i>
                                    </div>
                                </div>
                                <h2 class="text-bold-700 mt-1 mb-25"><?= $counters->row('transactioncount') ?></h2>
                                <p class="mb-0">Total Orders
                                    <i class="mb-0 feather icon-info"></i>
                                </p>
                            </div>
                            <div class="card-content">
                                <div id="chart4"></div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- end of dashboard analitics -->

                <!-- start of revenue & in proggress -->
                <div class="row">
                    <!-- Line Area Chart -->
                    <div class="col-lg-8 col-md-6 col-md-12">
                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">Transacaction Chart</h4>
                            </div>
                            <div class="card-content">
                                <div class="card-body">
                                    <div id="line-area-chart"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-6 col-12">
                        <div class="card">
                            <div class="card-header d-flex justify-content-between align-items-end">
                                <h4 class="mb-0">Monthly Complete Transaction</h4>
                                <p class="font-medium-5 mb-0"></p>
                            </div>
                            <div class="card-content">
                                <div class="card-body px-0 pb-0">
                                    <div id="goal-overview-chart" class="mt-75"></div>
                                    <div class="row text-center mx-0">
                                        <div class="col-6 border-top border-right d-flex align-items-between flex-column py-1">
                                            <p class="mb-50">In progress</p>
                                            <p class="font-large-1 text-bold-700"><?= $totalprogress ?></p>
                                        </div>
                                        <div class="col-6 border-top d-flex align-items-between flex-column py-1">
                                                    <p class="mb-50">Completed</p>
                                                    <p class="font-large-1 text-bold-700 text-success"><?= $totalsuccess ?></p>
                                                </div>
                                                <div class="col-6 border-top border-right d-flex align-items-between flex-column py-1">
                                            <p class="mb-50">Canceled</p>
                                            <p class="font-large-1 text-bold-700 text-danger"><?= $totalcanceled ?></p>
                                        </div>
                                        <div class="col-6 border-top d-flex align-items-between flex-column py-1">
                                                    <p class="mb-50">No Driver</p>
                                                    <p class="font-large-1 text-bold-700 text-danger"><?= $totalnodriver ?></p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                <!-- end of revenue & in proggress -->

            </section>
            <!-- recent transaction table start -->
            <section id="basic-datatable">
                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">Recent Transaction</h4>
                            </div>
                            <div class="card-content">
                                <div class="card-body card-dashboard">
                                    <div class="table-responsive">
                                        <table class="table zero-configuration">
                                            <thead>
                                                <tr>
                                                    <th>No</th>
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
                                                        <th><?= $tr['customer_fullname'] ?></th>
                                                        <th>
                                                            <?php if (empty($tr['driver_name']) || $tr['driver_name'] == 'D0') { ?>
                                                                <p class="text-info">search driver</p>
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
                                                            <span class="mr-1">
                                                                <a href="<?= base_url(); ?>detailtransaction/index/<?= $tr['transaction_id']; ?>">
                                                                    <i class="feather icon-eye text-success"></i></a>
                                                            </span>

                                                            <span class="action-delete ml-1">
                                                                <a onclick="return confirm ('Are You Sure?')" href="<?= base_url(); ?>dashboard/deletetransaction/<?= $tr['transaction_id']; ?>">
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
            <!-- end of recent transaction table -->
        </div>
    </div>
</div>
<!-- END: Content-->