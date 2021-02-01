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
                                    <?= form_open_multipart('settings/adddatabank'); ?>
                                    <form class="form form-vertical">
                                        <div class="form-body">
                                            <div class="row">

                                                <div class="col-12 form-group">
                                                    <input type="file" class="dropify" name="bank_logo" data-max-file-size="3mb" required />
                                                </div>
                                                <div class="col-12 form-group">
                                                    <label for="birthdate">Bank Name</label>
                                                    <input type="text" class="form-control" id="" name="bank_name" placeholder="enter bank name" required>
                                                </div>
                                                <div class="col-12 form-group">
                                                    <label for="birthdate">Account Number</label>
                                                    <input type="text" class="form-control" id="" name="bank_account" placeholder="enter bank name" required>
                                                </div>
                                                <div class="col-12 form-group">
                                                    <label for="gender">status</label>
                                                    <select class="select2 form-group" name="bank_status" style="width:100%">
                                                        <option value="1">Active</option>
                                                        <option value="0">Nonactive</option>
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