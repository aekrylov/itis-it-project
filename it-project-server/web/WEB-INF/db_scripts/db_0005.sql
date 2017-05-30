ALTER TABLE public.feedbacks DROP buy_sell;

ALTER TABLE public.users ADD rate_count INT DEFAULT 0 NOT NULL;

DROP TABLE messages;
DROP TABLE conversations;

CREATE TABLE messages (
  id INTEGER PRIMARY KEY,
  "from" INTEGER REFERENCES public.users (id),
  text TEXT,
  date TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
  read BOOLEAN NOT NULL DEFAULT false,
  "to" INTEGER REFERENCES public.users (id)
);
CREATE INDEX messages_from_to_index ON messages USING BTREE ("from", "to");