package socialmedia;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents an Account
 * @version 1.0
 */
public class Account implements Serializable {
  private int id;
  private String description;
  private String handle;
  private ArrayList<Post> postsList=new ArrayList<>();
  private ArrayList<Post> allPostTypesList=new ArrayList<>();
  private int numOfEndorsements;

  /**
   * A constructor for account
   * @param id the id of the account
   * @param handle the handle of the account
   * @param description the description of the account
   */
  public Account(int id, String handle, String description) {
    this.description=description;
    this.handle=handle;
    this.id=id;
    this.numOfEndorsements=0;
  }

  /**
   * Gets ID of the account
   * @return the integer id
   */
  public int getId() {
    return id;
  }
  
  /**
   * Gets the number of endorsements the account has received
   * @return the number of endorsements
   */
  public int getNumOfEndorsements() {
    return numOfEndorsements;
  }

  /**
   * Gets the handle of the account
   * @return the string containing the account handle
   */
  public String getHandle() {return handle;}
  
  /**
   * Gets the description of the account
   * @return the string containing the account description
   */
  public String getDescription() {return description;}
  
  /**
   * Gets the posts made by this account
   * @return the posts this account has made
   */
  public ArrayList<Post> getPostsList() {return postsList;}
  
  /**
   * Gets all the posts including comment and endorsement made by this account
   * @return all the posts this account has made
   */
  public ArrayList<Post> getAllPostTypesList() {return allPostTypesList;}

  /**
   * Set the handle of the account
   * @param handle the handle of the account
   */
  public void setHandle(String handle) {this.handle = handle;}
  
  /**
   * Gets the description of the account
   * @param description the description of the account
   */
  public void setDescription(String description) {this.description = description;}
  
  /**
   * Gets ID of the account
   * @param id the ID of the account
   */
  public void setId(int id) {this.id = id;}

  /**
   * increments the number of endorsements on posts by this account by one
   */
  public void addNumOfEndorsements(){
    numOfEndorsements++;
  }
  
  /**
   * decrements the number of endorsements on posts by this account by one
   */
  public void minusNumOfEndorsements(){
    numOfEndorsements--;
  }

  
  @Override
    public String toString(){
       return "ID: " + getId() + "\nHandle: " + getHandle() + "\nDescription: "+getDescription() + "\nPost count: " + postsList.size() + "\nEndorse count: " + numOfEndorsements;
    }

}
