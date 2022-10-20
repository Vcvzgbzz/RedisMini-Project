// Jadeyn Fincher -- 2022 QUIQ INTERNSHIP PROJECT
// +1 406-261-5152/jadeyn.fincher@gmail.com
//_________________________________________
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;



class NODE{

    String key;
    String value;

    long expired_time;

    public NODE() {


    }

}

class LIST{
    int number_of_items=0;
    String name= "";
    Stack<String> stacklist = new Stack<String>();


    public LIST() {

    }
}
public class Main {
    //static Stack<NODE> stack = new Stack<NODE>();
    //static Stack<LIST> listStack = new Stack<LIST>();
    static HashMap<String, NODE> HashMapKey = new HashMap<String, NODE>();

    static HashMap<String,LIST> HashMapList = new HashMap<String, LIST>();



    static void operateSet(String arr[], NODE node, boolean exists) {

    }


    public static void SET(String[] arr) {
        //for each option time, setif's

        boolean time_been_set = false;
        boolean setif_set = false;
        boolean time_saved = false;
        NODE node = new NODE();

        if(HashMapKey.containsKey(arr[1])==true) {
            node = HashMapKey.get(arr[1]);
        }


        NODE copy = new NODE();
        copy.key = node.key;
        copy.value = node.value;

        //Default code issue
        String CODEISSUE = "syntax error";

        String OUTPUTMESSAGE = "> OK";
        int total = 0; // Will be used to make sure all fields in command are being used by totalling the fields

        //COUNTS FOR THE KEY AND THE COMMAND SPOTS
        total++;
        total++;


        // FOR LOOP TO GRAB OPTIONAL COMMANDS THAT ARE INPUTTED
        for (int arrpos = 2; arrpos < arr.length; arrpos++) {

            if (arr[arrpos].toUpperCase().equals("GET")) {
                NODE search_node = HashMapKey.get(arr[1]);


                if (search_node != null) {
                    System.out.println(search_node.value);
                } else {
                    if(HashMapList.containsKey(arr[1])){
                        System.out.println("(error) WRONGTYPE Operation against a key holding the wrong kind of value");
                        return;
                    }else{
                        System.out.println("> (nil)");
                        return;
                    }

                }
                total++;
            }


            if (arr[arrpos].toUpperCase().equals("KEEPTTL")) {
                if (time_been_set != false) {
                    System.out.println("(error) more than one of the same type of modifying flag has been used");
                    return;
                } else {
                    time_saved = true;
                    time_been_set = true;
                }
                total++;

            }

            //NX -- Only set the key if it does not already exist.
            if (arr[arrpos].toUpperCase().equals("NX")) {
                if (setif_set != false) {
                    System.out.println("(error) more than one of the same type of modifying flag has been used");
                    return;
                } else {
                    if (node.key == null) {
                        node.key = arr[1];
                        node.value = arr[2];

                        //total++;
                    } else {

                        System.out.println("> (nil)");
                        return;
                    }

                    total++;
                    setif_set = true;
                }
            }
            // XX -- Only set the key if it already exists.
            if (arr[arrpos].toUpperCase().equals("XX")) {
                if (setif_set != false) {
                    System.out.println("(error) more than one of the same type of modifying flag has been used");
                    return;
                } else {
                    if (node.key != null) {
                        node.key = arr[1];
                        node.value = arr[2];


                    } else {
                        System.out.println("> (nil)");
                        return;
                    }
                    total++;
                    setif_set = true;
                }
            }

            if (node != null) {
                //Grabs EX value and increases counter by one to reduce redundant scans
                if (arr[arrpos].toUpperCase().equals("PX")) {
                    if (time_been_set != false) {
                        System.out.println("(error) more than one of the same type of modifying flag has been used");
                        return;
                    } else {
                        long miliseconds = System.currentTimeMillis() + Long.parseLong(arr[arrpos + 1]);
                        node.expired_time = miliseconds;
                        arrpos++;
                        total = total + 2;
                        time_been_set = true;
                    }
                }
                //Grabs PX value and increases counter by one to reduce redundant scans
                else if (arr[arrpos].toUpperCase().equals("EX")) {
                    if (time_been_set != false) {
                        System.out.println("(error) more than one of the same type of modifying flag has been used");
                        return;
                    } else {

                        long seconds = System.currentTimeMillis() + (Long.parseLong(arr[arrpos + 1]) * 1000);
                        node.expired_time = seconds;
                        arrpos++;
                        total = total + 2;
                        time_been_set = true;
                    }

                } else if (arr[arrpos].toUpperCase().equals("PXAT")) {
                    if (time_been_set != false) {
                        System.out.println("(error) more than one of the same type of modifying flag has been used");
                        return;
                    } else {


                        node.expired_time = Long.parseLong(arr[arrpos + 1]);

                        arrpos++;
                        total = total + 2;
                        time_been_set = true;
                    }

                } else if (arr[arrpos].toUpperCase().equals("EXAT")) {
                    if (time_been_set != false) {
                        System.out.println("(error) more than one of the same type of modifying flag has been used");
                        return;
                    } else {
                        node.expired_time = (Long.parseLong(arr[arrpos + 1]) * 1000);
                        arrpos++;
                        total = total + 2;
                        time_been_set = true;
                    }
                }
            }
        }

        //SETS THE VALUE OF THE NODE AFTER SCANNING HAS BEEN DONE AS THE GET ARGUMENT WOULD NOT BE ABLE TO RETRIEVE
        // THE OLD VALUE IF IT WAS SET BEFORE
        node.value = arr[2];


        //IF THE KEEPTTL FLAG WAS NOT SET AND NO OTHER EXPIRATION TIME WAS SET THEN THE VALUE WILL BE SET TO INFINITE
        if (time_saved != true && time_been_set != true) {
            node.expired_time = 0;
        }


        if (node != null) {

            if (node.key == null) {
                node.key = arr[1];
            }
            // Compare minus 1 because arr[0] holds the command
            if (total == arr.length - 1) {


                if (HashMapKey.containsKey(arr[1])) {
                    HashMapKey.put(node.key, node);
                    System.out.println(OUTPUTMESSAGE);
                } else {
                    HashMapKey.remove(node.key);
                    HashMapKey.put(node.key, node);
                    System.out.println(OUTPUTMESSAGE);
                }
            } else {


                System.out.println("(error) " + CODEISSUE);
            }
        }
    }

