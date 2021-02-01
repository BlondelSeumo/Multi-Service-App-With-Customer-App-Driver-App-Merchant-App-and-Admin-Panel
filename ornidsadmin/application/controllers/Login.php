<?php

defined('BASEPATH') or exit('No direct script access allowed');

class Login extends CI_Controller
{

	function __construct()
	{

		parent::__construct();

		$this->load->model('login_model', 'login');
	}

	function index()
	{
		if ($this->session->userdata('user_name') != NULL && $this->session->userdata('password') != NULL) {
			redirect(base_url("dashboard"));
		}
		$this->load->view('login/index');
	}

	function login_action()
	{

		$nama = html_escape($this->input->post('user_name', TRUE));
		$acak = html_escape($this->input->post('password', TRUE));
		$pass = sha1($acak);

		$user = $this->db->get_where('admin', ['user_name' => $nama, 'password' => $pass])->row_array();


		if ($user) {
			$data = [
				'id' => $user['id'],
				'user_name' => $user['user_name'],
				'password' => $user['password'],
				'image' => $user['image']
			];
			$this->session->set_userdata($data);
			header('Location: ' . base_url());
		} else {
			$this->session->set_flashdata('danger', 'Account not registered');
			redirect('login');
		}
	}

	function logout()
	{
		$this->session->sess_destroy();
		redirect('login');
	}
}
