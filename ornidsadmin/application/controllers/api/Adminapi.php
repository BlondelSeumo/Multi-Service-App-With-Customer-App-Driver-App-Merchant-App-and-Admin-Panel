<?php
//'tes' => number_format(200 / 100, 2, ",", "."),
defined('BASEPATH') or exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';
class Adminapi extends REST_Controller
{

    public function __construct()
    {
        parent::__construct();

        if ($this->session->userdata('user_name') == NULL && $this->session->userdata('password') == NULL) {
            redirect(base_url() . "login");
        }
        $this->load->helper("url");
        $this->load->database();
        $this->load->model('Dashboard_model', 'dasb');
        date_default_timezone_set(time_zone);
    }

    public function chart_user_get()
    {
        $firstday = $this->dasb->chartuser(date("d") - 6, date("m"), date("Y"));
        $secondday = $this->dasb->chartuser(date("d") - 5, date("m"), date("Y"));
        $thirdday = $this->dasb->chartuser(date("d") - 4, date("m"), date("Y"));
        $forthday = $this->dasb->chartuser(date("d") - 3, date("m"), date("Y"));
        $fifthday = $this->dasb->chartuser(date("d") - 2, date("m"), date("Y"));
        $sixthday = $this->dasb->chartuser(date("d") - 1, date("m"), date("Y"));
        $seventhday = $this->dasb->chartuser(date("d"), date("m"), date("Y"));

        $message = array(
            'code'              => '200',
            'message'           => 'success',
            'firstday'          => $firstday->row('chartuser'),
            'secondday'          => $secondday->row('chartuser'),
            'thirdday'          => $thirdday->row('chartuser'),
            'forthday'          => $forthday->row('chartuser'),
            'fifthday'          => $fifthday->row('chartuser'),
            'sixthday'          => $sixthday->row('chartuser'),
            'seventhday'         => $seventhday->row('chartuser')
        );
        $this->response($message, 200);
    }

    public function chart_driver_get()
    {
        $firstday = $this->dasb->chartdriver(date("d") - 6, date("m"), date("Y"));
        $secondday = $this->dasb->chartdriver(date("d") - 5, date("m"), date("Y"));
        $thirdday = $this->dasb->chartdriver(date("d") - 4, date("m"), date("Y"));
        $forthday = $this->dasb->chartdriver(date("d") - 3, date("m"), date("Y"));
        $fifthday = $this->dasb->chartdriver(date("d") - 2, date("m"), date("Y"));
        $sixthday = $this->dasb->chartdriver(date("d") - 1, date("m"), date("Y"));
        $seventhday = $this->dasb->chartdriver(date("d"), date("m"), date("Y"));

        $message = array(
            'code'              => '200',
            'message'           => 'success',
            'firstday'          => $firstday->row('chartdriver'),
            'secondday'          => $secondday->row('chartdriver'),
            'thirdday'          => $thirdday->row('chartdriver'),
            'forthday'          => $forthday->row('chartdriver'),
            'fifthday'          => $fifthday->row('chartdriver'),
            'sixthday'          => $sixthday->row('chartdriver'),
            'seventhday'         => $seventhday->row('chartdriver')
        );
        $this->response($message, 200);
    }

    public function chart_merchant_get()
    {
        $firstday = $this->dasb->chartmerchant(date("d") - 6, date("m"), date("Y"));
        $secondday = $this->dasb->chartmerchant(date("d") - 5, date("m"), date("Y"));
        $thirdday = $this->dasb->chartmerchant(date("d") - 4, date("m"), date("Y"));
        $forthday = $this->dasb->chartmerchant(date("d") - 3, date("m"), date("Y"));
        $fifthday = $this->dasb->chartmerchant(date("d") - 2, date("m"), date("Y"));
        $sixthday = $this->dasb->chartmerchant(date("d") - 1, date("m"), date("Y"));
        $seventhday = $this->dasb->chartmerchant(date("d"), date("m"), date("Y"));

        $message = array(
            'code'              => '200',
            'message'           => 'success',
            'firstday'          => $firstday->row('chartmerchant'),
            'secondday'          => $secondday->row('chartmerchant'),
            'thirdday'          => $thirdday->row('chartmerchant'),
            'forthday'          => $forthday->row('chartmerchant'),
            'fifthday'          => $fifthday->row('chartmerchant'),
            'sixthday'          => $sixthday->row('chartmerchant'),
            'seventhday'         => $seventhday->row('chartmerchant')
        );
        $this->response($message, 200);
    }

