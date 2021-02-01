<?php


class Merchant_model extends CI_model
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
        return  $this->db->get('partner')->result_array();
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
