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
                                <h4 class="card-title">Edit Vehicle Type</h4>
                            </div>
                            <br>
                            <div class="card-content">
                                <div class="card-body">
                                    <?= form_open_multipart('service/editpartnerjob'); ?>
                                    <form class="form form-vertical">
                                        <div class="form-body">
                                            <div class="row">

                                                <input type="hidden" name="id" value="<?= $partnerjob['id'] ?>">
                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="type">Icon</label>
                                                        <select class="select2 form-group" name="icon">
                                                            <option value="1" <?php if ($partnerjob['icon'] == '1') { ?>selected<?php } ?>>Bike Icon</option>
                                                            <option value="2" <?php if ($partnerjob['icon'] == '2') { ?>selected<?php } ?>>Car Icon</option>
                                                            <option value="3" <?php if ($partnerjob['icon'] == '3') { ?>selected<?php } ?>>Truck Icon</option>
                                                            <option value="4" <?php if ($partnerjob['icon'] == '4') { ?>selected<?php } ?>>Delivery Bike Icon</option>
                                                            <option value="5" <?php if ($partnerjob['icon'] == '5') { ?>selected<?php } ?>>HatchBack Car Icon</option>
                                                            <option value="6" <?php if ($partnerjob['icon'] == '6') { ?>selected<?php } ?>>SUV Car Icon</option>
                                                            <option value="7" <?php if ($partnerjob['icon'] == '7') { ?>selected<?php } ?>>VAN Car Icon</option>
                                                            <option value="8" <?php if ($partnerjob['icon'] == '8') { ?>selected<?php } ?>>Bicycle Icon</option>
                                                            <option value="9" <?php if ($partnerjob['icon'] == '9') { ?>selected<?php } ?>>Tuk Tuk Icon</option>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="col-12 form-group">
                                                    <label for="title">Vehicle Type</label>
                                                    <input type="text" class="form-control" name="driver_job" id="job" placeholder="enter job title" value="<?= $partnerjob['driver_job'] ?>" required>
                                                </div>

                                                <div class="col-12 form-group">
                                                    <label for="gender">status</label>
                                                    <select class="select2 form-group" name="status_job" id="statusjob">
                                                        <option value="1" <?php if ($partnerjob['status_job'] == '1') { ?>selected<?php } ?>>Active</option>
                                                        <option value="0" <?php if ($partnerjob['status_job'] == '0') { ?>selected<?php } ?>>NonActive</option>
                                                    </select>
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