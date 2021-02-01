<?php

defined('BASEPATH') or exit('No direct script access allowed');

class Customer_model extends CI_model
{

    function __construct()
    {
        parent::__construct();
    }

    public function count_user()
    {
        $this->db->select('count(id) as count');
        $this->db->from('customer');
        return $this->db->get();
    }

    public function check_phone_number($number)
    {
        $cek = $this->db->query("SELECT id FROM customer where phone='$number'");
        if ($cek->num_rows() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public function check_exist($email, $phone)
    {
        $cek = $this->db->query("SELECT id FROM customer where email = '$email' AND phone_number='$phone'");
        if ($cek->num_rows() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public function check_exist_phone($phone)
    {
        $cek = $this->db->query("SELECT id FROM customer where phone_number='$phone'");
        if ($cek->num_rows() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public function check_exist_phone_edit($id, $phone)
    {
        $cek = $this->db->query("SELECT phone_number FROM customer where phone_number='$phone' AND id!='$id'");
        if ($cek->num_rows() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public function check_exist_email($email)
    {
        $cek = $this->db->query("SELECT id FROM customer where email='$email'");
        if ($cek->num_rows() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public function check_exist_email_edit($id, $email)
    {
        $cek = $this->db->query("SELECT id FROM customer where email='$email' AND id!='$id'");
        if ($cek->num_rows() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public function check_banned($phone)
    {
        $stat =  $this->db->query("SELECT id FROM customer WHERE status='3' AND phone_number='$phone'");
        if ($stat->num_rows() == 1) {
            return true;
        } else {
            return false;
        }
    }


    public function get_data_pelanggan($condition)
    {
        $this->db->select('customer.*, balance.balance');
        $this->db->from('customer');
        $this->db->join('balance', 'customer.id = balance.id_user');
        $this->db->where($condition);
        return $this->db->get();
    }

    public function signup($data_signup)
    {
        $signup = $this->db->insert('customer', $data_signup);
        $dataIns = array(
            'id_user' => $data_signup['id'],
            'balance' => 0
        );
        $insSaldo = $this->db->insert('balance', $dataIns);
        return $signup;
    }

    public function edit_profile($data, $email)
    {

        $this->db->where('phone_number', $email);
        $this->db->update('customer', $data);
        return true;
    }

    public function check_password($condition)
    {
        $this->db->select('id');
        $this->db->from('customer');
        $this->db->where($condition);
        $cek = $this->db->get();
        if ($cek->num_rows() > 0) {
            return true;
        } else {
            return false;
        }
    }

    function emailsend($subject, $emailuser, $content, $host, $port, $username, $password, $from, $appname, $secure)
    {
        require APPPATH . '/libraries/class.phpmailer.php';
        $mail = new PHPMailer;
        $mail->IsSMTP();
        $mail->SMTPSecure = $secure;
        $mail->Host = $host; //host masing2 provider email
        $mail->SMTPDebug = 0;
        $mail->Port = $port;
        $mail->SMTPAuth = true;
        $mail->Username = $username; //user email
        $mail->Password = $password; //password email 
        $mail->SetFrom($from, $appname); //set email pengirim
        $mail->Subject = $subject; //subyek email
        $mail->AddAddress($emailuser, "User");  //tujuan email
        $mail->MsgHTML($content); //pesan dapat berupa html
        $mail->Send();
        return true;
    }

    public function get_settings()
    {
        $this->db->select('*');
        $this->db->from('app_settings');
        $this->db->where('id', '1');
        return $this->db->get()->result_array();
    }

    public function dataforgot($forgotpass)
    {
        $forgot = $this->db->insert('forgot_password', $forgotpass);
        return $forgot;
    }

    public function sliderhome()
    {
        $this->db->select('*');
        $this->db->from('promotion');
        $this->db->join('service', 'promotion.promotion_service = service.service_id', 'left');
        $this->db->where('is_show', '1');
        $this->db->where('exp_date>', date("Y-m-d"));
        return $this->db->get()->result_array();
    }

    public function fiturhome()
    {
        $this->db->select('*');
        $this->db->from('service');
        $this->db->where('active', '1');
        $this->db->order_by('service_id ASC');
        $this->db->limit('7');
        return $this->db->get()->result_array();
    }

    public function fiturhomeall()
    {
        $this->db->select('*');
        $this->db->from('service');
        $this->db->where('active', '1');
        $this->db->order_by('service_id ASC');
        return $this->db->get()->result_array();
    }

    public function beritahome()
    {
        $this->db->select('*');
        $this->db->from('news');
        $this->db->join('news_category', 'news.category_id = news_category.news_category_id', 'left');
        $this->db->order_by('news_id DESC');
        $this->db->limit('4');
        $this->db->where('news_status', 1);
        return $this->db->get()->result_array();
    }

    public function allberita()
    {
        $this->db->select('*');
        $this->db->from('news');
        $this->db->join('news_category', 'news.category_id = news_category.news_category_id', 'left');
        $this->db->order_by('news_id DESC');
        $this->db->where('news_status', 1);
        return $this->db->get()->result_array();
    }

    public function beritadetail($id)
    {
        $this->db->select('*');
        $this->db->from('news');
        $this->db->join('news_category', 'news.category_id = news_category.news_category_id', 'left');
        $this->db->where('news_id', $id);
        $this->db->order_by('news_id DESC');
        return $this->db->get();
    }

    public function ratinghome()
    {
        $this->db->select('driver_rating.*, customer.*');
        $this->db->from('driver_rating');
        $this->db->where('driver_rating.rating != 0');
        $this->db->where('driver_rating.rating != 1');
        $this->db->where('driver_rating.rating != 2');
        $this->db->where('driver_rating.rating != 3');
        $this->db->join('customer', 'driver_rating.customer_id = customer.id', 'left');
        $this->db->order_by('driver_rating.number DESC');
        $this->db->limit('3');
        return $this->db->get()->result_array();
    }

    public function saldouser($id)
    {
        $this->db->select('balance');
        $this->db->from('balance');
        $this->db->where('id_user', $id);
        $balance = $this->db->get();
        return $balance;
    }

    public function payusettings()
    {
        $this->db->select('*');
        $this->db->from('payusettings');
        $this->db->where('id', 1);
        $balance = $this->db->get();
        return $balance;
    }

    public function getwallet($id)
    {
        $this->db->select('*');
        $this->db->from('wallet');
        $this->db->where('id_user', $id);
        $this->db->order_by('id', 'DESC');
        return $this->db->get();
    }

    public function addsaldo($id, $data)
    {
        $this->db->where('id_user', $id);
        $this->db->update('balance', $data);
        return true;
    }


    public function insertwallet($data_withdraw)
    {
        $verify = $this->db->insert('wallet', $data_withdraw);
        return true;
    }

    public function get_driver_ride($lat, $lng, $service)
    {
        $url_foto = base_url() . 'images/driverphoto/';

        $result = $this->db->query("
            SELECT f.minimum_distance, f.minimum_wallet, d.id as id, d.driver_name, ld.latitude, ld.longitude, ld.bearing, ld.update_at,
            k.brand, k.vehicle_registration_number, k.color, k.type, s.balance,
            d.phone_number, CONCAT('$url_foto', d.photo, '') as photo, d.reg_id, dj.driver_job,
                (6371 * acos(cos(radians($lat)) * cos(radians( ld.latitude ))"
            . " * cos(radians(ld.longitude) - radians($lng))"
            . " + sin(radians($lat)) * sin( radians(ld.latitude)))) AS distance
            FROM config_driver ld, driver d, driver_job dj, vehicle k, balance s,service f
            WHERE ld.driver_id = d.id 
                AND f.service_id = $service
                AND ld.status = '1'
                AND dj.id = d.job
                AND d.job = f.driver_job
                AND d.status = '1'
                AND k.vehicle_id = d.vehicle
                AND s.id_user = d.id
                AND s.balance > f.minimum_wallet
            HAVING distance <= f.minimum_distance
            ORDER BY distance ASC");
        return $result;
    }

    public function get_driver_car($lat, $lng, $service)
    {
        $range = 10;
        $url_foto = base_url() . 'images/driverphoto/';

        $result = $this->db->query("
            SELECT f.minimum_distance, f.minimum_wallet, d.id as id, d.driver_name, ld.latitude, ld.longitude, ld.bearing, ld.update_at,
            k.brand, k.vehicle_registration_number, k.color, k.type, s.balance,
            d.phone_number, CONCAT('$url_foto', d.photo, '') as photo, d.reg_id, dj.driver_job,
                (6371 * acos(cos(radians($lat)) * cos(radians( ld.latitude ))"
            . " * cos(radians(ld.longitude) - radians($lng))"
            . " + sin(radians($lat)) * sin( radians(ld.latitude)))) AS distance
            FROM config_driver ld, driver d, driver_job dj, vehicle k, balance s,service f
            WHERE ld.driver_id = d.id 
                AND f.service_id = $service
                AND ld.status = '1'
                AND dj.id = d.job
                AND d.job = '2'
                AND d.status = '1'
                AND k.vehicle_id = d.vehicle
                AND s.id_user = d.id
                AND s.balance > 500
            HAVING distance <= $range
            ORDER BY distance ASC");
        return $result;
    }

    function get_biaya()
    {
        $tempBiaya = array();
        $this->db->select('service.*,voucher.voucher_discount, driver_job.icon as icon_driver');
        $this->db->from('service');
        $this->db->join('voucher', 'service.service_id = voucher.voucher_service', 'left');
        $this->db->join('driver_job', 'service.driver_job = driver_job.id', 'left');
        $cost = $this->db->get()->result_array();
        foreach ($cost as $row) {
            $tempBiaya[] = array(
                'service_id' => $row['service_id'],
                'service' => $row['service'],
                'cost' => $row['cost'],
                'icon' => $row['icon'],
                'home' => $row['home'],
                'maks_distance' => $row['maks_distance'],
                'icon_driver' => $row['icon_driver'],
                'minimum_cost' => $row['minimum_cost'],
                'cost_desc' => $row['cost_desc'],
                'description' => $row['description'],
                'diskon' => $row['voucher_discount'] . "%",
                'final_cost' => $row['voucher_discount'] / 100
            );
        }

        return $tempBiaya;
    }


    public function insert_transaksi($data_req)
    {

        $ha = 0;
        $kreditamuont = explode(".", $data_req['promo_discount']);
        $ha  = $data_req['price'] - $kreditamuont[0];
        if ($ha <= 0) {
            $ha = 0;
        }
        $data_req['promo_discount'] = $kreditamuont[0];
        $data_req['final_cost'] = $ha;

        $this->db->insert('transaction', $data_req);
        $reqid = $this->db->insert_id();
        if ($this->db->affected_rows() == 1) {
            $get_data = $this->get_data_transaksi($data_req);
            $data_hist = array(
                'transaction_id' => $reqid,
                'driver_id' => 'D0',
                'status' => '1'
            );
            $this->db->insert('transaction_history', $data_hist);
            return array(
                'status' => true,
                'data' => $get_data->result()
            );
        } else {
            return array(
                'status' => false,
                'data' => []
            );
        }
    }

    public function insert_transaksi_send($data_req, $dataDetail)
    {

        $ha = 0;
        $kreditamuont = explode(".", $data_req['promo_discount']);
        $ha  = $data_req['price'] - $kreditamuont[0];
        if ($ha <= 0) {
            $ha = 0;
        }
        $data_req['promo_discount'] = $kreditamuont[0];
        $data_req['final_cost'] = $ha;

        $ins_trans = $this->db->insert('transaction', $data_req);
        $reqid = $this->db->insert_id();
        if ($this->db->affected_rows() == 1) {
            $data_hist = array(
                'transaction_id' => $reqid,
                'driver_id' => 'D0',
                'status' => '1'
            );
            $this->db->insert('transaction_history', $data_hist);
            $dataDetail['transaction_id'] = $reqid;
            $this->db->insert('detail_send_transaction', $dataDetail);
            $get_data_msend = $this->get_data_transaksi_send($data_req);
            return array(
                'status' => true,
                'data' => $get_data_msend
            );
        } else {
            return array(
                'status' => false,
                'data' => []
            );
        }
    }

    function get_data_transaksi_send($data_cond)
    {
        $this->db->select('*');
        $this->db->from('transaction');
        $this->db->join('detail_send_transaction', 'transaction.id = detail_send_transaction.transaction_id', 'left');
        $this->db->where($data_cond);
        $cek = $this->db->get();
        return $cek;
    }

    function get_data_transaksi($cond)
    {
        $this->db->select('*');
        $this->db->from('transaction');
        $this->db->join('detail_send_transaction', 'transaction.id = detail_send_transaction.transaction_id', 'left');
        $this->db->where($cond);
        $cek = $this->db->get();
        return $cek;
    }

    function get_data_transaksi_merchant($cond)
    {
        $this->db->select('transaction.*,merchant_detail_transaction.merchant_transaction_id, merchant.merchant_token');
        $this->db->from('transaction');
        $this->db->join('merchant_detail_transaction', 'transaction.id = merchant_detail_transaction.transaction_id', 'left');
        $this->db->join('merchant', 'merchant_detail_transaction.merchant_id = merchant.merchant_id', 'left');
        $this->db->where($cond);
        $cek = $this->db->get();
        return $cek;
    }

    function check_status($dataTrans)
    {
        $this->db->select(''
            . 'transaction_status.id as status,'
            . 'transaction_status.transaction_status as description');
        $this->db->from('transaction_history');
        $this->db->join('transaction_status', 'transaction_history.status = transaction_status.id');
        $this->db->where($dataTrans);
        $cek = $this->db->get();

        $stat = TRUE;
        if ($cek->row('status') == 1) {
            $this->delete_transaksi($dataTrans['transaction_id']);
            $stat = FALSE;
        }
        $dataCheck = array(
            'message' => 'check status',
            'status' => $stat,
            'data' => $cek->result(),
            'list_driver' => $this->get_data_driver_histroy($dataTrans['transaction_id'])->result()
        );

        return $dataCheck;
    }

    function check_banned_user($email)
    {
        $this->db->select('*');
        $this->db->from('customer');
        $this->db->where('email', $email);
        $this->db->where("(status = '2' OR status = '3')", NULL, FALSE);
        //        $this->db->where('status', '3');
        $cek = $this->db->get();
        if ($cek->num_rows() > 0) {
            return TRUE;
        } else {
            return FALSE;
        }
    }

    function listbank()
    {
        $this->db->select('*');
        $this->db->from('bank_list');
        $this->db->where('bank_status', "1");
        return $this->db->get();
    }

    function get_data_driver_histroy($transaction_id)
    {
        $url_foto = base_url() . 'images/driverphoto/';

        $this->db->select(''
            . 'driver.id,'
            . 'driver_name,'
            . 'phone_number,'
            . 'cd.latitude,'
            . 'cd.longitude,'
            . 'cd.update_at,'
            . "CONCAT('$url_foto', driver.photo, '') as photo,"
            . 'reg_id,'
            . '"0" as distance,'
            . 'k.vehicle_id as id_kendaraan,'
            . 'k.brand,'
            . 'k.type,'
            . 'k.variant,'
            . 'k.vehicle_registration_number,'
            . 'k.color');
        $this->db->from('driver');
        $this->db->join('transaction_history', 'driver.id = transaction_history.driver_id');
        $this->db->join('config_driver cd', 'driver.id = cd.driver_id');
        $this->db->join('vehicle k', 'driver.vehicle = k.vehicle_id');
        $this->db->where('transaction_history.transaction_id', $transaction_id);
        $getD = $this->db->get();
        return $getD;
    }

    function delete_chat($otherid, $userid)
    {
        $headers = array(
            "Accept: application/json",
            "Content-Type: application/json"
        );
        $data3 = array();
        $url3 = firebaseDb . '/chat/' . $otherid . '-' . $userid . '/.json';
        $ch3 = curl_init($url3);

        curl_setopt($ch3, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch3, CURLOPT_CUSTOMREQUEST, 'DELETE');
        curl_setopt($ch3, CURLOPT_POSTFIELDS, json_encode($data3));
        curl_setopt($ch3, CURLOPT_HTTPHEADER, $headers);

        $return3 = curl_exec($ch3);

        $json_data3 = json_decode($return3, true);

        $data2 = array();

        $url2 = firebaseDb . '/chat/' . $userid . '-' . $otherid . '/.json';
        $ch2 = curl_init($url2);

        curl_setopt($ch2, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch2, CURLOPT_CUSTOMREQUEST, 'DELETE');
        curl_setopt($ch2, CURLOPT_POSTFIELDS, json_encode($data2));
        curl_setopt($ch2, CURLOPT_HTTPHEADER, $headers);

        $return2 = curl_exec($ch2);

        $json_data2 = json_decode($return2, true);

        $data1 = array();

        $url1 = firebaseDb . '/Inbox/' . $userid . '/' . $otherid . '/.json';
        $ch1 = curl_init($url1);

        curl_setopt($ch1, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch1, CURLOPT_CUSTOMREQUEST, 'DELETE');
        curl_setopt($ch1, CURLOPT_POSTFIELDS, json_encode($data1));
        curl_setopt($ch1, CURLOPT_HTTPHEADER, $headers);

        $return1 = curl_exec($ch1);

        $json_data1 = json_decode($return1, true);

        $data = array();

        $url = firebaseDb . '/Inbox/' . $otherid . '/' . $userid . '/.json';
        $ch = curl_init($url);

        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_CUSTOMREQUEST, 'DELETE');
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($data));
        curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);

        $return = curl_exec($ch);

        $json_data = json_decode($return, true);
    }

    public function get_trans_merchant($idtransaksi)
    {
        $this->db->select('partner.*,merchant_detail_transaction.merchant_id,merchant_detail_transaction.total_price');
        $this->db->from('merchant_detail_transaction');
        $this->db->join('partner', 'merchant_detail_transaction.merchant_id = partner.merchant_id');
        $this->db->where('transaction_id', $idtransaksi);
        return $this->db->get();
    }

    public function user_cancel_request($cond)
    {


        $this->db->select(''
            . 'driver_id,'
            . 'status');
        $this->db->from('transaction_history');
        $this->db->where('transaction_id', $cond['transaction_id']);
        $id = $this->db->get();

        $this->db->select('transaction.*, service.home');
        $this->db->from('transaction');
        $this->db->join('service', 'transaction.service_order = service.service_id', 'left');
        $this->db->where('id', $cond['transaction_id']);
        $id2 = $this->db->get();


        if ($id->row('status') == 1 || $id->row('status') == 2) {
            $data = array(
                'status' => '5'
            );
            if ($id2->row('home') == 4) {
                $get_mitra = $this->get_trans_merchant($cond['transaction_id']);
                $this->delete_chat($get_mitra->row('merchant_id'), $id2->row('customer_id'));
                $this->delete_chat($get_mitra->row('merchant_id'), $id2->row('driver_id'));
            };
            $this->db->where($cond);
            $edit = $this->db->update('transaction_history', $data);
            $data = array(
                'status' => '1'
            );
            $this->db->where('driver_id', $id->row('driver_id'));
            $edit = $this->db->update('config_driver', $data);
            return array(
                'status' => true,
                'data' => [],
                'iddriver' => $id->row('driver_id'),
                'idpelanggan' => $id2->row('customer_id')
            );
        } else {
            return array(
                'status' => false,
                'data' => []
            );
        }
    }

    public function user_expired_request($cond)
    {

        $this->db->select(''
            . 'driver_id, '
            . 'status');
        $this->db->from('transaction_history');
        $this->db->where('transaction_id', $cond['transaction_id']);
        $id = $this->db->get();

        if ($id->row('status') == 1 || $id->row('status') == 2) {
            $data = array(
                'status' => '0'
            );
            $this->db->where($cond);
            $edit = $this->db->update('transaction_history', $data);
            $data = array(
                'status' => '1'
            );
            $this->db->where('driver_id', $id->row('driver_id'));
            $edit = $this->db->update('config_driver', $data);

            return array(
                'status' => true,
                'data' => [],
                'iddriver' => $id->row('driver_id'),
                'idpelanggan' => $id->row('customer_id')
            );
        } else {
            return array(
                'status' => false,
                'data' => []
            );
        }
    }

    function detail_transaksi($idtrans)
    {
        $this->db->select('*');
        $this->db->from('transaction');
        $this->db->where('id', $idtrans);
        $loc = $this->db->get();
        return $loc;
    }

    function detail_driver($iddriver)
    {
        $this->db->select('*');
        $this->db->from('driver');
        $this->db->join('vehicle', 'driver.vehicle = vehicle.vehicle_id', 'left');
        $this->db->where('id', $iddriver);
        $loc = $this->db->get();
        return $loc;
    }

    function get_driver_location($idDriver)
    {
        $this->db->select(''
            . 'driver_id,'
            . 'latitude,'
            . 'longitude');
        $this->db->from('config_driver');
        $this->db->where('driver_id', $idDriver);
        $loc = $this->db->get();
        return $loc;
    }

    function get_driver_location_admin()
    {
        $this->db->select(''
            . 'config_driver.driver_id,'
            . 'config_driver.latitude,'
            . 'config_driver.longitude,'
            . 'config_driver.status,'
            . 'driver.driver_name');
        $this->db->from('config_driver');
        $this->db->join('driver', 'config_driver.driver_id = driver.id', 'left');
        $this->db->where('driver.status != 0');
        $this->db->where('driver.status != 3');
        $loc = $this->db->get();
        return $loc;
    }

    function rate_driver($data)
    {
        if ($data['rating'] > 0) {
            $this->db->insert('driver_rating', $data);
            if ($this->db->affected_rows() == 1) {

                $this->db->where('id', $data['transaction_id']);
                $upd_trans = $this->db->update('transaction', array('rate' => $data['rating']));


                $get_rating = $this->count_rate_driver($data['driver_id']);
                $this->db->where('id', $data['driver_id']);
                $this->db->update('driver', array('rating' => $get_rating));

                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    function count_rate_driver($id)
    {
        $this->db->select('rating');
        $this->db->from('driver_rating');
        $this->db->where('driver_id', $id);
        $cek = $this->db->get();
        $rate = 0;
        $hasil = 0;
        if ($cek->num_rows() > 0) {
            foreach ($cek->result() as $row) {
                $rate += $row->rating;
            }
            $hasil = $rate / $cek->num_rows();
        }
        return $hasil;
    }

    function diskon_wallet()
    {
        $this->db->select('*');
        $this->db->from('voucher');
        $this->db->where("voucher LIKE 'DISKON%'");
        $this->db->order_by('id', 'asc');
        $disk = $this->db->get();

        $arrDisk = array();

        foreach ($disk->result() as $row) {
            $diskmpay = array(
                'service' => $row->voucher_service,
                'diskon' => $row->voucher_discount . "%",
                'final_cost' => (100 - $row->voucher_discount) / 100
            );
            array_push($arrDisk, $diskmpay);
        }
        return $disk;
    }

    function promo_code()
    {
        $this->db->select('*');
        $this->db->from('promocode');
        $this->db->where("status", 1);
        $this->db->where('expired >', date("Y-m-d"));
        $this->db->order_by('promo_id', 'DESC');
        return $this->db->get();
    }

    function promo_code_use($code, $service)
    {
        $this->db->select('*');
        $this->db->from('promocode');
        $this->db->where('promo_code', $code);
        $this->db->where("service", $service);
        $this->db->where("status", 1);
        $this->db->where('expired >', date("Y-m-d"));
        $diskon = $this->db->get();

        if ($diskon->num_rows() > 0) {
            $out = [
                'nominal' => $diskon->row('promo_amount'),
                'type' => $diskon->row('promo_type')
            ];
            return $out;
        } else {
            return false;
        }
    }

    function transaction($idtran)
    {
        $this->db->select('*');
        $this->db->from('transaction');

        $this->db->join('merchant_detail_transaction', 'transaction.id = merchant_detail_transaction.transaction_id', 'left');
        $this->db->join('service', 'transaction.service_order = service.service_id', 'left');
        $this->db->join('merchant', 'merchant_detail_transaction.merchant_id = merchant.merchant_id', 'left');
        $this->db->join('transaction_history', 'transaction.id = transaction_history.transaction_id', 'left');
        $this->db->join('detail_send_transaction', 'transaction.id = detail_send_transaction.transaction_id', 'left');
        $this->db->where('transaction.id', $idtran);

        $trans = $this->db->get();
        return $trans;
    }

    function all_transaksi($iduser)
    {
        $this->db->select('*');
        $this->db->from('transaction');
        $this->db->join('transaction_history', 'transaction.id = transaction_history.transaction_id', 'left');
        $this->db->join('transaction_status', 'transaction_history.status = transaction_status.id', 'left');
        $this->db->join('service', 'transaction.service_order = service.service_id', 'left');
        $this->db->where('transaction.customer_id', $iduser);
        $this->db->where('transaction_history.status != 1');
        $this->db->where('transaction_history.status != 0');
        $this->db->order_by('transaction.id', 'DESC');
        $trans = $this->db->get();
        return $trans;
    }

    public function getAlltransaksipickup()
    {
        $this->db->select('transaction.*,' . 'driver.driver_name,' . 'customer.customer_fullname,' . 'transaction_history.*,' . 'transaction_status.*,' . 'service.icon,' . 'service.service');
        $this->db->from('transaction');
        $this->db->join('transaction_history', 'transaction.id = transaction_history.transaction_id', 'left');
        $this->db->join('transaction_status', 'transaction_history.status = transaction_status.id', 'left');
        $this->db->join('driver', 'transaction.driver_id = driver.id', 'left');
        $this->db->join('customer', 'transaction.customer_id = customer.id', 'left');
        $this->db->join('service', 'transaction.service_order = service.service_id', 'left');
        $this->db->where('transaction_history.status != 1');
        $this->db->where('transaction_history.status != 4');
        $this->db->where('transaction_history.status != 5');
        $this->db->where('transaction_history.status != 3');
        $this->db->order_by('transaction.id', 'DESC');
        $this->db->limit('10');
        return $this->db->get();
    }

    public function getAlltransaksidestination()
    {
        $this->db->select('transaction.*,' . 'driver.driver_name,' . 'customer.customer_fullname,' . 'transaction_history.*,' . 'transaction_status.*,' . 'service.icon,' . 'service.service');
        $this->db->from('transaction');
        $this->db->join('transaction_history', 'transaction.id = transaction_history.transaction_id', 'left');
        $this->db->join('transaction_status', 'transaction_history.status = transaction_status.id', 'left');
        $this->db->join('driver', 'transaction.driver_id = driver.id', 'left');
        $this->db->join('customer', 'transaction.customer_id = customer.id', 'left');
        $this->db->join('service', 'transaction.service_order = service.service_id', 'left');
        $this->db->where('transaction_history.status != 1');
        $this->db->where('transaction_history.status != 4');
        $this->db->where('transaction_history.status != 5');
        $this->db->where('transaction_history.status != 2');
        $this->db->order_by('transaction.id', 'DESC');
        $this->db->limit('10');
        return $this->db->get();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    function template1($subject, $text1, $text2, $web, $appname, $linkbtn, $linkgoogle, $address)
    {
        $msg = '<html xmlns="http://www.w3.org/1999/xhtml" xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:v="urn:schemas-microsoft-com:vml"><head>
<!--[if gte mso 9]><xml><o:OfficeDocumentSettings><o:AllowPNG/><o:PixelsPerInch>96</o:PixelsPerInch></o:OfficeDocumentSettings></xml><![endif]-->
<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
<meta content="width=device-width" name="viewport">
<!--[if !mso]><!-->
<meta content="IE=edge" http-equiv="X-UA-Compatible">
<!--<![endif]-->
<title></title>
<!--[if !mso]><!-->
<!--<![endif]-->
<style type="text/css">
		body {
			margin: 0;
			padding: 0;
		}

		table,
		td,
		tr {
			vertical-align: top;
			border-collapse: collapse;
		}

		* {
			line-height: inherit;
		}

		a[x-apple-data-detectors=true] {
			color: inherit !important;
			text-decoration: none !important;
		}
	</style>
<style id="media-query" type="text/css">
		@media (max-width: 610px) {

			.block-grid,
			.col {
				min-width: 320px !important;
				max-width: 100% !important;
				display: block !important;
			}

			.block-grid {
				width: 100% !important;
			}

			.col {
				width: 100% !important;
			}

			.col>div {
				margin: 0 auto;
			}

			img.fullwidth,
			img.fullwidthOnMobile {
				max-width: 100% !important;
			}

			.no-stack .col {
				min-width: 0 !important;
				display: table-cell !important;
			}

			.no-stack.two-up .col {
				width: 50% !important;
			}

			.no-stack .col.num4 {
				width: 33% !important;
			}

			.no-stack .col.num8 {
				width: 66% !important;
			}

			.no-stack .col.num4 {
				width: 33% !important;
			}

			.no-stack .col.num3 {
				width: 25% !important;
			}

			.no-stack .col.num6 {
				width: 50% !important;
			}

			.no-stack .col.num9 {
				width: 75% !important;
			}

			.video-block {
				max-width: none !important;
			}

			.mobile_hide {
				min-height: 0px;
				max-height: 0px;
				max-width: 0px;
				display: none;
				overflow: hidden;
				font-size: 0px;
			}

			.desktop_hide {
				display: block !important;
				max-height: none !important;
			}
		}
	</style>
</head>
<body class="clean-body" style="margin: 0; padding: 0; -webkit-text-size-adjust: 100%; background-color: #FFFFFF;">

<table bgcolor="#FFFFFF" cellpadding="0" cellspacing="0" class="nl-container" role="presentation" style="table-layout: fixed; vertical-align: top; min-width: 320px; Margin: 0 auto; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #FFFFFF; width: 100%;" valign="top" width="100%">
<tbody>
<tr style="vertical-align: top;" valign="top">
<td style="word-break: break-word; vertical-align: top;" valign="top">


<div style="background-color:transparent;">
<div class="block-grid" style="Margin: 0 auto; min-width: 320px; max-width: 590px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: transparent;">
<div style="border-collapse: collapse;display: table;width: 100%;background-color:transparent;">
<!--[if (mso)|(IE)]><table width="100%" cellpadding="0" cellspacing="0" border="0" style="background-color:transparent;"><tr><td align="center"><table cellpadding="0" cellspacing="0" border="0" style="width:590px"><tr class="layout-full-width" style="background-color:transparent"><![endif]-->
<!--[if (mso)|(IE)]><td align="center" width="590" style="background-color:transparent;width:590px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;" valign="top"><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;"><![endif]-->
<div class="col num12" style="min-width: 320px; max-width: 590px; display: table-cell; vertical-align: top; width: 590px;">
<div style="width:100% !important;">
<!--[if (!mso)&(!IE)]><!-->
<div style="border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;">
<!--<![endif]-->
<!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 20px; padding-left: 20px; padding-top: 10px; padding-bottom: 10px; font-family: Arial, sans-serif"><![endif]-->
<div style="color:#555555;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;line-height:1.2;padding-top:10px;padding-right:20px;padding-bottom:10px;padding-left:20px;">
<div style="font-size: 14px; line-height: 1.2; color: #555555; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; mso-line-height-alt: 17px;">
<p style="font-size: 26px; line-height: 1.2; word-break: break-word; mso-line-height-alt: 31px; margin: 0;"><span style="font-size: 26px;"><strong>' . $subject . '</strong></span></p>
</div>
</div>
<!--[if mso]></td></tr></table><![endif]-->
<!--[if (!mso)&(!IE)]><!-->
</div>
<!--<![endif]-->
</div>
</div>
<!--[if (mso)|(IE)]></td></tr></table><![endif]-->
<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->
</div>
</div>
</div>
<div style="background-color:transparent;">
<div class="block-grid" style="Margin: 0 auto; min-width: 320px; max-width: 590px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: transparent;">
<div style="border-collapse: collapse;display: table;width: 100%;background-color:transparent;">
<!--[if (mso)|(IE)]><table width="100%" cellpadding="0" cellspacing="0" border="0" style="background-color:transparent;"><tr><td align="center"><table cellpadding="0" cellspacing="0" border="0" style="width:590px"><tr class="layout-full-width" style="background-color:transparent"><![endif]-->
<!--[if (mso)|(IE)]><td align="center" width="590" style="background-color:transparent;width:590px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;" valign="top"><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;"><![endif]-->
<div class="col num12" style="min-width: 320px; max-width: 590px; display: table-cell; vertical-align: top; width: 590px;">
<div style="width:100% !important;">
<!--[if (!mso)&(!IE)]><!-->
<div style="border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;">
<!--<![endif]-->
<table border="0" cellpadding="0" cellspacing="0" class="divider" role="presentation" style="table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;" valign="top" width="100%">
<tbody>
<tr style="vertical-align: top;" valign="top">
<td class="divider_inner" style="word-break: break-word; vertical-align: top; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding-top: 10px; padding-right: 20px; padding-bottom: 10px; padding-left: 20px;" valign="top">
<table align="left" border="0" cellpadding="0" cellspacing="0" class="divider_content" role="presentation" style="table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-top: 5px solid #02AC43; width: 50%;" valign="top" width="50%">
<tbody>
<tr style="vertical-align: top;" valign="top">
<td style="word-break: break-word; vertical-align: top; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;" valign="top"><span></span></td>
</tr>
</tbody>
</table>
</td>
</tr>
</tbody>
</table>
<!--[if (!mso)&(!IE)]><!-->
</div>
<!--<![endif]-->
</div>
</div>
<!--[if (mso)|(IE)]></td></tr></table><![endif]-->
<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->
</div>
</div>
</div>
<div style="background-color:transparent;">
<div class="block-grid" style="Margin: 0 auto; min-width: 320px; max-width: 590px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: transparent;">
<div style="border-collapse: collapse;display: table;width: 100%;background-color:transparent;">


<div class="col num12" style="min-width: 320px; max-width: 590px; display: table-cell; vertical-align: top; width: 590px;">
<div style="width:100% !important;">

<div style="border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;">


<div style="color:#555555;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;line-height:1.2;padding-top:10px;padding-right:20px;padding-bottom:10px;padding-left:20px;">

<div style="font-size: 14px; line-height: 1.2; color: #555555; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; mso-line-height-alt: 17px;">
<p style="font-size: 14px; line-height: 1.2; word-break: break-word; text-align: justify; mso-line-height-alt: 17px; margin: 0;">' . $text1 . '</p>
</div><div style="font-size: 14px; line-height: 1.2; color: #555555; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; mso-line-height-alt: 17px;">

</div>
<div style="background-color:transparent;">
<div class="block-grid" style="Margin: 0 auto; min-width: 320px; max-width: 590px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: transparent;">
<div style="border-collapse: collapse;display: table;width: 100%;background-color:transparent;">
<!--[if (mso)|(IE)]><table width="100%" cellpadding="0" cellspacing="0" border="0" style="background-color:transparent;"><tr><td align="center"><table cellpadding="0" cellspacing="0" border="0" style="width:590px"><tr class="layout-full-width" style="background-color:transparent"><![endif]-->
<!--[if (mso)|(IE)]><td align="center" width="590" style="background-color:transparent;width:590px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;" valign="top"><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;"><![endif]-->
<div class="col num12" style="min-width: 320px; max-width: 590px; display: table-cell; vertical-align: top; width: 590px;">
<div style="width:100% !important;">
<!--[if (!mso)&(!IE)]><!-->
<div style="border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;">
<!--<![endif]-->
<div align="center" class="button-container" style="padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;">
<!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0" style="border-spacing: 0; border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;"><tr><td style="padding-top: 10px; padding-right: 10px; padding-bottom: 10px; padding-left: 10px" align="center"><v:roundrect xmlns:v="urn:schemas-microsoft-com:vml" xmlns:w="urn:schemas-microsoft-com:office:word" href="" style="height:31.5pt; width:192pt; v-text-anchor:middle;" arcsize="10%" stroke="false" fillcolor="#02AC43"><w:anchorlock/><v:textbox inset="0,0,0,0"><center style="color:#ffffff; font-family:Arial, sans-serif; font-size:16px"><![endif]-->
<a href="' . $linkbtn . '" rel="noopener" style="color: #ffffff" target="_blank"><div style="text-decoration:none;display:inline-block;color:#ffffff;background-color:#02AC43;border-radius:4px;-webkit-border-radius:4px;-moz-border-radius:4px;width:auto; width:auto;;border-top:1px solid #02AC43;border-right:1px solid #02AC43;border-bottom:1px solid #02AC43;border-left:1px solid #02AC43;padding-top:5px;padding-bottom:5px;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;text-align:center;mso-border-alt:none;word-break:keep-all;"><span style="padding-left:20px;padding-right:20px;font-size:16px;display:inline-block;"><span style="font-size: 16px; line-height: 2; word-break: break-word; mso-line-height-alt: 32px;"><strong> Reset Password </strong></span></span></div></a>
<!--[if mso]></center></v:textbox></v:roundrect></td></tr></table><![endif]-->
</div>
<!--[if (!mso)&(!IE)]><!-->
</div>
<!--<![endif]-->
</div>
</div>
<!--[if (mso)|(IE)]></td></tr></table><![endif]-->
<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->
</div>
</div>
</div>
</div>


</div>

</div>
</div>


</div>
</div>
</div>
<div style="background-color:transparent;">
<div class="block-grid" style="Margin: 0 auto; min-width: 320px; max-width: 590px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: transparent;">
<div style="border-collapse: collapse;display: table;width: 100%;background-color:transparent;">
<!--[if (mso)|(IE)]><table width="100%" cellpadding="0" cellspacing="0" border="0" style="background-color:transparent;"><tr><td align="center"><table cellpadding="0" cellspacing="0" border="0" style="width:590px"><tr class="layout-full-width" style="background-color:transparent"><![endif]-->
<!--[if (mso)|(IE)]><td align="center" width="590" style="background-color:transparent;width:590px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;" valign="top"><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;"><![endif]-->
<div class="col num12" style="min-width: 320px; max-width: 590px; display: table-cell; vertical-align: top; width: 590px;">
<div style="width:100% !important;">
<!--[if (!mso)&(!IE)]><!-->
<div style="border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;">
<!--<![endif]-->
</div>
<!--[if (!mso)&(!IE)]><!-->
</div>
<!--<![endif]-->
</div>
</div>
<!--[if (mso)|(IE)]></td></tr></table><![endif]-->
<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->
</div>
</div>

<div style="background-color:transparent;">
<div class="block-grid" style="Margin: 0 auto; min-width: 320px; max-width: 590px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: transparent;">
<div style="border-collapse: collapse;display: table;width: 100%;background-color:transparent;">


<div class="col num12" style="min-width: 320px; max-width: 590px; display: table-cell; vertical-align: top; width: 590px;">
<div style="width:100% !important;">

<div style="border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;">


<div style="color:#555555;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;line-height:1.2;padding-top:10px;padding-right:20px;padding-bottom:10px;padding-left:20px;">
<div style="font-size: 14px; line-height: 1.2; color: #555555; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; mso-line-height-alt: 17px;">
<p style="font-size: 14px; line-height: 1.2; word-break: break-word; mso-line-height-alt: 17px; margin: 0;">' . $text2 . ' .</p>
</div>
</div>


</div>

</div>
</div>


</div>
</div>
</div>
<div style="background-color:transparent;">
<div class="block-grid two-up" style="Margin: 0 auto; min-width: 320px; max-width: 590px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: transparent;">
<div style="border-collapse: collapse;display: table;width: 100%;background-color:transparent;">


<div class="col num6" style="max-width: 320px; min-width: 295px; display: table-cell; vertical-align: top; width: 295px;">
<div style="width:100% !important;">

<div style="border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;">


<div style="color:#555555;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;line-height:1.2;padding-top:10px;padding-right:20px;padding-bottom:10px;padding-left:20px;">
<div style="font-size: 14px; line-height: 1.2; color: #555555; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; mso-line-height-alt: 17px;">
<p style="line-height: 1.2; word-break: break-word; mso-line-height-alt: NaNpx; margin: 0;">' . $address . '</p>
</div>
</div>





</div>

</div>
</div>


<div class="col num6" style="max-width: 320px; min-width: 295px; display: table-cell; vertical-align: top; width: 295px;">
<div style="width:100% !important;">

<div style="border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;">

<div align="center" class="img-container center fixedwidth" style="padding-right: 10px;padding-left: 10px;">
<!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr style="line-height:0px"><td style="padding-right: 10px;padding-left: 10px;" align="center"><![endif]--><a href="' . $linkgoogle . '"><img align="center" alt="Image" border="0" class="center fixedwidth" src="http://ourdevelops.com/fileenvato/googleplay.png" style="text-decoration: none; -ms-interpolation-mode: bicubic; border: 0; height: auto; width: 100%; max-width: 206px; display: block;" title="Image" width="206" data-iml="5150976.220000001"></a><a>
<!--[if mso]></td></tr></table><![endif]-->
</a></div></div><a>
<!--<![endif]-->
</a></div><a>
</a></div><a>
<!--[if (mso)|(IE)]></td></tr></table><![endif]-->
<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->
</a></div><a>
</a></div><a>
</a></div><a>
</a><div style="background-color:transparent;"><a>
</a><div class="block-grid" style="Margin: 0 auto; min-width: 320px; max-width: 590px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: transparent;"><a>
</a><div style="border-collapse: collapse;display: table;width: 100%;background-color:transparent;"><a>
<!--[if (mso)|(IE)]><table width="100%" cellpadding="0" cellspacing="0" border="0" style="background-color:transparent;"><tr><td align="center"><table cellpadding="0" cellspacing="0" border="0" style="width:590px"><tr class="layout-full-width" style="background-color:transparent"><![endif]-->
<!--[if (mso)|(IE)]><td align="center" width="590" style="background-color:transparent;width:590px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;" valign="top"><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;"><![endif]-->
</a><div class="col num12" style="min-width: 320px; max-width: 590px; display: table-cell; vertical-align: top; width: 590px;"><a>
</a><div style="width:100% !important;"><a>
<!--[if (!mso)&(!IE)]><!-->
</a><div style="border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;"><a>
<!--<![endif]-->
<!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 20px; padding-left: 20px; padding-top: 10px; padding-bottom: 10px; font-family: Arial, sans-serif"><![endif]-->
</a><div style="color:#999999;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;line-height:1.2;padding-top:10px;padding-right:20px;padding-bottom:10px;padding-left:20px;"><a>
</a><div style="font-size: 14px; line-height: 1.2; color: #999999; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; mso-line-height-alt: 17px;"><a>
</a><p style="font-size: 14px; line-height: 1.2; word-break: break-word; text-align: center; mso-line-height-alt: 17px; margin: 0;"><a>Copyright Â© 2020 </a><a href="' . $web . '" rel="noopener" style="text-decoration: underline; color: #02AC43;" target="_blank">' . $appname . '</a>. All Rights Reserved</p>
</div><a>
</a></div><a>
<!--[if mso]></td></tr></table><![endif]-->
<!--[if (!mso)&(!IE)]><!-->
</a></div><a>
<!--<![endif]-->
</a></div><a>
</a></div><a>
<!--[if (mso)|(IE)]></td></tr></table><![endif]-->
<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->
</a></div><a>
</a></div><a>
</a></div><a>
<!--[if (mso)|(IE)]></td></tr></table><![endif]-->
</a></td>
</tr>
</tbody>
</table>



</body></html>';
        return $msg;
    }

    public function kategorymerchant()
    {
        $this->db->select('merchant_category.*');
        $this->db->where('merchant_category.category_status = 1');
        $this->db->where('service.active = 1');
        $this->db->or_where('merchant_category.service_id = 0');

        $this->db->join('service', 'merchant_category.service_id = service.service_id', 'left');
        return $this->db->get('merchant_category');
    }

    public function merchantnearby($long, $lat)
    {
        $this->db->select("merchant.merchant_id , merchant.merchant_name , merchant.merchant_address , merchant.merchant_latitude , merchant.merchant_longitude , merchant.open_hour , merchant.close_hour ,
            merchant.merchant_desc , merchant.merchant_category , merchant.merchant_image , merchant.merchant_telephone_number , merchant.merchant_status , merchant.merchant_open_status, balance.balance,
            (6371 * acos(cos(radians($lat)) * cos(radians(merchant.merchant_latitude)) * cos(radians(merchant.merchant_longitude) - radians( $long)) + sin(radians($lat)) * sin( radians(merchant.merchant_latitude)))) AS distance
            ,(SELECT item.promo_status FROM item where merchant.merchant_id = item.merchant_id AND item_status = 1  ORDER BY promo_status DESC LIMIT 1) AS promo_status, service.minimum_distance, service.minimum_wallet");

        $this->db->from('merchant');
        $this->db->where('merchant.merchant_status = 1');
        $this->db->where('partner.partner_status = 1');
        $this->db->where('balance.balance >= service.minimum_wallet');
        $this->db->having("promo_status is not null");
        $this->db->having("distance <= service.minimum_distance");
        $this->db->order_by('distance ASC');

        $this->db->join('service', 'merchant.service_id = service.service_id');
        $this->db->join('partner', 'merchant.merchant_id = partner.merchant_id');
        $this->db->join('balance', 'partner.partner_id = balance.id_user');

        $this->db->limit('4');
        $query =  $this->db->get();

        $data = [];


        foreach ($query->result_array() as $q) {

            if ($q['promo_status'] == NULL) {
                $status = 0;
            } else {
                $status = $q['promo_status'];
            }
            $data[] = [
                'merchant_id'               => $q['merchant_id'],
                'merchant_name'             => $q['merchant_name'],
                'merchant_address'           => $q['merchant_address'],
                'merchant_latitude'         => $q['merchant_latitude'],
                'merchant_longitude'        => $q['merchant_longitude'],
                'open_hour'                  => $q['open_hour'],
                'close_hour'                 => $q['close_hour'],
                'merchant_desc'        => $q['merchant_desc'],
                'merchant_category'         => $q['merchant_category'],
                'merchant_image'             => $q['merchant_image'],
                'merchant_telephone_number'          => $q['merchant_telephone_number'],
                'merchant_status'           => $q['merchant_status'],
                'merchant_open_status'             => $q['merchant_open_status'],
                'balance'                     => $q['balance'],
                'distance'                  => $q['distance'],
                'promo_status'              => $status
            ];
        }

        return $data;
    }

    public function merchantpromo($long, $lat)
    {
        $this->db->select("merchant.merchant_id , merchant.merchant_name , merchant.merchant_address , merchant.merchant_latitude , merchant.merchant_longitude , merchant.open_hour , merchant.close_hour ,
            merchant.merchant_desc , merchant.merchant_category , merchant.merchant_image , merchant.merchant_telephone_number , merchant.merchant_status , merchant.merchant_open_status,balance.balance,
            (6371 * acos(cos(radians($lat)) * cos(radians(merchant.merchant_latitude)) * cos(radians(merchant.merchant_longitude) - radians( $long)) + sin(radians($lat)) * sin( radians(merchant.merchant_latitude)))) AS distance
            ,(SELECT item.promo_status FROM item WHERE merchant.merchant_id = item.merchant_id AND item.promo_status = 1 AND item_status = 1 ORDER BY promo_status DESC LIMIT 1) AS promo_status, service.minimum_distance, service.minimum_wallet");

        $this->db->where('merchant.merchant_status = 1');
        $this->db->where('partner.partner_status = 1');
        $this->db->where('balance.balance >= service.minimum_wallet');


        $this->db->having("promo_status is not null");
        $this->db->having("distance <= service.minimum_distance");
        $this->db->order_by('distance ASC');

        $this->db->join('service', 'merchant.service_id = service.service_id');
        $this->db->join('partner', 'merchant.merchant_id = partner.merchant_id', 'left');
        $this->db->join('balance', 'partner.partner_id = balance.id_user', 'left');

        $this->db->limit('4');
        return $this->db->get('merchant');
    }

    public function merchantbykategori($category, $long, $lat)
    {
        $this->db->select("merchant.merchant_id , merchant.merchant_name , merchant.merchant_address , merchant.merchant_latitude , merchant.merchant_longitude , merchant.open_hour , merchant.close_hour ,
            merchant.merchant_desc , merchant.merchant_category , merchant.merchant_image , merchant.merchant_telephone_number , merchant.merchant_status , merchant.merchant_open_status,balance.balance,
            (6371 * acos(cos(radians($lat)) * cos(radians(merchant.merchant_latitude)) * cos(radians(merchant.merchant_longitude) - radians( $long)) + sin(radians($lat)) * sin( radians(merchant.merchant_latitude)))) AS distance
            ,(SELECT item.promo_status FROM item where merchant.merchant_id = item.merchant_id AND item_status = 1 ORDER BY promo_status DESC LIMIT 1) AS promo_status, service.minimum_distance, service.minimum_wallet");
        $this->db->select('merchant_category.category_name');


        $this->db->where('merchant.merchant_status = 1');
        $this->db->where('partner.partner_status = 1');
        $this->db->where('balance.balance >= service.minimum_wallet');
        if ($category != '1') {
            $this->db->where("merchant.merchant_category = $category");
        }

        $this->db->having("promo_status is not null");
        $this->db->having("distance <= service.minimum_distance");
        $this->db->order_by('distance ASC');

        $this->db->join('service', 'merchant.service_id = service.service_id');
        $this->db->join('partner', 'partner.merchant_id = merchant.merchant_id', 'left');
        $this->db->join('balance', 'partner.partner_id = balance.id_user', 'left');
        $this->db->join('merchant_category', 'merchant.merchant_category = merchant_category.category_merchant_id', 'left');

        $this->db->limit('4');
        return $this->db->get('merchant');
    }

    public function merchantbykategoripromo($category, $long, $lat)
    {
        $this->db->select("merchant.merchant_id , merchant.merchant_name , merchant.merchant_address , merchant.merchant_latitude , merchant.merchant_longitude , merchant.open_hour , merchant.close_hour ,
            merchant.merchant_desc , merchant.merchant_category , merchant.merchant_image , merchant.merchant_telephone_number , merchant.merchant_status , merchant.merchant_open_status,balance.balance,
            (6371 * acos(cos(radians($lat)) * cos(radians(merchant.merchant_latitude)) * cos(radians(merchant.merchant_longitude) - radians( $long)) + sin(radians($lat)) * sin( radians(merchant.merchant_latitude)))) AS distance
            ,(SELECT item.promo_status FROM item where merchant.merchant_id = item.merchant_id AND item.promo_status = 1 AND item_status = 1 ORDER BY promo_status DESC LIMIT 1) AS promo_status, service.minimum_distance, service.minimum_wallet");
        $this->db->select('merchant_category.category_name');


        $this->db->where('merchant.merchant_status = 1');
        $this->db->where('partner.partner_status = 1');
        $this->db->where('balance.balance >= service.minimum_wallet');
        if ($category != '1') {
            $this->db->where("merchant.merchant_category = $category");
        }

        $this->db->having("promo_status is not null");
        $this->db->having("distance <= service.minimum_distance");
        $this->db->order_by('distance ASC');

        $this->db->join('service', 'merchant.service_id = service.service_id');
        $this->db->join('partner', 'partner.merchant_id = merchant.merchant_id', 'left');
        $this->db->join('balance', 'partner.partner_id = balance.id_user', 'left');
        $this->db->join('merchant_category', 'merchant.merchant_category = merchant_category.category_merchant_id', 'left');

        $this->db->limit('4');
        return $this->db->get('merchant');
    }

    public function allmerchantnearby($long, $lat, $service)
    {
        $this->db->select("merchant.merchant_id , merchant.merchant_name , merchant.merchant_address , merchant.merchant_latitude , merchant.merchant_longitude , merchant.open_hour , merchant.close_hour ,
            merchant.merchant_desc , merchant.merchant_category , merchant.merchant_image , merchant.merchant_telephone_number , merchant.merchant_status , merchant.merchant_open_status,balance.balance,
            (6371 * acos(cos(radians($lat)) * cos(radians(merchant.merchant_latitude)) * cos(radians(merchant.merchant_longitude) - radians( $long)) + sin(radians($lat)) * sin( radians(merchant.merchant_latitude)))) AS distance
            ,(SELECT item.promo_status FROM item where merchant.merchant_id = item.merchant_id AND item_status = 1 ORDER BY promo_status DESC LIMIT 1) AS promo_status, service.minimum_distance, service.minimum_wallet");
        $this->db->select('merchant_category.category_name');


        $this->db->where('merchant.merchant_status = 1');
        $this->db->where('partner.partner_status = 1');
        $this->db->where("merchant.service_id = $service");
        $this->db->where('balance.balance >= service.minimum_wallet');

        $this->db->having("promo_status is not null");
        $this->db->having("distance <= service.minimum_distance");
        $this->db->order_by('distance ASC');

        $this->db->join('service', 'merchant.service_id = service.service_id', 'left');
        $this->db->join('partner', 'partner.merchant_id = merchant.merchant_id', 'left');
        $this->db->join('balance', 'partner.partner_id = balance.id_user', 'left');
        $this->db->join('merchant_category', 'merchant.merchant_category = merchant_category.category_merchant_id', 'left');


        return $this->db->get('merchant');
    }

    public function allmerchantnearbybykategori($long, $lat, $service, $category)
    {
        $this->db->select("merchant.merchant_id , merchant.merchant_name , merchant.merchant_address , merchant.merchant_latitude , merchant.merchant_longitude , merchant.open_hour , merchant.close_hour ,
            merchant.merchant_desc , merchant.merchant_category , merchant.merchant_image , merchant.merchant_telephone_number , merchant.merchant_status , merchant.merchant_open_status,balance.balance,
            (6371 * acos(cos(radians($lat)) * cos(radians(merchant.merchant_latitude)) * cos(radians(merchant.merchant_longitude) - radians( $long)) + sin(radians($lat)) * sin( radians(merchant.merchant_latitude)))) AS distance
            ,(SELECT item.promo_status FROM item where merchant.merchant_id = item.merchant_id AND item_status = 1 ORDER BY promo_status DESC LIMIT 1) AS promo_status, service.minimum_distance, service.minimum_wallet");
        $this->db->select('merchant_category.category_name');


        $this->db->where('merchant.merchant_status = 1');
        $this->db->where('partner.partner_status = 1');
        $this->db->where("merchant.service_id = $service");

        $this->db->where('balance.balance >= service.minimum_wallet');
        if ($category != '1') {
            $this->db->where("merchant.merchant_category = $category");
        }

        $this->db->having("promo_status is not null");
        $this->db->having("distance <= service.minimum_distance");
        $this->db->order_by('distance ASC');
        $this->db->join('service', 'merchant.service_id = service.service_id', 'left');
        $this->db->join('partner', 'partner.merchant_id = merchant.merchant_id', 'left');
        $this->db->join('balance', 'partner.partner_id = balance.id_user', 'left');
        $this->db->join('merchant_category', 'merchant.merchant_category = merchant_category.category_merchant_id', 'left');


        return $this->db->get('merchant');
    }


    public function kategorymerchantbyfitur($service)
    {

        $this->db->where("service_id = $service OR service_id = 0");
        $this->db->where('category_status = 1');
        return $this->db->get('merchant_category');
    }

    public function searchmerchantnearby($like, $long, $lat, $service)
    {
        $this->db->select("merchant.merchant_id ,
         merchant.merchant_name , 
         merchant.merchant_address , 
         merchant.merchant_latitude , 
         merchant.merchant_longitude , 
         merchant.open_hour , merchant.close_hour ,
        merchant.merchant_desc , merchant.merchant_category , 
        merchant.merchant_image , merchant.merchant_telephone_number , 
        merchant.merchant_status , merchant.merchant_open_status,
        balance.balance,
        service.service,
        merchant_category.category_name,
        merchant.service_id,
        (6371 * acos(cos(radians($lat)) * cos(radians(merchant.merchant_latitude)) * 
        cos(radians(merchant.merchant_longitude) - radians( $long)) + sin(radians($lat)) * 
        sin( radians(merchant.merchant_latitude)))) AS distance
        ,(SELECT item.promo_status FROM item where merchant.merchant_id = item.merchant_id AND item_status = 1 ORDER BY promo_status DESC LIMIT 1) AS promo_status, service.minimum_distance, service.minimum_wallet");

        $this->db->from('merchant');

        $this->db->join('service', 'merchant.service_id = service.service_id', 'left');
        $this->db->join('partner', 'partner.merchant_id = merchant.merchant_id', 'left');
        $this->db->join('balance', 'partner.partner_id = balance.id_user', 'left');
        $this->db->join('merchant_category', 'merchant.merchant_category = merchant_category.category_merchant_id', 'left');

        $this->db->where("
        balance.balance >= service.minimum_wallet AND merchant.service_id = $service
        ");
        $this->db->where('partner.partner_status = 1');
        $this->db->where('merchant.merchant_status = 1');

        $this->db->group_start();
        $this->db->like('merchant.merchant_name', $like);
        $this->db->or_like('merchant.merchant_address', $like);


        $this->db->group_end();

        $this->db->having("promo_status is not null");
        $this->db->having("distance <= service.minimum_distance");
        $this->db->order_by('distance ASC');

        $query =  $this->db->get();

        $data = [];


        foreach ($query->result_array() as $q) {

            if ($q['promo_status'] == NULL) {
                $status = 0;
            } else {
                $status = $q['promo_status'];
            }
            $data[] = [
                'merchant_id'               => $q['merchant_id'],
                'merchant_name'             => $q['merchant_name'],
                'merchant_address'           => $q['merchant_address'],
                'merchant_latitude'         => $q['merchant_latitude'],
                'merchant_longitude'        => $q['merchant_longitude'],
                'open_hour'                  => $q['open_hour'],
                'close_hour'                 => $q['close_hour'],
                'merchant_desc'        => $q['merchant_desc'],
                'merchant_category'         => $q['merchant_category'],
                'merchant_image'             => $q['merchant_image'],
                'merchant_telephone_number'          => $q['merchant_telephone_number'],
                'merchant_status'           => $q['merchant_status'],
                'merchant_open_status'             => $q['merchant_open_status'],
                'balance'                     => $q['balance'],
                'distance'                  => $q['distance'],
                'promo_status'              => $status
            ];
        }

        return $data;
    }

    public function merchantbyid($idmerchant, $long, $lat)
    {

        $this->db->select("
            merchant.merchant_id , 
            merchant.service_id ,
            merchant.merchant_name , 
            merchant.merchant_address , merchant.merchant_latitude , 
            merchant.merchant_longitude , merchant.open_hour , merchant.close_hour ,
            merchant.merchant_desc , 
            merchant.merchant_category , 
            merchant.merchant_image , 
            merchant.merchant_telephone_number , 
            merchant.merchant_status , 
            merchant.merchant_open_status,
            (6371 * acos(cos(radians($lat)) * 
            cos(radians(merchant.merchant_latitude)) * 
            cos(radians(merchant.merchant_longitude) - 
            radians( $long)) + 
            sin(radians($lat)) * 
            sin( radians(merchant.merchant_latitude)))) AS distance
        ");
        $this->db->select('
            item.promo_status,
            item.item_name,
            item.item_price,
            item.promo_price,
            item.item_category,
            item.item_desc');


        $this->db->select('partner.partner');
        $this->db->select('merchant_category.category_name');
        $this->db->select('service.service, service.minimum_distance, service.minimum_wallet');
        $this->db->select('balance.balance');
        $this->db->where("merchant.merchant_id = $idmerchant");
        $this->db->where('merchant.merchant_status = 1');
        $this->db->where('balance.balance >= service.minimum_wallet');

        $this->db->join('service', 'merchant.service_id = service.service_id', 'left');
        $this->db->join('partner', 'partner.merchant_id = merchant.merchant_id', 'left');
        $this->db->join('balance', 'partner.partner_id = balance.id_user', 'left');
        $this->db->join('merchant_category', 'merchant.merchant_category = merchant_category.category_merchant_id', 'left');
        $this->db->join('item', 'merchant.merchant_id = item.merchant_id', 'left');

        $this->db->order_by('merchant.merchant_id');

        return $this->db->get('merchant');
    }

    public function itemstatus($idmerchant)
    {
        $this->db->select('item.promo_status');
        $this->db->where("item.item_status = 1");
        $this->db->where("item.promo_status = 1");
        $this->db->where("item.merchant_id = $idmerchant");
        return $this->db->get('item');
    }

    public function itembyid($idmerchant)
    {
        $this->db->select('item.*');
        $this->db->select('
            category_item.category_name_item,
            category_item.category_item_images');

        $this->db->where("item.item_status = 1");
        $this->db->where("item.merchant_id = $idmerchant");

        $this->db->join('category_item', 'item.item_category = category_item.category_item_id', 'left');
        $this->db->where("category_item.category_status = 1");

        return $this->db->get('item');
    }

    public function kategoriitem($idmerchant)
    {

        $this->db->select("category_name_item,category_item_id");
        $this->db->where("category_status = 1");
        $this->db->where("merchant_id = $idmerchant");
        $this->db->or_where('all_category = 1');
        return $this->db->get('category_item');
    }


    public function itembykategori($idmerchant, $itemk)
    {
        $this->db->select("item.*");
        $this->db->select('
            category_item.category_name_item,
            category_item.category_item_images');

        $this->db->where("item.item_status = 1");
        $this->db->where("item.merchant_id = $idmerchant");

        if ($itemk != '1') {
            $this->db->where("category_item_id = $itemk");
        }
        $this->db->join('category_item', 'item.item_category = category_item.category_item_id', 'left');
        $this->db->where("category_item.category_status = 1");
        return $this->db->get('item');
    }

    public function insert_data_transaksi_merchant($data_transaksi, $dataDetail, $total_belanja)
    {

        $final_cost = ($data_transaksi['price'] + $total_belanja['total_belanja']) - $data_transaksi['promo_discount'];

        $transaction = [
            'customer_id'      => $data_transaksi['customer_id'],
            'service_order'       => $data_transaksi['service_order'],
            'start_latitude'    => $data_transaksi['start_latitude'],
            'start_longitude'   => $data_transaksi['start_longitude'],
            'end_latitude'      => $data_transaksi['end_latitude'],
            'end_longitude'     => $data_transaksi['end_longitude'],
            'distance'             => $data_transaksi['distance'],
            'price'             => $data_transaksi['price'],
            'order_time'       => $data_transaksi['order_time'],
            'estimate_time'     => $data_transaksi['estimate_time'],
            'pickup_address'       => $data_transaksi['pickup_address'],
            'destination_address'     => $data_transaksi['destination_address'],
            'promo_discount'      => $data_transaksi['promo_discount'],
            'wallet_payment'      => $data_transaksi['wallet_payment'],
            'final_cost'      => $final_cost,
        ];

        $this->db->insert('transaction', $transaction);
        $reqid = $this->db->insert_id();
        if ($this->db->affected_rows() == 1) {

            $data_hist = array(
                'transaction_id' => $reqid,
                'driver_id' => 'D0',
                'status' => '1'
            );
            $this->db->insert('transaction_history', $data_hist);

            $dataDetail['transaction_id'] = $reqid;
            $this->db->insert('merchant_detail_transaction', $dataDetail);

            $get_data = $this->get_data_transaksi_merchant($data_transaksi);
            return array(
                'status'        => true,
                'transaction_id'  => $reqid,
                'data' => $get_data->result(),

            );
        } else {
            return array(
                'status' => false,
                'data' => []
            );
        }
    }

    public function diskon_by_fitur($service)
    {
        $this->db->select('voucher_discount');
        $this->db->from('voucher');
        $this->db->where("voucher_service = $service");
        $this->db->order_by('id', 'asc');
        return $this->db->get();
    }

    public function insert_data_item($item)
    {
        foreach ($item as $it) {
            $this->db->insert('item_transaction', $it);
        }


        if ($this->db->affected_rows() == 1) {
            return array(
                'status'        => true,

            );
        } else {
            return array(
                'status' => false
            );
        }
    }

    public function detail_item($id)
    {
        $this->db->select('item_transaction.item_amount,item.item_name, item_transaction.item_note, item_transaction.total_cost');
        $this->db->from('item_transaction');
        $this->db->join('item', 'item_transaction.item_id = item.item_id');
        $this->db->where('transaction_id', $id);
        return $this->db->get();
    }

    public function delete_transaksi($id)
    {
        $data = [
            'status' => '0'
        ];

        $this->db->where('transaction_id', $id);
        $this->db->update('transaction_history', $data);
        return true;
    }
}
