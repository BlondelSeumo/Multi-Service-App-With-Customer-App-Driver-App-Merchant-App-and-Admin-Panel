<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- centered-slides swiper option-1 start -->
            
            <section id="component-swiper-centered-slides">
                <div class="card bg-transparent shadow-none">
                    <div class="card-header">
                        <h4 class="card-title">Transaction Complete By Services</h4>
                    </div>
                    
                    <div class="card-content">
                        <div class="card-body">
                            <div class="swiper-centered-slides swiper-container p-1">
                                <div class="swiper-wrapper">
                                <?php foreach ($complete as $cmpt) { ?>
                                    <div class="swiper-slide rounded swiper-shadow col-md-3 col-12">
                                        <div class="card">
                                            <div class="card-header">
                                                <h4 class="card-title"><?= $cmpt['service'] ?> Service</h4>
                                            </div>
                                            <div class="card-content">
                                                <div class="card-body">
                                                    <div class="d-flex justify-content-between mb-25">
                                                        <div class="browser-info">
                                                            <p class="mb-25">Today</p>
                                                            <h4 class="text-left"><?= $cmpt['daily'] ?></h4>
                                                        </div>
                                                        <div class="stastics-info text-right">
                                                            <span><?= $cmpt['latestday'] ?>
                                                            <?php if ($cmpt['icondaily'] == 'up') { ?>
                                                                <i class="feather icon-arrow-up text-success"></i>
                                                            <?php } else { ?>
                                                                <i class="feather icon-arrow-down text-danger"></i>
                                                            <?php } ?>
                                                            </span>
                                                            <span class="text-muted d-block">yesterday</span>
                                                        </div>
                                                    </div>
                                                    <div class="d-flex justify-content-between mb-25">
                                                        <div class="browser-info">
                                                            <p class="mb-25">This Month</p>
                                                            <h4 class="text-left"><?= $cmpt['monthly'] ?></h4>
                                                        </div>
                                                        <div class="stastics-info text-right">
                                                            <span><?= $cmpt['latestmonth'] ?>
                                                            <?php if ($cmpt['iconmonthly'] == 'up') { ?>
                                                                <i class="feather icon-arrow-up text-success"></i>
                                                            <?php } else { ?>
                                                                <i class="feather icon-arrow-down text-danger"></i>
                                                            <?php } ?>
                                                            </span>
                                                            <span class="text-muted d-block">last month</span>
                                                        </div>
                                                    </div>
                                                    <div class="d-flex justify-content-between mb-25">
                                                        <div class="browser-info">
                                                            <p class="mb-25">This Year</p>
                                                            <h4 class="text-left"><?= $cmpt['yearly'] ?></h4>
                                                        </div>
                                                        <div class="stastics-info text-right">
                                                            <span><?= $cmpt['latestyear'] ?>
                                                            <?php if ($cmpt['iconyearly'] == 'up') { ?>
                                                                <i class="feather icon-arrow-up text-success"></i>
                                                            <?php } else { ?>
                                                                <i class="feather icon-arrow-down text-danger"></i>
                                                            <?php } ?>
                                                            </span>
                                                            <span class="text-muted d-block">last year</span>
                                                        </div>
                                                    </div>

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <?php } ?>
                                </div>
                           
                                <!-- Add Arrows -->
                                <div class="swiper-button-next"></div>
                                <div class="swiper-button-prev"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <!-- centered-slides swiper option-1 ends -->
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
                                <h4 class="mb-0">Entire Complete Transaction</h4>
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
            <!-- end of transaction statistic -->
            <!-- Transaction statistic Analytics Start -->
            <section id="dashboard-analytics">
                <div class="row">
                <div class="col-md-3 col-12">
                            <div class="card text-center">
                                <div class="card-content">
                                    <div class="card-body">
                                        <h2 class="text-bold-700"><?= $statistic->row('passangertranscount') ?></h2>
                                        <p class="mb-0 line-ellipsis">Total Passanger Transport Transaction</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3 col-12">
                            <div class="card text-center">
                                <div class="card-content">
                                    <div class="card-body">
                                        <h2 class="text-bold-700"><?= $statistic->row('shipmenttranscount') ?></h2>
                                        <p class="mb-0 line-ellipsis">Total Shipment Transaction</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3 col-12">
                            <div class="card text-center">
                                <div class="card-content">
                                    <div class="card-body">
                                        <h2 class="text-bold-700"><?= $statistic->row('rentaltranscount') ?></h2>
                                        <p class="mb-0 line-ellipsis">Total Rental Transaction</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3 col-12">
                            <div class="card text-center">
                                <div class="card-content">
                                    <div class="card-body">
                                        <h2 class="text-bold-700"><?= $statistic->row('purchasingtranscount') ?></h2>
                                        <p class="mb-0 line-ellipsis">Total Purchasing Service Transaction</p>
                                    </div>
                                </div>
                            </div>
                        </div>
            </div>
            <!-- end of dashboard analitics -->

            <div class="row">
                <div class="col-md-3 col-12">
                    <div class="card">
                        <div class="card-header">
                            <h4 class="card-title">Passanger Transport</h4>
                        </div>
                        <div class="card-content">
                            <div class="card-body">
                                <div class="d-flex justify-content-between mb-25">
                                    <div class="browser-info">
                                        <p class="mb-25">Complete</p>
                                        <h4><?= $countpassanger->row('successpassangercount') ?></h4>
                                    </div>
                                    <div class="stastics-info text-right">
                                        <span><?= $countpassanger->row('successpassangercountdaily') ?>
                                        </span>
                                        <span class="text-muted d-block">today</span>
                                    </div>
                                </div>                            

                                <div class="d-flex justify-content-between mb-25">
                                    <div class="browser-info">
                                        <p class="mb-25">Canceled</p>
                                        <h4><?= $countpassanger->row('cancelpassangercount') ?></h4>
                                    </div>
                                    <div class="stastics-info text-right">
                                        <span><?= $countpassanger->row('cancelpassangercountdaily') ?></span>
                                        <span class="text-muted d-block">today</span>
                                    </div>
                                </div>

                                <div class="d-flex justify-content-between mb-25">
                                    <div class="browser-info">
                                        <p class="mb-25">No Driver</p>
                                        <h4><?= $countpassanger->row('nodriverpassangercount') ?></h4>
                                    </div>
                                    <div class="stastics-info text-right">
                                        <span><?= $countpassanger->row('nodriverpassangercountdaily') ?></span>
                                        <span class="text-muted d-block">today</span>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-3 col-12">
                    <div class="card">
                        <div class="card-header">
                            <h4 class="card-title">Shipment Transport</h4>
                        </div>
                        <div class="card-content">
                            <div class="card-body">
                                <div class="d-flex justify-content-between mb-25">
                                    <div class="browser-info">
                                        <p class="mb-25">Complete</p>
                                        <h4><?= $countshipment->row('successshipmentcount') ?></h4>
                                    </div>
                                    <div class="stastics-info text-right">
                                        <span><?= $countshipment->row('successshipmentcountdaily') ?>
                                        </span>
                                        <span class="text-muted d-block">today</span>
                                    </div>
                                </div>                            

                                <div class="d-flex justify-content-between mb-25">
                                    <div class="browser-info">
                                        <p class="mb-25">Canceled</p>
                                        <h4><?= $countshipment->row('cancelshipmentcount') ?></h4>
                                    </div>
                                    <div class="stastics-info text-right">
                                        <span><?= $countshipment->row('cancelshipmentcountdaily') ?></span>
                                        <span class="text-muted d-block">today</span>
                                    </div>
                                </div>

                                <div class="d-flex justify-content-between mb-25">
                                    <div class="browser-info">
                                        <p class="mb-25">No Driver</p>
                                        <h4><?= $countshipment->row('nodrivershipmentcount') ?></h4>
                                    </div>
                                    <div class="stastics-info text-right">
                                        <span><?= $countshipment->row('nodrivershipmentcountdaily') ?></span>
                                        <span class="text-muted d-block">today</span>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-3 col-12">
                    <div class="card">
                        <div class="card-header">
                            <h4 class="card-title">Rental Transport</h4>
                        </div>
                        <div class="card-content">
                            <div class="card-body">
                                <div class="d-flex justify-content-between mb-25">
                                    <div class="browser-info">
                                        <p class="mb-25">Complete</p>
                                        <h4><?= $countrental->row('successrentalcount') ?></h4>
                                    </div>
                                    <div class="stastics-info text-right">
                                        <span><?= $countrental->row('successrentalcountdaily') ?>
                                        </span>
                                        <span class="text-muted d-block">today</span>
                                    </div>
                                </div>                           

                                <div class="d-flex justify-content-between mb-25">
                                    <div class="browser-info">
                                        <p class="mb-25">Canceled</p>
                                        <h4><?= $countrental->row('cancelrentalcount') ?></h4>
                                    </div>
                                    <div class="stastics-info text-right">
                                        <span><?= $countrental->row('cancelrentalcountdaily') ?></span>
                                        <span class="text-muted d-block">today</span>
                                    </div>
                                </div>

                                <div class="d-flex justify-content-between mb-25">
                                    <div class="browser-info">
                                        <p class="mb-25">No Driver</p>
                                        <h4><?= $countrental->row('nodriverrentalcount') ?></h4>
                                    </div>
                                    <div class="stastics-info text-right">
                                        <span><?= $countrental->row('nodriverrentalcountdaily') ?></span>
                                        <span class="text-muted d-block">today</span>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-3 col-12">
                    <div class="card">
                        <div class="card-header">
                            <h4 class="card-title">Purchasing Transport</h4>
                        </div>
                        <div class="card-content">
                            <div class="card-body">
                                <div class="d-flex justify-content-between mb-25">
                                    <div class="browser-info">
                                        <p class="mb-25">Complete</p>
                                        <h4><?= $countpurchasing->row('successpurchasingcount') ?></h4>
                                    </div>
                                    <div class="stastics-info text-right">
                                        <span><?= $countpurchasing->row('successpurchasingcountdaily') ?>
                                        </span>
                                        <span class="text-muted d-block">today</span>
                                    </div>
                                </div>                           

                                <div class="d-flex justify-content-between mb-25">
                                    <div class="browser-info">
                                        <p class="mb-25">Canceled</p>
                                        <h4><?= $countpurchasing->row('cancelpurchasingcount') ?></h4>
                                    </div>
                                    <div class="stastics-info text-right">
                                        <span><?= $countpurchasing->row('cancelpurchasingcountdaily') ?></span>
                                        <span class="text-muted d-block">today</span>
                                    </div>
                                </div>

                                <div class="d-flex justify-content-between mb-25">
                                    <div class="browser-info">
                                        <p class="mb-25">No Driver</p>
                                        <h4><?= $countpurchasing->row('nodriverpurchasingcount') ?></h4>
                                    </div>
                                    <div class="stastics-info text-right">
                                        <span><?= $countpurchasing->row('nodriverpurchasingcountdaily') ?></span>
                                        <span class="text-muted d-block">today</span>
                                    </div>
                                </div>

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