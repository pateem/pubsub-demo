# About

Sample Java FIFO producer–consumer that processes simple commands and persists users in an embedded
H2 database. No
Spring is used.

Supported commands:

- `Add (id, "guid", "name")` — insert a user
- `PrintAll` — print all users to standard output
- `DeleteAll` — remove all users

Data model:

- Table: `SUSERS`
- Columns: `USER_ID` (BIGINT), `USER_GUID` (VARCHAR), `USER_NAME` (VARCHAR)

