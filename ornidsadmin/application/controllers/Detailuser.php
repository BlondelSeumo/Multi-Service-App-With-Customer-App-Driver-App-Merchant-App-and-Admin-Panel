<?php
defined('BASEPATH') or exit('No direct script access allowed');

class Detailuser extends CI_Controller
{
    public function __construct()
    {
        parent::__construct();
        if ($this->session->userdata('user_name') == NULL && $this->session->userdata('password') == NULL) {
            redirect(base_url() . "login");
        }
        $this->load->library('session');
        $this->load->model('Appsettings_model', 'appset');
        $this->load->model('Driverdata_model', 'drv');
        $this->load->model('Merchantdata_model', 'mrc');
        $this->load->model('Customerdata_model', 'cstm');
        $this->load->model('Customer_model');
        $this->load->model('Email_model');
        $this->load->model('Appsettings_model', 'app');
        $this->load->library('form_validation');
        $this->load->library('upload');
    }

    public function detaildriver($id)
    {
        $getview['view'] = 'detaildriver';
        $getview['menu'] = $this->appset->getMenuAdmin();
        $data['driver'] = $this->drv->getdriverbyid($id);
        $data['currency'] = $this->appset->getcurrency();
        $data['countorder'] = $this->drv->countorder($id);
        $data['transaction'] = $this->drv->transaction($id);
        $data['wallet'] = $this->drv->wallet($id);
        $data['driverjob'] = $this->drv->driverjob();

        $this->load->view('includes/header', $getview);
        $this->load->view('detailuser/detaildriver', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function editdriver()
    {

        $this->form_validation->set_rules('driver_name', 'driver_name', 'trim|prep_for_form');
        $this->form_validation->set_rules('password', 'password', 'trim|prep_for_form');
        $this->form_validation->set_rules('driver_address', 'driver_address', 'trim|prep_for_form');
        $this->form_validation->set_rules('gender', 'gender', 'trim|prep_for_form');
        $this->form_validation->set_rules('job', 'job', 'trim|prep_for_form');

        $id = html_escape($this->input->post('id', TRUE));
        if ($this->form_validation->run() == TRUE) {

            @$_FILES['photo']['name'];

            if ($_FILES != NULL) {

                $config['upload_path']     = './images/driverphoto/';
                $config['allowed_types'] = 'gif|jpg|png|jpeg';
                $config['max_size']         = '10000';
                $config['file_name']     = 'name';
                $config['encrypt_name']     = true;
                $this->upload->initialize($config);


                $datafoto = $this->drv->getdriverbyid($id);

                if ($this->input->post('password') != NULL) {
                    $password = sha1($this->input->post('password', TRUE));
                } else {
                    $password = $datafoto['password'];
                }

                if ($this->upload->do_upload('photo')) {
                    if ($datafoto['photo'] != 'noimage.jpg' || $datafoto['photo'] != '') {
                        $gambar = $datafoto['photo'];
                        unlink('images/driverphoto/' . $gambar);
                    }

                    $photo = html_escape($this->upload->data('file_name'));
                } else {
                    $photo = $datafoto['photo'];
                }
            }

            $data             = [
                'photo'           => $photo,
                'id'                    => $id,
                'driver_name'           => html_escape($this->input->post('driver_name', TRUE)),
                'password'           => $password,
                'gender'                => html_escape($this->input->post('gender', TRUE)),
                'job'                => html_escape($this->input->post('job', TRUE)),
                'driver_address'         => html_escape($this->input->post('driver_address', TRUE))
            ];


            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('detailuser/detaildriver/' . $this->input->post('id', TRUE));
            } else {
                $id = html_escape($this->input->post('id', TRUE));
                $success = $this->drv->editdatainfo($data);
                if ($success) {
                    $this->session->set_flashdata('success', 'Driver info Has Been Change');
                    redirect('detailuser/detaildriver/' . $id);
                } else {
                    $this->session->set_flashdata('danger', 'Error, please try again!');
                    redirect('detailuser/detaildriver/' . $id);
                }
            }
        } else {
            $this->session->set_flashdata('danger', 'Error, please try again!');
            redirect('detailuser/detaildriver/' . $id);
        }
    }

    public function editidentity()
    {

        $this->form_validation->set_rules('user_nationid', 'user_nationid', 'trim|prep_for_form');
        $this->form_validation->set_rules('driver_license_id', 'driver_license_id', 'trim|prep_for_form');
        $this->form_validation->set_rules('dob', 'dob', 'trim|prep_for_form');

        $id = html_escape($this->input->post('id', TRUE));
        $datafile = $this->drv->getdriverbyid($id);
        $idcard_images = $datafile['idcard_images'];
        $driver_license_images = $datafile['driver_license_images'];

        if ($this->form_validation->run() == TRUE) {

            if (@$_FILES['idcard_images']['name']) {

                $config['upload_path']     = './images/photofile/ktp';
                $config['allowed_types'] = 'gif|jpg|png|jpeg';
                $config['max_size']         = '10000';
                $config['file_name']     = 'name';
                $config['encrypt_name']     = true;
                $this->upload->initialize($config);

                if ($this->upload->do_upload('idcard_images')) {
                    if ($datafile['idcard_images'] != 'noimage.jpg' || $datafile['idcard_images'] != '') {
                        $gambar = $datafile['idcard_images'];
                        unlink('images/photofile/ktp/' . $gambar);
                    }
                    $idcard_images = html_escape($this->upload->data('file_name'));
                }
            }
            if (@$_FILES['driver_license_images']['name']) {

                $config['upload_path']     = './images/photofile/sim';
                $config['allowed_types'] = 'gif|jpg|png|jpeg';
                $config['max_size']         = '10000';
                $config['file_name']     = 'name';
                $config['encrypt_name']     = true;
                $this->upload->initialize($config);

                if ($this->upload->do_upload('driver_license_images')) {
                    if ($datafile['driver_license_images'] != 'noimage.jpg' || $datafile['driver_license_images'] != '') {
                        $gambar = $datafile['driver_license_images'];
                        unlink('images/photofile/sim/' . $gambar);
                    }

                    $driver_license_images = html_escape($this->upload->data('file_name'));
                }
            }



            $data = [
                'idcard_images'           => $idcard_images,
                'driver_license_images'           => $driver_license_images,
                'driver_license_id'           => html_escape($this->input->post('driver_license_id', TRUE)),
                'driver_id'               => html_escape($this->input->post('id', TRUE))
            ];

            $data2 = [
                'user_nationid'           => html_escape($this->input->post('user_nationid', TRUE)),
                'dob'           => html_escape($this->input->post('dob', TRUE)),
                'id'               => html_escape($this->input->post('id', TRUE))
            ];

            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('detailuser/detaildriver/' . $id);
            } else {

                $success = $this->drv->editdataidentity($data, $data2);
                if ($success) {
                    $this->session->set_flashdata('success', 'Driver identity Has Been Change');
                    redirect('detailuser/detaildriver/' . $id);
                } else {
                    $this->session->set_flashdata('danger', 'Error, please try again!');
                    redirect('detailuser/detaildriver/' . $id);
                }
            }
        } else {
            $this->session->set_flashdata('danger', 'Error, please try again!');
            redirect('detailuser/detaildriver/' . $id);
        }
    }

