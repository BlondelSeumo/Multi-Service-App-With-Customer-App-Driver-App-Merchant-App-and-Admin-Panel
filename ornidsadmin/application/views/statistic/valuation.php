<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">

        <section id="dashboard-analytics">
            <div class="row">
                <div class="col-xl-12">
                            <div class="card text-center">
                                <div class="card-content">
                                    <div class="card-body">
                                        <h1 class="text-bold-700">
                                        <?= $currency ?>
                                        <?= number_format($totaltransvalue->row('totaltransactionvalue') / 100, 2, ".", ".") ?>
                                        </h1>
                                        <p class="mb-0 line-ellipsis">Transaction Value</p>
                                    </div>
                                </div>
                            </div>
                        </div>
            </div>

            <div class="row">
                <div class="col-md-4 col-12">
                            <div class="card text-center">
                                <div class="card-content">
                                    <div class="card-body">
                                        <h2 class="text-bold-700">
                                        <?= $currency ?>
                                        <?= number_format($completetransvalue->row('completetransactionvalue') / 100, 2, ".", ".") ?>
                                        </h2>
                                        <p class="mb-0 line-ellipsis">Complete Transaction Value</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4 col-12">
                            <div class="card text-center">
                                <div class="card-content">
                                    <div class="card-body">
                                        <h2 class="text-bold-700">
                                        <?= $currency ?>
                                        <?= number_format($canceltransvalue->row('canceltransactionvalue') / 100, 2, ".", ".") ?>
                                        </h2>
                                        <p class="mb-0 line-ellipsis">Cancel Transaction Value</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4 col-12">
                            <div class="card text-center">
                                <div class="card-content">
                                    <div class="card-body">
                                        <h2 class="text-bold-700">
                                        <?= $currency ?>
                                        <?= number_format($nodrivertransvalue->row('nodrivertransactionvalue') / 100, 2, ".", ".") ?>
                                        </h2>
                                        <p class="mb-0 line-ellipsis">No Driver Transaction Value</p>
                                    </div>
                                </div>
                            </div>
                        </div>
            </div>
            </section>
            
            <!-- centered-slides swiper option-1 start -->
            
            <section id="component-swiper-centered-slides">
                <div class="card bg-transparent shadow-none">
                    <div class="card-header">
                        <h4 class="card-title">Transaction Complete Value By Services</h4>
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
                                                            <h4 class="text-left">
                                                            <?= $currency ?>
                                                            <?= number_format($cmpt['daily'] / 100, 2, ".", ".") ?>
                                                            </h4>
                                                        </div>
                                                        <div class="stastics-info text-right">
                                                            <span>
                                                            <?= $currency ?>
                                                            <?= number_format($cmpt['latestday'] / 100, 2, ".", ".") ?>
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
                                                            <h4 class="text-left">
                                                            <?= $currency ?>
                                                            <?= number_format($cmpt['monthly'] / 100, 2, ".", ".") ?>
                                                            </h4>
                                                        </div>
                                                        <div class="stastics-info text-right">
                                                            <span>
                                                            <?= $currency ?>
                                                            <?= number_format($cmpt['latestmonth'] / 100, 2, ".", ".") ?>
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
                                                            <h4 class="text-left">
                                                            <?= $currency ?>
                                                            <?= number_format($cmpt['yearly'] / 100, 2, ".", ".") ?>
                                                            </h4>
                                                        </div>
                                                        <div class="stastics-info text-right">
                                                            <span>
                                                            <?= $currency ?>
                                                            <?= number_format($cmpt['latestyear'] / 100, 2, ".", ".") ?>
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
            
            <!-- Transaction statistic Analytics Start -->
            <section id="dashboard-analytics">
                <div class="row">
                <div class="col-md-3 col-12">
                            <div class="card text-center">
                                <div class="card-content">
                                    <div class="card-body">
                                        <h2 class="text-bold-700">
                                        <?= $currency ?>
                                        <?= number_format($statistic->row('passangertranscount') / 100, 2, ".", ".") ?>
                                        </h2>
                                        <p class="mb-0 line-ellipsis">Passanger Transport Value</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3 col-12">
                            <div class="card text-center">
                                <div class="card-content">
                                    <div class="card-body">
                                        <h2 class="text-bold-700">
                                        <?= $currency ?>
                                        <?= number_format($statistic->row('shipmenttranscount') / 100, 2, ".", ".") ?>
                                        </h2>
                                        <p class="mb-0 line-ellipsis">Shipment Transaction Value</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3 col-12">
                            <div class="card text-center">
                                <div class="card-content">
                                    <div class="card-body">
                                        <h2 class="text-bold-700">
                                        <?= $currency ?>
                                        <?= number_format($statistic->row('rentaltranscount') / 100, 2, ".", ".") ?>
                                        </h2>
                                        <p class="mb-0 line-ellipsis">Rental Transaction Value</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3 col-12">
                            <div class="card text-center">
                                <div class="card-content">
                                    <div class="card-body">
                                        <h2 class="text-bold-700">
                                        <?= $currency ?>
                                        <?= number_format($statistic->row('purchasingtranscount') / 100, 2, ".", ".") ?>
                                        </h2>
                                        <p class="mb-0 line-ellipsis">Purchasing Service Transaction Value</p>
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
                                        <h4>
                                        <?= $currency ?>
                                        <?= number_format($valuepassanger->row('successpassangercount') / 100, 2, ".", ".") ?>
                                        </h4>
                                    </div>
                                    <div class="stastics-info text-right">
                                        <span>
                                        <?= $currency ?>
                                        <?= number_format($valuepassanger->row('successpassangercountdaily') / 100, 2, ".", ".") ?>
                                        </span>
                                        <span class="text-muted d-block">today</span>
                                    </div>
                                </div>                           

                                <div class="d-flex justify-content-between mb-25">
                                    <div class="browser-info">
                                        <p class="mb-25">Canceled</p>
                                        <h4>
                                        <?= $currency ?>
                                        <?= number_format($valuepassanger->row('cancelpassangercount') / 100, 2, ".", ".") ?>
                                        </h4>
                                    </div>
                                    <div class="stastics-info text-right">
                                        <span>
                                        <?= $currency ?>
                                        <?= number_format($valuepassanger->row('cancelpassangercountdaily') / 100, 2, ".", ".") ?>
                                        </span>
                                        <span class="text-muted d-block">today</span>
                                    </div>
                                </div>

                                <div class="d-flex justify-content-between mb-25">
                                    <div class="browser-info">
                                        <p class="mb-25">No Driver</p>
                                        <h4>
                                        <?= $currency ?>
                                        <?= number_format($valuepassanger->row('nodriverpassangercount') / 100, 2, ".", ".") ?>
                                        </h4>
                                    </div>
                                    <div class="stastics-info text-right">
                                        <span>
                                        <?= $currency ?>
                                        <?= number_format($valuepassanger->row('nodriverpassangercountdaily') / 100, 2, ".", ".") ?>
                                        </span>
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
                                        <h4>
                                        <?= $currency ?>
                                        <?= number_format($valueshipment->row('successshipmentcount') / 100, 2, ".", ".") ?>
                                        </h4>
                                    </div>
                                    <div class="stastics-info text-right">
                                        <span>
                                        <?= $currency ?>
                                        <?= number_format($valueshipment->row('successshipmentcountdaily') / 100, 2, ".", ".") ?>
                                        </span>
                                        <span class="text-muted d-block">today</span>
                                    </div>
                                </div>                           

                                <div class="d-flex justify-content-between mb-25">
                                    <div class="browser-info">
                                        <p class="mb-25">Canceled</p>
                                        <h4>
                                        <?= $currency ?>
                                        <?= number_format($valueshipment->row('cancelshipmentcount') / 100, 2, ".", ".") ?>
                                        </h4>
                                    </div>
                                    <div class="stastics-info text-right">
                                        <span>
                                        <?= $currency ?>
                                        <?= number_format($valueshipment->row('cancelshipmentcountdaily') / 100, 2, ".", ".") ?>
                                        </span>
                                        <span class="text-muted d-block">today</span>
                                    </div>
                                </div>

                                <div class="d-flex justify-content-between mb-25">
                                    <div class="browser-info">
                                        <p class="mb-25">No Driver</p>
                                        <h4>
                                        <?= $currency ?>
                                        <?= number_format($valueshipment->row('nodrivershipmentcount') / 100, 2, ".", ".") ?>
                                        </h4>
                                    </div>
                                    <div class="stastics-info text-right">
                                        <span>
                                        <?= $currency ?>
                                        <?= number_format($valueshipment->row('nodrivershipmentcountdaily') / 100, 2, ".", ".") ?>
                                        </span>
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
                                        <h4>
                                        <?= $currency ?>
                                        <?= number_format($valuerental->row('successrentalcount') / 100, 2, ".", ".") ?>
                                        </h4>
                                    </div>
                                    <div class="stastics-info text-right">
                                        <span>
                                        <?= $currency ?>
                                        <?= number_format($valuerental->row('successrentalcountdaily') / 100, 2, ".", ".") ?>
                                        </span>
                                        <span class="text-muted d-block">today</span>
                                    </div>
                                </div>                           

                                <div class="d-flex justify-content-between mb-25">
                                    <div class="browser-info">
                                        <p class="mb-25">Canceled</p>
                                        <h4>
                                        <?= $currency ?>
                                        <?= number_format($valuerental->row('cancelrentalcount') / 100, 2, ".", ".") ?>
                                        </h4>
                                    </div>
                                    <div class="stastics-info text-right">
                                        <span>
                                        <?= $currency ?>
                                        <?= number_format($valuerental->row('cancelrentalcountdaily') / 100, 2, ".", ".") ?>
                                        </span>
                                        <span class="text-muted d-block">today</span>
                                    </div>
                                </div>

                                <div class="d-flex justify-content-between mb-25">
                                    <div class="browser-info">
                                        <p class="mb-25">No Driver</p>
                                        <h4>
                                        <?= $currency ?>
                                        <?= number_format($valuerental->row('nodriverrentalcount') / 100, 2, ".", ".") ?>
                                        </h4>
                                    </div>
                                    <div class="stastics-info text-right">
                                        <span>
                                        <?= $currency ?>
                                        <?= number_format($valuerental->row('nodriverrentalcountdaily') / 100, 2, ".", ".") ?>
                                        </span>
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
                                        <h4>
                                        <?= $currency ?>
                                        <?= number_format($valuepurchasing->row('successpurchasingcount') / 100, 2, ".", ".") ?>
                                        </h4>
                                    </div>
                                    <div class="stastics-info text-right">
                                        <span>
                                        <?= $currency ?>
                                        <?= number_format($valuepurchasing->row('successpurchasingcountdaily') / 100, 2, ".", ".") ?>
                                        </span>
                                        <span class="text-muted d-block">today</span>
                                    </div>
                                </div>                           

                                <div class="d-flex justify-content-between mb-25">
                                    <div class="browser-info">
                                        <p class="mb-25">Canceled</p>
                                        <h4>
                                        <?= $currency ?>
                                        <?= number_format($valuepurchasing->row('cancelpurchasingcount') / 100, 2, ".", ".") ?>
                                        </h4>
                                    </div>
                                    <div class="stastics-info text-right">
                                        <span>
                                        <?= $currency ?>
                                        <?= number_format($valuepurchasing->row('cancelpurchasingcountdaily') / 100, 2, ".", ".") ?>
                                        </span>
                                        <span class="text-muted d-block">today</span>
                                    </div>
                                </div>

                                <div class="d-flex justify-content-between mb-25">
                                    <div class="browser-info">
                                        <p class="mb-25">No Driver</p>
                                        <h4>
                                        <?= $currency ?>
                                        <?= number_format($valuepurchasing->row('nodriverpurchasingcount') / 100, 2, ".", ".") ?>
                                        </h4>
                                    </div>
                                    <div class="stastics-info text-right">
                                        <span>
                                        <?= $currency ?>
                                        <?= number_format($valuepurchasing->row('nodriverpurchasingcountdaily') / 100, 2, ".", ".") ?>
                                        </span>
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