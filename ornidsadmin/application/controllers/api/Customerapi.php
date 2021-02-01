<?php
//'tes' => number_format(200 / 100, 2, ",", "."),
defined('BASEPATH') or exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';
class Customerapi extends REST_Controller
{

    public function __construct()
    {
        parent::__construct();
        $this->load->helper("url");
        $this->load->database();
        $this->load->model('Customer_model');
        $this->load->model('Driver_model');
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
        $cek_login = $this->Customer_model->get_data_pelanggan($condition);
        $app_settings = $this->Customer_model->get_settings();
        $token = sha1(rand(0, 999999) . time());


        if ($cek_login->num_rows() > 0) {
            $cheker = array('msg' => $cek_login->result());
            foreach ($app_settings as $item) {
                foreach ($cheker['msg'] as $item2 => $val) {
                    $dataforgot = array(
                        'userid' => $val->id,
                        'token' => $token,
                        'idKey' => '1'
                    );
                }


                $forgot = $this->Customer_model->dataforgot($dataforgot);

                $linkbtn = base_url() . 'resetpass/rest/' . $token . '/1';
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
            'token' => $decoded_data->token
        );

        $condition = array(
            'password' => sha1($decoded_data->password),
            'phone_number' => $decoded_data->phone_number,
            //'token' => $decoded_data->token
        );
        $check_banned = $this->Customer_model->check_banned($decoded_data->phone_number);
        if ($check_banned) {
            $message = array(
                'message' => 'banned',
                'data' => []
            );
            $this->response($message, 200);
        } else {
            $cek_login = $this->Customer_model->get_data_pelanggan($condition);
            $message = array();

            if ($cek_login->num_rows() > 0) {
                $upd_regid = $this->Customer_model->edit_profile($reg_id, $decoded_data->phone_number);
                $get_pelanggan = $this->Customer_model->get_data_pelanggan($condition);

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

    function register_user_post()
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
        $check_exist = $this->Customer_model->check_exist($email, $phone);
        $check_exist_phone = $this->Customer_model->check_exist_phone($phone);
        $check_exist_email = $this->Customer_model->check_exist_email($email);
        if ($check_exist) {
            $message = array(
                'code' => '201',
                'message' => 'email and phone number already exist',
                'data' => []
            );
            $this->response($message, 201);
        } else if ($check_exist_phone) {
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
            if ($dec_data->checked == "true") {
                $message = array(
                    'code' => '200',
                    'message' => 'next',
                    'data' => []
                );
                $this->response($message, 200);
            } else {
                $image = $dec_data->customer_image;
                $namafoto = time() . '-' . rand(0, 99999) . ".jpg";
                $path = "images/customer/" . $namafoto;
                file_put_contents($path, base64_decode($image));
                $data_signup = array(
                    'id' => 'P' . time(),
                    'customer_fullname' => $dec_data->customer_fullname,
                    'email' => $dec_data->email,
                    'phone_number' => $dec_data->phone_number,
                    'phone' => $dec_data->phone,
                    'password' => sha1($dec_data->password),
                    'dob' => $dec_data->dob,
                    'countrycode' => $dec_data->countrycode,
                    'customer_image' => $namafoto,
                    'token' => $dec_data->token,
                );
                $signup = $this->Customer_model->signup($data_signup);
                if ($signup) {
                    $condition = array(
                        'password' => sha1($dec_data->password),
                        'email' => $dec_data->email
                    );
                    $datauser1 = $this->Customer_model->get_data_pelanggan($condition);
                    $message = array(
                        'code' => '200',
                        'message' => 'success',
                        'data' => $datauser1->result()
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
    }

    function kodepromo_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);
        $promocode = $this->Customer_model->promo_code_use($dec_data->code, $dec_data->service);
        if ($promocode) {
            $message = array(
                'code' => '200',
                'message' => 'success',
                'nominal' => $promocode['nominal'],
                'type' => $promocode['type']
            );
            $this->response($message, 200);
        } else {
            $message = array(
                'code' => '201',
                'message' => 'failed'
            );
            $this->response($message, 200);
        }
    }

    function listkodepromo_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        $promocode = $this->Customer_model->promo_code()->result();
        $message = array(
            'code' => '200',
            'message' => 'success',
            'data' => $promocode
        );
        $this->response($message, 200);
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
        $slider = $this->Customer_model->sliderhome();
        $service = $this->Customer_model->fiturhome();
        $allfitur = $this->Customer_model->fiturhomeall();
        $rating = $this->Customer_model->ratinghome();
        $balance = $this->Customer_model->saldouser($dec_data->id);
        $app_settings = $this->Customer_model->get_settings();
        $news = $this->Customer_model->beritahome();
        $kategorymerchant = $this->Customer_model->kategorymerchant()->result();
        $long = $dec_data->longitude;
        $lat = $dec_data->latitude;
        $merchantpromo = $this->Customer_model->merchantpromo($long, $lat)->result();
        $merchantnearby = $this->Customer_model->merchantnearby($long, $lat);



        $condition = array(
            'phone_number' => $dec_data->phone_number,
            'status' => '1'
        );
        $cek_login = $this->Customer_model->get_data_pelanggan($condition);
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
                    'slider' => $slider,
                    'service' => $service,
                    'allfitur' => $allfitur,
                    'ratinghome' => $rating,
                    'beritahome' => $news,
                    'kategorymerchanthome' => $kategorymerchant,
                    'merchantnearby' => $merchantnearby,
                    'merchantpromo' => $merchantpromo,
                    'data' => $cek_login->result(),
                    'payu' => $payu


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

    public function merchantbykategori_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);
        $category = $dec_data->category;
        $long = $dec_data->longitude;
        $lat = $dec_data->latitude;
        $merchantbykategori = $this->Customer_model->merchantbykategori($category, $long, $lat)->result();
        $condition = array(
            'phone_number' => $dec_data->phone_number,
            'status' => '1'
        );
        $cek_login = $this->Customer_model->get_data_pelanggan($condition);
        if ($cek_login->num_rows() > 0) {
            $message = array(
                'code' => '200',
                'message' => 'success',

                'merchantbykategori' => $merchantbykategori
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

    public function merchantbykategoripromo_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);
        $category = $dec_data->category;
        $long = $dec_data->longitude;
        $lat = $dec_data->latitude;

        $merchantbykategori = $this->Customer_model->merchantbykategoripromo($category, $long, $lat)->result();
        $condition = array(
            'phone_number' => $dec_data->phone_number,
            'status' => '1'
        );
        $cek_login = $this->Customer_model->get_data_pelanggan($condition);
        if ($cek_login->num_rows() > 0) {
            $message = array(
                'code' => '200',
                'message' => 'success',

                'merchantbykategori' => $merchantbykategori
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

    public function allmerchant_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);


        $service = $dec_data->service;
        $kategorymerchant = $this->Customer_model->kategorymerchantbyfitur($service)->result();
        $long = $dec_data->longitude;
        $lat = $dec_data->latitude;

        $allmerchantnearby = $this->Customer_model->allmerchantnearby($long, $lat, $service)->result();
        $condition = array(
            'phone_number' => $dec_data->phone_number,
            'status' => '1'
        );
        $cek_login = $this->Customer_model->get_data_pelanggan($condition);

        if ($cek_login->num_rows() > 0) {
            $message = array(
                'code' => '200',
                'message' => 'success',

                'kategorymerchant' => $kategorymerchant,
                'allmerchantnearby' => $allmerchantnearby


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

    public function allmerchantbykategori_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);


        $service = $dec_data->service;

        $long = $dec_data->longitude;
        $lat = $dec_data->latitude;
        $category = $dec_data->category;
        $allmerchantnearbybykategori = $this->Customer_model->allmerchantnearbybykategori($long, $lat, $service, $category)->result();
        $condition = array(
            'phone_number' => $dec_data->phone_number,
            'status' => '1'
        );
        $cek_login = $this->Customer_model->get_data_pelanggan($condition);

        if ($cek_login->num_rows() > 0) {
            $message = array(
                'code' => '200',
                'message' => 'success',


                'allmerchantnearby' => $allmerchantnearbybykategori


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

    public function searchmerchant_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);
        $like = $dec_data->like;
        $long = $dec_data->longitude;
        $lat = $dec_data->latitude;
        $service = $dec_data->service;
        $searchmerchantnearby = $this->Customer_model->searchmerchantnearby($like, $long, $lat, $service);
        $condition = array(
            'phone_number' => $dec_data->phone_number,
            'status' => '1'
        );
        $cek_login = $this->Customer_model->get_data_pelanggan($condition);

        if ($cek_login->num_rows() > 0) {
            $message = array(
                'code' => '200',
                'message' => 'success',


                'allmerchantnearby' => $searchmerchantnearby


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

    public function merchantbyid_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);
        $idmerchant = $dec_data->idmerchant;
        $long = $dec_data->longitude;
        $lat = $dec_data->latitude;

        $merchantbyid = $this->Customer_model->merchantbyid($idmerchant, $long, $lat)->row();
        $itemstatus = $this->Customer_model->itemstatus($idmerchant)->row();
        if (empty($itemstatus->promo_status)) {
            $itempromo = '0';
        } else {
            $itempromo = $itemstatus->promo_status;
        }


        $itembyid = $this->Customer_model->itembyid($idmerchant)->Result();
        $kategoriitem = $this->Customer_model->kategoriitem($idmerchant)->Result();

        $condition = array(
            'phone_number' => $dec_data->phone_number,
            'status' => '1'
        );
        $cek_login = $this->Customer_model->get_data_pelanggan($condition);

        if ($cek_login->num_rows() > 0) {

            $message = array(
                'code'              => '200',
                'message'           => 'success',
                'idfitur'           => $merchantbyid->service_id,
                'idmerchant'        => $merchantbyid->merchant_id,
                'namamerchant'      => $merchantbyid->merchant_name,
                'alamatmerchant'    => $merchantbyid->merchant_address,
                'latmerchant'       => $merchantbyid->merchant_latitude,
                'longmerchant'      => $merchantbyid->merchant_longitude,
                'bukamerchant'      => $merchantbyid->open_hour,
                'tutupmerchant'     => $merchantbyid->close_hour,
                'descmerchant'      => $merchantbyid->merchant_desc,
                'fotomerchant'      => $merchantbyid->merchant_image,
                'telpcmerchant'     => $merchantbyid->merchant_telephone_number,
                'distance'          => $merchantbyid->distance,
                'partner'           => $merchantbyid->partner,
                'category'          => $merchantbyid->category_name,
                'promo'             => $itempromo,
                'itembyid'          => $itembyid,
                'kategoriitem'      => $kategoriitem


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

    public function itembykategori_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);
        $idmerchant = $dec_data->id;

        $itemk = $dec_data->category;
        $itembykategori = $this->Customer_model->itembykategori($idmerchant, $itemk)->result();

        $condition = array(
            'phone_number' => $dec_data->phone_number,
            'status' => '1'
        );
        $cek_login = $this->Customer_model->get_data_pelanggan($condition);

        if ($cek_login->num_rows() > 0) {

            $message = array(
                'code'              => '200',
                'message'           => 'success',
                'itembyid'          => $itembykategori


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



    function rate_driver_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);


        $data_rate = array();

        if ($dec_data->note == "") {
            $data_rate = array(
                'customer_id' => $dec_data->customer_id,
                'driver_id' => $dec_data->driver_id,
                'rating' => $dec_data->rating,
                'transaction_id' => $dec_data->transaction_id
            );
        } else {
            $data_rate = array(
                'customer_id' => $dec_data->customer_id,
                'driver_id' => $dec_data->driver_id,
                'rating' => $dec_data->rating,
                'transaction_id' => $dec_data->transaction_id,
                'note' => $dec_data->note
            );
        }

        $finish_transaksi = $this->Customer_model->rate_driver($data_rate);

        if ($finish_transaksi) {
            $message = array(
                'message' => 'success',
                'data' => []
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
        $cek_login = $this->Customer_model->get_data_pelanggan($condition);

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
        $cek_login = $this->Customer_model->get_data_pelanggan($condition);

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

    public function topupstripe_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);

        $name = $dec_data->name;
        $email = $dec_data->email;
        $card_num = $dec_data->card_num;
        $card_cvc = $dec_data->cvc;
        $card_exp = explode("/", $dec_data->expired);

        $product = $dec_data->product;
        $number = $dec_data->number;
        $price = $dec_data->price;

        $iduser = $dec_data->id;

        //include Stripe PHP library
        require_once APPPATH . "third_party/stripe/init.php";

        //set api key
        $app_settings = $this->Customer_model->get_settings();
        foreach ($app_settings as $item) {
            $stripe = array(
                "secret_key"      => $item['stripe_secret_key'],
                "publishable_key" => $item['stripe_published_key']
            );

            if ($item['stripe_status'] == '1') {
                \Stripe\Stripe::setApiKey($stripe['secret_key']);
            } else if ($item['stripe_status'] == '2') {
                \Stripe\Stripe::setApiKey($stripe['publishable_key']);
            } else {
                \Stripe\Stripe::setApiKey("");
            }
        }

        $tokenstripe = \Stripe\Token::create([
            'card' => [
                'number' => $card_num,
                'exp_month' => $card_exp[0],
                'exp_year' => $card_exp[1],
                'cvc' => $card_cvc,
            ],
        ]);


        if (!empty($tokenstripe['id'])) {

            //add customer to stripe
            $customer = \Stripe\Customer::create(array(
                'email' => $email,
                'source' => $tokenstripe['id']
            ));

            //item information
            $itemName = $product;
            $itemNumber = $number;
            $itemPrice = $price;
            $currency = "usd";
            $orderID = "INV-" . time();

            //charge a credit or a debit card
            $charge = \Stripe\Charge::create(array(
                'customer' => $customer->id,
                'amount'   => $itemPrice,
                'currency' => $currency,
                'description' => $itemNumber,
                'metadata' => array(
                    'item_id' => $itemNumber
                )
            ));

            //retrieve charge details
            $chargeJson = $charge->jsonSerialize();

            //check whether the charge is successful
            if ($chargeJson['amount_refunded'] == 0 && empty($chargeJson['failure_code']) && $chargeJson['paid'] == 1 && $chargeJson['captured'] == 1) {
                //order details 
                $amount = $chargeJson['amount'];
                $balance_transaction = $chargeJson['balance_transaction'];
                $currency = $chargeJson['currency'];
                $status = $chargeJson['status'];
                $date = date("Y-m-d H:i:s");

                $datatopup = array(
                    'id_user' => $iduser,
                    'wallet_account' => $card_num,
                    'bank' => 'stripe',
                    'holder_name' => $name,
                    'type' => 'topup',
                    'wallet_amount' => $chargeJson['amount'],
                    'status' => 1
                );

                if ($status == 'succeeded') {
                    $topupdata = $this->Customer_model->insertwallet($datatopup);
                    $saldolama = $this->Customer_model->saldouser($iduser);
                    $saldobaru = $saldolama->row('balance') + $itemPrice;
                    $balance = array('balance' => $saldobaru);
                    $this->Customer_model->addsaldo($iduser, $balance);

                    $message = array(
                        'code' => '200',
                        'message' => 'success',
                        'data' => []
                    );
                    $this->response($message, 200);
                } else {
                    $message = array(
                        'code' => '201',
                        'message' => 'error',
                        'data' => []
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
            echo "Invalid Token";
            $statusMsg = "";
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
        $check_exist = $this->Customer_model->check_exist($email, $phone);

        if ($check_exist) {
            $this->Customer_model->insertwallet($datatopup);
            $saldolama = $this->Customer_model->saldouser($iduser);
            $saldobaru = $saldolama->row('balance') + $amount;
            $balance = array('balance' => $saldobaru);
            $this->Customer_model->addsaldo($iduser, $balance);

            $message = array(
                'code' => '200',
                'message' => 'success',
                'data' => []
            );
            $this->response($message, 200);
        } else {
            $message = array(
                'code' => '201',
                'message' => 'You have insufficient balance',
                'data' => []
            );
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
        $check_exist = $this->Customer_model->check_exist($email, $phone);

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

    function list_ride_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);

        $near = $this->Customer_model->get_driver_ride($dec_data->latitude, $dec_data->longitude, $dec_data->service);
        $message = array(
            'data' => $near->result()
        );
        $this->response($message, 200);
    }

    function list_bank_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $near = $this->Customer_model->listbank();
        $message = array(
            'data' => $near->result()
        );
        $this->response($message, 200);
    }

    function list_car_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);

        $near = $this->Customer_model->get_driver_car($dec_data->latitude, $dec_data->longitude, $dec_data->service);
        $message = array(
            'data' => $near->result()
        );
        $this->response($message, 200);
    }

    function detail_fitur_get()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        $app_settings = $this->Customer_model->get_settings();
        $cost = $this->Customer_model->get_biaya();
        foreach ($app_settings as $item) {
            $message = array(
                'data' => $cost,
                'currency' => $item['app_currency'],
            );
            $this->response($message, 200);
        }
    }

    function request_transaksi_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        } else {
            $cek = $this->Customer_model->check_banned_user($_SERVER['PHP_AUTH_USER']);
            if ($cek) {
                $message = array(
                    'message' => 'fail',
                    'data' => 'Status User Banned'
                );
                $this->response($message, 200);
            }
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);

        $data_req = array(
            'customer_id' => $dec_data->customer_id,
            'service_order' => $dec_data->service_order,
            'start_latitude' => $dec_data->start_latitude,
            'start_longitude' => $dec_data->start_longitude,
            'end_latitude' => $dec_data->end_latitude,
            'end_longitude' => $dec_data->end_longitude,
            'distance' => $dec_data->distance,
            'price' => $dec_data->price,
            'estimate_time' => $dec_data->estimasi,
            'order_time' => date('Y-m-d H:i:s'),
            'pickup_address' => $dec_data->pickup_address,
            'destination_address' => $dec_data->destination_address,
            'final_cost' => $dec_data->price,
            'promo_discount' => $dec_data->promo_discount,
            'wallet_payment' => $dec_data->wallet_payment
        );

        $request = $this->Customer_model->insert_transaksi($data_req);
        if ($request['status']) {
            $message = array(
                'message' => 'success',
                'data' => $request['data']
            );
            $this->response($message, 200);
        } else {
            $message = array(
                'message' => 'fail',
                'data' => $request['data']
            );
            $this->response($message, 200);
        }
    }

    function check_status_transaksi_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);

        $dataTrans = array(
            'transaction_id' => $dec_data->transaction_id
        );

        $getStatus = $this->Customer_model->check_status($dataTrans);
        $this->response($getStatus, 200);
    }

    function user_cancel_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);

        $data_req = array(
            'transaction_id' => $dec_data->transaction_id
        );
        $cancel_req = $this->Customer_model->user_cancel_request($data_req);
        if ($cancel_req['status']) {
            $this->Driver_model->delete_chat($cancel_req['iddriver'], $cancel_req['idpelanggan']);
            $message = array(
                'message' => 'canceled',
                'data' => []
            );
            $this->response($message, 200);
        } else {
            $message = array(
                'message' => 'cancel fail',
                'data' => []
            );
            $this->response($message, 200);
        }
    }

    function liat_lokasi_driver_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);
        $getLoc = $this->Customer_model->get_driver_location($dec_data->id);
        $message = array(
            'status' => true,
            'data' => $getLoc->result()
        );
        $this->response($message, 200);
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
        $getdriver = $this->Customer_model->detail_driver($dec_data->driver_id);
        $getitem = $this->Customer_model->detail_item($dec_data->id);

        $message = array(
            'status' => true,
            'data' => $gettrans->result(),
            'driver' => $getdriver->result(),
            'item' => $getitem->result(),

        );
        $this->response($message, 200);
    }

    function detail_berita_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);
        $getberita = $this->Customer_model->beritadetail($dec_data->id);
        $message = array(
            'status' => true,
            'data' => $getberita->result()
        );
        $this->response($message, 200);
    }

    function all_berita_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);
        $getberita = $this->Customer_model->allberita();
        $message = array(
            'status' => true,
            'data' => $getberita
        );
        $this->response($message, 200);
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
        $check_exist_phone = $this->Customer_model->check_exist_phone_edit($decoded_data->id, $decoded_data->phone_number);
        $check_exist_email = $this->Customer_model->check_exist_email_edit($decoded_data->id, $decoded_data->email);
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

