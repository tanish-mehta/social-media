package socialmedia;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a Post
 * @version 1.0
 */
public class Post implements Serializable {
  protected String handle;
  protected Account account=null;
  protected int id;
  protected String message;
  protected ArrayList<Comment> commentsList=new ArrayList<>();
  protected ArrayList<Endorsement> endorsementsList = new ArrayList<>();

  /**
   * A constructor for post
   * @param handle the handle of the account making the post
   * @param account the account making the post
   * @param id the id of the post
   * @param message the message of the post
   */
  public Post(String handle, Account account, int id, String message){
    this.handle = handle;
    this.account=account;
    this.id = id;
    this.message = message;
    
  }

  /**
   * Gets the handle of the account that made the post
   * @return the string containing the account handle of the account that made the post
   */
  public String getHandle() {
    return handle;
  }

  /**
   * Gets the account that made the post
   * @return the account that made the post
   */
  public Account getAccount() {
    return account;
  }

  /**
   * Gets ID of the post
   * @return the integer id
   */
  public int getId() {
    return id;
  }

  /**
   * Gets the message of the post
   * @return the string containing the post message
   */
  public String getMessage() {
    return message;
  }

  /**
   * Gets the endorsements made on this post
   * @return the endorsements made on this post
   */
  public ArrayList<Endorsement> getEndorsementsList() {
    return endorsementsList;
  }

  /**
   * Gets the comments made on this post
   * @return the comments made on this post
   */
  public ArrayList<Comment> getCommentsList() {
    return commentsList;
  }

  /**
   * Set the handle of the account that made the post
   * @param handle the handle of the account that made the post
   */
  public void setHandle(String handle) {
    this.handle=handle;
  }

  /**
   * Gets ID of the post
   * @param id the ID of the post
   */
  public void setId(int id) {
    this.id=id;
  };

  /**
   * Gets message of the post
   * @param message the message of the post
   */
  public void setMessage(String message) {
    this.message=message;
  }

  public String toString(){
    return "ID: "+id+"\nAccount: "+ account.getHandle() + "\nNo. endorsements: "+endorsementsList.size()+"| No. comments: "+commentsList.size()+"\n"+message;
  }
}
