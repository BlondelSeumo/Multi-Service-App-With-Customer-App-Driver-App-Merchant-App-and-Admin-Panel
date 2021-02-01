<?php


class Customerdata_model extends CI_model
{
    public function getAllcustomer()
    {
        $this->db->order_by('id DESC');
        return $this->db->get('customer')->result_array();
    }

    public function getcustomerbyid($id)
    {
        $this->db->select('balance.balance');
        $this->db->select('customer.*');
        $this->db->join('balance', 'customer.id = balance.id_user', 'left');
        return  $this->db->get_where('customer', ['customer.id' => $id])->row_array();
    }

    public function wallet($id)
    {
        $this->db->order_by('wallet.id', 'DESC');
        return $this->db->get_where('wallet', ['id_user' => $id])->result_array();
    }

    public function countorder($id)
    {

        $this->db->select('transaction_status.*');
        $this->db->select('transaction_history.*');
        $this->db->select('service.*');
        $this->db->select('transaction.*');
        $this->db->join('transaction_history', 'transaction.id = transaction_history.transaction_id', 'left');
        $this->db->join('transaction_status', 'transaction_history.status = transaction_status.id', 'left');
        $this->db->join('service', 'transaction.service_order = service.service_id', 'left');
        $this->db->where('transaction_history.status != 1');
        $this->db->order_by('transaction.id', 'DESC');
        $query =    $this->db->get_where('transaction', ['customer_id' => $id])->result_array();
        return $query;
    }
    public function getcurrency()
    {
        $this->db->select('app_currency as duit');
        $this->db->where('id', '1');
        return $this->db->get('app_settings')->row_array();
    }

    public function editdatacustomer($data)
    {
        $this->db->where('id', $data['id']);
        return $this->db->update('customer', $data);
    }

    public function adddatacustomer($data)
    {
        $this->db->insert('customer', $data);
        $data2 = [
            'id_user' => $data['id'],
            'balance'   => 0,
        ];
        return $this->db->insert('balance', $data2);
    }

    public function getusersbyid($id)
    {
        $this->db->select('balance.balance');
        $this->db->select('customer.*');
        $this->db->join('balance', 'customer.id = balance.id_user', 'left');
        return  $this->db->get_where('customer', ['customer.id' => $id])->row_array();
    }

    public function deletedatauserbyid($id)
    {
        $this->db->where('id', $id);
        $this->db->delete('customer');

        $this->db->where('customer_id', $id);
        $this->db->delete('transaction');

        $this->db->where('id_user', $id);
        $this->db->delete('balance');

        $this->db->where('userid', $id);
        $this->db->delete('forgot_password');

        $this->db->where('customer_id', $id);
        $this->db->delete('driver_rating');

        $this->db->where('id_user', $id);
        $this->db->delete('wallet');
        return true;
    }

    public function blockusersById($id)
    {
        $this->db->set('status', 0);
        $this->db->where('id', $id);
        return $this->db->update('customer');
    }

    public function unblockusersById($id)
    {
        $this->db->set('status', 1);
        $this->db->where('id', $id);
        return $this->db->update('customer');
    }

    public function getAllusers()
    {
        return $this->db->get('customer')->result_array();
    }
}
