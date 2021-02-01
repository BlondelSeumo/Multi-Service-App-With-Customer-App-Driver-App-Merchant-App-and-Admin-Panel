<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- customer data Start -->
            <!-- customer tabs start -->
            <section id="basic-tabs-components">
                <div class="row">
                    <div class="col-sm-12">
                        <div class="card overflow-hidden">
                            <div class="card-header">
                                <h4 class="card-title">Customer Data</h4>
                            </div>
                            <div class="card-content">
                                <div class="card-body">
                                    <a class="btn btn-success mb-1 text-white" href="<?= base_url(); ?>user/addcustomerview">
                                        <i class="feather icon-plus mr-1"></i>Add User</a>
                                    <ul class="nav nav-tabs" role="tablist">
                                        <li class="nav-item">
                                            <a class="nav-link active" id="allcustomer-tab" data-toggle="tab" href="#allcustomer" aria-controls="allcustomer" role="tab" aria-selected="true">All Customers</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" id="blockeduser-tab" data-toggle="tab" href="#blockeduser" aria-controls="blockeduser" role="tab" aria-selected="false">Blocked User</a>
                                        </li>
                                    </ul>
                                    <div class="tab-content">
                                        <div class="tab-pane active" id="allcustomer" aria-labelledby="allcustomer-tab" role="tabpanel">
                                            <!-- start all customer data table -->
                                            <section id="column-selectors">
                                                <div class="row">
                                                    <div class="col-12">
                                                        <div class="card">
                                                            <div class="card-header">
                                                                <h4 class="card-title">All Customer Data</h4>
                                                            </div>
                                                            <div class="card-content">
                                                                <div class="card-body card-dashboard">
                                                                    <div class="table-responsive">
                                                                        <table class="table table-striped dataex-html5-selectors">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th>No</th>
                                                                                    <th>Users Id</th>
                                                                                    <th>Image</th>
                                                                                    <th>Full Name</th>
                                                                                    <th>Email</th>
                                                                                    <th>Phone</th>
                                                                                    <th>Status</th>
                                                                                    <th>Actions</th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                                <?php $i = 1;
                                                                                foreach ($customer as $cstm) { ?>
                                                                                    <tr>
                                                                                        <td><?= $i ?></td>
                                                                                        <td><?= $cstm['id'] ?></td>
                                                                                        <td>
                                                                                            <img class="round" style="width: 40px; height: 40px;" src="<?= base_url('images/customer/') . $cstm['customer_image']; ?>">
                                                                                        </td>
                                                                                        <td><?= $cstm['customer_fullname'] ?></td>
                                                                                        <td><?= $cstm['email'] ?></td>
                                                                                        <td><?= $cstm['phone_number'] ?></td>
                                                                                        <td>
                                                                                            <?php if ($cstm['status'] == 1) { ?>
                                                                                                <label class="badge badge-success">Active</label>
                                                                                            <?php } else { ?>
                                                                                                <label class="badge badge-dark">Blocked</label>
                                                                                            <?php } ?>
                                                                                        </td>
                                                                                        <td>
                                                                                            <span class="">
                                                                                                <a href="<?= base_url(); ?>detailuser/detailcustomer/<?= $cstm['id'] ?>">
                                                                                                    <i class="feather icon-eye text-success"></i>
                                                                                                </a>
                                                                                            </span>
                                                                                            <?php if ($cstm['status'] == 0) { ?>
                                                                                                <span class="mr-1 ml-1">
                                                                                                    <a href="<?= base_url(); ?>user/unblock/<?= $cstm['id']; ?>">
                                                                                                        <i class="feather icon-lock text-info"></i>
                                                                                                    </a>
                                                                                                </span>
                                                                                            <?php } else { ?>
                                                                                                <span class="mr-1 ml-1">
                                                                                                    <a href="<?= base_url(); ?>user/block/<?= $cstm['id']; ?>">
                                                                                                        <i class="feather icon-unlock text-dark"></i>
                                                                                                    </a>
                                                                                                </span>
                                                                                            <?php } ?>
                                                                                            <span class="action-delete ml-1">
                                                                                                <a onclick="return confirm ('Are You Sure?')" href="<?= base_url(); ?>user/deleteusers/<?= $cstm['id']; ?>">
                                                                                                    <i class="feather icon-trash text-danger"></i></a>
                                                                                            </span>
                                                                                        </td>
                                                                                    </tr>
                                                                                <?php $i++;
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
                                            <!-- end of all customer data table -->
                                        </div>
                                        <div class="tab-pane" id="blockeduser" aria-labelledby="blockeduser-tab" role="tabpanel">
                                            <!-- start all blocked customer data table -->
                                            <section id="column-selectors">
                                                <div class="row">
                                                    <div class="col-12">
                                                        <div class="card">
                                                            <div class="card-header">
                                                                <h4 class="card-title">All Blocked Customer Data</h4>
                                                            </div>
                                                            <div class="card-content">
                                                                <div class="card-body card-dashboard">
                                                                    <div class="table-responsive">
                                                                        <table class="table table-striped dataex-html5-selectors">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th>No</th>
                                                                                    <th>Users Id</th>
                                                                                    <th>Image</th>
                                                                                    <th>Full Name</th>
                                                                                    <th>Email</th>
                                                                                    <th>Phone</th>
                                                                                    <th>Status</th>
                                                                                    <th>Actions</th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                                <?php $i = 1;
                                                                                foreach ($customer as $cstm) {
                                                                                    if ($cstm['status'] == 0) { ?>
                                                                                        <tr>
                                                                                            <td><?= $i ?></td>
                                                                                            <td><?= $cstm['id'] ?></td>
                                                                                            <td>
                                                                                                <img class="round" style="width: 40px; height: 40px;" src="<?= base_url('images/customer/') . $cstm['customer_image']; ?>">
                                                                                            </td>
                                                                                            <td><?= $cstm['customer_fullname'] ?></td>
                                                                                            <td><?= $cstm['email'] ?></td>
                                                                                            <td><?= $cstm['phone_number'] ?></td>
                                                                                            <td>
                                                                                                <?php if ($cstm['status'] == 1) { ?>
                                                                                                    <label class="badge badge-success">Active</label>
                                                                                                <?php } else { ?>
                                                                                                    <label class="badge badge-dark">Blocked</label>
                                                                                                <?php } ?>
                                                                                            </td>
                                                                                            <td>
                                                                                                <span class="">
                                                                                                    <a href="<?= base_url(); ?>detailuser/detailcustomer/<?= $cstm['id'] ?>">
                                                                                                        <i class="feather icon-eye text-success"></i>
                                                                                                    </a>
                                                                                                </span>
                                                                                                <?php if ($cstm['status'] == 0) { ?>
                                                                                                    <span class="action-delete mr-1 ml-1">
                                                                                                        <a href="<?= base_url(); ?>user/unblock/<?= $cstm['id']; ?>">
                                                                                                            <i class="feather icon-lock text-info"></i>
                                                                                                        </a>
                                                                                                    </span>
                                                                                                <?php } else { ?>
                                                                                                    <span class="action-delete mr-1 ml-1">
                                                                                                        <a href="<?= base_url(); ?>user/block/<?= $cstm['id']; ?>">
                                                                                                            <i class="feather icon-unlock text-dark"></i>
                                                                                                        </a>
                                                                                                    </span>
                                                                                                <?php } ?>
                                                                                                <span class="action-delete mr-1 ml-1">
                                                                                                    <a onclick="return confirm ('Are You Sure?')" href="<?= base_url(); ?>user/deleteusers/<?= $cstm['id']; ?>">
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
                                            <!-- end of all blocked customer data table -->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <!-- end of customer tab -->
            <!-- end of customer data -->
        </div>
    </div>
</div>
<!-- END: Content-->