    public static void GET(String []arr) {
        boolean found = false;
        // DOESNT ALLOW FOR MORE THAN 1 KEY
        if (arr.length > 2) {
            System.out.println("(error) ERR wrong number of arguments for command");
            return;
        }

        NODE get_node = HashMapKey.get(arr[1]);
        if (HashMapKey.containsKey(arr[1]) == true) {
            //CHECKS IF THE OBJECT IS EXPIRED OR NOT OR IF THE VALUE WAS NOT SET MEANING IT WONT EXPIRE (0=NO EXPIRATION)
            if ((get_node.key.equals(arr[1]) && (get_node.expired_time - System.currentTimeMillis()) > 0)
                    || (get_node.key.equals(arr[1]) && get_node.expired_time == 0)) {
                System.out.println(get_node.value);
            }else {
                System.out.println("> (nil)");
        }


        }
        else {
            if(HashMapList.containsKey(arr[1])){
                System.out.println("(error) WRONGTYPE Operation against a key holding the wrong kind of value");
                return;
            }

            System.out.println("> (nil)");
        }
    }
    public static void DEL(String[] arr){
        int counter = 0;

            for(int cycle =1;cycle< arr.length;cycle++){
                if(HashMapKey.containsKey(arr[cycle])){
                    HashMapKey.remove(arr[cycle]);
                    counter++;
                }else if(HashMapList.containsKey(arr[cycle])){
                    HashMapList.remove(arr[cycle]);
                    counter++;
                }
            }


                    //LOOP THROUGH EACH ELEMENT IN ARR PAST SPOT 1
                    // SEE IF IT EXISTS AND IF IT DOES DELETE AND COUNTER++


        System.out.println("> (integer "+counter+")");
    }

