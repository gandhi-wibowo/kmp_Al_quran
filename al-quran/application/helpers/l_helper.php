<?php
define( 'API_ACCESS_KEY', "");
function FCM($token,$title,$isi){
  $msg = array(
   'body' 	=> $isi,
   'title'	=> $title,
   'icon'=>'ic_launcher',
   'sound'=>'RingtoneManager.TYPE_NOTIFICATION'
 );
 $fields = array(
   'to'		=> $token,
   'notification'	=> $msg
 );
 $headers = array(
   'Authorization: key=' . API_ACCESS_KEY,
  'Content-Type: application/json'
 );
 $ch = curl_init();
 curl_setopt( $ch,CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send' );
 curl_setopt( $ch,CURLOPT_POST, true );
 curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
 curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
 curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
 curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
 $result = curl_exec($ch );
 curl_close( $ch );
}
function tanggal(){
  $datestring = '%Y-%m-%d';
  $time = time();
  return mdate($datestring,$time);
}
function waktu(){
  $datestring = '%G:%i:%s';
  $time = time();
  return mdate($datestring,$time);
}
function DateTime(){
  $datestring = '%Y-%m-%d %G:%i:%s';
  $time = time();
  return mdate($datestring,$time);
}
function cekHp($hp){
  // if no hp udah
  $ini =& get_instance();
  $ini->db->where(array('hp_user'=> $hp));
  $user = $ini->db->get('tb_users');
  if($user->num_rows() > 0){
    // sudah terdaftar
    return FALSE;
  }
  else{
    return TRUE;
  }
}
function cekValue($tabel,$field,$param){
  // cek apakah $param sudah ada di $tabel dengan $field ini
  $ini =& get_instance();
  $ini->db->where(array($field=> $param));
  $user = $ini->db->get($tabel);
  if($user->num_rows() > 0){
    return FALSE;
  }
  else{
    return TRUE;
  }
}
function LastCode($table,$field){
  $ini =& get_instance();
  $query = $ini->db->query("SELECT ".$field." FROM ".$table." ORDER BY ".$field." DESC LIMIT 1");
  if($query->num_rows()>0){
    $row = $query->row();
    return $row->$field;
  }
  else{
    return 0;
  }
  // kalau database kosong dari awal, muncul error
}


function Code($f,$rec,$char,$much){
  // $f == $first Code ADM-20081992
  // $rec == id sebelumnya yg di dapat dari id dari table
  // $char == dimulai dari karakter keberapa ?
  // $much == berapa banyak yg di ambil
  $stringDate = '%y%m%d';
  $date = mdate($stringDate,time());
  $code = substr($rec,$char,$much)+1;
  $newCode = $f."-".$date."-".sprintf("%03s",$code);
  return $newCode;
}

function Password($value){
  $salt = mcrypt_create_iv(22,MCRYPT_DEV_URANDOM);
  return password_hash($value,PASSWORD_BCRYPT,array('cost'=>10,'salt'=>$salt));
}

function CekPassword($input,$hash){
  if(password_verify($input,$hash)){
    return TRUE;
  }
  else{
    return FALSE;
  }
}

function Login($email,$password){
  $ini =& get_instance();
  if($user = $ini->Model_user->CekEmail($email)){
    if(CekPassword($password,$user->user_password)){
      $data = array(
        'login_status'=>'1',
        'user_name'=>$user->user_nama,
        'user_email'=>$user->user_email,
        'user_id'=>$user->user_id
      );
      $ini->session->set_userdata($data);
      $ini->session->set_flashdata(array('class'=>'success','alert'=>'Berhasil','value'=>'Selamat datang '.$cek->user_nama));
      Redirect('Welcome');
    }
    else{
      $ini->session->set_flashdata(array('class'=>'danger','alert'=>'Gagal','value'=>'Password yang anda masukkan salah !'));
      Redirect('Welcome/Login');
    }
  }
  else{
    $ini->session->set_flashdata(array('class'=>'danger','alert'=>'Gagal','value'=>'Email yang anda masukkan belum terdaftar !'));
    Redirect('Welcome/Login');
  }
}

function CekLogin(){
  $ini =& get_instance();
  if($ini->session->userdata('login_status') == 1){
    // kalau sudah login
  }
  else{
    //kalau belum login
    $ini->session->set_flashdata(array('class'=>'danger','alert'=>'!','value'=>'Harus Login terlebih dahulu !'));
    Redirect('Welcome/Login');
  }
}

function reArrayFiles($file){
  $file_ary = array();
  $file_count = count($file['name']);
  $file_key = array_keys($file);
  for($i=0;$i<$file_count;$i++){
    foreach ($file_key as $val) {
      $file_ary[$i][$val] = $file[$val][$i];
    }
  }
  return $file_ary;
}

function uplodGambar(){
  $ini =& get_instance();
  $folder = "img/";
  $date   = date('Ymd His');
  $name   = str_replace(" ","_",$date);
  $img = $_FILES['images'];
  if($img['tmp_name'] != NULL){
    $fileinfo = pathinfo($_FILES['images']['name']);
    $newName = $name.'.'.$fileinfo['extension'];
    $gambar = $folder.basename($newName);
    if(move_uploaded_file($img['tmp_name'],$gambar)){
      return $newName;
    }
  }
  else{
    return FALSE;
  }
}

function Upl64(){
  $image = $_POST['image'];
  $date   = date('Ymd His');
  $name   = str_replace(" ","_",$date);
  $path = "img/".$name.".jpg";
  if(file_put_contents($path,base64_decode($image))){
    $newName = $name.".jpg";
    return $newName;
  }
  else{
    return FALSE;
  }
}

function KMP($p,$t){
  $result = array();
  $pattern = str_split($p);
  $text    = str_split($t);
  $prefix = preKMP($pattern);
  $i = $j = 0;
  $num=0;
  while($j<count($text)){
    while($i>-1 && $pattern[$i]!=$text[$j]){
   // if it doesn't match, then uses then look at the prefix table
      $i = $prefix[$i];
    }
    $i++;
    $j++;
    if($i>=count($pattern)){
   // if its match, find the matches string potition
// Then use prefix table to swipe to the right.
      $result[$num++]=$j-count($pattern);
      $i = $prefix[$i];
    }
  }
  return $result;
}

// Making Prefix table with preKMP function
function preKMP($pattern){
  $i = 0;
  $j = $prefix[0] = -1;
  while($i<count($pattern)){
    while($j>-1 && $pattern[$i]!=$pattern[$j]){
      $j = $prefix[$j];
    }
    $i++;
    $j++;
    if(isset($pattern[$i])==isset($pattern[$j])){
      $prefix[$i]=$prefix[$j];
    }else{
      $prefix[$i]=$j;
    }
  }
  return $prefix;
}

 ?>
