<?php
require APPPATH . '/libraries/REST_Controller.php';
class Al_quran extends REST_Controller {
  function __construct($config = 'rest') {
      parent::__construct($config);
  }

  function index_get(){
    if($this->get("cari") != NULL){
      $cari = $this->get("cari");
      $sql = "SELECT terjemahan.*, daftarsurat.*, arabic.* FROM terjemahan
      LEFT JOIN daftarsurat ON terjemahan.nm_surat = daftarsurat.index
      LEFT JOIN arabic ON terjemahan.id = arabic.id";
      $quran = $this->db->query($sql)->result();
      $datas=array();
      foreach ($quran as $key => $value) {
        $Arti = KMP($cari,$value->arti);
        $NoAyat = $value->no_ayat;
        $NamaSurah = KMP($cari,$value->surat_indonesia);
        if($Arti != NULL){
          $datas[] = array(
            "no_surah"=>$value->nm_surat,
            "no_ayat"=>$value->no_ayat,
            "nama_surah"=>$value->surat_indonesia,
            "text_arab"=>$value->arab,
            "text_indo"=>$value->arti
          );
        }
        else if($NoAyat === $cari){
          $datas[] = array(
            "no_surah"=>$value->nm_surat,
            "no_ayat"=>$value->no_ayat,
            "nama_surah"=>$value->surat_indonesia,
            "text_arab"=>$value->arab,
            "text_indo"=>$value->arti
          );
        }
        else if($NamaSurah != NULL){
          $datas[] = array(
            "no_surah"=>$value->nm_surat,
            "no_ayat"=>$value->no_ayat,
            "nama_surah"=>$value->surat_indonesia,
            "text_arab"=>$value->arab,
            "text_indo"=>$value->arti
          );
        }
      }
      if(count($datas) != NULL){
        $this->response($datas, 200);
      }
      else{
        $datas = array("no_surah"=>0);
        $this->response(array($datas),200);
      }
    }
    else if($this->get("all") != NULL){
      $sql = "SELECT terjemahan.*, daftarsurat.*, arabic.* FROM terjemahan
      LEFT JOIN daftarsurat ON terjemahan.nm_surat = daftarsurat.index
      LEFT JOIN arabic ON terjemahan.id = arabic.id
      GROUP BY daftarsurat.surat_indonesia
      ORDER BY daftarsurat.index ASC
      ";
      $quran = $this->db->query($sql)->result();
      $datas=array();
      foreach ($quran as $key => $value) {
        $datas[] = array(
          "no_surah"=>$value->nm_surat,
          "no_ayat"=>$value->no_ayat,
          "nama_surah"=>$value->surat_indonesia,
          "text_arab"=>$value->arab,
          "text_indo"=>$value->arti
        );
      }
      if(count($datas) != NULL){
        $this->response($datas, 200);
      }
      else{
        $datas = array("no_ayat"=>0);
        $this->response(array($datas),200);
      }
    }
    else if($this->get("noSurah") != NULL){
      $NoSurah = $this->get("noSurah");
      $sql = "SELECT terjemahan.*, daftarsurat.*, arabic.* FROM terjemahan
      LEFT JOIN daftarsurat ON terjemahan.nm_surat = daftarsurat.index
      LEFT JOIN arabic ON terjemahan.id = arabic.id
      WHERE terjemahan.nm_surat='$NoSurah'
      ORDER BY terjemahan.no_ayat ASC
      ";
      $quran = $this->db->query($sql)->result();
      $datas=array();
      foreach ($quran as $key => $value) {
        $datas[] = array(
          "no_surah"=>$value->nm_surat,
          "no_ayat"=>$value->no_ayat,
          "nama_surah"=>$value->surat_indonesia,
          "text_arab"=>$value->arab,
          "text_indo"=>$value->arti
        );
      }
      if(count($datas) != NULL){
        $this->response($datas, 200);
      }
      else{
        $datas = array("no_ayat"=>0);
        $this->response(array($datas),200);
      }      
    }
  }
}
