CREATE TABLE IF NOT EXISTS MESSAGES(
    id int,
    "from" text,
    subject text,
    body text,
    PRIMARY KEY (id)
);

ALTER TABLE messages ADD headers BLOB;

INSERT INTO messages (id, "from", subject, body) VALUES(1, 'Adrian', 'Hi', 'How are you?');
INSERT INTO messages (id, "from", subject, body) VALUES(2, 'Nico', 'Hello', 'I am good!');
INSERT INTO messages (id, "from", subject, body) VALUES(3, 'Char', 'Me too!', 'Me too!');
