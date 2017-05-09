CREATE TABLE categories (
  id  int(11) NOT NULL auto_increment PRIMARY KEY,
  cat_name VARCHAR(128),
  description  VARCHAR(128),
  created_at DATETIME,
  updated_at DATETIME
)ENGINE=InnoDB;