<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- merchant data Start -->
            <!-- merchant tabs start -->
            <section id="basic-tabs-components">
                <div class="row">
                    <div class="col-sm-12">
                        <div class="card overflow-hidden">
                            <div class="card-header">
                                <h4 class="card-title">Merchant Data</h4>
                            </div>
                            <div class="card-content">
                                <div class="card-body">
                                    <a class="btn btn-success mb-1 text-white" href="<?= base_url(); ?>user/addmerchant">
                                        <i class="feather icon-plus mr-1"></i>Add Merchant</a>
                                    <ul class="nav nav-tabs" role="tablist">
                                        <li class="nav-item">
                                            <a class="nav-link active" id="allmerchant-tab" data-toggle="tab" href="#allmerchant" aria-controls="allmerchant" role="tab" aria-selected="true">All Merchants</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" id="activemerchant-tab" data-toggle="tab" href="#activemerchant" aria-controls="activemerchant" role="tab" aria-selected="false">Active Merchant</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" id="suspendedmerchant-tab" data-toggle="tab" href="#suspendedmerchant" aria-controls="suspendedmerchant" role="tab" aria-selected="false">Suspended Merchant</a>
                                        </li>
                                    </ul>
                                    <div class="tab-content">
                                        <div class="tab-pane active" id="allmerchant" aria-labelledby="allmerchant-tab" role="tabpanel">
                                            <!-- start all merchant data table -->
                                            <section id="column-selectors">
                                                <div class="row">
                                                    <div class="col-12">
                                                        <div class="card">
                                                            <div class="card-header">
                                                                <h4 class="card-title">All Merchants Data</h4>
                                                            </div>
                                                            <div class="card-content">
                                                                <div class="card-body card-dashboard">
                                                                    <div class="table-responsive">
                                                                        <table class="table table-striped dataex-html5-selectors">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th>No</th>
                                                                                    <th>Image</th>
                                                                                    <th>Merchant Id</th>
                                                                                    <th>Full Name</th>
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
                                                                                    if ($mrc['partner_status'] != 0) { ?>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <?= $i ?>
                                                                                            </td>
                                                                                            <td>
                                                                                                <img class="round" style="width: 40px; height: 40px;" src="<?= base_url('images/merchant/') . $mrc['merchant_image']; ?>">
                                                                                            </td>
                                                                                            <td><?= $mrc['partner_id'] ?></td>
                                                                                            <td><?= $mrc['partner_name'] ?></td>
                                                                                            <td><?= $mrc['partner_telephone'] ?></td>
                                                                                            <td><?= $mrc['merchant_name'] ?></td>
                                                                                            <td><?= $mrc['service'] ?></td>
                                                                                            <td><?= $mrc['category_name'] ?></td>
                                                                                            <td>
                                                                                                <?php if ($mrc['partner_status'] == 3) { ?>
                                                                                                    <label class="badge badge-dark">Banned</label>
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
                                                                                                <?php
                                                                                                if ($mrc['partner_status'] == 1) { ?>
                                                                                                    <span class="action-edit mr-1">
                                                                                                        <a href="<?= base_url(); ?>user/blockmerchant/<?= $mrc['partner_id']; ?>">
                                                                                                            <i class="feather icon-unlock text-info"></i>
                                                                                                        </a>
                                                                                                    </span>
                                                                                                <?php } else { ?>
                                                                                                    <span class="action-edit mr-1">
                                                                                                        <a href="<?= base_url(); ?>user/unblockmerchant/<?= $mrc['partner_id']; ?>">
                                                                                                            <i class="feather icon-lock text-danger"></i>
                                                                                                        </a>
                                                                                                    </span>
                                                                                                <?php } ?>
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
                                            <!-- end of all merchant data table -->
                                        </div>

                                        <div class="tab-pane" id="activemerchant" aria-labelledby="activemerchant-tab" role="tabpanel">
                                            <!-- start active merchant data table -->
                                            <section id="column-selectors">
                                                <div class="row">
                                                    <div class="col-12">
                                                        <div class="card">
                                                            <div class="card-header">
                                                                <h4 class="card-title">Active Merchants Data</h4>
                                                            </div>
                                                            <div class="card-content">
                                                                <div class="card-body card-dashboard">
                                                                    <div class="table-responsive">
                                                                        <table class="table table-striped dataex-html5-selectors">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th>No</th>
                                                                                    <th>Image</th>
                                                                                    <th>Merchant Id</th>
                                                                                    <th>Full Name</th>
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
                                                                                    if ($mrc['partner_status'] == 1) { ?>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <?= $i ?>
                                                                                            </td>
                                                                                            <td>
                                                                                                <div class="avatar mr-1"></div>
                                                                                            </td>
                                                                                            <td><?= $mrc['partner_id'] ?></td>
                                                                                            <td><?= $mrc['partner_name'] ?></td>
                                                                                            <td><?= $mrc['partner_telephone'] ?></td>
                                                                                            <td><?= $mrc['merchant_name'] ?></td>
                                                                                            <td><?= $mrc['service'] ?></td>
                                                                                            <td><?= $mrc['category_name'] ?></td>
                                                                                            <td>
                                                                                                <?php if ($mrc['partner_status'] == 3) { ?>
                                                                                                    <label class="badge badge-dark">Banned</label>
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
                                                                                                <?php
                                                                                                if ($mrc['partner_status'] == 1) { ?>
                                                                                                    <span class="action-edit mr-1">
                                                                                                        <a href="<?= base_url(); ?>user/blockmerchant/<?= $mrc['partner_id']; ?>">
                                                                                                            <i class="feather icon-unlock text-info"></i>
                                                                                                        </a>
                                                                                                    </span>
                                                                                                <?php } else { ?>
                                                                                                    <span class="action-edit mr-1">
                                                                                                        <a href="<?= base_url(); ?>user/unblockmerchant/<?= $mrc['partner_id']; ?>">
                                                                                                            <i class="feather icon-lock text-danger"></i>
                                                                                                        </a>
                                                                                                    </span>
                                                                                                <?php } ?>
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
                                            <!-- end of active merchant data table -->
                                        </div>

                                        <div class="tab-pane" id="suspendedmerchant" aria-labelledby="suspendedmerchant-tab" role="tabpanel">
                                            <!-- start suspended merchant data table -->
                                            <section id="column-selectors">
                                                <div class="row">
                                                    <div class="col-12">
                                                        <div class="card">
                                                            <div class="card-header">
                                                                <h4 class="card-title">Suspended Merchants Data</h4>
                                                            </div>
                                                            <div class="card-content">
                                                                <div class="card-body card-dashboard">
                                                                    <div class="table-responsive">
                                                                        <table class="table table-striped dataex-html5-selectors">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th>No</th>
                                                                                    <th>Image</th>
                                                                                    <th>Merchant Id</th>
                                                                                    <th>Full Name</th>
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
                                                                                    if ($mrc['partner_status'] == 3) { ?>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <?= $i ?>
                                                                                            </td>
                                                                                            <td>
                                                                                                <div class="avatar mr-1"></div>
                                                                                            </td>
                                                                                            <td><?= $mrc['partner_id'] ?></td>
                                                                                            <td><?= $mrc['partner_name'] ?></td>
                                                                                            <td><?= $mrc['partner_telephone'] ?></td>
                                                                                            <td><?= $mrc['merchant_name'] ?></td>
                                                                                            <td><?= $mrc['service'] ?></td>
                                                                                            <td><?= $mrc['category_name'] ?></td>
                                                                                            <td>
                                                                                                <?php if ($mrc['partner_status'] == 3) { ?>
                                                                                                    <label class="badge badge-dark">Banned</label>
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
                                                                                                <?php
                                                                                                if ($mrc['partner_status'] == 1) { ?>
                                                                                                    <span class="action-edit mr-1">
                                                                                                        <a href="<?= base_url(); ?>user/blockmerchant/<?= $mrc['partner_id']; ?>">
                                                                                                            <i class="feather icon-unlock text-info"></i>
                                                                                                        </a>
                                                                                                    </span>
                                                                                                <?php } else { ?>
                                                                                                    <span class="action-edit mr-1">
                                                                                                        <a href="<?= base_url(); ?>user/unblockmerchant/<?= $mrc['partner_id']; ?>">
                                                                                                            <i class="feather icon-lock text-danger"></i>
                                                                                                        </a>
                                                                                                    </span>
                                                                                                <?php } ?>
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
                                            <!-- end of suspended merchant data table -->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <!-- end of merchant tab -->
            <!-- end of merchant data -->
        </div>
    </div>
</div>
<!-- END: Content-->