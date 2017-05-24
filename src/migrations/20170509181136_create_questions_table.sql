CREATE TABLE questions (
  id  int(11) NOT NULL auto_increment PRIMARY KEY,
  category_id int(11),
  question VARCHAR(256),
  answer1 VARCHAR(256),
  answer2 VARCHAR(256),
  answer3 VARCHAR(256),
  answer4 VARCHAR(256),
  rightans smallint,  
  created_at DATETIME,
  updated_at DATETIME
)ENGINE=InnoDB;
