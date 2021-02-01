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
                        <div class="card" >
                            <div class="card-header d-flex align-items-start pb-0">
                                <div>
                                    <h2 class="text-bold-700 mb-0" style="font-size: 18px;">
                                        <i class="feather icon-plus-circle text-success"></i>
                                        <?= $currency ?>
                                        <?= number_format($totalorderplus['total'] / 100, 2, ".", ".") ?>
                                    </h2>
                                    <p>Transaction Amount</p>
                                </div>
                                <div>
                                    <h2 class="text-bold-700 mb-0" style="font-size: 18px;">
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

            <div class="row">
                <div class="col-xl-3 col-md-4 col-sm-6">
                    <div class="card text-center">
                        <div class="card-content">
                            <div class="card-body">
                                <h2 class="text-bold-700"><?= $currency ?>
                                    <?= number_format($totaltopupadmin['totaltopupbyadmin'] / 100, 2, ".", ".") ?></h2>
                                <p class="mb-0 line-ellipsis" style="font-size: 13px;">Admin Manual TopUp Amount</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xl-3 col-md-4 col-sm-6">
                    <div class="card text-center">
                        <div class="card-content">
                            <div class="card-body">
                                <h2 class="text-bold-700"><?= $currency ?>
                                    <?= number_format($totaltopupstripe['totaltopupbystripe'] / 100, 2, ".", ".") ?></h2>
                                <p class="mb-0 line-ellipsis" style="font-size: 13px;">Stripe TopUp Amount</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xl-3 col-md-4 col-sm-6">
                    <div class="card text-center">
                        <div class="card-content">
                            <div class="card-body">
                                <h2 class="text-bold-700"><?= $currency ?>
                                    <?= number_format($totaltopuppaypal['totaltopupbypaypal'] / 100, 2, ".", ".") ?></h2>
                                <p class="mb-0 line-ellipsis" style="font-size: 13px;">Paypal TopUp Amount</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xl-3 col-md-4 col-sm-6">
                    <div class="card text-center">
                        <div class="card-content">
                            <div class="card-body">
                                <h2 class="text-bold-700"><?= $currency ?>
                                    <?= number_format($totaltopuptransfer->row('totaltopupbytransfer') / 100, 2, ".", ".") ?></h2>
                                <p class="mb-0 line-ellipsis" style="font-size: 13px;">Bank Transfer TopUp Amount</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- start of revenue & in proggress -->
            <div class="row">
                <!-- Line Area Chart -->
                <div class="col-lg-12 col-md-6 col-md-12">
                    <div class="card">
                        <div class="card-header">
                            <h4 class="card-title">Revenue Chart</h4>
                        </div>
                        <div class="card-content">
                            <div class="card-body">
                                <div id="line-area-chart"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- end of transaction statistic -->





        </div>
    </div>
</div>
<!-- END: Content-->