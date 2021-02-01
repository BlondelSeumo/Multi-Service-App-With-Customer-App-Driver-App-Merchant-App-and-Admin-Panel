<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- Dashboard Analytics Start -->
            <section id="dashboard-analytics">
                <div class="row">
                    <div class="col-md-4 col-12">
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
                    <div class="col-md-4 col-12">
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
                    <div class="col-md-4 col-12">
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
                </div>
                <!-- end of dashboard analitics -->

                <div class="row">
                        <div class="col-xl-2 col-md-4 col-sm-6">
                            <div class="card text-center">
                                <div class="card-content">
                                    <div class="card-body">
                                        <div class="avatar bg-rgba-warning p-50 m-0 mb-1">
                                            <div class="avatar-content">
                                                <i class="feather icon-layers text-warning font-medium-5"></i>
                                            </div>
                                        </div>
                                        <h2 class="text-bold-700"><?= $statistic->row('servicecount') ?></h2>
                                        <p class="mb-0 line-ellipsis" style="font-size: 13px;">Services</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-2 col-md-4 col-sm-6">
                            <div class="card text-center">
                                <div class="card-content">
                                    <div class="card-body">
                                        <div class="avatar bg-rgba-danger p-50 m-0 mb-1">
                                            <div class="avatar-content">
                                                <i class="feather icon-truck text-danger font-medium-5"></i>
                                            </div>
                                        </div>
                                        <h2 class="text-bold-700"><?= $statistic->row('driverjobcount') ?></h2>
                                        <p class="mb-0 line-ellipsis" style="font-size: 13px;">Driver Job</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-2 col-md-4 col-sm-6">
                            <div class="card text-center">
                                <div class="card-content">
                                    <div class="card-body">
                                        <div class="avatar bg-rgba-info p-50 m-0 mb-1">
                                            <div class="avatar-content">
                                                <i class="feather icon-shopping-bag text-info font-medium-5"></i>
                                            </div>
                                        </div>
                                        <h2 class="text-bold-700"><?= $statistic->row('merchanttypecount') ?></h2>
                                        <p class="mb-0 line-ellipsis" style="font-size: 13px;">Merchant Type</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-2 col-md-4 col-sm-6">
                            <div class="card text-center">
                                <div class="card-content">
                                    <div class="card-body">
                                        <div class="avatar bg-rgba-success p-50 m-0 mb-1">
                                            <div class="avatar-content">
                                                <i class="feather icon-shopping-cart text-success font-medium-5"></i>
                                            </div>
                                        </div>
                                        <h2 class="text-bold-700"><?= $statistic->row('merchantcatcount') ?></h2>
                                        <p class="mb-0 line-ellipsis" style="font-size: 13px;">Merchant Category</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-2 col-md-4 col-sm-6">
                            <div class="card text-center">
                                <div class="card-content">
                                    <div class="card-body">
                                        <div class="avatar bg-rgba-danger p-50 m-0 mb-1">
                                            <div class="avatar-content">
                                                <i class="feather icon-smartphone text-danger font-medium-5"></i>
                                            </div>
                                        </div>
                                        <h2 class="text-bold-700"><?= $statistic->row('slidercount') ?></h2>
                                        <p class="mb-0 line-ellipsis" style="font-size: 13px;">Slider Promotion</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-2 col-md-4 col-sm-6">
                            <div class="card text-center">
                                <div class="card-content">
                                    <div class="card-body">
                                        <div class="avatar bg-rgba-info p-50 m-0 mb-1">
                                            <div class="avatar-content">
                                                <i class="feather icon-percent text-info font-medium-5"></i>
                                            </div>
                                        </div>
                                        <h2 class="text-bold-700"><?= $statistic->row('promocodecount') ?></h2>
                                        <p class="mb-0 line-ellipsis" style="font-size: 13px;">Promo Code</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                <div class="row">
                        <!-- Pie Chart -->
                        <div class="col-lg-6 col-md-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Driver Job Spread</h4>
                                </div>
                                <div class="card-content">
                                    <div class="card-body">
                                        <div id="pie-chart" class="mx-auto"></div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Donut Chart -->
                        <div class="col-lg-6 col-md-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Merchant Type Spread</h4>
                                </div>
                                <div class="card-content">
                                    <div class="card-body">
                                        <div id="donut-chart" class="mx-auto"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

            </section>
            
        </div>
    </div>
</div>
<!-- END: Content-->