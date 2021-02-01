<?php
defined('BASEPATH') or exit('No direct script access allowed');

class Historytransaction extends CI_Controller
{
    public function __construct()
    {
        parent::__construct();
        if ($this->session->userdata('user_name') == NULL && $this->session->userdata('password') == NULL) {
            redirect(base_url() . "login");
        }

        $this->load->model('Dashboard_model', 'dasb');
        $this->load->model('Appsettings_model', 'appset');
    }

    public function index()
    {
        $getview['view'] = 'historytransaction';
        $getview['menu'] = $this->appset->getMenuAdmin();
        $data['currency'] = $this->appset->getcurrency();
        $data['transaction'] = $this->dasb->getalltransaction();

        $this->load->view('includes/header', $getview);
        $this->load->view('historytransaction/index', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function deletetransaction($id)
    {
        if (demo == TRUE) {
            $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
            redirect('historytransaction');
        } else {
            $success = $this->dasb->deletetransaction($id);
            if ($success) {
                $this->session->set_flashdata('success', 'Transaction Has Been Delete ');
                redirect('historytransaction');
            } else {
                $this->session->set_flashdata('danger', 'Error, please try again!');
                redirect('historytransaction');
            }
        }
    }
}
