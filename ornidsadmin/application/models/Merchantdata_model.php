<?php


class Merchantdata_model extends CI_model
{
    public function getAllmerchant()
    {
        $this->db->select('merchant_category.category_name');
        $this->db->select('service.service');
        $this->db->select('merchant.*');
        $this->db->select('partner.*');
        $this->db->join('merchant', 'partner.merchant_id = merchant.merchant_id', 'left');
        $this->db->join('merchant_category', 'merchant.merchant_category = merchant_category.category_merchant_id', 'left');
        $this->db->join('service', 'merchant.service_id = service.service_id', 'left');
        $this->db->order_by('partner.partner_id DESC');
        return  $this->db->get('partner')->result_array();
    }

    public function getpartnerbyid($id)
    {
        $this->db->select('file_driver.idcard_images');
        $this->db->select('merchant_category.category_name');
        $this->db->select('service.service');
        $this->db->select('balance.balance');
        $this->db->select('merchant.*');
        $this->db->select('partner.*');
        $this->db->join('file_driver', 'partner.partner_id = file_driver.driver_id', 'left');
        $this->db->join('balance', 'partner.partner_id = balance.id_user', 'left');
        $this->db->join('merchant', 'partner.merchant_id = merchant.merchant_id', 'left');
        $this->db->join('service', 'merchant.service_id = service.service_id', 'left');
        $this->db->join('merchant_category', 'merchant.merchant_category = merchant_category.category_merchant_id', 'left');
        return  $this->db->get_where('partner', ['partner.partner_id' => $id])->row_array();
    }

    public function getitembyid($id)
    {
        $this->db->select('item.*, category_item.*');
        $this->db->from('item');
        $this->db->join('category_item', 'item.item_category = category_item.category_item_id', 'left');
        $this->db->where('item.merchant_id', $id);
        return $this->db->get()->result_array();
    }

    public function getcatitembyid($id)
    {
        $this->db->from('category_item');
        $this->db->where('merchant_id', $id);
        return $this->db->get()->result_array();
    }

    public function countorder($id)
    {
        $this->db->select('merchant_id');
        $query = $this->db->get_where('merchant_detail_transaction', ['merchant_id' => $id])->result_array();
        return count($query);
    }

    public function wallet($id)
    {
        $this->db->order_by('wallet.id', 'DESC');
        return $this->db->get_where('wallet', ['id_user' => $id])->result_array();
    }

    public function getmerchantcat()
    {
        $this->db->from('merchant_category');
        $this->db->where('service_id != 0');
        return $this->db->get()->result_array();
    }

    public function gettranshistory($id)
    {
        $this->db->select('customer.customer_fullname');
        $this->db->select('item_transaction.*');
        $this->db->select('transaction.*');
        $this->db->select('merchant_detail_transaction.*');

        $this->db->join('item_transaction', 'merchant_detail_transaction.transaction_id = item_transaction.transaction_id', 'left');
        $this->db->join('transaction', 'merchant_detail_transaction.transaction_id = transaction.id', 'left');
        $this->db->join('customer', 'transaction.customer_id = customer.id', 'left');
        $this->db->join('transaction_history', 'transaction.id = transaction_history.transaction_id', 'left');
        $this->db->where('transaction_history.status != 0');
        return  $this->db->get_where('merchant_detail_transaction', "merchant_detail_transaction.merchant_id = $id")->result_array();
    }

    public function get_service_merchant()
    {
        $this->db->select('*');
        $this->db->from('service');
        $this->db->where('active', '1');
        $this->db->where('home', '4');
        return $this->db->get()->result_array();
    }

    public function editmerchantbyid($data, $data2)
    {

        $file = $this->editfilemerchantbyid($data2);

        if ($file) {
            $this->db->where('partner_id', $data['partner_id']);
            return $this->db->update('partner', $data);
        }
    }

    public function editfilemerchantbyid($data2)
    {
        $this->db->where('driver_id', $data2['driver_id']);
        return $this->db->update('file_driver', $data2);
    }

    public function getdetailmerchant($id)
    {
        $this->db->where('merchant_id', $id);
        return  $this->db->get('merchant')->row_array();
    }

    public function updatemerchant($data)
    {
        $idmerchant = $data['merchant_id'];
        $this->db->where('merchant_id', $idmerchant);
        return $this->db->update('merchant', $data);
    }

    public function additemcategorybyid($data)
    {
        return $this->db->insert('category_item', $data);
    }

    public function editcategoryitembyid($data, $id)
    {
        $this->db->where('category_item_id', $id);
        return $this->db->update('category_item', $data);
    }

