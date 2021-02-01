<?php
defined('BASEPATH') or exit('No direct script access allowed');

class News extends CI_Controller
{
    public function __construct()
    {
        parent::__construct();
        if ($this->session->userdata('user_name') == NULL && $this->session->userdata('password') == NULL) {
            redirect(base_url() . "login");
        }
        $this->load->model('News_model', 'news');
        $this->load->library('form_validation');
        $this->load->model('Appsettings_model', 'appset');
    }

    public function newscategory()
    {
        $getview['view'] = 'newscategory';
        $getview['menu'] = $this->appset->getMenuAdmin();
        $data['newscategory'] = $this->news->getAllnewscategory();

        $this->load->view('includes/header', $getview);
        $this->load->view('news/newscategory', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function newsdata()
    {
        $getview['view'] = 'newsdata';
        $getview['menu'] = $this->appset->getMenuAdmin();
        $data['news'] = $this->news->getAllnews();

        $this->load->view('includes/header', $getview);
        $this->load->view('news/newsdata', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function addnews()
    {
        $getview['view'] = 'appsettings';
        $getview['menu'] = $this->appset->getMenuAdmin();
        $data['news'] = $this->news->getAllnewscategory();
        $this->load->view('includes/header', $getview);
        $this->load->view('news/addnews', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function editnews($id)
    {
        $getview['view'] = 'appsettings';
        $getview['menu'] = $this->appset->getMenuAdmin();
        $data['news'] = $this->news->getnewsById($id);
        $data['knews'] = $this->news->getAllnewscategory();
        $this->load->view('includes/header', $getview);
        $this->load->view('news/editnews', $data);
        $this->load->view('includes/footer', $getview);
    }

    public function addcategory()
    {

        $this->form_validation->set_rules('category', 'category', 'trim|prep_for_form');

        if ($this->form_validation->run() == TRUE) {


            if ($this->input->post('view') == 'edit') {
                $data1             = [
                    'news_category_id'                => html_escape($this->input->post('id', TRUE)),
                    'category'                => html_escape($this->input->post('category', TRUE))
                ];
            } else {
                $data             = [
                    'category'                => html_escape($this->input->post('category', TRUE))
                ];
            }

            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('news/newscategory');
            } else {
                if ($this->input->post('view') == 'edit') {
                    $success = $this->news->editdatakategori($data1, $data1['news_category_id']);
                    if ($success) {
                        $this->session->set_flashdata('success', 'Category news Has Been changed');
                        redirect('news/newscategory');
                    } else {
                        $this->session->set_flashdata('danger', 'Error, please try again!');
                        redirect('news/newscategory');
                    }
                } else {
                    $success = $this->news->adddatakategori($data);
                    if ($success) {
                        $this->session->set_flashdata('success', 'Category news Has Been added');
                        redirect('news/newscategory');
                    } else {
                        $this->session->set_flashdata('danger', 'Error, please try again!');
                        redirect('news/newscategory');
                    }
                }
            }
        } else {
            $this->session->set_flashdata('danger', 'Error, please try again!');
            redirect('news/newscategory');
        }
    }

    public function add()
    {

        $this->form_validation->set_rules('title', 'title', 'trim|prep_for_form');
        $this->form_validation->set_rules('category_id', 'category_id', 'trim|prep_for_form');


        if ($this->form_validation->run() == TRUE) {
            $config['upload_path']     = './images/news/';
            $config['allowed_types'] = 'gif|jpg|png|jpeg';
            $config['max_size']         = '10000';
            $config['file_name']     = 'name';
            $config['encrypt_name']     = true;
            $this->load->library('upload', $config);

            if ($this->upload->do_upload('news_images')) {
                $gambar = html_escape($this->upload->data('file_name'));
            } else {
                $gambar = 'noimage.jpg';
            }

            $data             = [
                'news_images'                => $gambar,
                'title'                      => html_escape($this->input->post('title', TRUE)),
                'content'                    => $this->input->post('content', TRUE),
                'category_id'                => html_escape($this->input->post('category_id', TRUE)),
                'news_status'              => html_escape($this->input->post('news_status', TRUE))
            ];

            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('news/addnews');
            } else {

                $success = $this->news->addnews($data);
                if ($success) {
                    $this->session->set_flashdata('success', 'News Has Been Added');
                    redirect('news/newsdata');
                } else {
                    $this->session->set_flashdata('danger', 'Error, Please try again!');
                    redirect('news/newsdata');
                }
            }
        } else {
            $this->session->set_flashdata('danger', 'Error, Please try again!');
            redirect('news/newsdata');
        }
    }

    public function edit()
    {

        $this->form_validation->set_rules('title', 'title', 'trim|prep_for_form');
        $this->form_validation->set_rules('category_id', 'category_id', 'trim|prep_for_form');

        $id  = html_escape($this->input->post('news_id', TRUE));

        $data['news'] = $this->news->getnewsById($id);


        if ($this->form_validation->run() == TRUE) {
            $config['upload_path']     = './images/news/';
            $config['allowed_types'] = 'gif|jpg|png|jpeg';
            $config['max_size']         = '10000';
            $config['file_name']     = 'name';
            $config['encrypt_name']     = true;
            $this->load->library('upload', $config);

            if ($this->upload->do_upload('news_images')) {
                if ($data['news']['news_images'] != 'noimage.jpg') {
                    $gambar = $data['news']['news_images'];
                    unlink('images/news/' . $gambar);
                }

                $gambar = html_escape($this->upload->data('file_name'));
            } else {
                $gambar = $data['news']['news_images'];
            }
            $data             = [
                'news_id'                     => html_escape($this->input->post('news_id', TRUE)),
                'news_images'                   => $gambar,
                'title'                         => html_escape($this->input->post('title', TRUE)),
                'content'                       => $this->input->post('content'),
                'category_id'                   => html_escape($this->input->post('category_id', TRUE)),
                'news_status'                 => html_escape($this->input->post('news_status', TRUE))
            ];

            if (demo == TRUE) {
                $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
                redirect('news/editnews/' . $id);
            } else {

                $success = $this->news->editdataberita($data);
                if ($success) {
                    $this->session->set_flashdata('success', 'News Has Been changed');
                    redirect('news/newsdata');
                } else {
                    $this->session->set_flashdata('danger', 'Error, Please try again!');
                    redirect('news/editnews/' . $id);
                }
            }
        } else {
            $this->session->set_flashdata('danger', 'Error, Please try again!');
            redirect('news/editnews/' . $id);
        }
    }

    public function delete($id)
    {
        if (demo == TRUE) {
            $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
            redirect('news/newsdata');
        } else {
            $data = $this->news->getnewsById($id);
            if ($data['news_images'] != 'noimage.jpg') {
                $gambar = $data['news_images'];
                unlink('images/news/' . $gambar);
            }
            $success = $this->news->deleteberitaById($id);
            if ($success) {
                $this->session->set_flashdata('success', 'News Has Been deleted');
                redirect('news/newsdata');
            } else {
                $this->session->set_flashdata('danger', 'Error, Please try again!');
                redirect('news/newsdata');
            }
        }
    }

    public function deletecategory($id)
    {
        if (demo == TRUE) {
            $this->session->set_flashdata('demo', 'NOT ALLOWED FOR DEMO');
            redirect('news/index');
        } else {
            $success =  $this->news->deletekategoribyid($id);
            if ($success) {
                $this->session->set_flashdata('delete', 'Category News Has Been Deleted');
                redirect('news/newscategory');
            } else {
                $this->session->set_flashdata('danger', 'Error, Please try again!');
                redirect('news/newscategory');
            }
        }
    }
}
