<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- new registration merchant data Start -->
            <section id="column-selectors">
                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">New Registration Merchant</h4>
                            </div>
                            <div class="card-content">
                                <div class="card-body card-dashboard">
                                    <div class="table-responsive">
                                        <table class="table zero-configuration">
                                            <thead>
                                                <tr>
                                                    <th>No</th>
                                                    <th>Merchant Id</th>
                                                    <th>Merchant Pic</th>
                                                    <th>Owners Name</th>
                                                    <th>Phone</th>
                                                    <th>Merchant Name</th>
                                                    <th>Service</th>
                                                    <th>Category</th>
                                                    <th>Status</th>
                                                    <th>Actions</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <?php $i = 1;
                                                foreach ($merchant as $mrc) {
                                                    if ($mrc['partner_status'] == 0) { ?>
                                                        <tr>
                                                            <td>
                                                                <?= $i ?>
                                                            </td>
                                                            <td><?= $mrc['partner_id'] ?></td>
                                                            <td>
                                                                <img class="round" style="width: 40px; height: 40px;" src="<?= base_url('images/merchant/') . $mrc['merchant_image']; ?>">
                                                            </td>
                                                            <td><?= $mrc['partner_name'] ?></td>
                                                            <td><?= $mrc['partner_telephone'] ?></td>
                                                            <td><?= $mrc['merchant_name'] ?></td>
                                                            <td><?= $mrc['service'] ?></td>
                                                            <td><?= $mrc['category_name'] ?></td>
                                                            <td>
                                                                <?php if ($mrc['partner_status'] == 0) { ?>
                                                                    <label class="badge badge-secondary text-dark">Newreg</label>
                                                                <?php } else { ?>
                                                                    <label class="badge badge-primary">Active</label>
                                                                <?php } ?>
                                                            </td>
                                                            <td>
                                                                <span class="mr-1">
                                                                    <a href="<?= base_url(); ?>detailuser/detailmerchant/<?= $mrc['partner_id'] ?>">
                                                                        <i class="feather icon-eye text-success"></i>
                                                                    </a>
                                                                </span>

                                                                <span class="action-edit mr-1">
                                                                    <a onclick="return confirm ('Are You Sure?')" href="<?= base_url(); ?>user/deletemerchant/<?= $mrc['partner_id']; ?>">
                                                                        <i class="feather icon-trash text-danger"></i></a>
                                                                </span>
                                                            </td>
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
            <!-- end of new registration merchant data table -->

            <!-- end of customer data -->
        </div>
    </div>
</div>
<!-- END: Content-->