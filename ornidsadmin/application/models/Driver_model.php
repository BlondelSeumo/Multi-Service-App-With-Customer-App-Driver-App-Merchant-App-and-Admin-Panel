<?php

defined('BASEPATH') or exit('No direct script access allowed');

class Driver_model extends CI_model
{

    function __construct()
    {
        parent::__construct();
    }

    public function signup($data_signup, $data_kendaraan, $data_berkas)
    {
        $inskendaraan = $this->db->insert('vehicle', $data_kendaraan);
        $inserid = $this->db->insert_id();
        $datasignup = array(
            'id' => $data_signup['id'],
            'driver_name' => $data_signup['driver_name'],
            'user_nationid' => $data_signup['user_nationid'],
            'dob' => $data_signup['dob'],
            'phone_number' => $data_signup['phone_number'],
            'phone' => $data_signup['phone'],
            'email' => $data_signup['email'],
            'countrycode' => $data_signup['countrycode'],
            'photo' => $data_signup['photo'],
            'password' => $data_signup['password'],
            'job' => $data_signup['job'],
            'gender' => $data_signup['gender'],
            'driver_address' => $data_signup['driver_address'],
            'reg_id' => '12345',
            'vehicle' => $inserid,
            'status' => '0'
        );
        $signup = $this->db->insert('driver', $datasignup);




        $dataconfig = array(
            'driver_id' => $data_signup['id'],
            'latitude' => '0',
            'longitude' => '0',
            'status' => '5'
        );
        $insconfig = $this->db->insert('config_driver', $dataconfig);

        $databerkas = array(
            'driver_id' => $data_signup['id'],
            'idcard_images' => $data_berkas['idcard_images'],
            'driver_license_images' => $data_berkas['driver_license_images'],
            'driver_license_id' => $data_berkas['driver_license_id']
        );
        $insberkas = $this->db->insert('file_driver', $databerkas);

        $datasaldo = array(
            'id_user' => $data_signup['id'],
            'balance' => 0
        );
        $insSaldo = $this->db->insert('balance', $datasaldo);
        return $signup;
    }

