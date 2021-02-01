<?php
defined('BASEPATH') or exit('No direct script access allowed');

class Promotion extends CI_Controller
{
    public function __construct()
    {
        parent::__construct();
        if ($this->session->userdata('user_name') == NULL && $this->session->userdata('password') == NULL) {
            redirect(base_url() . "login");
        }
        $this->load->model('Slider_model', 'sldr');
        $this->load->model('Promocode_model', 'prmcd');
        $this->load->model('Service_model', 'srv');
        $this->load->library('form_validation');
        $this->load->library('upload');
        $this->load->model('Appsettings_model', 'appset');
    }

    public function sliderdata()
    {
        $getview['view'] = 'sliderdata';
        $data['slider'] = $this->sldr->getAllslider();
        $getview['menu'] = $this->appset->getMenuAdmin();
        $this->load->view('includes/header', $getview);
        $this->load->view('promotion/sliderdata', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function promocodedata()
    {
        $getview['view'] = 'sliderdata';
        $data['promocode'] = $this->prmcd->getAllpromocode();
        $getview['menu'] = $this->appset->getMenuAdmin();
        $this->load->view('includes/header', $getview);
        $this->load->view('promotion/promocode', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function addpromoslider()

    {
        $this->form_validation->set_rules('exp_date', 'exp_date', 'trim|prep_for_form');
        $this->form_validation->set_rules('promotion_service', 'promotion_service', 'trim|prep_for_form');
        $this->form_validation->set_rules('promotion_link', 'promotion_link', 'trim|prep_for_form');
        $this->form_validation->set_rules('is_show', 'is_show', 'trim|prep_for_form');
        $this->form_validation->set_rules('promotion_type', 'promotion_type', 'trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {
            $config['upload_path']     = './images/promo';
            $config['allowed_types'] = 'gif|jpg|png|jpeg';
            $config['max_size']         = '30000';
            $config['file_name']     = 'name';
            $config['encrypt_name']     = true;
            $this->upload->initialize($config);

            if ($this->upload->do_upload('photo')) {
                $gambar = $this->upload->data('file_name');
            } else {
                $gambar = 'noimage.jpg';
            }


            $type = html_escape($this->input->post('promotion_type', TRUE));

            $getfitur = $this->sldr->getserviceid(html_escape($this->input->post('promotion_service', TRUE)));
            if ($type != 'link') {
                $service = html_escape($this->input->post('promotion_service', TRUE));
                $linkpromo = $getfitur['home'];
            } else {
                $service = 0;
                $linkpromo = html_escape($this->input->post('promotion_link', TRUE));
            }



            $data             = [
                'photo'                         => $gambar,
                'exp_date'                  => html_escape($this->input->post('exp_date', TRUE)),
                'promotion_service'         => $service,
                'promotion_type'            => html_escape($this->input->post('promotion_type', TRUE)),
                'is_show'                   => html_escape($this->input->post('is_show', TRUE)),
                'promotion_link'            => $linkpromo
            ];

            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('promotion/sliderdata');
            } else {
                $this->sldr->adddataslider($data);
                $this->session->set_flashdata('success', 'Promotion Slider Has Been Added');
                redirect('promotion/sliderdata');
            }
        } else {

            $getview['view'] = 'addsliderdata';
            $getview['menu'] = $this->appset->getMenuAdmin();
            $data['service'] = $this->srv->getallservice();

            $this->load->view('includes/header', $getview);
            $this->load->view('promotion/addslider', $data);
            $this->load->view('includes/footer', $getview);
        }
    }

    public function editpromocode($id)
    {
        $getview['view'] = 'addpromotioncode';
        $getview['menu'] = $this->appset->getMenuAdmin();
        $data['service'] = $this->srv->getallservice();
        $data['promo'] = $this->prmcd->getpromocodebyid($id);
        $this->load->view('includes/header', $getview);
        $this->load->view('promotion/editpromocode', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function addpromocode()
    {
        $getview['view'] = 'addpromotioncode';
        $getview['menu'] = $this->appset->getMenuAdmin();
        $data['service'] = $this->srv->getallservice();

        $this->load->view('includes/header', $getview);
        $this->load->view('promotion/addpromocode', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function editpromosliderview($id)
    {
        $getview['view'] = 'editsliderdata';
        $getview['menu'] = $this->appset->getMenuAdmin();
        $data = $this->sldr->getsliderById($id);
        $data['service'] = $this->srv->getallservice();
        $this->load->view('includes/header', $getview);
        $this->load->view('promotion/editslider', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function editpromoslider($id)
    {
        $this->form_validation->set_rules('exp_date', 'exp_date', 'trim|prep_for_form');
        $this->form_validation->set_rules('promotion_service', 'promotion_service', 'trim|prep_for_form');
        $this->form_validation->set_rules('promotion_link', 'promotion_link', 'trim|prep_for_form');
        $this->form_validation->set_rules('is_show', 'is_show', 'trim|prep_for_form');
        $this->form_validation->set_rules('promotion_type', 'promotion_type', 'trim|prep_for_form');

        $data = $this->sldr->getsliderById($id);
        $data['service'] = $this->srv->getallservice();
        $id = html_escape($this->input->post('id', TRUE));

        if ($this->form_validation->run() == TRUE) {
            $config['upload_path']     = './images/promo/';
            $config['allowed_types'] = 'gif|jpg|png|jpeg';
            $config['max_size']         = '10000';
            $config['file_name']     = 'name';
            $config['encrypt_name']     = true;
            $this->upload->initialize($config);

            if ($this->upload->do_upload('photo')) {
                if ($data['photo'] != 'noimage.jpg') {
                    $gambar = $data['photo'];
                    unlink('images/promo/' . $gambar);
                }
                $gambar = html_escape($this->upload->data('file_name'));
            } else {
                $gambar = $data['photo'];
            }

            $type = html_escape($this->input->post('promotion_type', TRUE));

            $getfitur = $this->sldr->getserviceId(html_escape($this->input->post('promotion_service', TRUE)));
            if ($type != 'link') {
                $service = html_escape($this->input->post('promotion_service', TRUE));
                $linkpromo = $getfitur['home'];
            } else {
                $service = 0;
                $linkpromo = html_escape($this->input->post('promotion_link', TRUE));
            }

            $data             = [
                'id'                            => html_escape($this->input->post('id', TRUE)),
                'photo'                          => $gambar,
                'exp_date'              => html_escape($this->input->post('exp_date', TRUE)),
                'promotion_service'                 => $service,
                'promotion_type'                  => $type,
                'is_show'                       => html_escape($this->input->post('is_show', TRUE)),
                'promotion_link'                  => $linkpromo,
            ];

            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('promotion/editpromosliderview/' . $id);
            } else {
                $insert = $this->sldr->editdataslider($data);
                if ($insert) {
                    $this->session->set_flashdata('success', 'Promotion Slider Has Been Changed');
                    redirect('promotion/sliderdata');
                } else {
                    $this->session->set_flashdata('danger', 'Error, Please Try Again!');
                    redirect('promotion/editpromosliderview/' . $id);
                }
            }
        }
    }

    public function addpromocodedata()

    {


        $this->form_validation->set_rules('promo_title', 'promo_title', 'trim|prep_for_form');
        $this->form_validation->set_rules('promo_code', 'promo_code', 'trim|prep_for_form');
        $this->form_validation->set_rules('promo_amount', 'promo_amount', 'trim|prep_for_form');
        $this->form_validation->set_rules('promo_type', 'promo_type', 'trim|prep_for_form');
        $this->form_validation->set_rules('service', 'service', 'trim|prep_for_form');
        $this->form_validation->set_rules('status', 'status', 'trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {
            $config['upload_path']     = './images/promo';
            $config['allowed_types'] = 'gif|jpg|png|jpeg';
            $config['max_size']         = '10000';
            $config['file_name']     = 'name';
            $config['encrypt_name']     = true;
            $this->upload->initialize($config);

            if ($this->upload->do_upload('promo_image')) {
                $gambar = html_escape($this->upload->data('file_name'));
            } else {
                $gambar = 'noimage.jpg';
            }

            if ($this->input->post('promo_type') == 'persen') {
                $nominal = html_escape($this->input->post('nominal_promo_persen', TRUE));
            } else {
                $nominal = str_replace(".", "", html_escape($this->input->post('promo_amount', TRUE)));
            }

            $data             = [
                'promo_image'                       => $gambar,
                'promo_title'              => html_escape($this->input->post('promo_title', TRUE)),
                'promo_code'              => html_escape($this->input->post('promo_code', TRUE)),
                'promo_amount'              => $nominal,
                'promo_type'              => html_escape($this->input->post('promo_type', TRUE)),
                'expired'              => html_escape($this->input->post('expired', TRUE)),
                'service'                  => html_escape($this->input->post('service', TRUE)),
                'status'                       => html_escape($this->input->post('status', TRUE)),
            ];
            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('promotion/addpromocode');
            } else {
                $cekpromo = $this->prmcd->cekpromo($this->input->post('promo_code'));
                if ($cekpromo->num_rows() > 0) {
                    $this->session->set_flashdata('danger', 'Promotion code already exist');
                    redirect('promotion/addpromocode');
                } else {
                    $success = $this->prmcd->addpromocode($data);
                    if ($success) {
                        $this->session->set_flashdata('success', 'Promotion Code Has Been Added');
                        redirect('promotion/promocodedata');
                    } else {
                        $this->session->set_flashdata('danger', 'Error, Please Try Again!');
                        redirect('promotion/addpromocode');
                    }
                }
            }
        } else {
            $this->session->set_flashdata('danger', 'Error, Please Try Again!');
            redirect('promotion/addpromocode');
        }
    }

    public function deletepromocode($id)
    {
        if (demo == TRUE) {
            $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
            redirect('promotion/promocodedata');
        } else {
            $success = $this->prmcd->deletepromocodeById($id);
            if ($success) {
                $data = $this->prmcd->getpromocodeById($id);

                if ($data['promo_image'] != 'noimage.jpg') {
                    $gambar = $data['promo_image'];
                    unlink('images/promo/' . $gambar);
                }
                $this->session->set_flashdata('success', 'Promo Code Has Been deleted');
                redirect('promotion/promocodedata');
            } else {
                $this->session->set_flashdata('danger', 'Error, Please Try Again!');
                redirect('promotion/promocodedata');
            }
        }
    }

    public function deleteslider($id)
    {
        if (demo == TRUE) {
            $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
            redirect('promotion/sliderdata');
        } else {
            $data = $this->prmcd->getpromoById($id);



            $success = $this->prmcd->deletepromoById($id);
            if ($success) {
                if ($data['photo'] != 'noimage.jpg') {
                    $gambar = $data['photo'];
                    unlink('images/promo/' . $gambar);
                }
                $this->session->set_flashdata('delete', 'Promotion Slider Has Been deleted');
                redirect('promotion/sliderdata');
            } else {
                $this->session->set_flashdata('danger', 'Error, Please Try Again!');
                redirect('promotion/promocodedata');
            }
        }
    }

    public function editpromocodedata($id)
    {

        $this->form_validation->set_rules('promo_title', 'promo_title', 'trim|prep_for_form');
        $this->form_validation->set_rules('promo_code', 'promo_code', 'trim|prep_for_form');
        $this->form_validation->set_rules('promo_amount', 'promo_amount', 'trim|prep_for_form');
        $this->form_validation->set_rules('promo_type', 'promo_type', 'trim|prep_for_form');
        $this->form_validation->set_rules('service', 'service', 'trim|prep_for_form');
        $this->form_validation->set_rules('status', 'status', 'trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {
            if ($this->input->post('promo_type') == 'persen') {
                $nominal = html_escape($this->input->post('nominal_promo_persen', TRUE));
            } else {
                $nominal = str_replace(".", "", html_escape($this->input->post('promo_amount', TRUE)));
            }

            $config['upload_path']     = './images/promo/';
            $config['allowed_types'] = 'gif|jpg|png|jpeg';
            $config['max_size']         = '10000';
            $config['file_name']     = time();
            $config['encrypt_name']     = true;
            $this->upload->initialize($config);

            if ($this->upload->do_upload('promo_image')) {
                unlink('images/promo/' . $this->prmcd->getpromobyid($id)['promo_image']);
                $gambar = html_escape($this->upload->data('file_name'));
                $datainsert             = [
                    'promo_id'                  => html_escape($this->input->post('promo_id', TRUE)),
                    'promo_image'                       => $gambar,
                    'promo_title'              => html_escape($this->input->post('promo_title', TRUE)),
                    'promo_code'              => html_escape($this->input->post('promo_code', TRUE)),
                    'promo_amount'              => $nominal,
                    'promo_type'              => html_escape($this->input->post('promo_type', TRUE)),
                    'expired'              => html_escape($this->input->post('expired', TRUE)),
                    'service'                  => html_escape($this->input->post('service', TRUE)),
                    'status'                       => html_escape($this->input->post('status', TRUE)),
                ];
            } else {
                $datainsert             = [
                    'promo_id'                  => html_escape($this->input->post('promo_id', TRUE)),
                    'promo_title'              => html_escape($this->input->post('promo_title', TRUE)),
                    'promo_code'              => html_escape($this->input->post('promo_code', TRUE)),
                    'promo_amount'              => $nominal,
                    'promo_type'              => html_escape($this->input->post('promo_type', TRUE)),
                    'expired'              => html_escape($this->input->post('expired', TRUE)),
                    'service'                  => html_escape($this->input->post('service', TRUE)),
                    'status'                       => html_escape($this->input->post('status', TRUE)),
                ];
            }




            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('promotion/promocodedata');
            } else {
                $cekpromo = $this->prmcd->cekpromo($this->input->post('promo_code'));
                if ($cekpromo->num_rows() > 0 && $cekpromo->row_array()['promo_id'] != $this->input->post('promo_id')) {
                    $this->session->set_flashdata('danger', 'Promotion code already exist');
                    redirect('promotion/editpromocode/' . $id);
                } else {
                    $success = $this->prmcd->editpromocode($datainsert);
                    if ($success) {
                        $this->session->set_flashdata('success', 'Promotion code Has Been Changed');
                        redirect('promotion/promocodedata');
                    } else {
                        $this->session->set_flashdata('danger', 'Error, Please Try Again!');
                        redirect('promotion/promocodedata');
                    }
                }
            }
        } else {
            $this->session->set_flashdata('danger', 'Error, Please Try Again!');
            redirect('promotion/promocodedata');
        }
    }
}
