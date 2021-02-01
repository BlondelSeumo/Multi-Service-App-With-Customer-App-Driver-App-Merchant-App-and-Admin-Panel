<?php


class Appsettings_model extends CI_model
{
    public function getcurrency()
    {
        $this->db->select('app_currency as currency');
        $this->db->where('id', '1');
        return $this->db->get('app_settings')->row('currency');
    }

    public function getallbanktransfer()
    {
        $this->db->select('*');
        $this->db->from('bank_list');
        return $this->db->get()->result_array();
    }

    public function getMenuAdmin()
    {
        $this->db->select('*');
        $this->db->from('menu_admin');
        $this->db->order_by('menu_admin.menu_id ASC');
        $menu = $this->db->get()->result_array();



        $result = array();
        foreach ($menu as $mnu) {
            $submenu = $this->getSubMenuAdmin($mnu['menu_id']);
            $result[] = array(
                "menu_id" => $mnu['menu_id'],
                "menu_icon" => $mnu['menu_icon'],
                "menu_title" => $mnu['menu_title'],
                "menu_url" => $mnu['menu_url'],
                "menu_sub" => $mnu['menu_sub'],
                "sub_menu" => $submenu
            );
        }

        return $result;
    }

    public function getSubMenuAdmin($id)
    {
        $this->db->select('*');
        $this->db->from('submenu_admin');
        $this->db->where('menu_id', $id);
        $this->db->order_by('submenu_admin.sub_id ASC');
        return $this->db->get()->result_array();
    }

    public function getappbyid()
    {
        return $this->db->get_where('app_settings', ['id' => '1'])->row_array();
    }

    public function editdataappsettings($data)
    {
        $this->db->where('id', '1');
        return $this->db->update('app_settings', $data);
    }

    public function adddatarekening($data)
    {
        return $this->db->insert('bank_list', $data);
    }

    public function deleterekening($id)
    {
        $this->db->where('bank_id', $id);
        return $this->db->delete('bank_list');
    }

    public function getbankid($id)
    {
        $this->db->select('*');
        $this->db->from('bank_list');
        $this->db->where('bank_id', $id);
        return $this->db->get()->row_array();
    }

    public function editdatarekening($data, $id)
    {
        $this->db->where('bank_id', $id);
        return $this->db->update('bank_list', $data);
    }
}
