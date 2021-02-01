<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- Data list view starts -->
            <section id="basic-datatable">
                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">News Data</h4>
                            </div>
                            <div class="card-header">
                                <a class="btn btn-success mb-1 text-white" href="<?= base_url(); ?>news/addnews">
                                    <i class="feather icon-plus mr-1"></i>Add News</a></div>
                            <div class="card-content">
                                <div class="card-body card-dashboard">
                                    <div class="table-responsive">
                                        <table class="table zero-configuration">
                                            <thead>
                                                <tr>
                                                    <th>No.</th>
                                                    <th>Image</th>
                                                    <th>Title</th>
                                                    <th>Create On</th>
                                                    <th>Category</th>
                                                    <th>Status</th>
                                                    <th>Actions</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <?php $i = 1;
                                                foreach ($news as $nw) { ?>
                                                    <tr>
                                                        <td><?= $i ?></td>
                                                        <td>
                                                            <img class="product-img" style="width: 40px; height: 40px;"  src="<?= base_url('images/news/') . $nw['news_images']; ?>">
                                                        </td>
                                                        <td><?= $nw['title']; ?></td>
                                                        <td><?= $nw['news_created']; ?></td>
                                                        <td><?= $nw['category']; ?></td>
                                                        <td>
                                                            <?php if ($nw['news_status'] == 1) { ?>
                                                                <label class="badge badge-success">Active</label>
                                                            <?php } else { ?>
                                                                <label class="badge badge-danger">Non Active</label>
                                                            <?php } ?>
                                                        </td>
                                                        <td>
                                                            <span class="mr-1">
                                                                <a href="<?= base_url(); ?>news/editnews/<?= $nw['news_id']; ?>">
                                                                    <i class="feather icon-edit text-info"></i>
                                                                </a>
                                                            </span>
                                                            <span class="mr-1">
                                                                <a href="<?= base_url(); ?>news/delete/<?= $nw['news_id']; ?>" onclick="return confirm ('are you sure want to delete?')">
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
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <!-- Data list view end -->
        </div>
    </div>
</div>
<!-- END: Content-->