<?php
//'tes' => number_format(200 / 100, 2, ",", "."),
defined('BASEPATH') or exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';
class Driver extends REST_Controller
{

    public function __construct()
    {
        parent::__construct();

        $this->load->helper("url");
        $this->load->database();
        $this->load->model('Driver_model');
        $this->load->model('Customer_model');
        date_default_timezone_set(time_zone);
    }

    function index_get()
    {
        $this->response("Api for ouride!", 200);
    }

    function privacy_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $app_settings = $this->Customer_model->get_settings();

        $message = array(
            'code' => '200',
            'message' => 'found',
            'data' => $app_settings
        );
        $this->response($message, 200);
    }

    function job_post()
    {

        $job = $this->Driver_model->get_job();

        $message = array(
            'code' => '200',
            'message' => 'found',
            'data' => $job
        );
        $this->response($message, 200);
    }

    function login_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $decoded_data = json_decode($data);
        $reg_id = array(
            'reg_id' => $decoded_data->token
        );

        $condition = array(
            'password' => sha1($decoded_data->password),
            'phone_number' => $decoded_data->phone_number,
            //'token' => $decoded_data->token
        );
        $check_banned = $this->Driver_model->check_banned($decoded_data->phone_number);
        if ($check_banned) {
            $message = array(
                'message' => 'banned',
                'data' => []
            );
            $this->response($message, 200);
        } else {
            $cek_login = $this->Driver_model->get_data_pelanggan($condition);
            $message = array();

            if ($cek_login->num_rows() > 0) {
                $upd_regid = $this->Driver_model->edit_profile($reg_id, $decoded_data->phone_number);
                $get_pelanggan = $this->Driver_model->get_data_pelanggan($condition);
                $this->Driver_model->edit_status_login($decoded_data->phone_number);
                $message = array(
                    'code' => '200',
                    'message' => 'found',
                    'data' => $get_pelanggan->result()
                );
                $this->response($message, 200);
            } else {
                $message = array(
                    'code' => '404',
                    'message' => 'wrong phone or password',
                    'data' => []
                );
                $this->response($message, 200);
            }
        }
    }

    function update_location_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        $data = file_get_contents("php://input");
        $decoded_data = json_decode($data);
        $data = array(
            'latitude' => $decoded_data->latitude,
            'longitude' => $decoded_data->longitude,
            'bearing' => $decoded_data->bearing,
            'driver_id' => $decoded_data->driver_id
        );
        $ins = $this->Driver_model->my_location($data);

        if ($ins) {
            $message = array(
                'message' => 'location updated',
                'data' => []
            );
            $this->response($message, 200);
        }
    }

    function home_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);
        $balance = $this->Customer_model->saldouser($dec_data->id);
        $app_settings = $this->Customer_model->get_settings();
        $condition = array(
            'phone_number' => $dec_data->phone_number
        );
        $cek_login = $this->Driver_model->get_data_driver($condition);

        foreach ($app_settings as $item) {
            if ($cek_login->num_rows() > 0) {
                $message = array(
                    'code' => '200',
                    'message' => 'success',
                    'balance' => $balance->row('balance'),
                    'currency' => $item['app_currency'],
                    'currency_text' => $item['app_currency_text'],
                    'app_aboutus' => $item['app_aboutus'],
                    'app_contact' => $item['app_contact'],
                    'app_website' => $item['app_website'],
                    'stripe_active' => $item['stripe_active'],
                    'stripe_publish' => $item['stripe_published_key'],
                    'paypal_key' => $item['paypal_key'],
                    'paypal_mode' => $item['paypal_mode'],
                    'paypal_active' => $item['paypal_active'],
                    'app_email' => $item['app_email']


                );
                $this->response($message, 200);
            } else {
                $message = array(
                    'code' => '201',
                    'message' => 'failed',
                    'data' => []
                );
                $this->response($message, 201);
            }
        }
    }

    function logout_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $decoded_data = json_decode($data);
        $dataEdit = array(
            'status' => 5
        );

        $logout = $this->Driver_model->logout($dataEdit, $decoded_data->id);
        if ($logout) {
            $message = array(
                'message' => 'success',
                'data' => ''
            );
            $this->response($message, 200);
        } else {
            $message = array(
                'message' => 'fail',
                'data' => ''
            );
            $this->response($message, 200);
        }
    }

    public function intentstripe_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);

        $condition = array(
            'id' => $dec_data->userid,
            'phone_number' => $dec_data->phone_number,
            'status' => '1'
        );
        $cek_login = $this->Driver_model->get_data_pelanggan($condition);

        if ($cek_login->num_rows() > 0) {
            require_once APPPATH . "third_party/stripe/init.php";
            $app_settings = $this->Customer_model->get_settings();
            foreach ($app_settings as $item) {
                $stripe = array(
                    "secret_key" => $item['stripe_secret_key']
                );
            }
            \Stripe\Stripe::setApiKey($stripe['secret_key']);
            $intent =  \Stripe\Paymentintent::create([
                'amount' => $dec_data->price,
                'currency' => "usd",
                'payment_method_types' => ['card'],
                'metadata' => [
                    'user_id' => $dec_data->userid
                ],
            ]);

            $message = array(
                'message' => 'success',
                'data' => $intent->client_secret
            );
            $this->response($message, 200);
        } else {
            $message = array(
                'message' => 'error',
                'data' => ''
            );
            $this->response($message, 200);
        }
    }

    public function stripeaction_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);

        $condition = array(
            'phone_number' => $dec_data->phone_number,
            'status' => '1'
        );
        $cek_login = $this->Driver_model->get_data_pelanggan($condition);

        if ($cek_login->num_rows() > 0) {
            require_once APPPATH . "third_party/stripe/init.php";
            $app_settings = $this->Customer_model->get_settings();
            foreach ($app_settings as $item) {
                $stripe = array(
                    "secret_key" => $item['stripe_secret_key']
                );
            }
            \Stripe\Stripe::setApiKey($stripe['secret_key']);
            $intent = \Stripe\PaymentIntent::retrieve($dec_data->id);
            $charges = $intent->charges->data;



            if ($charges[0]->metadata->user_id == $dec_data->userid && $charges[0]->amount_refunded == 0 && empty($charges[0]->failure_code) && $charges[0]->paid == 1) {
                //order details 
                $amount = $charges[0]->amount;
                $status = $charges[0]->status;

                $datatopup = array(
                    'id_user' => $charges[0]->metadata->user_id,
                    'wallet_account' => $dec_data->id,
                    'bank' => 'stripe',
                    'holder_name' => $dec_data->name,
                    'type' => 'topup',
                    'wallet_amount' => $amount,
                    'status' => 1
                );

                if ($status == 'succeeded') {
                    $this->Customer_model->insertwallet($datatopup);
                    $saldolama = $this->Customer_model->saldouser($charges[0]->metadata->user_id);
                    $saldobaru = $saldolama->row('balance') + $amount;
                    $balance = array('balance' => $saldobaru);
                    $this->Customer_model->addsaldo($charges[0]->metadata->user_id, $balance);

                    $message = array(
                        'code' => '200',
                        'message' => 'success',
                        'data' => ''
                    );
                    $this->response($message, 200);
                } else {
                    $message = array(
                        'code' => '201',
                        'message' => 'error',
                        'data' => ''
                    );
                    $this->response($message, 200);
                }
            } else {
                $message = array(
                    'code' => '202',
                    'message' => 'error',
                    'data' => []
                );
                $this->response($message, 200);
            }
        } else {
            $message = array(
                'message' => 'error',
                'data' => ''
            );
            $this->response($message, 200);
        }
    }

    function syncronizing_account_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);
        $balance = $this->Customer_model->saldouser($dec_data->id);
        $app_settings = $this->Customer_model->get_settings();
        $getDataDriver = $this->Driver_model->get_data_driver_sync($dec_data->id);
        $condition = array(
            'phone_number' => $dec_data->phone_number
        );
        $cek_login = $this->Driver_model->get_data_pelanggan($condition);
        foreach ($app_settings as $item) {
            if ($cek_login->num_rows() > 0) {
                $payu = $this->Customer_model->payusettings()->result();
                if ($getDataDriver['status_order']->num_rows() > 0) {
                    $stat = 0;
                    if ($getDataDriver['status_order']->row('status') == 3) {
                        $stat = 3;
                    } else if ($getDataDriver['status_order']->row('status') == 2) {
                        $stat = 2;
                    } else {
                        $stat = 1;
                    }

                    $this->Driver_model->change_status_driver($dec_data->id, $stat);
                    $message = array(
                        'message' => 'success',
                        'driver_status' => $stat,
                        'data_driver' => $getDataDriver['data_driver']->result(),
                        'data_transaksi' => $getDataDriver['status_order']->result(),
                        'balance' => $balance->row('balance'),
                        'currency' => $item['app_currency'],
                        'currency_text' => $item['app_currency_text'],
                        'app_aboutus' => $item['app_aboutus'],
                        'app_contact' => $item['app_contact'],
                        'app_website' => $item['app_website'],
                        'stripe_active' => $item['stripe_active'],
                        'stripe_publish' => $item['stripe_published_key'],
                        'paypal_key' => $item['paypal_key'],
                        'paypal_mode' => $item['paypal_mode'],
                        'paypal_active' => $item['paypal_active'],
                        'app_email' => $item['app_email'],
                        'payu' => $payu
                    );
                    $this->response($message, 200);
                } else {
                    $this->Driver_model->change_status_driver($dec_data->id, 1);
                    $getDataDriver2 = $this->Driver_model->get_data_driver_sync($dec_data->id);
                    $message = array(
                        'message' => 'success',
                        'driver_status' => $getDataDriver2['data_driver']->row('status_config'),
                        'data_driver' => $getDataDriver2['data_driver']->result(),
                        'data_transaksi' => [],
                        'balance' => $balance->row('balance'),
                        'currency' => $item['app_currency'],
                        'currency_text' => $item['app_currency_text'],
                        'app_aboutus' => $item['app_aboutus'],
                        'app_contact' => $item['app_contact'],
                        'app_website' => $item['app_website'],
                        'stripe_active' => $item['stripe_active'],
                        'stripe_publish' => $item['stripe_published_key'],
                        'paypal_key' => $item['paypal_key'],
                        'paypal_mode' => $item['paypal_mode'],
                        'paypal_active' => $item['paypal_active'],
                        'app_email' => $item['app_email'],
                        'payu' => $payu
                    );
                    $this->response($message, 200);
                }
            } else {
                $message = array(
                    'code' => '201',
                    'message' => 'failed',
                    'data' => []
                );
                $this->response($message, 201);
            }
        }
    }

    function turning_on_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);

        $is_turn = $dec_data->is_turn;
        $dataEdit = array();
        if ($is_turn) {
            $dataEdit = array(
                'status' => 1
            );
            $upd_regid = $this->Driver_model->edit_config($dataEdit, $dec_data->id);
            if ($upd_regid) {
                $message = array(
                    'message' => 'success',
                    'data' => '1'
                );
                $this->response($message, 200);
            } else {
                $message = array(
                    'message' => 'fail',
                    'data' => []
                );
                $this->response($message, 200);
            }
        } else {
            $dataEdit = array(
                'status' => 4
            );
            $upd_regid = $this->Driver_model->edit_config($dataEdit, $dec_data->id);
            if ($upd_regid) {
                $message = array(
                    'message' => 'success',
                    'data' => '4'
                );
                $this->response($message, 200);
            } else {
                $message = array(
                    'message' => 'fail',
                    'data' => []
                );
                $this->response($message, 200);
            }
        }
    }

    function accept_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);

        $data_req = array(
            'driver_id' => $dec_data->id,
            'transaction_id' => $dec_data->transaction_id
        );

        $condition = array(
            'driver_id' => $dec_data->id,
            'status' => '1'
        );

        $cek_login = $this->Driver_model->get_status_driver($condition);
        if ($cek_login->num_rows() > 0) {

            $acc_req = $this->Driver_model->accept_request($data_req);
            if ($acc_req['status']) {
                $message = array(
                    'message' => 'berhasil',
                    'data' => 'berhasil'
                );
                $this->response($message, 200);
            } else {
                if ($acc_req['data'] == 'canceled') {
                    $message = array(
                        'message' => 'canceled',
                        'data' => 'canceled'
                    );
                    $this->response($message, 200);
                } else {
                    $message = array(
                        'message' => 'unknown fail',
                        'data' => 'canceled'
                    );
                    $this->response($message, 200);
                }
            }
        } else {
            $message = array(
                'message' => 'unknown fail',
                'data' => 'canceled'
            );
            $this->response($message, 200);
        }
    }

    function start_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);

        $data_req = array(
            'driver_id' => $dec_data->id,
            'transaction_id' => $dec_data->transaction_id
        );

        $acc_req = $this->Driver_model->start_request($data_req);
        if ($acc_req['status']) {
            $message = array(
                'message' => 'berhasil',
                'data' => 'success'
            );
            $this->response($message, 200);
        } else {
            if ($acc_req['data'] == 'canceled') {
                $message = array(
                    'message' => 'canceled',
                    'data' => 'canceled'
                );
                $this->response($message, 200);
            } else {
                $message = array(
                    'message' => 'unknown fail',
                    'data' => 'unknown fail'
                );
                $this->response($message, 200);
            }
        }
    }

    function finish_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);

        $data_req = array(
            'driver_id' => $dec_data->id,
            'transaction_id' => $dec_data->transaction_id
        );

        $data_tr = array(
            'driver_id' => $dec_data->id,
            'id' => $dec_data->transaction_id
        );

        $finish_transaksi = $this->Driver_model->finish_request($data_req, $data_tr);
        if ($finish_transaksi['status']) {
            $message = array(
                'message' => 'berhasil',
                'data' => 'finish',
            );
            $this->response($message, 200);
        } else {
            $message = array(
                'message' => 'fail',
                'data' => $finish_transaksi['data']
            );
            $this->response($message, 200);
        }
    }

    function detail_transaksi_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);
        $gettrans = $this->Customer_model->transaction($dec_data->id);
        $getdriver = $this->Driver_model->get_data_pelangganid($dec_data->customer_id);
        $getitem = $this->Customer_model->detail_item($dec_data->id);

        $message = array(
            'status' => true,
            'data' => $gettrans->result(),
            'customer' => $getdriver->result(),
            'item' => $getitem->result(),
        );
        $this->response($message, 200);
    }

    function verifycode_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);
        $condition = array(
            'phone_number' => $dec_data->phone_number
        );
        $dataverify = array(
            'validation_code' => $dec_data->verifycode,
            'transaction_id' => $dec_data->transaction_id
        );
        $dataver = $this->Driver_model->get_verify($dataverify);
        $cek_login = $this->Driver_model->get_data_pelanggan($condition);
        if ($cek_login->num_rows() > 0 && $dataver->num_rows() > 0) {

            $message = array(
                'message' => 'success',
                'data' => '',
            );
            $this->response($message, 200);
        } else {
            $message = array(
                'message' => 'fail',
                'data' => ''
            );
            $this->response($message, 200);
        }
    }

    function edit_profile_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $decoded_data = json_decode($data);
        $check_exist_phone = $this->Driver_model->check_exist_phone_edit($decoded_data->id, $decoded_data->phone_number);
        $check_exist_email = $this->Driver_model->check_exist_email_edit($decoded_data->id, $decoded_data->email);
        if ($check_exist_phone) {
            $message = array(
                'code' => '201',
                'message' => 'phone already exist',
                'data' => []
            );
            $this->response($message, 201);
        } else if ($check_exist_email) {
            $message = array(
                'code' => '201',
                'message' => 'email already exist',
                'data' => []
            );
            $this->response($message, 201);
        } else {
            $condition = array(
                'phone_number' => $decoded_data->phone_number
            );
            $condition2 = array(
                'phone_number' => $decoded_data->no_telepon_lama
            );

            if ($decoded_data->fotodriver == null && $decoded_data->fotodriver_lama == null) {
                $datauser = array(
                    'driver_name' => $decoded_data->customer_fullname,
                    'phone_number' => $decoded_data->phone_number,
                    'phone' => $decoded_data->phone,
                    'email' => $decoded_data->email,
                    'countrycode' => $decoded_data->countrycode,
                    'dob' => $decoded_data->dob
                );
            } else {
                $image = $decoded_data->fotodriver;
                $namafoto = time() . '-' . rand(0, 99999) . ".jpg";
                $path = "images/driverphoto/" . $namafoto;
                file_put_contents($path, base64_decode($image));

                $photo = $decoded_data->fotodriver_lama;
                $path = "./images/driverphoto/$photo";
                unlink("$path");


                $datauser = array(
                    'driver_name' => $decoded_data->customer_fullname,
                    'phone_number' => $decoded_data->phone_number,
                    'phone' => $decoded_data->phone,
                    'email' => $decoded_data->email,
                    'countrycode' => $decoded_data->countrycode,
                    'photo' => $namafoto,
                    'dob' => $decoded_data->dob
                );
            }


            $cek_login = $this->Driver_model->get_data_pelanggan($condition2);
            if ($cek_login->num_rows() > 0) {
                $upd_user = $this->Driver_model->edit_profile($datauser, $decoded_data->no_telepon_lama);
                $getdata = $this->Driver_model->get_data_pelanggan($condition);
                $message = array(
                    'code' => '200',
                    'message' => 'success',
                    'data' => $getdata->result()
                );
                $this->response($message, 200);
            } else {
                $message = array(
                    'code' => '404',
                    'message' => 'error data',
                    'data' => []
                );
                $this->response($message, 200);
            }
        }
    }

    function edit_kendaraan_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $decoded_data = json_decode($data);

        $condition = array(
            'id' => $decoded_data->id,
            'phone_number' => $decoded_data->phone_number
        );

        $datakendaraan = array(
            'brand' => $decoded_data->brand,
            'type' => $decoded_data->type,
            'vehicle_registration_number' => $decoded_data->no_kendaraan,
            'color' => $decoded_data->color
        );



        $cek_login = $this->Driver_model->get_data_pelanggan($condition);
        if ($cek_login->num_rows() > 0) {
            $upd_user = $this->Driver_model->edit_kendaraan($datakendaraan, $decoded_data->id_kendaraan);
            $getdata = $this->Driver_model->get_data_pelanggan($condition);
            $message = array(
                'code' => '200',
                'message' => 'success',
                'data' => $getdata->result()
            );
            $this->response($message, 200);
        } else {
            $message = array(
                'code' => '404',
                'message' => 'error data',
                'data' => []
            );
            $this->response($message, 200);
        }
    }

    function changepass_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $decoded_data = json_decode($data);
        $reg_id = array(
            'password' => sha1($decoded_data->new_password)
        );

        $condition = array(
            'password' => sha1($decoded_data->password),
            'phone_number' => $decoded_data->phone_number
        );
        $condition2 = array(
            'password' => sha1($decoded_data->new_password),
            'phone_number' => $decoded_data->phone_number
        );
        $cek_login = $this->Driver_model->get_data_pelanggan($condition);
        $message = array();

        if ($cek_login->num_rows() > 0) {
            $upd_regid = $this->Driver_model->edit_profile($reg_id, $decoded_data->phone_number);
            $get_pelanggan = $this->Driver_model->get_data_pelanggan($condition2);

            $message = array(
                'code' => '200',
                'message' => 'found',
                'data' => $get_pelanggan->result()
            );
            $this->response($message, 200);
        } else {
            $message = array(
                'code' => '404',
                'message' => 'wrong password',
                'data' => []
            );
            $this->response($message, 200);
        }
    }

    function history_progress_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        $data = file_get_contents("php://input");
        $decoded_data = json_decode($data);
        $getWallet = $this->Driver_model->all_transaksi($decoded_data->id);
        $message = array(
            'status' => true,
            'data' => $getWallet->result()
        );
        $this->response($message, 200);
    }

    function forgot_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $decoded_data = json_decode($data);

        $condition = array(
            'email' => $decoded_data->email,
            'status' => '1'
        );
        $cek_login = $this->Driver_model->get_data_pelanggan($condition);
        $app_settings = $this->Customer_model->get_settings();
        $token = sha1(rand(0, 999999) . time());


        if ($cek_login->num_rows() > 0) {
            $cheker = array('msg' => $cek_login->result());
            foreach ($app_settings as $item) {
                foreach ($cheker['msg'] as $item2 => $val) {
                    $dataforgot = array(
                        'userid' => $val->id,
                        'token' => $token,
                        'idKey' => '2'
                    );
                }


                $forgot = $this->Customer_model->dataforgot($dataforgot);

                $linkbtn = base_url() . 'resetpass/rest/' . $token . '/2';
                $template = $this->Customer_model->template1($item['email_subject'], $item['email_text1'], $item['email_text2'], $item['app_website'], $item['app_name'], $linkbtn, $item['app_linkgoogle'], $item['app_address']);
                $sendmail = $this->Customer_model->emailsend($item['email_subject'] . " [ticket-" . rand(0, 999999) . "]", $decoded_data->email, $template, $item['smtp_host'], $item['smtp_port'], $item['smtp_username'], $item['smtp_password'], $item['smtp_from'], $item['app_name'], $item['smtp_secure']);
            }
            if ($forgot && $sendmail) {
                $message = array(
                    'code' => '200',
                    'message' => 'found',
                    'data' => []
                );
                $this->response($message, 200);
            } else {
                $message = array(
                    'code' => '401',
                    'message' => 'email not registered',
                    'data' => []
                );
                $this->response($message, 200);
            }
        } else {
            $message = array(
                'code' => '404',
                'message' => 'email not registered',
                'data' => []
            );
            $this->response($message, 200);
        }
    }

    function register_driver_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);

        $email = $dec_data->email;
        $phone = $dec_data->phone_number;
        $check_exist = $this->Driver_model->check_exist($email, $phone);
        $check_exist_phone = $this->Driver_model->check_exist_phone($phone);
        $check_exist_email = $this->Driver_model->check_exist_email($email);
        $check_exist_sim = $this->Driver_model->check_sim($dec_data->driver_license_id);
        $check_exist_ktp = $this->Driver_model->check_ktp($dec_data->user_nationid);
        if ($check_exist) {
            $message = array(
                'code' => '201',
                'message' => 'email and phone number already exist',
                'data' => ''
            );
            $this->response($message, 201);
        } else if ($check_exist_phone) {
            $message = array(
                'code' => '201',
                'message' => 'phone already exist',
                'data' => ''
            );
            $this->response($message, 201);
        } else if ($check_exist_sim) {
            $message = array(
                'code' => '201',
                'message' => 'Driver license already exist',
                'data' => ''
            );
            $this->response($message, 201);
        } else if ($check_exist_ktp) {
            $message = array(
                'code' => '201',
                'message' => 'ID Card already exist',
                'data' => ''
            );
            $this->response($message, 201);
        } else if ($check_exist_email) {
            $message = array(
                'code' => '201',
                'message' => 'email already exist',
                'data' => ''
            );
            $this->response($message, 201);
        } else {
            if ($dec_data->checked == "true") {
                $message = array(
                    'code' => '200',
                    'message' => 'next',
                    'data' => ''
                );
                $this->response($message, 200);
            } else {
                $image = $dec_data->photo;
                $namafoto = time() . '-' . rand(0, 99999) . ".jpg";
                $path = "images/driverphoto/" . $namafoto;
                file_put_contents($path, base64_decode($image));
                $data_signup = array(
                    'id' => 'D' . time(),
                    'driver_name' => $dec_data->driver_name,
                    'user_nationid' => $dec_data->user_nationid,
                    'dob' => $dec_data->dob,
                    'phone_number' => $dec_data->phone_number,
                    'phone' => $dec_data->phone,
                    'email' => $dec_data->email,
                    'photo' => $namafoto,
                    'password' => sha1(time()),
                    'job' => $dec_data->job,
                    'countrycode' => $dec_data->countrycode,
                    'gender' => $dec_data->gender,
                    'driver_address' => $dec_data->driver_address,
                    'reg_id' => 12345,
                    'status' => 0
                );

                $data_kendaraan = array(
                    'brand' => $dec_data->brand,
                    'type' => $dec_data->type,
                    'vehicle_registration_number' => $dec_data->vehicle_registration_number,
                    'color' => $dec_data->color
                );

                $imagektp = $dec_data->idcard_images;
                $namafotoktp = time() . '-' . rand(0, 99999) . ".jpg";
                $pathktp = "images/photofile/ktp/" . $namafotoktp;
                file_put_contents($pathktp, base64_decode($imagektp));

                $imagesim = $dec_data->driver_license_images;
                $namafotosim = time() . '-' . rand(0, 99999) . ".jpg";
                $pathsim = "images/photofile/sim/" . $namafotosim;
                file_put_contents($pathsim, base64_decode($imagesim));

                $data_berkas = array(
                    'idcard_images' => $namafotoktp,
                    'driver_license_images' => $namafotosim,
                    'driver_license_id' => $dec_data->driver_license_id
                );


                $signup = $this->Driver_model->signup($data_signup, $data_kendaraan, $data_berkas);
                if ($signup) {
                    $message = array(
                        'code' => '200',
                        'message' => 'success',
                        'data' => 'register has been succesed!'
                    );
                    $this->response($message, 200);
                } else {
                    $message = array(
                        'code' => '201',
                        'message' => 'failed',
                        'data' => ''
                    );
                    $this->response($message, 201);
                }
            }
        }
    }

    public function withdraw_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);

        $iduser = $dec_data->id;
        $bank = $dec_data->bank;
        $nama = $dec_data->nama;
        $amount = $dec_data->amount;
        $card = $dec_data->card;
        $email = $dec_data->email;
        $phone = $dec_data->phone_number;

        $saldolama = $this->Customer_model->saldouser($iduser);
        $datawithdraw = array(
            'id_user' => $iduser,
            'wallet_account' => $card,
            'bank' => $bank,
            'holder_name' => $nama,
            'type' => $dec_data->type,
            'wallet_amount' => $amount,
            'status' => 0
        );
        $check_exist = $this->Driver_model->check_exist($email, $phone);

        if ($dec_data->type ==  "topup") {
            $withdrawdata = $this->Customer_model->insertwallet($datawithdraw);

            $message = array(
                'code' => '200',
                'message' => 'success',
                'data' => ''
            );
            $this->response($message, 200);
        } else {

            if ($saldolama->row('balance') >= $amount && $check_exist) {
                $withdrawdata = $this->Customer_model->insertwallet($datawithdraw);

                $message = array(
                    'code' => '200',
                    'message' => 'success',
                    'data' => ''
                );
                $this->response($message, 200);
            } else {
                $message = array(
                    'code' => '201',
                    'message' => 'You have insufficient balance',
                    'data' => ''
                );
                $this->response($message, 200);
            }
        }
    }

    public function topuppaypal_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);

        $iduser = $dec_data->id;
        $bank = $dec_data->bank;
        $nama = $dec_data->nama;
        $amount = $dec_data->amount;
        $card = $dec_data->card;
        $email = $dec_data->email;
        $phone = $dec_data->phone_number;

        $datatopup = array(
            'id_user' => $iduser,
            'wallet_account' => $card,
            'bank' => $bank,
            'holder_name' => $nama,
            'type' => 'topup',
            'wallet_amount' => $amount,
            'status' => 1
        );
        $check_exist = $this->Driver_model->check_exist($email, $phone);

        if ($check_exist) {
            $this->Customer_model->insertwallet($datatopup);
            $saldolama = $this->Customer_model->saldouser($iduser);
            $saldobaru = $saldolama->row('balance') + $amount;
            $balance = array('balance' => $saldobaru);
            $this->Customer_model->addsaldo($iduser, $balance);

            $message = array(
                'code' => '200',
                'message' => 'success',
                'data' => ''
            );
            $this->response($message, 200);
        } else {
            $message = array(
                'code' => '201',
                'message' => 'You have insufficient balance',
                'data' => ''
            );
            $this->response($message, 200);
        }
    }
}
