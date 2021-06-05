package socialmedia;

import java.io.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class is a functioning implementor of the SocialMediaPlatform interface.
 * @version 1.0
 */
public class SocialMedia implements SocialMediaPlatform {
  private int idGiver=0;
  private ArrayList<Account> accountsList = new ArrayList<>();
  private ArrayList<Post> postsList=new ArrayList<>();

  @Override
	public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
    verifyHandle(handle);
		for(Account account : accountsList){
			if(account.getHandle().equals(handle)){
				throw new IllegalHandleException("handle already exists");
			}
		}
		idGiver++;
		Account temp = new Account(idGiver, handle, "");
		accountsList.add(temp);
		return idGiver;
	}

  /**
   * This methods checks if the handle meets required criteria
   * @param handle a handle for the account
   * @throws InvalidHandleException
   */
  public void verifyHandle(String handle) throws InvalidHandleException{
    if(handle == null){
			throw new InvalidHandleException("handle is null");
		}
		if(handle.length() > 30){
			throw new InvalidHandleException("the handle has to be shorter than 30 characters");
		}
		if(handle.contains(" ")){
			throw new InvalidHandleException("handle contains whitespace");
		}
    if(handle.trim().length() == 0){
			throw new InvalidHandleException("handle is empty");
		}
  }

  /**
   * This method checks if the handle exists in the system
   * @param handle a handle for the account
   * @throws HandleNotRecognisedException
   */
  public void doesHandleExist(String handle) throws HandleNotRecognisedException{
    for(Account account : accountsList){
      if(account.getHandle().equals(handle)) {
        return;
      }
    }
    throw new HandleNotRecognisedException("this handle does not exist");
  }

  @Override
	public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
    verifyHandle(handle);
		for(Account account : accountsList){
			if(account.getHandle().equals(handle)){
				throw new IllegalHandleException("handle already exists");
			}
		}
		idGiver++;
		Account temp = new Account(idGiver, handle, description);
		accountsList.add(temp);
		return idGiver;
	}

  @Override
  public void removeAccount(int id) throws AccountIDNotRecognisedException {
    boolean foundAccount=false;
    Account temp=null;
    ArrayList<Post> postsListStorer=new ArrayList<>();
    for(Account account : accountsList){
			if(account.getId() == id){
        foundAccount=true;
        temp=account;
        for(Post post : account.getPostsList()){
          postsListStorer.add(post);
          try{deletePost(post.getId());}
          catch(Exception e){e.printStackTrace();}
        }
      }
    }
    if(foundAccount == false){throw new AccountIDNotRecognisedException("account not found");}
    accountsList.remove(temp);
    for(Post post: postsListStorer){
      post.getAccount().getPostsList().remove(post);
    }
	}

  @Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {
    boolean foundAccount=false;
    Account temp=null;
    ArrayList<Post> postsListStorer=new ArrayList<>();
    for(Account account : accountsList){
			if(account.getHandle() == handle){
        foundAccount=true;
        temp=account;
        for(Post post : account.getPostsList()){
          postsListStorer.add(post);
          try{deletePost(post.getId());}
          catch(Exception e){e.printStackTrace();}
        }
      }
    }
    if(!foundAccount){throw new HandleNotRecognisedException("account not found");}
    accountsList.remove(temp);
    for(Post post: postsListStorer){
      post.getAccount().getPostsList().remove(post);
    }
	}

  @Override
	public void changeAccountHandle(String oldHandle, String newHandle) throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
    for(Account account : accountsList){
      if(account.getHandle().equals(newHandle)){
        throw new IllegalHandleException("new handle already exists");
      }
		}
    verifyHandle(newHandle);
    for(Account account : accountsList){
      if(account.getHandle().equals(oldHandle)) {
        account.setHandle(newHandle);
        return;
      }
    }
		throw new HandleNotRecognisedException("this handle does not exist");
	}

  @Override
	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
    for(Account account : accountsList){
      if(account.getHandle().equals(handle)){
        account.setDescription(description);
        return;
      }
    }
    throw new HandleNotRecognisedException("this handle does not exist");
	}

  @Override
	public String showAccount(String handle) throws HandleNotRecognisedException {
    for(Account account : accountsList){
      if(account.getHandle().equals(handle)){
        return account.toString();
      }
    }
    throw new HandleNotRecognisedException("handle does not exist");
	}

  @Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
    doesHandleExist(handle);
    Account store=null;
    if(message.length()>100){
      throw new InvalidPostException("post message is over 100 characters");
    }
    if(message.trim().length() == 0){
      throw new InvalidPostException("post message is empty");
    }
    for(Account account : accountsList){
      if(account.getHandle().equals(handle)) {
        store=account;
      }
    }
    idGiver++;
    Post temp = new Post(handle, store, idGiver, message);
    postsList.add(temp);
    store.getPostsList().add(temp);
    store.getAllPostTypesList().add(temp);
    return idGiver;
	}

  @Override
	public int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
    doesHandleExist(handle);
    for(Post i:postsList){
      if(i.getId()==id){
        if(i instanceof Endorsement){
          throw new NotActionablePostException("cannot endorse an endorsement");
        }
        else{
          for(Account account : accountsList){
            if(account.getHandle().equals(handle)) {
              String message=i.getMessage();
              idGiver++;
              Endorsement temp=new Endorsement(i, account, idGiver, message);
              i.getEndorsementsList().add(temp);
              i.getAccount().addNumOfEndorsements();
              account.getAllPostTypesList().add(temp);
              return idGiver;
            }
          }
        }
      }
    }
    throw new PostIDNotRecognisedException("post id does not exist");
  }

  @Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
      doesHandleExist(handle);
      if(message.length()>100){
        throw new InvalidPostException("post message is over 100 characters");
      }
      if(message.trim().length() == 0){
        throw new InvalidPostException("post message is empty");
      }
      for(Post i:postsList){
        if(i.getId()==id){
          if(i instanceof Endorsement){
            throw new NotActionablePostException("cannot endorse an endorsement");
          }
          else{
            for(Account account : accountsList){
              if(account.getHandle().equals(handle)) {
                idGiver++;
                Comment temp=new Comment(i, account, idGiver, message);
                i.getCommentsList().add(temp);
                account.getAllPostTypesList().add(temp);
                postsList.add(temp);
                return idGiver;
              }
            }
          }
        }
      }
      throw new PostIDNotRecognisedException("post id does not exist");
	}

  @Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
    boolean foundID = false;
    Post temp=null;
		for (Post post: postsList){
			if (id == post.getId()){
        temp=post;
				foundID = true;
				post.setMessage("The original content was removed from the system and is no longer available.");
        post.getAccount().getAllPostTypesList().remove(post);
        for(Endorsement endorsement : post.getEndorsementsList()){
          endorsement.setMessage("");
         
          endorsement.getPost().getAccount().minusNumOfEndorsements();
          endorsement.getAccount().getAllPostTypesList().remove(endorsement);
        }
        for(Comment comment : post.getCommentsList()){
    
          comment.setPost(null);
        }
			}
		}
		if(!foundID) {
			throw new PostIDNotRecognisedException();
		}
    postsList.remove(temp);
	}

  @Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
    for(Post post:postsList){
      if(post.getId()==id){
        return post.toString();
      }
    }
		throw new PostIDNotRecognisedException("cant find the posts id");
	}

	@Override
	public StringBuilder showPostChildrenDetails(int id)
			throws PostIDNotRecognisedException, NotActionablePostException {
        StringBuilder returner = new StringBuilder();
        for(Post post:postsList){
          if(post.getId()==id){
            if(post instanceof Endorsement){
              throw new NotActionablePostException("this id belongs to endorsement");
            }
            return showChildren(post, 0, new StringBuilder(),new ArrayList<>());
          }
        }
    throw new PostIDNotRecognisedException("cant find post id");
	}

  /**
   * To print the conversation tree
   * @param post the post that needs to be printed
   * @param level the amount of spacing required 
   * @param str the formatted posts
   * @param added the posts that have been added to the StringBuilder
   * @return the posts in a conversation tree
   */
  public StringBuilder showChildren(Post post, int level, StringBuilder str, ArrayList<Post> added){
    for(Post postChild: post.getCommentsList()){
      if(!added.contains(post)){
        str.append(printSinglePost(post, ++level));
        added.add(post);
      }
      showChildren(postChild, ++level, str, added);
    }
    if(post.getCommentsList().size() == 0){
			str.append(printSinglePost(post, level));
		}
		return str;
  }

  /**
   * Formats the post to be added to the StringBuilder
   * @param post the post to be formatted
   * @param level the amount of spacing required to signify the level of the tree
   * @return
   */
  public String printSinglePost(Post post, int level){
    StringBuilder space = new StringBuilder();
    StringBuilder spaceAboveId = new StringBuilder();
    StringBuilder spaceBeforeId = new StringBuilder();
    if(level!=0){spaceBeforeId.append("|\n");}
    for(int i=0; i<level-1; i++){
      spaceBeforeId.append("\t");
    }
    for(int i=0; i<level; i++){
      space.append("\t");
    }
    if(level>0){spaceBeforeId.append("| > ");}
    return spaceBeforeId + "ID: "+post.getId()+"\n"+space+"Account: "+ post.getHandle() +"\n"+space+ "No. endorsements: "+post.getEndorsementsList().size()+"| No. comments: "+post.getCommentsList().size()+"\n"+space+post.getMessage()+"\n";
  }  

  @Override
	public int getNumberOfAccounts() {
		return accountsList.size();
	}

  @Override
	public int getTotalOriginalPosts() {
    int count=0;
		for(Post post : postsList){
			if(!(post instanceof Comment) && !(post instanceof Endorsement)){
				count++;
			}
		}
		return count;
	}

  @Override
	public int getTotalEndorsmentPosts() {
    int count = 0;
		for (Account account : accountsList) {
			for (Post post : account.getAllPostTypesList()) {
				if(post instanceof Endorsement){
					count++;
				}
			}
		}
		return count;
	}

  @Override
	public int getTotalCommentPosts() {
    int count = 0;
		for (Account account : accountsList) {
			for (Post post : account.getAllPostTypesList()) {
				if(post instanceof Comment){
					count++;
				}
			}
		}
		return count;
	}

  @Override
	public int getMostEndorsedPost() {
    int count = -1;
    int id=0;
		for (Post post : postsList) {
			if(count < post.getEndorsementsList().size()){
        count = post.getEndorsementsList().size();
        id=post.getId();
      }
    }
		return id;
	}

  @Override
	public int getMostEndorsedAccount() {
    int count = -1;
    int id=0;
		for (Account account: accountsList){
			if(account.getNumOfEndorsements() > count){
        count = account.getNumOfEndorsements();
				id = account.getId();
			}
		}
		return id;
	}

  @Override
	public void erasePlatform() {
		idGiver = 0;
    accountsList.clear();
    postsList.clear();
	}

	@Override
	public void savePlatform(String filename) throws IOException {
    try {
			FileOutputStream f = new FileOutputStream(filename);
			ObjectOutputStream o = new ObjectOutputStream(f);
			ArrayList<Object> place = new ArrayList<>();
			place.add(accountsList);
			place.add(postsList);
			place.add(idGiver);
			o.writeObject(place);
			o.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
    ArrayList<Object> extract = null;
		try {
			FileInputStream f = new FileInputStream(filename);
			ObjectInputStream o = new ObjectInputStream(f);
			extract = (ArrayList<Object>) o.readObject();
			o.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    accountsList = (ArrayList<Account>) extract.get(0);
    postsList = (ArrayList<Post>) extract.get(1);
		idGiver=(int) extract.get(2);
	}
}
