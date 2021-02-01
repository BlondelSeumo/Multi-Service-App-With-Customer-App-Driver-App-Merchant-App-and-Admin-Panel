<!-- BEGIN: Content-->
<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="header-navbar-shadow"></div>
    <div class="content-wrapper">
        <div class="content-body">
            <!-- start send email -->

            <section id="basic-vertical-layouts">
                <div class="row match-height justify-content-center">
                    <div class="col-md-8 col-12">
                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">Send Email</h4>
                            </div>
                            <br>
                            <div class="card-content">
                                <div class="card-body">
                                    <?= form_open_multipart('notification/sendemaildata'); ?>
                                    <form class="form form-vertical">
                                        <div class="form-body">
                                            <div class="row">
                                                <!-- start send email template -->
                                                <div class="col-12 form-group">
                                                    <label for="newscategory">Send To</label>
                                                    <select id="getFname" onchange="admSelectCheck(this);" class="select2 form-group" style="width:100%" name="sendto">
                                                        <option id="users" value="users">Users</option>
                                                        <option id="pengendara" value="drivers">Drivers</option>
                                                        <option id="merchant" value="merchant">Merchant</option>
                                                        <option id="other" value="other">Others</option>
                                                    </select>
                                                </div>
                                                <div id="usercheck" style="display:block;" class="col-12 form-group">
                                                    <label for="newscategory">Users</label>
                                                    <select class="select2 form-group" style="width:100%" name="emailpelanggan">
                                                        <?php foreach ($user as $us) { ?>
                                                            <option value="<?= $us['email'] ?>"><?= $us['customer_fullname'] ?></option>
                                                        <?php } ?>
                                                    </select>
                                                </div>

                                                <div id="drivercheck" style="display:none;" class="col-12 form-group">
                                                    <label for="newscategory">Drivers</label>
                                                    <select class="select2 form-group" style="width:100%" name="emaildriver">
                                                        <?php foreach ($driver as $dr) { ?>
                                                            <option value="<?= $dr['email'] ?>"><?= $dr['driver_name'] ?></option>
                                                        <?php } ?>
                                                    </select>
                                                </div>

                                                <div id="merchantcheck" style="display:none;" class="col-12 form-group">
                                                    <label for="newscategory">Merchant</label>
                                                    <select class="select2 form-group" style="width:100%" name="emailmitra">
                                                        <?php foreach ($partner as $mt) { ?>
                                                            <option value="<?= $mt['partner_email'] ?>"><?= $mt['partner_name'] ?></option>
                                                        <?php } ?>
                                                    </select>
                                                </div>

                                                <div id="othercheck" style="display:none;" class="col-12 form-group">
                                                    <label for="newstitle">Others</label>
                                                    <input type="email" class="form-control" id="linktes" placeholder="enter email Address" name="emailothers">
                                                </div>

                                                <div class="col-12 form-group">
                                                    <label for="newstitle">title</label>
                                                    <input type="text" class="form-control" id="newstitle" placeholder="enter email Address" name="subject" required>
                                                </div>
                                                <div class="col-12 form-group">
                                                    <label for="newscontent">Email Content</label>
                                                    <textarea type="text" class="form-control" id="summernoteExample1" placeholder="Location" name="content" required></textarea>
                                                </div>
                                                <!-- end of send email template -->

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

            <!-- end of send email -->
        </div>
    </div>
</div>
<!-- END: Content-->