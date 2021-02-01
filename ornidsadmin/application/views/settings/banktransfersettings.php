<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- Data list view starts -->
            <section id="data-thumb-view" class="data-thumb-view-header">

                <div class="card-header">
                    <h4>Bank Account Transfer <span><a class="btn btn-success float-right mb-1 text-white" href="<?= base_url(); ?>settings/addbankaccount">
                                <i class="feather icon-plus mr-1"></i>Add Bank</a></span></h4>
                </div>
                <!-- slider Table starts -->
                <div class="table-responsive">
                    <table class="table data-thumb-view">
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>Bank Logo</th>
                                <th>Account Holder</th>
                                <th>Account Number</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <?php $i = 1;
                            foreach ($banktransfer as $bnk) { ?>
                                <tr>

                                    <td><?= $i ?></td>
                                    <td class="product-img"><img src="<?= base_url('images/bank/') . $bnk['bank_logo']; ?>"></td>
                                    <td class="product-name"><?= $bnk['bank_name'] ?></td>
                                    <td class="product-name"><?= $bnk['bank_account'] ?></td>
                                    <td><?php if ($bnk['bank_status'] == 1) { ?>
                                            <label class="badge badge-primary">Active</label>
                                        <?php } else if ($bnk['bank_status'] == 0) { ?>
                                            <label class="badge badge-danger">Non Active</label>
                                        <?php } ?>
                                    </td>
                                    <td>
                                        <span class="action-edit mr-1">
                                            <a href="<?= base_url(); ?>settings/editbankaccount/<?= $bnk['bank_id']; ?>">
                                                <i class="feather icon-edit text-info"></i>
                                            </a>
                                        </span>
                                        <span class="action-delete mr-1">
                                            <span class="action-delete mr-1">
                                                <a onclick="return confirm ('Are You Sure?')" href="<?= base_url(); ?>settings/deletebankdata/<?= $bnk['bank_id']; ?>">
                                                    <i class="feather icon-trash text-danger"></i></a>
                                            </span>
                                        </span>
                                    </td>
                                </tr>

                            <?php $i++;
                            } ?>
                        </tbody>
                    </table>
                </div>
                <!-- slider data Table ends -->


            </section>
            <!-- Data list view end -->
        </div>
    </div>
</div>
<!-- END: Content-->