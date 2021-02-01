<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- Data list view starts -->
            <section id="data-thumb-view" class="data-thumb-view-header">

                <div class="card-header">
                    <h4>Promo Code Data <span><a class="btn btn-success float-right mb-1 text-white" href="<?= base_url(); ?>promotion/addpromocode">
                                <i class="feather icon-plus mr-1"></i>Add Promo Code</a></span></h4>
                </div>
                <!-- Promo code Table starts -->
                <div class="table-responsive">
                    <table class="table data-thumb-view">
                        <thead>
                            <tr>
                                <th></th>
                                <th>No</th>
                                <th>Image</th>
                                <th>Title</th>
                                <th>Promo Code</th>
                                <th>Discount</th>
                                <th>Service</th>
                                <th>Expired</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <?php $i = 1;
                            foreach ($promocode as $prmcd) { ?>
                                <tr>
                                    <td></td>
                                    <td><?= $i ?></td>
                                    <td class="product-img">
                                        <img src="<?= base_url('images/promo/') . $prmcd['promo_image']; ?>">
                                    </td>
                                    <td class="product-name"><?= $prmcd['promo_title']; ?></td>
                                    <td class="text-primary"><?= $prmcd['promo_code']; ?></td>

                                    <?php if ($prmcd['promo_type'] == 'persen') { ?>
                                        <td class="text-danger"><?= $prmcd['promo_amount']; ?>%</td>
                                    <?php } else { ?>
                                        <td class="text-danger">$<?= number_format($prmcd['promo_amount'] / 100, 2, ".", "."); ?></td>
                                    <?php } ?>

                                    <td class="product-name"><?= $prmcd['service']; ?></td>
                                    <td class="product-name"><?= $prmcd['expired']; ?></td>
                                    <td>
                                        <?php if ($prmcd['status'] == 1) { ?>
                                            <label class="badge badge-success">Active</label>
                                        <?php } else { ?>
                                            <label class="badge badge-danger">Non Active</label>
                                        <?php } ?>
                                    </td>
                                    <td>
                                        <span class="action-edit mr-1">
                                            <a href="<?= base_url(); ?>promotion/editpromocode/<?= $prmcd['promo_id']; ?>">
                                                <i class="feather icon-edit text-info"></i>
                                            </a>
                                        </span>
                                        <span class="action-delete mr-1">
                                            <a onclick="return confirm ('Are You Sure?')" href="<?= base_url(); ?>promotion/deletepromocode/<?= $prmcd['promo_id']; ?>">
                                                <i class="feather icon-trash text-danger"></i></a>
                                        </span>
                                    </td>
                                </tr>
                            <?php $i++;
                            } ?>
                        </tbody>
                    </table>
                </div>
                <!-- promo code Table ends -->
            </section>
            <!-- Data list view end -->
        </div>
    </div>
</div>
<!-- END: Content-->