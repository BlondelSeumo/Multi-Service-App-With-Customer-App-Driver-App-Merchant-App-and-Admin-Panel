<?php
defined('BASEPATH') or exit('No direct script access allowed');

class Statistic extends CI_Controller
{
    public function __construct()
    {
        parent::__construct();
        if ($this->session->userdata('user_name') == NULL && $this->session->userdata('password') == NULL) {
            redirect(base_url() . "login");
        }
        $this->load->model('Dashboard_model', 'dasb');
        $this->load->model('Appsettings_model', 'appset');
        $this->load->model('Wallet_model', 'wlt');
        $this->load->model('Statistic_model', 'stc');
    }

    public function general()
    {
        $getview['view'] = 'statisticgeneral';
        $getview['menu'] = $this->appset->getMenuAdmin();
        $data['counters'] = $this->dasb->count();
        $data['currency'] = $this->appset->getcurrency();
        $data['transaction'] = $this->dasb->getalltransaction();
        $data['statistic'] = $this->stc->countgeneral();


        $curentorderplusmonth = $this->dasb->gettotalorderplus(date("m"))->row('totalorderplus');
        $curentorderminmonth = $this->dasb->gettotalordermin(date("m"))->row('totalordermin');
        $curentdiscountmonth = $this->dasb->gettotaldiscount(date("m"))->row('totaldiscount');

        $lastorderplusmonth = $this->dasb->gettotalorderplus(date("m") - 1)->row('totalorderplus');
        $lastorderminmonth = $this->dasb->gettotalordermin(date("m") - 1)->row('totalordermin');
        $lastdiscountmonth = $this->dasb->gettotaldiscount(date("m") - 1)->row('totaldiscount');

        $data['recent_revenue'] = ($curentorderminmonth - $curentorderplusmonth) - $curentdiscountmonth;
        $data['lastmonth_revenue'] = ($lastorderminmonth - $lastorderplusmonth) - $lastdiscountmonth;


        $this->load->view('includes/header', $getview);
        $this->load->view('statistic/general', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function trasactionstatistic()
    {
        $getview['view'] = 'statistictransaction';
        $getview['menu'] = $this->appset->getMenuAdmin();
        $data['statistic'] = $this->stc->counttransaction();
        $data['complete'] = $this->stc->completestatisticbyservice(date('d'), date('m'), date('Y'));
        $data['totalsuccess'] = $this->dasb->getentireprogresstotalsuccess();
        $data['totalprogress'] = $this->dasb->getentireprogresstotalprogress();
        $data['countpassanger'] = $this->stc->countservicepassanger();
        $data['countshipment'] = $this->stc->countserviceshipment();
        $data['countrental'] = $this->stc->countservicerental();
        $data['countpurchasing'] = $this->stc->countservicepurchasing();
        $data['totalcanceled'] = $this->dasb->getentiretotalcanceled();
        $data['totalnodriver'] = $this->dasb->getentiretotalnodriver();


        $this->load->view('includes/header', $getview);
        $this->load->view('statistic/transaction', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function earningsstatistic()
    {
        $getview['view'] = 'statisticfinance';
        $getview['menu'] = $this->appset->getMenuAdmin();
        $data['totaldiscount'] = $this->wlt->gettotaldiscount();
        $data['totalorderplus'] = $this->wlt->gettotalorderplus();
        $data['totalordermin'] = $this->wlt->gettotalordermin();
        $data['totalwithdraw'] = $this->wlt->gettotalwithdraw();
        $data['totaltopup'] = $this->wlt->gettotaltopup();
        $data['balance'] = $this->wlt->getallbalance();
        $data['currency'] = $this->appset->getcurrency();
        $data['wallet'] = $this->wlt->getwallet();
        $data['totaltopupadmin'] = $this->stc->totaltopupbyadmin();
        $data['totaltopupstripe'] = $this->stc->totaltopupbystripe();
        $data['totaltopuppaypal'] = $this->stc->totaltopupbypaypal();
        $data['totaltopuptransfer'] = $this->stc->totaltopupbytransfer();

        $this->load->view('includes/header', $getview);
        $this->load->view('statistic/earnings', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function valuationstatistic()
    {
        $getview['view'] = 'valuation';
        $getview['menu'] = $this->appset->getMenuAdmin();
        $data['currency'] = $this->appset->getcurrency();
        $data['totaltransvalue'] = $this->stc->getalltransactionvalue();
        $data['completetransvalue'] = $this->stc->getcompletetransactionvalue();
        $data['canceltransvalue'] = $this->stc->getcanceltransactionvalue();
        $data['nodrivertransvalue'] = $this->stc->getnodrivertransactionvalue();
        $data['complete'] = $this->stc->completevaluebyservice(date('d'), date('m'), date('Y'));
        $data['statistic'] = $this->stc->servicevalue();
        $data['valuepassanger'] = $this->stc->valueservicepassanger();
        $data['valueshipment'] = $this->stc->valueserviceshipment();
        $data['valuerental'] = $this->stc->valueservicerental();
        $data['valuepurchasing'] = $this->stc->valueservicepurchasing();

        $this->load->view('includes/header', $getview);
        $this->load->view('statistic/valuation', $data);
        $this->load->view('includes/footer', $getview);
    }
}
