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

  if (isset($_POST['difficulty']) && isset($_POST['type'])) {
    $difficulty = $_POST['difficulty'];
    $type = $_POST['type'];
    $sql = "SELECT * FROM questions WHERE difficulty=? AND type=? ORDER BY RAND() LIMIT 1;";
    $stmt = $c -> prepare($sql);
    $stmt -> bind_param("ss",
      $difficulty,
      $type
    );
    $stmt -> execute();
    // $result = $c->query($sql);
    $result = $stmt -> get_result();

    if($questions = $result->fetch_object()) {
      echo json_encode(array("result"=>"OK", "data" => $questions));
    } else {
      echo json_encode(array("result"=>"ERROR", "message" => "No data found"));
      die();
    }
  } else {
    echo json_encode(array("result" => "ERROR", "messages" => "difficulty & type is required"));
    die();
  }
?>