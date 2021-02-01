<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- start form add customer -->

            <section id="basic-vertical-layouts">
                <div class="row match-height justify-content-center">
                    <div class="col-md-6 col-12">
                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">Add Customer</h4>
                            </div>
                            <br>
                            <div class="card-content">
                                <div class="card-body">
                                    <?= form_open_multipart('user/addcustomer'); ?>
                                    <form class="form form-vertical">
                                        <div class="form-body">
                                            <div class="row">

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label>Image</label>
                                                        <input type="file" name="customer_image" class="dropify" data-max-file-size="1mb" />
                                                    </div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="customer_fullname">Name</label>
                                                        <input type="text" id="customer_fullname" class="form-control" name="customer_fullname" placeholder="enter name" required="required"></div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="dob">Date of birth</label>
                                                        <input type="date" id="dob" class="form-control" name="dob" placeholder="enter name" required="required"></div>
                                                </div>

                                                <div class="col-12">
                                                    <label>Phone</label>

                                                    <div class="row">

                                                        <input type="hidden" id="countryec" value="">

                                                        <div class="form-group col-4">
                                                            <input type="tel" id="txtPhone" class="form-control" name="countrycode" required="required">
                                                        </div>
                                                        <div class=" form-group col-8">
                                                            <input type="text" class="form-control" id="phone" name="phone" placeholder="enter phone number" value="" required="required">
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="email">Email</label>
                                                        <input type="email" id="email" class="form-control" name="email" placeholder="enter email" required="required"></div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="password">Password</label>
                                                        <input type="password" id="password" class="form-control" name="password" placeholder="enter password" required="required"></div>
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

            <!-- end of form add customer -->
        </div>
    </div>
</div>
<!-- END: Content-->