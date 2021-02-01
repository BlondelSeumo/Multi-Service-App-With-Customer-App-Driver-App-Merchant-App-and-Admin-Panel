<?php

class Vehicletype_model extends CI_model
{
    public function getAllvehicletype()
    {
        return  $this->db->get('driver_job')->result_array();
    }

    public function getAllTypeService()
    {
        return  $this->db->get('service_type')->result_array();
    }

    public function addpartnerjob($data)
    {
        return $this->db->insert('driver_job', $data);
    }

    public function getpartnerjobbyid($id)
    {
        return $this->db->get_where('driver_job', ['id' => $id])->row_array();
    }

    public function editdatapartnerjob($data)
    {
        $this->db->where('id', $data['id']);
        return $this->db->update('driver_job', $data);
    }

    public function deletepartnerjobbyId($id)
    {
        $this->db->where('id', $id);
        return $this->db->delete('driver_job');
    }

    public function addservice($data, $datanilai)
    {
        $this->db->insert('service', $data);

        $id = $this->db->insert_id();
        $datavocher = [
            'voucher_service' => $id,
            'voucher_discount' => $datanilai['voucher_discount'],
            'voucher' => 'DISKON',
            'voucher_type' => '1',
            'expired' => '2020-01-31',
            'description' => 'Discount',
            'count_to_use' => '0',
            'is_valid' => 'yes'

        ];
        return $this->db->insert('voucher', $datavocher);
    }

    public function getservicebyid($id)
    {
        $this->db->select('voucher.voucher_discount');
        $this->db->select('service.*');
        $this->db->join('voucher', 'service.service_id = voucher.voucher_service', 'left');
        $this->db->where('service_id', $id);
        return $this->db->get('service')->row_array();
    }
}
