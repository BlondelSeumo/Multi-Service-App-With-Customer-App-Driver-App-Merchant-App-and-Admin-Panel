<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- start form add slider -->

            <section id="basic-vertical-layouts">
                <div class="row match-height justify-content-center">
                    <div class="col-md-6 col-12">
                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">Add Bank</h4>
                            </div>
                            <br>
                            <div class="card-content">
                                <div class="card-body">
                                    <?= form_open_multipart('profile/edit'); ?>
                                    <form class="form form-vertical">
                                        <div class="form-body">
                                            <div class="row">

                                                <div class="col-12 form-group">
                                                    <label>Image Profile</label>
                                                    <input type="file" class="dropify" data-max-file-size="1mb" name="image" data-default-file="<?= base_url('images/admin/') . $image ?>" />
                                                    <div class="form-group mt-5">
                                                    </div>
                                                    <div class="col-12 form-group">
                                                        <label for="name">User Name</label>
                                                        <input type="text" class="form-control" name="user_name" id="name" value="<?= $user_name ?>" required>
                                                    </div>
                                                    <div class="col-12 form-group">
                                                        <label for="email">Email</label>
                                                        <input type="email" class="form-control" name="email" id="email" value="<?= $email ?>" required>
                                                    </div>
                                                    <div class="col-12 form-group">
                                                        <label for="email">Password</label>
                                                        <input type="password" class="form-control" name="password" id="email" placeholder="change password here" required>
                                                    </div>

                                                    <div class="col-12">
                                                        <button type="submit" class="btn btn-primary mr-1 mb-1">Save</button>
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

            <!-- end of form add slider -->
        </div>
    </div>
</div>
<!-- END: Content-->