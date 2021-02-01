<?php
//'tes' => number_format(200 / 100, 2, ",", "."),
defined('BASEPATH') or exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';
class Payumoney extends REST_Controller
{

    public function __construct()
    {
        parent::__construct();
        $this->load->helper("url");
        $this->load->model('Customer_model');
        $this->load->model('Driver_model');
        $this->load->model('Merchantapi_model');
        date_default_timezone_set(time_zone);
    }

    function index_get()
    {
        $this->response("Api for ouride!", 200);
    }

    function payu_post()
    {

        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);
        $statususer = $dec_data->udf4;
        if ($statususer == "customer") {
            $conditionpelanggan = array(
                'id' => $dec_data->udf1,
                'password' => $dec_data->udf3
            );
            $cek_login = $this->Customer_model->get_data_pelanggan($conditionpelanggan);
        } else if ($statususer == "driver") {
            $conditiondriver = array(
                'id' => $dec_data->udf1,
                'password' => $dec_data->udf3
            );
            $cek_login = $this->Driver_model->get_data_pelanggan($conditiondriver);
        } else if ($statususer == "merchant") {
            $conditionmerchant = array(
                'partner_id' => $dec_data->udf1,
                'password' => $dec_data->udf3
                //'token' => $decoded_data->token
            );
            $cek_login = $this->Merchantapi_model->get_data_merchant($conditionmerchant);
        }
        if ($cek_login->num_rows() > 0) {
            if ($dec_data->status == 'Success') {
                $amount = str_replace(".", "", $dec_data->amount);

                $datapayu = array(
                    'id_user' => $dec_data->udf1,
                    'wallet_account' => $dec_data->paymentId,
                    'bank' => $dec_data->udf2,
                    'holder_name' => $dec_data->customerName,
                    'type' => $dec_data->productInfo,
                    'wallet_amount' => $amount,
                    'status' => 1
                );

                $this->Customer_model->insertwallet($datapayu);
                foreach ($cek_login->result_array() as $gettoken) {
                    $datasaldo = [
                        'balance' => ($gettoken['balance'] + $amount)
                    ];
                    $this->Customer_model->addsaldo($dec_data->udf1, $datasaldo);
                }


                $message = array(
                    'code' => '200',
                    'message' => 'success'
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
        //test server after webhook payumoney is enabled, delete this.
        $this->response("test webhook", 200);
    }
}
