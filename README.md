CREATE TABLE IF NOT EXISTS posts (
	id       INT           NOT NULL AUTO_INCREMENT,
	message  TEXT          NOT NULL,
	postDate TIMESTAMP     NOT NULL,
	userName VARCHAR(25)   NOT NULL,
	PRIMARY KEY (id)
);

GRANT ALL PRIVILEGES ON guestbook.* TO app@localhost
IDENTIFIED BY 'app' WITH GRANT OPTION;
