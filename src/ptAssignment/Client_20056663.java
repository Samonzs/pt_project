package ptAssignment;

public class Client_20056663 {

    private int ClientID;
    private String ClientName;
    private String StreetAddress;
    private String Suburb;
    private String State;
    private String PostCode;

    private String fName;
    private String lName;


// GETTERS AND SETTERS

    
//client ID
    public void setClientID(int clientId) {
        ClientID = clientId;

    }

    public int getClientID() {

        return ClientID;
    }

// Client Name
    public void setClientName(String clientName){
          ClientName = clientName;
          String[] temp = ClientName.split(" ");
          fName = temp[0];
          lName = temp[1];	
         // this was contruicted to split the 2 values in the name , which are the first and last name, 
         //this simply sperates them both, so we deal with them individually later throught the code , like sorting the last names in ascending order
        }

    public String getClientName() { // get full name
          return ClientName;
        }

    public String getLastName() // get only last name 
    {
    	return lName;
    }

    public String getFirstName() // get only first name
    {
    	return fName;
    }



//StreetAddress
    public void setStreetAddress(String streetAddress) {
        StreetAddress = streetAddress;

    }

    public String getStreetAddress() {

        return StreetAddress;
    }

//Suburb
    public void setSuburb(String suburb) {
        Suburb = suburb;

    }

    public String getSuburb() {

        return Suburb;
    }

//State
    public void setState(String state) {
        State = state;

    }

    public String getState() {

        return State;
    }

//Post Code
    public void setPostCode(String postCode) {
        PostCode = postCode;

    }

    public String getPostCode() {

        return PostCode;
    }

}