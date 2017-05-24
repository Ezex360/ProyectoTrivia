CREATE TABLE histories (
  id  int(11) NOT NULL auto_increment PRIMARY KEY,
  user_id  int(11),
  game_id int(11),
  question_id int(11),
  isCorrect boolean,
  created_at DATETIME,
  updated_at DATETIME
)ENGINE=InnoDB;
