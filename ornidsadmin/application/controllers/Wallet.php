<?php
defined('BASEPATH') or exit('No direct script access allowed');

class Wallet extends CI_Controller
{
    public function __construct()
    {
        parent::__construct();
        if ($this->session->userdata('user_name') == NULL && $this->session->userdata('password') == NULL) {
            redirect(base_url() . "login");
        }
        $this->load->model('Wallet_model', 'wlt');
        $this->load->model('Appsettings_model', 'appset');
        $this->load->model('customerdata_model', 'user');
        $this->load->model('Appsettings_model', 'appset');
    }

    public function walletdata()
    {
        $getview['view'] = 'walletdata';
        $data['totaldiscount'] = $this->wlt->gettotaldiscount();
        $data['totalorderplus'] = $this->wlt->gettotalorderplus();
        $data['totalordermin'] = $this->wlt->gettotalordermin();
        $data['totalwithdraw'] = $this->wlt->gettotalwithdraw();
        $data['totaltopup'] = $this->wlt->gettotaltopup();
        $data['balance'] = $this->wlt->getallbalance();
        $data['currency'] = $this->appset->getcurrency();
        $data['wallet'] = $this->wlt->getwallet();

        $getview['menu'] = $this->appset->getMenuAdmin();
        $this->load->view('includes/header', $getview);
        $this->load->view('wallet/walletdata', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function topupdata()
    {
        $getview['view'] = 'topupdata';
        $data['totaldiscount'] = $this->wlt->gettotaldiscount();
        $data['totalorderplus'] = $this->wlt->gettotalorderplus();
        $data['totalordermin'] = $this->wlt->gettotalordermin();
        $data['totalwithdraw'] = $this->wlt->gettotalwithdraw();
        $data['totaltopup'] = $this->wlt->gettotaltopup();
        $data['balance'] = $this->wlt->getallbalance();
        $data['currency'] = $this->appset->getcurrency();
        $data['wallet'] = $this->wlt->getwallet();

        $getview['menu'] = $this->appset->getMenuAdmin();
        $this->load->view('includes/header', $getview);
        $this->load->view('wallet/topupdata', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function addtopup()
    {
        $getview['view'] = 'addtopup';
        $data['currency'] = $this->user->getcurrency();
        $data['balance'] = $this->wlt->getallsaldouser();

        $getview['menu'] = $this->appset->getMenuAdmin();
        $this->load->view('includes/header', $getview);
        $this->load->view('wallet/manualtopup', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function addwithdraw()
    {
        $getview['view'] = 'addtopup';
        $data['currency'] = $this->user->getcurrency();
        $data['balance'] = $this->wlt->getallsaldouser();

        $getview['menu'] = $this->appset->getMenuAdmin();
        $this->load->view('includes/header', $getview);
        $this->load->view('wallet/manualwithdraw', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function withdrawdata()
    {
        $getview['view'] = 'withdrawdata';
        $data['totaldiscount'] = $this->wlt->gettotaldiscount();
        $data['totalorderplus'] = $this->wlt->gettotalorderplus();
        $data['totalordermin'] = $this->wlt->gettotalordermin();
        $data['totalwithdraw'] = $this->wlt->gettotalwithdraw();
        $data['totaltopup'] = $this->wlt->gettotaltopup();
        $data['balance'] = $this->wlt->getallbalance();
        $data['currency'] = $this->appset->getcurrency();
        $data['wallet'] = $this->wlt->getwallet();

        $getview['menu'] = $this->appset->getMenuAdmin();
        $this->load->view('includes/header', $getview);
        $this->load->view('wallet/withdrawdata', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function addtopupdata()
    {
        if ($this->input->post('type_user') == 'customer') {
            $id_user = $this->input->post('customer_id');
        } elseif ($this->input->post('type_user') == 'partner') {
            $id_user = $this->input->post('partner_id');
        } else {
            $id_user = $this->input->post('driver_id');
        }

        $balance = html_escape($this->input->post('balance', TRUE));
        $remove = array(".", ",");
        $add = array("", "");

        $data = [
            'id_user'                       => $id_user,
            'balance'                         => str_replace($remove, $add, $balance),
            'type_user'                     => $this->input->post('type_user')
        ];

        $success = $this->wlt->updatesaldowallet($data);
        if ($success) {
            $this->session->set_flashdata('success', 'Top Up Has Been Added');
            redirect('wallet/walletdata');
        } else {
            $this->session->set_flashdata('danger', 'Error, please try again!');
            redirect('wallet/addtopup');
        }
    }

    public function addwithdrawdata()
    {
        if ($this->input->post('type_user') == 'customer') {
            $id_user = $this->input->post('customer_id');
        } elseif ($this->input->post('type_user') == 'partner') {
            $id_user = $this->input->post('partner_id');
        } else {
            $id_user = $this->input->post('driver_id');
        }


        $balance = html_escape($this->input->post('balance', TRUE));
        $remove = array(".", ",");
        $add = array("", "");

        $data = [
            'id_user'                       => $id_user,
            'balance'                         => str_replace($remove, $add, $balance),
            'type_user'                     => $this->input->post('type_user')
        ];

        $data2 = [
            'bank'                          => $this->input->post('bank'),
            'holder_name'                  => $this->input->post('holder_name'),
            'wallet_account'                      => $this->input->post('wallet_account'),
        ];


        $success = $this->wlt->updatesaldowalletwithdraw($data, $data2);
        if ($success) {
            $this->session->set_flashdata('success', 'Withdraw Has Been Added');
            redirect('wallet/walletdata');
        } else {
            $this->session->set_flashdata('danger', 'Error, please try again!');
            redirect('wallet/addwithdraw');
        }
    }

    public function tconfirm($id, $id_user, $amount)
    {
        $token = $this->wlt->gettoken($id_user);
        $regid = $this->wlt->getregid($id_user);
        $tokenmerchant = $this->wlt->gettokenmerchant($id_user);

        if ($token == NULL and $tokenmerchant == NULL and $regid != NULL) {
            $topic = $regid['reg_id'];
        } else if ($regid == NULL and $tokenmerchant == NULL and $token != NULL) {
            $topic = $token['token'];
        } else if ($regid == NULL and $token == NULL and $tokenmerchant != NULL) {
            $topic = $tokenmerchant['merchant_token'];
        }

        $title = 'Topup success';
        $message = 'We Have Confirmed Your Topup';
        $balance = $this->wlt->getsaldo($id_user);



        $this->wlt->editsaldotopup($id_user, $amount, $balance);
        $success = $this->wlt->editstatuswithdrawbyid($id);
        $this->wlt->send_notif($title, $message, $topic);

        if ($success) {
            $this->session->set_flashdata('success', 'Top Up Has Been Confirmed');
            redirect('wallet/topupdata');
        } else {
            $this->session->set_flashdata('danger', 'Error, please try again!');
            redirect('wallet/topupdata');
        }
    }

    public function tcancel($id, $id_user)
    {
        $token = $this->wlt->gettoken($id_user);
        $regid = $this->wlt->getregid($id_user);
        $tokenmerchant = $this->wlt->gettokenmerchant($id_user);

        if ($token == NULL and $regid != NULL) {
            $topic = $regid['reg_id'];
        }

        if ($regid == NULL and $token != NULL) {
            $topic = $token['token'];
        }

        if ($regid == NULL and $token == NULL and $tokenmerchant != NULL) {
            $topic = $tokenmerchant['merchant_token'];
        }

        $title = 'Topup canceled';
        $message = 'Sorry, topup has been canceled';

        $success = $this->wlt->cancelstatuswithdrawbyid($id);
        $this->wlt->send_notif($title, $message, $topic);
        if ($success) {
            $this->session->set_flashdata('success', 'topup has been canceled');
            redirect('wallet/topupdata');
        } else {
            $this->session->set_flashdata('danger', 'Error, please try again!');
            redirect('wallet/topupdata');
        }
    }

    public function wconfirm($id, $id_user, $amount)
    {
        $token = $this->wlt->gettoken($id_user);
        $regid = $this->wlt->getregid($id_user);
        $tokenmerchant = $this->wlt->gettokenmerchant($id_user);

        if ($token == NULL and $tokenmerchant == NULL and $regid != NULL) {
            $topic = $regid['reg_id'];
        } else if ($regid == NULL and $tokenmerchant == NULL and $token != NULL) {
            $topic = $token['token'];
        } else if ($regid == NULL and $token == NULL and $tokenmerchant != NULL) {
            $topic = $tokenmerchant['merchant_token'];
        }

        $title = 'Withdraw Success';
        $message = 'Withdraw Has Been Confirmed';
        $balance = $this->wlt->getsaldo($id_user);



        $this->wlt->editsaldo($id_user, $amount, $balance);
        $success = $this->wlt->editstatuswithdrawbyid($id);
        $this->wlt->send_notif($title, $message, $topic);

        if ($success) {
            $this->session->set_flashdata('success', 'withdraw Has Been Confirmed');
            redirect('wallet/withdrawdata');
        } else {
            $this->session->set_flashdata('danger', 'Error, please try again!');
            redirect('wallet/withdrawdata');
        }
    }

    public function wcancel($id, $id_user)
    {
        $token = $this->wlt->gettoken($id_user);
        $regid = $this->wlt->getregid($id_user);
        $tokenmerchant = $this->wlt->gettokenmerchant($id_user);

        if ($token == NULL and $tokenmerchant == NULL and $regid != NULL) {
            $topic = $regid['reg_id'];
        } else if ($regid == NULL and $tokenmerchant == NULL and $token != NULL) {
            $topic = $token['token'];
        } else if ($regid == NULL and $token == NULL and $tokenmerchant != NULL) {
            $topic = $tokenmerchant['merchant_token'];
        }

        $title = 'Withdraw Cancel';
        $message = 'Withdraw Has Been Canceled';

        $success = $this->wlt->cancelstatuswithdrawbyid($id);
        $this->wlt->send_notif($title, $message, $topic);
        if ($success) {
            $this->session->set_flashdata('success', 'withdraw has been canceled');
            redirect('wallet/withdrawdata');
        } else {
            $this->session->set_flashdata('danger', 'Error, please try again!');
            redirect('wallet/withdrawdata');
        }
    }
}
