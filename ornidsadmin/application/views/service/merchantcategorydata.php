<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- Data list view starts -->
            <section id="data-thumb-view" class="data-thumb-view-header">

                <div class="card-header">
                    <h4>Category Merchant <span><a class="btn btn-success float-right mb-1 text-white" href="<?= base_url(); ?>service/addmerchantcategoryview">
                                <i class="feather icon-plus mr-1"></i>Add Category Merchant</a></span></h4>
                </div>
                <!-- merchant category Table starts -->
                <div class="table-responsive">
                    <table class="table data-thumb-view">
                        <thead>
                            <tr>
                            <th>No</th>
                                    <th>Category Name</th>
                                    <th>For Service</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>

                        <?php $i = 1;
                                foreach ($merchantcategory as $mc) { ?>
                                    <tr>
                                        <h1 id="idkat<?= $i ?>" style="display:none;"><?= $mc['category_merchant_id']; ?></h1>
                                        <h1 id="statm<?= $i ?>" style="display:none;"><?= $mc['category_status']; ?></h1>
                                        <td><?= $i ?></td>
                                        <td id="namkat<?= $i ?>"><?= $mc['category_name']; ?></td>
                                        <td id="service<?= $i ?>"><?= $mc['service']; ?></td>
                                        <td>
                                            <div>
                                                <?php if ($mc['category_status'] == 1) { ?>
                                                    <label class="badge badge-success">Active
                                                    </label>
                                                <?php } else { ?>
                                                    <label class="badge badge-danger">Non Active
                                                    </label>
                                                <?php } ?>
                                            </div>
                                        </td>
                                        <td>
                                        <span class="mr-1">
                                                                <a href="<?= base_url(); ?>service/editmerchantcategoryview/<?= $mc['category_merchant_id']; ?>">
                                                                    <i class="feather icon-edit text-info"></i>
                                                                </a>
                                                            </span>
                                                            <span class="action-delete mr-1">
                                                                <a href="<?= base_url(); ?>service/deletemerchantcategory/<?= $mc['category_merchant_id']; ?>" onclick="return confirm ('are you sure want to delete?')">
                                                                    <i class="feather icon-trash text-danger"></i>
                                                                </a>
                                                            </span>
                                        </td>
                                    </tr>

                                <?php $i++;
                                } ?>

                        </tbody>
                    </table>
                </div>
                <!-- merchant category data Table ends -->

                
            </section>
            <!-- Data list view end -->
        </div>
    </div>
</div>
<!-- END: Content-->