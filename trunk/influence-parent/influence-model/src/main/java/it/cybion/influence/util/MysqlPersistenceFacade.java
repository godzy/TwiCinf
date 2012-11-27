package it.cybion.influence.util;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class MysqlPersistenceFacade {
	
	private String host;
	private int port;
	private String mysqlUser;
	private String password;
	private String database;
		
	
	
	public MysqlPersistenceFacade(String host, int port, String mysqlUser, String password, String database) {
		super();
		this.host = host;
		this.port = port;
		this.mysqlUser = mysqlUser;
		this.password = password;
		this.database = database;
	}
	
	
	public List<String> getAllJsonTweets() {
		List<String> jsons = new ArrayList<String>();

		Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://"+host+":"+port+"/"+database;
        String query = "SELECT tweet_json FROM `"+database+"`.tweets;";
        try {
            con = DriverManager.getConnection(url, mysqlUser, password);
            st = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
            	jsons.add(rs.getString("tweet_json"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                //TODO throw a checked exception
            	ex.printStackTrace();
            }
        }
        return jsons;
	}
	
	public List<String> getFriendsEnrichedUsers() {
		List<String> users = new ArrayList<String>();

		Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://"+host+":"+port+"/"+database;
        String query = "SELECT distinct(user_screenname) FROM `"+database+"`.friends;";

        try {
            con = DriverManager.getConnection(url, mysqlUser, password);
            st = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
            	users.add(rs.getString("user_screenname"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                //TODO throw a checked exception
            	ex.printStackTrace();
            }
        }
        return users;   
	}
	
	public void writeFriends(String user, List<String> friends) {
		if (friends.size()==0)
			return;
		Connection con = null;
		PreparedStatement pst = null;

		String url = "jdbc:mysql://"+host+":"+port+"/"+database;
        String query = createFriendsInsertQuery(user, friends);
    		         
        try {
            con = DriverManager.getConnection(url, mysqlUser, password);
            pst = con.prepareStatement(query);
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
            	ex.printStackTrace();
            }
        }		
	}
	
	private String createFriendsInsertQuery(String user, List<String> friends ) {
		String query = "INSERT INTO `"+database+"`.`friends` (`user_screenname`, `friend_screenname`) VALUES ";
		for (String friend : friends) {
			query = query + "('"+user+"', '"+friend+"') ,";
		}
		query = query.substring(0, query.length()-2); //this removes last comma
		return query;
	}
	
	
	
	public List<String> getFollowersEnrichedUsers() {
		List<String> users = new ArrayList<String>();

		Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://"+host+":"+port+"/"+database;
        String query = "SELECT distinct(user_screenname) FROM `"+database+"`.followers;";

        try {
            con = DriverManager.getConnection(url, mysqlUser, password);
            st = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
            	users.add(rs.getString("user_screenname"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                //TODO throw a checked exception
            	ex.printStackTrace();
            }
        }
        return users;   
	}
	
	public void writeFollowers(String user, List<String> friends) {
		if (friends.size()==0)
			return;
		Connection con = null;
		PreparedStatement pst = null;

		String url = "jdbc:mysql://"+host+":"+port+"/"+database;
        String query = createFollowersInsertQuery(user, friends);
    		         
        try {
            con = DriverManager.getConnection(url, mysqlUser, password);
            pst = con.prepareStatement(query);
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
            	ex.printStackTrace();
            }
        }		
	}
	
	/*
	 * This creates a single query string to insert in one shot all followers ids
	 */
	private String createFollowersInsertQuery(String user, List<String> friends ) {
		String query = "INSERT INTO `"+database+"`.`followers` (`user_screenname`, `follower_screenname`) VALUES ";
		for (String friend : friends) {
			query = query + "('"+user+"', '"+friend+"') ,";
		}
		query = query.substring(0, query.length()-2); //this removes last comma
		return query;
	}
	
	
	public List<String> getFriends(String userScreenName) {
		List<String> friends = new ArrayList<String>();

		Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://"+host+":"+port+"/"+database;
        String query = "SELECT friend_screenname FROM `"+database+"`.friends where user_screenname='"+userScreenName+"';";

        try {
            con = DriverManager.getConnection(url, mysqlUser, password);
            st = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
            	friends.add(rs.getString("friend_screenname"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                //TODO throw a checked exception
            	ex.printStackTrace();
            }
        }
        return friends;   
	}
	
	
	public List<String> getFollowers(String userScreenName) {
		List<String> followers = new ArrayList<String>();

		Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://"+host+":"+port+"/"+database;
        String query = "SELECT follower_screenname FROM `"+database+"`.followers where user_screenname='"+userScreenName+"';";

        try {
            con = DriverManager.getConnection(url, mysqlUser, password);
            st = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
            	followers.add(rs.getString("follower_screenname"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                //TODO throw a checked exception
            	ex.printStackTrace();
            }
        }
        return followers;   
	}
	
	
	
}