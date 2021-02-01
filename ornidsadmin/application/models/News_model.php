<?php


class News_model extends CI_model
{
    public function getallnewscategory()
    {
        return  $this->db->get('news_category')->result_array();
    }

    public function getAllnews()
    {
        $this->db->join('news_category', 'news.category_id = news_category.news_category_id', 'left');
        return  $this->db->get('news')->result_array();
    }

    public function adddatakategori($data)
    {
        return $this->db->insert('news_category', $data);
    }

    public function editdatakategori($data, $id)
    {
        $this->db->where('news_category_id', $id);
        return $this->db->update('news_category', $data);
    }

    public function addnews($data)
    {
        return $this->db->insert('news', $data);
    }

    public function getnewsbyid($id)
    {
        return $this->db->get_where('news', ['news_id' => $id])->row_array();
    }

    public function editdataberita($data)
    {
        $this->db->where('news_id', $data['news_id']);
        return $this->db->update('news', $data);
    }

    public function deleteberitabyId($id)
    {
        $this->db->where('news_id', $id);
        return $this->db->delete('news');
    }

    public function deletekategoribyId($id)
    {
        $this->db->where('news_category_id', $id);
        return $this->db->delete('news_category');
    }
}