    public function chart_order_get()
    {
        $firstday = $this->dasb->chartorder(date("d") - 6, date("m"), date("Y"));
        $secondday = $this->dasb->chartorder(date("d") - 5, date("m"), date("Y"));
        $thirdday = $this->dasb->chartorder(date("d") - 4, date("m"), date("Y"));
        $forthday = $this->dasb->chartorder(date("d") - 3, date("m"), date("Y"));
        $fifthday = $this->dasb->chartorder(date("d") - 2, date("m"), date("Y"));
        $sixthday = $this->dasb->chartorder(date("d") - 1, date("m"), date("Y"));
        $seventhday = $this->dasb->chartorder(date("d"), date("m"), date("Y"));

        $message = array(
            'code'              => '200',
            'message'           => 'success',
            'firstday'          => $firstday->row('chartorder'),
            'secondday'          => $secondday->row('chartorder'),
            'thirdday'          => $thirdday->row('chartorder'),
            'forthday'          => $forthday->row('chartorder'),
            'fifthday'          => $fifthday->row('chartorder'),
            'sixthday'          => $sixthday->row('chartorder'),
            'seventhday'         => $seventhday->row('chartorder')
        );
        $this->response($message, 200);
    }

    public function chart_progress_get()
    {
        $totaltrans = $this->dasb->getprogresstotaltrans();
        $totalprogress = $this->dasb->getprogresstotalsuccess();

        if ($totaltrans != 0) {
            $total = $totalprogress / $totaltrans * 100;

            $message = array(
                'code'              => '200',
                'message'           => 'success',
                'progress'           => number_format($total, 0)
            );
            $this->response($message, 200);
        } else {
            $message = array(
                'code'              => '200',
                'message'           => 'success',
                'progress'           => number_format(0, 0)
            );
            $this->response($message, 200);
        }
    }

    public function chart_entireprogress_get()
    {
        $totaltrans = $this->dasb->getentireprogresstotaltrans();
        $totalprogress = $this->dasb->getentireprogresstotalsuccess();

        if ($totaltrans != 0) {
            $total = $totalprogress / $totaltrans * 100;

            $message = array(
                'code'              => '200',
                'message'           => 'success',
                'progress'           => number_format($total, 0)
            );
            $this->response($message, 200);
        } else {
            $message = array(
                'code'              => '200',
                'message'           => 'success',
                'progress'           => number_format(0, 0)
            );
            $this->response($message, 200);
        }
    }

