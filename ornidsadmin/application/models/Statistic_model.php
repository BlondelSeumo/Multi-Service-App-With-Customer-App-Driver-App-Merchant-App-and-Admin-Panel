<?php


class Statistic_model extends CI_model
{
    public function countgeneral()
    {
        $this->db->select("(SELECT COUNT(srv.service_id) FROM service srv) as servicecount");
        $this->db->select("(SELECT COUNT(dj.id) FROM driver_job dj) as driverjobcount");
        $this->db->select("(SELECT COUNT(srvm.service_id) FROM service srvm WHERE srvm.home = 4) as merchanttypecount");
        $this->db->select("(SELECT COUNT(mcat.category_merchant_id) FROM merchant_category mcat) as merchantcatcount");
        $this->db->select("(SELECT COUNT(sld.id) FROM promotion sld) as slidercount");
        $this->db->select("(SELECT COUNT(prcd.promo_id) FROM promocode prcd) as promocodecount");
        return $this->db->get();
    }

    public function counttransaction()
    {
        $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
        left join service on tr.service_order = service.service_id WHERE service.home = 1) as passangertranscount");
        $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
        left join service on tr.service_order = service.service_id WHERE service.home = 2) as shipmenttranscount");
        $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
        left join service on tr.service_order = service.service_id WHERE service.home = 3) as rentaltranscount");
        $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
        left join service on tr.service_order = service.service_id WHERE service.home = 4) as purchasingtranscount");
        return $this->db->get();
    }

    public function completestatisticbyservice($day, $month, $year)
  {
      $beforedaily = $day - 1;
      $beforemonthly = $month - 1;
      $beforeyearly = $year - 1;

      
    $this->db->select('service.service');
    $this->db->select("
                (SELECT COUNT(tr.id)
                FROM transaction tr
                left join transaction_history on tr.id = transaction_history.transaction_id
                WHERE DAY(tr.finish_time) = $day
                AND tr.service_order = service.service_id
                AND transaction_history.status = 4) as daily
                ");
                $this->db->select("
                (SELECT COUNT(tr.id)
                FROM transaction tr
                left join transaction_history on tr.id = transaction_history.transaction_id
                WHERE MONTH(tr.finish_time) = $month
                AND tr.service_order = service.service_id
                AND transaction_history.status = 4) as monthly
                ");
                $this->db->select("
                (SELECT COUNT(tr.id)
                FROM transaction tr
                left join transaction_history on tr.id = transaction_history.transaction_id
                WHERE YEAR(tr.finish_time) = $year
                AND tr.service_order = service.service_id
                AND transaction_history.status = 4) as yearly
                ");

                $this->db->select("
                (SELECT COUNT(tr.id)
                FROM transaction tr
                left join transaction_history on tr.id = transaction_history.transaction_id
                WHERE DAY(tr.finish_time) = $beforedaily
                AND tr.service_order = service.service_id
                AND transaction_history.status = 4) as beforedaily
                ");
                $this->db->select("
                (SELECT COUNT(tr.id)
                FROM transaction tr
                left join transaction_history on tr.id = transaction_history.transaction_id
                WHERE MONTH(tr.finish_time) = $beforemonthly
                AND tr.service_order = service.service_id
                AND transaction_history.status = 4) as beforemonthly
                ");
                $this->db->select("
                (SELECT COUNT(tr.id)
                FROM transaction tr
                left join transaction_history on tr.id = transaction_history.transaction_id
                WHERE YEAR(tr.finish_time) = $beforeyearly
                AND tr.service_order = service.service_id
                AND transaction_history.status = 4) as beforeyearly
                ");
    $this->db->from('service');
    $datacomplete = $this->db->get()->result_array();

        
        $data = array();
        foreach($datacomplete as $cmpt) {

            
            $lastestday = $cmpt['daily'] - $cmpt['beforedaily'];
            $lastestmonth = $cmpt['monthly'] - $cmpt['beforemonthly'];
            $lastestyear = $cmpt['yearly'] - $cmpt['beforeyearly'];
            
            if ($cmpt['beforedaily'] > 0) {
            if ($lastestday >= 0) {
                $icondaily = 'up';
                $totalday = $lastestday;
            } else {
                $icondaily = 'down';
                $totalday = $lastestday*-1;
            }
        } else {
            $icondaily = 'up';
                $totalday = $cmpt['beforedaily'];
        }

            if ($cmpt['beforemonthly'] > 0) {
            if ($lastestmonth >= 0) {
                $iconmonthly = 'up';
                $totalmonth = $lastestmonth;
            } else {
                $iconmonthly = 'down';
                $totalmonth = $lastestmonth*-1;
            }
        } else {
            $iconmonthly = 'up';
                $totalmonth = $cmpt['beforemonthly'];
        }

            if ($cmpt['beforeyearly'] > 0) {
            if ($lastestyear >= 0) {
                $iconyearly = 'up';
                $totalyear = $lastestyear;
            } else {
                $iconyearly = 'down';
                $totalyear = $lastestyear*-1;
            }
            } else {
                $iconyearly = 'up';
                $totalyear = $cmpt['beforeyearly'];
            }

            
            

        $data[] = array(
            'service' =>  $cmpt['service'],
            'daily' =>  $cmpt['daily'],
            'monthly' =>  $cmpt['monthly'],
            'yearly' =>  $cmpt['yearly'],
            'latestday' => $totalday,
            'icondaily' => $icondaily,
            'latestmonth' => $totalmonth,
            'iconmonthly' => $iconmonthly,
            'latestyear' => $totalyear,
            'iconyearly' => $iconyearly
            
        );
        
        }

        return $data;

  }

  public function countservicepassanger()
  {
    $day = date('d');
    
    $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE transaction_history.status = 4
    AND service.home = 1
    ) as successpassangercount");

    $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE transaction_history.status = 5
    AND service.home = 1
    ) as cancelpassangercount");

    $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE transaction_history.status = 0
    AND service.home = 1
    ) as nodriverpassangercount");

    $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE transaction_history.status = 1 
    AND transaction_history.status = 2 
    AND transaction_history.status = 3 
    AND service.home = 1
    ) as onprogresspassangercount");

    $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE DAY(tr.order_time) = $day
    AND transaction_history.status = 4
    AND service.home = 1
    ) as successpassangercountdaily");

    $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE DAY(tr.order_time) = $day
    AND transaction_history.status = 5
    AND service.home = 1
    ) as cancelpassangercountdaily");

    $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE DAY(tr.order_time) = $day
    AND transaction_history.status = 0
    AND service.home = 1
    ) as nodriverpassangercountdaily");

    $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE DAY(tr.order_time) = $day
    AND transaction_history.status = 1
    AND transaction_history.status = 2
    AND transaction_history.status = 3
    AND service.home = 1
    ) as onprogresspassangercountdaily");
    return $this->db->get();
  }

  public function countserviceshipment()
  {
    $day = date('d');
    
    $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE transaction_history.status = 4
    AND service.home = 2
    ) as successshipmentcount");

    $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE transaction_history.status = 5
    AND service.home = 2
    ) as cancelshipmentcount");

    $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE transaction_history.status = 0
    AND service.home = 2
    ) as nodrivershipmentcount");

    $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE transaction_history.status = 1 
    AND transaction_history.status = 2 
    AND transaction_history.status = 3 
    AND service.home = 2
    ) as onprogressshipmentcount");

    $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE DAY(tr.order_time) = $day
    AND transaction_history.status = 4
    AND service.home = 2
    ) as successshipmentcountdaily");

    $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE DAY(tr.order_time) = $day
    AND transaction_history.status = 5
    AND service.home = 2
    ) as cancelshipmentcountdaily");

    $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE DAY(tr.order_time) = $day
    AND transaction_history.status = 0
    AND service.home = 2
    ) as nodrivershipmentcountdaily");

    $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE DAY(tr.order_time) = $day
    AND transaction_history.status = 1
    AND transaction_history.status = 2
    AND transaction_history.status = 3
    AND service.home = 2
    ) as onprogressshipmentcountdaily");
    return $this->db->get();
  }

  public function countservicerental()
  {
    $day = date('d');
    
    $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE transaction_history.status = 4
    AND service.home = 3
    ) as successrentalcount");

    $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE transaction_history.status = 5
    AND service.home = 3
    ) as cancelrentalcount");

    $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE transaction_history.status = 0
    AND service.home = 3
    ) as nodriverrentalcount");

    $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE transaction_history.status = 1 
    AND transaction_history.status = 2 
    AND transaction_history.status = 3 
    AND service.home = 3
    ) as onprogressrentalcount");

    $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE DAY(tr.order_time) = $day
    AND transaction_history.status = 4
    AND service.home = 3
    ) as successrentalcountdaily");

    $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE DAY(tr.order_time) = $day
    AND transaction_history.status = 5
    AND service.home = 3
    ) as cancelrentalcountdaily");

    $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE DAY(tr.order_time) = $day
    AND transaction_history.status = 0
    AND service.home = 3
    ) as nodriverrentalcountdaily");

    $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE DAY(tr.order_time) = $day
    AND transaction_history.status = 1
    AND transaction_history.status = 2
    AND transaction_history.status = 3
    AND service.home = 3
    ) as onprogressrentalcountdaily");
    return $this->db->get();
  }

  public function countservicepurchasing()
  {
    $day = date('d');
    
    $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE transaction_history.status = 4
    AND service.home = 4
    ) as successpurchasingcount");

    $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE transaction_history.status = 5
    AND service.home = 4
    ) as cancelpurchasingcount");

    $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE transaction_history.status = 0
    AND service.home = 4
    ) as nodriverpurchasingcount");

    $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE transaction_history.status = 1 
    AND transaction_history.status = 2 
    AND transaction_history.status = 3 
    AND service.home = 4
    ) as onprogresspurchasingcount");

    $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE DAY(tr.order_time) = $day
    AND transaction_history.status = 4
    AND service.home = 4
    ) as successpurchasingcountdaily");

    $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE DAY(tr.order_time) = $day
    AND transaction_history.status = 5
    AND service.home = 4
    ) as cancelpurchasingcountdaily");

    $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE DAY(tr.order_time) = $day
    AND transaction_history.status = 0
    AND service.home = 4
    ) as nodriverpurchasingcountdaily");

    $this->db->select("(SELECT COUNT(tr.service_order) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE DAY(tr.order_time) = $day
    AND transaction_history.status = 1
    AND transaction_history.status = 2
    AND transaction_history.status = 3
    AND service.home = 4
    ) as onprogresspurchasingcountdaily");
    return $this->db->get();
  }

  public function totaltopupbyadmin()
    {
        $this->db->select('SUM(wallet_amount)as totaltopupbyadmin');
        $this->db->where('status', 1);
        $this->db->where('type', 'topup');
        $this->db->where('bank', 'admin');
        return $this->db->get('wallet')->row_array();
    }

    public function totaltopupbystripe()
    {
        $this->db->select('SUM(wallet_amount)as totaltopupbystripe');
        $this->db->where('status', 1);
        $this->db->where('type', 'topup');
        $this->db->where('bank', 'stripe');
        return $this->db->get('wallet')->row_array();
    }

    public function totaltopupbypaypal()
    {
        $this->db->select('SUM(wallet_amount)as totaltopupbypaypal');
        $this->db->where('status', 1);
        $this->db->where('type', 'topup');
        $this->db->where('bank', 'paypal');
        return $this->db->get('wallet')->row_array();
    }

    public function totaltopupbytransfer()
    {
        $this->db->select("(SELECT SUM(wlt.wallet_amount) FROM wallet wlt 
    WHERE wlt.status = 1
    AND wlt.type = 'topup'
    AND wlt.bank != 'admin'
    AND wlt.bank != 'stripe'
    AND wlt.bank != 'paypal'
    ) as totaltopupbytransfer");
    return $this->db->get();
    }

    public function getalltransactionvalue()
    {
    $this->db->select("(SELECT IFNULL(SUM(tr.final_cost), 0) FROM transaction tr 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    ) as totaltransactionvalue");
    return $this->db->get();
    }

    public function getcompletetransactionvalue()
    {
    $this->db->select("(SELECT IFNULL(SUM(tr.final_cost), 0) FROM transaction tr 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE transaction_history.status = 4
    ) as completetransactionvalue");
    return $this->db->get();
    }

    public function getcanceltransactionvalue()
    {
    $this->db->select("(SELECT IFNULL(SUM(tr.final_cost), 0) FROM transaction tr 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE transaction_history.status = 5
    ) as canceltransactionvalue");
    return $this->db->get();
    }

    public function getnodrivertransactionvalue()
    {
    $this->db->select("(SELECT IFNULL(SUM(tr.final_cost), 0) FROM transaction tr 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE transaction_history.status = 0
    ) as nodrivertransactionvalue");
    return $this->db->get();
    }

    public function completevaluebyservice($day, $month, $year)
  {
      $beforedaily = $day - 1;
      $beforemonthly = $month - 1;
      $beforeyearly = $year - 1;

      
    $this->db->select('service.service');
    $this->db->select("
                (SELECT IFNULL(SUM(tr.final_cost), 0)
                FROM transaction tr
                left join transaction_history on tr.id = transaction_history.transaction_id
                WHERE DAY(tr.finish_time) = $day
                AND tr.service_order = service.service_id
                AND transaction_history.status = 4) as daily
                ");
                $this->db->select("
                (SELECT IFNULL(SUM(tr.final_cost), 0)
                FROM transaction tr
                left join transaction_history on tr.id = transaction_history.transaction_id
                WHERE MONTH(tr.finish_time) = $month
                AND tr.service_order = service.service_id
                AND transaction_history.status = 4) as monthly
                ");
                $this->db->select("
                (SELECT IFNULL(SUM(tr.final_cost), 0)
                FROM transaction tr
                left join transaction_history on tr.id = transaction_history.transaction_id
                WHERE YEAR(tr.finish_time) = $year
                AND tr.service_order = service.service_id
                AND transaction_history.status = 4) as yearly
                ");

                $this->db->select("
                (SELECT IFNULL(SUM(tr.final_cost), 0)
                FROM transaction tr
                left join transaction_history on tr.id = transaction_history.transaction_id
                WHERE DAY(tr.finish_time) = $beforedaily
                AND tr.service_order = service.service_id
                AND transaction_history.status = 4) as beforedaily
                ");
                $this->db->select("
                (SELECT IFNULL(SUM(tr.final_cost), 0)
                FROM transaction tr
                left join transaction_history on tr.id = transaction_history.transaction_id
                WHERE MONTH(tr.finish_time) = $beforemonthly
                AND tr.service_order = service.service_id
                AND transaction_history.status = 4) as beforemonthly
                ");
                $this->db->select("
                (SELECT IFNULL(SUM(tr.final_cost), 0)
                FROM transaction tr
                left join transaction_history on tr.id = transaction_history.transaction_id
                WHERE YEAR(tr.finish_time) = $beforeyearly
                AND tr.service_order = service.service_id
                AND transaction_history.status = 4) as beforeyearly
                ");
    $this->db->from('service');
    $datacomplete = $this->db->get()->result_array();

        
        $data = array();
        foreach($datacomplete as $cmpt) {

            
            $lastestday = $cmpt['daily'] - $cmpt['beforedaily'];
            $lastestmonth = $cmpt['monthly'] - $cmpt['beforemonthly'];
            $lastestyear = $cmpt['yearly'] - $cmpt['beforeyearly'];
            
            if ($cmpt['beforedaily'] > 0) {
            if ($lastestday >= 0) {
                $icondaily = 'up';
                $totalday = $lastestday;
            } else {
                $icondaily = 'down';
                $totalday = $lastestday*-1;
            }
        } else {
            $icondaily = 'up';
                $totalday = $cmpt['beforedaily'];
        }

            if ($cmpt['beforemonthly'] > 0) {
            if ($lastestmonth >= 0) {
                $iconmonthly = 'up';
                $totalmonth = $lastestmonth;
            } else {
                $iconmonthly = 'down';
                $totalmonth = $lastestmonth*-1;
            }
        } else {
            $iconmonthly = 'up';
                $totalmonth = $cmpt['beforemonthly'];
        }

            if ($cmpt['beforeyearly'] > 0) {
            if ($lastestyear >= 0) {
                $iconyearly = 'up';
                $totalyear = $lastestyear;
            } else {
                $iconyearly = 'down';
                $totalyear = $lastestyear*-1;
            }
            } else {
                $iconyearly = 'up';
                $totalyear = $cmpt['beforeyearly'];
            }

            
            

        $data[] = array(
            'service' =>  $cmpt['service'],
            'daily' =>  $cmpt['daily'],
            'monthly' =>  $cmpt['monthly'],
            'yearly' =>  $cmpt['yearly'],
            'latestday' => $totalday,
            'icondaily' => $icondaily,
            'latestmonth' => $totalmonth,
            'iconmonthly' => $iconmonthly,
            'latestyear' => $totalyear,
            'iconyearly' => $iconyearly
            
        );
        
        }

        return $data;

  }

  public function servicevalue()
    {
        $this->db->select("(SELECT IFNULL(SUM(tr.final_cost), 0) FROM transaction tr 
        left join service on tr.service_order = service.service_id WHERE service.home = 1) as passangertranscount");
        $this->db->select("(SELECT IFNULL(SUM(tr.final_cost), 0) FROM transaction tr 
        left join service on tr.service_order = service.service_id WHERE service.home = 2) as shipmenttranscount");
        $this->db->select("(SELECT IFNULL(SUM(tr.final_cost), 0) FROM transaction tr 
        left join service on tr.service_order = service.service_id WHERE service.home = 3) as rentaltranscount");
        $this->db->select("(SELECT IFNULL(SUM(tr.final_cost), 0) FROM transaction tr 
        left join service on tr.service_order = service.service_id WHERE service.home = 4) as purchasingtranscount");
        return $this->db->get();
    }

    public function valueservicepassanger()
  {
    $day = date('d');
    
    $this->db->select("(SELECT IFNULL(SUM(tr.final_cost), 0) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE transaction_history.status = 4
    AND service.home = 1
    ) as successpassangercount");

    $this->db->select("(SELECT IFNULL(SUM(tr.final_cost), 0) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE transaction_history.status = 5
    AND service.home = 1
    ) as cancelpassangercount");

    $this->db->select("(SELECT IFNULL(SUM(tr.final_cost), 0) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE transaction_history.status = 0
    AND service.home = 1
    ) as nodriverpassangercount");

    $this->db->select("(SELECT IFNULL(SUM(tr.final_cost), 0) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE DAY(tr.order_time) = $day
    AND transaction_history.status = 4
    AND service.home = 1
    ) as successpassangercountdaily");

    $this->db->select("(SELECT IFNULL(SUM(tr.final_cost), 0) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE DAY(tr.order_time) = $day
    AND transaction_history.status = 5
    AND service.home = 1
    ) as cancelpassangercountdaily");

    $this->db->select("(SELECT IFNULL(SUM(tr.final_cost), 0) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE DAY(tr.order_time) = $day
    AND transaction_history.status = 0
    AND service.home = 1
    ) as nodriverpassangercountdaily");

    return $this->db->get();

  }

  public function valueserviceshipment()
  {
    $day = date('d');
    
    $this->db->select("(SELECT IFNULL(SUM(tr.final_cost), 0) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE transaction_history.status = 4
    AND service.home = 2
    ) as successshipmentcount");

    $this->db->select("(SELECT IFNULL(SUM(tr.final_cost), 0) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE transaction_history.status = 5
    AND service.home = 2
    ) as cancelshipmentcount");

    $this->db->select("(SELECT IFNULL(SUM(tr.final_cost), 0) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE transaction_history.status = 0
    AND service.home = 2
    ) as nodrivershipmentcount");

    $this->db->select("(SELECT IFNULL(SUM(tr.final_cost), 0) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE DAY(tr.order_time) = $day
    AND transaction_history.status = 4
    AND service.home = 2
    ) as successshipmentcountdaily");

    $this->db->select("(SELECT IFNULL(SUM(tr.final_cost), 0) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE DAY(tr.order_time) = $day
    AND transaction_history.status = 5
    AND service.home = 2
    ) as cancelshipmentcountdaily");

    $this->db->select("(SELECT IFNULL(SUM(tr.final_cost), 0) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE DAY(tr.order_time) = $day
    AND transaction_history.status = 0
    AND service.home = 2
    ) as nodrivershipmentcountdaily");

    return $this->db->get();

  }

  public function valueservicerental()
  {
    $day = date('d');
    
    $this->db->select("(SELECT IFNULL(SUM(tr.final_cost), 0) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE transaction_history.status = 4
    AND service.home = 3
    ) as successrentalcount");

    $this->db->select("(SELECT IFNULL(SUM(tr.final_cost), 0) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE transaction_history.status = 5
    AND service.home = 3
    ) as cancelrentalcount");

    $this->db->select("(SELECT IFNULL(SUM(tr.final_cost), 0) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE transaction_history.status = 0
    AND service.home = 3
    ) as nodriverrentalcount");

    $this->db->select("(SELECT IFNULL(SUM(tr.final_cost), 0) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE DAY(tr.order_time) = $day
    AND transaction_history.status = 4
    AND service.home = 3
    ) as successrentalcountdaily");

    $this->db->select("(SELECT IFNULL(SUM(tr.final_cost), 0) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE DAY(tr.order_time) = $day
    AND transaction_history.status = 5
    AND service.home = 3
    ) as cancelrentalcountdaily");

    $this->db->select("(SELECT IFNULL(SUM(tr.final_cost), 0) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE DAY(tr.order_time) = $day
    AND transaction_history.status = 0
    AND service.home = 3
    ) as nodriverrentalcountdaily");

    return $this->db->get();

  }

  public function valueservicepurchasing()
  {
    $day = date('d');
    
    $this->db->select("(SELECT IFNULL(SUM(tr.final_cost), 0) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE transaction_history.status = 4
    AND service.home = 4
    ) as successpurchasingcount");

    $this->db->select("(SELECT IFNULL(SUM(tr.final_cost), 0) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE transaction_history.status = 5
    AND service.home = 4
    ) as cancelpurchasingcount");

    $this->db->select("(SELECT IFNULL(SUM(tr.final_cost), 0) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE transaction_history.status = 0
    AND service.home = 4
    ) as nodriverpurchasingcount");

    $this->db->select("(SELECT IFNULL(SUM(tr.final_cost), 0) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE DAY(tr.order_time) = $day
    AND transaction_history.status = 4
    AND service.home = 4
    ) as successpurchasingcountdaily");

    $this->db->select("(SELECT IFNULL(SUM(tr.final_cost), 0) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE DAY(tr.order_time) = $day
    AND transaction_history.status = 5
    AND service.home = 4
    ) as cancelpurchasingcountdaily");

    $this->db->select("(SELECT IFNULL(SUM(tr.final_cost), 0) FROM transaction tr 
    left join service on tr.service_order = service.service_id 
    left join transaction_history on tr.id = transaction_history.transaction_id 
    WHERE DAY(tr.order_time) = $day
    AND transaction_history.status = 0
    AND service.home = 4
    ) as nodriverpurchasingcountdaily");

    return $this->db->get();

  }
}
