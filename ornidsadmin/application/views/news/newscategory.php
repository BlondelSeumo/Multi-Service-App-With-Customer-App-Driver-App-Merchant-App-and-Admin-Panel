<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- Data list view starts -->
            <section id="data-thumb-view" class="data-thumb-view-header">

                <div class="card-header">
                    <h4>Category News <span><a class="btn btn-success float-right mb-1 text-white action-add">
                                <i class="feather icon-plus mr-1"></i>Add Category News</a></span></h4>
                </div>
                <!-- news category Table starts -->
                <div class="table-responsive">
                    <table class="table data-thumb-view">
                        <thead>
                            <tr>
                                <th>No.</th>
                                <th>Category</th>
                                <th>Created On</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <?php $i = 1;
                            foreach ($newscategory as $nwcat) { ?>
                                <tr>
                                    <td><?= $i ?></td>
                                    <td class="product-name"><?= $nwcat['category']; ?></td>
                                    <td class="product-name"><?= $nwcat['created']; ?></td>
                                    <td>
                                        <span class="mr-1">
                                            <a href="#" onclick="editcatnews('<?= $nwcat['news_category_id'] . ',' . $nwcat['category'] ?>')">
                                                <i class="feather icon-edit text-info"></i>
                                            </a>
                                        </span>
                                        <span class="mr-1">
                                            <a href="<?= base_url(); ?>news/deletecategory/<?= $nwcat['news_category_id']; ?>" onclick="return confirm ('are you sure want to delete?')">
                                                <i class="feather icon-trash text-danger"></i>
                                            </a>
                                        </span>
                                    </td>
                                <?php $i++;
                            } ?>
                        </tbody>
                    </table>
                </div>
                <!-- news category data Table ends -->

                <!-- add new sidebar starts -->
                <div class="add-new-data-sidebar">
                    <div class="overlay-bg"></div>
                    <div class="add-new-data">
                        <div class="div mt-2 px-2 d-flex new-data-title justify-content-between">
                            <div>
                                <h4 class="text-uppercase">Add/Edit Category</h4>
                            </div>
                            <div class="hide-data-sidebar">
                                <i class="feather icon-x"></i>
                            </div>
                        </div>
                        <?= form_open_multipart('news/addcategory'); ?>
                        <form class="form form-vertical">
                            <div class="data-items pb-3">
                                <div class="data-fields px-2 mt-3">
                                    <div class="row">
                                        <div class="col-sm-12 data-field-col">
                                            <label for="data-name">Category Name</label>
                                            <input type="text" class="form-control" id="data-name" name="category"></div>
                                        <input type="hidden" class="form-control" id="action-view" name="view">
                                        <input type="hidden" class="form-control" id="id" name="id">
                                    </div>
                                    <div class="col-sm-12 data-field-col">
                                    </div>
                                </div>
                            </div>
                            <div class="add-data-footer d-flex justify-content-around px-3 mt-2">
                                <div class="add-data-btn">
                                    <button type="submit" class="btn btn-primary">Save</button>
                                </div>
                            </div>
                    </div>
                    </form>
                    <?= form_close(); ?>
                </div>
                <!-- add new sidebar ends -->
            </section>
            <!-- Data list view end -->
        </div>
    </div>
</div>
<!-- END: Content-->