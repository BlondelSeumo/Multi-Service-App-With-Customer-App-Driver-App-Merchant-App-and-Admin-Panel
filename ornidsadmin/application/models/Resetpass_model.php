<?php

class Resetpass_model extends CI_model
{

    public function check_token($token, $type)
    {
        return $this->db->get_where('forgot_password', ['token' => $token, 'idKey' => $type])->row_array();
    }

    public function reset_pass($data, $id, $type)
    {
        if ($type == '1') {
            $this->db->where('id', $id);
            $this->db->update('customer', $data);
        } elseif ($type == '2') {
            $this->db->where('id', $id);
            $this->db->update('driver', $data);
        } elseif ($type == '3') {
            $this->db->where('partner_id', $id);
            $this->db->update('partner', $data);
        }
        return true;
    }

    public function resetpass($password)
    {

        $data = [
            "password" => sha1($password)
        ];
        if ($this->input->post('type') == '1') {
            $this->db->where('id', $this->input->post('id'));
            $this->db->update('customer', $data);
        } elseif ($this->input->post('type') == '2') {
            $this->db->where('id', $this->input->post('id'));
            $this->db->update('driver', $data);
        } elseif ($this->input->post('type') == '3') {
            $this->db->where('partner_id', $this->input->post('id'));
            $this->db->update('partner', $data);
        }
        return true;
    }

    public function deletetoken()
    {
        $this->db->delete('forgot_password', ['token' => $this->input->post('token')]);
        return true;
    }
}
