<?php

defined('BASEPATH') or exit('No direct script access allowed');

class Merchantapi_model extends CI_model
{

    public function check_banned($phone)
    {
        $stat =  $this->db->query("SELECT partner_id FROM partner WHERE partner_status='3' AND partner_telephone='$phone'");
        if ($stat->num_rows() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public function check_exist($email, $phone)
    {
        $cek = $this->db->query("SELECT partner_id FROM partner where partner_email = '$email' AND partner_telephone='$phone'");
        if ($cek->num_rows() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public function check_exist_phone($phone)
    {
        $cek = $this->db->query("SELECT partner_id FROM partner where partner_telephone='$phone'");
        if ($cek->num_rows() == 1) {
            return true;
        } else {
            return false;
        }
    }
    public function check_exist_email($email)
    {
        $cek = $this->db->query("SELECT partner_id FROM partner where partner_email='$email'");
        if ($cek->num_rows() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public function check_ktp($ktp)
    {
        $cek = $this->db->query("SELECT partner_id FROM partner where partner_identity_number='$ktp'");
        if ($cek->num_rows() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public function get_data_merchant($condition)
    {
        $this->db->select('partner.*, balance.balance, merchant.*');
        $this->db->from('partner');
        $this->db->join('balance', 'partner.partner_id = balance.id_user');
        $this->db->join('merchant', 'partner.merchant_id = merchant.merchant_id');
        $this->db->where($condition);
        $this->db->where('partner_status', '1');
        return $this->db->get();
    }

    public function onmerchant($data, $where)
    {
        $this->db->where($where);
        $this->db->update('merchant', $data);
        return true;
    }

    public function edit_profile_token($data, $phone)
    {
        $this->db->where('merchant_telephone_number', $phone);
        $this->db->update('merchant', $data);
        return true;
    }

    public function edit_profile($data, $phone)
    {
        $this->db->where('partner_telephone', $phone);
        $this->db->update('partner', $data);
        return true;
    }

    public function edit_profile_mitra_merchant($data, $phone)
    {
        $datamitra = [
            'partner_name' => $data['nama'],
            'partner_telephone' => $data['phone_number'],
            'partner_phone' => $data['phone'],
            'partner_email' => $data['email'],
            'partner_country_code' => $data['countrycode'],
            'partner_address' => $data['alamat']
        ];

        $datamerchant = [
            'merchant_telephone_number' => $data['phone_number'],
            'merchant_phone_number' => $data['phone'],
            'merchant_country_code' => $data['countrycode']
        ];

        $this->db->where('merchant_telephone_number', $phone);
        $this->db->update('merchant', $datamerchant);

        $this->db->where('partner_telephone', $phone);
        $this->db->update('partner', $datamitra);
        return true;
    }

    public function signup($data_signup, $data_merchant, $data_berkas)
    {
        $this->db->insert('merchant', $data_merchant);
        $inserid = $this->db->insert_id();
        $datasignup = array(
            'partner_id' => $data_signup['partner_id'],
            'partner_name' => $data_signup['partner_name'],
            'partner_type_identity' => $data_signup['partner_type_identity'],
            'partner_identity_number' => $data_signup['partner_identity_number'],
            'partner_address' => $data_signup['partner_address'],
            'partner_email' => $data_signup['partner_email'],
            'password' => $data_signup['password'],
            'partner_telephone' => $data_signup['partner_telephone'],
            'partner_phone' => $data_signup['partner_phone'],
            'partner_country_code' => $data_signup['partner_country_code'],
            'partner' => '0',
            'merchant_id' => $inserid,
            'partner_status' => '0'
        );
        $signup = $this->db->insert('partner', $datasignup);

        $databerkas = array(
            'driver_id' => $data_signup['partner_id'],
            'idcard_images' => $data_berkas['idcard_images'],
            'driver_license_images' => "",
            'driver_license_id' => ""
        );
        $insberkas = $this->db->insert('file_driver', $databerkas);

        $datasaldo = array(
            'id_user' => $data_signup['partner_id'],
            'balance' => 0
        );
        $insSaldo = $this->db->insert('balance', $datasaldo);
        return $signup;
    }

    public function transaksi_home($idmerchant)
    {
        $this->db->select('merchant_detail_transaction.*, transaction_history.*, transaction.customer_id, customer.customer_fullname, (SELECT SUM(ti.item_amount)
        FROM item_transaction ti
        WHERE ti.transaction_id = merchant_detail_transaction.transaction_id) quantity, customer.customer_fullname');
        $this->db->from('merchant_detail_transaction');
        $this->db->join('transaction', 'merchant_detail_transaction.transaction_id = transaction.id');
        $this->db->join('transaction_history', 'transaction.id = transaction_history.transaction_id');
        $this->db->join('customer', 'transaction.customer_id = customer.id');
        $this->db->where('merchant_detail_transaction.merchant_id', $idmerchant);
        $this->db->where('transaction_history.status = 2');
        $this->db->order_by('transaction.id DESC');
        return $this->db->get();
    }

    public function transaksi_history($idmerchant)
    {
        $this->db->select('merchant_detail_transaction.*, transaction_history.*, transaction.customer_id, customer.customer_fullname, (SELECT SUM(ti.item_amount)
        FROM item_transaction ti
        WHERE ti.transaction_id = merchant_detail_transaction.transaction_id) quantity');
        $this->db->from('merchant_detail_transaction');
        $this->db->join('transaction', 'merchant_detail_transaction.transaction_id = transaction.id');
        $this->db->join('transaction_history', 'transaction.id = transaction_history.transaction_id');
        $this->db->join('customer', 'transaction.customer_id = customer.id');
        $this->db->where('merchant_detail_transaction.merchant_id', $idmerchant);
        $this->db->where('transaction_history.status != 2');
        $this->db->where('transaction_history.status != 1');
        $this->db->where('transaction_history.status != 0');
        $this->db->order_by('transaction.id DESC');
        return $this->db->get();
    }

    public function total_history_daily($day, $idmerchant)
    {
        $this->db->select('SUM(merchant_detail_transaction.total_price) daily');
        $this->db->from('merchant_detail_transaction');
        $this->db->join('transaction_history', 'merchant_detail_transaction.transaction_id = transaction_history.transaction_id');
        $this->db->where('date(merchant_detail_transaction.created)', $day);
        $this->db->where('merchant_id', $idmerchant);
        $this->db->where('transaction_history.status', '4');
        return $this->db->get();
    }

    public function total_history_earning($idmerchant)
    {
        $this->db->select('SUM(merchant_detail_transaction.total_price) earning');
        $this->db->from('merchant_detail_transaction');
        $this->db->join('transaction_history', 'merchant_detail_transaction.transaction_id = transaction_history.transaction_id');
        $this->db->where('merchant_id', $idmerchant);
        $this->db->where('transaction_history.status', '4');
        return $this->db->get();
    }

    public function total_history_month($month, $idmerchant)
    {
        $this->db->select('SUM(merchant_detail_transaction.total_price) month');
        $this->db->from('merchant_detail_transaction');
        $this->db->join('transaction_history', 'merchant_detail_transaction.transaction_id = transaction_history.transaction_id');
        $this->db->where('MONTH(merchant_detail_transaction.created)', $month);
        $this->db->where('merchant_id', $idmerchant);
        $this->db->where('transaction_history.status', '4');
        return $this->db->get();
    }

    public function total_history_yearly($year, $idmerchant)
    {
        $this->db->select('SUM(merchant_detail_transaction.total_price) yearly');
        $this->db->from('merchant_detail_transaction');
        $this->db->join('transaction_history', 'merchant_detail_transaction.transaction_id = transaction_history.transaction_id');
        $this->db->where('YEAR(merchant_detail_transaction.created)', $year);
        $this->db->where('merchant_id', $idmerchant);
        $this->db->where('transaction_history.status', '4');
        return $this->db->get();
    }

    public function kategori_active($idmerchant)
    {
        $this->db->select('category_item.*, (SELECT COUNT(ti.item_id)
        FROM item ti
        WHERE ti.item_category = category_item.category_item_id) total_item');
        $this->db->from('category_item');
        $this->db->where('category_item.merchant_id', $idmerchant);
        $this->db->where('category_item.all_category != 1');
        $this->db->where('category_item.category_status = 1');
        return $this->db->get()->result();
    }
    public function kategori_nonactive($idmerchant)
    {
        $this->db->select('category_item.*, (SELECT COUNT(ti.item_id)
        FROM item ti
        WHERE ti.item_category = category_item.category_item_id) total_item');
        $this->db->from('category_item');
        $this->db->where('category_item.merchant_id', $idmerchant);
        $this->db->where('category_item.all_category != 1');
        $this->db->where('category_item.category_status = 0');
        return $this->db->get()->result();
    }

    public function itembycatactive($idmerchant, $idcat)
    {
        $this->db->select('*');
        $this->db->from('item');
        $this->db->where('item.merchant_id', $idmerchant);
        $this->db->where('item.item_category', $idcat);
        $this->db->order_by('item.item_id DESC');
        return $this->db->get();
    }

    public function totalitemactive($idmerchant)
    {
        $this->db->select('COUNT(item_id) as active, (SELECT COUNT(ti.item_id)
        FROM item ti
        WHERE ti.merchant_id =' . $idmerchant . ' and ti.item_status = 0) as nonactive, (SELECT COUNT(ti.item_id)
        FROM item ti
        WHERE ti.merchant_id =' . $idmerchant . ' and ti.item_status = 1 and ti.promo_status = 1) as promo');
        $this->db->from('item');
        $this->db->where('item.merchant_id', $idmerchant);
        $this->db->where('item.item_status', '1');
        return $this->db->get();
    }

    public function actived_kategori($id, $status)
    {
        $data = array(
            'category_status' => $status
        );
        $this->db->where('category_item_id', $id);
        $this->db->update('category_item', $data);
        return true;
    }

    public function actived_item($id, $status)
    {
        $data = array(
            'item_status' => $status
        );
        $this->db->where('item_id', $id);
        $this->db->update('item', $data);
        return true;
    }

    public function addkategori($nama, $status, $id)
    {
        $data = array(
            'category_name_item' => $nama,
            'category_status' => $status,
            'merchant_id' => $id,
            'all_category' => 0,
        );
        $this->db->insert('category_item', $data);
        return true;
    }

    public function editkategori($editdata, $id)
    {
        $this->db->where('category_item_id', $id);
        $this->db->update('category_item', $editdata);
        return true;
    }

    public function deletekategori($id)
    {
        $this->db->where('category_item_id', $id);
        $this->db->delete('category_item');

        $this->db->where('item_category', $id);
        $this->db->delete('item');
        return true;
    }

    public function additem($data)
    {
        $this->db->insert('item', $data);
        return true;
    }

    public function edititem($editdata, $id)
    {
        $this->db->where('item_id', $id);
        $this->db->update('item', $editdata);
        return true;
    }

    public function deleteitem($id)
    {
        $this->db->where('item_id', $id);
        $this->db->delete('item');
        return true;
    }

    public function check_exist_phone_edit($id, $phone)
    {
        $cek = $this->db->query("SELECT partner_telephone FROM partner where partner_telephone='$phone' AND partner_id!='$id'");
        if ($cek->num_rows() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public function check_exist_email_edit($id, $email)
    {
        $cek = $this->db->query("SELECT partner_id FROM partner where partner_email='$email' AND partner_id!='$id'");
        if ($cek->num_rows() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public function kategori_merchant_active()
    {
        $this->db->select('category_name,category_merchant_id');
        $this->db->where('category_status != 0');
        return $this->db->get('merchant_category')->result_array();
    }

    public function kategori_merchant_active_data($idfitur)
    {
        $this->db->select('category_name,category_merchant_id');
        $this->db->where('category_status != 0');
        $this->db->where('service_id', $idfitur);
        return $this->db->get('merchant_category')->result_array();
    }

    public function fitur_merchant_active()
    {
        $this->db->select('service_id,service');
        $this->db->from('service');
        $this->db->where('home = 4');
        $this->db->where('active', '1');
        $this->db->order_by('service_id ASC');
        return $this->db->get()->result_array();
    }
}
