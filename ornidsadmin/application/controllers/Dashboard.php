<?php
defined('BASEPATH') or exit('No direct script access allowed');

class Dashboard extends CI_Controller
{
    public function __construct()
    {
        parent::__construct();
        if ($this->session->userdata('user_name') == NULL && $this->session->userdata('password') == NULL) {
            redirect(base_url() . "login");
        }
        $this->load->model('Dashboard_model', 'dasb');
        $this->load->model('Appsettings_model', 'appset');
        date_default_timezone_set(time_zone);
    }

    public function index()
    {
        $this->dasb->nodriver();
        $getview['view'] = 'dashboard';
        $getview['menu'] = $this->appset->getMenuAdmin();
        $data['counters'] = $this->dasb->count();
        $data['currency'] = $this->appset->getcurrency();
        $data['transaction'] = $this->dasb->getAlltransactiondasboard();
        $data['totalsuccess'] = $this->dasb->getprogresstotalsuccess();
        $data['totalprogress'] = $this->dasb->getprogresstotalprogress();
        $data['totalcanceled'] = $this->dasb->gettotalcanceled();
        $data['totalnodriver'] = $this->dasb->gettotalnodriver();



        $curentorderplusmonth = $this->dasb->gettotalorderplus(date("m"))->row('totalorderplus');
        $curentorderminmonth = $this->dasb->gettotalordermin(date("m"))->row('totalordermin');
        $curentdiscountmonth = $this->dasb->gettotaldiscount(date("m"))->row('totaldiscount');

        $lastorderplusmonth = $this->dasb->gettotalorderplus(date("m") - 1)->row('totalorderplus');
        $lastorderminmonth = $this->dasb->gettotalordermin(date("m") - 1)->row('totalordermin');
        $lastdiscountmonth = $this->dasb->gettotaldiscount(date("m") - 1)->row('totaldiscount');

        $data['recent_revenue'] = ($curentorderminmonth - $curentorderplusmonth) - $curentdiscountmonth;
        $data['lastmonth_revenue'] = ($lastorderminmonth - $lastorderplusmonth) - $lastdiscountmonth;


        $this->load->view('includes/header', $getview);
        $this->load->view('dashboard/index', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function deletetransaction($id)
    {
        if (demo == TRUE) {
            $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
            redirect('dashboard/index');
        } else {
            $success = $this->dasb->deletetransaction($id);
            if ($success) {
                $this->session->set_flashdata('success', 'Transaction Has Been Delete ');
                redirect('dashboard/index');
            } else {
                $this->session->set_flashdata('danger', 'Error, please try again!');
                redirect('dashboard/index');
            }
        }
    }
}
