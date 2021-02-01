<div class="sidenav-overlay"></div>
<div class="drag-target"></div>



<?php if ($this->session->flashdata('success')) { ?>

    <section id="animation">

        <div id="type-success">

            <input type="hidden" id="desctoast" value="<?php echo $this->session->flashdata('success'); ?>" />
        </div>
    </section>
<?php } ?>

<?php if ($this->session->flashdata('danger')) { ?>

    <section id="animation">

        <div id="type-danger">

            <input type="hidden" id="desctoast" value="<?php echo $this->session->flashdata('danger'); ?>" />
        </div>
    </section>
<?php } ?>

<?php if ($this->session->flashdata('demo')) { ?>

    <section id="animation">

        <div id="type-danger">

            <input type="hidden" id="desctoast" value="<?php echo $this->session->flashdata('demo'); ?>" />
        </div>
    </section>
<?php } ?>


<!-- BEGIN: Footer-->
<footer class="footer footer-static footer-light">
    <p class="clearfix blue-grey lighten-2 mb-0"><span class="float-md-left d-block d-md-inline-block mt-25">COPYRIGHT &copy; 2020<a class="text-bold-800 grey darken-2" href="#" target="_blank">Ourdevelops,</a>All rights Reserved</span><span class="float-md-right d-none d-md-block">Hand-crafted & Made with<i class="feather icon-heart pink"></i></span>
        <button class="btn btn-primary btn-icon scroll-top" type="button"><i class="feather icon-arrow-up"></i></button>
    </p>
</footer>
<!-- END: Footer-->


<!-- BEGIN: Vendor JS-->
<script src="<?= base_url(); ?>asset/app-assets/vendors/js/vendors.min.js"></script>

<script src="<?= base_url(); ?>asset/app-assets/vendors/js/extensions/toastr.min.js"></script>
<!-- BEGIN Vendor JS-->

<!-- BEGIN: Page Vendor JS-->


<script src="<?= base_url(); ?>asset/app-assets/vendors/js/charts/apexcharts.min.js"></script>
<script src="<?= base_url(); ?>asset/app-assets/vendors/js/tables/datatable/vfs_fonts.js"></script>
<script src="<?= base_url(); ?>asset/app-assets/vendors/js/tables/datatable/datatables.min.js"></script>
<script src="<?= base_url(); ?>asset/app-assets/vendors/js/tables/datatable/datatables.buttons.min.js"></script>
<script src="<?= base_url(); ?>asset/app-assets/vendors/js/tables/datatable/buttons.html5.min.js"></script>
<script src="<?= base_url(); ?>asset/app-assets/vendors/js/tables/datatable/buttons.print.min.js"></script>
<script src="<?= base_url(); ?>asset/app-assets/vendors/js/tables/datatable/buttons.bootstrap.min.js"></script>
<script src="<?= base_url(); ?>asset/app-assets/vendors/js/tables/datatable/datatables.bootstrap4.min.js"></script>

<script src="<?= base_url(); ?>asset/app-assets/vendors/js/tables/datatable/dataTables.select.min.js"></script>
<script src="<?= base_url(); ?>asset/app-assets/vendors/js/tables/datatable/datatables.checkboxes.min.js"></script>
<script src="<?= base_url(); ?>asset/app-assets/vendors/js/extensions/dropzone.min.js"></script>

<script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/intlTelInput-jquery.min.js"></script>




<!-- END: Page Vendor JS-->

<!-- BEGIN: Theme JS-->
<script src="<?= base_url(); ?>asset/app-assets/js/core/app-menu.js"></script>
<script src="<?= base_url(); ?>asset/app-assets/js/core/app.js"></script>
<script src="<?= base_url(); ?>asset/app-assets/js/scripts/components.js"></script>
<!-- END: Theme JS-->

<!-- BEGIN: Page JS-->
<?php if ($view == "dashboard") { ?>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/chart-dashboard.js"></script>
<?php } ?>

<?php if ($view == "statisticgeneral") { ?>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/chart-general.js"></script>
<?php } ?>

<?php if ($view == "statistictransaction") { ?>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/extensions/swiper.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/swiper.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/transactionstatistic.js"></script>
<?php } else if ($view == "valuation") { ?>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/extensions/swiper.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/swiper.js"></script>
<?php } ?>



<?php if ($view == "statisticfinance") { ?>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/financestatistic.js"></script>
<?php } ?>

<?php if ($view == "drivermap") { ?>

    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/mapbox-gl.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/maps-tracking-mapbox.js"></script>
<?php } ?>

<?php if ($view == "merchantmap") { ?>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/mapbox-gl.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/maps-merchant-mapbox.js"></script>
<?php } ?>

<?php if ($view == "appsettings") { ?>
    <script src="<?= base_url(); ?>asset/node_modules/summernote/dist/summernote-bs4.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/extensions/jquery.steps.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/forms/validation/jquery.validate.min.js"></script>
    <script src="<?= base_url(); ?>asset/node_modules/dropify/dist/js/dropify.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/dropify.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/forms/select/select2.full.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/quilleditor.js"></script>
<?php } ?>

<?php if ($view == "emailsettings") { ?>
    <script src="<?= base_url(); ?>asset/node_modules/summernote/dist/summernote-bs4.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/extensions/jquery.steps.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/forms/validation/jquery.validate.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/quilleditor.js"></script>
<?php } ?>

