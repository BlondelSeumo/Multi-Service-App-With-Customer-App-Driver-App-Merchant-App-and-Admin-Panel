<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- start form add driver -->

            <section id="basic-vertical-layouts">
                <div class="row match-height justify-content-center">
                    <div class="col-md-6 col-12">
                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">Add Driver</h4>
                            </div>
                            <br>
                            <div class="card-content">
                                <div class="card-body">
                                    <?= form_open_multipart('user/adddriverdata'); ?>
                                    <form class="form form-vertical">
                                        <div class="form-body">
                                            <div class="row">

                                                <h5 class="col-12 text-muted">Driver Info
                                                </h5>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label>Image</label>
                                                        <input type="file" name="photo" class="dropify" data-max-file-size="1mb" />
                                                    </div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="driver_name">Name</label>
                                                        <input type="text" id="name" class="form-control" name="driver_name" placeholder="enter name" required></div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="password">Password</label>
                                                        <input type="password" class="form-control" name="password" placeholder="enter password" required></div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="driver_address">Address</label>
                                                        <fieldset class="form-group">
                                                            <textarea class="form-control" id="basicTextarea" rows="3" name="driver_address" required></textarea>
                                                        </fieldset>
                                                    </div>
                                                </div>

                                                <div class="col-12 form-group">
                                                    <label for="gender">
                                                        Gender
                                                    </label>
                                                    <select class="select2 form-control" id="data-gender" name="gender" required>
                                                        <option value="Male">Male</option>
                                                        <option value="Female">Female</option>
                                                    </select>
                                                </div>

                                                <div class="col-12 form-group">
                                                    <label for="jobservice">
                                                        Job Service
                                                    </label>
                                                    <select class="select2 form-control" id="data-job" name="job" required>
                                                        <?php foreach ($driverjob as $drj) { ?>
                                                            <option value="<?= $drj['id'] ?>"><?= $drj['driver_job'] ?></option>
                                                        <?php } ?>
                                                    </select>
                                                </div>


                                                <h5 class="col-12 text-muted mt-2">Driver Identity
                                                </h5>

                                                <div class="col-12">
                                                    <div class="col-12">
                                                        <div class="form-group">
                                                            <label>ID Card Image</label>
                                                            <input type="file" name="idcard_images" class="dropify" data-max-file-size="1mb" />
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="user_nationid">Id Card Number</label>
                                                        <input type="text" id="user_nationid" class="form-control" name="user_nationid" placeholder="enter id number" required></div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label>Driver License Image</label>
                                                        <input type="file" name="driver_license_images" class="dropify" data-max-file-size="1mb" />
                                                    </div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="driver_license_id">Driver License Number</label>
                                                        <input type="text" id="driver_license_id" class="form-control" name="driver_license_id" placeholder="enter driver license number" required></div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="dob">Date Of Birth</label>
                                                        <input type="date" id="dob" class="form-control" name="dob" required></div>
                                                </div>

                                                <h5 class="col-12 text-muted mt-2">Driver Contact
                                                </h5>

                                                <div class="col-12">
                                                    <label>Phone</label>

                                                    <div class="row">

                                                        <input type="hidden" id="country" value="+62">

                                                        <div class="form-group col-4">
                                                            <input type="tel" id="txtPhone" class="form-control" name="countrycode" required>
                                                        </div>
                                                        <div class=" form-group col-8">
                                                            <input type="text" class="form-control" id="phone" name="phone" placeholder="enter phone number" required>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="email">Email</label>
                                                        <input type="email" id="email" class="form-control" name="email" placeholder="enter email" required></div>
                                                </div>

                                                <h5 class="col-12 text-muted mt-2">Driver Vehicle
                                                </h5>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="brand">Vehicle Brand</label>
                                                        <input type="text" id="brand" class="form-control" name="brand" placeholder="enter vehicle brand" required></div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="type">Brand Variant</label>
                                                        <input type="text" id="type" class="form-control" name="type" placeholder="enter vehicle variant" required></div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="color">Vehicle Color</label>
                                                        <input type="text" id="color" class="form-control" name="color" placeholder="enter vehicle color" required></div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="vehicle_registration_number">Vehicle Registration Number</label>
                                                        <input type="text" id="vehicle_registration_number" class="form-control" name="vehicle_registration_number" placeholder="enter vehicle registration number" required></div>
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

            <!-- end of form add driver -->
        </div>
    </div>
</div>
<!-- END: Content-->