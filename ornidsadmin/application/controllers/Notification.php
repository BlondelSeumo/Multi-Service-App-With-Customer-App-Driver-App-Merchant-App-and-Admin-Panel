<?php
defined('BASEPATH') or exit('No direct script access allowed');

class Notification extends CI_Controller
{
    public function __construct()
    {
        parent::__construct();
        if ($this->session->userdata('user_name') == NULL && $this->session->userdata('password') == NULL) {
            redirect(base_url() . "login");
        }

        $this->load->model('Driverdata_model', 'driver');
        $this->load->model('Customerdata_model', 'user');
        $this->load->model('Merchantdata_model', 'partner');
        $this->load->model('appsettings_model', 'app');
        $this->load->model('email_model', 'email_model');
        $this->load->model('notification_model', 'notif');
    }

    public function sendemail()
    {
        $getview['view'] = 'sendemail';
        $getview['menu'] = $this->app->getMenuAdmin();
        $data['driver'] = $this->driver->getalldriver();
        $data['user'] = $this->user->getallusers();
        $data['partner'] = $this->partner->getAllmerchant();
        $this->load->view('includes/header', $getview);
        $this->load->view('notification/sendemail', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function sendnotification()
    {
        $getview['view'] = 'sendemail';
        $getview['menu'] = $this->app->getMenuAdmin();
        $this->load->view('includes/header', $getview);
        $this->load->view('notification/sendnotification');
        $this->load->view('includes/footer', $getview);
    }

    public function sendemaildata()
    {
        if (demo == TRUE) {
            $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
            redirect('notification/sendemail');
        } else {
            $data['app'] = $this->app->getappbyid();

            $emailpelanggan = $this->input->post('emailpelanggan');
            $emaildriver = $this->input->post('emaildriver');
            $emailmitra = $this->input->post('emailmitra');
            $emailothers = $this->input->post('emailothers');
            $sendto = $this->input->post('sendto');

            if ($sendto == 'users') {
                $emailuser = $emailpelanggan;
            } elseif ($sendto == 'drivers') {
                $emailuser = $emaildriver;
            } elseif ($sendto == 'merchant') {
                $emailuser = $emailmitra;
            } else {
                $emailuser = $emailothers;
            }
            $subject = $this->input->post('subject');
            $emailmessage = $this->input->post('content');
            $host = $data['app']['smtp_host'];
            $port = $data['app']['smtp_port'];
            $username = $data['app']['smtp_username'];
            $password = $data['app']['smtp_password'];
            $from = $data['app']['smtp_from'];
            $appname = $data['app']['app_name'];
            $secure = $data['app']['smtp_secure'];
            $address = $data['app']['app_address'];
            $linkgoogle = $data['app']['app_linkgoogle'];
            $web = $data['app']['app_website'];

            $content = $this->email_model->template2($subject, $emailmessage, $address, $appname, $linkgoogle, $web);
            $success = $this->email_model->emailsend($subject, $emailuser, $content, $host, $port, $username, $password, $from, $appname, $secure);

            if ($success) {
                $this->session->set_flashdata('success', 'Email Hass Been Sended');
                redirect('notification/sendemail');
            } else {
                $this->session->set_flashdata('danger', 'Error, please try again!');
                redirect('notification/sendemail');
            }
        }
    }

    public function send()
    {

        $topic = $this->input->post('topic');
        $title = $this->input->post('title');
        $message = $this->input->post('message');
        $success = $this->notif->send_notif($title, $message, $topic);
        if ($success) {
            $this->session->set_flashdata('success', 'Notification Hass Been Sended');
            redirect('notification/sendnotification');
        } else {
            $this->session->set_flashdata('danger', 'Error, please try again!');
            redirect('notification/sendnotification');
        }
    }
}
