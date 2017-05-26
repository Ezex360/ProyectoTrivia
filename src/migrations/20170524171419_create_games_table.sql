CREATE TABLE games (
  id  int(11) NOT NULL auto_increment PRIMARY KEY,
  user_id  int(11),
  lifes int,
  status boolean,
  created_at DATETIME,
  updated_at DATETIME
)ENGINE=InnoDB;
