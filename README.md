# room-relationship-collate-nocase

This repository contains minimal code to reproduce a room relationship with collate no case related
issue.

Link: (Issue Tracker)[https://issuetracker.google.com/issues/326258556]

## description

One to many relation between User & Books. Field "id" is marked as collate = ColumnInfo.NOCASE in
User & "uid" in Book entity.

Prepopulated data:
![User table](/images/user-table.png)
![Book table](/images/book-table.png)

Actual:
Query books with Nils, nils or NILS results in the same list of books
Query user with books with Nils, nils or NILS results in different sized lists.

Expected:
Query user with books with Nils, nils or NILS results

See also DatabaseTest.kt

## Run test task

`gradle :app:testDebugUnitTest`