    static void LPUSH(String arr[]){
        try {
            int count_added=0;
            LIST node_address_copy=null;
            int found_pos=-1;




            //IF A LIST WITH THE SAME NAME WAS ALREADY CREATED ONLY PUSH NEW KEYS ONTO THE EXISTING HASHMAP
            // DO NOT CREATE A NEW HASHMAP WITH SAME NAME
            if(HashMapList.containsKey(arr[1])){
                for(int arrcycle=2; arrcycle<arr.length;arrcycle ++){
                    HashMapList.get(arr[1]).stacklist.push(arr[arrcycle]);
                    HashMapList.get(arr[1]).number_of_items++;
                }
                node_address_copy=HashMapList.get(arr[1]);
                //COPIED THE MEMORY ADRESS TO ANOTHER OBJ TO BE ABLE TO OUTPUT NUM OF ITEMS IN HASHMAP
            }else {
                // IF A LIST WAS NOT FOUND CREATE A NEW ONE AND PUSH ALL KEYS ONTO IT
                LIST newList = new LIST();
                newList.name=arr[1];
                for (int arrcycle = 2; arrcycle < arr.length; arrcycle++) {

                    newList.stacklist.push(arr[arrcycle]);
                    newList.number_of_items++;
                }
                HashMapList.put(newList.name,newList);

                node_address_copy=newList;

            }




        System.out.println("(integer) "+node_address_copy.number_of_items);
        }catch(Exception e) {
            System.out.println("(error) syntax error"+e);
        }
    }
    static void LPOP(String[] arr){
        try{
        int found_pos=-1;
        //FIND IF THE LIST EXISTS TO PREFORM THE POP

        LIST search_node = HashMapList.get(arr[1]);
        if(HashMapList.containsKey(arr[1])) {
            //IF STACK OF ITEMS IN THE LIST IS EMPTY DO NOT TRY AND POP
            if (HashMapList.get(arr[1]).stacklist.empty()) {
                System.out.println("> (nil)");
            } else {

                if (arr.length == 2) {
                    //IF THERE IS NO ARGUMENT FOR AMOUNT DEFAULT TO ONE POP
                    System.out.println(search_node.stacklist.peek());
                    search_node.stacklist.pop();
                    search_node.number_of_items--;
                } else if (arr.length == 3) {
                    //PARSE 3RD INPUT FOR INT AND POP THAT MANY TIMES, ONLY TO MAX SIZE
                    int counter = 0;
                    //IF THE AMOUNT TO POP IS BIGGER THAN THE STACK SIZE DEFAULT TO THE STACK SIZE
                    if(Integer.parseInt(arr[2])>search_node.stacklist.size()){
                        arr[2]=search_node.stacklist.size()+"";
                    }

                    for (int loop_amount = Integer.parseInt(arr[2]); loop_amount > 0; loop_amount--) {

                        System.out.println(++counter + ") " + search_node.stacklist.peek());

                        search_node.stacklist.pop();
                        search_node.number_of_items--;
                    }
                }


            }
        }
            //IF THE LIST DOESNT EXIST OUTPUT NILL
            else{
                System.out.println("> (nil)");
            }

    }catch(Exception e) {
        System.out.println("(error) syntax error");
    }

    }
    static void LRANGE(String arr[]){
        try{
            if(arr.length==4) {





                LIST search_node = HashMapList.get(arr[1]);
                if (HashMapList.containsKey(arr[1])) {
                int start = (Integer.parseInt(arr[2]));
                int end = (Integer.parseInt(arr[3]));
                if (start < 0) {
                    //Incase of a negative add it to the size so that if you wanted to start 3 from the end you can say
                    // -3 which is going to be processed as n+(-3) which gives you that pos
                    start = search_node.stacklist.size() + start;
                }
                if (end < 0) {
                    end = search_node.stacklist.size() + end;
                }


                //IF THE START IS TOO FAR BACK EVEN AFTER THE SHIFT DEFAULT TO 0
                if (start < 0) {
                    start = 0;
                }
                if (end < 0) {
                    end = -1;
                }
                //IF THE SIZE OF EITHER START OR END IS LARGER THAN THE SIZE OF THE STACK STOP IT AT THE END
                if (end > search_node.stacklist.size()) {
                    end = search_node.stacklist.size();
                }
                if (start > search_node.stacklist.size()) {
                    start = search_node.stacklist.size();
                }
                //OUTPUT THE CONTROLLED RANGEl

                int counter = 0;
                //IF THE START IS LARGER THAN THE END AFTER PROCESSING
                if(start>end){
                    System.out.println("(empty array)");
                    return;
                }
                //IF END DOESNT EQUAL THE SIZE OF THE STACKLIST
                if(end!=search_node.stacklist.size()) {
                    end++;
                }
                //IF END == START AND == STACKLIST SIZE
                else if (start==end) {
                    System.out.println("(empty array)");
                    return;
                }
                //System.out.println("start: " + start + "| end: " + end);


                if(start ==0&&end==0){
                    System.out.println("(empty array)");
                    return;
                }

                for (start = start; start < end; start++) {

                    System.out.println(++counter + ") " + search_node.stacklist.elementAt(search_node.stacklist.size()-start-1));
                }

            }else{
                    System.out.println("> (nil)");
                    return;
                }
        }else{
            System.out.println("(error) syntax error");
        }
    }catch(Exception e) {
        System.out.println("(error) syntax error"+e);
    }
    }

    static void HSET(String arr[]){

    }



    public static void CommandLine(){
        try {

            System.out.println("> What Is your command?");
            System.out.print(">>");
            Scanner myScan = new Scanner(System.in);
            String line = myScan.nextLine();

            // Splits on spaces but groups strings within quotations
            String arr[] = line.split(" (?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            arr[0] = arr[0].toUpperCase();

            switch(arr[0]){
                case "SET":
                    SET(arr);
                    break;
                case "GET":
                    GET(arr);
                    break;
                case "DEL":
                    DEL(arr);
                    break;
                case "LPUSH":
                    LPUSH(arr);
                    break;
                case "LPOP":
                    LPOP(arr);
                    break;
                case "LRANGE":
                    LRANGE(arr);
                    break;

                case "OUT":
                    // OUTPUTS HASHMAPS

                    System.out.println(HashMapKey);
                    System.out.println(HashMapList);
                        break;

                default:
                    System.out.println("(error) unknown command '"+arr[0]+"'");
                    break;
                }


            arr=null;//makes sure no remanents are left during next pass
            CommandLine();
        }catch(Exception e){
            System.out.println("(error) syntax error");
            CommandLine();
        }
    }

    public static void main(String[] args) {
        System.out.println("Program Starting...");
        CommandLine();



    }


}