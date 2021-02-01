<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- start detail transaction -->
            <div class="row">

                <div class="col-lg-4 col-sm-12">
                    <div class="card">
                        <div class="card-header justify-content-right">
                            <section class="invoice-print mb-1">
                                <div class=" d-flex flex-column flex-md-row floating-right">
                                    <button class="btn btn-primary btn-print mb-1 mb-md-0">
                                        <i class="feather icon-file-text"></i>
                                        Print</button>

                                    <?php if ($transaction['status'] != 5 and $transaction['status'] != 4 and $transaction['status'] != 0) { ?>
                                        <a href="<?= base_url(); ?>detailtransaction/cancletransaction/<?= $transaction['id'] ?>/<?= $transaction['driver_id'] ?>" class="btn btn-danger ml-1">
                                            Cancel</a>
                                    <?php } ?>
                                </div>
                            </section>
                        </div>
                        <div class="card-content mt-2">
                            <div class="card-body">

                                <?php if ($transaction['home'] == 4) { ?>
                                    <ul class="activity-timeline timeline-left list-unstyled">
                                        <li>
                                            <div class="timeline-icon bg-success">
                                                <i class="feather icon-user font-medium-2"></i>
                                            </div>
                                            <div class="avatar mr-1 ">
                                                <img src="<?= base_url('images/customer/') . $transaction['customer_image']; ?>" alt="avtar img holder" height="32" width="32">
                                            </div>
                                            <div class="timeline-info">
                                                <p class="font-weight-bold"><?= $transaction['customer_fullname'] ?></p>
                                                <span><?= $transaction['customer_id'] ?></span>
                                            </div>
                                            <small class="">
                                                <i class="feather icon-smartphone"></i>+<?= $transaction['telepon_pelanggan'] ?></small>
                                        </li>
                                        <?php if ($transaction['status'] == 0) { ?>

                                            <li>
                                                <div class="timeline-icon bg-info">
                                                    <i class="feather icon-truck font-medium-2"></i>
                                                </div>
                                                <div class="timeline-info">
                                                    <p class="font-weight-bold">No Driver!</p>
                                                    <span>---------</span>
                                                </div>
                                                <small class="">
                                                    ----------</small>
                                            </li>

                                        <?php } else { ?>

                                            <li>
                                                <div class="timeline-icon bg-info">
                                                    <i class="feather icon-truck font-medium-2"></i>
                                                </div>
                                                <div class="avatar mr-1 ">
                                                    <img src="<?= base_url('images/driverphoto/') . $transaction['photo']; ?>" alt="avtar img holder" height="32" width="32">
                                                </div>
                                                <div class="timeline-info">
                                                    <p class="font-weight-bold"><?= $transaction['driver_name'] ?></p>
                                                    <span><?= $transaction['driver_id'] ?></span>
                                                </div>
                                                <small class="">
                                                    <i class="feather icon-smartphone"></i>+<?= $transaction['phone_number'] ?></small>
                                            </li>

                                        <?php } ?>
                                        <li>
                                            <div class="timeline-icon bg-warning">
                                                <i class="feather icon-shopping-bag font-medium-2"></i>
                                            </div>
                                            <div class="avatar mr-1 ">
                                                <img src="<?= base_url('images/merchant/') . $transaction['merchant_image']; ?>" alt="avtar img holder" height="32" width="32">
                                            </div>
                                            <div class="timeline-info">
                                                <p class="font-weight-bold"><?= $transaction['merchant_name'] ?></p>
                                                <span><?= $transaction['merchant_id'] ?></span>
                                            </div>
                                            <small class="">
                                                <i class="feather icon-smartphone"></i>+<?= $transaction['merchant_telephone_number'] ?></small>
                                        </li>
                                    </ul>

                                <?php } else { ?>

                                    <ul class="activity-timeline timeline-left list-unstyled">
                                        <li>
                                            <div class="timeline-icon bg-success">
                                                <i class="feather icon-user font-medium-2"></i>
                                            </div>
                                            <div class="avatar mr-1 ">
                                                <img src="<?= base_url('images/customer/') . $transaction['customer_image']; ?>" alt="avtar img holder" height="32" width="32">
                                            </div>
                                            <div class="timeline-info">
                                                <p class="font-weight-bold"><?= $transaction['customer_fullname'] ?></p>
                                                <span><?= $transaction['customer_id'] ?></span>
                                            </div>
                                            <small class="">
                                                <i class="feather icon-smartphone"></i>+<?= $transaction['telepon_pelanggan'] ?></small>
                                        </li>
                                        <?php if ($transaction['status'] == 0) { ?>

                                            <li>
                                                <div class="timeline-icon bg-info">
                                                    <i class="feather icon-truck font-medium-2"></i>
                                                </div>
                                                <div class="timeline-info">
                                                    <p class="font-weight-bold">No Driver!</p>
                                                    <span>---------</span>
                                                </div>
                                                <small class="">
                                                    ----------</small>
                                            </li>

                                        <?php } else { ?>

                                            <li>
                                                <div class="timeline-icon bg-info">
                                                    <i class="feather icon-truck font-medium-2"></i>
                                                </div>
                                                <div class="avatar mr-1 ">
                                                    <img src="<?= base_url('images/driverphoto/') . $transaction['photo']; ?>" alt="avtar img holder" height="32" width="32">
                                                </div>
                                                <div class="timeline-info">
                                                    <p class="font-weight-bold"><?= $transaction['driver_name'] ?></p>
                                                    <span><?= $transaction['driver_id'] ?></span>
                                                </div>
                                                <small class="">
                                                    <i class="feather icon-smartphone"></i>+<?= $transaction['phone_number'] ?></small>
                                            </li>

                                        <?php } ?>
                                    </ul>

                                <?php } ?>
                            </div>
                        </div>
                    </div>

                    <!-- start detail for passanger & shipment service -->
                    <?php if ($transaction['home'] == 1 || $transaction['home'] == 2) { ?>
                        <div class="card">
                            <div class="card-content">
                                <div class="card-body">
                                    <ul class="activity-timeline timeline-left list-unstyled">
                                        <li>
                                            <div class="timeline-icon bg-info">
                                                <i class="feather icon-map-pin font-medium-2"></i>
                                            </div>
                                            <div class="timeline-info">
                                                <p class="font-weight-bold">Pick Up</p>
                                                <span><?= $transaction['pickup_address'] ?></span>
                                            </div>
                                            <small class="">order time:
                                                <?= $transaction['order_time'] ?></small>
                                        </li>
                                        <li>

                                            <div class="timeline-info">
                                                <span><?= $transaction['service'] ?></span>
                                            </div>
                                            <small class="">
                                                <?php {
                                                    $distance = $transaction['distance'];
                                                    $jarakbulat = number_format($distance, 1);
                                                    echo $jarakbulat;
                                                    echo ' ';
                                                    echo $transaction['cost_desc'];
                                                } ?>
                                            </small>
                                        </li>
                                        <li>
                                            <div class="timeline-icon bg-success">
                                                <i class="feather icon-map-pin font-medium-2"></i>
                                            </div>
                                            <div class="timeline-info">
                                                <p class="font-weight-bold">Drop Point</p>
                                                <span><?= $transaction['destination_address'] ?>
                                                </span>
                                            </div>
                                            <small class="">finish time:
                                                <?= $transaction['finish_time'] ?></small>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    <?php } ?>
                    <!-- start detail for passanger & shipment service -->

                    <!-- start detail for rental service -->
                    <?php if ($transaction['home'] == 3) { ?>
                        <div class="card">
                            <div class="card-content">
                                <div class="card-body">
                                    <ul class="activity-timeline timeline-left list-unstyled">
                                        <li>
                                            <div class="timeline-icon bg-info">
                                                <i class="feather icon-map-pin font-medium-2"></i>
                                            </div>
                                            <div class="timeline-info">
                                                <p class="font-weight-bold">Pick Up</p>
                                                <span><?= $transaction['pickup_address'] ?></span>
                                            </div>
                                            <small class="">order time:
                                                <?= $transaction['order_time'] ?></small>
                                        </li>
                                        <li>

                                            <div class="timeline-info">
                                                <span><?= $transaction['service'] ?></span>
                                            </div>
                                            <small class=""><?= $transaction['estimate_time'] ?></small>
                                        </li>
                                        <li>
                                            <div class="timeline-icon bg-success">
                                                <i class="feather icon-check font-medium-2"></i>
                                            </div>
                                            <div class="timeline-info">
                                                <p class="font-weight-bold">Complete</p>
                                            </div>
                                            <small class=""><?= $transaction['finish_time'] ?></small>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    <?php } ?>
                    <!-- end detail for rental service -->

                    <!-- start detail for purchasing service -->
                    <?php if ($transaction['home'] == 4) { ?>
                        <div class="card">
                            <div class="card-content">
                                <div class="card-body">
                                    <ul class="activity-timeline timeline-left list-unstyled">
                                        <li>
                                            <div class="timeline-icon bg-info">
                                                <i class="feather icon-map-pin font-medium-2"></i>
                                            </div>
                                            <div class="timeline-info">
                                                <p class="font-weight-bold">Merchant Location</p>
                                                <span><?= $transaction['pickup_address'] ?></span>
                                            </div>
                                            <small class=""><?= $transaction['order_time'] ?></small>
                                        </li>
                                        <li>

                                            <div class="timeline-info">
                                                <span><?= $transaction['service'] ?></span>
                                            </div>
                                            <small class=""><?php {
                                                                $distance = $transaction['distance'];
                                                                $jarakbulat = number_format($distance, 1);
                                                                echo $jarakbulat;
                                                                echo ' ';
                                                                echo $transaction['cost_desc'];
                                                            } ?></small>
                                        </li>
                                        <li>
                                            <div class="timeline-icon bg-success">
                                                <i class="feather icon-check font-medium-2"></i>
                                            </div>
                                             <div class="timeline-info">
                                                <p class="font-weight-bold">Drop Point</p>
                                                <span><?= $transaction['destination_address'] ?>
                                                </span>
                                            </div>
                                            <small class=""><?= $transaction['finish_time'] ?></small>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    <?php } ?>
                    <!-- end detail for purchasing service -->

                </div>

                <?php if ($transaction['home'] == 4) { ?>

                    <!-- start invoice for purchasing service -->
                    <div class="col-lg-8 col-sm-12">
                        <section class="card invoice-page">
                            <div id="invoice-template" class="card-body">

                                <div id="invoice-company-details" class="row">
                                    <div class="col-sm-6 col-12 text-left pt-1">
                                        <div class="media pt-1">
                                            <img src="<?= base_url(); ?>images/icon/logotext.png" alt="avatar" height="40" width="150">
                                        </div>
                                    </div>
                                    <div class="col-sm-6 col-12 text-right">
                                        <h1>Invoice</h1>
                                        <div class="invoice-details mt-2">
                                            <h6>INVOICE NO.</h6>
                                            <p>#INV-<?= $transaction['id'] ?></p>
                                            <h6 class="mt-2">User Review</h6>
                                            <p>
                                                <?= $transaction['note'] ?>
                                                <span class="ml-2">
                                                    <i class="feather icon-star text-warning"></i><?= $transaction['rate'] ?></span></p>
                                        </div>
                                    </div>
                                </div>

                                <div id="invoice-items-details" class="pt-1 invoice-items-table">
                                    <div class="row">
                                        <div class="table-responsive col-12">
                                            <table class="table table-borderless">
                                                <thead>
                                                    <tr>
                                                        <th>ITEM NAME</th>
                                                        <th>QTY</th>
                                                        <th>AMOUNT</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <tr>
                                                        <td>
                                                            <?php foreach ($itemtransaction as $item) { ?>
                                                                <ul class="list-group list-group-flush">
                                                                    <li class="list-group-item"><?= $item['item_name'] ?></li>
                                                                </ul>
                                                            <?php } ?>
                                                        </td>
                                                        <td>
                                                            <?php foreach ($itemtransaction as $item) { ?>
                                                                <ul class="list-group list-group-flush">
                                                                    <li class="list-group-item"><?= $item['item_amount'] ?></li>
                                                                </ul>
                                                            <?php } ?>
                                                        </td>
                                                        
                                                        <td>
                                                            <?php foreach ($itemtransaction as $item) { ?>
                                                                <ul class="list-group list-group-flush">
                                                                    <li class="list-group-item">
                                                                        <?= $currency ?>
                                                                        <?= number_format($item['total_cost'] / 100, 2, ".", ".") ?>
                                                                    </li>
                                                                </ul>
                                                            <?php } ?>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>

                                <div id="invoice-items-details" class="pt-1 invoice-items-table">
                                    <div class="row">
                                        <div class="table-responsive col-12">
                                            <table class="table table-borderless">
                                                <thead>
                                                    <tr>
                                                        <th>SERVICE</th>
                                                        <th>UNIT COST</th>
                                                        <th>DISTANCE/DURATION</th>
                                                        <th>AMOUNT</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <tr>
                                                        <td>DELIVERY</td>
                                                        <td>
                                                            <?= $currency ?>
                                                            <?= number_format($transaction['cost'] / 100, 2, ".", ".") ?>
                                                        </td>
                                                        <td>
                                                            <?php if ($transaction['home'] != '2') { ?>

                                                                <?php if ($transaction['home'] == '3') {
                                                                    if ($transaction['distance'] == '0') {
                                                                        echo $transaction['estimate_time'];
                                                                    }
                                                                } else {
                                                                    $distance = $transaction['distance'];
                                                                    $jarakbulat = number_format($distance, 1);
                                                                    echo $jarakbulat;
                                                                    echo ' ';
                                                                    echo $transaction['cost_desc'];
                                                                } ?>

                                                            <?php } ?>
                                                        </td>
                                                        <td>
                                                            <?= $currency ?>
                                                            <?= number_format($transaction['price'] / 100, 2, ".", ".") ?>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>

                                <div id="invoice-total-details" class="invoice-total-table">
                                    <div class="row">
                                        <div class="col-7 offset-5">
                                            <div class="table-responsive">
                                                <table class="table table-borderless">
                                                    <tbody>
                                                        <tr>
                                                            <th>ITEM PRICE</th>
                                                            <td><?= $currency ?>
                                                                <?= number_format($transaction['total_belanja'] / 100, 2, ".", ".") ?></td>
                                                        </tr>
                                                        <tr>
                                                            <th>DELIVERY</th>
                                                            <td><?= $currency ?>
                                                                <?= number_format($transaction['price'] / 100, 2, ".", ".") ?></td>
                                                        </tr>
                                                        <tr>
                                                            <th>DISCOUNT
                                                                <span class="text-danger">
                                                                    (<?php if ($transaction['wallet_payment'] == '1') {
                                                                            echo $transaction['voucher_discount'];
                                                                        } else {
                                                                            echo 0;
                                                                        } ?>
                                                                    %)
                                                                </span>

                                                            </th>
                                                            <td class="text-danger">
                                                                <?= $currency ?>
                                                                <?= number_format($transaction['promo_discount'] / 100, 2, ".", ".") ?>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <th>PAYMENT</th>
                                                            <td>
                                                                <?php if ($transaction['wallet_payment'] == '0') { ?>
                                                                    <span class="badge badge-success"><?= 'CASH'; ?>
                                                                    <?php } else { ?>
                                                                        <span class="badge badge-primary"><?= 'wallet';
                                                                                                        } ?>
                                                                        </span>
                                                        </tr>
                                                        <tr>
                                                            <th>STATUS</th>
                                                            <td>
                                                                <?php if ($transaction['status'] == '0') { ?>
                                                                    <p class="badge badge-danger">No Driver!</p>
                                                                <?php } ?>
                                                                <?php if ($transaction['status'] == '1') { ?>
                                                                    <p class="badge badge-info"><?= $transaction['transaction_status'] ?></p>
                                                                <?php } ?>
                                                                <?php if ($transaction['status'] == '2') { ?>
                                                                    <p class="badge badge-primary"><?= $transaction['transaction_status'] ?></p>
                                                                <?php } ?>
                                                                <?php if ($transaction['status'] == '3') { ?>
                                                                    <p class="badge badge-primary"><?= $transaction['transaction_status'] ?></p>
                                                                <?php } ?>
                                                                <?php if ($transaction['status'] == '4') { ?>
                                                                    <p class="badge badge-success"><?= $transaction['transaction_status'] ?></p>
                                                                <?php } ?>
                                                                <?php if ($transaction['status'] == '5') { ?>
                                                                    <p class="badge badge-warning"><?= $transaction['transaction_status'] ?></p>
                                                                <?php } ?>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <th>TOTAL</th>
                                                            <td class="card-title"><?= $currency ?>
                                                                <?= number_format($transaction['final_cost'] / 100, 2, ".", ".") ?></td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </section>

                    </div>
                    <!-- end of infoice for purchasing service -->

                <?php } else { ?>

                    <!-- start Invoice for passanger, shipment, rental service -->
                    <div class="col-lg-8 col-sm-12">
                        <section class="card invoice-page">
                            <div id="invoice-template" class="card-body">

                                <div id="invoice-company-details" class="row">
                                    <div class="col-sm-6 col-12 text-left pt-1">
                                        <div class="media pt-1">
                                        <img  src="<?= base_url(); ?>images/icon/logotext.png" alt="avatar" height="40" width="150">
                                        </div>
                                    </div>
                                    <div class="col-sm-6 col-12 text-right">
                                        <h1>Invoice</h1>
                                        <div class="invoice-details mt-2">
                                            <h6>INVOICE NO.</h6>
                                            <p>#INV-<?= $transaction['id'] ?></p>
                                            <h6 class="mt-2">User Review</h6>
                                            <p>
                                                <?= $transaction['note'] ?>
                                                <span class="ml-2">
                                                    <i class="feather icon-star text-warning"></i><?= $transaction['rate'] ?></span></p>
                                        </div>
                                    </div>
                                </div>

                                <div id="invoice-items-details" class="pt-1 invoice-items-table">
                                    <div class="row">
                                        <div class="table-responsive col-12">
                                            <table class="table table-borderless">
                                                <thead>
                                                    <tr>
                                                        <th>SERVICE</th>
                                                        <th>UNIT COST</th>
                                                        <th>DISTANCE/DURATION</th>
                                                        <th>AMOUNT</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <tr>
                                                        <td><?= $transaction['service'] ?></td>
                                                        <td>
                                                            <?= $currency ?>
                                                            <?= number_format($transaction['cost'] / 100, 2, ".", ".") ?>
                                                        </td>
                                                        <td>

                                                            <?php if ($transaction['home'] == '3') {
                                                                if ($transaction['distance'] == '0') {
                                                                    echo $transaction['estimate_time'];
                                                                }
                                                            } else {
                                                                $distance = $transaction['distance'];
                                                                $jarakbulat = number_format($distance, 1);
                                                                echo $jarakbulat;
                                                                echo ' ';
                                                                echo $transaction['cost_desc'];
                                                            } ?>

                                                        <td>
                                                            <?= $currency ?>
                                                            <?= number_format($transaction['price'] / 100, 2, ".", ".") ?>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>

                                <div id="invoice-total-details" class="invoice-total-table">
                                    <div class="row">
                                        <div class="col-7 offset-5">
                                            <div class="table-responsive">
                                                <table class="table table-borderless">
                                                    <tbody>
                                                        <tr>
                                                            <th>SUBTOTAL</th>
                                                            <td><?= $currency ?>
                                                                <?= number_format($transaction['price'] / 100, 2, ".", ".") ?></td>
                                                        </tr>
                                                        <tr>
                                                            <th>DISCOUNT
                                                                <span class="text-danger">
                                                                    (<?php if ($transaction['wallet_payment'] == '1') {
                                                                            echo $transaction['voucher_discount'];
                                                                        } else {
                                                                            echo 0;
                                                                        } ?>
                                                                    %)
                                                                </span>
                                                            </th>
                                                            <td class="text-danger">
                                                                <?= $currency ?>
                                                                <?= number_format($transaction['promo_discount'] / 100, 2, ".", ".") ?>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <th>PAYMENT</th>
                                                            <td>
                                                                <?php if ($transaction['wallet_payment'] == '0') { ?>
                                                                    <span class="badge badge-success"><?= 'CASH'; ?>
                                                                    <?php } else { ?>
                                                                        <span class="badge badge-primary"><?= 'wallet';
                                                                                                        } ?>
                                                                        </span>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <th>STATUS</th>
                                                            <td>
                                                                <?php if ($transaction['status'] == '0') { ?>
                                                                    <p class="badge badge-danger">No Driver!</p>
                                                                <?php } ?>
                                                                <?php if ($transaction['status'] == '1') { ?>
                                                                    <p class="badge badge-info"><?= $transaction['transaction_status'] ?></p>
                                                                <?php } ?>
                                                                <?php if ($transaction['status'] == '2') { ?>
                                                                    <p class="badge badge-primary"><?= $transaction['transaction_status'] ?></p>
                                                                <?php } ?>
                                                                <?php if ($transaction['status'] == '3') { ?>
                                                                    <p class="badge badge-primary"><?= $transaction['transaction_status'] ?></p>
                                                                <?php } ?>
                                                                <?php if ($transaction['status'] == '4') { ?>
                                                                    <p class="badge badge-success"><?= $transaction['transaction_status'] ?></p>
                                                                <?php } ?>
                                                                <?php if ($transaction['status'] == '5') { ?>
                                                                    <p class="badge badge-warning"><?= $transaction['transaction_status'] ?></p>
                                                                <?php } ?>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <th>TOTAL</th>
                                                            <td class="card-title">
                                                                <?= $currency ?>
                                                                <?= number_format($transaction['final_cost'] / 100, 2, ".", ".") ?>
                                                            </td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </section>

                    </div>
                    <!-- end of Invoice for passanger, shipment, rental service -->

                <?php } ?>

            </div>

            <!-- end of detail transaction-->
        </div>
    </div>
</div>
<!-- END: Content-->