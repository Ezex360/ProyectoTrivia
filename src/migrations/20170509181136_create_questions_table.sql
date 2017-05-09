CREATE TABLE questions (
  id  int(11) NOT NULL auto_increment PRIMARY KEY,
  category_id int(11),
  question VARCHAR(256),
  answer1 VARCHAR(256),
  answer2 VARCHAR(256),
  answer3 VARCHAR(256),
  answer4 VARCHAR(256),
  rightans INTEGER,  
  created_at DATETIME,
  updated_at DATETIME,
  CHECK (rightans>0 && rightans<5),
  FOREIGN KEY (category_id) REFERENCES categories(id)
)ENGINE=InnoDB;