<?php if ($view == "sendemail") { ?>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/forms/select/select2.full.min.js"></script>
    <script src="<?= base_url(); ?>asset/node_modules/summernote/dist/summernote-bs4.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/extensions/jquery.steps.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/forms/validation/jquery.validate.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/quilleditor.js"></script>
<?php } ?>

<?php if ($view == "addnews") { ?>
    <script src="<?= base_url(); ?>asset/node_modules/summernote/dist/summernote-bs4.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/extensions/jquery.steps.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/forms/validation/jquery.validate.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/quilleditor.js"></script>
<?php } ?>

<?php if ($view == "detaildriver") { ?>
    <script src="<?= base_url(); ?>asset/node_modules/dropify/dist/js/dropify.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/dropify.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/extensions/swiper.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/extensions/dropzone.min.js"></script>

    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/forms/validation/jqBootstrapValidation.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/forms/validation/jquery.validate.min.js"></script>

    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/intlTelInput-jquery.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/forms/select/select2.full.min.js"></script>

    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/modal/components-modal.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/detaildriver.js"></script>
<?php } ?>

<?php if ($view == "detailmerchant") { ?>
    <script src="<?= base_url(); ?>asset/node_modules/dropify/dist/js/dropify.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/dropify.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/extensions/swiper.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/forms/validation/jqBootstrapValidation.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/forms/select/select2.full.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/modal/components-modal.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/detailmerchant.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/duit.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/mapbox-gl.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/maps-picker-mapbox.js"></script>

<?php } ?>

<?php if ($view == "addmerchant") { ?>
    <script src="<?= base_url(); ?>asset/node_modules/dropify/dist/js/dropify.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/dropify.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/forms/validation/jqBootstrapValidation.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/forms/select/select2.full.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/modal/components-modal.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/addmerchant.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/mapbox-gl.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/maps-picker-mapbox.js"></script>

<?php } ?>

<?php if ($view == "detailtransaction") { ?>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/pages/invoice.js"></script>
<?php } ?>

<?php if ($view == "detailcustomer") { ?>
    <script src="<?= base_url(); ?>asset/node_modules/dropify/dist/js/dropify.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/dropify.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/detailcustomer.js"></script>
<?php } ?>

<?php if ($view == "adddriver") { ?>
    <script src="<?= base_url(); ?>asset/node_modules/dropify/dist/js/dropify.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/dropify.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/extensions/swiper.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/extensions/dropzone.min.js"></script>

    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/forms/validation/jqBootstrapValidation.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/forms/validation/jquery.validate.min.js"></script>

    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/intlTelInput-jquery.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/forms/select/select2.full.min.js"></script>

    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/modal/components-modal.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/detaildriver.js"></script>
<?php } ?>

<?php if ($view == "edititemview") { ?>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/forms/select/select2.full.min.js"></script>
    <script src="<?= base_url(); ?>asset/node_modules/dropify/dist/js/dropify.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/dropify.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/edititem.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/duit.js"></script>
<?php } ?>

<?php if ($view == "additemview") { ?>
    <script src="<?= base_url(); ?>asset/node_modules/dropify/dist/js/dropify.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/dropify.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/forms/select/select2.full.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/additem.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/duit.js"></script>
<?php } ?>

<?php if ($view == "addsliderdata") { ?>
    <script src="<?= base_url(); ?>asset/node_modules/dropify/dist/js/dropify.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/forms/select/select2.full.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/dropify.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/addslider.js"></script>
<?php } ?>

<?php if ($view == "addpromotioncode") { ?>
    <script src="<?= base_url(); ?>asset/node_modules/dropify/dist/js/dropify.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/forms/select/select2.full.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/dropify.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/addpromocode.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/duit.js"></script>
<?php } ?>

<?php if ($view == "addservice") { ?>
    <script src="<?= base_url(); ?>asset/node_modules/dropify/dist/js/dropify.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/forms/select/select2.full.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/dropify.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/addtopup.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/duit.js"></script>
<?php } ?>

<?php if ($view == "editservice") { ?>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/forms/select/select2.full.min.js"></script>
    <script src="<?= base_url(); ?>asset/node_modules/dropify/dist/js/dropify.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/dropify.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/addtopup.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/duit.js"></script>
<?php } ?>

<?php if ($view == "addtopup") { ?>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/forms/select/select2.full.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/addtopup.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/duit.js"></script>
<?php } ?>

<?php if ($view == "editsliderdata") { ?>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/forms/select/select2.full.min.js"></script>
    <script src="<?= base_url(); ?>asset/node_modules/dropify/dist/js/dropify.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/dropify.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/editslider.js"></script>
<?php } ?>

<?php if ($view == "addmerchnatcategory") { ?>
    <script src="<?= base_url(); ?>asset/app-assets/vendors/js/forms/select/select2.full.min.js"></script>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/merchantcategory.js"></script>
<?php } ?>

<script src="<?= base_url(); ?>asset/app-assets/js/scripts/datatables/datatable.js"></script>
<script src="<?= base_url(); ?>asset/app-assets/js/scripts/ui/data-list-view.js"></script>

<?php if ($this->session->flashdata('success')) { ?>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/toastrsuccess.js"></script>
<?php } ?>

<?php if ($this->session->flashdata('danger') || $this->session->flashdata('demo')) { ?>
    <script src="<?= base_url(); ?>asset/app-assets/js/scripts/ourdevelops/toastrerror.js"></script>
<?php } ?>





<!-- END: Page JS-->

</body>
<!-- END: Body-->

</html>