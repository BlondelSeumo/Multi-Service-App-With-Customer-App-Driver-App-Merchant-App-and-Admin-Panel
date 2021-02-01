<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- withdraw data start -->
            <section id="data-thumb-view" class="data-thumb-view-header">
                <div class="card-header">
                    <h4>Services<span><a class="btn btn-success float-right mb-1 text-white" href="<?= base_url(); ?>service/addservice">
                                <i class="feather icon-plus mr-1"></i>Add Service</a></span></h4>
                </div>
                <div class="table-responsive">
                    <table class="table data-thumb-view">
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>Service</th>
                                <th>Icon</th>
                                <th>Price</th>
                                <th>Discount</th>
                                <th>Unit</th>
                                <th>Comission</th>
                                <th>Minimum Price</th>
                                <th>Driver Radius</th>
                                <th>Max Distance Order</th>
                                <th>Minimum Wallet</th>
                                <th>Job</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <?php $i = 1;
                            foreach ($service as $srvc) { ?>
                                <tr>
                                    <td class="product-name"><?= $i ?></td>
                                    <td class="product-name"><?= $srvc['service']; ?></td>
                                    <td class="product-img">
                                        <div class="badge badge-primary">
                                            <img src="<?= base_url('images/service/') . $srvc['icon']; ?>">
                                        </div>
                                    </td>
                                    <td class="product-name">
                                        <?= $currency ?>
                                        <?= number_format($srvc['cost'] / 100, 2, ".", ".") ?>
                                    </td>
                                    <td class="product-name"><?= $srvc['voucher_discount']; ?>%</td>
                                    <td class="product-name">/<?= $srvc['cost_desc']; ?></td>
                                    <td class="product-name"><?= $srvc['commision']; ?>
                                        %</td>
                                    <td class="product-name"><?= $currency ?>
                                        <?= number_format($srvc['minimum_cost'] / 100, 2, ".", ".") ?></td>
                                    <td class="product-name"><?= $srvc['minimum_distance']; ?>km</td>
                                    <td class="product-name"><?= $srvc['maks_distance']; ?>km</td>
                                    <td class="product-name"><?= $currency ?>
                                        <?= number_format($srvc['minimum_wallet'] / 100, 2, ".", ".") ?></td>
                                    <?php foreach ($driverjob as $dj) {
                                        if ($srvc['driver_job'] == $dj['id']) { ?>
                                            <td class="product-name"><?= $dj['driver_job']; ?></td>
                                    <?php }
                                    } ?>

                                    <td>
                                        <?php if ($srvc['active'] == 1) { ?>
                                            <label class="badge badge-success">Active</label>
                                        <?php } else { ?>
                                            <label class="badge badge-danger">Non Active</label>
                                        <?php } ?>
                                    </td>
                                    <td>
                                        <span class="action-edit mr-1">
                                            <a href="<?= base_url(); ?>service/editservice/<?= $srvc['service_id']; ?>">
                                                <i class="feather icon-edit text-info"></i>
                                            </a>
                                        </span>
                                        <span class="action-delete mr-1">
                                            <span class="action-delete mr-1">
                                                <a onclick="return confirm ('Are You Sure?')" href="<?= base_url(); ?>service/deleteservice/<?= $srvc['service_id']; ?>">
                                                    <i class="feather icon-trash text-danger"></i></a>
                                            </span>
                                    </td>
                                <?php $i++;
                            } ?>
                        </tbody>
                    </table>
                </div>

            </section>
            <!-- end of withdraw data -->
        </div>
    </div>
</div>
<!-- END: Content-->