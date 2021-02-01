<?php


class Dashboard_model extends CI_model
{
    public function count()
    {
        $this->db->select("(SELECT COUNT(cs.id) FROM customer cs) as usercount");
        $this->db->select("(SELECT COUNT(dr.id) FROM driver dr) as drivercount");
        $this->db->select("(SELECT COUNT(mr.merchant_id) FROM merchant mr) as merchantcount");
        $this->db->select("(SELECT COUNT(tr.id) FROM transaction tr 
      left join transaction_history on tr.id = transaction_history.transaction_id) as transactioncount");

        return $this->db->get();
    }

    public function chartuser($day, $month, $year)
    {
        $this->db->select("id, count(*) as chartuser");
        $this->db->from("customer");
        $this->db->where("DAY(created_on)", $day);
        $this->db->where("MONTH(created_on)", $month);
        $this->db->where("YEAR(created_on)", $year);
        return $this->db->get();
    }

    public function chartdriver($day, $month, $year)
    {
        $this->db->select("id, count(*) as chartdriver");
        $this->db->from("driver");
        $this->db->where("DAY(created_at)", $day);
        $this->db->where("MONTH(created_at)", $month);
        $this->db->where("YEAR(created_at)", $year);
        return $this->db->get();
    }

    public function chartmerchant($day, $month, $year)
    {
        $this->db->select("partner_id, count(*) as chartmerchant");
        $this->db->from("partner");
        $this->db->where("DAY(partner_created)", $day);
        $this->db->where("MONTH(partner_created)", $month);
        $this->db->where("YEAR(partner_created)", $year);
        return $this->db->get();
    }

    public function chartorder($day, $month, $year)
    {
        $this->db->select("id, count(*) as chartorder");
        $this->db->from("transaction");
        $this->db->where("DAY(order_time)", $day);
        $this->db->where("MONTH(order_time)", $month);
        $this->db->where("YEAR(order_time)", $year);
        return $this->db->get();
    }

    public function gettotalorderplus($month)
    {
        $this->db->select('SUM(wallet_amount)as totalorderplus');
        $this->db->from("wallet");
        $this->db->where('status', 1);
        $this->db->where('type', 'Order+');
        $this->db->where("MONTH(date)", $month);
        return $this->db->get();
    }

    public function gettotalordermin($month)
    {
        $this->db->select('id, SUM(wallet_amount) as totalordermin');
        $this->db->from("wallet");
        $this->db->where('status', 1);
        $this->db->where('type', 'Order-');
        $this->db->where("MONTH(date)", $month);
        return $this->db->get();
    }


    public function gettotaldiscount($month)
    {
        $this->db->select('SUM(promo_discount) as totaldiscount');
        $this->db->from("transaction");
        $this->db->where("MONTH(order_time)", $month);
        return $this->db->get();
    }

