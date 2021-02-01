<?php
defined('BASEPATH') or exit('No direct script access allowed');

class Settings extends CI_Controller
{
    public function __construct()
    {
        parent::__construct();
        if ($this->session->userdata('user_name') == NULL && $this->session->userdata('password') == NULL) {
            redirect(base_url() . "login");
        }
        $this->load->model('Appsettings_model', 'appset');
        $this->load->library('form_validation');
        $this->load->library('session');
    }

    public function appsettings()
    {
        $data['appsettings'] = $this->appset->getappbyid();
        $getview['view'] = 'appsettings';
        $getview['menu'] = $this->appset->getMenuAdmin();
        $this->load->view('includes/header', $getview);
        $this->load->view('settings/applicationsetting', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function emailsettings()
    {
        $getview['view'] = 'emailsettings';
        $data['appsettings'] = $this->appset->getappbyid();
        $getview['menu'] = $this->appset->getMenuAdmin();
        $this->load->view('includes/header', $getview);
        $this->load->view('settings/emailsettings', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function smtpsettings()
    {
        $getview['view'] = 'appsettings';
        $data['appsettings'] = $this->appset->getappbyid();
        $getview['menu'] = $this->appset->getMenuAdmin();
        $this->load->view('includes/header', $getview);
        $this->load->view('settings/smtpsettings', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function stripesettings()
    {
        $getview['view'] = 'appsettings';
        $data['appsettings'] = $this->appset->getappbyid();
        $getview['menu'] = $this->appset->getMenuAdmin();
        $this->load->view('includes/header', $getview);
        $this->load->view('settings/stripesettings', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function paypalsettings()
    {
        $getview['view'] = 'appsettings';
        $data['appsettings'] = $this->appset->getappbyid();
        $getview['menu'] = $this->appset->getMenuAdmin();
        $this->load->view('includes/header', $getview);
        $this->load->view('settings/paypalsettings', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function banktransfersettings()
    {
        $getview['view'] = 'banktransfersettings';
        $data['banktransfer'] = $this->appset->getallbanktransfer();

        $getview['menu'] = $this->appset->getMenuAdmin();
        $this->load->view('includes/header', $getview);
        $this->load->view('settings/banktransfersettings', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function addbankaccount()
    {
        $getview['view'] = 'appsettings';

        $getview['menu'] = $this->appset->getMenuAdmin();
        $this->load->view('includes/header', $getview);
        $this->load->view('settings/addbankaccount');
        $this->load->view('includes/footer', $getview);
    }

    public function editbankaccount($id)
    {
        $getview['view'] = 'appsettings';
        $data['transfer'] = $this->appset->getbankid($id);
        $getview['menu'] = $this->appset->getMenuAdmin();
        $this->load->view('includes/header', $getview);
        $this->load->view('settings/editbankaccount', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function adddatabank()
    {
        $this->form_validation->set_rules('bank_name', 'bank_name', 'trim|prep_for_form');
        $this->form_validation->set_rules('bank_account', 'bank_account', 'trim|prep_for_form');
        if ($this->form_validation->run() == TRUE) {
            $config['upload_path']     = './images/bank/';
            $config['allowed_types'] = 'gif|jpg|png|jpeg';
            $config['max_size']         = '10000';
            $config['file_name']     = time();
            $config['encrypt_name']     = true;
            $this->load->library('upload', $config);

            if ($this->upload->do_upload('bank_logo')) {
                $app_logo = html_escape($this->upload->data('file_name'));
            }

            $data = [
                'bank_name' => html_escape($this->input->post('bank_name', TRUE)),
                'bank_account' => html_escape($this->input->post('bank_account', TRUE)),
                'bank_status' => html_escape($this->input->post('bank_status', TRUE)),
                'bank_logo' => $app_logo
            ];

            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('appsettings/addbankaccount');
            } else {

                $success = $this->appset->adddatarekening($data);
                if ($success) {
                    $this->session->set_flashdata('success', 'Bank Has Been added!');
                    redirect('settings/banktransfersettings');
                } else {
                    $this->session->set_flashdata('danger', 'Error, please try again!');
                    redirect('appsettings/addbankaccount');
                }
            }
        } else {
            $this->session->set_flashdata('danger', 'Error, please try again!');
            redirect('appsettings/addbankaccount');
        }
    }

    public function editapp()
    {


        $this->form_validation->set_rules('app_email', 'app_email', 'trim|prep_for_form');
        $this->form_validation->set_rules('app_website', 'app_website', 'trim|prep_for_form');
        $this->form_validation->set_rules('app_linkgoogle', 'app_linkgoogle', 'trim|prep_for_form');
        $this->form_validation->set_rules('app_currency', 'app_currency', 'trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {
            $config['upload_path']     = './asset/images/icon/';
            $config['allowed_types'] = 'gif|jpg|png|jpeg';
            $config['max_size']         = '10000';
            $config['file_name']     = 'name';
            $config['encrypt_name']     = true;
            $this->load->library('upload', $config);
            $data = $this->appset->getappbyid();


            if ($this->upload->do_upload('app_logo')) {
                if ($data['app_logo'] != 'noimage.jpg') {
                    $gambar = $data['app_logo'];
                    unlink('asset/images/icon/' . $gambar);
                }

                $app_logo = html_escape($this->upload->data('file_name'));
            } else {
                $app_logo = $data['app_logo'];
            }

            $data             = [
                'app_logo'                    => $app_logo,
                'app_email'                    => html_escape($this->input->post('app_email', TRUE)),
                'app_website'                => html_escape($this->input->post('app_website', TRUE)),
                'app_privacy_policy'        => $this->input->post('app_privacy_policy', TRUE),
                'app_aboutus'                => $this->input->post('app_aboutus', TRUE),
                'app_address'                => $this->input->post('app_address'),
                'app_linkgoogle'            => html_escape($this->input->post('app_linkgoogle', TRUE)),
                'app_name'                  => html_escape($this->input->post('app_name', TRUE)),
                'app_currency'                => html_escape($this->input->post('app_currency', TRUE))
            ];

            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('settings/appsettings');
            } else {

                $success = $this->appset->editdataappsettings($data);
                if ($success) {
                    $this->session->set_flashdata('success', 'APP Has Been Changed');
                    redirect('settings/appsettings');
                } else {
                    $this->session->set_flashdata('danger', 'Error, please try again!');
                    redirect('settings/appsettings');
                }
            }
        } else {
            $this->session->set_flashdata('danger', 'Error, please try again!');
            redirect('settings/appsettings');
        }
    }

    public function editemail()
    {

        $this->form_validation->set_rules('email_subject', 'email_subject', 'trim|prep_for_form');
        $this->form_validation->set_rules('email_subject_confirm', 'email_subject', 'trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {
            $data             = [
                'email_subject'                    => html_escape($this->input->post('email_subject', TRUE)),
                'email_subject_confirm'                    => html_escape($this->input->post('email_subject_confirm', TRUE)),
                'email_text1'                    => $this->input->post('email_text1'),
                'email_text2'                    => $this->input->post('email_text2'),
                'email_text3'                    => $this->input->post('email_text3'),
                'email_text4'                    => $this->input->post('email_text4')
            ];


            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('settings/emailsettings');
            } else {

                $success = $this->appset->editdataappsettings($data);
                if ($success) {
                    $this->session->set_flashdata('success', 'APP Has Been Changed');
                    redirect('settings/emailsettings');
                } else {
                    $this->session->set_flashdata('danger', 'Error, please try again!');
                    redirect('settings/emailsettings');
                }
            }
        } else {

            $this->session->set_flashdata('danger', 'Error, please try again!');
            redirect('settings/emailsettings');
        }
    }

    public function editsmtp()
    {

        $this->form_validation->set_rules('smtp_host', 'smtp_host', 'trim|prep_for_form');
        $this->form_validation->set_rules('smtp_port', 'smtp_port', 'trim|prep_for_form');
        $this->form_validation->set_rules('smtp_username', 'smtp_username', 'trim|prep_for_form');
        $this->form_validation->set_rules('smtp_password', 'smtp_password', 'trim|prep_for_form');
        $this->form_validation->set_rules('smtp_form', 'smtp_form', 'trim|prep_for_form');
        $this->form_validation->set_rules('smtp_secure', 'smtp_secure', 'trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {
            $data             = [
                'smtp_host'                        => html_escape($this->input->post('smtp_host', TRUE)),
                'smtp_port'                        => html_escape($this->input->post('smtp_port', TRUE)),
                'smtp_username'                    => html_escape($this->input->post('smtp_username', TRUE)),
                'smtp_password'                    => html_escape($this->input->post('smtp_password', TRUE)),
                'smtp_from'                        => html_escape($this->input->post('smtp_from', TRUE)),
                'smtp_secure'                    => html_escape($this->input->post('smtp_secure', TRUE))
            ];


            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('settings/smtpsettings');
            } else {

                $success = $this->appset->editdataappsettings($data);
                if ($success) {
                    $this->session->set_flashdata('success', 'APP Has Been Changed');
                    redirect('settings/smtpsettings');
                } else {
                    $this->session->set_flashdata('danger', 'Error, please try again!');
                    redirect('settings/smtpsettings');
                }
            }
        } else {

            $this->session->set_flashdata('danger', 'Error, please try again!');
            redirect('settings/smtpsettings');
        }
    }

    public function editstripe()
    {

        $this->form_validation->set_rules('stripe_secret_key', 'stripe_secret_key', 'trim|prep_for_form');
        $this->form_validation->set_rules('stripe_published_key', 'stripe_published_key', 'trim|prep_for_form');
        $this->form_validation->set_rules('stripe_status', 'stripe_status', 'trim|prep_for_form');
        $this->form_validation->set_rules('stripe_active', 'stripe_active', 'trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {
            $data             = [
                'stripe_secret_key'                    => html_escape($this->input->post('stripe_secret_key', TRUE)),
                'stripe_published_key'                => html_escape($this->input->post('stripe_published_key', TRUE)),
                'stripe_status'                        => html_escape($this->input->post('stripe_status', TRUE)),
                'stripe_active'                        => html_escape($this->input->post('stripe_active', TRUE))
            ];
            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('settings/stripesettings');
            } else {

                $success = $this->appset->editdataappsettings($data);
                if ($success) {
                    $this->session->set_flashdata('success', 'APP Has Been Changed');
                    redirect('settings/stripesettings');
                } else {
                    $this->session->set_flashdata('danger', 'Error, please try again!');
                    redirect('settings/stripesettings');
                }
            }
        } else {

            $this->session->set_flashdata('danger', 'Error, please try again!');
            redirect('settings/stripesettings');
        }
    }

    public function editpaypal()
    {

        $this->form_validation->set_rules('paypal_key', 'paypal_key', 'trim|prep_for_form');
        $this->form_validation->set_rules('app_currency_text', 'stripe_published_key', 'trim|prep_for_form');
        $this->form_validation->set_rules('paypal_mode', 'paypal_mode', 'trim|prep_for_form');
        $this->form_validation->set_rules('paypal_active', 'paypal_active', 'trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {
            $data             = [
                'paypal_key'                    => html_escape($this->input->post('paypal_key', TRUE)),
                'app_currency_text'                => html_escape($this->input->post('app_currency_text', TRUE)),
                'paypal_mode'                        => html_escape($this->input->post('paypal_mode', TRUE)),
                'paypal_active'                        => html_escape($this->input->post('paypal_active', TRUE))
            ];
            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('settings/paypalsettings');
            } else {

                $success = $this->appset->editdataappsettings($data);
                if ($success) {
                    $this->session->set_flashdata('success', 'Paypal Has Been Changed');
                    redirect('settings/paypalsettings');
                } else {
                    $this->session->set_flashdata('danger', 'Error, please try again!');
                    redirect('settings/paypalsettings');
                }
            }
        } else {

            $this->session->set_flashdata('danger', 'Error, please try again!');
            redirect('settings/paypalsettings');
        }
    }

    public function deletebankdata($id)
    {
        if (demo == TRUE) {
            $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
            redirect('settings/banktransfersettings');
        } else {

            $success = $this->appset->deleterekening($id);
            if ($success) {
                $dataget = $this->appset->getbankid($id);
                $gambar = $dataget['bank_logo'];
                unlink('./images/bank/' . $gambar);
                $this->session->set_flashdata('success', 'Bank Has Been deleted!');
                redirect('settings/banktransfersettings');
            } else {
                $this->session->set_flashdata('danger', 'Error, please try again!');
                redirect('settings/banktransfersettings');
            }
        }
    }

    public function editdatabank($id)
    {
        $this->form_validation->set_rules('bank_name', 'bank_name', 'trim|prep_for_form');
        $this->form_validation->set_rules('bank_account', 'bank_account', 'trim|prep_for_form');
        if ($this->form_validation->run() == TRUE) {
            $config['upload_path']     = './images/bank/';
            $config['allowed_types'] = 'gif|jpg|png|jpeg';
            $config['max_size']         = '10000';
            $config['file_name']     = time();
            $config['encrypt_name']     = true;
            $this->load->library('upload', $config);
            $dataget = $this->appset->getbankid($id);

            if ($this->upload->do_upload('bank_logo')) {
                if ($dataget['bank_logo'] != 'noimage.jpg') {
                    $gambar = $dataget['bank_logo'];
                    unlink('./images/bank/' . $gambar);
                }
                $gambar = $dataget['bank_logo'];
                unlink('./images/bank/' . $gambar);
                $app_logo = html_escape($this->upload->data('file_name'));
            } else {
                $app_logo = $dataget['bank_logo'];
            }

            $data = [
                'bank_name' => html_escape($this->input->post('bank_name', TRUE)),
                'bank_account' => html_escape($this->input->post('bank_account', TRUE)),
                'bank_status' => html_escape($this->input->post('bank_status', TRUE)),
                'bank_logo' => $app_logo
            ];
            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('appsettings/editbankaccount/' . $id);
            } else {
                $success = $this->appset->editdatarekening($data, $id);
                if ($success) {
                    $this->session->set_flashdata('success', 'Bank Has Been changed!');
                    redirect('settings/banktransfersettings');
                } else {
                    $this->session->set_flashdata('danger', 'Error, please try again!');
                    redirect('appsettings/editbankaccount/' . $id);
                }
            }
        } else {
            $this->session->set_flashdata('danger', 'Error, please try again!');
            redirect('appsettings/editbankaccount/' . $id);
        }
    }
}
