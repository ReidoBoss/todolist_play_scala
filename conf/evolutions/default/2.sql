-- !Ups
INSERT INTO "USERS" ("USERNAME","PASSWORD")VALUES(
	'jhong',
	'$2a$10$EejweEVuC4Wjbo6OyXZTseqQEQoUL1U9OTMQzOcGlNXKwk.cph222'
);
-- !Downs
DELETE FROM "USERS_AUTH";
DELETE FROM "USERS";

