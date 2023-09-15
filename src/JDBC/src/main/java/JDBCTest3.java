package JDBC.src.main.java;

import java.sql.*;
import java.util.Scanner;

public class JDBCTest3 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/jdbcTest?serverTimezone=UTC";
        String id = "root";
        String pw = "wkdrns98";
        Connection conn = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;

        String sql = null, sql2 = null;
        int no=0,rCnt=0;
        String name = null, title = null, contents = null,wTime = null;
        int menu=0;
        int result=0;
        Scanner sc = new Scanner(System.in);

        try {
            conn = DriverManager.getConnection(url,id,pw);
            sql = "INSERT INTO board(name,title,contents) VALUES(?,?,?) ";
            sql2 = "UPDATE board SET contents = ? WHERE no = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt2 = conn.prepareStatement(sql2);
            while (true) {
                System.out.println("메뉴선택");
                System.out.println("1.쓰기");
                System.out.println("2.조회");
                System.out.println("3.수정");
                System.out.println("4.삭제");
                System.out.println("5.test 테이블 생성");
                System.out.println("6.test 테이블 삭제");
                System.out.println("7.종료");
                System.out.println("선택 : ");
                menu = sc.nextInt();
                sc.nextLine();
                switch (menu) {
                    case 1:
                        System.out.println("이름을 입력하세요.");
                        name = sc.nextLine();
                        System.out.println("제목을 입력하세요.");
                        title = sc.nextLine();
                        System.out.println("내용을 입력하세요.");
                        contents = sc.nextLine();
                        pstmt.setString(1,name);
                        pstmt.setString(2,title);
                        pstmt.setString(3,contents);
                        result = pstmt.executeUpdate();
                        if(result > 0)
                            System.out.println("입력완료");
                        break;
                    case 2:
                        sql = "SELECT * FROM board ORDER BY no DESC";
                        rs = pstmt.executeQuery(sql);
                        while(rs.next())
                        {
                            no = rs.getInt("no");
                            name = rs.getString("name");
                            title = rs.getString("title");
                            contents = rs.getString("contents");
                            wTime = rs.getString("wTime");
                            rCnt = rs.getInt("rCnt");
                            System.out.println("번호 "+no);
                            System.out.println("이름 "+name);
                            System.out.println("제목 "+title);
                            System.out.println("내용 "+contents);
                            System.out.println("시간 "+wTime);
                            System.out.println("조회수 "+rCnt);
                            System.out.println();
                        }
                        break;
                    case 3:
                        System.out.println("수정할 번호를 입력하세요");
                        no = sc.nextInt();
                        sc.nextLine();
                        pstmt2.setInt(2,no);
                        System.out.println("내용 입력");
                        contents = sc.nextLine();
                        pstmt2.setString(1,contents);
                        result = pstmt2.executeUpdate();
                        if(result > 0)
                            System.out.println("수정완료");
                        break;
                    case 4:
                        System.out.println("삭제할 번호 입력");
                        no = sc.nextInt();
                        sc.nextLine();
                        sql = "DELETE FROM board WHERE no = "+no;
                        result = pstmt.executeUpdate(sql);
                        if(result > 0)
                            System.out.println("삭제완료");
                        break;
                    case 5:
                        sql = "CREATE TABLE test(name varchar(10))";
                        pstmt.executeUpdate(sql);
                        System.out.println("테이블 생성완료");
                        break;
                    case 6:
                        sql = "DROP TABLE test";
                        pstmt.executeUpdate(sql);
                        System.out.println("테이블 삭제완료");
                        break;
                    case 7:
                        System.out.println("프로그램을 종료합니다.");
                        return;
                    default:
                        System.out.println("잘못 입력하셨습니다.");
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