    public function check_exist($email, $phone)
    {
        $cek = $this->db->query("SELECT id FROM driver where email='$email' AND phone_number='$phone'");
        if ($cek->num_rows() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public function check_ktp($ktp)
    {
        $cek = $this->db->query("SELECT id FROM driver where user_nationid='$ktp'");
        if ($cek->num_rows() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public function check_sim($sim)
    {
        $cek = $this->db->query("SELECT file_id FROM file_driver where driver_license_id='$sim'");
        if ($cek->num_rows() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public function check_exist_phone($phone)
    {
        $cek = $this->db->query("SELECT id FROM driver where phone_number='$phone'");
        if ($cek->num_rows() == 1) {
            return true;
        } else {
            return false;
        }
    }
    public function check_exist_email($email)
    {
        $cek = $this->db->query("SELECT id FROM driver where email='$email'");
        if ($cek->num_rows() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public function check_banned($phone)
    {
        $stat =  $this->db->query("SELECT id FROM driver WHERE status='3' AND phone_number='$phone'");
        if ($stat->num_rows() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public function check_exist_phone_edit($id, $phone)
    {
        $cek = $this->db->query("SELECT phone_number FROM driver where phone_number='$phone' AND id!='$id'");
        if ($cek->num_rows() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public function check_exist_email_edit($id, $email)
    {
        $cek = $this->db->query("SELECT id FROM driver where email='$email' AND id!='$id'");
        if ($cek->num_rows() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public function get_data_pelanggan($condition)
    {
        $this->db->select('driver.*, balance.balance,vehicle.*');
        $this->db->from('driver');
        $this->db->join('balance', 'driver.id = balance.id_user');
        $this->db->join('vehicle', 'driver.vehicle = vehicle.vehicle_id');
        $this->db->where($condition);
        $this->db->where('status', '1');
        return $this->db->get();
    }

    public function get_job()
    {
        $this->db->select('*');
        $this->db->from('driver_job');
        $this->db->where('status_job', '1');
        return $this->db->get()->result();
    }



    public function get_status_driver($condition)
    {
        $this->db->select('*');
        $this->db->from('config_driver');
        $this->db->where($condition);
        return $this->db->get();
    }

    public function edit_profile($data, $phone)
    {

        $this->db->where('phone_number', $phone);
        $this->db->update('driver', $data);
        return true;
    }

    public function edit_status_login($phone)
    {
        $phonenumber = array('phone_number' => $phone);
        $datadriver = $this->get_data_driver($phonenumber);
        $datas = array('status' => '4');
        $this->db->where('driver_id', $datadriver->row('id'));
        $this->db->update('config_driver', $datas);
        return true;
    }

    public function logout($dataEdit, $id)
    {

        $this->db->where('driver_id', $id);
        $logout = $this->db->update('config_driver', $dataEdit);
        if ($this->db->affected_rows() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public function edit_kendaraan($data, $id)
    {

        $this->db->where('vehicle_id', $id);
        $this->db->update('vehicle', $data);
        return true;
    }

    function my_location($data_l)
    {
        if ($data_l['bearing'] != 'NaN') {
            $data = array(
                'latitude' => $data_l['latitude'],
                'longitude' => $data_l['longitude'],
                'bearing' => $data_l['bearing']
            );
        } else {
            $data = array(
                'latitude' => $data_l['latitude'],
                'longitude' => $data_l['longitude']
            );
        }
        $this->db->where('driver_id', $data_l['driver_id']);
        $upd = $this->db->update('config_driver', $data);
        if ($this->db->affected_rows() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public function get_data_driver($condition)
    {
        $this->db->select('driver.*, balance.balance');
        $this->db->from('driver');
        $this->db->join('config_driver', 'driver.id = config_driver.driver_id');
        $this->db->join('balance', 'driver.id = balance.id_user');
        $this->db->where($condition);
        return $this->db->get();
    }

    function change_status_driver($idD, $stat_order)
    {


        $params = array(
            'status' => $stat_order
        );
        $this->db->where('driver_id', $idD);
        $upd = $this->db->update('config_driver', $params);
        if ($this->db->affected_rows() > 0) {
            return true;
        } else {
            return false;
        }
    }

    function get_data_driver_sync($id)
    {

        $this->db->select(""
            . "driver.*,"
            . "vehicle.*,"
            . "driver.photo as photo,"
            . "balance,"
            . "config_driver.status as status_config");
        $this->db->from('driver');
        $this->db->join('config_driver', 'driver.id = config_driver.driver_id');
        $this->db->join('balance', 'driver.id = balance.id_user');
        $this->db->join('vehicle', 'driver.vehicle = vehicle.vehicle_id');
        $this->db->where('driver.id', $id);
        $dataCon = $this->db->get();
        return array(
            'data_driver' => $dataCon,
            'status_order' => $this->check_status_order($id)
        );
    }

    function check_status_order($idDriver)
    {
        $this->db->select(''
            . 'transaction.*,'
            . 'detail_send_transaction.*,'
            . 'transaction_history.*,'
            . 'customer.customer_fullname,'
            . 'customer.phone_number as telepon,'
            . 'customer.token as reg_id_pelanggan');
        $this->db->join('transaction', 'transaction.id = transaction_history.transaction_id');
        $this->db->join('customer', 'transaction.customer_id = customer.id');
        $this->db->join('detail_send_transaction', 'transaction.id = detail_send_transaction.transaction_id', 'left');
        $this->db->where("(transaction_history.status = '2' OR transaction_history.status = '3')", NULL, FALSE);
        $this->db->where('transaction_history.driver_id', $idDriver);
        $this->db->order_by('transaction_history.number', 'DESC');
        $check = $this->db->get('transaction_history', 1, 0);
        return $check;
    }

    function edit_config($data, $id)
    {
        $this->db->where('driver_id', $id);
        $edit = $this->db->update('config_driver', $data);
        if ($this->db->affected_rows() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public function accept_request($cond)
    {

        $this->db->where('driver_id', 'D0');
        $this->db->where('transaction_id', $cond['transaction_id']);
        $this->db->where("(status = '1')", NULL, FALSE);
        $this->db->from('transaction_history');
        $cek_once = $this->db->get();
        if ($cek_once->num_rows() > 0) {
            $data = array(
                'status' => '2',
                'driver_id' => $cond['driver_id']
            );
            $this->db->where('driver_id', 'D0');
            $this->db->where('transaction_id', $cond['transaction_id']);
            $edit = $this->db->update('transaction_history', $data);

            if ($this->db->affected_rows() > 0) {
                $this->db->where('id', $cond['transaction_id']);
                $update_trans = $this->db->update('transaction', array('driver_id' => $cond['driver_id']));

                $datD = array(
                    'status' => '2'
                );
                $this->db->where(array('driver_id' => $cond['driver_id']));
                $updDriver = $this->db->update('config_driver', $datD);
                return array(
                    'status' => true,
                    'data' => []
                );
            } else {
                return array(
                    'status' => false,
                    'data' => []
                );
            }
        } else {
            return array(
                'status' => false,
                'data' => 'canceled'
            );
        }
    }

    public function start_request($cond)
    {

        $this->db->where($cond);
        $this->db->where('status', '2');
        $this->db->from('transaction_history');
        $cek_once = $this->db->get();
        if ($cek_once->num_rows() > 0) {
            $data = array(
                'status' => '3',
                'driver_id' => $cond['driver_id']
            );
            $this->db->where($cond);
            $edit = $this->db->update('transaction_history', $data);
            if ($this->db->affected_rows() > 0) {
                $datD = array(
                    'status' => '3'
                );
                $this->db->where(array('driver_id' => $cond['driver_id']));
                $updDriver = $this->db->update('config_driver', $datD);
                return array(
                    'status' => true,
                    'data' => []
                );
            } else {
                return array(
                    'status' => false,
                    'data' => []
                );
            }
        } else {
            $datD = array(
                'status' => '1'
            );
            $this->db->where(array('driver_id' => $cond['driver_id']));

            $updDriver = $this->db->update('config_driver', $datD);
            return array(
                'status' => false,
                'data' => 'canceled'
            );
        }
    }

    public function finish_request($cond, $condtr)
    {
        $this->db->where($condtr);
        $this->db->update('transaction', array('finish_time' => date('Y-m-d H:i:s')));


        if ($this->db->affected_rows() > 0) {
            $last_trans = $this->get_data_last_transaksi($condtr);

            $get_mitra = $this->get_trans_merchant($last_trans->row('transaction_id'));
            $datapelanggan = $this->get_data_pelangganid($last_trans->row('customer_id'));
            $datadriver = $this->get_data_driverid($cond['driver_id']);

            $data_cut = array(
                'driver_id' => $cond['driver_id'],
                'price' => $last_trans->row('price'),
                'final_cost' => $last_trans->row('final_cost'),
                'promo_discount' => $last_trans->row('promo_discount'),
                'transaction_id' => $last_trans->row('transaction_id'),
                'service' => $last_trans->row('service'),
                'service_order' => $last_trans->row('service_order'),
                'driver_name' => $datadriver->row('driver_name'),
                'wallet_payment' => $last_trans->row('wallet_payment')
            );
            $dataC = array(
                'customer_id' => $last_trans->row('customer_id'),
                'price' => $last_trans->row('price'),
                'final_cost' => $last_trans->row('final_cost'),
                'promo_discount' => $last_trans->row('promo_discount'),
                'transaction_id' => $last_trans->row('transaction_id'),
                'wallet_payment' => $last_trans->row('wallet_payment'),
                'service_order' => $last_trans->row('service_order'),
                'nama_pelanggan' => $datapelanggan->row('customer_fullname'),
                'service' => $last_trans->row('service')
            );
            $deletechat = $this->delete_chat($cond['driver_id'], $last_trans->row('customer_id'));
            if ($deletechat) {
            if ($last_trans->row('home') == 4) {

                $data_cut_mitra = array(
                    'partner_id' => $get_mitra->row('partner_id'),
                    'price' => $get_mitra->row('total_price'),
                    'final_cost' => $last_trans->row('final_cost'),
                    'promo_discount' => $last_trans->row('promo_discount'),
                    'transaction_id' => $last_trans->row('transaction_id'),
                    'service' => $last_trans->row('service'),
                    'service_order' => $last_trans->row('service_order'),
                    'partner_name' => $get_mitra->row('partner_name'),
                    'wallet_payment' => $last_trans->row('wallet_payment')
                );
                $this->cut_mitra_saldo_by_order($data_cut_mitra);
                $this->delete_chat($get_mitra->row('merchant_id'), $last_trans->row('customer_id'));
                $this->delete_chat($get_mitra->row('merchant_id'), $cond['driver_id']);
            };

            $cutUser = $this->cut_user_saldo_by_order($dataC);
            $cutting = $this->cut_driver_saldo_by_order($data_cut);


            if ($cutting['status'] && $cutUser) {
                
                $data = array(
                    'status' => '4'
                );
                $this->db->where($cond);
                $this->db->update('transaction_history', $data);
                $datD = array(
                    'status' => '1'
                );
                $this->db->where(array('driver_id' => $cond['driver_id']));
                $this->db->update('config_driver', $datD);
                return array(
                    'status' => true,
                    'data' => $last_trans->result(),
                    'idp' => $last_trans->row('customer_id'),
                );
            } else {
                return array(
                    'status' => false,
                    'data' => 'false in cutting'
                );
            }

        }
        } else {
            return array(
                'status' => false,
                'data' => 'abc'
            );
        }
    
    }

    public function get_data_pelangganid($id)
    {
        $this->db->select('customer.*, balance.balance');
        $this->db->from('customer');
        $this->db->join('balance', 'customer.id = balance.id_user');
        $this->db->where('id', $id);
        return $this->db->get();
    }

    public function get_data_driverid($id)
    {
        $this->db->select('driver.*, balance.balance');
        $this->db->from('driver');
        $this->db->join('balance', 'driver.id = balance.id_user');
        $this->db->where('id', $id);
        return $this->db->get();
    }

    function cut_user_saldo_by_order($dataC)
    {

        $this->db->where('id_user', $dataC['customer_id']);
        $balance = $this->db->get('balance')->row('balance');

        if ($dataC['wallet_payment'] == 1) {
            $data_ins = array(
                'id_user' => $dataC['customer_id'],
                'wallet_amount' => $dataC['final_cost'],
                'bank' => $dataC['service'],
                'holder_name' => $dataC['nama_pelanggan'],
                'wallet_account' => 'wallet',
                'type' => 'Order-'
            );
            $ins_trans = $this->db->insert('wallet', $data_ins);
            if ($ins_trans) {
                $this->db->where('id_user', $dataC['customer_id']);
                return $this->db->update('balance', array('balance' => ($balance - $dataC['final_cost'])));
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    function cut_driver_saldo_by_order($data)
    {
        $this->db->select('commision');
        $this->db->where('service_id', $data['service_order']);
        $persen = $this->db->get('service')->row('commision');

        $this->db->where('id_user', $data['driver_id']);
        $balance = $this->db->get('balance')->row('balance');
        if ($data['wallet_payment'] == 1) {
            $kred = $data['price'];
            $potongan = $kred * ($persen / 100);
            $hasil = $kred - $potongan;

            $data_ins = array(
                'id_user' => $data['driver_id'],
                'wallet_amount' => $hasil,
                'bank' => $data['service'],
                'holder_name' => $data['driver_name'],
                'wallet_account' => 'wallet',
                'type' => 'Order+'
            );
            $ins_trans = $this->db->insert('wallet', $data_ins);
            if ($ins_trans) {
                $this->db->where('id_user', $data['driver_id']);
                $upd = $this->db->update('balance', array('balance' => ($balance + $hasil)));
                if ($this->db->affected_rows() > 0) {
                    return array(
                        'status' => true,
                        'data' => array('balance' => ($balance + $hasil))
                    );
                } else {
                    return array(
                        'status' => false,
                        'data' => 'fail in update'
                    );
                }
            } else {
                return array(
                    'status' => false,
                    'data' => []
                );
            }
        } else {
            $hasil = $data['price'] * ($persen / 100);
            $data_ins = array(
                'id_user' => $data['driver_id'],
                'wallet_amount' => $hasil,
                'bank' => $data['service'],
                'holder_name' => $data['driver_name'],
                'wallet_account' => 'wallet',
                'type' => 'Order-'
            );

            $data_ins_promo = array(
                'id_user' => $data['driver_id'],
                'wallet_amount' => $data['promo_discount'],
                'bank' => $data['service'],
                'holder_name' => $data['driver_name'],
                'wallet_account' => 'wallet',
                'type' => 'Order+'
            );
            $ins_trans = $this->db->insert('wallet', $data_ins);
            if ($ins_trans) {
                $this->db->where('id_user', $data['driver_id']);
                $upd = $this->db->update('balance', array('balance' => ($balance - $hasil)));

                if ($upd) {
                    if ($data['promo_discount'] > 0) {
                        $this->db->where('id_user', $data['driver_id']);
                        $balanceupd = $this->db->get('balance')->row('balance');
                        $this->db->insert('wallet', $data_ins_promo);
                        $this->db->where('id_user', $data['driver_id']);
                        $this->db->update('balance', array('balance' => ($balanceupd + $data['promo_discount'])));
                    }
                    return array(
                        'status' => true,
                        'data' => []
                    );
                } else {
                    return array(
                        'status' => false,
                        'data' => 'fail in update'
                    );
                }
            } else {
                return array(
                    'status' => false,
                    'data' => []
                );
            }
        }
    }

    function cut_mitra_saldo_by_order($data)
    {
        $this->db->select('commision');
        $this->db->where('service_id', $data['service_order']);
        $persen = $this->db->get('service')->row('commision');

        $this->db->where('id_user', $data['partner_id']);
        $balance = $this->db->get('balance')->row('balance');
        if ($data['wallet_payment'] == 1) {
            $kred = $data['price'];
            $potongan = $kred * ($persen / 100);
            $hasil = $kred - $potongan;

            $data_ins = array(
                'id_user' => $data['partner_id'],
                'wallet_amount' => $hasil,
                'bank' => $data['service'],
                'holder_name' => $data['partner_name'],
                'wallet_account' => 'wallet',
                'type' => 'Order+'
            );
            $ins_trans = $this->db->insert('wallet', $data_ins);
            if ($ins_trans) {
                $this->db->where('id_user', $data['partner_id']);
                $upd = $this->db->update('balance', array('balance' => ($balance + $hasil)));
                if ($this->db->affected_rows() > 0) {
                    return array(
                        'status' => true,
                        'data' => array('balance' => ($balance + $hasil))
                    );
                } else {
                    return array(
                        'status' => false,
                        'data' => 'fail in update'
                    );
                }
            } else {
                return array(
                    'status' => false,
                    'data' => []
                );
            }
        } else {
            $hasil = $data['price'] * ($persen / 100);
            $data_ins = array(
                'id_user' => $data['partner_id'],
                'wallet_amount' => $hasil,
                'bank' => $data['service'],
                'holder_name' => $data['partner_name'],
                'wallet_account' => 'wallet',
                'type' => 'Order-'
            );
            $ins_trans = $this->db->insert('wallet', $data_ins);
            if ($ins_trans) {
                $this->db->where('id_user', $data['partner_id']);
                $upd = $this->db->update('balance', array('balance' => ($balance - $hasil)));
                if ($this->db->affected_rows() > 0) {
                    return array(
                        'status' => true,
                        'data' => []
                    );
                } else {
                    return array(
                        'status' => false,
                        'data' => 'fail in update'
                    );
                }
            } else {
                return array(
                    'status' => false,
                    'data' => []
                );
            }
        }
    }

    function get_data_last_transaksi($cond)
    {
        $this->db->select('id as transaction_id,'
            . '(finish_time - order_time) as lama,'
            . 'finish_time,'
            . 'price,'
            . 'final_cost,'
            . 'promo_discount,'
            . 'service_order,'
            . 'customer_id,'
            . 'service.home, service.service,'
            . 'wallet_payment');
        $this->db->from('transaction');
        $this->db->join('service', 'transaction.service_order = service.service_id', 'left');
        $this->db->where($cond);
        $cek = $this->db->get();
        return $cek;
    }



    function all_transaksi($iduser)
    {
        $this->db->select('*');
        $this->db->from('transaction');
        $this->db->join('merchant_detail_transaction', 'transaction.id = merchant_detail_transaction.transaction_id', 'left');
        $this->db->join('transaction_history', 'transaction.id = transaction_history.transaction_id', 'left');
        $this->db->join('transaction_status', 'transaction_history.status = transaction_status.id', 'left');
        $this->db->join('service', 'transaction.service_order = service.service_id', 'left');
        $this->db->where('transaction.driver_id', $iduser);
        $this->db->where('transaction_history.status != 1');
        $this->db->where('transaction_history.status != 2');
        $this->db->where('transaction_history.status != 3');
        $this->db->where('transaction_history.status != 0');
        $this->db->order_by('transaction.id', 'DESC');
        $trans = $this->db->get();
        return $trans;
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

        return $return;
    }


    public function getAlldriver()
    {
        $this->db->select('config_driver.status as status_job');
        $this->db->select('driver_job.driver_job');
        $this->db->select('driver.*');
        $this->db->join('config_driver', 'driver.id = config_driver.driver_id', 'left');
        $this->db->join('driver_job', 'driver.job = driver_job.id', 'left');
        return  $this->db->get('driver')->result_array();
    }

    public function getdriverbyid($id)
    {
        $this->db->select('vehicle.*');
        $this->db->select('balance.balance');
        $this->db->select('config_driver.status as status_job');
        $this->db->select('driver_job.driver_job');
        $this->db->select('file_driver.*');
        $this->db->select('driver.*');
        $this->db->join('vehicle', 'driver.vehicle = vehicle.vehicle_id', 'left');
        $this->db->join('balance', 'driver.id = balance.id_user', 'left');
        $this->db->join('config_driver', 'driver.id = config_driver.driver_id', 'left');
        $this->db->join('driver_job', 'driver.job = driver_job.id', 'left');
        $this->db->join('file_driver', 'driver.id = file_driver.driver_id', 'left');
        return  $this->db->get_where('driver', ['driver.id' => $id])->row_array();
    }

    public function countorder($id)
    {
        $this->db->select('driver_id');
        $query = $this->db->get_where('transaction', ['driver_id' => $id])->result_array();
        return count($query);
    }

    public function wallet($id)
    {
        $this->db->order_by('wallet.id', 'DESC');
        return $this->db->get_where('wallet', ['id_user' => $id])->result_array();
    }

    public function transaction($id)
    {
        $this->db->select('transaction_status.*');
        $this->db->select('transaction_history.*');
        $this->db->select('service.*');
        $this->db->select('transaction.*');
        $this->db->join('transaction_history', 'transaction.id = transaction_history.transaction_id', 'left');
        $this->db->join('transaction_status', 'transaction_history.status = transaction_status.id', 'left');
        $this->db->join('service', 'transaction.service_order = service.service_id', 'left');
        $this->db->order_by('transaction.id', 'DESC');
        $this->db->where('transaction_history.status != 1');
        return $this->db->get_where('transaction', ['transaction.driver_id' => $id])->result_array();
    }

    public function editdataid($data)
    {
        $this->db->set('driver_name', $data['driver_name']);
        $this->db->set('email', $data['email']);
        $this->db->set('countrycode', $data['countrycode']);
        $this->db->set('phone', $data['phone']);
        $this->db->set('phone_number', $data['phone_number']);
        $this->db->set('driver_birthplace', $data['driver_birthplace']);
        $this->db->set('dob', $data['dob']);
        $this->db->set('driver_address', $data['driver_address']);

        $this->db->where('id', $data['id']);
        $this->db->update('driver', $data);
    }

    public function editdatakendaraan($data, $data2)
    {
        $this->db->set('variant', $data['variant']);
        $this->db->set('brand', $data['brand']);
        $this->db->set('type', $data['type']);
        $this->db->set('vehicle_registration_number', $data['vehicle_registration_number']);
        $this->db->set('color', $data['color']);


        $this->db->where('vehicle_id', $data['vehicle_id']);
        $this->db->update('vehicle', $data);

        $this->db->set('job', $data2['job']);
        $this->db->where('id', $data2['id']);
        $this->db->update('driver', $data2);
    }

    public function editdatafoto($data)
    {
        $this->db->set('photo', $data['photo']);

        $this->db->where('id', $data['id']);
        $this->db->update('driver', $data);
    }

    public function editdatapassword($data)
    {
        $this->db->set('password', $data['password']);

        $this->db->where('id', $data['id']);
        $this->db->update('driver', $data);
    }

    public function blockdriverbyid($id)
    {
        $this->db->set('status', 3);
        $this->db->where('id', $id);
        $this->db->update('driver');

        $this->db->set('status', 5);
        $this->db->where('driver_id', $id);
        $this->db->update('config_driver');
    }

    public function unblockdriverbyid($id)
    {
        $this->db->set('status', 1);
        $this->db->where('id', $id);
        $this->db->update('driver');
    }

    public function editdatacard($data, $data2)
    {

        $this->db->set('idcard_images', $data['idcard_images']);
        $this->db->set('driver_license_images', $data['driver_license_images']);
        $this->db->set('driver_license_id', $data['driver_license_id']);
        $this->db->where('driver_id', $data['id']);
        $this->db->update('file_driver');

        $this->db->set('user_nationid', $data2['user_nationid']);
        $this->db->where('id', $data2['id']);
        $this->db->update('driver');
    }

    public function driverjob()
    {
        return $this->db->get('driver_job')->result_array();
    }

    public function deletedriverbyid($id)
    {
        $this->db->where('id', $id);
        $this->db->delete('driver');

        $this->db->where('driver_id', $id);
        $this->db->delete('config_driver');

        $this->db->where('driver_id', $id);
        $this->db->delete('transaction');

        $this->db->where('id_user', $id);
        $this->db->delete('balance');

        $this->db->where('driver_id', $id);
        $this->db->delete('transaction_history');

        $this->db->where('driver_id', $id);
        $this->db->delete('file_driver');

        $this->db->where('userid', $id);
        $this->db->delete('forgot_password');

        $this->db->where('driver_id', $id);
        $this->db->delete('driver_rating');

        $this->db->where('id_user', $id);
        $this->db->delete('wallet');
    }

    public function adddatadriver($datadriver)
    {
        $this->db->insert('driver');
    }

    public function editstatusnewreg($id)
    {
        $this->db->set('status', 1);
        $this->db->where('id', $id);
        $this->db->update('driver');
    }

    public function get_trans_merchant($idtransaksi)
    {
        $this->db->select('partner.*,merchant_detail_transaction.merchant_id,merchant_detail_transaction.total_price');
        $this->db->from('merchant_detail_transaction');
        $this->db->join('partner', 'merchant_detail_transaction.merchant_id = partner.merchant_id');
        $this->db->where('transaction_id', $idtransaksi);
        return $this->db->get();
    }

    public function get_verify($data)
    {
        $this->db->select('*');
        $this->db->from('merchant_detail_transaction');
        $this->db->where($data);
        return $this->db->get();
    }
}
