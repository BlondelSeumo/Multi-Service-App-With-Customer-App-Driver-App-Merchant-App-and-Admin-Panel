<?php


class Wallet_model extends CI_model
{

    public function getwallet()
    {
        $this->db->select('partner.partner_name');
        $this->db->select('driver.driver_name');
        $this->db->select('customer.customer_fullname');
        $this->db->select('wallet.*');
        $this->db->join('partner', 'wallet.id_user = partner.partner_id', 'left');
        $this->db->join('driver', 'wallet.id_user = driver.id', 'left');
        $this->db->join('customer', 'wallet.id_user = customer.id', 'left');
        $this->db->order_by('wallet.id', 'DESC');
        return $this->db->get('wallet')->result_array();
    }

    public function getallsaldouser()
    {
        $this->db->select('partner.partner_name');
        $this->db->select('driver.driver_name');
        $this->db->select('customer.customer_fullname');
        $this->db->select('balance.*');
        $this->db->join('partner', 'balance.id_user = partner.partner_id', 'left');
        $this->db->join('driver', 'balance.id_user = driver.id', 'left');
        $this->db->join('customer', 'balance.id_user = customer.id', 'left');
        return $this->db->get('balance')->result_array();
    }

    public function gettotaldiscount()
    {
        $this->db->select('SUM(promo_discount) as discount');
        $this->db->join('transaction_history', 'transaction.id = transaction_history.transaction_id', 'left');
        $this->db->where('transaction_history.status', 4);
        return $this->db->get('transaction')->row_array();
    }

    public function gettotalorderplus()
    {
        $this->db->select('SUM(wallet_amount)as total');
        $this->db->where('type', 'Order+');
        return $this->db->get('wallet')->row_array();
    }

    public function gettotalordermin()
    {
        $this->db->select('SUM(wallet_amount)as total');
        $this->db->where('type', 'Order-');
        return $this->db->get('wallet')->row_array();
    }

    public function gettotalwithdraw()
    {
        $this->db->select('SUM(wallet_amount)as total');
        $this->db->where('type', 'withdraw');
        $this->db->where('status', 1);
        return $this->db->get('wallet')->row_array();
    }

    public function gettotaltopup()
    {
        $this->db->select('SUM(wallet_amount)as total');
        $this->db->where('status', 1);
        $this->db->where('type', 'topup');
        return $this->db->get('wallet')->row_array();
    }

    public function getallbalance()
    {
        $this->db->select('SUM(balance)as total');
        return $this->db->get('balance')->row_array();
    }

    public function updatesaldowallet($data)
    {
        $this->db->select('partner.partner_name');
        $this->db->select('driver.driver_name');
        $this->db->select('customer.customer_fullname');
        $this->db->select('balance.balance as saldolama');
        $this->db->join('partner', 'balance.id_user = partner.partner_id', 'left');
        $this->db->join('driver', 'balance.id_user = driver.id', 'left');
        $this->db->join('customer', 'balance.id_user = customer.id', 'left');
        $this->db->where('id_user', $data['id_user']);
        $saldolama = $this->db->get('balance')->row_array();

        $saldobaru = $saldolama['saldolama'] + $data['balance'];

        $this->db->set('balance', $saldobaru);
        $this->db->where('id_user', $data['id_user']);
        $updatebalance = $this->db->update('balance');

        if ($data['type_user'] == 'customer') {
            $nama = $saldolama['customer_fullname'];
        } elseif ($data['type_user'] == 'partner') {
            $nama = $saldolama['partner_name'];
        } else {
            $nama = $saldolama['driver_name'];
        }

        if ($updatebalance) {
            $this->db->set('status', '1');
            $this->db->set('type', 'topup');
            $this->db->set('wallet_account', 'admin');
            $this->db->set('bank', 'admin');
            $this->db->set('holder_name', $nama);
            $this->db->set('wallet_amount', $data['balance']);
            $this->db->set('id_user', $data['id_user']);
            return $this->db->insert('wallet');
        }
    }

