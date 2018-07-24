/*
 * Hay!
 * Hello
 * I'm Htet Oakkar
 */
package library.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import library.system.database.Database;
import library.system.model.Member;

/**
 *
 * @author Htet-Oakkar
 */
public class MemberDAO {

    public void saveMember(Member member) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        if (member.getId() == 0) {
            String sql = "insert into lbdb.members (name, mobile, address) values (?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, member.getName());
            stmt.setString(2, member.getMobile());
            stmt.setString(3, member.getAddress());
            stmt.execute();
        } else {
            String sql = "insert into lbdb.members (id, name, mobile, address) values (?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, member.getId());
            stmt.setString(2, member.getName());
            stmt.setString(3, member.getMobile());
            stmt.setString(4, member.getAddress());
            stmt.execute();
        }
    }

    public ObservableList<Member> getMembers() throws SQLException {

        Connection conn = Database.getInstance().getConnection();

        ObservableList<Member> memberList = FXCollections.observableArrayList();

        String sql = "select * from lbdb.members";
        Statement stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery(sql);

        while (results.next()) {
            int id = results.getInt("id");
            String name = results.getString("name");
            String mobile = results.getString("mobile");
            String address = results.getString("address");

            Member member = new Member(id, name, mobile, address);
            memberList.add(member);
        }
        return memberList;
    }
    
    public void deleteMember(int id) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "delete from lbdb.members where id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.execute();
    }
    
    public void updateMembers(Member member) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "update lbdb.members set name = ?, mobile = ?, address = ? where id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, member.getName());
        stmt.setString(2, member.getMobile());
        stmt.setString(3, member.getAddress());
        stmt.setInt(4, member.getId());
        stmt.execute();
    }

    public Member getmember(int id) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "select * from lbdb.members where id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet result = stmt.executeQuery();
        Member member = null;
        if (result.next()) {
            String name = result.getString("name");
            String mobile = result.getString("mobile");
            String address = result.getString("address");
            
            member = new Member(id, name, mobile, address);
        }
        return member;
    }
}
