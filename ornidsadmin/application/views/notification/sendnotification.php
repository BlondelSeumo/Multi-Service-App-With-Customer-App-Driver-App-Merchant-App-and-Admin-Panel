<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- start send notification -->

            <section id="basic-vertical-layouts">
                <div class="row match-height justify-content-center">
                    <div class="col-md-8 col-12">
                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">Send Notification</h4>
                            </div>
                            <br>
                            <div class="card-content">
                                <div class="card-body">
                                    <?= form_open_multipart('notification/send'); ?>
                                    <form class="form form-vertical">
                                        <div class="form-body">
                                            <div class="row">
                                                <!-- start send notification form -->
                                                <div class="col-12 form-group">
                                                    <label for="data-category">
                                                        Send To
                                                    </label>
                                                    <select class="select2 form-control" id="data-category" name='topic'>
                                                        <option value="customer">Users</option>
                                                        <option value="driver">Drivers</option>
                                                        <option value="mitra">Merchant Partner</option>
                                                        <option value="ouride">All</option>
                                                    </select>
                                                </div>

                                                <div class="col-12">
                                                    <div class="form-group">
                                                        <label for="first-name-vertical">Title</label>
                                                        <input type="text" class="form-control" placeholder="notification" name="title" required>
                                                    </div>

                                                    <fieldset class="form-group">
                                                        <label for="first-name-vertical">Notification Content</label>
                                                        <textarea type="text" class="form-control" placeholder="enter notification title" name="message" required></textarea>
                                                    </fieldset>

                                                    <!-- end of send notification form -->

                                                    <div class="col-12">
                                                        <button type="submit" class="btn btn-primary mr-1 mb-1">Send</button>
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

            <!-- end of notification -->
        </div>
    </div>
</div>
<!-- END: Content-->