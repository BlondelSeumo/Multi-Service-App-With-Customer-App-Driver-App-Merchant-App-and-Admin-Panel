<?php

class Promocode_model extends CI_model
{
    public function getAllpromocode()
    {
        $this->db->join('service', 'promocode.service = service.service_id', 'left');
        return  $this->db->get('promocode')->result_array();
    }

    public function cekpromo($code)
    {
        $this->db->select('*');
        $this->db->from('promocode');
        $this->db->where('promo_code', $code);
        return $this->db->get();
    }

    public function addpromocode($data)
    {
        return $this->db->insert('promocode', $data);
    }

    public function deletepromocodebyId($id)
    {
        $this->db->where('promo_id', $id);
        return $this->db->delete('promocode');
    }

    public function getpromocodebyid($id)
    {
        return $this->db->get_where('promocode', ['promo_id' => $id])->row_array();
    }

    public function editpromocode($data)
    {
        $this->db->where('promo_id', $data['promo_id']);
        return $this->db->update('promocode', $data);
    }

    public function deletepromobyId($id)
    {
        $this->db->where('id', $id);
        return $this->db->delete('promotion');
    }

    public function getpromobyid($id)
    {
        return $this->db->get_where('promotion', ['id' => $id])->row_array();
    }
}