            if ($decoded_data->customer_image == null && $decoded_data->fotopelanggan_lama == null) {
                $datauser = array(
                    'customer_fullname' => $decoded_data->customer_fullname,
                    'phone_number' => $decoded_data->phone_number,
                    'phone' => $decoded_data->phone,
                    'email' => $decoded_data->email,
                    'countrycode' => $decoded_data->countrycode,
                    'dob' => $decoded_data->dob
                );
            } else {
                $image = $decoded_data->customer_image;
                $namafoto = time() . '-' . rand(0, 99999) . ".jpg";
                $path = "images/customer/" . $namafoto;
                file_put_contents($path, base64_decode($image));

                $photo = $decoded_data->fotopelanggan_lama;
                $path = "./images/customer/$photo";
                unlink("$path");


                $datauser = array(
                    'customer_fullname' => $decoded_data->customer_fullname,
                    'phone_number' => $decoded_data->phone_number,
                    'phone' => $decoded_data->phone,
                    'email' => $decoded_data->email,
                    'customer_image' => $namafoto,
                    'countrycode' => $decoded_data->countrycode,
                    'dob' => $decoded_data->dob
                );
            }


            $cek_login = $this->Customer_model->get_data_pelanggan($condition2);
            if ($cek_login->num_rows() > 0) {
                $upd_user = $this->Customer_model->edit_profile($datauser, $decoded_data->no_telepon_lama);
                $getdata = $this->Customer_model->get_data_pelanggan($condition);
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

    function wallet_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        $data = file_get_contents("php://input");
        $decoded_data = json_decode($data);
        $getWallet = $this->Customer_model->getwallet($decoded_data->id);
        $message = array(
            'status' => true,
            'data' => $getWallet->result()
        );
        $this->response($message, 200);
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
        $getWallet = $this->Customer_model->all_transaksi($decoded_data->id);
        $message = array(
            'status' => true,
            'data' => $getWallet->result()
        );
        $this->response($message, 200);
    }

