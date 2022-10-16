// Jadeyn Fincher -- 2022 QUIQ INTERNSHIP PROJECT
// +1 406-261-5152/jadeyn.fincher@gmail.com
//_________________________________________
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

    public void add(String key){
        stacklist.push(key);
    }
    public LIST() {
        Stack<String> stacklist = new Stack<String>();

    }
}
public class Main {
    static Stack<NODE> stack = new Stack<NODE>();
    static Stack<LIST> listStack = new Stack<LIST>();



    static void operateSet(String arr[], NODE node,boolean exists,int pos){
        //for each option time, setif's

        boolean time_been_set =false;
        boolean setif_set =false;
        boolean time_saved=false;



        NODE copy = new NODE();
        copy.key = node.key;
        copy.value = node.value;

        //Default code issue
        String CODEISSUE="syntax error";

        String OUTPUTMESSAGE = "> OK";
        int total=0; // Will be used to make sure all fields in command are being used by totalling the fields

        //COUNTS FOR THE KEY AND THE COMMAND SPOTS
        total++;
        total++;


        // FOR LOOP TO GRAB OPTIONAL COMMANDS THAT ARE INPUTTED
        for(int arrpos = 2; arrpos<arr.length;arrpos++) {

            if (arr[arrpos].toUpperCase().equals("GET")) {
                int get_search=-1;
                for(int search=0;search<stack.size();search++){
                    if (stack.elementAt(search).key.equals(arr[1])){
                        get_search=search;
                    }
                }


                if(get_search>=0){
                    System.out.println(stack.elementAt(get_search).value);

                }else{
                    System.out.println("> (nil)");
                    return;
                }
                total++;
            }


            if (arr[arrpos].toUpperCase().equals("KEEPTTL")) {
                if(time_been_set!=false){
                    System.out.println("(error) more than one of the same type of modifying flag has been used");
                    return;
                }else{
                    time_saved=true;
                    time_been_set=true;
                }
                total++;

            }

            //NX -- Only set the key if it does not already exist.
            if (arr[arrpos].toUpperCase().equals("NX")) {
                if(setif_set!=false){
                    System.out.println("(error) more than one of the same type of modifying flag has been used");
                    return;
                }else {
                    if (node.key == null) {
                        node.key = arr[1];
                        node.value = arr[2];
                        //total++;
                    } else {

                        OUTPUTMESSAGE = "> (nil)";
                        node = copy;
                    }

                    total++;
                    setif_set=true;
                }
            }
            // XX -- Only set the key if it already exists.
            if (arr[arrpos].toUpperCase().equals("XX")) {
                if(setif_set!=false){
                    System.out.println("(error) more than one of the same type of modifying flag has been used");
                    return;
                }else {
                    if (node.key != null) {
                        node.key = arr[1];
                        node.value = arr[2];


                    } else {
                        System.out.println("> (nil)");
                        node = null;
                    }
                    total++;
                    setif_set=true;
                }
            }

            if(node!=null) {
            //Grabs EX value and increases counter by one to reduce redundant scans
            if (arr[arrpos].toUpperCase().equals("PX")) {
                if (time_been_set != false) {
                    System.out.println("(error) more than one of the same type of modifying flag has been used");
                    return;
                }else{
                long miliseconds = System.currentTimeMillis() + Long.parseLong(arr[arrpos + 1]);
                node.expired_time = miliseconds;
                arrpos++;
                total = total + 2;
                time_been_set = true;
            }
            }
            //Grabs PX value and increases counter by one to reduce redundant scans
            else if (arr[arrpos].toUpperCase().equals("EX")) {
                if(time_been_set!=false){
                    System.out.println("(error) more than one of the same type of modifying flag has been used");
                    return;
                }else {

                    long seconds = System.currentTimeMillis() + (Long.parseLong(arr[arrpos + 1]) * 1000);
                    node.expired_time = seconds;
                    arrpos++;
                    total = total + 2;
                    time_been_set=true;
                }

            } else if (arr[arrpos].toUpperCase().equals("PXAT")) {
                if(time_been_set!=false){
                    System.out.println("(error) more than one of the same type of modifying flag has been used");
                    return;
                }else {


                    node.expired_time = Long.parseLong(arr[arrpos + 1]);

                    arrpos++;
                    total = total + 2;
                    time_been_set=true;
                }

            } else if (arr[arrpos].toUpperCase().equals("EXAT")) {
                if(time_been_set!=false){
                    System.out.println("(error) more than one of the same type of modifying flag has been used");
                    return;
                }else {
                    node.expired_time = (Long.parseLong(arr[arrpos + 1]) * 1000);
                    arrpos++;
                    total = total + 2;
                    time_been_set=true;
                }
            }
        }
        }

        //SETS THE VALUE OF THE NODE AFTER SCANNING HAS BEEN DONE AS THE GET ARGUMENT WOULD NOT BE ABLE TO RETRIEVE
        // THE OLD VALUE IF IT WAS SET BEFORE
        node.value=arr[2];



        //IF THE KEEPTTL FLAG WAS NOT SET AND NO OTHER EXPIRATION TIME WAS SET THEN THE VALUE WILL BE SET TO INFINITE
        if(time_saved!=true&&time_been_set!=true){
            node.expired_time=0;
        }



        if(node!=null) {

            if (node.key == null) {
                node.key = arr[1];
            }
            // Compare minus 1 because arr[0] holds the command
            if (total == arr.length - 1) {


                if (exists == false) {
                    stack.push(node);
                    System.out.println(OUTPUTMESSAGE);
                } else {
                    stack.removeElementAt(pos);
                    stack.push(node);
                    System.out.println(OUTPUTMESSAGE);
                }
            } else {
                //System.out.println(total+" "+arr.length);

                System.out.println("(error) "+CODEISSUE);
            }
        }
    }



