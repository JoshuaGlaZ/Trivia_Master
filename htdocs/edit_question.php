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

  if (isset($_POST['id'])) {
    $id = (int) $_POST['id'];
    $sql = "UPDATE questions SET question=?, answer_a=?, answer_b=?, answer_c=?, answer_d=?,
      correct_answer=? WHERE id=?;";
    $stmt = $c -> prepare($sql);
    $stmt -> bind_param("ssssssi",
      $_POST["question"],
      $_POST["answer_a"],
      $_POST["answer_b"],
      $_POST["answer_c"],
      $_POST["answer_d"],
      $_POST["correct_answer"],
      $id
    );
    $stmt -> execute();
    $arr = array("result" => "OK", "sql" => $sql, "messages" => "question edited");
    echo json_encode($arr);
  } else {
    $arr = array("result" => "ERROR", "messages" => "id is required");
    echo json_encode($arr);
  }
?>