    public function getchartdashboard($month, $year, $service)
    {
        $query = $this->db->query("
                SELECT COUNT(transaction.id) as total
                FROM transaction
                left join service on transaction.service_order = service.service_id
                left join transaction_history on transaction.id = transaction_history.transaction_id
                
                WHERE MONTH(finish_time) = $month
                AND YEAR(finish_time) = $year
                AND service.home = $service
                AND transaction_history.status = 4
            ");
        return $query->row('total');
    }

    public function getprogresstotaltrans()
    {
        $this->db->select('COUNT(transaction.id) as totaltransaction');
        $this->db->from("transaction");
        $this->db->join('transaction_history', 'transaction.id = transaction_history.transaction_id', 'left');
        $this->db->where("YEAR(order_time)", date('Y'));
        $this->db->where("MONTH(order_time)", date('m'));
        return $this->db->get()->row('totaltransaction');
    }

    public function getprogresstotalprogress()
    {
        $this->db->select('COUNT(transaction.id) as totalprogress');
        $this->db->from("transaction");
        $this->db->join('transaction_history', 'transaction.id = transaction_history.transaction_id', 'left');
        $this->db->where("YEAR(transaction.order_time)", date('Y'));
        $this->db->where("MONTH(transaction.order_time)", date('m'));
        $this->db->where("transaction_history.status", "2");
        $this->db->or_where("transaction_history.status", "3");
        return $this->db->get()->row('totalprogress');
    }

    public function getentireprogresstotaltrans()
    {
        $this->db->select('COUNT(transaction.id) as totaltransaction');
        $this->db->from("transaction");
        $this->db->join('transaction_history', 'transaction.id = transaction_history.transaction_id', 'left');
        return $this->db->get()->row('totaltransaction');
    }

    public function getentireprogresstotalprogress()
    {
        $this->db->select('COUNT(transaction.id) as totalprogress');
        $this->db->from("transaction");
        $this->db->join('transaction_history', 'transaction.id = transaction_history.transaction_id', 'left');
        $this->db->where("transaction_history.status", "2");
        $this->db->or_where("transaction_history.status", "3");
        return $this->db->get()->row('totalprogress');
    }

    public function getprogresstotalsuccess()
    {
        $this->db->select('COUNT(transaction.id) as totalprogress');
        $this->db->from("transaction");
        $this->db->join('transaction_history', 'transaction.id = transaction_history.transaction_id', 'left');
        $this->db->where("YEAR(transaction.order_time)", date('Y'));
        $this->db->where("MONTH(transaction.order_time)", date('m'));
        $this->db->where("transaction_history.status", "4");
        return $this->db->get()->row('totalprogress');
    }
    public function getentireprogresstotalsuccess()
    {
        $this->db->select('COUNT(transaction.id) as totalprogress');
        $this->db->from("transaction");
        $this->db->join('transaction_history', 'transaction.id = transaction_history.transaction_id', 'left');
        $this->db->where("transaction_history.status", "4");
        return $this->db->get()->row('totalprogress');
    }

    public function gettotalcanceled()
    {
        $this->db->select('COUNT(transaction.id) as totalcanceled');
        $this->db->from("transaction");
        $this->db->join('transaction_history', 'transaction.id = transaction_history.transaction_id', 'left');
        $this->db->where("YEAR(transaction.order_time)", date('Y'));
        $this->db->where("MONTH(transaction.order_time)", date('m'));
        $this->db->where("transaction_history.status", "5");
        return $this->db->get()->row('totalcanceled');
    }

    public function getentiretotalcanceled()
    {
        $this->db->select('COUNT(transaction.id) as totalcanceled');
        $this->db->from("transaction");
        $this->db->join('transaction_history', 'transaction.id = transaction_history.transaction_id', 'left');
        $this->db->where("transaction_history.status", "5");
        return $this->db->get()->row('totalcanceled');
    }

    public function gettotalnodriver()
    {
        $this->db->select('COUNT(transaction.id) as totalnodriver');
        $this->db->from("transaction");
        $this->db->join('transaction_history', 'transaction.id = transaction_history.transaction_id', 'left');
        $this->db->where("YEAR(transaction.order_time)", date('Y'));
        $this->db->where("MONTH(transaction.order_time)", date('m'));
        $this->db->where("transaction_history.status", "0");
        return $this->db->get()->row('totalnodriver');
    }

    public function getentiretotalnodriver()
    {
        $this->db->select('COUNT(transaction.id) as totalnodriver');
        $this->db->from("transaction");
        $this->db->join('transaction_history', 'transaction.id = transaction_history.transaction_id', 'left');
        $this->db->where("transaction_history.status", "0");
        return $this->db->get()->row('totalnodriver');
    }


    public function getAlltransaction()
    {
        $this->db->select('transaction.*,' . 'driver.driver_name,' . 'customer.customer_fullname,' . 'transaction_history.*,' . 'transaction_status.*,' . 'service.service');
        $this->db->from('transaction');
        $this->db->join('transaction_history', 'transaction.id = transaction_history.transaction_id', 'left');
        $this->db->join('transaction_status', 'transaction_history.status = transaction_status.id', 'left');
        $this->db->join('driver', 'transaction.driver_id = driver.id', 'left');
        $this->db->join('customer', 'transaction.customer_id = customer.id', 'left');
        $this->db->join('service', 'transaction.service_order = service.service_id', 'left');
        $this->db->order_by('transaction.id', 'DESC');
        return $this->db->get()->result_array();
    }

    public function getAlltransactiondasboard()
    {
        $this->db->select('transaction.*,' . 'driver.driver_name,' . 'customer.customer_fullname,' . 'transaction_history.*,' . 'transaction_status.*,' . 'service.service');
        $this->db->from('transaction');
        $this->db->join('transaction_history', 'transaction.id = transaction_history.transaction_id', 'left');
        $this->db->join('transaction_status', 'transaction_history.status = transaction_status.id', 'left');
        $this->db->join('driver', 'transaction.driver_id = driver.id', 'left');
        $this->db->join('customer', 'transaction.customer_id = customer.id', 'left');
        $this->db->join('service', 'transaction.service_order = service.service_id', 'left');
        $this->db->where('transaction_history.status != 0');
        $this->db->where('transaction_history.status != 4');
        $this->db->where('transaction_history.status != 5');
        $this->db->order_by('transaction.id', 'DESC');
        return $this->db->get()->result_array();
    }

    public function gettransactionbyid($id)
    {
        $this->db->select('merchant.*');
        $this->db->select('merchant_detail_transaction.total_price as total_belanja');
        $this->db->select('detail_send_transaction.*');
        $this->db->select('transaction_history.*');
        $this->db->select('transaction_status.*');
        $this->db->select('voucher.*');
        $this->db->select('service.*');
        $this->db->select('driver_rating.*');
        $this->db->select('customer.customer_fullname,customer.email as email_pelanggan,customer.phone_number as telepon_pelanggan,customer.customer_image,customer.token');
        $this->db->select('driver.driver_name,driver.photo,driver.email,driver.phone_number,driver.reg_id');
        $this->db->select('transaction.*');

        $this->db->join('merchant_detail_transaction', 'transaction.id = merchant_detail_transaction.transaction_id', 'left');
        $this->db->join('merchant', 'merchant_detail_transaction.merchant_id = merchant.merchant_id', 'left');
        $this->db->join('detail_send_transaction', 'transaction.id = detail_send_transaction.transaction_id', 'left');
        $this->db->join('transaction_history', 'transaction.id = transaction_history.transaction_id', 'left');
        $this->db->join('transaction_status', 'transaction_history.status = transaction_status.id', 'left');
        $this->db->join('voucher', 'transaction.service_order = voucher.voucher_service', 'left');
        $this->db->join('service', 'transaction.service_order = service.service_id', 'left');
        $this->db->join('driver_rating', 'transaction.id = driver_rating.transaction_id', 'left');
        $this->db->join('driver', 'transaction.driver_id = driver.id', 'left');
        $this->db->join('customer', 'transaction.customer_id = customer.id', 'left');
        $this->db->order_by('transaction.id', 'DESC');
        return $this->db->get_where('transaction', ['transaction.id' => $id])->row_array();
    }

    public function getitembyid($id)
    {
        $this->db->where("item_transaction.transaction_id = $id");
        $this->db->join('item', 'item_transaction.item_id = item.item_id', 'left');
        return $this->db->get('item_transaction')->result_array();
    }

    public function edittransactonstatusbyid($id)
    {
        $this->db->set('status', 5);
        $this->db->where('transaction_id', $id);
        $this->db->update('transaction_history');
    }

    public function editdriverstatusbyid($driver_id)
    {
        $this->db->set('status', 4);
        $this->db->where('driver_id', $driver_id);
        $this->db->update('config_driver');
    }

    function get_driver_location_admin()
    {
        $this->db->select(''
            . 'config_driver.driver_id,'
            . 'config_driver.latitude,'
            . 'config_driver.longitude,'
            . 'config_driver.status,'
            . 'driver.driver_name');
        $this->db->from('config_driver');
        $this->db->join('driver', 'config_driver.driver_id = driver.id', 'left');
        $this->db->where('driver.status != 0');
        $this->db->where('driver.status != 3');
        $loc = $this->db->get();
        return $loc;
    }

    function get_merchant_location_admin()
    {
        $this->db->select(''
            . 'merchant_id,'
            . 'merchant_latitude,'
            . 'merchant_longitude,'
            . 'merchant_status,'
            . 'merchant_name');
        $this->db->from('merchant');
        $this->db->where('merchant_status != 0');
        $this->db->where('merchant_status != 3');
        return $this->db->get();
    }

    public function alljob()
    {
        $this->db->select('*');
        $this->db->from('driver_job');
        $this->db->where('driver_job.status_job = 1');
        $getdata = $this->db->get()->result_array();

        $get = array();
        foreach ($getdata as $gdta) {
            $gettotal = $this->alldriver($gdta['id']);
            $get[] = array(
                'name' => $gdta['driver_job'],
                'total' => $gettotal
            );
        }

        return $get;
    }

    public function alldriver($job)
    {
        $this->db->select('COUNT(driver.id) as total');
        $this->db->from('driver');
        $this->db->where('job', $job);
        return $this->db->get()->row('total');
    }

    public function alltype()
    {
        $this->db->select('*');
        $this->db->from('service');
        $this->db->where('active = 1');
        $this->db->where('home = 4');
        $getdata = $this->db->get()->result_array();


        $get = array();
        foreach ($getdata as $gdta) {
            $gettotal = $this->allmerchant($gdta['service_id']);
            $get[] = array(
                'name' => $gdta['service'],
                'total' => $gettotal
            );
        }

        return $get;
    }

    public function allmerchant($service)
    {
        $this->db->select('COUNT(merchant.merchant_id) as total');
        $this->db->from('merchant');
        $this->db->join('merchant_category', 'merchant.merchant_category = merchant_category.category_merchant_id', 'left');
        $this->db->where('merchant_category.service_id', $service);
        return $this->db->get()->row('total');
    }

    public function deletetransaction($id)
    {
        $this->db->where('id', $id);
        $this->db->delete('transaction');

        $this->db->where('transaction_id', $id);
        $this->db->delete('transaction_history');

        $this->db->where('transaction_id', $id);
        $this->db->delete('driver_rating');

        $this->db->where('transaction_id', $id);
        $this->db->delete('detail_send_transaction');
        return true;
    }

    public function nodriver()
    {
        $this->db->where('status', 1);
        $this->db->from('transaction_history');
        $gethistorytransaction = $this->db->get()->result_array();

        foreach ($gethistorytransaction as $ght){

            $time = date('YmdHis');
            $datecreate = date_create($ght['date']);
            $timecek = date_format($datecreate, 'YmdHis');

            
            if ($timecek <= ($time - 60)) {

                $this->db->where('status', 1);
                $this->db->update('transaction_history', ['status' => 0]);

            }
            
        }
        
    }
}
