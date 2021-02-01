<?php
defined('BASEPATH') or exit('No direct script access allowed');

class User extends CI_Controller
{
    public function __construct()
    {
        parent::__construct();
        if ($this->session->userdata('user_name') == NULL && $this->session->userdata('password') == NULL) {
            redirect(base_url() . "login");
        }
        $this->load->model('Customerdata_model', 'cstm');
        $this->load->model('Driverdata_model', 'drv');
        $this->load->model('Merchantdata_model', 'mrc');
        $this->load->library('form_validation');
        $this->load->library('upload');
        $this->load->model('Appsettings_model', 'appset');
    }

    public function customerdata()
    {
        $getview['view'] = 'customerdata';
        $data['customer'] = $this->cstm->getAllcustomer();

        $getview['menu'] = $this->appset->getMenuAdmin();
        $this->load->view('includes/header', $getview);
        $this->load->view('user/customerdata', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function driverdata()
    {
        $getview['view'] = 'driverdata';
        $data['driver'] = $this->drv->getAlldriver();

        $getview['menu'] = $this->appset->getMenuAdmin();
        $this->load->view('includes/header', $getview);
        $this->load->view('user/driverdata', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function merchantdata()
    {
        $getview['view'] = 'merchantdata';
        $data['merchant'] = $this->mrc->getAllmerchant();

        $getview['menu'] = $this->appset->getMenuAdmin();
        $this->load->view('includes/header', $getview);
        $this->load->view('user/merchantdata', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function addcustomerview()
    {
        $getview['view'] = 'detailcustomer';

        $getview['menu'] = $this->appset->getMenuAdmin();
        $this->load->view('includes/header', $getview);
        $this->load->view('user/addcustomer');
        $this->load->view('includes/footer', $getview);
    }

    public function adddriver()
    {
        $getview['view'] = 'adddriver';
        $data['driverjob'] = $this->drv->driverjob();
        $getview['menu'] = $this->appset->getMenuAdmin();
        $this->load->view('includes/header', $getview);
        $this->load->view('user/adddriver', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function addmerchant()
    {
        $getview['view'] = 'addmerchant';
        $data['merchantcategory'] = $this->mrc->getmerchantcat();
        $data['service'] = $this->mrc->get_merchant_service();
        $getview['menu'] = $this->appset->getMenuAdmin();
        $this->load->view('includes/header', $getview);
        $this->load->view('user/addmerchant', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function addcustomer()
    {
        $password = html_escape($this->input->post('password', TRUE));
        $countrycode = html_escape($this->input->post('countrycode', TRUE));
        $phone = html_escape($this->input->post('phone', TRUE));
        $email = html_escape($this->input->post('email', TRUE));

        $this->form_validation->set_rules('customer_fullname', 'customer_fullname', 'trim|prep_for_form');
        $this->form_validation->set_rules('phone', 'phone', 'trim|prep_for_form|is_unique[customer.phone]');
        $this->form_validation->set_rules('email', 'email', 'trim|prep_for_form|is_unique[customer.email]');
        $this->form_validation->set_rules('password', 'password', 'trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {

            $config['upload_path']     = './images/customer/';
            $config['allowed_types'] = 'gif|jpg|png|jpeg';
            $config['max_size']         = '10000';
            $config['file_name']     = 'name';
            $config['encrypt_name']     = true;
            $this->upload->initialize($config);


            if ($this->upload->do_upload('customer_image')) {

                $photo = html_escape($this->upload->data('file_name'));
            } else {
                $photo = 'noimage.jpg';
            }

            $data             = [

                'id'                        => 'P' . time(),
                'phone'                     => html_escape($this->input->post('phone', TRUE)),
                'countrycode'               => html_escape($this->input->post('countrycode', TRUE)),
                'dob'                       => html_escape($this->input->post('dob', TRUE)),
                'token'                     => 'T' . time(),
                'customer_image'            => $photo,
                'customer_fullname'         => html_escape($this->input->post('customer_fullname', TRUE)),
                'phone_number'              => str_replace("+", "", $countrycode) . $phone,
                'email'                     => html_escape($this->input->post('email', TRUE)),
                'password'                  => sha1($password),

            ];


            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('user/addcustomerview');
            } else {

                $insert = $this->cstm->adddatacustomer($data);

                if ($insert) {
                    $this->session->set_flashdata('success', 'User Has Been Added');
                    redirect('user/customerdata');
                } else {
                    $this->session->set_flashdata('danger', 'Error, please try again!');
                    redirect('user/addcustomerview');
                }
            }
        } else {
            $this->session->set_flashdata('danger', 'Error, email or phone already exist');
            redirect('user/addcustomerview');
        }
    }

    public function deleteusers($id)
    {
        if (demo == TRUE) {
            $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
            redirect('users');
        } else {
            $data = $this->cstm->getusersbyid($id);
            $gambar = $data['customer_image'];
            unlink('images/customer/' . $gambar);

            $success = $this->cstm->deletedatauserbyid($id);
            if ($success) {
                $this->session->set_flashdata('success', 'User Has Been Deleted');
                redirect('user/customerdata');
            } else {
                $this->session->set_flashdata('danger', 'error, please try again!');
                redirect('user/customerdata');
            }
        }
    }

    public function block($id)
    {
        $success = $this->cstm->blockusersById($id);
        if ($success) {
            $this->session->set_flashdata('success', 'blocked');
            redirect('user/customerdata');
        } else {
            $this->session->set_flashdata('danger', 'error, please try again!');
            redirect('user/customerdata');
        }
    }

    public function unblock($id)
    {
        $success = $this->cstm->unblockusersById($id);
        if ($success) {
            $this->session->set_flashdata('success', 'unblocked');
            redirect('user/customerdata');
        } else {
            $this->session->set_flashdata('danger', 'error, please try again!');
            redirect('user/customerdata');
        }
    }

    public function adddriverdata()
    {

        $phone = html_escape($this->input->post('phone', TRUE));

        $this->form_validation->set_rules('driver_name', 'driver_name', 'trim|prep_for_form');
        $this->form_validation->set_rules('phone', 'Phone', 'trim|prep_for_form|is_unique[driver.phone]');
        $this->form_validation->set_rules('email', 'Email', 'trim|prep_for_form|is_unique[driver.email]');
        $this->form_validation->set_rules('password', 'password', 'trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {

            if (@$_FILES['photo']['name']) {

                $config['upload_path']     = './images/driverphoto/';
                $config['allowed_types'] = 'gif|jpg|png|jpeg';
                $config['max_size']         = '10000';
                $config['file_name']     = 'name';
                $config['encrypt_name']     = true;
                $this->upload->initialize($config);

                if ($this->upload->do_upload('photo')) {
                    $photo = html_escape($this->upload->data('file_name'));
                } else {
                    $photo = 'noimage.jpg';
                }
            }

            if ($this->form_validation->run() == TRUE) {
                if (@$_FILES['driver_license_images']['name']) {

                    $config['upload_path']     = './images/photofile/sim';
                    $config['allowed_types'] = 'gif|jpg|png|jpeg';
                    $config['max_size']         = '10000';
                    $config['file_name']     = 'name';
                    $config['encrypt_name']     = true;
                    $this->upload->initialize($config);

                    if ($this->upload->do_upload('driver_license_images')) {

                        $fotosim = html_escape($this->upload->data('file_name'));
                    } else {
                        $fotosim = 'noimage.jpg';
                    }
                }
            }

            if ($this->form_validation->run() == TRUE) {
                if (@$_FILES['idcard_images']['name']) {

                    $config['upload_path']     = './images/photofile/ktp';
                    $config['allowed_types'] = 'gif|jpg|png|jpeg';
                    $config['max_size']         = '10000';
                    $config['file_name']     = 'name';
                    $config['encrypt_name']     = true;
                    $this->upload->initialize($config);

                    if ($this->upload->do_upload('idcard_images')) {

                        $fotoktp = html_escape($this->upload->data('file_name'));
                    } else {
                        $fotoktp = 'noimage.jpg';
                    }
                }
            }


            $countrycode = html_escape($this->input->post('countrycode', TRUE));
            $id = 'D' . time();


            $datasignup             = [

                'id'                        => $id,
                'phone'                     => html_escape($this->input->post('phone', TRUE)),
                'countrycode'               => html_escape($this->input->post('countrycode', TRUE)),
                'dob'                       => html_escape($this->input->post('dob', TRUE)),
                'reg_id'                    => 'R' . time(),
                'photo'                      => $photo,
                'password'                   => sha1(time()),
                'driver_name'               => html_escape($this->input->post('driver_name', TRUE)),
                'phone_number'                => str_replace("+", "", $countrycode) . $phone,
                'email'                     => html_escape($this->input->post('email', TRUE)),
                'gender'                    => html_escape($this->input->post('gender', TRUE)),
                'driver_address'             => html_escape($this->input->post('driver_address', TRUE)),
                'job'                       => html_escape($this->input->post('job', TRUE)),
                'user_nationid'                    => html_escape($this->input->post('user_nationid', TRUE))

            ];



            $datakendaraan             = [

                'brand'                     => html_escape($this->input->post('brand', TRUE)),
                'type'                      => html_escape($this->input->post('type', TRUE)),
                'color'                     => html_escape($this->input->post('color', TRUE)),
                'vehicle_registration_number'           => html_escape($this->input->post('vehicle_registration_number', TRUE))

            ];

            $databerkas             = [

                'driver_id'                 => $id,
                'driver_license_images'                  => $fotosim,
                'idcard_images'                  => $fotoktp,
                'driver_license_id'                    => html_escape($this->input->post('driver_license_id', TRUE))

            ];
            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('driver/add');
            } else {

                $success = $this->drv->signup($datasignup, $datakendaraan, $databerkas);
                if ($success) {
                    $this->session->set_flashdata('success', 'Driver Has Been Added');
                    redirect('newregistration/newregdriver');
                } else {
                    $this->session->set_flashdata('danger', 'Error, please try again!');
                    redirect('user/adddriver');
                }
            }
        } else {
            $this->session->set_flashdata('danger', 'Error, please check detail!');
            redirect('user/adddriver');
        }
    }

    public function blockdriver($id)
    {
        $success = $this->drv->blockdriverbyid($id);
        if ($success) {
            $this->session->set_flashdata('success', 'blocked');
            redirect('user/driverdata');
        } else {
            $this->session->set_flashdata('danger', 'error, please try again!');
            redirect('user/driverdata');
        }
    }

    public function unblockdriver($id)
    {
        $success = $this->drv->unblockdriverbyid($id);
        if ($success) {
            $this->session->set_flashdata('success', 'unblocked');
            redirect('user/driverdata');
        } else {
            $this->session->set_flashdata('danger', 'error, please try again!');
            redirect('user/driverdata');
        }
    }

    public function deletedriver($id)
    {
        if (demo == TRUE) {
            $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
            redirect('user/driverdata');
        } else {
            $data = $this->drv->getdriverbyid($id);
            $gambar = $data['photo'];
            $gambarsim = $data['driver_license_images'];
            $gambarktp = $data['idcard_images'];
            unlink('images/driverphoto/' . $gambar);
            unlink('images/photofile/ktp/' . $gambarktp);
            unlink('images/photofile/sim/' . $gambarsim);

            $success = $this->drv->deletedriverbyid($id);
            if ($success) {
                $this->session->set_flashdata('success', 'Driver Has Been Deleted');
                redirect('user/driverdata');
            } else {
                $this->session->set_flashdata('danger', 'error, please try again!');
                redirect('user/driverdata');
            }
        }
    }

    public function addmerchantdata()
    {


        $this->form_validation->set_rules('partner_name', 'partner_name', 'trim|prep_for_form');
        $this->form_validation->set_rules('partner_address', 'partner_address', 'trim|prep_for_form');
        $this->form_validation->set_rules('partner_email', 'partner_email', 'trim|prep_for_form|is_unique[partner.partner_email]');
        $this->form_validation->set_rules('partner_phone', 'partner_phone', 'trim|prep_for_form|is_unique[partner.partner_phone]');
        $this->form_validation->set_rules('partner_country_code', 'partner_country_code', 'trim|prep_for_form');
        $this->form_validation->set_rules('partner_type_identity', 'partner_type_identity', 'trim|prep_for_form');
        $this->form_validation->set_rules('partner_identity_number', 'partner_identity_number', 'trim|prep_for_form');
        $this->form_validation->set_rules('merchant_name', 'merchant_name', 'trim|prep_for_form');
        $this->form_validation->set_rules('service_id', 'service_id', 'trim|prep_for_form');
        $this->form_validation->set_rules('merchant_category', 'merchant_category', 'trim|prep_for_form');
        $this->form_validation->set_rules('merchant_address', 'merchant_address', 'trim|prep_for_form');
        $this->form_validation->set_rules('open_hour', 'open_hour', 'trim|prep_for_form');
        $this->form_validation->set_rules('close_hour', 'close_hour', 'trim|prep_for_form');

        if ($this->input->post('merchant_category') == NUll) {
            $this->session->set_flashdata('danger', 'Please Add Category Merchant First');
            redirect('user/addmerchant');
        }

        if ($this->form_validation->run() == TRUE) {

            if (@$_FILES['merchant_image']['name']) {

                $config['upload_path']     = './images/merchant';
                $config['allowed_types'] = 'gif|jpg|png|jpeg';
                $config['max_size']         = '30000';
                $config['file_name']     = 'name';
                $config['encrypt_name']     = true;
                $this->upload->initialize($config);

                if ($this->upload->do_upload('merchant_image')) {

                    $fotomerchant = html_escape($this->upload->data('file_name'));
                }


                if ($this->form_validation->run() == TRUE) {
                    if (@$_FILES['idcard_images']['name']) {

                        $config['upload_path']     = './images/photofile/ktp';
                        $config['allowed_types'] = 'gif|jpg|png|jpeg';
                        $config['max_size']         = '30000';
                        $config['file_name']     = 'name';
                        $config['encrypt_name']     = true;
                        $this->upload->initialize($config);

                        if ($this->upload->do_upload('idcard_images')) {
                            $fotoktp = html_escape($this->upload->data('file_name'));
                        }
                    }
                }
            }

            $countrycode = html_escape($this->input->post('partner_country_code', TRUE));
            $phone = html_escape($this->input->post('partner_phone', TRUE));
            $id = 'M' . time();

            $datamerchant = [
                'service_id'                              => html_escape($this->input->post('service_id', TRUE)),
                'merchant_name'                         => html_escape($this->input->post('merchant_name', TRUE)),
                'merchant_address'                       => html_escape($this->input->post('merchant_address', TRUE)),
                'merchant_latitude'                     => html_escape($this->input->post('merchant_latitude', TRUE)),
                'merchant_longitude'                    => html_escape($this->input->post('merchant_longitude', TRUE)),
                'open_hour'                              => html_escape($this->input->post('open_hour', TRUE)),
                'close_hour'                             => html_escape($this->input->post('close_hour', TRUE)),
                'merchant_category'                     => html_escape($this->input->post('merchant_category', TRUE)),
                'merchant_image'                         => $fotomerchant,
                'merchant_telephone_number'                      => str_replace("+", "", $countrycode) . $phone,
                'merchant_country_code'                 => $countrycode,
                'merchant_phone_number'                        => $phone,
                'merchant_status'                       => '0',
                'merchant_token'                        => sha1(time())
            ];

            $idmerchant = $this->mrc->insertmerchant($datamerchant);

            $datamitra = [
                'partner_id'                          => $id,
                'partner_name'                        => html_escape($this->input->post('partner_name', TRUE)),
                'partner_type_identity'             => html_escape($this->input->post('partner_type_identity', TRUE)),
                'partner_identity_number'             => html_escape($this->input->post('partner_identity_number', TRUE)),
                'partner_address'                      => html_escape($this->input->post('partner_address', TRUE)),
                'partner_email'                       => html_escape($this->input->post('partner_email', TRUE)),
                'password'                          => sha1(time()),
                'partner_telephone'                     => str_replace("+", "", $countrycode) . $phone,
                'partner_country_code'                => $countrycode,
                'partner_phone'                       => $phone,
                'merchant_id'                       => $idmerchant,
                'partner'                           => '0',
                'partner_status'                      => '0'
            ];

            $databerkas = [
                'driver_id'                          => $id,
                'idcard_images'                          => $fotoktp,
            ];

            $datasaldo = [
                'id_user' => $id,
                'balance' => 0
            ];

            $success = $this->mrc->addmerchant($datamitra, $databerkas, $datasaldo);
            if ($success) {
                $this->session->set_flashdata('success', 'Merchant Has Been Added');
                redirect('newregistration/newregmerchant');
            } else {
                $this->session->set_flashdata('danger', 'Error, please try again!');
                redirect('user/addmerchant');
            }
        } else {

            $this->session->set_flashdata('danger', 'Error, please try again!');
            redirect('user/addmerchant');
        }
    }

    public function deletemerchant($id)
    {
        if (demo == TRUE) {
            $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
            redirect('user/merchantdata');
        } else {
            $success = $this->mrc->deletemitrabyid($id);
            if ($success) {
                $berkas = $this->mrc->getfilebyid($id);
                $fotoktp = $berkas['idcard_images'];
                unlink('images/photofile/ktp/' . $fotoktp);
                $this->session->set_flashdata('success', 'Owner Merchant Has Been Deleted');
                redirect('user/merchantdata');
            } else {
                $this->session->set_flashdata('danger', 'Error, please try again!');
                redirect('user/merchantdata');
            }
        }
    }

    public function blockmerchant($id)
    {
        $success = $this->mrc->blockmitrabyid($id);
        if ($success) {
            $this->session->set_flashdata('success', 'blocked');
            redirect('user/merchantdata');
        } else {
            $this->session->set_flashdata('danger', 'Error, please try again!');
            redirect('user/merchantdata');
        }
    }

    public function unblockmerchant($id)
    {
        $success = $this->mrc->unblockmitrabyid($id);
        if ($success) {
            $this->session->set_flashdata('success', 'unblocked');
            redirect('user/merchantdata');
        } else {
            $this->session->set_flashdata('danger', 'Error, please try again!');
            redirect('user/merchantdata');
        }
    }
}
