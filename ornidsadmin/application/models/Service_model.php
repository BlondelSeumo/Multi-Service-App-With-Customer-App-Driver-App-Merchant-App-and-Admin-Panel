<?php


class Service_model extends CI_model
{
    public function getAllservice()
    {
        $this->db->select('voucher.voucher_discount');
        $this->db->select('service.*');
        $this->db->order_by('service_id', 'ASC');
        $this->db->join('voucher', 'service.service_id = voucher.voucher_service', 'left');

        return  $this->db->get('service')->result_array();
    }

    public function getAlldriverjob()
    {
        return  $this->db->get('driver_job')->result_array();
    }

    public function editdataservice($data)
    {
        $this->db->set('icon', $data['icon']);
        $this->db->set('service', $data['service']);
        $this->db->set('home', $data['home']);
        $this->db->set('cost', $data['cost']);
        $this->db->set('cost_desc', $data['cost_desc']);
        $this->db->set('commision', $data['commision']);
        $this->db->set('driver_job', $data['driver_job']);
        $this->db->set('minimum_cost', $data['minimum_cost']);
        $this->db->set('minimum_distance', $data['minimum_distance']);
        $this->db->set('maks_distance', $data['maks_distance']);
        $this->db->set('minimum_wallet', $data['minimum_wallet']);
        $this->db->set('description', $data['description']);
        $this->db->set('active', $data['active']);
        $this->db->where('service_id', $data['service_id']);
        $service = $this->db->update('service');

        if ($service) {
            $this->db->set('voucher_discount', $data['voucher_discount']);
            $this->db->where('voucher_service', $data['service_id']);
            return $this->db->update('voucher');
        } else {
            return false;
        }
    }

    public function deleteservicebyId($id)
    {
        $this->db->where('service_id', $id);
        $service = $this->db->delete('service');
        if ($service) {
            $this->db->where('voucher_service', $id);
            return $this->db->delete('voucher');
        }
    }

    public function getAllmerchantcategory()
    {
        $this->db->where('merchant_category.service_id != 0');
        $this->db->join('service', 'merchant_category.service_id = service.service_id', 'left');
        return  $this->db->get('merchant_category')->result_array();
    }

    public function getservicemerchant()
    {
        $this->db->where('home = 4');
        return  $this->db->get('service')->result_array();
    }

    public function addmerchantcategory($data)
    {
        return $this->db->insert('merchant_category', $data);
    }

    public function getmerchantcategorybyid($id)
    {
        $this->db->from('merchant_category');
        $this->db->where('category_merchant_id', $id);
        return $this->db->get()->row_array();
    }

    public function editmerchantcategory($data, $id)
    {
        $this->db->where('category_merchant_id', $id);
        return $this->db->update('merchant_category', $data);
    }

    public function deletemerchantcategory($id)
    {
        $this->db->where('category_merchant_id', $id);
        return $this->db->delete('merchant_category');
    }
}