    public static void SET(String []arr){
        int total=0; // Will be used to make sure all fields in command are being used by totalling the fields
        boolean exists = false;
        NODE exists_node = null;
        int pos =-1;


        //checks to make sure there aren't equal key values that are already in the system
        for(int check = 0;check < stack.size();check++){

            if(stack.elementAt(check).key.equals(arr[1])){
                exists=true;
                exists_node=stack.elementAt(check);
                pos=check;
            }
        }
        //IF IT EXISTS ALREADY IT WILL PASS IN THAT NODE, BUT IF NOT IT WILL CREATE A NEW NODE AND SEND THAT
        if(exists==true){
            operateSet(arr,exists_node,exists,pos);
        }else {
            NODE newNode = new NODE();
        operateSet(arr,newNode,exists,pos);
        }

        }

    public static void GET(String []arr){
        boolean found =false;
        int found_pos=-2;





            for (int check = 0; check < stack.size(); check++) {
                //CHECKS IF THE OBJECT IS EXPIRED OR NOT OR IF THE VALUE WAS NOT SET MEANING IT WONT EXPIRE (0=NO EXPIRATION)
                if ((stack.elementAt(check).key.equals(arr[1]) && (stack.elementAt(check).expired_time - System.currentTimeMillis()) > 0)
                        || (stack.elementAt(check).key.equals(arr[1]) && stack.elementAt(check).expired_time == 0)) {



                    System.out.println(stack.elementAt(check).value);
                    found = true;
                    found_pos=-1;
                }
            }

            //IF THE GET DID NOT FIND ANYTHING WITHIN THE STACK CHECK THE LISTSTACK TO SEE IF
            // THE USER IS TRYING TO USE GET INCORRECTLY AND RETURN AN ERROR MESSAGE
            if(found_pos!=-1){
                for (int search = 0; search < listStack.size(); search++) {
                    if (listStack.elementAt(search).name.equals(arr[1])) {
                        found_pos = search;
                        break;
                    }


            }
                if(found_pos>=0){
                    System.out.println("(error) WRONGTYPE Operation against a key holding the wrong kind of value");
                }else{
                    if (found == false) {
                        System.out.println("> (nil)");
                    }
                }

        }



    }

    public static void DEL(String[] arr){
        int counter = 0;
        int found =0;
        for(int spot =0;spot<stack.size();spot++){
            for(int arrspot=1;arrspot<arr.length;arrspot++) {
                if (stack.elementAt(spot).key.equals(arr[arrspot])){
                    stack.removeElementAt(spot);
                    counter++;
                }
                    //LOOP THROUGH EACH ELEMENT IN ARR PAST SPOT 1
                    // SEE IF IT EXISTS AND IF IT DOES DELETE AND COUNTER++
            }






        }
        System.out.println("> (integer "+counter+")");
    }

