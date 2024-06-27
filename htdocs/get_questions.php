<?php
  error_reporting(0);

  try {
    $c = new mysqli("localhost", "root", "","jkr_master");

  } catch(Exception $e) {
    $error = array(
      'result' => "ERROR",
      "message" => "failed to connect DB");
    echo json_encode($error);
    die();
  }
  $c->set_charset("UTF8");
  $sql = "SELECT * FROM questions;";
  $result = $c->query($sql);
  $questions = array();
  if($result -> num_rows > 0) {
    while ($obj = $result -> fetch_object()) {
      $questions[] = $obj;
    }
    echo json_encode(array("result"=>"OK", "data" => $questions));
  } else {
    echo json_encode(array("result"=>"ERROR", "message" => "No data found"));
    die();
  }
?>