    public function updatesaldowalletwithdraw($data, $data2)
    {
        $this->db->select('partner.partner_name');
        $this->db->select('driver.driver_name');
        $this->db->select('customer.customer_fullname');
        $this->db->select('balance.balance as saldolama');
        $this->db->join('partner', 'balance.id_user = partner.partner_id', 'left');
        $this->db->join('driver', 'balance.id_user = driver.id', 'left');
        $this->db->join('customer', 'balance.id_user = customer.id', 'left');
        $this->db->where('id_user', $data['id_user']);
        $saldolama = $this->db->get('balance')->row_array();

        $saldobaru = $saldolama['saldolama'] - $data['balance'];
        if ($saldobaru < 0) {
            $this->session->set_flashdata('salah', 'Not enaugh Balances');
            redirect('wallet/addwithdraw');
        } else {
            $this->db->set('balance', $saldobaru);
            $this->db->where('id_user', $data['id_user']);
            $updatebalance = $this->db->update('balance');

            if ($data['type_user'] == 'customer') {
                $nama = $saldolama['customer_fullname'];
            } elseif ($data['type_user'] == 'partner') {
                $nama = $saldolama['partner_name'];
            } else {
                $nama = $saldolama['driver_name'];
            }

            if($updatebalance) {
                $this->db->set('status', '1');
                $this->db->set('type', 'withdraw');
                $this->db->set('wallet_account', $data2['wallet_account']);
                $this->db->set('bank', $data2['bank']);
                $this->db->set('holder_name', $data2['holder_name']);
                $this->db->set('wallet_amount', $data['balance']);
                $this->db->set('id_user', $data['id_user']);
                return $this->db->insert('wallet');
            }
        }
    }

    public function editstatuswithdrawbyid($id)
    {
        $this->db->set('status', 1);
        $this->db->where('id', $id);
        return $this->db->update('wallet');
    }

    public function editsaldotopup($id_user, $amount, $balance)
    {
        $this->db->set('balance', $balance['balance'] + $amount);
        $this->db->where('id_user', $id_user);
        $this->db->update('balance');
    }

    public function send_notif($title, $message, $topic)
    {

        $data = array(
            'title' => $title,
            'message' => $message,
            'type' => 3
        );
        $senderdata = array(
            'data' => $data,
            'to' => $topic
        );

        $headers = array(
            'Content-Type : application/json',
            'Authorization: key=' . keyfcm
        );
        $curl = curl_init();

        curl_setopt_array($curl, array(
            CURLOPT_URL => "https://fcm.googleapis.com/fcm/send",
            CURLOPT_RETURNTRANSFER => true,
            CURLOPT_ENCODING => "",
            CURLOPT_MAXREDIRS => 10,
            CURLOPT_TIMEOUT => 30,
            CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
            CURLOPT_CUSTOMREQUEST => "POST",
            CURLOPT_POSTFIELDS => json_encode($senderdata),
            CURLOPT_HTTPHEADER => $headers,
        ));

        $response = curl_exec($curl);
        $err = curl_error($curl);

        curl_close($curl);
    }

    public function gettokenmerchant($id_user)

    {
        $this->db->select('partner.*');
        $this->db->select('merchant.merchant_token');
        $this->db->join('merchant', 'partner.merchant_id = merchant.merchant_id', 'left');
        $this->db->where('partner.partner_id', $id_user);
        return $this->db->get('partner')->row_array();
    }

    public function gettoken($id_user)

    {
        $this->db->select('token');
        $this->db->where('id', $id_user);
        return $this->db->get('customer')->row_array();
    }

    public function getregid($id_user)
    {
        $this->db->select('reg_id');
        $this->db->where('id', $id_user);
        return $this->db->get('driver')->row_array();
    }

    public function getsaldo($id_user)
    {
        $this->db->select('balance');
        $this->db->where('id_user', $id_user);
        return $this->db->get('balance')->row_array();
    }

    public function cancelstatuswithdrawbyid($id)
    {
        $this->db->set('status', 2);
        $this->db->where('id', $id);
        return $this->db->update('wallet');
    }

    public function editsaldo($id_user, $amount, $balance)
    {
        $this->db->set('balance', $balance['balance'] - $amount);
        $this->db->where('id_user', $id_user);
        $this->db->update('balance');
    }
}
