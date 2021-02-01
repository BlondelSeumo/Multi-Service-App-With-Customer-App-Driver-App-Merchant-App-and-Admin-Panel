<?php

class login_model extends CI_Model
{
    function cek_login($where)
    {
        return $this->db->get_where('admin', $where)->row_array();
    }
}
