class directIP{
    //searched IP is for user input, static final methods are for the provided IP, subnet, and routes
    private
        int counter, fullAddress = 0;
        char[] binaryIP = new char[32];        //Binary of input IP address
        char[] bitSelect = new char[32];       //Bits to use for mask
        String binaryAndResult, matchedIP;
        String[] ipAddressMask = {              //  converted to binary for ease of comparison
            "10000111001011100011100000000000", //  135.46.56.0/22
            "10000111001011100011110000000000", //  135.46.60.0/22
            "11000000001101010010100000000000"};//  192.53.40.0/23
        String[] routes = {
            "Interface 0",
            "Interface 1",
            "Router 1",
            "Router 2"}; // use if default gateway
        String[] binaryAnd = {                  //net mask
                "11111111111111111111110000000000",
                "11111111111111111111111000000000",
                "11111111111111111111111111111111"};
        String[] initialIPvals = {  //first numbers of ip address
                "135",
                "192"};

    //used to gather input from the user in String format then converts the input values into binary
    public
        void setSearchedIP(String testInput) {
            //replaces all of the input so that it's in proper IP form
            counter+=counter;
            String searchedIP = testInput.replaceAll("[a-zA-Z+\\s]","");

            // decide how many bits for binary AND
            bitSelect = binaryAnd[-0].toCharArray();
            for(int i = 0; i < binaryAnd.length-1; i++)
                if (searchedIP.contains(initialIPvals[i])) bitSelect = binaryAnd[i].toCharArray();

            // convert our IP address to binary
            int tempIP = 0;
            for (int x = 0; x < searchedIP.length(); x++){
                char digit = searchedIP.charAt(x);
                if (digit != '.') tempIP = tempIP * 10 + (digit - '0');
                else{
                    fullAddress = (fullAddress << 8) | tempIP;
                    tempIP = 0;
                }
            }
            fullAddress = (fullAddress << 8) | tempIP;

            for (int x = 0; x < 32; x++, fullAddress <<= 1)
                binaryIP[x] = ((fullAddress & 0x80000000) != 0) ? '1' : '0';
        }

        String findRoute() {
            String output = new String();
            /* this should complete the binary AND of the input value, and then find the proper route
	            Additionally, we will use binaryIP and bitSelect for our binary AND, then compare the values
	            to our addresses to decide where to route it */
            // binary AND
            char[] tempResult = new char[32];
            for(int place = 0; place < 32; place++){
                if(binaryIP[place] == '1' && bitSelect[place] == '1') tempResult[place] = '1';
                else tempResult[place] = '0';
            }
            binaryAndResult = String.copyValueOf(tempResult);

            //compare result to routing table ipAddressMask
            int i;
            for(i=0; i<routes.length-1; i++){
                if(ipAddressMask[i].contains(binaryAndResult)){
                    output = routes[i];
                    matchedIP = ipAddressMask[i];
                }
            }
            if(i == (routes.length-1)){
                output = routes[3];
                matchedIP = "No match";
            }
            return output;
        }
        String getBinaryIP(){
            return String.copyValueOf(binaryIP);
        }
        String getMatchedIP(){
            return matchedIP;
        }
        String getBinaryAndResult(){
            return binaryAndResult;
        }
}
