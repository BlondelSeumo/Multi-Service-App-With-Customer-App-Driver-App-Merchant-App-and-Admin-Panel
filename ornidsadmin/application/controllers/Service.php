<?php
defined('BASEPATH') or exit('No direct script access allowed');

class Service extends CI_Controller
{
    public function __construct()
    {
        parent::__construct();
        if ($this->session->userdata('user_name') == NULL && $this->session->userdata('password') == NULL) {
            redirect(base_url() . "login");
        }
        $this->load->model('Vehicletype_model', 'vhc');
        $this->load->model('Service_model', 'srvc');
        $this->load->model('Appsettings_model', 'appset');
        $this->load->library('form_validation');
    }

    public function vehicletypedata()
    {
        $getview['view'] = 'vehicledata';
        $getview['menu'] = $this->appset->getMenuAdmin();
        $data['vehicletype'] = $this->vhc->getAllvehicletype();

        $this->load->view('includes/header', $getview);
        $this->load->view('service/vehicletype', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function servicedata()
    {
        $getview['view'] = 'servicedata';
        $getview['menu'] = $this->appset->getMenuAdmin();
        $data['currency'] = $this->appset->getcurrency();
        $data['service'] = $this->srvc->getallservice();
        $data['driverjob'] = $this->srvc->getAlldriverjob();

        $this->load->view('includes/header', $getview);
        $this->load->view('service/servicedata', $data);
        $this->load->view('includes/footer', $getview);
    }



    public function editvehicletype($id)
    {
        $data['partnerjob'] = $this->vhc->getpartnerjobById($id);
        $getview['view'] = 'addtopup';
        $getview['menu'] = $this->appset->getMenuAdmin();
        $this->load->view('includes/header', $getview);
        $this->load->view('service/editvehicletype', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function addvehicletypedata()
    {
        $this->form_validation->set_rules('icon', 'icon', 'trim|prep_for_form');
        $this->form_validation->set_rules('driver_job', 'driver_job', 'trim|prep_for_form');
        $this->form_validation->set_rules('status_job', 'status_job', 'trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {


            $data             = [
                'icon'              => html_escape($this->input->post('icon', TRUE)),
                'driver_job'              => html_escape($this->input->post('driver_job', TRUE)),
                'status_job'                  => html_escape($this->input->post('status_job', TRUE))
            ];

            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('service/addvehicletype');
            } else {
                $success = $this->vhc->addpartnerjob($data);
                if ($success) {
                    $this->session->set_flashdata('success', 'Vehicle type Has Been Added');
                    redirect('service/vehicletypedata');
                } else {
                    $this->session->set_flashdata('danger', 'Error, please try again!');
                    redirect('service/addvehicletype');
                }
            }
        } else {

            $this->session->set_flashdata('danger', 'Error, please try again!');
            redirect('service/addvehicletype');
        }
    }

    public function addvehicletype()
    {
        $getview['view'] = 'addtopup';
        $getview['menu'] = $this->appset->getMenuAdmin();
        $this->load->view('includes/header', $getview);
        $this->load->view('service/addvehicletype');
        $this->load->view('includes/footer', $getview);
    }

    public function addservice()
    {
        $getview['view'] = 'addservice';
        $data['driverjob'] = $this->vhc->getAllvehicletype();
        $data['typeservice'] = $this->vhc->getAllTypeService();
        $getview['menu'] = $this->appset->getMenuAdmin();
        $this->load->view('includes/header', $getview);
        $this->load->view('service/addservice', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function editservice($id)
    {
        $getview['view'] = 'editservice';
        $data = $this->vhc->getservicebyid($id);
        $data['driverjob'] = $this->vhc->getAllvehicletype();
        $data['typeservice'] = $this->vhc->getAllTypeService();
        $getview['menu'] = $this->appset->getMenuAdmin();
        $this->load->view('includes/header', $getview);
        $this->load->view('service/editservice', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function editpartnerjob()
    {
        $this->form_validation->set_rules('icon', 'icon', 'trim|prep_for_form');
        $this->form_validation->set_rules('driver_job', 'driver_job', 'trim|prep_for_form');
        $this->form_validation->set_rules('status_job', 'status_job', 'trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {


            $data             = [
                'id'                            => html_escape($this->input->post('id', TRUE)),
                'icon'              => html_escape($this->input->post('icon', TRUE)),
                'driver_job'              => html_escape($this->input->post('driver_job', TRUE)),
                'status_job'                  => html_escape($this->input->post('status_job', TRUE))
            ];

            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('partnerjob/index');
            } else {
                $success = $this->vhc->editdatapartnerjob($data);

                if ($success) {
                    $this->session->set_flashdata('success', 'Service type Has Been Changed');
                    redirect('service/vehicletypedata');
                } else {
                    $this->session->set_flashdata('danger', 'Error, please try again!');
                    redirect('service/editvehicletype/' . html_escape($this->input->post('id', TRUE)));
                }
            }
        } else {

            $this->session->set_flashdata('danger', 'Error, please try again!');
            redirect('service/editvehicletype/' . html_escape($this->input->post('id', TRUE)));
        }
    }

    public function deleteservicetype($id)
    {
        if (demo == TRUE) {
            $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
            redirect('service/vehicletypedata');
        } else {
            $success = $this->vhc->deletepartnerjobById($id);
            if ($success) {
                $this->session->set_flashdata('success', 'Service type Has Been Deleted');
                redirect('service/vehicletypedata');
            } else {
                $this->session->set_flashdata('danger', 'Error, please try again!');
                redirect('service/vehicletypedata');
            }
        }
    }

    public function addservicedata()

    {
        $this->form_validation->set_rules('service', 'service', 'trim|prep_for_form');
        $this->form_validation->set_rules('home', 'home', 'trim|prep_for_form');
        $this->form_validation->set_rules('cost', 'cost', 'trim|prep_for_form');
        $this->form_validation->set_rules('cost_desc', 'cost_desc', 'trim|prep_for_form');
        $this->form_validation->set_rules('commision', 'commision', 'trim|prep_for_form');
        $this->form_validation->set_rules('driver_job', 'driver_job', 'trim|prep_for_form');
        $this->form_validation->set_rules('minimum_cost', 'minimum_cost', 'trim|prep_for_form');
        $this->form_validation->set_rules('minimum_distance', 'minimum_distance', 'trim|prep_for_form');
        $this->form_validation->set_rules('maks_distance', 'maks_distance', 'trim|prep_for_form');
        $this->form_validation->set_rules('minimum_wallet', 'minimum_wallet', 'trim|prep_for_form');
        $this->form_validation->set_rules('description', 'description', 'trim|prep_for_form');
        $this->form_validation->set_rules('voucher_discount', 'voucher_discount', 'trim|prep_for_form');
        $this->form_validation->set_rules('active', 'active', 'trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {
            $config['upload_path']     = './images/service/';
            $config['allowed_types'] = 'gif|jpg|png|jpeg';
            $config['max_size']         = '10000';
            $config['file_name']     = 'name';
            $config['encrypt_name']     = true;
            $this->load->library('upload', $config);

            if ($this->upload->do_upload('icon')) {
                $gambar = html_escape($this->upload->data('file_name'));
            } else {
                $gambar = 'noimage.jpg';
            }

            $cost = html_escape($this->input->post('cost', TRUE));
            $minimum_cost = html_escape($this->input->post('minimum_cost', TRUE));
            $minimum_wallet = html_escape($this->input->post('minimum_wallet', TRUE));

            $remove = array(".", ",");
            $add = array("", "");

            $data             = [
                'icon'                          => $gambar,
                'service'                         => html_escape($this->input->post('service', TRUE)),
                'home'                         => html_escape($this->input->post('home', TRUE)),
                'cost'                         => str_replace($remove, $add, $cost),
                'cost_desc'              => html_escape($this->input->post('cost_desc', TRUE)),
                'commision'                        => html_escape($this->input->post('commision', TRUE)),
                'driver_job'                    => html_escape($this->input->post('driver_job', TRUE)),
                'minimum_cost'                 => str_replace($remove, $add, $minimum_cost),
                'minimum_distance'                 => html_escape($this->input->post('minimum_distance', TRUE)),
                'maks_distance'                 => html_escape($this->input->post('maks_distance', TRUE)),
                'minimum_wallet'                => str_replace($remove, $add, $minimum_wallet),
                'description'                    => html_escape($this->input->post('description', TRUE)),
                'active'                        => html_escape($this->input->post('active', TRUE))
            ];

            $datanilai = [

                'voucher_discount'                         => html_escape($this->input->post('voucher_discount', TRUE)),

            ];

            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('services/index');
            } else {

                $success = $this->vhc->addservice($data, $datanilai);
                if ($success) {
                    $this->session->set_flashdata('success', 'Services Has Been Added');
                    redirect('service/servicedata');
                } else {
                    $this->session->set_flashdata('danger', 'Error, please try again!');
                    redirect('service/addservice');
                }
            }
        } else {
            $this->session->set_flashdata('danger', 'Error, please try again!');
            redirect('service/addservice');
        }
    }

    public function editservicedata($id)
    {

        $this->form_validation->set_rules('service', 'service', 'trim|prep_for_form');
        $this->form_validation->set_rules('home', 'home', 'trim|prep_for_form');
        $this->form_validation->set_rules('cost', 'cost', 'trim|prep_for_form');
        $this->form_validation->set_rules('cost_desc', 'cost_desc', 'trim|prep_for_form');
        $this->form_validation->set_rules('commision', 'commision', 'trim|prep_for_form');
        $this->form_validation->set_rules('driver_job', 'driver_job', 'trim|prep_for_form');
        $this->form_validation->set_rules('minimum_cost', 'minimum_cost', 'trim|prep_for_form');
        $this->form_validation->set_rules('minimum_distance', 'minimum_distance', 'trim|prep_for_form');
        $this->form_validation->set_rules('maks_distance', 'maks_distance', 'trim|prep_for_form');
        $this->form_validation->set_rules('minimum_wallet', 'minimum_wallet', 'trim|prep_for_form');
        $this->form_validation->set_rules('description', 'description', 'trim|prep_for_form');

        $data = $this->vhc->getservicebyid($id);


        if ($this->form_validation->run() == TRUE) {
            $config['upload_path']     = './images/service/';
            $config['allowed_types'] = 'gif|jpg|png|jpeg';
            $config['max_size']         = '10000';
            $config['file_name']     = 'name';
            $config['encrypt_name']     = true;
            $this->load->library('upload', $config);

            if ($this->upload->do_upload('icon')) {
                if ($data['icon'] != 'noimage.jpg') {
                    $gambar = $data['icon'];
                    unlink('images/service/' . $gambar);
                }

                $gambar = html_escape($this->upload->data('file_name'));
            } else {
                $gambar = $data['icon'];
            }

            $cost = html_escape($this->input->post('cost', TRUE));
            $minimum_cost = html_escape($this->input->post('minimum_cost', TRUE));
            $minimum_wallet = html_escape($this->input->post('minimum_wallet', TRUE));

            $remove = array(".", ",");
            $add = array("", "");

            $data             = [
                'icon'                          => $gambar,
                'service_id'                      => $id,
                'service'                         => html_escape($this->input->post('service', TRUE)),
                'home'                         => html_escape($this->input->post('home', TRUE)),
                'urutan'                        => html_escape($this->input->post('urutan', TRUE)),
                'cost'                         => str_replace($remove, $add, $cost),
                'cost_desc'              => html_escape($this->input->post('cost_desc', TRUE)),
                'commision'                        => html_escape($this->input->post('commision', TRUE)),
                'driver_job'                    => html_escape($this->input->post('driver_job', TRUE)),
                'minimum_cost'                 => str_replace($remove, $add, $minimum_cost),
                'minimum_distance'                 => html_escape($this->input->post('minimum_distance', TRUE)),
                'maks_distance'                 => html_escape($this->input->post('maks_distance', TRUE)),
                'minimum_wallet'                => str_replace($remove, $add, $minimum_wallet),
                'description'                    => html_escape($this->input->post('description', TRUE)),
                'voucher_discount'                         => html_escape($this->input->post('voucher_discount', TRUE)),
                'description'                    => html_escape($this->input->post('description', TRUE)),
                'active'                        => html_escape($this->input->post('active', TRUE))
            ];



            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('service/servicedata');
            } else {
                $success = $this->srvc->editdataservice($data);
                if ($success) {
                    $this->session->set_flashdata('success', 'Services Has Been Changed');
                    redirect('service/servicedata');
                } else {
                    $this->session->set_flashdata('danger', 'Error, please try again!');
                    redirect('service/editservice/' . $id);
                }
            }
        } else {
            $this->session->set_flashdata('danger', 'Error, please try again!');
            redirect('service/editservice/' . $id);
        }
    }

    public function deleteservice($id)
    {
        if (demo == TRUE) {
            $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
            redirect('service/servicedata');
        } else {
            $data = $this->vhc->getservicebyid($id);

            if ($data['icon'] != 'noimage.jpg') {
                $gambar = $data['icon'];
                unlink('images/service/' . $gambar);
            }

            $success = $this->srvc->deleteserviceById($id);
            if ($success) {
                $this->session->set_flashdata('success', 'Service Has Been deleted');
                redirect('service/servicedata');
            } else {
                $this->session->set_flashdata('danger', 'Error, please try again!');
                redirect('service/servicedata');
            }
        }
    }

    public function merchantcategorydata()
    {
        $getview['view'] = 'merchantcategorydata';
        $data['merchantcategory'] = $this->srvc->getAllmerchantcategory();
        $data['service'] = $this->srvc->getservicemerchant();

        $getview['menu'] = $this->appset->getMenuAdmin();
        $this->load->view('includes/header', $getview);
        $this->load->view('service/merchantcategorydata', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function addmerchantcategoryview()
    {
        $getview['view'] = 'addmerchnatcategory';
        $data['service'] = $this->srvc->getservicemerchant();

        $getview['menu'] = $this->appset->getMenuAdmin();
        $this->load->view('includes/header', $getview);
        $this->load->view('service/addmerchantcategory', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function addmerchantcategory()
    {
        $this->form_validation->set_rules('category_name', 'category_name', 'trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {

            $data = [
                'category_name'     => html_escape($this->input->post('category_name', TRUE)),
                'service_id'          => html_escape($this->input->post('service_id', TRUE)),
                'category_status'   => html_escape($this->input->post('category_status', TRUE)),
            ];

            $insert = $this->srvc->addmerchantcategory($data);

            if ($insert) {
                $this->session->set_flashdata('success', 'Merchant category has been Added');
                redirect('service/merchantcategorydata');
            } else {
                $this->session->set_flashdata('danger', 'Error, please try again!');
                redirect('service/addmerchantcategoryview');
            }
        } else {
            $this->session->set_flashdata('danger', 'Error, please try again!');
            redirect('service/addmerchantcategoryview');
        }
    }

    public function editmerchantcategoryview($id)
    {
        $getview['view'] = 'addmerchnatcategory';
        $data['service'] = $this->srvc->getservicemerchant();
        $data['merchantcategory'] = $this->srvc->getmerchantcategorybyid($id);

        $getview['menu'] = $this->appset->getMenuAdmin();
        $this->load->view('includes/header', $getview);
        $this->load->view('service/editmerchantcategory', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function editmerchantcategory()
    {

        $this->form_validation->set_rules('category_name', 'category_name', 'trim|prep_for_form');
        $id = $this->input->post('category_merchant_id');
        if ($this->form_validation->run() == TRUE) {

            $data = [
                'category_name'     => html_escape($this->input->post('category_name', TRUE)),
                'service_id'          => html_escape($this->input->post('service_id', TRUE)),
                'category_status'   => html_escape($this->input->post('category_status', TRUE)),
            ];

            $insert = $this->srvc->editmerchantcategory($data, $id);

            if ($insert) {
                $this->session->set_flashdata('success', 'Merchant category has been changed');
                redirect('service/merchantcategorydata');
            } else {
                $this->session->set_flashdata('danger', 'Error, please try again!');
                redirect('service/editmerchantcategoryview/', $id);
            }
        } else {
            $this->session->set_flashdata('danger', 'Error, please try again!');
            redirect('service/editmerchantcategoryview/', $id);
        }
    }

    public function deletemerchantcategory($id)
    {
        $this->srvc->deletemerchantcategory($id);
        $this->session->set_flashdata('success', 'Merchant category has been deleted');
        redirect('service/merchantcategorydata');
    }
}
