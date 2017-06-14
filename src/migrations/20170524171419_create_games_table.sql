CREATE TABLE games (
  id  int(11) NOT NULL auto_increment PRIMARY KEY,
  user_id  int(11),
  lifes int default 3,
  consecutives int default 0,
  status boolean default true,
  created_at DATETIME,
  updated_at DATETIME
)ENGINE=InnoDB;
