package socialmedia;

/**
 * This class represents a Comment
 * @version 1.0
 */
public class Comment extends Post{
    private Post post;

    /**
     * The constructor for comment
     * @param post the post the comment is of
     * @param account the account making the comment
     * @param id the id of the comment
     * @param message the message being commented
     */
    public Comment(Post post, Account account, int id, String message) {
      super(account.getHandle(), account, id, message);
      this.post = post;
    }

    /**
     * Sets the post that is being commented on
     * @param post set the post that is being commented on
     */
    public void setPost(Post post){
      this.post=post;
    }

    /**
     * Gets the post the comment is being made on
     * @return the post being commented on
     */
    public Post getPost(){
      return post;
    }
  }
