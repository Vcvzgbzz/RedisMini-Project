
Documentation:


Data Structuring:

Newest version uses a hash map for GET/SET/DEL and a main Hashmap for LPUSH,LPOP,LRANGE. Original Coding used a stack for GET/SET/DEL. This was changed to reduce time complexity for GET/SET/DEL searching from O(n) to O(1).

There are two main Hashmaps one for lists and one for key/vals

There is a stack within each LIST type for LPUSH/LPOP/LRANGE

Lists and key/vals are stored separately within this solution


Handling Expiring Keys:

For expiring keys with the commands: EX,PX,EXAT,PXAT. Each node has a variable within itself which is the time in EPOCH time in milliseconds that the key will expire. Any of the options to set the expiration time will convert to this before setting the time to expire. Such as EX 10 meaning that you set the key to expire in 10 seconds it will first multiply that by 1000 to get the milliseconds and then add that to the system time. This is only checked with GET where it checks to see if the expiration time minus the system time is less than 0 meaning is has 0 time left of being live meaning its expired.

With this method you can recover expired keys by setting their expiration out further with a set command. I was going to add a function to delete the key completely if it was expired but I left it as it to archive past keys.



Commands and syntax:

Reference:
https://redis.io/commands/

For GET,SET,DEL,LPUSH,LPOP,LRANGE




If I was going to do it again I would have the functions within classes and have the equivalent list and node extend a different class to make this more expandable. 
