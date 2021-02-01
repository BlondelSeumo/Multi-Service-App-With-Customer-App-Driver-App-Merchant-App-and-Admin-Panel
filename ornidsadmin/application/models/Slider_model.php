<?php


class Slider_model extends CI_model
{
    public function getAllslider()
    {
        $this->db->join('service', 'promotion.promotion_service = service.service_id', 'left');
        return  $this->db->get('promotion')->result_array();
    }

    public function getsliderbyid($id)
    {
        return $this->db->get_where('promotion', ['id' => $id])->row_array();
    }

    public function getserviceid($id)
    {
        return $this->db->get_where('service', ['service_id' => $id])->row_array();
    }

    public function adddataslider($data)
    {
        return $this->db->insert('promotion', $data);
    }

    public function editdataslider($data)
    {
        $this->db->where('id', $data['id']);
        return $this->db->update('promotion', $data);
    }

    public function editcm($data, $id)
    {
        $this->db->where('category_merchant_id', $id);
        return $this->db->update('merchant_category');
    }
}