    static void LPUSH(String arr[]){
        try {
            int count_added=0;
            LIST node_address_copy=null;
        int found_pos=-1;

            for (int search = 0; search < listStack.size(); search++) {
                if (listStack.elementAt(search).name.equals(arr[1])) {
                    found_pos = search;
                    break;
                }
            }


            //IF A LIST WITH THE SAME NAME WAS ALREADY CREATED ONLY PUSH NEW KEYS ONTO THE EXISTING STACK
            // DO NOT CREATE A NEW STACK WITH SAME NAME
            if(found_pos!=-1){
                for(int arrcycle=2; arrcycle<arr.length;arrcycle ++){
                    listStack.elementAt(found_pos).stacklist.push(arr[arrcycle]);
                    listStack.elementAt(found_pos).number_of_items++;
                }
                node_address_copy=listStack.elementAt(found_pos);
                //COPIED THE MEMORY ADRESS TO ANOTHER OBJ TO BE ABLE TO OUTPUT NUM OF ITEMS IN STACK
            }else {
                // IF A LIST WAS NOT FOUND CREATE A NEW ONE AND PUSH ALL KEYS ONTO IT
                LIST newList = new LIST();
                newList.name=arr[1];
                for (int arrcycle = 2; arrcycle < arr.length; arrcycle++) {

                    newList.stacklist.push(arr[arrcycle]);
                    newList.number_of_items++;
                }
                listStack.push(newList);
                //COPIED THE MEMORY ADRESS TO ANOTHER OBJ TO BE ABLE TO OUTPUT NUM OF ITEMS IN STACK
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
        for(int search=0;search<listStack.size();search++) {
            //System.out.println(listStack.elementAt(search).name+"||"+arr[1]);
            if(listStack.elementAt(search).name.equals(arr[1])){
                found_pos=search;
            }
        }
        //POP THE AMOUNT GIVEN
        if(found_pos!=-1) {
            //IF STACK OF ITEMS IN THE LIST IS EMPTY DO NOT TRY AND POP
            if (listStack.elementAt(found_pos).stacklist.empty()) {
                System.out.println("> (nil)");
            } else {


                if (arr.length == 2) {
                    //IF THERE IS NO ARGUMENT FOR AMOUNT DEFAULT TO ONE POP
                    System.out.println(listStack.elementAt(found_pos).stacklist.peek());
                    listStack.elementAt(found_pos).stacklist.pop();
                    listStack.elementAt(found_pos).number_of_items--;
                } else if (arr.length == 3) {
                    //PARSE 3RD INPUT FOR INT AND POP THAT MANY TIMES, ONLY TO MAX SIZE
                    int counter = 0;
                    //IF THE AMOUNT TO POP IS BIGGER THAN THE STACK SIZE DEFAULT TO THE STACK SIZE
                    if(Integer.parseInt(arr[2])>listStack.elementAt(found_pos).stacklist.size()){
                        arr[2]=listStack.elementAt(found_pos).stacklist.size()+"";
                    }

                    for (int loop_amount = Integer.parseInt(arr[2]); loop_amount > 0; loop_amount--) {

                        System.out.println(++counter + ") " + listStack.elementAt(found_pos).stacklist.peek());

                        listStack.elementAt(found_pos).stacklist.pop();
                        listStack.elementAt(found_pos).number_of_items--;
                    }
                }


            }
        }
            //IF THE LIST DOESNT EXIST OUTPUT NILL
            else{
                System.out.println("> (nil)");
            }

    }catch(Exception e) {
        System.out.println("(error) syntax error"+e);
    }

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

                case "OUT":
                    // OUTPUTS ALL KEY AND VALS (DOESNT WORK WITH LISTS CREATED WITH LPUSH)
                    while (stack.empty() != true) {
                        System.out.println(stack.peek().key + " " + stack.peek().value + " " + stack.peek().expired_time);
                        stack.pop();
                    }
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