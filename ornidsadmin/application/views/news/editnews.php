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
                            <h4 class="card-title">Edit News</h4>
                        </div>
                        <section id="basic-vertical-layouts">
                            <div class="card-content">
                                <div class="card-body">
                                    <?= form_open_multipart('news/edit'); ?>
                                    <form class="form form-vertical">
                                        <div class="form-body">
                                            <div class="row">
                                                    <input type="hidden" class="form-control" name="news_id" id="newstitle" value="<?= $news['news_id'] ?>">
                                                    <div class="col-12 form-group">
                                                        <label>News Image</label>
                                                        <input type="file" class="dropify" name="news_images" data-max-file-size="3mb" data-default-file="<?= base_url('images/news/') . $news['news_images'] ?>" />
                                                    </div>
                                                    <div class="col-12 form-group">
                                                        <label for="newstitle">Title</label>
                                                        <input type="text" class="form-control" name="title" id="newstitle" value="<?= $news['title'] ?>" required>
                                                    </div>
                                                    <div class="col-12 form-group">
                                                        <label for="newscategory">News Category</label>
                                                        <select class="select2 form-group" style="width:100%" name="category_id">
                                                            <?php foreach ($knews as $nw) { ?>


                                                                <option value="<?= $nw['news_category_id'] ?>" <?php if ($nw['news_category_id'] == $news['category_id']) { ?>selected<?php } ?>> <?= $nw['category'] ?></option>

                                                            <?php } ?>

                                                        </select>
                                                    </div>  
                                                    
                                                    <div class="col-12 form-group">
                                                        <label for="newscategory">News Status</label>
                                                        <select class="select2 form-group" style="width:100%" name="news_status">

                                                            <option value="1" <?php if ($news['news_status'] == '1') { ?>selected<?php } ?>>Active</option>
                                                            <option value="2" <?php if ($news['news_status'] == '2') { ?>selected<?php } ?>>NonActive</option>

                                                        </select>
                                                    </div>
                                                    <div class="col-12 form-group">
                                                        <label for="newscontent">News Content</label>
                                                        <textarea type="text" class="form-control" id="summernoteExample1" placeholder="Location" name="content" required><?= $news['content'] ?></textarea>
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
            </section>
            </div>
    </div>
</div>

            <!-- end of add news -->
        </div>
    </div>
</div>
<!-- END: Content-->