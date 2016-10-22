CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  username VARCHAR(32) UNIQUE,
  password VARCHAR(32),
  "name" VARCHAR(64),
  photo VARCHAR(128),
  rating DECIMAL
);

CREATE TYPE product_type AS ENUM ('desktop', 'laptop', 'aio');

CREATE TABLE products (
  id SERIAL PRIMARY KEY,
  "name" VARCHAR(64),
  "price" INT,
  "description" TEXT,
  "photo" VARCHAR(128),
  "brand" VARCHAR(32),
  "model" VARCHAR(32),
  cpu_name VARCHAR(32),
  cores INT,
  ram_gb INT,
  video_card VARCHAR(64),
  hdd_name VARCHAR(64),
  hdd_capacity INT,
  "type" product_type
);

CREATE TABLE posts (
  id SERIAL PRIMARY KEY,
  "user" int REFERENCES users(id),
  product int REFERENCES products(id),
  "date" datetime DEFAULT now()
);

CREATE TABLE buy_sells (
  id      SERIAL PRIMARY KEY,
  buyer   INT REFERENCES users (id),
  selller INT REFERENCES users (id),
  product INT REFERENCES products (id)
);

CREATE TABLE feedbacks (
  id SERIAL PRIMARY KEY,
  buy_sell INT REFERENCES buy_sells(id),
  comment TEXT,
  score int,
  "date" datetime DEFAULT now()
);

CREATE TABLE favorites (
  id SERIAL PRIMARY KEY,
  product int REFERENCES products(id),
  "user" int REFERENCES users(id),
  "date" datetime DEFAULT now()
);

CREATE TABLE conversations (
  id SERIAL PRIMARY KEY,
  user1 int REFERENCES users(id),
  user2 int REFERENCES users(id)
);

CREATE TABLE messages (
  id INT PRIMARY KEY ,
  "from" int REFERENCES users(id),
  "text" TEXT,
  "date" DATEtime DEFAULT now()
);