    function request_transaksi_send_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        } else {
            $cek = $this->Customer_model->check_banned_user($_SERVER['PHP_AUTH_USER']);
            if ($cek) {
                $message = array(
                    'message' => 'fail',
                    'data' => 'Status User Banned'
                );
                $this->response($message, 200);
            }
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);

        $data_req = array(
            'customer_id' => $dec_data->customer_id,
            'service_order' => $dec_data->service_order,
            'start_latitude' => $dec_data->start_latitude,
            'start_longitude' => $dec_data->start_longitude,
            'end_latitude' => $dec_data->end_latitude,
            'end_longitude' => $dec_data->end_longitude,
            'distance' => $dec_data->distance,
            'price' => $dec_data->price,
            'estimate_time' => $dec_data->estimasi,
            'order_time' => date('Y-m-d H:i:s'),
            'pickup_address' => $dec_data->pickup_address,
            'destination_address' => $dec_data->destination_address,
            'final_cost' => $dec_data->price,
            'promo_discount' => $dec_data->promo_discount,
            'wallet_payment' => $dec_data->wallet_payment
        );


        $dataDetail = array(
            'sender_name' => $dec_data->sender_name,
            'sender_phone' => $dec_data->sender_phone,
            'receiver_name' => $dec_data->receiver_name,
            'receiver_phone' => $dec_data->receiver_phone,
            'goods_item' => $dec_data->goods_item
        );

        $request = $this->Customer_model->insert_transaksi_send($data_req, $dataDetail);
        if ($request['status']) {
            $message = array(
                'message' => 'success',
                'data' => $request['data']->result()
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
        $cek_login = $this->Customer_model->get_data_pelanggan($condition);
        $message = array();

        if ($cek_login->num_rows() > 0) {
            $upd_regid = $this->Customer_model->edit_profile($reg_id, $decoded_data->phone_number);
            $get_pelanggan = $this->Customer_model->get_data_pelanggan($condition2);

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

    function alldriver_get($id)
    {
        $near = $this->Customer_model->get_driver_location_admin();
        $message = array(
            'data' => $near->result()
        );
        $this->response($message, 200);
    }

    function alltransactionpickup_get()
    {
        $near = $this->Customer_model->getAlltransaksipickup();
        $message = array(
            'data' => $near->result()
        );
        $this->response($message, 200);
    }

    function alltransactiondestination_get()
    {
        $near = $this->Customer_model->getAlltransaksidestination();
        $message = array(
            'data' => $near->result()
        );
        $this->response($message, 200);
    }


    function inserttransaksimerchant_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        } else {
            $cek = $this->Customer_model->check_banned_user($_SERVER['PHP_AUTH_USER']);
            if ($cek) {
                $message = array(
                    'message' => 'fail',
                    'data' => 'Status User Banned'
                );
                $this->response($message, 200);
            }
        }

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);

        $data_transaksi = array(
            'customer_id'      => $dec_data->customer_id,
            'service_order'       => $dec_data->service_order,
            'start_latitude'    => $dec_data->start_latitude,
            'start_longitude'   => $dec_data->start_longitude,
            'end_latitude'      => $dec_data->end_latitude,
            'end_longitude'     => $dec_data->end_longitude,
            'distance'             => $dec_data->distance,
            'price'             => $dec_data->price,
            'order_time'       => date('Y-m-d H:i:s'),
            'estimate_time'     => $dec_data->estimasi,
            'pickup_address'       => $dec_data->pickup_address,
            'destination_address'     => $dec_data->destination_address,
            'promo_discount'      => $dec_data->promo_discount,

            'wallet_payment'      => $dec_data->wallet_payment,
        );
        $total_belanja = [
            'total_belanja'     => $dec_data->total_biaya_belanja,
        ];



        $dataDetail = [
            'merchant_id'   => $dec_data->id_resto,
            'total_price'   => $dec_data->total_biaya_belanja,
            'validation_code'   => rand(0, 9999),

        ];



        $result = $this->Customer_model->insert_data_transaksi_merchant($data_transaksi, $dataDetail, $total_belanja);

        if ($result['status'] == true) {


            $pesanan = $dec_data->pesanan;

            foreach ($pesanan as $pes) {
                $item[] = [
                    'item_note' => $pes->note,
                    'item_id' => $pes->item_id,
                    'merchant_id' => $dec_data->id_resto,
                    'transaction_id' => $result['transaction_id'],
                    'item_amount' => $pes->qty,
                    'total_cost' => $pes->total_cost,
                ];
            }

            $request = $this->Customer_model->insert_data_item($item);

            if ($request['status']) {
                $message = array(
                    'message' => 'success',
                    'data' => $result['data'],


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
            $message = array(
                'message' => 'fail',
                'data' => []

            );
            $this->response($message, 200);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////


}
