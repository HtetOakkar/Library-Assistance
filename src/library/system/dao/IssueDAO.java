/*
 * Hay!
 * Hello
 * I'm Htet Oakkar
 */
package library.system.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import library.system.database.Database;
import library.system.model.IssueInfo;
import library.system.model.Member;

/**
 *
 * @author Htet-Oakkar
 */
public class IssueDAO {

    public void saveIssueInfo(IssueInfo issue) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "insert into lbdb.issue (book_id, member_id, issue_date, renew_count) values (?,?,now(),?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, issue.getBookId());
        stmt.setInt(2, issue.getMemberId());
        stmt.setInt(3, 0);
        stmt.execute();
    }

    public IssueInfo getIssuedInfo(int bookId) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "select * from lbdb.issue where book_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, bookId);

        ResultSet result = stmt.executeQuery();
        IssueInfo issueInfo = null;
        if (result.next()) {
            int memberId = result.getInt("member_id");
            Date issueDate = result.getDate("issue_date");
            int renewCount = result.getInt("renew_count");

            issueInfo = new IssueInfo(bookId, memberId, issueDate, renewCount);
        }

        return issueInfo;
    }

    public ObservableList<IssueInfo> getIssuedInfos() throws SQLException {

        Connection conn = Database.getInstance().getConnection();

        ObservableList<IssueInfo> issuedBooklist = FXCollections.observableArrayList();

        String sql = "select * from lbdb.issue";
        Statement stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery(sql);

        while (results.next()) {
            int bookId = results.getInt("book_id");
            int memberId = results.getInt("member_id");
            Date issueDate = results.getDate("issue_date");
            int renewCount = results.getInt("renew_count");

            IssueInfo issueInfo = new IssueInfo(bookId, memberId, issueDate, renewCount);
            issuedBooklist.add(issueInfo);
        }
        return issuedBooklist;
    }

    public void deleteIssueInfo(int bookId) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "delete from lbdb.issue where book_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, bookId);
        stmt.execute();
    }

    public void updateIssueInfo(int bookId) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "update lbdb.issue set renew_count = renew_count + 1 where book_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, bookId);
        stmt.execute();
    }
}