    public function chart_transactions_get()
    {
        $data['jan1'] = $this->dasb->getchartdashboard(1, date('Y'), 1);
        $data['feb1'] = $this->dasb->getchartdashboard(2, date('Y'), 1);
        $data['mar1'] = $this->dasb->getchartdashboard(3, date('Y'), 1);
        $data['apr1'] = $this->dasb->getchartdashboard(4, date('Y'), 1);
        $data['mei1'] = $this->dasb->getchartdashboard(5, date('Y'), 1);
        $data['jun1'] = $this->dasb->getchartdashboard(6, date('Y'), 1);
        $data['jul1'] = $this->dasb->getchartdashboard(7, date('Y'), 1);
        $data['aug1'] = $this->dasb->getchartdashboard(8, date('Y'), 1);
        $data['sep1'] = $this->dasb->getchartdashboard(9, date('Y'), 1);
        $data['okt1'] = $this->dasb->getchartdashboard(10, date('Y'), 1);
        $data['nov1'] = $this->dasb->getchartdashboard(11, date('Y'), 1);
        $data['des1'] = $this->dasb->getchartdashboard(12, date('Y'), 1);

        $data['jan2'] = $this->dasb->getchartdashboard(1, date('Y'), 2);
        $data['feb2'] = $this->dasb->getchartdashboard(2, date('Y'), 2);
        $data['mar2'] = $this->dasb->getchartdashboard(3, date('Y'), 2);
        $data['apr2'] = $this->dasb->getchartdashboard(4, date('Y'), 2);
        $data['mei2'] = $this->dasb->getchartdashboard(5, date('Y'), 2);
        $data['jun2'] = $this->dasb->getchartdashboard(6, date('Y'), 2);
        $data['jul2'] = $this->dasb->getchartdashboard(7, date('Y'), 2);
        $data['aug2'] = $this->dasb->getchartdashboard(8, date('Y'), 2);
        $data['sep2'] = $this->dasb->getchartdashboard(9, date('Y'), 2);
        $data['okt2'] = $this->dasb->getchartdashboard(10, date('Y'), 2);
        $data['nov2'] = $this->dasb->getchartdashboard(11, date('Y'), 2);
        $data['des2'] = $this->dasb->getchartdashboard(12, date('Y'), 2);

        $data['jan3'] = $this->dasb->getchartdashboard(1, date('Y'), 3);
        $data['feb3'] = $this->dasb->getchartdashboard(2, date('Y'), 3);
        $data['mar3'] = $this->dasb->getchartdashboard(3, date('Y'), 3);
        $data['apr3'] = $this->dasb->getchartdashboard(4, date('Y'), 3);
        $data['mei3'] = $this->dasb->getchartdashboard(5, date('Y'), 3);
        $data['jun3'] = $this->dasb->getchartdashboard(6, date('Y'), 3);
        $data['jul3'] = $this->dasb->getchartdashboard(7, date('Y'), 3);
        $data['aug3'] = $this->dasb->getchartdashboard(8, date('Y'), 3);
        $data['sep3'] = $this->dasb->getchartdashboard(9, date('Y'), 3);
        $data['okt3'] = $this->dasb->getchartdashboard(10, date('Y'), 3);
        $data['nov3'] = $this->dasb->getchartdashboard(11, date('Y'), 3);
        $data['des3'] = $this->dasb->getchartdashboard(12, date('Y'), 3);

        $data['jan4'] = $this->dasb->getchartdashboard(1, date('Y'), 4);
        $data['feb4'] = $this->dasb->getchartdashboard(2, date('Y'), 4);
        $data['mar4'] = $this->dasb->getchartdashboard(3, date('Y'), 4);
        $data['apr4'] = $this->dasb->getchartdashboard(4, date('Y'), 4);
        $data['mei4'] = $this->dasb->getchartdashboard(5, date('Y'), 4);
        $data['jun4'] = $this->dasb->getchartdashboard(6, date('Y'), 4);
        $data['jul4'] = $this->dasb->getchartdashboard(7, date('Y'), 4);
        $data['aug4'] = $this->dasb->getchartdashboard(8, date('Y'), 4);
        $data['sep4'] = $this->dasb->getchartdashboard(9, date('Y'), 4);
        $data['okt4'] = $this->dasb->getchartdashboard(10, date('Y'), 4);
        $data['nov4'] = $this->dasb->getchartdashboard(11, date('Y'), 4);
        $data['des4'] = $this->dasb->getchartdashboard(12, date('Y'), 4);
        $message = array(
            'code'              => '200',
            'message'           => 'success',
            'jan1'          => $data['jan1'],
        );
        $this->response($data, 200);
    }

    function alldriver_get($id)
    {
        $near = $this->dasb->get_driver_location_admin();
        $message = array(
            'data' => $near->result()
        );
        $this->response($message, 200);
    }

    function allmerchant_get($id)
    {
        $near = $this->dasb->get_merchant_location_admin();
        $message = array(
            'data' => $near->result()
        );
        $this->response($message, 200);
    }

    function allservice_get($id)
    {
        $near = $this->dasb->alljob();
        $message = array(
            'data' => $near
        );
        $this->response($message, 200);
    }

    function allmerchantdonut_get($id)
    {
        $near = $this->dasb->alltype();
        $message = array(
            'data' => $near
        );
        $this->response($message, 200);
    }

    function revenuechart_get()
    {

        $jan = $this->revenuechartdata(1);
        $feb = $this->revenuechartdata(2);
        $mar = $this->revenuechartdata(3);
        $apr = $this->revenuechartdata(4);
        $mei = $this->revenuechartdata(5);
        $jun = $this->revenuechartdata(6);
        $jul = $this->revenuechartdata(7);
        $aug = $this->revenuechartdata(8);
        $sep = $this->revenuechartdata(9);
        $oct = $this->revenuechartdata(10);
        $nov = $this->revenuechartdata(11);
        $des = $this->revenuechartdata(12);

        $message = array(
            'jan' => $jan,
            'feb' => $feb,
            'mar' => $mar,
            'apr' => $apr,
            'mei' => $mei,
            'jun' => $jun,
            'jul' => $jul,
            'aug' => $aug,
            'sep' => $sep,
            'oct' => $oct,
            'nov' => $nov,
            'des' => $des,
        );
        $this->response($message, 200);
    }

    function revenuechartdata($m)
    {
        $orderplus1 = $this->dasb->gettotalorderplus($m)->row('totalorderplus');
        $ordermin1 = $this->dasb->gettotalordermin($m)->row('totalordermin');
        return ($ordermin1 - $orderplus1);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////


}
