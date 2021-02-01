<?php


class Driverdata_model extends CI_model
{
    public function getAlldriver()
    {
        $this->db->select('config_driver.status as status_job');
        $this->db->select('driver_job.driver_job');
        $this->db->select('driver.*');
        $this->db->join('config_driver', 'driver.id = config_driver.driver_id', 'left');
        $this->db->join('driver_job', 'driver.job = driver_job.id', 'left');
        $this->db->order_by('driver.id DESC');
        return  $this->db->get('driver')->result_array();
    }

    public function getdriverbyid($id)
    {
        $this->db->select('vehicle.*');
        $this->db->select('balance.balance');
        $this->db->select('config_driver.status as status_job');
        $this->db->select('driver_job.driver_job');
        $this->db->select('file_driver.*');
        $this->db->select('driver.*');
        $this->db->join('vehicle', 'driver.vehicle = vehicle.vehicle_id', 'left');
        $this->db->join('balance', 'driver.id = balance.id_user', 'left');
        $this->db->join('config_driver', 'driver.id = config_driver.driver_id', 'left');
        $this->db->join('driver_job', 'driver.job = driver_job.id', 'left');
        $this->db->join('file_driver', 'driver.id = file_driver.driver_id', 'left');
        return  $this->db->get_where('driver', ['driver.id' => $id])->row_array();
    }

    public function deletedriverbyid($id)
    {
        $this->db->where('id', $id);
        $this->db->delete('driver');

        $this->db->where('driver_id', $id);
        $this->db->delete('config_driver');

        $this->db->where('driver_id', $id);
        $this->db->delete('transaction');

        $this->db->where('id_user', $id);
        $this->db->delete('balance');

        $this->db->where('driver_id', $id);
        $this->db->delete('transaction_history');

        $this->db->where('driver_id', $id);
        $this->db->delete('file_driver');

        $this->db->where('userid', $id);
        $this->db->delete('forgot_password');

        $this->db->where('driver_id', $id);
        $this->db->delete('driver_rating');

        $this->db->where('id_user', $id);
        $this->db->delete('wallet');
        return true;
    }

    public function countorder($id)
    {
        $this->db->select('driver_id');
        $query = $this->db->get_where('transaction', ['driver_id' => $id])->result_array();
        return count($query);
    }

    public function transaction($id)
    {
        $this->db->select('transaction_status.*');
        $this->db->select('transaction_history.*');
        $this->db->select('service.*');
        $this->db->select('transaction.*');
        $this->db->join('transaction_history', 'transaction.id = transaction_history.transaction_id', 'left');
        $this->db->join('transaction_status', 'transaction_history.status = transaction_status.id', 'left');
        $this->db->join('service', 'transaction.service_order = service.service_id', 'left');
        $this->db->order_by('transaction.id', 'DESC');
        $this->db->where('transaction_history.status != 1');
        return $this->db->get_where('transaction', ['transaction.driver_id' => $id])->result_array();
    }

    public function wallet($id)
    {
        $this->db->order_by('wallet.id', 'DESC');
        return $this->db->get_where('wallet', ['id_user' => $id])->result_array();
    }

    public function driverjob()
    {
        return $this->db->get('driver_job')->result_array();
    }

    public function editdatainfo($data)
    {


        $this->db->where('id', $data['id']);
        return $this->db->update('driver', $data);
        
    }

    public function editdataidentity($data, $data2)
    {

        $file = $this->editdataidentityfile($data);

        if ($file) {
            $this->db->where('id', $data['driver_id']);
            return $this->db->update('driver', $data2);
        }
    }

    public function editstatusnewreg($id)
    {
        $this->db->set('status', 1);
        $this->db->where('id', $id);
        return $this->db->update('driver');
    }

    public function editdataidentityfile($data)
    {

        $this->db->where('driver_id', $data['driver_id']);
        return $this->db->update('file_driver', $data);
    }

    public function editdatavehicle($data, $data2)
    {

        $vehicle = $this->editdatavehicles($data);

        if ($vehicle) {
            $this->db->where('id', $data['vehicle_id']);
            return $this->db->update('driver', $data2);
        }
    }

    public function editdatavehicles($data)
    {

        $this->db->where('vehicle_id', $data['vehicle_id']);
        return $this->db->update('vehicle', $data);
    }

    public function signup($data_signup, $data_kendaraan, $data_berkas)
    {
        $this->db->insert('vehicle', $data_kendaraan);
        $inserid = $this->db->insert_id();
        $datasignup = array(
            'id' => $data_signup['id'],
            'driver_name' => $data_signup['driver_name'],
            'user_nationid' => $data_signup['user_nationid'],
            'dob' => $data_signup['dob'],
            'phone_number' => $data_signup['phone_number'],
            'phone' => $data_signup['phone'],
            'email' => $data_signup['email'],
            'countrycode' => $data_signup['countrycode'],
            'photo' => $data_signup['photo'],
            'password' => $data_signup['password'],
            'job' => $data_signup['job'],
            'gender' => $data_signup['gender'],
            'driver_address' => $data_signup['driver_address'],
            'reg_id' => '12345',
            'vehicle' => $inserid,
            'status' => '0'
        );
        $signup = $this->db->insert('driver', $datasignup);
        $dataconfig = array(
            'driver_id' => $data_signup['id'],
            'latitude' => '0',
            'longitude' => '0',
            'status' => '5'
        );
        $this->db->insert('config_driver', $dataconfig);

        $databerkas = array(
            'driver_id' => $data_signup['id'],
            'idcard_images' => $data_berkas['idcard_images'],
            'driver_license_images' => $data_berkas['driver_license_images'],
            'driver_license_id' => $data_berkas['driver_license_id']
        );
        $this->db->insert('file_driver', $databerkas);

        $datasaldo = array(
            'id_user' => $data_signup['id'],
            'balance' => 0
        );
        $this->db->insert('balance', $datasaldo);
        return $signup;
    }

    public function blockdriverbyid($id)
    {
        $this->db->set('status', 3);
        $this->db->where('id', $id);
        $this->db->update('driver');

        $this->db->set('status', 5);
        $this->db->where('driver_id', $id);
        $this->db->update('config_driver');
        return true;
    }

    public function unblockdriverbyid($id)
    {
        $this->db->set('status', 1);
        $this->db->where('id', $id);
        $this->db->update('driver');
        return true;
    }
}
