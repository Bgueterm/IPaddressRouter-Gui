class directIP{
    //searched IP is for user input, static final methods are for the provided IP, subnet, and interfaces
    private int counter = 0;
    private int fullAddress = 0;
    private char[] binaryIP = new char[32];  // binary representation of input IP address
    private char[] bitSelect = new char[32]; // decided bits to use based on input IP for binary AND with binaryIP value
    private String binaryAndResult;


    private String matchedIP;
    private final String[] ipAddressMask = {
            "10000111001011100011100000000000", //  135.46.56.0/22
            "10000111001011100011110000000000", //  135.46.60.0/22
            "11000000001101010010100000000000"};//  192.53.40.0/23
    private final String[] interfaces = {
            "Interface 0",
            "Interface 1",
            "Router 1",
            "Router 2"}; // use if default gateway

    //used to gather input from the user in String format then converts the input values into binary
    protected void setSearchedIP(String testInput) {
        //replaces all of the input so that it's in proper IP form
        counter++;
        String searchedIP = testInput.replaceAll("[a-zA-Z+\\s]","");

        // decide how many bits for binary AND
        String tempBit;
        if     (searchedIP.contains("135")) tempBit = "11111111111111111111110000000000";
        else if(searchedIP.contains("192")) tempBit = "11111111111111111111111000000000";
        else tempBit = "11111111111111111111111111111111";
        bitSelect = tempBit.toCharArray();

        // convert our IP address to binary
        int tempIP = 0;
        for (int x = 0; x < searchedIP.length(); x++){
            char digit = searchedIP.charAt(x);
            if (digit != '.') {
                tempIP = tempIP * 10 + (digit - '0');
            }
            else{
                fullAddress = (fullAddress << 8) | tempIP;
                tempIP = 0;
            }
        }
        fullAddress = (fullAddress << 8) | tempIP;

        for (int x = 0; x < 32; x++, fullAddress <<= 1){
            binaryIP[x] = ((fullAddress & 0x80000000) != 0) ? '1' : '0';
        }
    }

    protected String findRoute() {
        String output;
        /* this should complete the binary AND of the input value, and then find the proper route
	       Additionally, we will use binaryIP and bitSelect for our binary AND, then compare the values
	       to our addresses to decide where to route it */
        // binary AND
        char[] tempResult = new char[32];
        for(int place = 0; place < 32; place++){
            if(binaryIP[place] == '1' && bitSelect[place] == '1'){
                tempResult[place] = '1';
            }
            else tempResult[place] = '0';
        }
        binaryAndResult = String.copyValueOf(tempResult);

        //compare result to routing table ipAddressMask
        if      (ipAddressMask[0].contains(binaryAndResult)){
            output = interfaces[0];
            matchedIP = ipAddressMask[0];
        }
        else if (ipAddressMask[1].contains(binaryAndResult)){
            output = interfaces[1];
            matchedIP = ipAddressMask[1];
        }
        else if (ipAddressMask[2].contains(binaryAndResult)){
            output = interfaces[2];
            matchedIP = ipAddressMask[2];
        }
        else{
            output = interfaces[3];
            matchedIP = new String("No Match");
        }

        return output;
    }
    protected String getBinaryIP(){
        return String.copyValueOf(binaryIP);
    }
    protected String getMatchedIP(){
        return matchedIP;
    }
    protected String getBinaryAndResult(){
        return binaryAndResult;
    }
}


