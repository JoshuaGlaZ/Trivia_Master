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

  $sql = "INSERT INTO players(name, score, difficulty, type) VALUES(?,?,?,?)";
  $stmt = $c -> prepare($sql);
  $stmt -> bind_param("siss",
    $_POST["name"],
    $_POST["score"],
    $_POST["difficulty"],
    $_POST["type"]);
  $stmt -> execute();
  $arr = array("result" => "OK", "sql" => $sql, "messages" => "add player");
  echo json_encode($arr);
?>