    public function editcontact()
    {

        $this->form_validation->set_rules('email', 'email', 'trim|prep_for_form');
        $this->form_validation->set_rules('countrycode', 'countrycode', 'trim|prep_for_form');
        $this->form_validation->set_rules('phone', 'phone', 'trim|prep_for_form');
        $this->form_validation->set_rules('phone_number', 'phone_number', 'trim|prep_for_form');

        $id = html_escape($this->input->post('id', TRUE));
        if ($this->form_validation->run() == TRUE) {

            $phone = html_escape($this->input->post('phone', TRUE));
            $countrycode = html_escape($this->input->post('countrycode', TRUE));

            $data             = [
                'id'                    => $id,
                'email'                 => html_escape($this->input->post('email', TRUE)),
                'countrycode'           => html_escape($this->input->post('countrycode', TRUE)),
                'phone'                 => html_escape($this->input->post('phone', TRUE)),
                'phone_number'            => str_replace("+", "", $countrycode) . $phone
            ];


            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('detailuser/detaildriver/' . $this->input->post('id', TRUE));
            } else {
                $id = html_escape($this->input->post('id', TRUE));
                $success = $this->drv->editdatainfo($data);
                if ($success) {
                    $this->session->set_flashdata('success', 'Driver contact Has Been Change');
                    redirect('detailuser/detaildriver/' . $id);
                } else {
                    $this->session->set_flashdata('danger', 'Error, please try again!');
                    redirect('detailuser/detaildriver/' . $id);
                }
            }
        } else {
            $this->session->set_flashdata('danger', 'Error, please try again!');
            redirect('detailuser/detaildriver/' . $id);
        }
    }

    public function editvehicle()
    {

        $this->form_validation->set_rules('brand', 'brand', 'trim|prep_for_form');
        $this->form_validation->set_rules('type', 'type', 'trim|prep_for_form');
        $this->form_validation->set_rules('vehicle_registration_number', 'vehicle_registration_number', 'trim|prep_for_form');
        $this->form_validation->set_rules('color', 'color', 'trim|prep_for_form');


        if ($this->form_validation->run() == TRUE) {
            $data             = [

                'vehicle_id'                => html_escape($this->input->post('vehicle_id', TRUE)),
                'brand'                     => html_escape($this->input->post('brand', TRUE)),
                'type'                      => html_escape($this->input->post('type', TRUE)),
                'vehicle_registration_number'           => html_escape($this->input->post('vehicle_registration_number', TRUE)),
                'color'                     => html_escape($this->input->post('color', TRUE))
            ];

            $data2             = [

                'id'                        => html_escape($this->input->post('id', TRUE))

            ];


            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('detailuser/detaildriver/' . $this->input->post('id', TRUE));
            } else {
                $id = html_escape($this->input->post('id', TRUE));
                $success = $this->drv->editdatavehicle($data, $data2);
                if ($success) {
                    $this->session->set_flashdata('success', 'Driver vehicle Has Been Change');
                    redirect('detailuser/detaildriver/' . $id);
                } else {
                    $this->session->set_flashdata('danger', 'Error, please try again!');
                    redirect('detailuser/detaildriver/' . $id);
                }
            }
        } else {
            $id = html_escape($this->input->post('id', TRUE));
            $this->session->set_flashdata('danger', 'Error, please try again!');
            redirect('detailuser/detaildriver/' . $id);
        }
    }

    public function detailmerchant($id)
    {

        $getview['view'] = 'detailmerchant';
        $data['partner'] = $this->mrc->getpartnerbyid($id);
        $getview['menu'] = $this->appset->getMenuAdmin();
        $data['item'] = $this->mrc->getitembyid($data['partner']['merchant_id']);
        $data['itemcategory'] = $this->mrc->getcatitembyid($data['partner']['merchant_id']);
        $data['currency'] = $this->appset->getcurrency();
        $data['countorder'] = $this->mrc->countorder($data['partner']['merchant_id']);
        $data['wallet'] = $this->mrc->wallet($id);
        $data['wallet_amount'] = count($data['item']);
        $data['merchantcategory'] = $this->mrc->getmerchantcat();
        $data['transaction'] = $this->mrc->gettranshistory($data['partner']['merchant_id']);
        $data['service'] = $this->mrc->get_service_merchant();


        $this->load->view('includes/header', $getview);
        $this->load->view('detailuser/detailmerchant', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function editownerinfo()
    {
        $this->form_validation->set_rules('partner_name', 'partner_name', 'trim|prep_for_form');
        $this->form_validation->set_rules('partner_address', 'partner_address', 'trim|prep_for_form');
        $this->form_validation->set_rules('partner_email', 'partner_email', 'trim|prep_for_form');
        $this->form_validation->set_rules('countrycode', 'countrycode', 'trim|prep_for_form');
        $this->form_validation->set_rules('phone', 'phone', 'trim|prep_for_form');
        $this->form_validation->set_rules('partner_type_identity', 'partner_type_identity', 'trim|prep_for_form');
        $this->form_validation->set_rules('partner_identity_number', 'partner_identity_number', 'trim|prep_for_form');
        $this->form_validation->set_rules('password', 'password', 'trim|prep_for_form');

        $id = html_escape($this->input->post('partner_id', TRUE));
        $dataphoto = $this->mrc->getpartnerbyid($id);

        if ($this->input->post('password') != NULL) {
            $password = sha1($this->input->post('password', TRUE));
        } else {
            $password = $dataphoto['password'];
        }

        $photo = $dataphoto['idcard_images'];

        if ($this->form_validation->run() == TRUE) {

            if (@$_FILES['idcard_images']['name']) {

                $config['upload_path']     = './images/photofile/ktp';
                $config['allowed_types'] = 'gif|jpg|png|jpeg';
                $config['max_size']         = '30000';
                $config['file_name']     = 'name';
                $config['encrypt_name']     = true;
                $this->upload->initialize($config);

                if ($this->upload->do_upload('idcard_images')) {
                    if ($dataphoto['idcard_images'] != 'noimage.jpg' || $dataphoto['idcard_images'] != '') {

                        $gambar = $dataphoto['idcard_images'];


                        unlink('images/photofile/ktp/' . $gambar);
                    }

                    $photo = html_escape($this->upload->data('file_name'));
                }
            }


            $phone       = html_escape($this->input->post('partner_phone', TRUE));
            $countrycode = html_escape($this->input->post('partner_country_code', TRUE));
            $remove = array("+", "-");

            $data = [
                'partner_id'                    => $id,
                'partner_name'                => html_escape($this->input->post('partner_name', TRUE)),
                'partner_address'              => html_escape($this->input->post('partner_address', TRUE)),
                'partner_email'               => html_escape($this->input->post('partner_email', TRUE)),
                'partner'                   => $this->input->post('partner'),
                'partner_phone'               => $phone,
                'partner_country_code'        => $countrycode,
                'partner_telephone'             => str_replace($remove, '', $countrycode) . $phone,
                'partner_type_identity'               => html_escape($this->input->post('partner_type_identity', TRUE)),
                'partner_identity_number'               => html_escape($this->input->post('partner_identity_number', TRUE)),
                'password'               => $password
            ];

            $data2 = [
                'driver_id'                    => $id,
                'idcard_images'                  => $photo
            ];

            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('detailuser/detailmerchant/' . $id);
            } else {

                $success = $this->mrc->editmerchantbyid($data, $data2);
                if ($success) {
                    $this->session->set_flashdata('success', 'Owner info Has Been Change');
                    redirect('detailuser/detailmerchant/' . $id);
                } else {
                    $this->session->set_flashdata('danger', 'Error, please try again!');
                    redirect('detailuser/detailmerchant/' . $id);
                }
            }
        } else {
            $this->session->set_flashdata('danger', 'Error, please try again!');
            redirect('detailuser/detailmerchant/' . $id);
        }
    }

    public function editmerchant()
    {
        $this->form_validation->set_rules('merchant_name', 'merchant_name', 'trim|prep_for_form');
        $this->form_validation->set_rules('merchant_address', 'merchant_address', 'trim|prep_for_form');
        $servicedata['service'] = $this->mrc->get_service_merchant();
        $id = html_escape($this->input->post('partner_id', TRUE));
        if ($this->form_validation->run() == TRUE) {

            $merchant = $this->mrc->getdetailmerchant($this->input->post('merchant_id'));

            $fotomerchant = $merchant['merchant_image'];
            if (@$_FILES['merchant_image']['name']) {

                $config['upload_path']     = './images/merchant';
                $config['allowed_types'] = 'gif|jpg|png|jpeg';
                $config['max_size']         = '30000';
                $config['file_name']     = 'name';
                $config['encrypt_name']     = true;
                $this->upload->initialize($config);

                if ($this->upload->do_upload('merchant_image')) {
                    if ($merchant['merchant_image'] != 'noimage.jpg' || $merchant['merchant_image'] != '') {

                        unlink('images/merchant/' . $fotomerchant);
                    }

                    $fotobarumerchant = $this->upload->data('file_name');
                } else {
                    $fotobarumerchant = $fotomerchant;
                }

                $data = [
                    'merchant_id'               => html_escape($this->input->post('merchant_id', TRUE)),
                    'service_id'                  => html_escape($this->input->post('service_id', TRUE)),
                    'merchant_name'             => html_escape($this->input->post('merchant_name', TRUE)),
                    'merchant_category'         => html_escape($this->input->post('merchant_category', TRUE)),
                    'merchant_address'           => html_escape($this->input->post('merchant_address', TRUE)),
                    'merchant_latitude'         => html_escape($this->input->post('merchant_latitude', TRUE)),
                    'merchant_longitude'        => html_escape($this->input->post('merchant_longitude', TRUE)),
                    'open_hour'                  => html_escape($this->input->post('open_hour', TRUE)),
                    'close_hour'                 => html_escape($this->input->post('close_hour', TRUE)),
                    'merchant_image'             => $fotobarumerchant
                ];


                if (demo == TRUE) {
                    $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                    redirect('detailuser/detailmerchant/' . $id);
                } else {
                    $success = $this->mrc->updatemerchant($data);
                    if ($success) {
                        $this->session->set_flashdata('success', 'Merchant info Has Been Change');
                        redirect('detailuser/detailmerchant/' . $id);
                    } else {
                        $this->session->set_flashdata('danger', 'Error, please try again!');
                        redirect('detailuser/detailmerchant/' . $id);
                    }
                }
            } else {


                $data = [
                    'merchant_id'               => html_escape($this->input->post('merchant_id', TRUE)),
                    'service_id'                  => html_escape($this->input->post('service_id', TRUE)),
                    'merchant_name'             => html_escape($this->input->post('merchant_name', TRUE)),
                    'merchant_category'         => html_escape($this->input->post('merchant_category', TRUE)),
                    'merchant_address'           => html_escape($this->input->post('merchant_address', TRUE)),
                    'merchant_latitude'         => html_escape($this->input->post('merchant_latitude', TRUE)),
                    'merchant_longitude'        => html_escape($this->input->post('merchant_longitude', TRUE)),
                    'open_hour'                  => html_escape($this->input->post('open_hour', TRUE)),
                    'close_hour'                 => html_escape($this->input->post('close_hour', TRUE)),
                    'merchant_image'             => $fotomerchant
                ];

                if (demo == TRUE) {
                    $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                    redirect('detailuser/detailmerchant/' . $id);
                } else {

                    $success = $this->mrc->updatemerchant($data);
                    if ($success) {
                        $this->session->set_flashdata('success', 'Merchant info Has Been Change');
                        redirect('detailuser/detailmerchant/' . $id);
                    } else {
                        $this->session->set_flashdata('danger', 'Error, please try again!');
                        redirect('detailuser/detailmerchant/' . $id);
                    }
                }
            }
        } else {
            $this->session->set_flashdata('danger', 'Error, please try again!');
            redirect('detailuser/detailmerchant/' . $id);
        }
    }

    public function addcategoryitem()
    {

        $this->form_validation->set_rules('category_name_item', 'category_name_item', 'trim|prep_for_form');
        if ($this->form_validation->run() == TRUE) {

            $id = $this->input->post('partner_id');

            $data = [
                'category_name_item'    => html_escape($this->input->post('category_name_item', TRUE)),
                'merchant_id'           => html_escape($this->input->post('merchant_id', TRUE)),
                'all_category'          => '0'
            ];

            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('detailuser/detailmerchant/' . $id);
            } else {

                $success = $this->mrc->additemcategorybyid($data);
                if ($success) {
                    $this->session->set_flashdata('success', 'Category item Has Been Added');
                    redirect('detailuser/detailmerchant/' . $id);
                } else {
                    $this->session->set_flashdata('danger', 'Error, please try again!');
                    redirect('detailuser/detailmerchant/' . $id);
                }
            }
        } else {
            $id = $this->input->post('partner_id');
            $this->session->set_flashdata('danger', 'Error, please try again!');
            redirect('detailuser/detailmerchant/' . $id);
        }
    }

    public function editcategoryitem()
    {
        $this->form_validation->set_rules('category_name_item', 'category_name_item', 'trim|prep_for_form');
        if ($this->form_validation->run() == TRUE) {

            $idm = $this->input->post('partner_id');
            $id = $this->input->post('category_item_id');
            $data = [
                'category_name_item'    => html_escape($this->input->post('category_name_item', TRUE)),
            ];

            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('detailuser/detailmerchant/' . $id);
            } else {

                $success = $this->mrc->editcategoryitembyid($data, $id);
                if ($success) {
                    $this->session->set_flashdata('success', 'Item category Has Been Change');
                    redirect('detailuser/detailmerchant/' . $idm);
                } else {
                    $this->session->set_flashdata('error', 'Please try again!');
                    redirect('detailuser/detailmerchant/' . $idm);
                }
            }
        } else {
            $idm = $this->input->post('partner_id');
            $this->session->set_flashdata('error', 'Please try again!');
            redirect('detailuser/detailmerchant/' . $idm);
        }
    }

    public function detailcustomer($id)
    {

        $getview['view'] = 'detailcustomer';
        $getview['menu'] = $this->appset->getMenuAdmin();
        $data['customer'] = $this->cstm->getcustomerbyid($id);
        $data['currency'] = $this->appset->getcurrency();
        $data['countorder'] = $this->cstm->countorder($id);
        $data['wallet'] = $this->cstm->wallet($id);
        $this->load->view('includes/header', $getview);
        $this->load->view('detailuser/detailcustomer', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function confirmmerchant($id)
    {

        $item = $this->app->getappbyid();

        $token = sha1(rand(0, 999999) . time());

        $dataforgot = array(
            'userid' => $id,
            'token' => $token,
            'idKey' => '3'
        );
        $this->Customer_model->dataforgot($dataforgot);

        $linkbtn = base_url() . 'resetpass/rest/' . $token . '/3';
        $judul_email = $item['email_subject_confirm'] . '[ticket-' . rand(0, 999999) . ']';
        $template = $this->Customer_model->template1($item['email_subject_confirm'], $item['email_text3'], $item['email_text4'], $item['app_website'], $item['app_name'], $linkbtn, $item['app_linkgoogle'], $item['app_address']);
        $email = $this->mrc->getmitrabyid($id);
        $emailuser = $email['partner_email'];
        $host = $item['smtp_host'];
        $port = $item['smtp_port'];
        $username = $item['smtp_username'];
        $password = $item['smtp_password'];
        $from = $item['smtp_from'];
        $appname = $item['app_name'];
        $secure = $item['smtp_secure'];

        $emailsend = $this->Email_model->emailsend($judul_email, $emailuser, $template, $host, $port, $username, $password, $from, $appname, $secure);

        if ($emailsend) {
            $success = $this->mrc->editstatusmitra($id);
            if ($success) {
                $this->session->set_flashdata('success', 'Merchant Has Been Confirm');
                redirect('detailuser/detailmerchant/' . $id);
            } else {
                $this->session->set_flashdata('danger', 'Error, please try again!');
                redirect('detailuser/detailmerchant/' . $id);
            }
        } else {
            $this->session->set_flashdata('danger', 'email confirmation not sent!');
            redirect('detailuser/detailmerchant/' . $id);
        }
    }

    public function confirmdriver($id)
    {

        $item = $this->app->getappbyid();

        $token = sha1(rand(0, 999999) . time());

        $dataforgot = array(
            'userid' => $id,
            'token' => $token,
            'idKey' => '2'
        );
        $this->Customer_model->dataforgot($dataforgot);

        $linkbtn = base_url() . 'resetpass/rest/' . $token . '/2';
        $judul_email = $item['email_subject_confirm'] . '[ticket-' . rand(0, 999999) . ']';
        $template = $this->Customer_model->template1($item['email_subject_confirm'], $item['email_text3'], $item['email_text4'], $item['app_website'], $item['app_name'], $linkbtn, $item['app_linkgoogle'], $item['app_address']);
        $email = $this->drv->getdriverbyid($id);
        $emailuser = $email['email'];
        $host = $item['smtp_host'];
        $port = $item['smtp_port'];
        $username = $item['smtp_username'];
        $password = $item['smtp_password'];
        $from = $item['smtp_from'];
        $appname = $item['app_name'];
        $secure = $item['smtp_secure'];

        $emailsend = $this->Email_model->emailsend($judul_email, $emailuser, $template, $host, $port, $username, $password, $from, $appname, $secure);

        if ($emailsend) {
            $success = $this->drv->editstatusnewreg($id);
            if ($success) {
                $this->session->set_flashdata('success', 'Driver Has Been Confirm');
                redirect('detailuser/detaildriver/' . $id);
            } else {
                $this->session->set_flashdata('danger', 'Error, please try again!');
                redirect('detailuser/detaildriver/' . $id);
            }
        } else {
            $this->session->set_flashdata('danger', 'email confirmation not sent!');
            redirect('detailuser/detaildriver/' . $id);
        }
    }

    public function editdatacustomer()
    {

        $this->form_validation->set_rules('customer_fullname', 'customer_fullname', 'trim|prep_for_form');
        $this->form_validation->set_rules('phone_number', 'phone_number', 'trim|prep_for_form');
        $this->form_validation->set_rules('email', 'email', 'trim|prep_for_form');
        $this->form_validation->set_rules('password', 'password', 'trim|prep_for_form');
        $id = html_escape($this->input->post('id', TRUE));

        $countrycode = html_escape($this->input->post('countrycode', TRUE));
        $phone = html_escape($this->input->post('phone', TRUE));



        if ($this->form_validation->run() == TRUE) {

            $customer = $this->cstm->getcustomerbyid($this->input->post('id'));

            if ($this->input->post('password') != NULL) {
                $password = sha1($this->input->post('password', TRUE));
            } else {
                $password = $customer['password'];
            }

            $customerphoto = $customer['customer_image'];

            if (@$_FILES['customer_image']['name']) {

                $config['upload_path']     = './images/customer';
                $config['allowed_types'] = 'gif|jpg|png|jpeg';
                $config['max_size']         = '30000';
                $config['file_name']     = 'name';
                $config['encrypt_name']     = true;
                $this->upload->initialize($config);

                if ($this->upload->do_upload('customer_image')) {
                    if ($customer['customer_image'] != 'noimage.jpg' || $customer['customer_image'] != '') {

                        unlink('images/customer/' . $customerphoto);
                    }

                    $fotobarucustomer = $this->upload->data('file_name');
                } else {
                    $fotobarucustomer = $customerphoto;
                }

                $data             = [
                    'phone'                     => html_escape($this->input->post('phone', TRUE)),
                    'countrycode'               => html_escape($this->input->post('countrycode', TRUE)),
                    'id'                        => html_escape($this->input->post('id', TRUE)),
                    'customer_fullname'                    => html_escape($this->input->post('customer_fullname', TRUE)),
                    'phone_number'                => str_replace("+", "", $countrycode) . $phone,
                    'email'                        => html_escape($this->input->post('email', TRUE)),
                    'password'                        => $password,
                    'dob'                        => html_escape($this->input->post('dob', TRUE)),
                    'customer_image'                        => $fotobarucustomer


                ];


                if (demo == TRUE) {
                    $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                    redirect('detailuser/detailcustomer/' . $id);
                } else {
                    $this->cstm->editdatacustomer($data);
                    $this->session->set_flashdata('success', 'Customer Has Been Change');
                    redirect('detailuser/detailcustomer/' . $id);
                }
            } else {

                $data             = [
                    'phone'                     => html_escape($this->input->post('phone', TRUE)),
                    'countrycode'               => html_escape($this->input->post('countrycode', TRUE)),
                    'id'                        => html_escape($this->input->post('id', TRUE)),
                    'customer_fullname'                    => html_escape($this->input->post('customer_fullname', TRUE)),
                    'phone_number'                => str_replace("+", "", $countrycode) . $phone,
                    'email'                        => html_escape($this->input->post('email', TRUE)),
                    'password'                        => sha1($this->input->post('password')),
                    'dob'                        => html_escape($this->input->post('dob', TRUE)),
                    'customer_image'                        => $customerphoto
                ];


                if (demo == TRUE) {
                    $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                    redirect('detailuser/detailcustomer/' . $id);
                } else {
                    $success = $this->cstm->editdatacustomer($data);
                    if ($success) {
                        $this->session->set_flashdata('success', 'Customer Has Been Change');
                        redirect('detailuser/detailcustomer/' . $id);
                    } else {
                        $this->session->set_flashdata('danger', 'Error, please try again!');
                        redirect('detailuser/detailcustomer/' . $id);
                    }
                }
            }
        } else {
            $this->session->set_flashdata('danger', 'Error, please try again!');
            redirect('detailuser/detailcustomer/' . $id);
        }
    }

    public function additemview($id)
    {
        $getview['view'] = 'additemview';
        $getview['menu'] = $this->appset->getMenuAdmin();
        $data['partner'] = $this->mrc->getpartnerbyid($id);
        $data['item'] = $this->mrc->getitembyid($data['partner']['merchant_id']);
        $data['itemcategory'] = $this->mrc->getcatitembyid($data['partner']['merchant_id']);
        $data['currency'] = $this->appset->getcurrency();

        $this->load->view('includes/header', $getview);
        $this->load->view('detailuser/additem', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function additem()
    {
        $this->form_validation->set_rules('item_name', 'item_name', 'trim|prep_for_form');
        $this->form_validation->set_rules('item_price', 'item_price', 'trim|prep_for_form');
        $this->form_validation->set_rules('item_desc', 'item_desc', 'trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {

            if (@$_FILES['item_image']['name']) {

                $config['upload_path']     = './images/itemphoto';
                $config['allowed_types'] = 'gif|jpg|png|jpeg';
                $config['max_size']         = '30000';
                $config['file_name']     = 'name';
                $config['encrypt_name']     = true;
                $this->upload->initialize($config);

                if ($this->upload->do_upload('item_image')) {

                    $fotoitem = html_escape($this->upload->data('file_name'));
                } else {
                    $fotoitem = 'noimage.jpg';
                }

                if ($this->input->post('promo_status') == 1) {
                    $promo = html_escape($this->input->post('promo_price', TRUE));
                } else {
                    $promo = '0';
                }
                $id = $this->input->post('partner_id');
                $hargaitem = html_escape($this->input->post('item_price', TRUE));
                $hargapromo = $promo;

                $remove = array(".", ",");
                $add = array("", "");
                $data = [
                    'item_category'     => html_escape($this->input->post('item_category', TRUE)),
                    'item_name'         => html_escape($this->input->post('item_name', TRUE)),
                    'item_price'        => str_replace($remove, $add, $hargaitem),
                    'promo_price'       => str_replace($remove, $add, $hargapromo),
                    'merchant_id'       => html_escape($this->input->post('merchant_id', TRUE)),
                    'item_desc'    => html_escape($this->input->post('item_desc', TRUE)),
                    'item_status'       => html_escape($this->input->post('item_status', TRUE)),
                    'promo_status'      => html_escape($this->input->post('promo_status', TRUE)),
                    'item_image'         => $fotoitem
                ];

                if (demo == TRUE) {
                    $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                    redirect('detailuser/detailmerchant/' . $id);
                } else {
                    $insert = $this->mrc->insertitem($data);
                    if ($insert) {
                        $this->session->set_flashdata('success', 'Item Has Been Added');
                        redirect('detailuser/detailmerchant/' . $id);
                    } else {
                        $id = $this->input->post('partner_id');
                        $this->session->set_flashdata('danger', 'Error, Please Try Again');
                        redirect('detailuser/detailmerchant/' . $id);
                    }
                }
            }
        }
    }

    public function edititemview($id)
    {
        $getview['view'] = 'edititemview';
        $getview['menu'] = $this->appset->getMenuAdmin();
        $data['item'] = $this->mrc->getitembyiditem($id);
        $data['itemcategory'] = $this->mrc->getcatitembyid($data['item']['merchant_id']);
        $data['currency'] = $this->appset->getcurrency();

        $this->load->view('includes/header', $getview);
        $this->load->view('detailuser/edititem', $data);
        $this->load->view('includes/footer', $getview);
    }


    public function edititem($id)
    {
        $idmerchant = $this->input->post('merchant_id');
        $merchant = $this->mrc->getidmerchant($idmerchant);

        $idm = $merchant['partner_id'];

        $this->form_validation->set_rules('item_name', 'item_name', 'trim|prep_for_form');
        $this->form_validation->set_rules('item_price', 'item_price', 'trim|prep_for_form');
        $this->form_validation->set_rules('item_desc', 'item_desc', 'trim|prep_for_form');
        if ($this->form_validation->run() == TRUE) {



            if (@$_FILES['item_image']['name']) {

                $config['upload_path']     = './images/itemphoto';
                $config['allowed_types'] = 'gif|jpg|png|jpeg';
                $config['max_size']         = '30000';
                $config['file_name']     = 'name';
                $config['encrypt_name']     = true;
                $this->upload->initialize($config);

                $photo = $this->mrc->getitemphoto($id);


                if ($this->upload->do_upload('item_image')) {

                    $fotoitem = $this->upload->data('file_name');
                    $fotolama = $photo['item_image'];
                    unlink('images/itemphoto/' . $fotolama);
                } else {
                    $fotolama = $photo['item_image'];
                    $fotoitem = $fotolama;
                }


                if ($this->input->post('promo_status') == 1) {
                    $promo = html_escape($this->input->post('promo_price', TRUE));
                } else {
                    $promo = '0';
                }

                $hargaitem = html_escape($this->input->post('item_price', TRUE));
                $hargapromo = $promo;

                $remove = array(".", ",");
                $add = array("", "");



                $data = [
                    'item_category'     => html_escape($this->input->post('item_category', TRUE)),
                    'item_name'         => html_escape($this->input->post('item_name', TRUE)),
                    'item_price'        => str_replace($remove, $add, $hargaitem),
                    'promo_price'       => str_replace($remove, $add, $hargapromo),
                    'merchant_id'       => html_escape($this->input->post('merchant_id', TRUE)),
                    'item_desc'    => html_escape($this->input->post('item_desc', TRUE)),
                    'item_status'       => html_escape($this->input->post('item_status', TRUE)),
                    'promo_status'      => html_escape($this->input->post('promo_status', TRUE)),
                    'item_image'         => $fotoitem

                ];



                if (demo == TRUE) {
                    $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                    redirect('detailuser/edititemview/' . $id);
                } else {

                    $this->mrc->updateitem($data, $id);
                    $this->session->set_flashdata('success', 'Item Has Been Changed');
                    redirect('detailuser/detailmerchant/' . $idm);
                }
            } else {

                $photo = $this->mrc->getitemphoto($id);
                $fotolama = $photo['item_image'];

                if ($this->input->post('promo_status') == 1) {
                    $promo = html_escape($this->input->post('promo_price', TRUE));
                } else {
                    $promo = '0';
                }

                $hargaitem = html_escape($this->input->post('item_price', TRUE));
                $hargapromo = $promo;

                $remove = array(".", ",");
                $add = array("", "");
                $data = [
                    'item_name'         => html_escape($this->input->post('item_name', TRUE)),
                    'item_category'     => html_escape($this->input->post('item_category', TRUE)),
                    'item_price'        => str_replace($remove, $add, $hargaitem),
                    'promo_price'       => str_replace($remove, $add, $hargapromo),
                    'merchant_id'       => html_escape($this->input->post('merchant_id', TRUE)),
                    'item_desc'         => html_escape($this->input->post('item_desc', TRUE)),
                    'item_status'       => html_escape($this->input->post('item_status', TRUE)),
                    'promo_status'      => html_escape($this->input->post('promo_status', TRUE)),
                    'item_image'         => $fotolama
                ];

                if (demo == TRUE) {
                    $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                    redirect('detailuser/edititemview/' . $id);
                } else {
                    $insert = $this->mrc->updateitem($data, $id);
                    if ($insert) {
                        $this->session->set_flashdata('success', 'Item Has Been Changed');
                        redirect('detailuser/detailmerchant/' . $idm);
                    } else {
                        $this->session->set_flashdata('danger', 'Error, Please Try Again');
                        redirect('detailuser/edititemview/' . $id);
                    }
                }
            }
        }
    }
}
