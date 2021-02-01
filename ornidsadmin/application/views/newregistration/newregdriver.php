<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- new registration driver data Start -->
            <section id="column-selectors">
                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">New Registration Driver</h4>
                            </div>
                            <div class="card-content">
                                <div class="card-body card-dashboard">
                                    <div class="table-responsive">
                                        <table class="table zero-configuration">
                                            <thead>
                                                <tr>
                                                    <th>No</th>
                                                    <th>drivers Id</th>
                                                    <th>Profile Pic</th>
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
                                                    if ($drv['status'] == 0) { ?>
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
                                                                        <label class="badge badge-primary">Active</label>
                                                                    <?php }
                                                                    if ($drv['status_job'] == 2) { ?>
                                                                        <label class="badge badge-info">Pick'up</label>
                                                                    <?php }
                                                                    if ($drv['status_job'] == 3) { ?>
                                                                        <label class="badge badge-success">work</label>
                                                                    <?php }
                                                                    if ($drv['status_job'] == 4) { ?>
                                                                        <label class="badge badge-danger">Non Active</label>
                                                                <?php }
                                                                } ?>
                                                            </td>
                                                            <td>
                                                                <span class="mr-1">
                                                                    <a href="<?= base_url(); ?>detailuser/detaildriver/<?= $drv['id'] ?>">
                                                                        <i class="feather icon-eye text-success"></i>
                                                                    </a>
                                                                </span>
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
            <!-- end of new registration driver data table -->

            <!-- end of customer data -->
        </div>
    </div>
</div>
<!-- END: Content-->