    public function insertitem($data)
    {
        return $this->db->insert('item', $data);
    }

    public function updateitem($data, $id)
    {
        $this->db->where('item_id', $id);
        return $this->db->update('item', $data);
    }

    public function getidmerchant($id)
    {

        $this->db->where('merchant_id', $id);
        return  $this->db->get('partner')->row_array();
    }

    public function getitemphoto($id)
    {

        $this->db->where('item_id', $id);
        return  $this->db->get('item')->row_array();
    }

    public function getitembyiditem($id)
    {
        $this->db->from('item');
        $this->db->where('item_id', $id);
        return $this->db->get()->row_array();
    }

    public function get_merchant_service()
    {
        $this->db->select('*');
        $this->db->from('service');
        $this->db->where('active', '1');
        $this->db->where('home', '4');
        return $this->db->get()->result_array();
    }

    public function addmerchant($datamitra, $databerkas, $datasaldo)
    {
        $addpartner = $this->db->insert('partner', $datamitra);
        if ($addpartner) {

            $addfiledriver = $this->db->insert('file_driver', $databerkas);
            if ($addfiledriver){
                return $this->db->insert('balance', $datasaldo);
            }
        }
    }

    public function insertmerchant($data)
    {
        $this->db->insert('merchant', $data);
        return $this->db->insert_id();
    }

    public function deletemitrabyid($id)
    {
        $this->db->where('partner_id', $id);
        $idm = $this->db->get('partner')->row('merchant_id');

        $this->db->where('merchant_id', $idm);
        $itemcategory = $this->db->get('category_item')->result();

        $this->db->where('merchant_id', $idm);
        $item = $this->db->get('item')->result();

        $this->db->where('id_user', $id);
        $deletewallet = $this->db->delete('wallet');
        if ($deletewallet) {
            $this->db->where('id_user', $id);
            $deletebalance = $this->db->delete('balance');
            if ($deletebalance) {
                $this->db->where('partner_id', $id);
                $deletepartner = $this->db->delete('partner');
                if ($deletepartner) {
                    $this->db->where('merchant_id', $idm);
                $deletemerchant = $this->db->delete('merchant');
                if ($deletemerchant && $itemcategory && $item) {

                    $this->db->where('merchant_id', $idm);
                    $deletecategoryitem = $this->db->delete('category_item');
                    if ($deletecategoryitem) {
                        $this->db->where('merchant_id', $idm);
                        return $this->db->delete('item');
                    }
                } else if ($deletemerchant && $item) {
                    $this->db->where('merchant_id', $idm);
                    return $this->db->delete('item');
                } else if ($deletemerchant) {
                    return true;
                } else {
                    return false;
                }
                }
            }
            
        }

    }

    public function getfilebyid($id)
    {
        $this->db->where('driver_id', $id);
        return $this->db->get('file_driver')->row_array();
    }

    public function blockmitrabyid($id)
    {
        $this->db->set('partner_status', 3);
        $this->db->where('partner_id', $id);
        return $this->db->update('partner');
    }

    public function unblockmitrabyid($id)
    {
        $this->db->set('partner_status', 1);
        $this->db->where('partner_id', $id);
        return $this->db->update('partner');
    }



    public function getmitrabyid($id)
    {
        $this->db->select('file_driver.idcard_images');
        $this->db->select('merchant_category.category_name');
        $this->db->select('service.service');
        $this->db->select('balance.balance');
        $this->db->select('merchant.*');
        $this->db->select('partner.*');
        $this->db->join('file_driver', 'partner.partner_id = file_driver.driver_id', 'left');
        $this->db->join('balance', 'partner.partner_id = balance.id_user', 'left');
        $this->db->join('merchant', 'partner.merchant_id = merchant.merchant_id', 'left');
        $this->db->join('service', 'merchant.service_id = service.service_id', 'left');
        $this->db->join('merchant_category', 'merchant.merchant_category = merchant_category.category_merchant_id', 'left');
        return  $this->db->get_where('partner', ['partner.partner_id' => $id])->row_array();
    }

    public function editstatusmitra($id)
    {


        $partner = $this->getmitrabyid($id);
        $idmerchant = $partner['merchant_id'];

        $this->db->set('partner_status', '1');
        $this->db->where('partner_id', $id);
        $success = $this->db->update('partner');

        if ($success) {
            $this->db->set('merchant_status', '1');
            $this->db->where('merchant_id', $idmerchant);
            return $this->db->update('merchant');
        }
    }
}
