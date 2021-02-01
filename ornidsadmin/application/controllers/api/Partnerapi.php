<?php
//'tes' => number_format(200 / 100, 2, ",", "."),
defined('BASEPATH') or exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';
class Partnerapi extends REST_Controller
{

    public function __construct()
    {
        parent::__construct();
        $this->load->helper("url");
        $this->load->database();
        $this->load->model('Merchantapi_model');
        $this->load->model('Customer_model');
        $this->load->model('Driver_model');
        date_default_timezone_set(time_zone);
    }

    function index_get()
    {
        $this->response("Api for merchant ouride!", 200);
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
            'merchant_token' => $decoded_data->token
        );

        $condition = array(
            'password' => sha1($decoded_data->password),
            'partner_telephone' => $decoded_data->phone_number,
            //'token' => $decoded_data->token
        );
        $check_banned = $this->Merchantapi_model->check_banned($decoded_data->phone_number);
        if ($check_banned) {
            $message = array(
                'message' => 'banned',
                'data' => []
            );
            $this->response($message, 200);
        } else {
            $cek_login = $this->Merchantapi_model->get_data_merchant($condition);
            $message = array();
            if ($cek_login->num_rows() > 0) {
                $this->Merchantapi_model->edit_profile_token($reg_id, $decoded_data->phone_number);
                $get_pelanggan = $this->Merchantapi_model->get_data_merchant($condition);
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

    function register_merchant_post()
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
        $check_exist = $this->Merchantapi_model->check_exist($email, $phone);
        $check_exist_phone = $this->Merchantapi_model->check_exist_phone($phone);
        $check_exist_email = $this->Merchantapi_model->check_exist_email($email);
        $check_exist_ktp = $this->Merchantapi_model->check_ktp($dec_data->user_nationid);
        if ($check_exist) {
            $message = array(
                'code' => '201',
                'message' => 'email and phone number already exist',
                'data' => ''
            );
            $this->response($message, 201);
        } else
         if ($check_exist_phone) {
            $message = array(
                'code' => '201',
                'message' => 'phone already exist',
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

                $data_signup = array(
                    'partner_id' => 'M' . time(),
                    'partner_name' => $dec_data->partner_name,
                    'partner_type_identity' => $dec_data->jenis_identitas,
                    'partner_identity_number' => $dec_data->user_nationid,
                    'partner_address' => $dec_data->partner_address,
                    'partner_email' => $dec_data->email,
                    'password' => sha1(time()),
                    'partner_telephone' => $dec_data->phone_number,
                    'partner_phone' => $dec_data->phone,
                    'partner_country_code' => $dec_data->countrycode,
                    'partner' => '0',
                    'partner_status' => '0'
                );

                $image = $dec_data->photo;
                $namafoto = time() . '-' . rand(0, 99999) . ".jpg";
                $path = "images/merchant/" . $namafoto;
                file_put_contents($path, base64_decode($image));

                $data_merchant = array(
                    'service_id' => $dec_data->service_id,
                    'merchant_name' => $dec_data->merchant_name,
                    'merchant_address' => $dec_data->merchant_address,
                    'merchant_latitude' => $dec_data->merchant_latitude,
                    'merchant_longitude' => $dec_data->merchant_longitude,
                    'open_hour' => $dec_data->open_hour,
                    'close_hour' => $dec_data->close_hour,
                    'merchant_category' => $dec_data->merchant_category,
                    'merchant_image' => $namafoto,
                    'merchant_telephone_number' => $dec_data->phone_number,
                    'merchant_phone_number' => $dec_data->phone,
                    'merchant_country_code' => $dec_data->countrycode,
                    'merchant_status' => '0',
                    'merchant_token' => time()
                );

                $imagektp = $dec_data->idcard_images;
                $namafotoktp = time() . '-' . rand(0, 99999) . ".jpg";
                $pathktp = "images/photofile/ktp/" . $namafotoktp;
                file_put_contents($pathktp, base64_decode($imagektp));

                $data_berkas = array(
                    'idcard_images' => $namafotoktp
                );


                $signup = $this->Merchantapi_model->signup($data_signup, $data_merchant, $data_berkas);
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

    function home_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);
        $condition = array(
            'partner_telephone' => $dec_data->phone_number,
            //'token' => $decoded_data->token
        );
        $app_settings = $this->Customer_model->get_settings();
        $balance = $this->Customer_model->saldouser($dec_data->idmitra);
        $transaction = $this->Merchantapi_model->transaksi_home($dec_data->idmerchant);
        $cek_login = $this->Merchantapi_model->get_data_merchant($condition);
        $payu = $this->Customer_model->payusettings()->result();
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
                    'app_email' => $item['app_email'],
                    'data' => $transaction->result(),
                    'user' => $cek_login->result(),
                    'payu' => $payu
                );
                $this->response($message, 200);
            } else {
                $message = array(
                    'code' => '200',
                    'message' => 'failed',
                    'data' => []
                );
                $this->response($message, 200);
            }
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
            'partner_telephone' => $dec_data->phone_number
        );
        $cek_login = $this->Merchantapi_model->get_data_merchant($condition);

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
            'partner_telephone' => $dec_data->phone_number
        );
        $cek_login = $this->Merchantapi_model->get_data_merchant($condition);

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

    function onoff_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        $data = file_get_contents("php://input");
        $decoded_data = json_decode($data);

        $update = [
            'merchant_status' => $decoded_data->status
        ];

        $where = [
            'merchant_id' => $decoded_data->idmerchant,
            'merchant_token' => $decoded_data->token
        ];

        $success = $this->Merchantapi_model->onmerchant($update, $where);

        if ($success) {
            $message = [
                'code' => '200',
                'message' => 'success',
                'data' => $decoded_data->status
            ];
            $this->response($message, 200);
        } else {
            $message = [
                'code' => '201',
                'message' => 'gagal',
                'data' => $decoded_data->status
            ];
            $this->response($message, 200);
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
        $check_exist = $this->Merchantapi_model->check_exist($email, $phone);

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
        $check_exist = $this->Merchantapi_model->check_exist($email, $phone);

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

    function history_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);
        $condition = array(
            'partner_telephone' => $dec_data->phone_number,
            //'token' => $decoded_data->token
        );
        $date = date_create($dec_data->day);
        $transaction = $this->Merchantapi_model->transaksi_history($dec_data->idmerchant);
        $transaksi_earning = $this->Merchantapi_model->total_history_earning($dec_data->idmerchant);
        $transaksi_daily = $this->Merchantapi_model->total_history_daily($dec_data->day, $dec_data->idmerchant);
        $transaksi_month = $this->Merchantapi_model->total_history_month(date_format($date, "m"), $dec_data->idmerchant);
        $transaksi_yearly = $this->Merchantapi_model->total_history_yearly(date_format($date, "Y"), $dec_data->idmerchant);
        $daily = '0';
        $month = '0';
        $yearly = '0';
        $earning = '0';
        if (!empty($transaksi_earning)) {
            foreach ($transaksi_earning->result_array() as $item) {
                if ($item['earning'] != NULL) {
                    $earning = $item['earning'];
                }
            }
        }

        foreach ($transaksi_daily->result_array() as $item) {
            if ($item['daily'] != NULL) {
                $daily = $item['daily'];
            }
        }
        foreach ($transaksi_month->result_array() as $item) {
            if ($item['month'] != NULL) {
                $month = $item['month'];
            }
        }
        foreach ($transaksi_yearly->result_array() as $item) {
            if ($item['yearly'] != NULL) {
                $yearly = $item['yearly'];
            }
        }
        $cek_login = $this->Merchantapi_model->get_data_merchant($condition);
        if ($cek_login->num_rows() > 0) {
            $message = array(
                'code' => '200',
                'message' => 'success',
                'data' => $transaction->result(),
                'daily' => $daily,
                'month' => $month,
                'year' => $yearly,
                'earning' => $earning,
            );
            $this->response($message, 200);
        } else {
            $message = array(
                'code' => '200',
                'message' => 'failed',
                'data' => []
            );
            $this->response($message, 200);
        }
    }

    function category_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);
        $condition = array(
            'partner_telephone' => $dec_data->phone_number,
            //'token' => $decoded_data->token
        );
        $cek_login = $this->Merchantapi_model->get_data_merchant($condition);
        $datakategori = $this->Merchantapi_model->kategori_active($dec_data->idmerchant);
        $datakategorinon = $this->Merchantapi_model->kategori_nonactive($dec_data->idmerchant);
        $totalitemactive = $this->Merchantapi_model->totalitemactive($dec_data->idmerchant);
        if ($cek_login->num_rows() > 0) {
            foreach ($totalitemactive->result() as $itemstatus) {
                $message = array(
                    'code' => '200',
                    'message' => 'success',
                    'dataactive' => $datakategori,
                    'datanonactive' => $datakategorinon,
                    'totalitemactive' => $itemstatus->active,
                    'totalitemnonactive' => $itemstatus->nonactive,
                    'totalitempromo' => $itemstatus->promo,
                );
                $this->response($message, 200);
            }
        } else {
            $message = array(
                'code' => '200',
                'message' => 'failed',
                'data' => [],
                'datanonactive' => []
            );
            $this->response($message, 200);
        }
    }

    function item_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);
        $condition = array(
            'partner_telephone' => $dec_data->phone_number,
            //'token' => $decoded_data->token
        );
        $cek_login = $this->Merchantapi_model->get_data_merchant($condition);
        $dataitemactive = $this->Merchantapi_model->itembycatactive($dec_data->idmerchant, $dec_data->idkategori);
        if ($cek_login->num_rows() > 0) {

            $message = array(
                'code' => '200',
                'message' => 'success',
                'data' => $dataitemactive->result()
            );
            $this->response($message, 200);
        } else {
            $message = array(
                'code' => '200',
                'message' => 'failed',
                'data' => []
            );
            $this->response($message, 200);
        }
    }

    function active_kategori_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);
        $condition = array(
            'partner_telephone' => $dec_data->phone_number,
            //'token' => $decoded_data->token
        );
        $cek_login = $this->Merchantapi_model->get_data_merchant($condition);
        $this->Merchantapi_model->actived_kategori($dec_data->idkategori, $dec_data->status);
        if ($cek_login->num_rows() > 0) {
            $message = array(
                'code' => '200',
                'message' => 'success'
            );
            $this->response($message, 200);
        } else {
            $message = array(
                'code' => '200',
                'message' => 'failed'
            );
            $this->response($message, 200);
        }
    }

    function active_item_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);
        $condition = array(
            'partner_telephone' => $dec_data->phone_number,
            //'token' => $decoded_data->token
        );
        $cek_login = $this->Merchantapi_model->get_data_merchant($condition);
        $this->Merchantapi_model->actived_item($dec_data->idkategori, $dec_data->status);
        if ($cek_login->num_rows() > 0) {
            $message = array(
                'code' => '200',
                'message' => 'success'
            );
            $this->response($message, 200);
        } else {
            $message = array(
                'code' => '200',
                'message' => 'failed'
            );
            $this->response($message, 200);
        }
    }

    function add_kategori_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);
        $condition = array(
            'partner_telephone' => $dec_data->phone_number,
            //'token' => $decoded_data->token
        );
        $cek_login = $this->Merchantapi_model->get_data_merchant($condition);
        $this->Merchantapi_model->addkategori($dec_data->namakategori, $dec_data->status, $dec_data->id);
        if ($cek_login->num_rows() > 0) {
            $message = array(
                'code' => '200',
                'message' => 'success'
            );
            $this->response($message, 200);
        } else {
            $message = array(
                'code' => '200',
                'message' => 'failed'
            );
            $this->response($message, 200);
        }
    }

    function edit_kategori_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);
        $condition = array(
            'partner_telephone' => $dec_data->phone_number,
            //'token' => $decoded_data->token
        );
        $dataedit = array(
            'category_name_item' => $dec_data->namakategori,
            'category_status' => $dec_data->status,
            'all_category' => 0,
        );
        $cek_login = $this->Merchantapi_model->get_data_merchant($condition);
        $this->Merchantapi_model->editkategori($dataedit, $dec_data->id);
        if ($cek_login->num_rows() > 0) {
            $message = array(
                'code' => '200',
                'message' => 'success'
            );
            $this->response($message, 200);
        } else {
            $message = array(
                'code' => '200',
                'message' => 'failed'
            );
            $this->response($message, 200);
        }
    }

    function delete_kategori_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);
        $condition = array(
            'partner_telephone' => $dec_data->phone_number,
            //'token' => $decoded_data->token
        );
        $cek_login = $this->Merchantapi_model->get_data_merchant($condition);
        $this->Merchantapi_model->deletekategori($dec_data->id);
        if ($cek_login->num_rows() > 0) {
            $message = array(
                'code' => '200',
                'message' => 'success'
            );
            $this->response($message, 200);
        } else {
            $message = array(
                'code' => '200',
                'message' => 'failed'
            );
            $this->response($message, 200);
        }
    }
    function add_item_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);
        $condition = array(
            'partner_telephone' => $dec_data->phone_number,
            //'token' => $decoded_data->token
        );

        $image = $dec_data->photo;
        $namafoto = time() . '-' . rand(0, 99999) . ".jpg";
        $path = "images/itemphoto/" . $namafoto;
        file_put_contents($path, base64_decode($image));

        $dataitem = array(
            'merchant_id' => $dec_data->idmerchant,
            'item_name' => $dec_data->nama,
            'item_price' => $dec_data->price,
            'promo_price' => $dec_data->promo_price,
            'item_category' => $dec_data->category,
            'item_desc' => $dec_data->deskripsi,
            'item_image' => $namafoto,
            'promo_status' => $dec_data->promo_status,
            'item_status' => 1
        );
        $cek_login = $this->Merchantapi_model->get_data_merchant($condition);
        $this->Merchantapi_model->additem($dataitem);
        if ($cek_login->num_rows() > 0) {
            $message = array(
                'code' => '200',
                'message' => 'success'
            );
            $this->response($message, 200);
        } else {
            $message = array(
                'code' => '200',
                'message' => 'failed'
            );
            $this->response($message, 200);
        }
    }

    function edit_item_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);
        $condition = array(
            'partner_telephone' => $dec_data->phone_number,
            //'token' => $decoded_data->token
        );



        if ($dec_data->photo == null && $dec_data->foto_lama == null) {
            $dataitem = array(
                'item_name' => $dec_data->nama,
                'item_price' => $dec_data->price,
                'promo_price' => $dec_data->promo_price,
                'item_category' => $dec_data->category,
                'item_desc' => $dec_data->deskripsi,
                'promo_status' => $dec_data->promo_status
            );
        } else {
            $image = $dec_data->photo;
            $namafoto = time() . '-' . rand(0, 99999) . ".jpg";
            $path = "images/itemphoto/" . $namafoto;
            file_put_contents($path, base64_decode($image));

            $photo = $dec_data->foto_lama;
            $path = "images/itemphoto/$photo";
            unlink("$path");

            $dataitem = array(
                'item_name' => $dec_data->nama,
                'item_price' => $dec_data->price,
                'promo_price' => $dec_data->promo_price,
                'item_category' => $dec_data->category,
                'item_desc' => $dec_data->deskripsi,
                'item_image' => $namafoto,
                'promo_status' => $dec_data->promo_status
            );
        }
        $cek_login = $this->Merchantapi_model->get_data_merchant($condition);
        $this->Merchantapi_model->edititem($dataitem, $dec_data->id);
        if ($cek_login->num_rows() > 0) {
            $message = array(
                'code' => '200',
                'message' => 'success'
            );
            $this->response($message, 200);
        } else {
            $message = array(
                'code' => '200',
                'message' => 'failed'
            );
            $this->response($message, 200);
        }
    }

    function delete_item_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);
        $condition = array(
            'partner_telephone' => $dec_data->phone_number,
            //'token' => $decoded_data->token
        );
        $cek_login = $this->Merchantapi_model->get_data_merchant($condition);
        $this->Merchantapi_model->deleteitem($dec_data->id);

        if ($cek_login->num_rows() > 0) {
            $photo = $dec_data->foto_lama;
            $path = "images/itemphoto/$photo";
            unlink("$path");
            $message = array(
                'code' => '200',
                'message' => 'success'
            );
            $this->response($message, 200);
        } else {
            $message = array(
                'code' => '200',
                'message' => 'failed'
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
        $check_exist_phone = $this->Merchantapi_model->check_exist_phone_edit($decoded_data->partner_id, $decoded_data->phone_number);
        $check_exist_email = $this->Merchantapi_model->check_exist_email_edit($decoded_data->partner_id, $decoded_data->email);
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
                'partner_telephone' => $decoded_data->phone_number
            );
            $condition2 = array(
                'partner_telephone' => $decoded_data->no_telepon_lama
            );
            $datauser = array(
                'nama' => $decoded_data->customer_fullname,
                'phone_number' => $decoded_data->phone_number,
                'phone' => $decoded_data->phone,
                'email' => $decoded_data->email,
                'countrycode' => $decoded_data->countrycode,
                'alamat' => $decoded_data->alamat
            );



            $cek_login = $this->Merchantapi_model->get_data_merchant($condition2);
            if ($cek_login->num_rows() > 0) {
                $upd_user = $this->Merchantapi_model->edit_profile_mitra_merchant($datauser, $decoded_data->no_telepon_lama);
                $getdata = $this->Merchantapi_model->get_data_merchant($condition);
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

    function edit_merchant_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $decoded_data = json_decode($data);

        $condition = array(
            'partner_telephone' => $decoded_data->phone_number
        );

        if ($decoded_data->photo == null && $decoded_data->foto_lama == null) {
            $datauser = array(
                'merchant_name' => $decoded_data->nama,
                'merchant_address' => $decoded_data->alamat,
                'merchant_latitude' => $decoded_data->latitude,
                'merchant_longitude' => $decoded_data->longitude,
                'open_hour' => $decoded_data->open_hour,
                'close_hour' => $decoded_data->close_hour
            );
        } else {
            $image = $decoded_data->photo;
            $namafoto = time() . '-' . rand(0, 99999) . ".jpg";
            $path = "images/merchant/" . $namafoto;
            file_put_contents($path, base64_decode($image));

            $photo = $decoded_data->foto_lama;
            $path = "./images/merchant/$photo";
            unlink("$path");


            $datauser = array(
                'merchant_name' => $decoded_data->nama,
                'merchant_address' => $decoded_data->alamat,
                'merchant_latitude' => $decoded_data->latitude,
                'merchant_longitude' => $decoded_data->longitude,
                'open_hour' => $decoded_data->open_hour,
                'close_hour' => $decoded_data->close_hour,
                'merchant_image' => $namafoto
            );
        }


        $cek_login = $this->Merchantapi_model->get_data_merchant($condition);
        if ($cek_login->num_rows() > 0) {
            $upd_user = $this->Merchantapi_model->edit_profile_token($datauser, $decoded_data->phone_number);
            $getdata = $this->Merchantapi_model->get_data_merchant($condition);
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

    function detail_transaksi_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);
        $condition = array(
            'partner_telephone' => $dec_data->phone_number
        );
        $cek_login = $this->Merchantapi_model->get_data_merchant($condition);
        if ($cek_login->num_rows() > 0) {
            $gettrans = $this->Customer_model->transaction($dec_data->id);
            $getdriver = $this->Customer_model->detail_driver($dec_data->driver_id);
            $getpelanggan = $this->Driver_model->get_data_pelangganid($dec_data->customer_id);
            $getitem = $this->Customer_model->detail_item($dec_data->id);

            $message = array(
                'status' => true,
                'message' => "success",
                'data' => $gettrans->result(),
                'driver' => $getdriver->result(),
                'customer' => $getpelanggan->result(),
                'item' => $getitem->result(),

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
            'partner_email' => $decoded_data->email,
            'partner_status' => '1'
        );
        $cek_login = $this->Merchantapi_model->get_data_merchant($condition);
        $app_settings = $this->Customer_model->get_settings();
        $token = sha1(rand(0, 999999) . time());


        if ($cek_login->num_rows() > 0) {
            $cheker = array('msg' => $cek_login->result());
            foreach ($app_settings as $item) {
                foreach ($cheker['msg'] as $item2 => $val) {
                    $dataforgot = array(
                        'userid' => $val->partner_id,
                        'token' => $token,
                        'idKey' => '3'
                    );
                }


                $forgot = $this->Customer_model->dataforgot($dataforgot);

                $linkbtn = base_url() . 'resetpass/rest/' . $token . '/3';
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
            'partner_telephone' => $decoded_data->phone_number
        );
        $condition2 = array(
            'password' => sha1($decoded_data->new_password),
            'partner_telephone' => $decoded_data->phone_number
        );
        $cek_login = $this->Merchantapi_model->get_data_merchant($condition);
        $message = array();

        if ($cek_login->num_rows() > 0) {
            $upd_regid = $this->Merchantapi_model->edit_profile($reg_id, $decoded_data->phone_number);
            $get_pelanggan = $this->Merchantapi_model->get_data_merchant($condition2);

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

    function kategorimerchant_get()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $category = $this->Merchantapi_model->kategori_merchant_active();
        $service = $this->Merchantapi_model->fitur_merchant_active();

        $message = [
            'code' => '200',
            'message' => 'success',
            'service' => $service,
            'category' => $category,
        ];
        $this->response($message, 200);
    }

    function kategorimerchantbyfitur_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        $data = file_get_contents("php://input");
        $decoded_data = json_decode($data);

        $category = $this->Merchantapi_model->kategori_merchant_active_data($decoded_data->idmerchant);
        $service = $this->Merchantapi_model->fitur_merchant_active();

        $message = [
            'code' => '200',
            'message' => 'success',
            'service' => $service,
            'category' => $category,
        ];
        $this->response($message, 200);
    }
}
