
Documentation:


Data Structuring:

The main data structure used is a stack throughout the entirety of this program. I used this to simplify the push and pop for LPOP and LPUSH and able to use it as a linked list for searching if need be.

Lists are stored as a “LIST” object with a stack within itself to add as many items as wanted within the list

Keys/values for GET,SET and DEL are stored as “NODE” objects and just allows for the simplification of placing data

Both LIST and NODE objects have a global stack that holds all of the ones created for that is referenced for Adding/Deleting/Modifying/Searching throughout the program


Since Lists and Nodes are stored as separately this allows to have lists and keys/vals with the same name such as a key being set to 1 and a list name being 1. This was not possible within the example webpage, but I thought it to be useful. On the webpage if you created a list with name 1 and then created a key with name one it would nuke the list.








Handling Expiring Keys:

For expiring keys with the commands: EX,PX,EXAT,PXAT. Each node has a variable within itself which is the time in EPOCH time in milliseconds that the key will expire. Any of the options to set the expiration time will convert to this before setting the time to expire. Such as PX 10 meaning that you set the key to expire in 10 seconds it will first multiply that by 1000 to get the milliseconds and then add that to the system time. This is only checked with GET where it checks to see if the expiration time minus the system time is less than 0 meaning is has 0 time left of being live meaning its expired.

With this method you can recover expired keys by setting their expiration out further with a set command. I was going to add a function to delete the key completely if it was expired but I left it as it to archive past keys.



Commands and syntax:

Reference:
https://redis.io/commands/

For GET,SET,DEL,LPUSH,LPOP,LRANGE




