<?php
defined('BASEPATH') or exit('No direct script access allowed');

class Tracking extends CI_Controller
{
    public function __construct()
    {
        parent::__construct();
        if ($this->session->userdata('user_name') == NULL && $this->session->userdata('password') == NULL) {
            redirect(base_url() . "login");
        }
        $this->load->model('Appsettings_model', 'appset');
    }

    public function trackingdriver()
    {
        $dataview['view'] = 'drivermap';
        $getview['menu'] = $this->appset->getMenuAdmin();
        $this->load->view('includes/header', $getview);
        $this->load->view('tracking/trackingdriver');
        $this->load->view('includes/footer', $dataview);
    }

    public function trackingmerchant()
    {
        $dataview['view'] = 'merchantmap';
        $getview['menu'] = $this->appset->getMenuAdmin();
        $this->load->view('includes/header', $getview);
        $this->load->view('tracking/trackingmerchant');
        $this->load->view('includes/footer', $dataview);
    }
}
