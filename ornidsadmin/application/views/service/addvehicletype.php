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
                                <h4 class="card-title">Add Vehicle Type</h4>
                            </div>
                            <br>
                            <div class="card-content">
                                <div class="card-body">
                                    <?= form_open_multipart('service/addvehicletypedata'); ?>
                                    <form class="form form-vertical">
                                        <div class="form-body">
                                            <div class="row">

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="type">Icon</label>
                                                        <select class="select2 form-group" name="icon">
                                                            <option value="1">Bike Icon</option>
                                                            <option value="2">Car Icon</option>
                                                            <option value="3">Truck Icon</option>
                                                            <option value="4">Delivery Bike Icon</option>
                                                            <option value="5">HatchBack Car Icon</option>
                                                            <option value="6">SUV Car Icon</option>
                                                            <option value="7">VAN Car Icon</option>
                                                            <option value="8">Bicycle Icon</option>
                                                            <option value="9">Tuk Tuk Icon</option>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="col-12 form-group">
                                                    <label for="title">Vehicle Type</label>
                                                    <input type="text" class="form-control" name="driver_job" id="job" placeholder="enter job title" required>
                                                </div>

                                                <div class="col-12 form-group">
                                                    <label for="gender">status</label>
                                                    <select class="select2 form-group" name="status_job" id="statusjob">
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