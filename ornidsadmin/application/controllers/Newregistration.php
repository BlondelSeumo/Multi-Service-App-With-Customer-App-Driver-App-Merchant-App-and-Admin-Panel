<?php
defined('BASEPATH') or exit('No direct script access allowed');

class Newregistration extends CI_Controller
{
    public function __construct()
    {
        parent::__construct();
        if ($this->session->userdata('user_name') == NULL && $this->session->userdata('password') == NULL) {
            redirect(base_url() . "login");
        }
        $this->load->model('Driverdata_model', 'drv');
        $this->load->model('Merchantdata_model', 'mrc');
        $this->load->model('Appsettings_model', 'appset');
    }

    public function newregdriver()
    {
        $getview['view'] = 'newregdriver';
        $getview['menu'] = $this->appset->getMenuAdmin();
        $data['driver'] = $this->drv->getAlldriver();

        $this->load->view('includes/header', $getview);
        $this->load->view('newregistration/newregdriver', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function newregmerchant()
    {
        $getview['view'] = 'newregmerchant';
        $getview['menu'] = $this->appset->getMenuAdmin();
        $data['merchant'] = $this->mrc->getAllmerchant();

        $this->load->view('includes/header', $getview);
        $this->load->view('newregistration/newregmerchant', $data);
        $this->load->view('includes/footer', $getview);
    }
}
