package socialmedia;

/**
 * This class represents a Endorsement
 * @version 1.0
 */
public class Endorsement extends Post{
    private Post post;
    private boolean hasEndorsedPost=true;

    /**
     * The constructor for endorsement
     * @param post the post the endorsement is of
     * @param account the account making the endorsement
     * @param id the id of the endorsement
     * @param message the message being endorsed
     */
    public Endorsement(Post post, Account account, int id, String message){
      super(account.getHandle(), account, id, message);
      this.post=post;
    }

    /**
     * Gets the post the endorsement is being made on
     * @return the endorsed post
     */
    public Post getPost() {
      return post;
    }

    @Override
    public String toString(){ return "EP@" + account.getHandle() + ": " + message;}
  }
