package myproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAO {

    private Connection con;

    public DAO() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ovsdata", "root", "root");
            System.out.println("connected");
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("loaded");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("connection closed");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertVoter(String fname, String lname, String gender, String pno, String prn, String password) throws VoteException {
        try {
            int id = 0;
            Statement st = con.createStatement();
            String q = "SELECT * FROM registration ORDER BY id DESC LIMIT 1";
            ResultSet rs = st.executeQuery(q);
            if (rs.next()) {
                id = rs.getInt("id");
                id++;
            }
            String query = "INSERT INTO registration VALUES(?,?,?,?,?,?,?)";
            try (PreparedStatement ps1 = con.prepareStatement(query)) {
                ps1.setInt(1, id);
                ps1.setString(2, fname);
                ps1.setString(3, lname);
                ps1.setString(4, gender);
                ps1.setString(5, pno);
                ps1.setString(6, prn);
                ps1.setString(7, password);

                ps1.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new VoteException();
        }
    }

    public void selectUser(String prn, String password) throws VoteException {
        try {
            String query = "SELECT * FROM registration WHERE prn=? AND password=?";
            try (PreparedStatement ps1 = con.prepareStatement(query)) {
                ps1.setString(1, prn);
                ps1.setString(2, password);
                try (ResultSet rs = ps1.executeQuery()) {
                    if (!rs.next()) {
                        throw new VoteException();
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new VoteException();
        }
    }

    public int selectAdmin(String prn, String password) throws VoteException {
        try {
            String query = "SELECT * FROM admin WHERE prn=? AND password=?";
            try (PreparedStatement ps1 = con.prepareStatement(query)) {
                ps1.setString(1, prn);
                ps1.setString(2, password);
                try (ResultSet rs = ps1.executeQuery()) {
                    if (!rs.next()) {
                        return 0;
                    }
                    return 1;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public void insertVote(int vote) throws VoteException {
        try {
            String query = "INSERT INTO votes(vote) VALUES(?)";
            try (PreparedStatement ps1 = con.prepareStatement(query)) {
                ps1.setInt(1, vote);
                ps1.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new VoteException();
        }
    }

    public int getVotes(int id) {
        try {
            String query = "SELECT count(*) FROM VOTES WHERE vote=? ";
            try (PreparedStatement ps1 = con.prepareStatement(query)) {
                ps1.setInt(1, id);
                try (ResultSet rs = ps1.executeQuery()) {
                    rs.next();
                    return rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public String getWinner() {
        try {
            String query = "SELECT opt_name FROM options INNER JOIN (SELECT opt_id, COUNT(*) AS vote_count FROM votes GROUP BY opt_id ORDER BY vote_count DESC LIMIT 1) AS vote_counts ON options.opt_id = vote_counts.opt_id";
            try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
                if (rs.next()) {
                    return rs.getString("opt_name");
                }
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
