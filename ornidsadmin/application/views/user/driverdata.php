<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- drivers data Start -->
            <!-- drivers tabs start -->
            <section id="basic-tabs-components">
                <div class="row">
                    <div class="col-sm-12">
                        <div class="card overflow-hidden">
                            <div class="card-header">
                                <h4 class="card-title">Driver Data</h4>
                            </div>
                            <div class="card-content">
                                <div class="card-body">
                                    <a class="btn btn-success mb-1 text-white" href="<?= base_url(); ?>user/adddriver">
                                        <i class="feather icon-plus mr-1"></i>Add Driver</a>
                                    <ul class="nav nav-tabs" role="tablist">
                                        <li class="nav-item">
                                            <a class="nav-link active" id="alldriver-tab" data-toggle="tab" href="#alldriver" aria-controls="alldriver" role="tab" aria-selected="true">All Drivers</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" id="activedriver-tab" data-toggle="tab" href="#activedriver" aria-controls="activedriver" role="tab" aria-selected="false">Active Drivers</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" id="nonactivedriver-tab" data-toggle="tab" href="#nonactivedriver" aria-controls="nonactivedriver" role="tab" aria-selected="false">NonActive Drivers</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" id="suspendeddriver-tab" data-toggle="tab" href="#suspendeddriver" aria-controls="suspendeddriver" role="tab" aria-selected="false">Suspended Driver</a>
                                        </li>
                                    </ul>
                                    <div class="tab-content">
                                        <div class="tab-pane active" id="alldriver" aria-labelledby="alldriver-tab" role="tabpanel">
                                            <!-- start all driver data table -->
                                            <section id="column-selectors">
                                                <div class="row">
                                                    <div class="col-12">
                                                        <div class="card">
                                                            <div class="card-header">
                                                                <h4 class="card-title">All Drivers Data</h4>
                                                            </div>
                                                            <div class="card-content">
                                                                <div class="card-body card-dashboard">
                                                                    <div class="table-responsive">
                                                                        <table class="table table-striped dataex-html5-selectors">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th>No</th>
                                                                                    <th>Drivers Id</th>
                                                                                    <th>Image</th>
                                                                                    <th>Full Name</th>
                                                                                    <th>Phone</th>
                                                                                    <th>Rating</th>
                                                                                    <th>Job Service</th>
                                                                                    <th>Status</th>
                                                                                    <th>Actions</th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                                <?php $i = 1;
                                                                                foreach ($driver as $drv) {
                                                                                    if ($drv['status'] != 0) { ?>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <?= $i ?>
                                                                                            </td>
                                                                                            <td><?= $drv['id'] ?></td>
                                                                                            <td>
                                                                                                <img class="round" style="width: 40px; height: 40px;" src="<?= base_url('images/driverphoto/') . $drv['photo']; ?>">
                                                                                            </td>
                                                                                            <td><?= $drv['driver_name'] ?></td>
                                                                                            <td><?= $drv['phone_number'] ?></td>
                                                                                            <td><?= number_format($drv['rating'], 1) ?></td>
                                                                                            <td><?= $drv['driver_job'] ?></td>
                                                                                            <td>
                                                                                                <?php if ($drv['status'] == 3) { ?>
                                                                                                    <label class="badge badge-dark">Banned</label>
                                                                                                <?php } elseif ($drv['status'] == 0) { ?>
                                                                                                    <label class="badge badge-secondary text-dark">New Reg</label>
                                                                                                    <?php } else {
                                                                                                    if ($drv['status_job'] == 1) { ?>
                                                                                                        <label class="badge badge-info">Active</label>
                                                                                                    <?php }
                                                                                                    if ($drv['status_job'] == 2) { ?>
                                                                                                        <label class="badge badge-primary">Pick'up</label>
                                                                                                    <?php }
                                                                                                    if ($drv['status_job'] == 3) { ?>
                                                                                                        <label class="badge badge-success">work</label>
                                                                                                    <?php }
                                                                                                    if ($drv['status_job'] == 4) { ?>
                                                                                                        <label class="badge badge-warning">Non Active</label>
                                                                                                    <?php }
                                                                                                    if ($drv['status_job'] == 5) { ?>
                                                                                                        <label class="badge badge-danger">Log Out</label>
                                                                                                <?php }
                                                                                                } ?>
                                                                                            </td>
                                                                                            <td>
                                                                                                <span class="mr-1">
                                                                                                    <a href="<?= base_url(); ?>detailuser/detaildriver/<?= $drv['id'] ?>">
                                                                                                        <i class="feather icon-eye text-success"></i>
                                                                                                    </a>
                                                                                                </span>
                                                                                                <?php
                                                                                                if ($drv['status'] != 0) {

                                                                                                    if ($drv['status'] != 3) { ?>
                                                                                                        <span class="action-edit mr-1">
                                                                                                            <a href="<?= base_url(); ?>user/blockdriver/<?= $drv['id']; ?>">
                                                                                                                <i class="feather icon-unlock text-info"></i>
                                                                                                            </a>
                                                                                                        </span>
                                                                                                    <?php } else { ?>
                                                                                                        <span class="action-edit mr-1">
                                                                                                            <a href="<?= base_url(); ?>user/unblockdriver/<?= $drv['id']; ?>">
                                                                                                                <i class="feather icon-lock text-info"></i>
                                                                                                            </a>
                                                                                                        </span>
                                                                                                <?php }
                                                                                                } ?>
                                                                                                <span class="action-edit mr-1">
                                                                                                    <a onclick="return confirm ('Are You Sure?')" href="<?= base_url(); ?>user/deletedriver/<?= $drv['id']; ?>">
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
                                            <!-- end of all driver data table -->
                                        </div>

                                        <div class="tab-pane" id="activedriver" aria-labelledby="activedriver-tab" role="tabpanel">
                                            <!-- start all active driver data table -->
                                            <section id="column-selectors">
                                                <div class="row">
                                                    <div class="col-12">
                                                        <div class="card">
                                                            <div class="card-header">
                                                                <h4 class="card-title">Active Drivers Data</h4>
                                                            </div>
                                                            <div class="card-content">
                                                                <div class="card-body card-dashboard">
                                                                    <div class="table-responsive">
                                                                        <table class="table table-striped dataex-html5-selectors">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th>No</th>
                                                                                    <th>Drivers Id</th>
                                                                                    <th>Image</th>
                                                                                    <th>Full Name</th>
                                                                                    <th>Phone</th>
                                                                                    <th>Rating</th>
                                                                                    <th>Job Service</th>
                                                                                    <th>Status</th>
                                                                                    <th>Actions</th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                                <?php $i = 1;
                                                                                foreach ($driver as $drv) {
                                                                                    if ($drv['status'] != 3) {
                                                                                        if ($drv['status_job'] == 1 or $drv['status_job'] == 2 or $drv['status_job'] == 3) { ?>
                                                                                            <tr>
                                                                                                <td>
                                                                                                    <?= $i ?>
                                                                                                </td>
                                                                                                <td><?= $drv['id'] ?></td>
                                                                                                <td>
                                                                                                    <img class="round" style="width: 40px; height: 40px;" src="<?= base_url('images/driverphoto/') . $drv['photo']; ?>">
                                                                                                </td>
                                                                                                <td><?= $drv['driver_name'] ?></td>
                                                                                                <td><?= $drv['phone_number'] ?></td>
                                                                                                <td><?= number_format($drv['rating'], 1) ?></td>
                                                                                                <td><?= $drv['driver_job'] ?></td>
                                                                                                <td>
                                                                                                    <?php if ($drv['status'] == 3) { ?>
                                                                                                        <label class="badge badge-dark">Banned</label>
                                                                                                    <?php } elseif ($drv['status'] == 0) { ?>
                                                                                                        <label class="badge badge-secondary text-dark">New Reg</label>
                                                                                                        <?php } else {
                                                                                                        if ($drv['status_job'] == 1) { ?>
                                                                                                            <label class="badge badge-info">Active</label>
                                                                                                        <?php }
                                                                                                        if ($drv['status_job'] == 2) { ?>
                                                                                                            <label class="badge badge-primary">Pick'up</label>
                                                                                                        <?php }
                                                                                                        if ($drv['status_job'] == 3) { ?>
                                                                                                            <label class="badge badge-success">work</label>
                                                                                                        <?php }
                                                                                                        if ($drv['status_job'] == 4) { ?>
                                                                                                            <label class="badge badge-warning">Non Active</label>
                                                                                                        <?php }
                                                                                                        if ($drv['status_job'] == 5) { ?>
                                                                                                            <label class="badge badge-danger">Log Out</label>
                                                                                                    <?php }
                                                                                                    } ?>
                                                                                                </td>
                                                                                                <td>
                                                                                                    <span class="mr-1">
                                                                                                        <a href="<?= base_url(); ?>detailuser/detaildriver/<?= $drv['id'] ?>">
                                                                                                            <i class="feather icon-eye text-success"></i>
                                                                                                        </a>
                                                                                                    </span>
                                                                                                    <?php
                                                                                                    if ($drv['status'] != 0) {

                                                                                                        if ($drv['status'] != 3) { ?>
                                                                                                            <span class="action-edit mr-1">
                                                                                                                <a href="<?= base_url(); ?>user/blockdriver/<?= $drv['id']; ?>">
                                                                                                                    <i class="feather icon-unlock text-info"></i>
                                                                                                                </a>
                                                                                                            </span>
                                                                                                        <?php } else { ?>
                                                                                                            <span class="action-edit mr-1">
                                                                                                                <a href="<?= base_url(); ?>user/unblockdriver/<?= $drv['id']; ?>">
                                                                                                                    <i class="feather icon-lock text-info"></i>
                                                                                                                </a>
                                                                                                            </span>
                                                                                                    <?php }
                                                                                                    } ?>
                                                                                                    <span class="action-edit mr-1">
                                                                                                        <a onclick="return confirm ('Are You Sure?')" href="<?= base_url(); ?>user/deletedriver/<?= $drv['id']; ?>">
                                                                                                            <i class="feather icon-trash text-danger"></i></a>
                                                                                                    </span>
                                                                                                </td>
                                                                                            </tr>
                                                                                <?php $i++;
                                                                                        }
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
                                            <!-- end of all active driver data table -->
                                        </div>

                                        <div class="tab-pane" id="nonactivedriver" aria-labelledby="nonactivedriver-tab" role="tabpanel">
                                            <!-- start nonactive driver data table -->
                                            <section id="column-selectors">
                                                <div class="row">
                                                    <div class="col-12">
                                                        <div class="card">
                                                            <div class="card-header">
                                                                <h4 class="card-title">NonActive Drivers Data</h4>
                                                            </div>
                                                            <div class="card-content">
                                                                <div class="card-body card-dashboard">
                                                                    <div class="table-responsive">
                                                                        <table class="table table-striped dataex-html5-selectors">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th>No</th>
                                                                                    <th>Drivers Id</th>
                                                                                    <th>Image</th>
                                                                                    <th>Full Name</th>
                                                                                    <th>Phone</th>
                                                                                    <th>Rating</th>
                                                                                    <th>Job Service</th>
                                                                                    <th>Status</th>
                                                                                    <th>Actions</th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                                <?php $i = 1;
                                                                                foreach ($driver as $drv) {
                                                                                    if ($drv['status_job'] == 4 or $drv['status_job'] == 5 and $drv['status'] != 0 and $drv['status'] != 3) { ?>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <?= $i ?>
                                                                                            </td>
                                                                                            <td><?= $drv['id'] ?></td>
                                                                                            <td>
                                                                                                <img class="round" style="width: 40px; height: 40px;" src="<?= base_url('images/driverphoto/') . $drv['photo']; ?>">
                                                                                            </td>
                                                                                            <td><?= $drv['driver_name'] ?></td>
                                                                                            <td><?= $drv['phone_number'] ?></td>
                                                                                            <td><?= number_format($drv['rating'], 1) ?></td>
                                                                                            <td><?= $drv['driver_job'] ?></td>
                                                                                            <td>
                                                                                                <?php if ($drv['status'] == 3) { ?>
                                                                                                    <label class="badge badge-dark">Banned</label>
                                                                                                <?php } elseif ($drv['status'] == 0) { ?>
                                                                                                    <label class="badge badge-secondary text-dark">New Reg</label>
                                                                                                    <?php } else {
                                                                                                    if ($drv['status_job'] == 1) { ?>
                                                                                                        <label class="badge badge-info">Active</label>
                                                                                                    <?php }
                                                                                                    if ($drv['status_job'] == 2) { ?>
                                                                                                        <label class="badge badge-primary">Pick'up</label>
                                                                                                    <?php }
                                                                                                    if ($drv['status_job'] == 3) { ?>
                                                                                                        <label class="badge badge-success">work</label>
                                                                                                    <?php }
                                                                                                    if ($drv['status_job'] == 4) { ?>
                                                                                                        <label class="badge badge-warning">Non Active</label>
                                                                                                    <?php }
                                                                                                    if ($drv['status_job'] == 5) { ?>
                                                                                                        <label class="badge badge-danger">Log Out</label>
                                                                                                <?php }
                                                                                                } ?>
                                                                                            </td>
                                                                                            <td>
                                                                                                <span class="mr-1">
                                                                                                    <a href="<?= base_url(); ?>detailuser/detaildriver/<?= $drv['id'] ?>">
                                                                                                        <i class="feather icon-eye text-success"></i>
                                                                                                    </a>
                                                                                                </span>
                                                                                                <?php
                                                                                                if ($drv['status'] != 0) {

                                                                                                    if ($drv['status'] != 3) { ?>
                                                                                                        <span class="action-edit mr-1">
                                                                                                            <a href="<?= base_url(); ?>user/blockdriver/<?= $drv['id']; ?>">
                                                                                                                <i class="feather icon-unlock text-info"></i>
                                                                                                            </a>
                                                                                                        </span>
                                                                                                    <?php } else { ?>
                                                                                                        <span class="action-edit mr-1">
                                                                                                            <a href="<?= base_url(); ?>user/unblockdriver/<?= $drv['id']; ?>">
                                                                                                                <i class="feather icon-lock text-info"></i>
                                                                                                            </a>
                                                                                                        </span>
                                                                                                <?php }
                                                                                                } ?>
                                                                                                <span class="action-edit mr-1">
                                                                                                    <a onclick="return confirm ('Are You Sure?')" href="<?= base_url(); ?>user/deletedriver/<?= $drv['id']; ?>">
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
                                            <!-- end of nonactive driver data table -->
                                        </div>

                                        <div class="tab-pane" id="suspendeddriver" aria-labelledby="suspendeddriver-tab" role="tabpanel">
                                            <!-- start suspended driver data table -->
                                            <section id="column-selectors">
                                                <div class="row">
                                                    <div class="col-12">
                                                        <div class="card">
                                                            <div class="card-header">
                                                                <h4 class="card-title">Suspended Driver Data</h4>
                                                            </div>
                                                            <div class="card-content">
                                                                <div class="card-body card-dashboard">
                                                                    <div class="table-responsive">
                                                                        <table class="table table-striped dataex-html5-selectors">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th>No</th>
                                                                                    <th>Drivers Id</th>
                                                                                    <th>Image</th>
                                                                                    <th>Full Name</th>
                                                                                    <th>Phone</th>
                                                                                    <th>Rating</th>
                                                                                    <th>Job Service</th>
                                                                                    <th>Status</th>
                                                                                    <th>Actions</th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                                <?php $i = 1;
                                                                                foreach ($driver as $drv) {
                                                                                    if ($drv['status'] == 3) { ?>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <?= $i ?>
                                                                                            </td>
                                                                                            <td><?= $drv['id'] ?></td>
                                                                                            <td>
                                                                                                <img class="round" style="width: 40px; height: 40px;" src="<?= base_url('images/driverphoto/') . $drv['photo']; ?>">
                                                                                            </td>
                                                                                            <td><?= $drv['driver_name'] ?></td>
                                                                                            <td><?= $drv['phone_number'] ?></td>
                                                                                            <td><?= number_format($drv['rating'], 1) ?></td>
                                                                                            <td><?= $drv['driver_job'] ?></td>
                                                                                            <td>
                                                                                                <?php if ($drv['status'] == 3) { ?>
                                                                                                    <label class="badge badge-dark">Banned</label>
                                                                                                <?php } elseif ($drv['status'] == 0) { ?>
                                                                                                    <label class="badge badge-secondary text-dark">New Reg</label>
                                                                                                    <?php } else {
                                                                                                    if ($drv['status_job'] == 1) { ?>
                                                                                                        <label class="badge badge-info">Active</label>
                                                                                                    <?php }
                                                                                                    if ($drv['status_job'] == 2) { ?>
                                                                                                        <label class="badge badge-primary">Pick'up</label>
                                                                                                    <?php }
                                                                                                    if ($drv['status_job'] == 3) { ?>
                                                                                                        <label class="badge badge-success">work</label>
                                                                                                    <?php }
                                                                                                    if ($drv['status_job'] == 4) { ?>
                                                                                                        <label class="badge badge-warning">Non Active</label>
                                                                                                    <?php }
                                                                                                    if ($drv['status_job'] == 5) { ?>
                                                                                                        <label class="badge badge-danger">Log Out</label>
                                                                                                <?php }
                                                                                                } ?>
                                                                                            </td>
                                                                                            <td>
                                                                                                <span class="mr-1">
                                                                                                    <a href="<?= base_url(); ?>detailuser/detaildriver/<?= $drv['id'] ?>">
                                                                                                        <i class="feather icon-eye text-success"></i>
                                                                                                    </a>
                                                                                                </span>
                                                                                                <?php
                                                                                                if ($drv['status'] != 0) {

                                                                                                    if ($drv['status'] != 3) { ?>
                                                                                                        <span class="action-edit mr-1">
                                                                                                            <a href="<?= base_url(); ?>user/blockdriver/<?= $drv['id']; ?>">
                                                                                                                <i class="feather icon-unlock text-info"></i>
                                                                                                            </a>
                                                                                                        </span>
                                                                                                    <?php } else { ?>
                                                                                                        <span class="action-edit mr-1">
                                                                                                            <a href="<?= base_url(); ?>user/unblockdriver/<?= $drv['id']; ?>">
                                                                                                                <i class="feather icon-lock text-info"></i>
                                                                                                            </a>
                                                                                                        </span>
                                                                                                <?php }
                                                                                                } ?>
                                                                                                <span class="action-edit mr-1">
                                                                                                    <a onclick="return confirm ('Are You Sure?')" href="<?= base_url(); ?>user/deletedriver/<?= $drv['id']; ?>">
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
                                            <!-- end of suspended driver data table -->
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <!-- end of drivers tab -->
            <!-- end of drivers data -->
        </div>
    </div>
</div>
<!-- END: Content-->