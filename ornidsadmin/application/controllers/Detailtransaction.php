<?php
defined('BASEPATH') or exit('No direct script access allowed');

class Detailtransaction extends CI_Controller
{
    public function __construct()
    {
        parent::__construct();
        if ($this->session->userdata('user_name') == NULL && $this->session->userdata('password') == NULL) {
            redirect(base_url() . "login");
        }
        $this->load->model('Dashboard_model', 'dasb');
        $this->load->model('Appsettings_model', 'appset');
        $this->load->model('Notification_model', 'notif');
    }

    public function index($id)
    {
        $getview['view'] = 'detailtransaction';
        $getview['menu'] = $this->appset->getMenuAdmin();
        $data['transaction'] = $this->dasb->gettransactionById($id);
        $data['currency'] = $this->appset->getcurrency();
        $data['itemtransaction'] = $this->dasb->getitembyid($id);

        $this->load->view('includes/header', $getview);
        $this->load->view('detailtransaction/index', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function cancletransaction($id)
    {
        $dataget = $this->dasb->gettransactionById($id);

        $driver_id      = $dataget['driver_id'];
        $transaction_id   = $dataget['id'];
        $token_user     = $dataget['token'];
        $token_driver     = $dataget['reg_id'];
        $this->notif->notif_cancel_user($driver_id, $transaction_id, $token_user);
        $this->notif->notif_cancel_driver($transaction_id, $token_driver);
        $this->dasb->edittransactonstatusbyid($id);
        $this->dasb->editdriverstatusbyid($driver_id);
        redirect('dashboard/index');
    }
}
