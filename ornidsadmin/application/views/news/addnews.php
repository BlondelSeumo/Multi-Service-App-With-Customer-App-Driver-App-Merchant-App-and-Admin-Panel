<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- start add news -->
            <div class="row match-height justify-content-center">
                <div class="col-md-8 col-12">
                    <div class="card">
                        <div class="card-header">
                            <h4 class="card-title">Add news</h4>
                        </div>
                        <section id="basic-vertical-layouts">
                            <div class="card-content">
                                <div class="card-body">
                                    <?= form_open_multipart('news/add'); ?>
                                    <form class="form form-vertical">
                                        <div class="form-body">
                                            <div class="row">
                                                <div class="col-12 form-group">
                                                    <label>News Image</label>
                                                    <input type="file" class="dropify" name="news_images" id="news_images" data-max-file-size="3mb" required />
                                                </div>
                                                <div class="col-12 form-group">
                                                    <label for="title">Title</label>
                                                    <input type="text" class="form-control" name="title" id="title" placeholder="enter news title" required>
                                                </div>
                                                <div class="col-12 form-group">
                                                    <label for="category_id">News Category</label>
                                                    <select class="select2 form-group" style="width:100%" name="category_id" id="category_id">
                                                        <?php foreach ($news as $nw) { ?>
                                                            <option value="<?= $nw['news_category_id'] ?>"><?= $nw['category'] ?></option>
                                                        <?php } ?>
                                                    </select>
                                                </div>
                                                <div class="col-12 form-group">
                                                    <label for="news_status">News Status</label>
                                                    <select class="select2 form-group" style="width:100%" name="news_status" id="news_status">
                                                        <option value="1">Active</option>
                                                        <option value="0">NonActive</option>
                                                    </select>
                                                </div>
                                                <div class="col-12 form-group">
                                                    <label for="newscontent">News Content</label>
                                                    <textarea name="content" type="text" class="form-control" id="summernoteExample1" placeholder="Location" required></textarea>
                                                </div>
                                                <!-- end of add news form -->

                                                <div class="col-12">
                                                    <button type="submit" class="btn btn-primary mr-1 mb-1">Submit</button>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                    <?= form_close(); ?>
                                </div>
                            </div>
                    </div>
                </div>
            </div>
            </section>

            <!-- end of add news -->
        </div>
    </div>
</div>
<!-- END: Content-->